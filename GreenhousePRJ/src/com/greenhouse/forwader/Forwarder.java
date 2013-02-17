package com.greenhouse.forwader;

import java.io.IOException;

import com.greenhouse.base.Message;
import com.greenhouse.base.NetworkInfoDTO;


public interface Forwarder {

	 public void send(Message mensaje, NetworkInfoDTO networkInfoDTO) throws IOException;
}
