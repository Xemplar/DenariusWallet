package com.xemplarsoft.libs.crypto.server.link;

import com.xemplarsoft.libs.crypto.server.NodeProps;
import com.xemplarsoft.libs.crypto.common.AgentConfigurator;
import com.xemplarsoft.libs.crypto.server.domain.Block;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ClientConfigurator extends AgentConfigurator {
	private static final Logger LOG = LoggerFactory.getLogger(ClientConfigurator.class);
	private String nodeVersion;
	
	@Override
	public Set<NodeProps> getRequiredProperties() {
		return EnumSet.of(NodeProps.RPC_PROTOCOL, NodeProps.RPC_HOST,
				NodeProps.RPC_PORT, NodeProps.RPC_USER, NodeProps.RPC_PASSWORD,
				NodeProps.HTTP_AUTH_SCHEME);
	}
	
	public CloseableHttpClient checkHttpProvider(CloseableHttpClient httpProvider) {
		if (httpProvider == null) {
			LOG.warn("-- checkHttpProvider(..): no preconfigured HTTP provider detected; reverting "
					+ "to library default settings");
			httpProvider = getDefaultHttpProvider();
		}
		return httpProvider;
	}

	public boolean checkNodeHealth(Block bestBlock) {
		long currentTime = System.currentTimeMillis() / 1000;
		if ((currentTime - bestBlock.getTime()) > TimeUnit.HOURS.toSeconds(6)) {
			LOG.warn("-- checkNodeHealth(..): last available block was mined >{} hours ago; please "
					+ "check your network connection", ((currentTime - bestBlock.getTime()) / 3600));
			return false;
		}
		return true;
	}

	private CloseableHttpClient getDefaultHttpProvider() {
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		CloseableHttpClient httpProvider = HttpClients.custom().setConnectionManager(connManager)
				.build();
		return httpProvider;
	}

	public String getNodeVersion(){
		return nodeVersion;
	}
}