package com.greenhouse.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.greenhouse.base.DispatcherInfoDTO;
import com.greenhouse.base.Message;
import com.greenhouse.base.MessageDTO;
import com.greenhouse.base.MessageType;
import com.greenhouse.base.NetworkInfoDTO;
import com.greenhouse.forwader.Forwarder;
import com.greenhouse.forwader.TCPForwarder;
import com.greenhouse.forwader.UDPForwarder;
import com.greenhouse.reciver.Receiver;
import com.greenhouse.reciver.TCPReceiver;
import com.greenhouse.reciver.UDPReceiver;
import com.greenhouse.util.NetworkInfoUtil;
import com.greenhouse.util.NetworkUtil;

public class ContextCommControllerImpl implements ContextCommController,
		ControllerListener {

	private final Map<String, String> ipTables = new HashMap<String, String>();
	private final Map<Long, Message> messages = new HashMap<Long, Message>();
	private final List<DispatcherInfoDTO> dispatchers = new ArrayList<DispatcherInfoDTO>();
	public static final String DISPATCHERS = "DISPATCHERS";
	private Forwarder forwarder;
	private Receiver receiver;
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
			networkInfoDTO.setFromPort(NetworkInfoUtil
					.getPort(NetworkInfoUtil.FROM_PORT));
			networkInfoDTO.setmCastHost(NetworkInfoUtil
					.getHost(NetworkInfoUtil.MCAST_ADDR));
			networkInfoDTO.setmCastPort(NetworkInfoUtil
					.getPort(NetworkInfoUtil.MCAST_PORT));

			loadDispatchersInfo(NetworkInfoUtil.getHost(DISPATCHERS));

			hostName = NetworkInfoUtil.getHost(NetworkInfoUtil.FROM_NAME);

			hostIp = NetworkUtil.getHostIP();

			portNumber = networkInfoDTO.getFromPort();
			
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
		} catch (final IOException ex) {
			System.err.println(ex);
			System.exit(-1);
		}
	}

	private void loadDispatchersInfo(final String dispatchersInfo) {

		final String[] info = dispatchersInfo.split(":");

		DispatcherInfoDTO dispatcherInfoDTO;
		for (final String dispatcherInfo : info) {
			final String[] temp = dispatcherInfo.split(",");
			dispatcherInfoDTO = new DispatcherInfoDTO();
			dispatcherInfoDTO.setHost(temp[0]);
			dispatcherInfoDTO.setName(temp[1]);
			dispatcherInfoDTO.setPort(Integer.valueOf(temp[2]));
			dispatchers.add(dispatcherInfoDTO);
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
		final Thread commListener = new Thread(new Runnable() {

			@Override
			public void run() {
				receiver.receive();
			}
		});
		commListener.start();
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

	public void propagateMessage(final Message message) {
		try {
			forwarder.sendBroadCast(message, networkInfoDTO);
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
	public void endMeeting() {
		try {
			receiver.end();
			ipTables.clear();
			messages.clear();
		} catch (final IOException ex) {
			System.out.println(ex);
		}
	}

	@Override
	public void notifyMsg(final Message message) {

		if (!messages.containsKey(message.getTimestamp())) {

			switch (message.getType()) {
			case CHANGE_FREQUENCY:
			case INFO_TEMPERATURE:

				propagateMessage(message);

				if (message.isToDispatcher()) {

					final NetworkInfoDTO networkInfoDTO = new NetworkInfoDTO();
					networkInfoDTO.setFromHost(getHostName());
					networkInfoDTO.setFromPort(getPortNumber());

					if (dispatchers.size() > 0) {

						for (final DispatcherInfoDTO dispatcherInfoDTO : dispatchers) {
							networkInfoDTO.setToHost(dispatcherInfoDTO
									.getHost());
							networkInfoDTO.setToPort(dispatcherInfoDTO
									.getPort());
							message.setToDispatcher(false);
							sendMessage(message, networkInfoDTO);
						}
					}

				}
				break;
			case INIT_COMM:
				ipTables.put(message.getAlias(), message.getHostIp());
				break;
			default:
				break;
			}
		}

		messages.put(message.getTimestamp(), message);

	}

	@Override
	public Map<Long, Message> receiveMessages() {
		final Map<Long, Message> mapRetorno = new HashMap<Long, Message>();
		mapRetorno.putAll(messages);
		messages.clear();
		return mapRetorno;
	}

}
