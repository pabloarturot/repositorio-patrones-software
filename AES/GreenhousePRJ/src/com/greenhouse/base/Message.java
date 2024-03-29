package com.greenhouse.base;

import java.io.Serializable;

public interface Message extends Serializable, Comparable<Message> {

	public String getHostIp();
    
    public String getAlias();
    
    public double getTemperature();

    public long getTimestamp();

    public int getFrequency();
}
