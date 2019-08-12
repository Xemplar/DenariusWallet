package com.xemplarsoft.libs.crypto.common;

import com.xemplarsoft.libs.crypto.server.NodeProps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**An abstract superclass containing the core functionality required for constructing 
 * &amp; configuring new <i>bitcoind</i> consumer instances (<i>i.e.</i> {@code CryptoLinkRPC}
 * (JSON-RPC API), {@code BtcdDaemon} ('callback-via-shell-command' API) etc.).*/
public abstract class AgentConfigurator {
	private static final Logger LOG = LoggerFactory.getLogger(AgentConfigurator.class);
	public abstract Set<NodeProps> getRequiredProperties();
	private Properties nodeConfig;
	
	public Properties checkNodeConfig(Properties nodeConfig) {
		for (NodeProps property : getRequiredProperties()) {
			if (nodeConfig.getProperty(property.getKey()) == null) {
				LOG.warn("-- checkNodeConfig(..): node property '{}' not set; reverting to "
						+ "default value '{}'", property.getKey(), property.getDefaultValue());
				nodeConfig.setProperty(property.getKey(), property.getDefaultValue());
			}
		}
		this.nodeConfig = nodeConfig;
		return nodeConfig;
	}
	
	public Properties toProperties(Object... values) {
		Properties properties = new Properties();
		List<NodeProps> requiredProperties = new ArrayList<>(
				getRequiredProperties());
		for (int i = 0; i < requiredProperties.size(); i++) {
			if (values[i] != null) {
				String key = requiredProperties.get(i).getKey();
				properties.setProperty(key, values[i].toString());
			}
		}
		return properties;
	}

	public Properties getNodeConfig() {
		return nodeConfig;
	}
}