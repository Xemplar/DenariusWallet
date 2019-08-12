package com.xemplarsoft.libs.crypto.server.jsonrpc.client;

import com.xemplarsoft.libs.crypto.server.CommunicationException;
import com.xemplarsoft.libs.crypto.server.CryptocoinException;
import com.xemplarsoft.libs.crypto.server.jsonrpc.JsonMapper;
import com.xemplarsoft.libs.crypto.server.jsonrpc.JsonPrimitiveParser;

import java.util.List;

public interface JsonRpcClient {
	public abstract String execute(String method) throws CryptocoinException, CommunicationException;
	public abstract <T> String execute(String method, T param) throws CryptocoinException, CommunicationException;
	public abstract <T> String execute(String method, List<T> params) throws CryptocoinException,  CommunicationException;
	
	JsonPrimitiveParser getParser();
	JsonMapper getMapper();
	
	void close();
}