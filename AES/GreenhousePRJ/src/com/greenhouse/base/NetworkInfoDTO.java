package com.greenhouse.base;


public class NetworkInfoDTO {

	private String clientHost;
 	private String serverHost;
 	private String mCastHost;
 	private Integer clientPort;
 	private Integer serverPort;
 	private Integer mCastPort;

 	
 	public NetworkInfoDTO() {
		
	}

	public String getClientHost() {
		return clientHost;
	}

	public void setClientHost(String clientHost) {
		this.clientHost = clientHost;
	}

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public Integer getClientPort() {
		return clientPort;
	}

	public void setClientPort(Integer clientPort) {
		this.clientPort = clientPort;
	}

	public Integer getServerPort() {
		return serverPort;
	}

	public void setServerPort(Integer serverPort) {
		this.serverPort = serverPort;
	}

	public String getmCastHost() {
		return mCastHost;
	}

	public void setmCastHost(String mCastHost) {
		this.mCastHost = mCastHost;
	}

	public Integer getmCastPort() {
		return mCastPort;
	}

	public void setmCastPort(Integer mCastPort) {
		this.mCastPort = mCastPort;
	}

}
