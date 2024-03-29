package com.greenhouse.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class NetworkUtil {

	public static String getHostIP() {

        Enumeration<NetworkInterface> ifaces = null;

        try {
            ifaces = NetworkInterface.getNetworkInterfaces();
            while (ifaces.hasMoreElements()) {
                NetworkInterface ni = ifaces.nextElement();

                Enumeration<InetAddress> inet = ni.getInetAddresses();

                while (inet.hasMoreElements()) {
                    InetAddress ia = inet.nextElement();

                    if (ia instanceof Inet4Address && !ia.isLoopbackAddress()) {
                        return ia.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            System.out.println(ex);
        }

        return "127.0.0.1";
    }

    public static String getHostName() {
        String hostname = "";
        
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        } catch (UnknownHostException ex) {
            System.out.println(ex);
        }
        
        return hostname;
    }
}
