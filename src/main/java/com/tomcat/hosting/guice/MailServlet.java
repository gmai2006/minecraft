package com.tomcat.hosting.guice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.stringtemplate.v4.ST;

import com.tomcat.hosting.utils.EmailClient;

public class MailServlet extends BaseServlet {
	private static final long serialVersionUID = 1666L;

	@Override
	protected String getHtmlTitle(String pageName)
	{
		return "Tomcat Hosting Service - Check out page";
	}
	@Override
	protected String gethtmlDescr(String pageName)
	{
		return "Tomcat Hosting Service - Secure Checout by Paypal";
	}
	
	@Override
	public ST processBody(HttpServletRequest req,
			HttpServletResponse resp, String pageName) throws Exception {
		String from = req.getParameter("email");
		String subject = req.getParameter("subject");
		String message = req.getParameter("message");
		EmailClient emailer = new EmailClient("support@tomcathostingservice.com", 
				new String[] {"support@tomcathostingservice.com"}, subject, "["+ from + "]:\n" + message);
		new Thread(emailer).run();
		return getTemplate(getContainer(), "mail");
	}
}
