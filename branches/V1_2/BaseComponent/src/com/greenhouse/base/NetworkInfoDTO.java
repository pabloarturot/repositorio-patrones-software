package com.greenhouse.base;

public class NetworkInfoDTO {

	private String terminalHost;
	private String dispatcherHost;
	private String mCastHost;
	private Integer terminalPort;
	private Integer dispatcherPort;
	private Integer mCastPort;
	
	public NetworkInfoDTO() {

	}

	public String getTerminalHost() {
		return terminalHost;
	}

	public void setTerminalHost(String terminalHost) {
		this.terminalHost = terminalHost;
	}

	public String getDispatcherHost() {
		return dispatcherHost;
	}

	public void setDispatcherHost(String dispatcherHost) {
		this.dispatcherHost = dispatcherHost;
	}

	public String getmCastHost() {
		return mCastHost;
	}

	public void setmCastHost(String mCastHost) {
		this.mCastHost = mCastHost;
	}

	public Integer getTerminalPort() {
		return terminalPort;
	}

	public void setTerminalPort(Integer terminalPort) {
		this.terminalPort = terminalPort;
	}

	public Integer getDispatcherPort() {
		return dispatcherPort;
	}

	public void setDispatcherPort(Integer dispatcherPort) {
		this.dispatcherPort = dispatcherPort;
	}

	public Integer getmCastPort() {
		return mCastPort;
	}

	public void setmCastPort(Integer mCastPort) {
		this.mCastPort = mCastPort;
	}

}
