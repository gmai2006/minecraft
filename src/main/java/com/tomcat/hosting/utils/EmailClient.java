package com.tomcat.hosting.utils;

import javax.mail.Message.RecipientType;

import org.codemonkey.simplejavamail.Email;
import org.codemonkey.simplejavamail.Mailer;

public class EmailClient implements Runnable
{
	static StringEncrypter encrypter;
	static Mailer MAIL;
	static
	{
		try {
		encrypter = new StringEncrypter(StringEncrypter.DES_ENCRYPTION_SCHEME);
		MAIL = new Mailer("tomcathostingservice.com", 25, 
				"support@tomcathostingservice.com", encrypter.decrypt("+NblWe2o8gJJnrI/qWoGHw=="));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	Email email;
	
	public EmailClient(String from, String[] to, String subject, String message)
	{
		email = new Email();
		email.setFromAddress(from, from);
		email.setSubject(subject);
		for (int i = 0; i < to.length ;i++)
		{
			email.addRecipient(to[i], to[i], RecipientType.TO);
		}
		email.setText(message);
	}
	
	public static void main(String[] s)
	{
		EmailClient mailer = new EmailClient("support@tomcathostingservice.com", 
				new String[] {"support@tomcathostingservice.com"}, "test", "from cert application" );
		new Thread(mailer).run();
		System.out.println("sent successfully");
	}

	@Override
	public void run() {
		MAIL.sendMail(email);
	}
}
