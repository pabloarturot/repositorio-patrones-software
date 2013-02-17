package com.greenhouse.reciver;

import java.io.IOException;

public interface Receiver {

	public void receive();

    public void end() throws IOException;
}
