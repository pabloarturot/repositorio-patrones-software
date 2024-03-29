package com.greenhouse.base;

public class MessageImpl implements Message{

	private static final long serialVersionUID = 1L;
	
		private String hostIp;
	    private String alias;
	    private final double temperature;
	    private long timestamp;
	    private final int frequency;

	    public MessageImpl(final double temperature, final int frequency) {
	        this.temperature = temperature;
	        this.frequency = frequency;
	    }

	    public MessageImpl(final String hostIp, final String alias, final double temperature, final long timestamp, final int frequency) {
	        this.hostIp = hostIp;
	        this.alias = alias;
	        this.temperature = temperature;
	        this.timestamp = timestamp;
	        this.frequency = frequency;
	    }

	    @Override
	    public String getHostIp() {
	        return hostIp;
	    }

	    @Override
	    public String getAlias() {
	        return alias;
	    }

	    @Override
	    public double getTemperature() {
	        return temperature;
	    }

	    @Override
	    public long getTimestamp() {
	        return timestamp;
	    }

	    @Override
	    public int getFrequency() {
	        return frequency;
	    }

	    @Override
	    public int compareTo(final Message mensaje) {
	        if (mensaje == null) {
	            return 1;
	        }
	        return hostIp.compareTo(mensaje.getHostIp());
	    }

	    @Override
	    public boolean equals(final Object obj) {
	        if (obj == null) {
	            return false;
	        }
	        if (getClass() != obj.getClass()) {
	            return false;
	        }
	        final MessageImpl other = (MessageImpl) obj;
	        if ((this.hostIp == null) ? (other.hostIp != null) : !this.hostIp.equals(other.hostIp)) {
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public int hashCode() {
	        int hash = 7;
	        hash = 29 * hash + (this.hostIp != null ? this.hostIp.hashCode() : 0);
	        return hash;
	    }
}
