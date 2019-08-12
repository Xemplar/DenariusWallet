package com.xemplarsoft.libs.crypto.server.http;

/**A set of commonly-used constants specified by the HTTP protocol.*/

public final class HttpConstants {
	public static final String REQ_METHOD_GET = "GET";
	public static final String REQ_METHOD_POST = "POST";
	
	public static final String HEADER_AUTH = "Authorization";
	public static final String HEADER_CONTENT_TYPE = "Content-Type";
	public static final String HEADER_SERVER = "Server";
	
	public static final String AUTH_SCHEME_NONE = "";
	public static final String AUTH_SCHEME_BASIC = "Basic";
}