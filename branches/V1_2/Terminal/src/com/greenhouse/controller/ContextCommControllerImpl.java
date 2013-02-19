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
import com.greenhouse.forwader.TCPForwarder;
import com.greenhouse.forwader.UDPForwarder;
import com.greenhouse.reciver.MultiCastReceiver;
import com.greenhouse.reciver.Receiver;
import com.greenhouse.reciver.TCPReceiver;
import com.greenhouse.reciver.UDPReceiver;
import com.greenhouse.util.NetworkInfoUtil;
import com.greenhouse.util.NetworkUtil;

public class ContextCommControllerImpl implements ContextCommController,
		ControllerListener {

	private final Map<Long, Message> messages = new HashMap<Long, Message>();
	private Forwarder forwarder;
	private Receiver receiver;
	private Receiver receiverMultiCast;
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
			
			receiverMultiCast = new MultiCastReceiver(this, networkInfoDTO);

			switch (NetworkInfoUtil.getMode()) {
			case TCP:
				forwarder = new TCPForwarder();
				receiver = new TCPReceiver(this, networkInfoDTO);				
				break;
			case UDP:
				forwarder = new UDPForwarder();
				receiver = new UDPReceiver(this, networkInfoDTO);			
				break;
			default:
				forwarder = new UDPForwarder();
				receiver = new UDPReceiver(this, networkInfoDTO);	
				break;
			}


			initCommListener();
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

	public void initCommListener() {
		final Thread initCommListener = new Thread(new Runnable() {

			@Override
			public void run() {
				receiver.receive();
			}
		});
		initCommListener.start();
	}

	private void initMultiCastListener() {
		final Thread initMultiCastListener = new Thread(new Runnable() {

			@Override
			public void run() {
				receiverMultiCast.receive();
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
			forwarder.send(Message, networkInfoDTO);
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
			forwarder.send(message, networkInfoDTO);
		} catch (final IOException ex) {
			System.out.println(ex);
		}
	}

	public void sendMessage(final Message message,
			final NetworkInfoDTO networkInfoDTO) {
		try {
			forwarder.send(message, networkInfoDTO);
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
			receiver.end();
			messages.clear();
		} catch (final IOException ex) {
			System.out.println(ex);
		}
	}

	@Override
	public void notifyMsg(final Message message) {
		messages.put(message.getTimestamp(), message);
	}

}
