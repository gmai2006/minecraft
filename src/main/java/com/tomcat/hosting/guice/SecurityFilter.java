package com.tomcat.hosting.guice;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tomcat.hosting.dao.User;

public class SecurityFilter implements Filter {
	protected static final Log logger = LogFactory.getLog(SecurityFilter.class);
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		String uri = ((HttpServletRequest)servletRequest).getRequestURI();
		String contextpath = ((HttpServletRequest)servletRequest).getContextPath();
		String pagename = uri.substring(contextpath.length() + 1, uri.length());

		if (false == isExcluded(req)
		&& false == securityCheck(req, response)) {
			System.out.println("failed authentication from " + pagename);
			response.sendRedirect(BaseServlet.getContextPath(req) + "login.xhtml");
		}
		else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}
	
	private boolean securityCheck(HttpServletRequest req, HttpServletResponse resp) {
		User user = (User)req.getSession().getAttribute("user");
		if (null == user) {
			logger.error("User not found ");
			return false;
		}
		return true;
	}
	
	private boolean isExcluded(HttpServletRequest req) {
		String uri = req.getRequestURI();
		String contextpath = req.getContextPath();
		String pageName = uri.substring(contextpath.length() + 1, uri.length());
		if (pageName.indexOf("/") != -1) {
			pageName = pageName.substring(pageName.lastIndexOf("/") + 1);
		}
		if (pageName.indexOf("?") != -1) { //strip query string
			pageName = pageName.substring(0, pageName.indexOf("?"));
		}
		if (pageName.indexOf(".") == -1
				&& !pageName.toLowerCase().endsWith("login")) return false; //check all pages without extension
		
		if (pageName.toLowerCase().endsWith(".css")
		||  pageName.toLowerCase().endsWith(".js")
		||  pageName.toLowerCase().endsWith(".jpg")
		||  pageName.toLowerCase().endsWith(".gif")
		||  pageName.toLowerCase().endsWith(".png")
		||  pageName.toLowerCase().endsWith(".jpeg")
		||  pageName.toLowerCase().endsWith(".tff")
		||  pageName.toLowerCase().endsWith(".woff")
		||  pageName.toLowerCase().endsWith("login.xhtml")
		||  pageName.toLowerCase().endsWith("login")
		||  pageName.toLowerCase().endsWith("login.html")
		) return true;
		else return false;
	}
}
