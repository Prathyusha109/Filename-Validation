package com.processing.model;

import java.io.Serializable;

public class Status implements Serializable{

	private String respCd="";
	private String respName="";
	private String respDesc="";

	/**
	 * @return the respCd
	 */
	public String getRespCd() {
		return respCd;
	}

	/**
	 * @param respCd the respCd to set
	 */
	public void setRespCd(String respCd) {
		this.respCd = respCd;
	}

	/**
	 * @return the respDesc
	 */
	public String getRespDesc() {
		return respDesc;
	}

	/**
	 * @param respDesc the respDesc to set
	 */
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}

	public String getRespName() {
		return respName;
	}

	public void setRespName(String respName) {
		this.respName = respName;
	}

	@Override
	public String toString() {
		return "Status{" +
				"respCd='" + respCd + '\'' +
				", respDesc='" + respDesc + '\'' +
				", respName='" + respName + '\'' +
				'}';
	}
}
