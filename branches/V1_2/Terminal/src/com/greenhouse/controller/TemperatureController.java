package com.greenhouse.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.greenhouse.base.Message;
import com.greenhouse.base.MessageDTO;
import com.greenhouse.base.MessageType;

public class TemperatureController {
	
	    private ContextCommController contextCommunication;
	    private int frequencyInterval;

	    public TemperatureController(){

	        frequencyInterval=8;
	        contextCommunication=new ContextCommControllerImpl();
	        contextCommunication.startMeeting();
	     
	    }


	     public List<Message> getTerminalsTemperatures() {

	         Map<String, Message> temperaturesMap = contextCommunication.receiveMessages();
	         Set<String> temperatures = temperaturesMap.keySet();

	         ArrayList<Message> lecturasTemp = new ArrayList<Message>();

	         for (String key : temperatures) {

	         	Message message = temperaturesMap.get(key);

	             if (message != null && message.getType() == MessageType.INFO_TEMPERATURE) {               
	                
	                 String name = message.getAlias();

	                 if (name == null || (name.trim()).equals("")) {
	                     name=message.getHostIp();
	                 }

	                 message.setAlias(name);
	                 
	                 lecturasTemp.add(message);
	             }
	         }

	         return lecturasTemp;
	     }
	     
	     public void checkPreferredFrequency(){
	    	 
	    	 Map<String, Message> temperaturesMap = contextCommunication.receiveMessages();
	    	 
	    	 long timeStamp=0;
	    	 long temp;
	    	 for (Entry<String, Message> entry : temperaturesMap.entrySet()) {
	    		 
	    		 if(entry.getValue().getType() == MessageType.CHANGE_FREQUENCY){
	    			 
	    			 temp=entry.getValue().getTimestamp();
	    			 
	    			 if(temp>timeStamp){
	    				 timeStamp=temp;
	    				 setFrequencyInterval(entry.getValue().getFrequency());
	    			 }
	    		 }
			}
	     }
	     
	     public void changeTerminalsFrequency(int frequency){
	    	 
	    	 Message message = new MessageDTO();
	    	 message.setFrequency(frequency);
	    	 message.setType(MessageType.CHANGE_FREQUENCY);
	    	 message.setTemperature(getLocalTemperature());
	    	
			contextCommunication.sendMessage(message);
	    	 
	    	 
	     }
	     
	     public double getLocalTemperature(){

	         return Thermometer.getTemperature();
	     }

	    
	    public int getFrequencyInterval() {
	        return frequencyInterval;
	    }
	   
	    public void setFrequencyInterval(int param) {

	        if(param>0){
	           frequencyInterval = param;           
	        }
	    }

	    public void getPreferredFrequency(List<Message> temperatures){
	        
	        long timeStamp=0;

	        for(Message dto : temperatures){

	            long temp=dto.getTimestamp();

	             if(temp>timeStamp){
	                 timeStamp=temp;
	                 setFrequencyInterval(dto.getFrequency());
	             }
	         }
	    }

	    public void closeTerminalConn(){
	        contextCommunication.endMeeting();
	    }

}
