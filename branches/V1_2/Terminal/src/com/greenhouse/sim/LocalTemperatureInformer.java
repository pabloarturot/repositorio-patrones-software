package com.greenhouse.sim;

import java.util.Date;

import com.greenhouse.base.Message;
import com.greenhouse.base.MessageDTO;
import com.greenhouse.base.MessageType;
import com.greenhouse.controller.ContextCommController;

public class LocalTemperatureInformer {

	private ContextCommController contextCommunication;

    public LocalTemperatureInformer(ContextCommController contextCommunication){
        this.contextCommunication=contextCommunication;
    }

    public Message getLocalTemperature() {
       
    	Message dto = new MessageDTO(contextCommunication.getHostIp(),contextCommunication.getHostName(),Thermometer.getTemperature(),new Date().getTime(),0,MessageType.INFO_TEMPERATURE);
        return dto;
    }

    void sendInfo(Message dto, int frecRefrescoInfo) {

    	Message mensajeDto = new MessageDTO(contextCommunication.getHostIp(),contextCommunication.getHostName(),dto.getTemperature(),dto.getTimestamp(),frecRefrescoInfo, MessageType.CHANGE_FREQUENCY);   
        contextCommunication.sendMessage(mensajeDto);
    }

}
