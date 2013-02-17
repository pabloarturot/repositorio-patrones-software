package com.greenhouse.base;

public class NetworkInfoDTO {

	private String fromHost;
	private String toHost;
	private String mCastHost;
	private Integer fromPort;
	private Integer toPort;
	private Integer mCastPort;
	
	public NetworkInfoDTO() {

	}

	public String getFromHost() {
		return fromHost;
	}

	public void setFromHost(String fromHost) {
		this.fromHost = fromHost;
	}

	public String getToHost() {
		return toHost;
	}

	public void setToHost(String toHost) {
		this.toHost = toHost;
	}

	public String getmCastHost() {
		return mCastHost;
	}

	public void setmCastHost(String mCastHost) {
		this.mCastHost = mCastHost;
	}

	public Integer getFromPort() {
		return fromPort;
	}

	public void setFromPort(Integer fromPort) {
		this.fromPort = fromPort;
	}

	public Integer getToPort() {
		return toPort;
	}

	public void setToPort(Integer toPort) {
		this.toPort = toPort;
	}

	public Integer getmCastPort() {
		return mCastPort;
	}

	public void setmCastPort(Integer mCastPort) {
		this.mCastPort = mCastPort;
	}
}
