package com.greenhouse.base;

public class MessageDTO implements Message{

	private static final long serialVersionUID = 1L;
	
		private String hostIp;
	    private String alias;
	    private double temperature;
	    private long timestamp;
	    private int frequency;
	    private MessageType type;
	    

	    public MessageDTO(final double temperature, final int frequency) {
	        this.temperature = temperature;
	        this.frequency = frequency;
	    }

	    public MessageDTO(final String hostIp, final String alias, final double temperature, final long timestamp, final int frequency, final MessageType type) {
	        this.hostIp = hostIp;
	        this.alias = alias;
	        this.temperature = temperature;
	        this.timestamp = timestamp;
	        this.frequency = frequency;
	        this.type = type;
	    }

		public MessageDTO() {

		}

		public String getHostIp() {
			return hostIp;
		}

		public void setHostIp(String hostIp) {
			this.hostIp = hostIp;
		}

		public String getAlias() {
			return alias;
		}

		public void setAlias(String alias) {
			this.alias = alias;
		}

		public long getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}

		public MessageType getType() {
			return type;
		}

		public void setType(MessageType type) {
			this.type = type;
		}

		public double getTemperature() {
			return temperature;
		}
		
		public void setTemperature(double temperature) {
			this.temperature=temperature;
		}

		public int getFrequency() {
			return frequency;
		}
		
	
		public void setFrequency(int frequency) {
			this.frequency=frequency;
			
		}

		@Override
	    public int compareTo(final Message mensaje) {
	        if (mensaje == null) {
	            return 1;
	        }
	        return alias.compareTo(mensaje.getAlias());
	    }

	    @Override
	    public boolean equals(final Object obj) {
	        if (obj == null) {
	            return false;
	        }
	        if (getClass() != obj.getClass()) {
	            return false;
	        }
	        final MessageDTO other = (MessageDTO) obj;
	        if ((this.alias == null) ? (other.alias != null) : !this.alias.equals(other.alias)) {
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public int hashCode() {
	        int hash = 7;
	        hash = 29 * hash + (this.alias != null ? this.alias.hashCode() : 0);
	        return hash;
	    }
}
