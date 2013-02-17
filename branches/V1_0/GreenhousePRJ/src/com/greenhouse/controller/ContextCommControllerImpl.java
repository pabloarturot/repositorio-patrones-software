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



public class ContextCommControllerImpl implements ContextCommController, ControllerListener {
	
	private final Map<String, String> ipTables = new HashMap<String, String>();
    private final Map<String, Message> messages = new HashMap<String, Message>();
    private Forwarder forwarderUDP;
    private Receiver receiverUDP;
    private Receiver receiveerMultiCast;
    private String hostIp;
    private String hostName;
    NetworkInfoDTO networkInfoDTO;

    public ContextCommControllerImpl() {
        try {
        	
        	NetworkInfoUtil.getBundle();
		 	
		 	networkInfoDTO = new NetworkInfoDTO();
		 	networkInfoDTO.setClientHost(NetworkInfoUtil.getHost(NetworkInfoUtil.CLIENT_HOST));
		 	networkInfoDTO.setServerHost(NetworkInfoUtil.getHost(NetworkInfoUtil.SERVER_HOST));
		 	networkInfoDTO.setClientPort(NetworkInfoUtil.getPort(NetworkInfoUtil.CLIENT_PORT));
		 	networkInfoDTO.setServerPort(NetworkInfoUtil.getPort(NetworkInfoUtil.SERVER_PORT));
		 	networkInfoDTO.setmCastHost(NetworkInfoUtil.getHost(NetworkInfoUtil.MCAST_ADDR));
		 	networkInfoDTO.setmCastPort(NetworkInfoUtil.getPort(NetworkInfoUtil.MCAST_PORT));
		 	
		 	
            hostName = NetworkInfoUtil.getHost(NetworkInfoUtil.CLIENT_NAME);

            hostIp = NetworkUtil.getHostIP();

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
            final Message Message = new MessageDTO(
                    hostIp,
                    hostName,
                    0,
                    new Date().getTime(),
                    0, MessageType.INIT_COMM);
            forwarderUDP.send(Message, networkInfoDTO);
        } catch (final IOException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void sendMessage(final Message message) {
    	final Message send = new MessageDTO(
    			hostIp,
    			hostName,
    			message.getTemperature(),
    			new Date().getTime(),
    			message.getFrequency(), message.getType());
    	try {
    		forwarderUDP.send(send, networkInfoDTO);
    	} catch (final IOException ex) {
    		System.out.println(ex);
    	}
    }
    
    public void sendMessage(final Message message, NetworkInfoDTO networkInfoDTO) {
    	final Message send = new MessageDTO(
    			hostIp,
    			hostName,
    			message.getTemperature(),
    			new Date().getTime(),
    			message.getFrequency(), message.getType());
    	try {
    		forwarderUDP.send(send, networkInfoDTO);
    	} catch (final IOException ex) {
    		System.out.println(ex);
    	}
    }
    
    @Override
    public Map<String, Message> receiveMessages() {
    	final Map<String, Message> mapRetorno = new HashMap<String, Message>();
    	mapRetorno.putAll(messages);
    	messages.clear();
    	return mapRetorno;
    }
    
    @Override
    public void endMeeting() {
        try {
			receiverUDP.end();
		} catch (IOException ex) {
			System.out.println(ex);
		}
    }

  
	@Override
	public void notifyUDPMsg(final Message message) {
		ipTables.put(message.getHostIp(), message.getAlias());
		messages.put(message.getHostIp(), message);
	}


}
