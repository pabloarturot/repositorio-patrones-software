package com.greenhouse.reciver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import com.greenhouse.base.Message;
import com.greenhouse.base.NetworkInfoDTO;
import com.greenhouse.controller.ControllerListener;
import com.greenhouse.util.MarshallingUtil;



public class UDPReceiver implements Receiver{

		private boolean isAlive = true;
	    private final DatagramSocket socket;
	    private final ControllerListener listener;
	    NetworkInfoDTO networkInfoDTO;

	    public UDPReceiver(final ControllerListener listener, NetworkInfoDTO networkInfoDTO) throws IOException {
	    	
	        this.listener = listener;
	        this.networkInfoDTO = networkInfoDTO;
	        socket = new DatagramSocket (networkInfoDTO.getFromPort());
	    }

	    @Override
	    public void receive() {
		 	
	        while (isAlive) {
	            final DatagramPacket packet = new DatagramPacket(new byte[512], 512);

	            try {
	                socket.receive(packet);

	                Message message = MarshallingUtil.fromByteArray(packet.getData());

	                listener.notifyUDPMsg(message);
	                
	                System.out.println("Mensaje"+message.getType()+" DESDE:"+message.getPort());
	                
	            } catch (final Exception ex) {
	                System.out.println(ex);
	            }
	        }
	    }

	    @Override
	    public void end() throws IOException{
	        isAlive = false;
	        socket.close();
	    }
}
