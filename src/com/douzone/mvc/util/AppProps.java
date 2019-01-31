package com.douzone.mvc.util;

import java.io.IOException;
import java.util.Properties;


public class AppProps {

	private static AppProps appProps = new AppProps();
	private Properties props;
	private static final String PROPERTIES_FILE = "info.properties";

	private AppProps() {
		super();
		propSetUp();

	}

	public static AppProps getProps() {

		if (appProps == null) {

			synchronized (appProps) {

				if (appProps == null) {
					appProps = new AppProps();
				}
			}

		}

		return appProps;

	}

	public String getDbName() {

		return props.getProperty("db.driverName");
	}

	public String getDbUrl() {

		return props.getProperty("db.url");
	}

	public String getDbId() {

		return props.getProperty("db.id");
	}

	public String getDbPwd() {

		return props.getProperty("db.pwd");
	}

	private void propSetUp() {

		props = new Properties();
		try {	
			props.load(this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE));
		} catch (IOException e) {
			System.out.println("Properties File Load Fail!");
			e.printStackTrace();
		}

	}

}
