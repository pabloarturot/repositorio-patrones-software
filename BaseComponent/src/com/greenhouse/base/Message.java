package com.greenhouse.base;

import java.io.Serializable;

public interface Message extends Serializable, Comparable<Message> {

	public String getHostIp();
	
	public void setHostIp(String hostIp);
    
    public String getAlias();
    
    public void setAlias(String alias);
    
    public double getTemperature();
    
    public void setTemperature(double temperature);
    
    public Integer getPort();
    
    public void setPort(Integer port);

    public long getTimestamp();
    
    public void setTimestamp(long timeStamp);

    public int getFrequency();
    
    public void setFrequency(int frequency);
   
	public MessageType getType();

	public void setType(MessageType type);
}
