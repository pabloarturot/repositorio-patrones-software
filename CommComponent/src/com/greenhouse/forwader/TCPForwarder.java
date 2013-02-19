package com.greenhouse.forwader;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

import com.greenhouse.base.Message;
import com.greenhouse.base.NetworkInfoDTO;
import com.greenhouse.util.MarshallingUtil;


public class TCPForwarder implements Forwarder{

	@Override
	public void send(Message message, NetworkInfoDTO networkInfoDTO)
			throws IOException {
		
		Socket socket = new Socket(networkInfoDTO.getToHost(),networkInfoDTO.getToPort());
				
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
	    oos.writeObject(message);

		socket.shutdownInput();
		socket.shutdownOutput();
		socket.close();
		
		oos.close();
		
		System.out.println("Envio Mensaje " + message.getType()
				+ ": DESDE:" + networkInfoDTO.getFromHost() + " HASTA:"
				+ networkInfoDTO.getToHost());
	}

	@Override
	public void sendBroadCast(Message message, NetworkInfoDTO networkInfoDTO)
			throws IOException {
		
		byte[] byteArray = MarshallingUtil.toByteArray(message);

		DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length,
				InetAddress.getByName(networkInfoDTO.getmCastHost()),
				networkInfoDTO.getmCastPort());
		MulticastSocket multicastSocket = new MulticastSocket(
				networkInfoDTO.getmCastPort());
		multicastSocket.send(packet);
		multicastSocket.close();

		System.out.println("Envio Mensaje Broadcast " + message.getType()
				+ ": DESDE:" + networkInfoDTO.getFromHost() + " HASTA:"
				+ networkInfoDTO.getmCastPort());
		
	}
}
