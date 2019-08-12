package com.xemplarsoft.libs.crypto.server.http.client;

import com.xemplarsoft.libs.crypto.server.http.HttpLayerException;

public interface SimpleHttpClient {
	String execute(String reqMethod, String reqPayload) throws HttpLayerException;
	void close();
}