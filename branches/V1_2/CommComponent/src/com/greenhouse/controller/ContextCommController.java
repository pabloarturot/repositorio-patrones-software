package com.greenhouse.controller;

import java.util.Map;

import com.greenhouse.base.Message;
import com.greenhouse.base.NetworkInfoDTO;


public interface ContextCommController {

		public void startMeeting();
	 
	    public void sendMessage(Message message);
	    
	    public Map<Long, Message> receiveMessages();
	    
	    public void endMeeting();
	    
	    public String getHostIp();
	    
	    public String getHostName();

		void sendMessage(Message message, NetworkInfoDTO networkInfoDTO);
}
