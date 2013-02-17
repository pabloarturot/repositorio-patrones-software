package com.greenhouse.forwader;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import com.greenhouse.base.Message;
import com.greenhouse.base.NetworkInfoDTO;
import com.greenhouse.util.MarshallingUtil;


public class UDPForwarder implements Forwarder{

	 	@Override
	    public void send(Message message, NetworkInfoDTO networkInfoDTO) throws IOException {
	        
	        byte[] byteArray = MarshallingUtil.toByteArray(message);
	        DatagramPacket packet;
	        
	        
	        switch (message.getType()) {
			case CHANGE_FREQUENCY:
				packet = new DatagramPacket(
						byteArray,
						byteArray.length,
						InetAddress.getByName(networkInfoDTO.getmCastHost()),
						networkInfoDTO.getmCastPort());
				 MulticastSocket multicastSocket = new MulticastSocket(networkInfoDTO.getmCastPort());
				 multicastSocket.send(packet);     
			     multicastSocket.close();
				break;
			case INFO_TEMPERATURE:
			case INIT_COMM:
				 packet = new DatagramPacket(
			        		byteArray,
			        		byteArray.length,
			                InetAddress.getByName(networkInfoDTO.getDispatcherHost()),
			                networkInfoDTO.getDispatcherPort());
				 DatagramSocket datagramSocket = new DatagramSocket(networkInfoDTO.getTerminalPort());
				 datagramSocket.send(packet);     
				 datagramSocket.close();
				break;
			}
	        
	       
	        
	       
	    }
}
