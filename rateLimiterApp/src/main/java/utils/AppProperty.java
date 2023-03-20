package utils;

import java.util.Properties;

public class AppProperty {
	private static Properties prop;
	
	public static String getValue(String key) {
		if(prop == null) {
			prop = new Properties();
			try {
				prop.load(AppProperty.class.getResourceAsStream("/application.properties"));
				return prop.getProperty(key);
			}catch(Exception e) {
				e.printStackTrace();
				return "";
			}
		}else {
			return prop.getProperty(key);
		}
	}
}
