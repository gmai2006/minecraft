package com.tomcat.hosting.guice;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.stringtemplate.v4.NumberRenderer;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STRawGroupDir;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.tomcat.hosting.dao.User;
import com.tomcat.hosting.utils.EmailClient;
import com.tomcat.hosting.utils.ServerUtils;

public abstract class BaseServlet extends HttpServlet {
	static public String REGEX = "minecraftserver[0-9]{1}";
	static public Pattern NAME = Pattern.compile(REGEX);
	private static final long serialVersionUID = 1555L;
	private STGroup group;
	protected Log logger = LogFactory.getLog(BaseServlet.class);
	protected String error = "";
	private String message;
	private String cssId = "alert-success";
	
	@Inject
	@Named("location")
	private String location;
	
	@Inject
	@Named("env")
	private String env;
		
	@Override
	  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	    processRequest(req, res);
	  }
	 
	  @Override
	  public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	    processRequest(req, res);
	  }
	 protected ST getFooter(STGroup templateGroup)
	 {
		 return templateGroup.getInstanceOf("footer");
	 }
	 
	public static String getContextPath(HttpServletRequest req) {
		String contextpath = req.getContextPath();
		contextpath = (contextpath.endsWith("/")) ? contextpath : contextpath.concat("/");
		return contextpath;
		
	}
	protected void processRequest(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		logger = LogFactory.getLog(this.getClass());
		logger.info("calling " + this.getClass().getName());
		String context = getContextPath(req);
		String pageName = getPageName(req);
		ST pageST = getLayout();
		
		try {
			User user = getUser(req);

			pageST.add("desc", gethtmlDescr(pageName));
			pageST.add("title", getHtmlTitle(pageName));
			pageST.add("context", context);
			pageST.add(getActiveMenu(pageName), "active");
			if (null == user)
			{
				pageST.add("loginmenu", "true");
			}
			else
			{
				pageST.add("logoutmenu", "true");
				pageST.add("userId", user.getUserId());
			}
			pageST.add("body", processBody(req, resp, pageName));
		} 
		
		catch (Exception e) {
			error = e.getMessage();
			ST errorPage = getContainer().getInstanceOf("error");
			errorPage.add("error", error);
			pageST.add("body", errorPage);
		}

		PrintWriter out = resp.getWriter();
		out.write(pageST.render());
		out.flush();
	}
	
	protected abstract ST processBody(HttpServletRequest req,
			HttpServletResponse resp, String pageName) throws Exception;
	
	
	protected ST getTemplate(STGroup template, String pageName)
	{
		File test = new File(template.getRootDirURL().getFile(), pageName + ".st");
		if (!test.exists())
		{	
			logger.info("page NOT FOUND:" + pageName);
			pageName = "404";
		}
		return template.getInstanceOf(pageName);
	}
	
	protected abstract String getHtmlTitle(String pageName);
	protected abstract String gethtmlDescr(String pageName);
	protected String getActiveMenu(String pageName) {
		return pageName.replaceAll("-", "");
	}
	protected String getPageName(HttpServletRequest req)
	{
		String pageName = req.getRequestURI();
		logger.info("incoming page: " + pageName);
		if (pageName.lastIndexOf(".") == -1)
		{
			pageName = "index";
		}
		else
		{
			pageName = pageName.substring(pageName.lastIndexOf("/") + 1, pageName.lastIndexOf("."));
		}
		return pageName;
	}
	
	protected void notifyAdmin(String subject, String message)
	{
		EmailClient emailer = new EmailClient("support@tomcathostingservice.com", 
				new String[] {"support@tomcathostingservice.com"}, subject, message);
		new Thread(emailer).run();
	}
	protected User getUser(HttpServletRequest request)
	{
		HttpSession session = request.getSession(true);
		return (User)session.getAttribute("user");
	}
	protected String getReferer(HttpServletRequest request)
	{
		HttpSession session = request.getSession(true);
		return (String)session.getAttribute("referer");
	}
	
	protected void catchException (Exception e, String className) {
		StringWriter output = new StringWriter();
		PrintWriter writer = new PrintWriter(output);
		if (null != e) {
			e.printStackTrace(writer);
		} else {
			writer.write("Null exception");
		}
		writer.flush();
		writer.close();
		logger.error("ERROR: " + output.toString());
		notifyAdmin(className + ": ERROR ", output.toString());
	}
	
	protected void catchException (String className, String message) {
		logger.error("ERROR: " + className + ":" + message);
		notifyAdmin(className + ": ERROR ", message);
	}
	
	protected void setUser(HttpServletRequest request, HttpServletResponse response, User user)
	{
		addData(request, "user", user);
	}
	
	protected void addData(HttpServletRequest request, String id, Object o)
	{
		HttpSession session = request.getSession(true);
		session.setAttribute(id, o);
	}

//	protected abstract boolean getAuthenticated() throws Exception;
//	protected abstract boolean getAuthorized() throws Exception ;
	
	protected STGroup getContainer() {
		if (null == group) {
			group = 
				new STRawGroupDir(getServletContext().getRealPath("/WEB-INF/template/"), '$', '$');
			group.registerRenderer(Number.class, new NumberRenderer());
		}
		return group;
	}
	
	protected ST getLayout() {
		return getContainer().getInstanceOf("layout");
	}
	
	protected boolean preprocess (HttpServletRequest request,
	HttpServletResponse response) throws Exception
	{	
		return true;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCssId() {
		return cssId;
	}

	public void setCssId(String cssId) {
		this.cssId = cssId;
	}
}
