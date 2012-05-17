package com.tmall.assole.zkclient;
/***
 * 
 * @author jiuxian.tjo
 *
 */
public class ZKClientVersion {
	
	private String version="1.0.0";

	public String getVersion() {
		return version;
	}

	public boolean isCompatible(String dataVersion) {
		return version.trim().equals(dataVersion.trim());
	}


}
