package com.tomcat.hosting.guice;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.stringtemplate.v4.ST;

public class LogoutServlet extends BaseServlet {
	private static final long serialVersionUID = 1666L;
	protected static final Log logger = LogFactory.getLog(LogoutServlet.class);
	
	@Override
	protected String getHtmlTitle(String pageName)
	{
		return "Tomcat Hosting Service - Logout Page";
	}
	@Override
	protected String gethtmlDescr(String pageName)
	{
		return "Tomcat Hosting Service";
	}
	
	
	@Override
	protected void processRequest(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		try {
			HttpSession session = req.getSession(true);
			session.invalidate();
			RequestDispatcher rd= req.getRequestDispatcher("/login.xhtml");
			rd.forward(req, resp);
		}
		catch (Exception e) {
			logger.error("Error while logging to the system " + e.getMessage());
			req.setAttribute("error", e.getMessage());
			RequestDispatcher rd= req.getRequestDispatcher("/error.xhtml");
			rd.forward(req, resp);
			notifyAdmin("ERROR while logging", e.getMessage());
		}
	}
	
	@Override
	protected ST processBody(HttpServletRequest req, HttpServletResponse resp,
			String pageName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
