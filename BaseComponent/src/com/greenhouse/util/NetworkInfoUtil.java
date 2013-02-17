package com.greenhouse.util;

import java.util.ResourceBundle;


public class NetworkInfoUtil {
	
	public static final String DISPATCHER_PORT="DISPATCHER_PORT";
	public static final String DISPATCHER_HOST ="DISPATCHER_HOST";
	public static final String TERMINAL_PORT="TERMINAL_PORT";
	public static final String TERMINAL_HOST="TERMINAL_HOST";
	public static final String TERMINAL_NAME = "TERMINAL_NAME"; 
	public static final String MCAST_ADDR="MCAST_ADDR";
	public static final String MCAST_PORT="MCAST_PORT";
	
	public static ResourceBundle resources; 

	public static void getBundle() {
		resources = ResourceBundle.getBundle("conf");
	}
	
	
	public static Integer getPort(String prop){
		return Integer.valueOf(resources.getString(prop));
	}
	
	public static String getHost(String prop){
		return resources.getString(prop);
	}

}
