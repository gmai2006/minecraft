package com.tomcat.hosting.guice;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.stringtemplate.v4.ST;

import com.tomcat.hosting.dao.User;
import com.tomcat.hosting.utils.ApplicationUtils;
import com.tomcat.hosting.utils.ServerUtils;

public class LoginServlet extends BaseServlet {
	private static final long serialVersionUID = 1666L;
	protected static final Log logger = LogFactory.getLog(LoginServlet.class);
	
	@Override
	protected String getHtmlTitle(String pageName)
	{
		return "Tomcat Hosting Service - Login Page";
	}
	@Override
	protected String gethtmlDescr(String pageName)
	{
		return "Tomcat Hosting Service";
	}
	
	
	@Override
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		boolean result = false;
		String userId = req.getParameter("user");
		String password = req.getParameter("pass");
		if (StringUtils.isEmpty(userId)) result = false;
		else if (StringUtils.isEmpty(password)) result = false;
		else {
			try {
				result = authenticate(userId, password);
			}
			catch (Exception e) {
				logger.error("Error while logging to the system " + e.getMessage());
				req.setAttribute("error", e.getMessage());
				
			}
		}
		if (false == result) {
			ST pageST = getLayout();
			ST st = getTemplate(getContainer(), "login");
			st.add("message", "Invalid userId or password.  Please try again");
			st.add("cssId", "alert-warning");
			pageST.add("body", st);
			PrintWriter out = resp.getWriter();
			out.write(pageST.render());
			out.flush();
		} else {
			logger.info("passed authentication...");
			User user = new User();
			user.setUserId(userId);
			user.setPassword(password);
			setUser(req, resp, user);
			RequestDispatcher rd= req.getRequestDispatcher("/index.xhtml");
			rd.forward(req, resp);
		}
		
	}
	
	private boolean authenticate(String userId, String password) {
		Properties prop = ServerUtils.loadApplicationProperties();
		String admin = prop.getProperty("admin");
		String pass = prop.getProperty("password");
		if (!admin.equals(userId)) return false;
		if (!pass.equals(password)) return false;
		return true;
	}
	@Override
	protected ST processBody(HttpServletRequest req, HttpServletResponse resp,
			String pageName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
