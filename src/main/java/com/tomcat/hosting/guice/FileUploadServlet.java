package com.tomcat.hosting.guice;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.tomcat.hosting.dao.User;
import com.tomcat.hosting.utils.ServerUtils;
import com.tomcat.hosting.utils.StreamCopier;
import com.tomcat.hosting.utils.UnzipUtility;

public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1666L;
	private static final int SIZE = 10*1024;
	private String path;
	@Inject
	public FileUploadServlet(@Named("download.dir") String path) {
		this.path = path;
	}
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,  IOException {
		process (request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,  IOException {
		process (request, response);
	}
	
	private void process(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		String serverId = "";
		File uploadFile = null;
		FileItem type = null;
		try {
			File storage = new File(path);
			if (!storage.exists()) {
				System.out.println("storage NOT found");
				storage.mkdir();
				System.out.println("createtemp storage:" + storage.getName());
			}
			
			System.out.println("destinated directory: " + storage);
		    
//			 Create a factory for disk-based file items
	    	DiskFileItemFactory factory = new DiskFileItemFactory();

//	    	 Set factory constraints
	    	factory.setSizeThreshold(SIZE);
	    	factory.setRepository(storage);
	    	
//	    	 Create a new file upload handler
	    	ServletFileUpload upload = new ServletFileUpload(factory);

//	    	 Parse the request
	    	List<FileItem> /* FileItem */ items = upload.parseRequest(req);
	    	System.out.println("uploaded " + items.size());
	    	
			FileItem textFilefield = findFileField(items, "userfile");
			FileItem fileserverId = findRequestParameter(items, "id");
			type = findRequestParameter(items, "type");
			
//			System.out.println("is item null " + (textFilefield));
//			System.out.println("is serverid null " + (fileserverId));
			serverId = fileserverId.getString();
			if (null != textFilefield && (StringUtils.isNotEmpty(textFilefield.getName())
					&& (null != serverId)))
			{		
				System.out.println("upload file to server:" + serverId);
				File uploadDir = (null != type && "plugins".equals(type.getString())) 
						? ServerUtils.getServerDir(serverId, type.getString())
						: ServerUtils.getServerDir(serverId);
				processUploadedFile(uploadDir, textFilefield);	
				uploadFile = new File(uploadDir, textFilefield.getName());
				if (uploadFile.exists())
				{
					new UnzipUtility().unzip(uploadFile.getAbsolutePath(), 
							uploadDir.getAbsolutePath());
				}
				else System.out.println("Unable to upload:" + uploadFile.getName());
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		String pageName = (null != type && "plugins".equals(type.getString())) 
				? "manage-plugin.xhtml"
				: "manage-map.xhtml";
		String returnPage = getContextPath(req) + pageName+ "?id=" + serverId;
		
		System.out.println("returned page " + returnPage);
		resp.sendRedirect(returnPage);
		//for some reason the forwarding does not work
		//it some how relates to the google mapping which does not accept 
		//a page name with query string as it considers the query string
		//is a part of the page name
//		RequestDispatcher rd= req.getRequestDispatcher(returnPage);
//		rd.forward(req, resp);
	}

	FileItem findFileField(List<FileItem> items, String keyword)
	{
		for (FileItem item : items)
		{
			if (!item.isFormField())
			{
//				System.out.println(item.getFieldName() + ": " + item.getString());
				if (item.getFieldName().equals(keyword) 
						&& (StringUtils.isNotEmpty(item.getName()))) return item;
			}
			else continue;
		}
		return null;
	}
	
	FileItem findRequestParameter(List<FileItem> items, String keyword)
	{
		for (FileItem item : items)
		{
			if (item.isFormField() && item.getFieldName().equals(keyword)) return item;
			else continue;
		}
		return null;
	}
	
	private int processUploadedFile(File dir, FileItem item)
	  {
		  BufferedInputStream input = null;
		  BufferedOutputStream output = null;

		  try
		  {
		    String fileName = item.getName();
		    System.out.println("uploaded file name " + fileName + " type " + fileName.lastIndexOf("."));
		    System.out.println("upload file " + fileName + " to " + dir.getAbsolutePath());
		    File file = new File(dir, fileName);
			input = new BufferedInputStream(item.getInputStream());
			output = new BufferedOutputStream(new FileOutputStream(file));
			StreamCopier.copy(input, output);	
			
		  }
		  catch(Exception e)
		  {
			  System.out.println("ERROR: process upload file due to\n" + e.getMessage());
			  return -1;
		  }
		  finally {
			  try {
				
				  if (null != input)
				  {
					  input.close();
				  }
				  if (null != output)
				  {
					  output.flush();
					  output.close();
				  }
			  } catch (Exception ignored) {}
		  }
		  return 0;
	  }
	
	public static String getContextPath(HttpServletRequest req) {
		String contextpath = req.getContextPath();
		contextpath = (contextpath.endsWith("/")) ? contextpath : contextpath.concat("/");
		return contextpath;
		
	}

}
