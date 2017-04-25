package com.tomcat.hosting.guice;

import java.io.File;
import java.io.FileFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.stringtemplate.v4.ST;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.tomcat.hosting.utils.ServerUtils;

public class CreateServerServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private String minecraftfolder;
	
	@Inject
	public CreateServerServlet(@Named("location") String loc) {
		minecraftfolder = loc;
	}
	
	@Override
	protected String getHtmlTitle(String pageName)
	{
		return "Tomcat Hosting Service";
	}
	@Override
	protected String gethtmlDescr(String pageName)
	{
		return "Tomcat Hosting Service";
	}
	
	@Override
	public ST processBody(HttpServletRequest req,
			HttpServletResponse resp, String pageName) throws Exception {
		ST st = getTemplate(getContainer(), pageName);
		ST menu = getTemplate(getContainer(), "menu-createserver");
		st.add("menu", menu);
		String action = req.getParameter("action");
		if (StringUtils.isNotEmpty(action)) {
			String serverType = req.getParameter("serverType");
			String memory = req.getParameter("memory");
			if (StringUtils.isEmpty(memory)) {
				setMessage("Missing required memory value.  Please select a memory value");
				setCssId("alert-warning");
			} else if (StringUtils.isEmpty(serverType)) {
				setMessage("Missing required server type value.  Please select a server type value");
				setCssId("alert-warning");
			} else {
				try {
					ServerUtils.buildNewMineCraftServer(minecraftfolder, serverType, memory);
					setMessage("Successfully create new minecraft server");
					setCssId("alert-success");
				} catch (Exception e) {
					setMessage("Unable to create new minecraft server due to: " + e.getMessage());
					setCssId("alert-warning");
					catchException(e, this.getClass().getName());
				}
			}
			
			st.add("action", true);
			st.add("message", getMessage());
			st.add("cssId", getCssId());
		}
		File source = new File(minecraftfolder);
		File[] folders = source.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});
		st.add("types", folders);
		return st;
	}
	
}
