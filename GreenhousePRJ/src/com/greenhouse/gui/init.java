package com.greenhouse.gui;

import java.util.List;

import com.greenhouse.base.Message;
import com.greenhouse.sim.TemperatureController;

public class init{

    public init(){

    }

    public static void main(String args[]) {

    	temperatureController = new TemperatureController();

        while(true){
        	
        	Message localMessage = temperatureController.getLocalTemperature();				

            sleep(1000);
            
            System.out.println(localMessage.getAlias()+ ", "+localMessage.getHostIp()+ ", "+localMessage.getTemperature()+ ", "+localMessage.getTimestamp());
            System.out.println("\n");
            int frecuenciaSegs=temperatureController.getFrequencyInterval();
            
            System.out.println("HOST REMOTOS");
            List<Message> terminalsTemperatures = temperatureController.getTerminalsTemperatures();
            for (Message message : terminalsTemperatures) {
            	  System.out.println(message.getAlias()+ ", "+message.getHostIp()+ ", "+message.getTemperature()+ ", "+message.getTimestamp());
			}

            sleep(frecuenciaSegs*1000);
        }
    }

    private static TemperatureController temperatureController;

    private static void sleep(int tiempoEsperaMilis){
		try {
			Thread.sleep(tiempoEsperaMilis);
		} catch (InterruptedException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
    }
}
