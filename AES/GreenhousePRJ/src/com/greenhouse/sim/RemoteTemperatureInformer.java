package com.greenhouse.sim;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.greenhouse.base.Message;
import com.greenhouse.base.MessageImpl;
import com.greenhouse.controller.ContextCommController;

public class RemoteTemperatureInformer {

	private ContextCommController contextCommunication;

    public RemoteTemperatureInformer(ContextCommController contextCommunication){
        this.contextCommunication=contextCommunication;
    }

    public List<Message> getTerminalsTemperatures() {

        Map<String, Message> temperaturesMap = contextCommunication.receiveMessages();
        Set<String> temperatures = temperaturesMap.keySet();

        ArrayList<Message> lecturasTemp = new ArrayList<Message>();

        for (String key : temperatures) {

        	Message message = temperaturesMap.get(key);

            if (message != null) {               
               
                String name = message.getAlias();

                if (name == null || (name.trim()).equals("")) {
                    name=message.getHostIp();
                }

                Message dto = new MessageImpl(message.getHostIp(),name,message.getTemperature(),message.getTimestamp(),message.getFrequency());
                lecturasTemp.add(dto);
            }
        }

        return lecturasTemp;

    }
}
