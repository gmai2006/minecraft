package com.tomcat.hosting.guice;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.stringtemplate.v4.ST;

import com.tomcat.hosting.utils.ApplicationUtils;

public class GenericServlet extends BaseServlet {
	private static final long serialVersionUID = 1666L;
	private static Map<String, String> TITLE = new HashMap<String, String>();
	private static Map<String, String> DESCR = new HashMap<String, String>();
	static
	{
		TITLE.put("index", "Tomcat Hosting Service");
		TITLE.put("about-us", "About Us Tomcat Hosting Service");
		TITLE.put("contact-us", "Contact Us");
		TITLE.put("login", "Tomcat Hosting Service - Customer portal");
		TITLE.put("FAQ", "FAQ");
		TITLE.put("tomcat-hosting", "Tomcat Hosting - JSP Hosting - Java Hosting");
		TITLE.put("support", "Tomcat Hosting - Support - Contact Us");
		TITLE.put("privacy", "Tomcat Hosting - Privacy");
		TITLE.put("user-policy", "Tomcat Hosting - User Policies");
		TITLE.put("email-policy", "Tomcat Hosting - Email Policies");
		TITLE.put("term-of-services", "Tomcat Hosting - Term of services");
		TITLE.put("services", "Java Consulting - JEE projects");
	}
	
	static
	{
		DESCR.put("index", "Provide Java Hosting, Jetty Hosting, JSP hosting , Servlet Hosting, Self Managed Dedicated Linux Servers - and Web 2.0 Developments");
		DESCR.put("about-us", "Provide Java Hosting, Jetty Hosting, JSP hosting , Servlet Hosting, Self Managed Dedicated Linux Servers - and Web 2.0 Developments");
		DESCR.put("contact-us", "Provide Java Hosting, Jetty Hosting, JSP hosting , Servlet Hosting, Self Managed Dedicated Linux Servers - and Web 2.0 Developments");
		DESCR.put("login", "Tomcat Hosting Service - Customer portal");
		DESCR.put("FAQ", "Provide Java Hosting, Jetty Hosting, JSP hosting , Servlet Hosting, Self Managed Dedicated Linux Servers - and Web 2.0 Developments");
		DESCR.put("tomcat-hosting", "Provide Java Hosting, Jetty Hosting, JSP hosting , Servlet Hosting, Self Managed Dedicated Linux Servers - and Web 2.0 Developments");
		DESCR.put("support", "Tomcat Hosting - Support - Contact Us");
		DESCR.put("privacy", "Provide Java Hosting, Jetty Hosting, JSP hosting , Servlet Hosting, Self Managed Dedicated Linux Servers - and Web 2.0 Developments");
		DESCR.put("user-policy", "Tomcat Hosting - User Policies");
		DESCR.put("email-policy", "Tomcat Hosting - Email Policies");
		DESCR.put("term-of-services", "Tomcat Hosting - Term of services");
		DESCR.put("services", "Java Consulting - JEE projects");
	}
	
	@Override
	protected ST processBody(HttpServletRequest req,
			HttpServletResponse resp, String pageName) throws Exception {
		ST body = getTemplate(getContainer(), pageName);
		logger.info("get page" + pageName);
		String error = (String)req.getAttribute("error");
		if (!ApplicationUtils.isStringNull(error)) {
			logger.info("add error message:" + error);
			body.add("error", error);
		}
		return body;
	}
	
	@Override
	protected String getHtmlTitle(String pageName)
	{
		String title = TITLE.get(pageName);
		if (null == title) title = "Tomcat Hosting Service";
		return title;
	}
	@Override
	protected String gethtmlDescr(String pageName)
	{
		String descr = DESCR.get(pageName);
		if (null == descr) descr = "Tomcat Hosting Service";
		return descr;
	}
	
}
