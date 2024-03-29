package com.greenhouse.forwader;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import com.greenhouse.base.Message;
import com.greenhouse.base.NetworkInfoDTO;
import com.greenhouse.util.MarshallingUtil;


public class UDPForwarder implements Forwarder{

	 	@Override
	    public void send(Message message, NetworkInfoDTO networkInfoDTO) throws IOException {
	        
	        byte[] byteArray = MarshallingUtil.toByteArray(message);
	        
	        MulticastSocket multicastSocket = new MulticastSocket(networkInfoDTO.getmCastPort());
	        
	        DatagramPacket packet = new DatagramPacket(
	        		byteArray,
	        		byteArray.length,
	                InetAddress.getByName(networkInfoDTO.getmCastHost()),
	                networkInfoDTO.getmCastPort());
	        
	        multicastSocket.send(packet);     
	        multicastSocket.close();
	    }
}
