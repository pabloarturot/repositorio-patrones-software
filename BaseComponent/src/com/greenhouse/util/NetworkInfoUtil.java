package com.greenhouse.util;

import java.util.ResourceBundle;


public class NetworkInfoUtil {
	
	public static final String TO_PORT="TO_PORT";
	public static final String TO_HOST ="TO_HOST";
	public static final String FROM_PORT="FROM_PORT";
	public static final String FROM_HOST="FROM_HOST";
	public static final String FROM_NAME = "FROM_NAME"; 
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