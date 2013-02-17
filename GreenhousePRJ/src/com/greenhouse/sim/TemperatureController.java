package com.greenhouse.sim;

import java.util.List;

import com.greenhouse.base.Message;
import com.greenhouse.controller.ContextCommControllerImpl;
import com.greenhouse.controller.ContextCommController;

public class TemperatureController {
	
	 	private RemoteTemperatureInformer remoteInformer;
	    private LocalTemperatureInformer localInformer;
	    private ContextCommController contextCommunication;
	    private int frequencyInterval;

	    public TemperatureController(){

	        frequencyInterval=8;
	        contextCommunication=new ContextCommControllerImpl();
	        contextCommunication.startMeeting();
	        
	        remoteInformer=new RemoteTemperatureInformer(contextCommunication);
	        localInformer=new LocalTemperatureInformer(contextCommunication);
	    }


	     public List<Message> getTerminalsTemperatures() {

	         List<Message> temperatures=remoteInformer.getTerminalsTemperatures();
	         getPreferredFrequency(temperatures);

	         return temperatures;
	     }

	     public Message getLocalTemperature(){

	    	 Message dto = localInformer.getLocalTemperature();
	         localInformer.sendInfo(dto,frequencyInterval);
	         return dto;
	     }

	    
	    public int getFrequencyInterval() {
	        return frequencyInterval;
	    }
	   
	    public void setFrequencyInterval(int param) {

	        if(param>0){
	           frequencyInterval = param;           
	        }
	    }

	    private void getPreferredFrequency(List<Message> temperatures){
	        
	        long timeStamp=0;

	        for(Message dto : temperatures){

	            long temp=dto.getTimestamp();

	             if(temp>timeStamp){
	                 timeStamp=temp;
	                 setFrequencyInterval(dto.getFrequency());
	             }
	         }
	    }

	    public void closeTerminalsConn(){
	        contextCommunication.endMeeting();
	    }

}
