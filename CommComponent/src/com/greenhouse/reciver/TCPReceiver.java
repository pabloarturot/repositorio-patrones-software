package com.greenhouse.reciver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.greenhouse.base.Message;
import com.greenhouse.base.NetworkInfoDTO;
import com.greenhouse.controller.ControllerListener;



public class TCPReceiver implements Receiver {

	private boolean isAlive = true;
    private final ServerSocket serverSocket;
    private final ControllerListener controllerListener;

    public TCPReceiver(final ControllerListener controllerListener, NetworkInfoDTO networkInfoDTO) throws IOException {
        serverSocket = new ServerSocket(networkInfoDTO.getFromPort());
        this.controllerListener = controllerListener;
    }

    @Override
    public void receive() {
        while (isAlive) {
            try {
                final Socket client = serverSocket.accept();
                final Thread thread = new Thread(new SocketClient(client));
                thread.start();
            } catch (final IOException ex) {
                System.out.println(ex);
            }
        }
    }

    @Override
    public void end() {
        isAlive = false;
        try {
            serverSocket.close();
        } catch (final IOException ex) {
            System.out.println(ex);
        }
    }

    private class SocketClient implements Runnable {

        private final Socket client;

        public SocketClient(final Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                final ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
                final Message message = (Message) ois.readObject();
                controllerListener.notifyMsg(message);
                
                System.out.println("Recepcion Mensaje "+message.getType()+": DESDE:"+message.getPort()+" "+message.getHostIp());

                client.shutdownInput();
                client.shutdownOutput();
                
            } catch (final IOException ex) {
                System.out.println(ex);
            } catch (final ClassNotFoundException ex) {
                System.out.println(ex);
            }
        }
    }
}