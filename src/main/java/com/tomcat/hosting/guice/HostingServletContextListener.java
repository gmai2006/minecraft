package com.tomcat.hosting.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class HostingServletContextListener extends GuiceServletContextListener {
	
	@Override
	protected Injector getInjector() {
		return Guice.createInjector(
				new MainModule());
	}
}