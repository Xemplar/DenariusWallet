package com.xemplarsoft.libs.crypto.server.jsonrpc.client;

import com.xemplarsoft.libs.crypto.server.CommunicationException;
import com.xemplarsoft.libs.crypto.server.jsonrpc.JsonMapper;
import com.xemplarsoft.libs.crypto.server.jsonrpc.JsonPrimitiveParser;
import com.xemplarsoft.libs.crypto.server.jsonrpc.domain.JsonRpcError;
import com.xemplarsoft.libs.crypto.server.CryptocoinException;
import com.xemplarsoft.libs.crypto.common.Defaults;
import com.xemplarsoft.libs.crypto.common.Errors;
import com.xemplarsoft.libs.crypto.server.http.HttpConstants;
import com.xemplarsoft.libs.crypto.server.http.client.SimpleHttpClient;
import com.xemplarsoft.libs.crypto.server.http.client.SimpleHttpClientImpl;
import com.xemplarsoft.libs.crypto.server.jsonrpc.JsonRpcLayerException;
import com.xemplarsoft.libs.crypto.server.jsonrpc.domain.JsonRpcRequest;
import com.xemplarsoft.libs.crypto.server.jsonrpc.domain.JsonRpcResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;


public class JsonRpcClientImpl implements JsonRpcClient {
	
	private static final Logger LOG = LoggerFactory.getLogger(JsonRpcClientImpl.class);
	
	private SimpleHttpClient httpClient;
	private JsonPrimitiveParser parser;
	private JsonMapper mapper;


	public JsonRpcClientImpl(CloseableHttpClient httpProvider, Properties nodeConfig) {
		LOG.info("** JsonRpcClientImpl(): initiating the JSON-RPC communication layer");
		httpClient = new SimpleHttpClientImpl(httpProvider, nodeConfig);
		parser = new JsonPrimitiveParser();
		mapper = new JsonMapper();
	}

	@Override
	public String execute(String method) throws CryptocoinException, CommunicationException {
		return execute(method, null);
	}

	@Override
	public <T> String execute(String method, T param) throws CryptocoinException, 
			CommunicationException {
		List<T> params = new ArrayList<T>();
		params.add(param);
		return execute(method, params);
	}

	@Override
	public <T> String execute(String method, List<T> params) throws CryptocoinException, CommunicationException {
		LOG.info(">> execute(..): invoking 'bitcoind' JSON-RPC API command '{}' with params: '{}'", method, params);
		String requestUuid = getNewUuid();
		JsonRpcRequest<T> request = getNewRequest(method, params, requestUuid);
		String requestJson = mapper.mapToJson(request);
		LOG.debug("-- execute(..): sending JSON-RPC request as (raw): '{}'", requestJson.trim());
		String responseJson = httpClient.execute(HttpConstants.REQ_METHOD_POST, requestJson);
		LOG.debug("-- execute(..): received JSON-RPC response as (raw): '{}'", responseJson.trim());
		JsonRpcResponse response = mapper.mapToEntity(responseJson, JsonRpcResponse.class);
		response = verifyResponse(request, response);
		response = checkResponse(response);
		LOG.info("<< execute(..): returning result for 'bitcoind' API command '{}' as: '{}'", method, response.getResult());
		return response.getResult();
	}

	@Override
	public JsonPrimitiveParser getParser() {
		return parser;
	}

	@Override
	public JsonMapper getMapper() {
		return mapper;
	}

	@Override
	public void close() {
		httpClient.close();
	}
	
	private <T> JsonRpcRequest<T> getNewRequest(String method, List<T> params, String id) {
		JsonRpcRequest<T> rpcRequest = new JsonRpcRequest<T>();
		rpcRequest.setJsonrpc(Defaults.JSON_RPC_VERSION);
		rpcRequest.setMethod(method);
		rpcRequest.setParams(params);
		rpcRequest.setID(id);
		return rpcRequest;
	}

	private JsonRpcResponse getNewResponse(String result, JsonRpcError error, String id) {
		JsonRpcResponse rpcResponse = new JsonRpcResponse();
		rpcResponse.setJsonrpc(Defaults.JSON_RPC_VERSION);
		rpcResponse.setResult(result);
		rpcResponse.setError(error);
		rpcResponse.setID(id);
		return rpcResponse;
	}

	private String getNewUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	private <T> JsonRpcResponse verifyResponse(JsonRpcRequest<T> request, JsonRpcResponse response) throws JsonRpcLayerException {
		LOG.debug(">> verifyResponse(..): verifying JSON-RPC response for basic protocol conformance");
		if (response == null) {
			throw new JsonRpcLayerException(Errors.RESPONSE_JSONRPC_NULL);
		}
		if (response.getId() == null) {
			throw new JsonRpcLayerException(Errors.RESPONSE_JSONRPC_NULL_ID);
		}
		if (!response.getId().equals(request.getId())) {
			throw new JsonRpcLayerException(Errors.RESPONSE_JSONRPC_UNEQUAL_IDS);
		}
		if ((response.getJsonrpc() != null) && (!response.getJsonrpc().equals(
				Defaults.JSON_RPC_VERSION))) {
			LOG.warn("-- verifyResponse(..): JSON-RPC version mismatch - client optimized for '{}'"
					+ ", node responded in '{}'", Defaults.JSON_RPC_VERSION, response.getJsonrpc());
		}
		return response;
	}

	private <T> JsonRpcResponse checkResponse(JsonRpcResponse response) throws CryptocoinException {
		LOG.debug(">> checkResponse(..): checking JSON-RPC response for nested 'bitcoind' errors");
		if (!(response.getError() == null)) {
			JsonRpcError cryptocoinError = response.getError();
			throw new CryptocoinException(cryptocoinError.getCode(), String.format("Error #%s: %s", 
					cryptocoinError.getCode(), cryptocoinError.getMessage()));
		}
		return response;
	}
}