package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NetworkTotals extends Entity {
	@JsonProperty("totalbytesrecv")
	private Long totalBytesRecv;
	@JsonProperty("totalbytessent")
	private Long totalBytesSent;
	@JsonProperty("timemillis")
	private Long timeMillis;

	public NetworkTotals() {}
	public NetworkTotals(long totalBytesRecv, long totalBytesSent, long timeMillis){
		this.timeMillis = timeMillis;
		this.totalBytesRecv = totalBytesRecv;
		this.totalBytesSent = totalBytesSent;
	}
}