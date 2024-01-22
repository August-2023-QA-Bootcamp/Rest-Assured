package util;

import java.io.IOException;
import java.util.Properties;

import constant.FileName;
import constant.Key;

public class Configure {

	private Properties properties;
	
	public Configure(FileName fileName) {
		initProperties(fileName.getFileNameValue());
	}
	
	private void initProperties(String file) {
		if(properties == null) {
			properties = new Properties();
			}
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream(file));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getPropertyValue(Key key) {
		return properties.getProperty(key.getKeyValue());
	}
}
