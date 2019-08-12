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
public class NetworkInfo extends Entity {

	private Integer version;
	@JsonProperty("subversion")
	private String subVersion;
	@JsonProperty("protocolversion")
	private Integer protocolVersion;
	@JsonProperty("localservices")
	private String localServices;
	@JsonProperty("timeoffset")
	private Integer timeOffset;
	private Integer connections;
	private List<Network> networks;
	@JsonProperty("relayfee")
	private BigDecimal relayFee;
	@JsonProperty("localaddresses")
	private List<NetworkAddress> localAddresses;

	public NetworkInfo(){}
	public NetworkInfo(Integer version, String subVersion, Integer protocolVersion, 
			String localServices, Integer timeOffset, Integer connections, List<Network> networks, 
			BigDecimal relayFee, List<NetworkAddress> localAddresses) {
		setVersion(version);
		setSubVersion(subVersion);
		setProtocolVersion(protocolVersion);
		setLocalServices(localServices);
		setTimeOffset(timeOffset);
		setConnections(connections);
		setNetworks(networks);
		setRelayFee(relayFee);
		setLocalAddresses(localAddresses);
	}

	public void setRelayFee(BigDecimal relayFee) {
		this.relayFee = relayFee.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
	}

    public void setProtocolVersion(Integer protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setTimeOffset(Integer timeOffset) {
        this.timeOffset = timeOffset;
    }

    public void setSubVersion(String subVersion) {
        this.subVersion = subVersion;
    }

    public void setLocalServices(String localServices) {
        this.localServices = localServices;
    }

    public void setConnections(Integer connections) {
        this.connections = connections;
    }

    public void setLocalAddresses(List<NetworkAddress> localAddresses) {
        this.localAddresses = localAddresses;
    }

    public void setNetworks(List<Network> networks) {
        this.networks = networks;
    }
}