package com.greenhouse.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.greenhouse.base.Message;
import com.greenhouse.base.MessageDTO;
import com.greenhouse.base.MessageType;
import com.greenhouse.base.NetworkInfoDTO;
import com.greenhouse.forwader.Forwarder;
import com.greenhouse.forwader.UDPForwarder;
import com.greenhouse.reciver.MultiCastReceiver;
import com.greenhouse.reciver.Receiver;
import com.greenhouse.reciver.UDPReceiver;
import com.greenhouse.util.NetworkInfoUtil;
import com.greenhouse.util.NetworkUtil;

public class ContextCommControllerImpl implements ContextCommController,
		ControllerListener {

	private final Map<Long, Message> messages = new HashMap<Long, Message>();
	private Forwarder forwarderUDP;
	private Receiver receiverUDP;
	private Receiver receiveerMultiCast;
	private String hostIp;
	private String hostName;
	private Integer portNumber;
	NetworkInfoDTO networkInfoDTO;

	public ContextCommControllerImpl() {
		try {

			NetworkInfoUtil.getBundle();

			networkInfoDTO = new NetworkInfoDTO();
			networkInfoDTO.setFromHost(NetworkInfoUtil
					.getHost(NetworkInfoUtil.FROM_HOST));
			networkInfoDTO.setToHost(NetworkInfoUtil
					.getHost(NetworkInfoUtil.TO_HOST));
			networkInfoDTO.setFromPort(NetworkInfoUtil
					.getPort(NetworkInfoUtil.FROM_PORT));
			networkInfoDTO.setToPort(NetworkInfoUtil
					.getPort(NetworkInfoUtil.TO_PORT));
			networkInfoDTO.setmCastHost(NetworkInfoUtil
					.getHost(NetworkInfoUtil.MCAST_ADDR));
			networkInfoDTO.setmCastPort(NetworkInfoUtil
					.getPort(NetworkInfoUtil.MCAST_PORT));

			hostName = NetworkInfoUtil.getHost(NetworkInfoUtil.FROM_NAME);

			hostIp = NetworkUtil.getHostIP();
			
			portNumber = networkInfoDTO.getFromPort();

			forwarderUDP = new UDPForwarder();
			receiverUDP = new UDPReceiver(this, networkInfoDTO);
			receiveerMultiCast = new MultiCastReceiver(this, networkInfoDTO);

			initUPDListener();
			initMultiCastListener();
		} catch (final IOException ex) {
			System.err.println(ex);
			System.exit(-1);
		}
	}

	public String getHostIp() {
		return hostIp;
	}

	public String getHostName() {
		return hostName;
	}

	public Integer getPortNumber() {
		return portNumber;
	}

	private void initUPDListener() {
		final Thread udpListener = new Thread(new Runnable() {

			@Override
			public void run() {
				receiverUDP.receive();
			}
		});
		udpListener.start();
	}

	private void initMultiCastListener() {
		final Thread initMultiCastListener = new Thread(new Runnable() {

			@Override
			public void run() {
				receiveerMultiCast.receive();
			}
		});
		initMultiCastListener.start();
	}

	@Override
	public void startMeeting() {
		try {
			final Message Message = new MessageDTO(hostIp, hostName,
					portNumber, 0, new Date().getTime(), 0,
					MessageType.INIT_COMM);
			forwarderUDP.send(Message, networkInfoDTO);
		} catch (final IOException ex) {
			System.out.println(ex);
		}
	}

	@Override
	public void sendMessage(final Message message) {
		message.setHostIp(hostIp);
		message.setAlias(hostName);
		message.setPort(portNumber);
		message.setTimestamp(new Date().getTime());
		
		try {
			forwarderUDP.send(message, networkInfoDTO);
		} catch (final IOException ex) {
			System.out.println(ex);
		}
	}

	public void sendMessage(final Message message,
			final NetworkInfoDTO networkInfoDTO) {
		try {
			forwarderUDP.send(message, networkInfoDTO);
		} catch (final IOException ex) {
			System.out.println(ex);
		}
	}

	@Override
	public Map<Long, Message> receiveMessages() {
		final Map<Long, Message> mapRetorno = new HashMap<Long, Message>();
		mapRetorno.putAll(messages);
		messages.clear();
		return mapRetorno;
	}

	@Override
	public void endMeeting() {
		try {
			receiverUDP.end();
			messages.clear();
		} catch (final IOException ex) {
			System.out.println(ex);
		}
	}

	@Override
	public void notifyUDPMsg(final Message message) {
		messages.put(message.getTimestamp(), message);
	}

}
