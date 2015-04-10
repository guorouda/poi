package com.ron.pereference;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class SystemGlobals implements VariableStore {
	private static SystemGlobals globals = new SystemGlobals();
	
	private String defaultConfig;
	private Properties defaults = new Properties();
	
	public static Logger log = Logger.getLogger(SystemGlobals.class);
	
	private SystemGlobals(){}
	
	public static void initGlobals(String appPath, String mainConfigurationFile){
		globals = new SystemGlobals();
		
		globals.defaultConfig = mainConfigurationFile;
		globals.defaults = new Properties();
		
		globals.defaults.put(ConfigKeys.APPLICATION_PATH, appPath);
		globals.defaults.put(ConfigKeys.DEFAULT_CONFIG, mainConfigurationFile);
		
		SystemGlobals.loadDefaults();
	}
	
	public static void loadDefaults() {
		try {
			FileInputStream input = new FileInputStream(globals.defaultConfig);
			globals.defaults.load(input);
			input.close();
		} catch (IOException e) {
		}
	}
	
	public static String getDefaultsValue(String variableName){
		return globals.defaults.getProperty(variableName);
	}
	
	// TODO
	public static String getValue(String variableName){
		return SystemGlobals.getValue(variableName);
	}
	
	@Override
	public String getVariableValue(String variableName) {
		// TODO Auto-generated method stub
		return null;
	}

}
