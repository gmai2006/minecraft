package com.tomcat.hosting;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.tomcat.hosting.guice.MainModule;

public class StandaloneModule extends AbstractModule {

	@Override
	protected void configure() {
		Properties properties = new Properties();
		try {
			InputStream in = MainModule.class.getResourceAsStream("/application.properties");
			properties.load(in);
			Names.bindProperties(binder(), properties);
		} catch (FileNotFoundException e) {
			System.out
					.println("The configuration file Test.properties can not be found");
		} catch (IOException e) {
			System.out.println("I/O Exception during loading configuration");
		}

	}
	
	

}
