package com.tomcat.hosting.guice;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;

public class MainModule extends ServletModule {
	@Override
	protected void configureServlets() {
		Properties properties = new Properties();
		try {
			InputStream in = MainModule.class.getResourceAsStream("/application.properties");
			properties.load(in);
			Names.bindProperties(binder(), properties);
		} catch (FileNotFoundException e) {
			System.out
					.println("The configuration file application.properties can not be found");
		} catch (IOException e) {
			System.out.println("I/O Exception during loading configuration");
		}
		
		bind(GenericServlet.class).in(Singleton.class);
		bind(IndexServlet.class).in(Singleton.class);
		bind(MailServlet.class).in(Singleton.class);
		bind(CreateServerServlet.class).in(Singleton.class);
		bind(ManageServerServlet.class).in(Singleton.class);
		bind(UpdateServerConfigurationServlet.class).in(Singleton.class);
		bind(RConServlet.class).in(Singleton.class);
		bind(ViewLogServlet.class).in(Singleton.class);
		bind(LoginServlet.class).in(Singleton.class);
		bind(LogoutServlet.class).in(Singleton.class);
		bind(ManageMapServlet.class).in(Singleton.class);
		bind(ManagePluginServlet.class).in(Singleton.class);
		bind(FileUploadServlet.class).in(Singleton.class);
		serve("/").with(IndexServlet.class);
		serve("/login").with(LoginServlet.class);
		serve("/logout").with(LogoutServlet.class);
		serve("/upload").with(FileUploadServlet.class);
		serve("/index.xhtml").with(IndexServlet.class);
		serve("/create-server.xhtml").with(CreateServerServlet.class);
		serve("/manage-server.xhtml").with(ManageServerServlet.class);
		serve("/update-server-configuration.xhtml").with(UpdateServerConfigurationServlet.class);
		serve("/rcon.xhtml").with(RConServlet.class);
		serve("/view-logs.xhtml").with(ViewLogServlet.class);
		serve("/manage-map.xhtml").with(ManageMapServlet.class);
		serve("/manage-plugin.xhtml").with(ManagePluginServlet.class);
		serve("/mail").with(MailServlet.class);
		serve("*.xhtml").with(GenericServlet.class);
	}
}
