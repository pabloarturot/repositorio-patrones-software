package com.greenhouse.forwader;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import com.greenhouse.base.Message;
import com.greenhouse.base.NetworkInfoDTO;
import com.greenhouse.util.MarshallingUtil;

public class UDPForwarder implements Forwarder {

	@Override
	public void send(Message message, NetworkInfoDTO networkInfoDTO)
			throws IOException {

		byte[] byteArray = MarshallingUtil.toByteArray(message);
		DatagramPacket packet;

		packet = new DatagramPacket(byteArray, byteArray.length,
				InetAddress.getByName(networkInfoDTO.getToHost()),
				networkInfoDTO.getToPort());
		DatagramSocket datagramSocket = new DatagramSocket(5559);
		datagramSocket.send(packet);
		datagramSocket.close();

		System.out.println("Envio Mensaje " + message.getType()
				+ ": DESDE:" + networkInfoDTO.getFromHost() + " HASTA:"
				+ networkInfoDTO.getToHost());

	}

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
