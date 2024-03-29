package com.greenhouse.util;

import java.util.ResourceBundle;


public class NetworkInfoUtil {
	
	public static final String SERVER_PORT="SERVER_PORT";
	public static final String SERVER_HOST ="SERVER_HOST";
	public static final String CLIENT_PORT="CLIENT_PORT";
	public static final String CLIENT_HOST="CLIENT_HOST";
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
