package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xemplarsoft.libs.crypto.common.Defaults;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PeerNode extends Entity {

	private Integer id;
	private String addr;
	@JsonProperty("addrlocal")
	private String addrLocal;
	private String services;
	@JsonProperty("lastsend")
	private Long lastSend;
	@JsonProperty("lastrecv")
	private Long lastRecv;
	@JsonProperty("bytessent")
	private Long bytesSent;
	@JsonProperty("bytesrecv")
	private Long bytesRecv;
	@JsonProperty("conntime")
	private Long connTime;
	@JsonProperty("pingtime")
	private BigDecimal pingTime;
	@JsonProperty("pingwait")
	private BigDecimal pingWait;
	private Integer version;
	@JsonProperty("subver")
	private String subVer;
	private Boolean inbound;
	@JsonProperty("startingheight")
	private Integer startingHeight;
	@JsonProperty("banscore")
	private Integer banScore;
	@JsonProperty("synced_headers")
	private Integer syncedHeaders;
	@JsonProperty("synced_blocks")
	private Integer syncedBlocks;
	@JsonProperty("syncnode")
	private Boolean syncNode;
	@JsonProperty("inflight")
	private List<Integer> inFlight;
	private Boolean whitelisted;

	public PeerNode(){}
	public PeerNode(Integer id, String addr, String addrLocal, String services, Long lastSend, 
			Long lastRecv, Long bytesSent, Long bytesRecv, Long connTime, BigDecimal pingTime,
			BigDecimal pingWait, Integer version, String subVer, Boolean inbound, 
			Integer startingHeight, Integer banScore, Integer syncedHeaders, 
			Integer syncedBlocks, Boolean syncNode, List<Integer> inFlight, Boolean whitelisted) {
		setId(id);
		setAddr(addr);
		setAddrLocal(addrLocal);
		setServices(services);
		setLastSend(lastSend);
		setLastRecv(lastRecv);
		setBytesSent(bytesSent);
		setBytesRecv(bytesRecv);
		setConnTime(connTime);
		setPingTime(pingTime);
		setPingWait(pingWait);
		setVersion(version);
		setSubVer(subVer);
		setInbound(inbound);
		setStartingHeight(startingHeight);
		setBanScore(banScore);
		setSyncedHeaders(syncedHeaders);
		setSyncedBlocks(syncedBlocks);
		setSyncNode(syncNode);
		setInFlight(inFlight);
		setWhitelisted(whitelisted);
	}

	public void setPingTime(BigDecimal pingTime) {
		this.pingTime = pingTime.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
	}

	public void setPingWait(BigDecimal pingWait) {
		this.pingWait = pingWait.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setAddrLocal(String addrLocal) {
		this.addrLocal = addrLocal;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public void setLastSend(Long lastSend) {
		this.lastSend = lastSend;
	}

	public void setBytesRecv(Long bytesRecv) {
		this.bytesRecv = bytesRecv;
	}

	public void setBanScore(Integer banScore) {
		this.banScore = banScore;
	}

	public void setBytesSent(Long bytesSent) {
		this.bytesSent = bytesSent;
	}

	public void setConnTime(Long connTime) {
		this.connTime = connTime;
	}

	public void setInbound(Boolean inbound) {
		this.inbound = inbound;
	}

	public void setLastRecv(Long lastRecv) {
		this.lastRecv = lastRecv;
	}

	public void setInFlight(List<Integer> inFlight) {
		this.inFlight = inFlight;
	}

	public void setStartingHeight(Integer startingHeight) {
		this.startingHeight = startingHeight;
	}

	public void setSubVer(String subVer) {
		this.subVer = subVer;
	}

	public void setSyncedBlocks(Integer syncedBlocks) {
		this.syncedBlocks = syncedBlocks;
	}

	public void setSyncedHeaders(Integer syncedHeaders) {
		this.syncedHeaders = syncedHeaders;
	}

	public void setSyncNode(Boolean syncNode) {
		this.syncNode = syncNode;
	}

	public void setWhitelisted(Boolean whitelisted) {
		this.whitelisted = whitelisted;
	}
}