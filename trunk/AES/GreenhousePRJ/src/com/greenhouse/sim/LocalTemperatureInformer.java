package com.greenhouse.sim;

import java.util.Date;

import com.greenhouse.base.Message;
import com.greenhouse.base.MessageImpl;
import com.greenhouse.controller.ContextCommController;

public class LocalTemperatureInformer {

	private ContextCommController contextCommunication;

    public LocalTemperatureInformer(ContextCommController contextCommunication){
        this.contextCommunication=contextCommunication;
    }

    public Message getLocalTemperature() {
       
    	Message dto = new MessageImpl(contextCommunication.getHostIp(),contextCommunication.getHostName(),Thermometer.getTemperature(),new Date().getTime(),0);
        return dto;
    }

    void sendInfo(Message dto, int frecRefrescoInfo) {

    	Message mensajeDto = new MessageImpl(contextCommunication.getHostIp(),contextCommunication.getHostName(),dto.getTemperature(),dto.getTimestamp(),frecRefrescoInfo);   
        contextCommunication.sendMessage(mensajeDto);
    }

}
