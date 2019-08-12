package com.xemplarsoft.libs.crypto.common;

/**An enumeration specifying the data formats recognized by btcd-cli4j.*/
public enum DataFormats {
	
	HEX(0, "text/plain"),
	JSON(1, "application/json"), 
	PLAIN_TEXT(2, "text/plain");

	DataFormats(int code, String type){
		this.code = code;
		this.mediaType = type;
	}

	public int getCode(){
		return code;
	}

	public String getMediaType(){
		return mediaType;
	}

	private final int code;
	private final String mediaType;
}