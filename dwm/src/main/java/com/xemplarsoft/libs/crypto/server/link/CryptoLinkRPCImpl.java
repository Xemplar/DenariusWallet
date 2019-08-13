package com.xemplarsoft.libs.crypto.server.link;

import com.xemplarsoft.libs.crypto.server.CommunicationException;
import com.xemplarsoft.libs.crypto.server.jsonrpc.client.JsonRpcClient;
import com.xemplarsoft.libs.crypto.server.jsonrpc.client.JsonRpcClientImpl;
import com.xemplarsoft.libs.crypto.server.util.CollectionUtils;
import com.xemplarsoft.libs.crypto.server.util.NumberUtils;
import com.xemplarsoft.libs.crypto.server.Commands;
import com.xemplarsoft.libs.crypto.server.CryptocoinException;
import com.xemplarsoft.libs.crypto.common.DataFormats;
import com.xemplarsoft.libs.crypto.common.Defaults;
import com.xemplarsoft.libs.crypto.server.domain.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CryptoLinkRPCImpl implements CryptoLinkRPC {

	private static final Logger LOG = LoggerFactory.getLogger(CryptoLinkRPCImpl.class);

	private ClientConfigurator configurator;
	private JsonRpcClient rpcClient;


	public CryptoLinkRPCImpl(Properties nodeConfig) throws CryptocoinException, CommunicationException {
		this(null, nodeConfig);
	}

	public CryptoLinkRPCImpl(CloseableHttpClient httpProvider, Properties nodeConfig)
			throws CryptocoinException, CommunicationException {
		initialize();
		rpcClient = new JsonRpcClientImpl(configurator.checkHttpProvider(httpProvider), configurator.checkNodeConfig(nodeConfig));
		configurator.checkNodeHealth((Block)getBlock(getBestBlockHash(), true));
	}

	public CryptoLinkRPCImpl(String rpcUser, String rpcPassword) throws CryptocoinException,
			CommunicationException {
		this(null, null, rpcUser, rpcPassword);
	}

	public CryptoLinkRPCImpl(CloseableHttpClient httpProvider, String rpcUser, String rpcPassword)
			throws CryptocoinException, CommunicationException {
		this(httpProvider, null, null, rpcUser, rpcPassword);
	}

	public CryptoLinkRPCImpl(String rpcHost, Integer rpcPort, String rpcUser, String rpcPassword)
			throws CryptocoinException, CommunicationException {
		this((String)null, rpcHost, rpcPort, rpcUser, rpcPassword);
	}

	public CryptoLinkRPCImpl(CloseableHttpClient httpProvider, String rpcHost, Integer rpcPort,
							 String rpcUser, String rpcPassword) throws CryptocoinException, CommunicationException {
		this(httpProvider, null, rpcHost, rpcPort, rpcUser, rpcPassword);
	}

	public CryptoLinkRPCImpl(String rpcProtocol, String rpcHost, Integer rpcPort, String rpcUser,
							 String rpcPassword) throws CryptocoinException, CommunicationException {
		this(rpcProtocol, rpcHost, rpcPort, rpcUser, rpcPassword, null);
	}

	public CryptoLinkRPCImpl(CloseableHttpClient httpProvider, String rpcProtocol, String rpcHost,
							 Integer rpcPort, String rpcUser, String rpcPassword) throws CryptocoinException,
			CommunicationException {
		this(httpProvider, rpcProtocol, rpcHost, rpcPort, rpcUser, rpcPassword, null);
	}

	public CryptoLinkRPCImpl(String rpcProtocol, String rpcHost, Integer rpcPort, String rpcUser,
							 String rpcPassword, String httpAuthScheme) throws CryptocoinException,
			CommunicationException {
		this(null, rpcProtocol, rpcHost, rpcPort, rpcUser, rpcPassword, httpAuthScheme);
	}

	public CryptoLinkRPCImpl(CloseableHttpClient httpProvider, String rpcProtocol, String rpcHost,
							 Integer rpcPort, String rpcUser, String rpcPassword, String httpAuthScheme)
			throws CryptocoinException, CommunicationException {
		this(httpProvider, new ClientConfigurator().toProperties(rpcProtocol, rpcHost, rpcPort, 
				rpcUser, rpcPassword, httpAuthScheme));
	}

	@Override
	public String addMultiSigAddress(Integer minSignatures, List<String> addresses) 
			throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(minSignatures, addresses);
		String multiSigAddressJson = rpcClient.execute(Commands.ADD_MULTI_SIG_ADDRESS.getName(), 
				params);
		String multiSigAddress = rpcClient.getParser().parseString(multiSigAddressJson);
		return multiSigAddress;		
	}

	@Override
	public String addMultiSigAddress(Integer minSignatures, List<String> addresses, 
			String account) throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(minSignatures, addresses, account);
		String multiSigAddressJson = rpcClient.execute(Commands.ADD_MULTI_SIG_ADDRESS.getName(),
				params);
		String multiSigAddress = rpcClient.getParser().parseString(multiSigAddressJson);
		return multiSigAddress;
	}

	@Override
	public void addNode(String node, String command) throws CryptocoinException, 
			CommunicationException {
		List<Object> params = CollectionUtils.asList(node, command);
		rpcClient.execute(Commands.ADD_NODE.getName(), params);
	}

	@Override
	public void backupWallet(String filePath) throws CryptocoinException, CommunicationException {
		rpcClient.execute(Commands.BACKUP_WALLET.getName(), filePath);
	}

	@Override
	public MultiSigAddress createMultiSig(Integer minSignatures, List<String> addresses) throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(minSignatures, addresses);
		String multiSigAddressJson = rpcClient.execute(Commands.CREATE_MULTI_SIG.getName(), params);
		MultiSigAddress multiSigAddress = rpcClient.getMapper().mapToEntity(multiSigAddressJson, MultiSigAddress.class);
		return multiSigAddress;
	}

	@Override
	public String createRawTransaction(List<OutputOverview> outputs,
			Map<String, BigDecimal> toAddresses) throws CryptocoinException, CommunicationException {
		toAddresses = NumberUtils.setValueScale(toAddresses, Defaults.DECIMAL_SCALE);
		List<Object> params = CollectionUtils.asList(outputs, toAddresses);
		String hexTransactionJson = rpcClient.execute(Commands.CREATE_RAW_TRANSACTION.getName(), 
				params);
		String hexTransaction = rpcClient.getParser().parseString(hexTransactionJson);
		return hexTransaction;
	}

	@Override
	public RawTransactionOverview decodeRawTransaction(String hexTransaction)
			throws CryptocoinException, CommunicationException {
		String rawTransactionJson = rpcClient.execute(Commands.DECODE_RAW_TRANSACTION.getName(), 
				hexTransaction);
		RawTransactionOverview rawTransaction = rpcClient.getMapper().mapToEntity(
				rawTransactionJson, RawTransactionOverview.class);
		return rawTransaction;
	}

	@Override
	public RedeemScript decodeScript(String hexRedeemScript) throws CryptocoinException,
			CommunicationException {
		String redeemScriptJson = rpcClient.execute(Commands.DECODE_SCRIPT.getName(), 
				hexRedeemScript);
		RedeemScript redeemScript = rpcClient.getMapper().mapToEntity(redeemScriptJson, 
				RedeemScript.class);
		redeemScript.setHex(hexRedeemScript);
		return redeemScript;
	}

	@Override
	public String dumpPrivKey(String address) throws CryptocoinException, CommunicationException {
		String privateKeyJson = rpcClient.execute(Commands.DUMP_PRIV_KEY.getName(), address);
		String privateKey = rpcClient.getParser().parseString(privateKeyJson);
		return privateKey;
	}

	@Override
	public void dumpWallet(String filePath) throws CryptocoinException, CommunicationException {
		rpcClient.execute(Commands.DUMP_WALLET.getName(), filePath);
	}

	@Override
	public String encryptWallet(String passphrase) throws CryptocoinException, 
			CommunicationException {
		String noticeMsgJson = rpcClient.execute(Commands.ENCRYPT_WALLET.getName(), passphrase);
		String noticeMsg = rpcClient.getParser().parseString(noticeMsgJson);
		return noticeMsg;
	}

	@Override
	public BigDecimal estimateFee(Integer maxBlocks) throws CryptocoinException, 
			CommunicationException {
		String estimatedFeeJson = rpcClient.execute(Commands.ESTIMATE_FEE.getName(), maxBlocks);
		BigDecimal estimatedFee = rpcClient.getParser().parseBigDecimal(estimatedFeeJson);
		return estimatedFee;
	}

	@Override
	public BigDecimal estimatePriority(Integer maxBlocks) throws CryptocoinException, 
			CommunicationException {
		String estimatedPriorityJson = rpcClient.execute(Commands.ESTIMATE_PRIORITY.getName(), 
				maxBlocks);
		BigDecimal estimatedPriority = rpcClient.getParser().parseBigDecimal(estimatedPriorityJson);
		return estimatedPriority;
	}

	@Override
	public String getAccount(String address) throws CryptocoinException, CommunicationException {
		String accountJson = rpcClient.execute(Commands.GET_ACCOUNT.getName(), address);
		String account = rpcClient.getParser().parseString(accountJson);
		return account;
	}

	@Override
	public String getAccountAddress(String account) throws CryptocoinException, 
			CommunicationException {
		String addressJson = rpcClient.execute(Commands.GET_ACCOUNT_ADDRESS.getName(), account);
		String address = rpcClient.getParser().parseString(addressJson);
		return address;
	}

	@Override
	public List<AddedNode> getAddedNodeInfo(Boolean withDetails) throws CryptocoinException,
			CommunicationException {
		String addedNodesJson = rpcClient.execute(Commands.GET_ADDED_NODE_INFO.getName(), 
				withDetails);
		List<AddedNode> addedNodes = rpcClient.getMapper().mapToList(addedNodesJson, 
				AddedNode.class);
		return addedNodes;
	}

	@Override
	public List<AddedNode> getAddedNodeInfo(Boolean withDetails, String node) 
			throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(withDetails, node);
		String addedNodesJson = rpcClient.execute(Commands.GET_ADDED_NODE_INFO.getName(), params);
		List<AddedNode> addedNodes = rpcClient.getMapper().mapToList(addedNodesJson, 
				AddedNode.class);
		return addedNodes;
	}

	@Override
	public List<String> getAddressesByAccount(String account) throws CryptocoinException, 
			CommunicationException {
		String addressesJson = rpcClient.execute(Commands.GET_ADDRESSES_BY_ACCOUNT.getName(), 
				account);
		List<String> addresses = rpcClient.getMapper().mapToList(addressesJson, String.class);
		return addresses;
	}

	@Override
	public BigDecimal getBalance() throws CryptocoinException, CommunicationException {
		String balanceJson = rpcClient.execute(Commands.GET_BALANCE.getName());
		BigDecimal balance = rpcClient.getParser().parseBigDecimal(balanceJson);
		return balance;
	}

	@Override
	public BigDecimal getBalance(String account) throws CryptocoinException, CommunicationException {
		String balanceJson = rpcClient.execute(Commands.GET_BALANCE.getName(), account);
		BigDecimal balance = rpcClient.getParser().parseBigDecimal(balanceJson);
		return balance;
	}

	@Override
	public BigDecimal getBalance(String account, Integer confirmations) throws CryptocoinException, 
			CommunicationException {
		List<Object> params = CollectionUtils.asList(account, confirmations);
		String balanceJson = rpcClient.execute(Commands.GET_BALANCE.getName(), params);
		BigDecimal balance = rpcClient.getParser().parseBigDecimal(balanceJson);
		return balance;
	}

	@Override
	public BigDecimal getBalance(String account, Integer confirmations, Boolean withWatchOnly)
			throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(account, confirmations, withWatchOnly);
		String balanceJson = rpcClient.execute(Commands.GET_BALANCE.getName(), params);
		BigDecimal balance = rpcClient.getParser().parseBigDecimal(balanceJson);
		return balance;
	}

	@Override
	public String getBestBlockHash() throws CryptocoinException, CommunicationException {
		String headerHashJson = rpcClient.execute(Commands.GET_BEST_BLOCK_HASH.getName());
		String headerHash = rpcClient.getParser().parseString(headerHashJson);
		return headerHash;
	}

	@Override
	public Block getBlock(String headerHash) throws CryptocoinException, CommunicationException {
		String blockJson = rpcClient.execute(Commands.GET_BLOCK.getName(), headerHash);
		Block block = rpcClient.getMapper().mapToEntity(blockJson, Block.class);
		return block;
	}

	@Override
	public Object getBlock(String headerHash, Boolean isDecoded) throws CryptocoinException, 
			CommunicationException {
		List<Object> params = CollectionUtils.asList(headerHash, isDecoded);
		String blockJson = rpcClient.execute(Commands.GET_BLOCK.getName(), params);
		if (isDecoded) {
			Block block = rpcClient.getMapper().mapToEntity(blockJson, Block.class);
			return block;
		} else {
			String block = rpcClient.getParser().parseString(blockJson);
			return block;
		}
	}

	@Override
	public BlockChainInfo getBlockChainInfo() throws CryptocoinException, CommunicationException {
		String blockChainInfoJson = rpcClient.execute(Commands.GET_BLOCK_CHAIN_INFO.getName());
		BlockChainInfo blockChainInfo = rpcClient.getMapper().mapToEntity(blockChainInfoJson, 
				BlockChainInfo.class);
		return blockChainInfo;		
	}

	@Override
	public Long getBlockCount() throws CryptocoinException, CommunicationException {
		String blockHeightJson = rpcClient.execute(Commands.GET_BLOCK_COUNT.getName());
		Long blockHeight = rpcClient.getParser().parseLong(blockHeightJson);
		return blockHeight;
	}

	@Override
	public String getBlockHash(Long blockHeight) throws CryptocoinException,
			CommunicationException {
		String headerHashJson = rpcClient.execute(Commands.GET_BLOCK_HASH.getName(), blockHeight);
		String headerHash = rpcClient.getParser().parseString(headerHashJson);
		return headerHash;
	}

	@Override
	public List<Tip> getChainTips() throws CryptocoinException, CommunicationException {
		String chainTipsJson = rpcClient.execute(Commands.GET_CHAIN_TIPS.getName());
		List<Tip> chainTips = rpcClient.getMapper().mapToList(chainTipsJson, Tip.class);
		return chainTips;
	}

	@Override
	public Integer getConnectionCount() throws CryptocoinException, CommunicationException {
		String connectionCountJson = rpcClient.execute(Commands.GET_CONNECTION_COUNT.getName());
		Integer connectionCount = rpcClient.getParser().parseInteger(connectionCountJson);
		return connectionCount;
	}

	@Override
	public Difficulty getDifficulty() throws CryptocoinException, CommunicationException {
		String difficultyJson = rpcClient.execute(Commands.GET_DIFFICULTY.getName());
		Difficulty difficulty = rpcClient.getMapper().mapToEntity(difficultyJson, Difficulty.class);
		return difficulty;
	}

	@Override
	public Boolean getGenerate() throws CryptocoinException, CommunicationException {
		String isGenerateJson = rpcClient.execute(Commands.GET_GENERATE.getName());
		Boolean isGenerate = rpcClient.getParser().parseBoolean(isGenerateJson);
		return isGenerate;
	}

	@Override
	public Long getHashesPerSec() throws CryptocoinException, CommunicationException {
		String hashesPerSecJson = rpcClient.execute(Commands.GET_HASHES_PER_SEC.getName());
		Long hashesPerSec = rpcClient.getParser().parseLong(hashesPerSecJson);
		return hashesPerSec;
	}

	@Override
	public Info getInfo() throws CryptocoinException, CommunicationException {
		String infoJson = rpcClient.execute(Commands.GET_INFO.getName());
		Info info = rpcClient.getMapper().mapToEntity(infoJson, Info.class);
		return info;
	}

	@Override
	public MemPoolInfo getMemPoolInfo() throws CryptocoinException, CommunicationException {
		String memPoolInfoJson = rpcClient.execute(Commands.GET_MEM_POOL_INFO.getName());
		MemPoolInfo memPoolInfo = rpcClient.getMapper().mapToEntity(memPoolInfoJson, 
				MemPoolInfo.class);
		return memPoolInfo;
	}

	@Override
	public MiningInfo getMiningInfo() throws CryptocoinException, CommunicationException {
		String miningInfoJson = rpcClient.execute(Commands.GET_MINING_INFO.getName());
		MiningInfo miningInfo = rpcClient.getMapper().mapToEntity(miningInfoJson, MiningInfo.class);
		return miningInfo;
	}

	@Override
	public NetworkTotals getNetTotals() throws CryptocoinException, CommunicationException {
		String netTotalsJson = rpcClient.execute(Commands.GET_NET_TOTALS.getName());
		NetworkTotals netTotals = rpcClient.getMapper().mapToEntity(netTotalsJson, 
				NetworkTotals.class);
		return netTotals;
	}

	@Override
	public BigInteger getNetworkHashPs() throws CryptocoinException, CommunicationException {
		String networkHashPsJson = rpcClient.execute(Commands.GET_NETWORK_HASH_PS.getName());
		BigInteger networkHashPs = rpcClient.getParser().parseBigInteger(networkHashPsJson);
		return networkHashPs;
	}

	@Override
	public BigInteger getNetworkHashPs(Integer blocks) throws CryptocoinException, 
			CommunicationException {
		String networkHashPsJson = rpcClient.execute(Commands.GET_NETWORK_HASH_PS.getName(), blocks);
		BigInteger networkHashPs = rpcClient.getParser().parseBigInteger(networkHashPsJson);
		return networkHashPs;
	}

	@Override
	public BigInteger getNetworkHashPs(Integer blocks, Integer blockHeight) throws CryptocoinException,
			CommunicationException {
		List<Object> params = CollectionUtils.asList(blocks, blockHeight);
		String networkHashPsJson = rpcClient.execute(Commands.GET_NETWORK_HASH_PS.getName(), params);
		BigInteger networkHashPs = rpcClient.getParser().parseBigInteger(networkHashPsJson);
		return networkHashPs;
	}

	@Override
	public NetworkInfo getNetworkInfo() throws CryptocoinException, CommunicationException {
		String networkInfoJson = rpcClient.execute(Commands.GET_NETWORK_INFO.getName());
		NetworkInfo networkInfo = rpcClient.getMapper().mapToEntity(networkInfoJson, 
				NetworkInfo.class);
		return networkInfo;
	}

	@Override
	public String getNewAddress() throws CryptocoinException, CommunicationException {
		String addressJson = rpcClient.execute(Commands.GET_NEW_ADDRESS.getName());
		String address = rpcClient.getParser().parseString(addressJson);
		return address;
	}

	@Override
	public String getNewAddress(String account) throws CryptocoinException, CommunicationException {
		String addressJson = rpcClient.execute(Commands.GET_NEW_ADDRESS.getName(), account);
		String address = rpcClient.getParser().parseString(addressJson);
		return address;
	}

	@Override
	public List<PeerNode> getPeerInfo() throws CryptocoinException, CommunicationException {
		String peerInfoJson = rpcClient.execute(Commands.GET_PEER_INFO.getName());
		List<PeerNode> peerInfo = rpcClient.getMapper().mapToList(peerInfoJson, PeerNode.class);
		return peerInfo;
	}

	@Override
	public String getRawChangeAddress() throws CryptocoinException, CommunicationException {
		String addressJson = rpcClient.execute(Commands.GET_RAW_CHANGE_ADDRESS.getName());
		String address = rpcClient.getParser().parseString(addressJson);
		return address;
	}

	@Override
	public List<String> getRawMemPool() throws CryptocoinException, CommunicationException {
		String memPoolTxnsJson = rpcClient.execute(Commands.GET_RAW_MEM_POOL.getName());
		List<String> memPoolTxns = rpcClient.getMapper().mapToList(memPoolTxnsJson, String.class);
		return memPoolTxns;
	}

	@Override
	public List<? extends Object> getRawMemPool(Boolean isDetailed) throws CryptocoinException, 
			CommunicationException {
		String memPoolTxnsJson = rpcClient.execute(Commands.GET_RAW_MEM_POOL.getName(), isDetailed);
		if (isDetailed) {
			Map<String, MemPoolTransaction> memPoolTxns = rpcClient.getMapper().mapToMap(
					memPoolTxnsJson, String.class, MemPoolTransaction.class);
			for (Map.Entry<String, MemPoolTransaction> memPoolTxn : memPoolTxns.entrySet()) {
				memPoolTxn.getValue().setTxId(memPoolTxn.getKey());
			}
			return new ArrayList<MemPoolTransaction>(memPoolTxns.values());
		} else {
			List<String> memPoolTxns = rpcClient.getMapper().mapToList(memPoolTxnsJson, 
					String.class);
			return memPoolTxns;
		}
	}

	@Override
	public String getRawTransaction(String txId) throws CryptocoinException, CommunicationException {
		String hexTransactionJson = rpcClient.execute(Commands.GET_RAW_TRANSACTION.getName(), txId);
		String hexTransaction = rpcClient.getParser().parseString(hexTransactionJson);
		return hexTransaction;
	}

	@Override
	public Object getRawTransaction(String txId, Integer verbosity) throws CryptocoinException, 
			CommunicationException {
		List<Object> params = CollectionUtils.asList(txId, verbosity);
		String transactionJson = rpcClient.execute(Commands.GET_RAW_TRANSACTION.getName(), params);
		if (verbosity == DataFormats.HEX.getCode()) {
			String hexTransaction = rpcClient.getParser().parseString(transactionJson);
			return hexTransaction;
		} else {
			RawTransaction rawTransaction = rpcClient.getMapper().mapToEntity(transactionJson, 
					RawTransaction.class);
			return rawTransaction;
		}
	}

	@Override
	public BigDecimal getReceivedByAccount(String account) throws CryptocoinException, 
			CommunicationException {
		String totalReceivedJson = rpcClient.execute(Commands.GET_RECEIVED_BY_ACCOUNT.getName(),
				account);
		BigDecimal totalReceived = rpcClient.getParser().parseBigDecimal(totalReceivedJson);
		return totalReceived;
	}

	@Override
	public BigDecimal getReceivedByAccount(String account, Integer confirmations) 
			throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(account, confirmations);
		String totalReceivedJson = rpcClient.execute(Commands.GET_RECEIVED_BY_ACCOUNT.getName(), 
				params);
		BigDecimal totalReceived = rpcClient.getParser().parseBigDecimal(totalReceivedJson);
		return totalReceived;
	}

	@Override
	public BigDecimal getReceivedByAddress(String address) throws CryptocoinException,
			CommunicationException {
		String totalReceivedJson = rpcClient.execute(Commands.GET_RECEIVED_BY_ADDRESS.getName(),
				address);
		BigDecimal totalReceived = rpcClient.getParser().parseBigDecimal(totalReceivedJson);
		return totalReceived;
	}

	@Override
	public BigDecimal getReceivedByAddress(String address, Integer confirmations) 
			throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(address, confirmations);
		String totalReceivedJson = rpcClient.execute(Commands.GET_RECEIVED_BY_ADDRESS.getName(),
				params);
		BigDecimal totalReceived = rpcClient.getParser().parseBigDecimal(totalReceivedJson);
		return totalReceived;
	}

	@Override
	public Transaction getTransaction(String txId) throws CryptocoinException, 
			CommunicationException {
		String transactionJson = rpcClient.execute(Commands.GET_TRANSACTION.getName(), txId);
		Transaction transaction = rpcClient.getMapper().mapToEntity(renameNarration(transactionJson),
				Transaction.class);
		return transaction;
	}

	@Override
	public Transaction getTransaction(String txId, Boolean withWatchOnly) throws CryptocoinException,
			CommunicationException {
		List<Object> params = CollectionUtils.asList(txId, withWatchOnly);
		String transactionJson = rpcClient.execute(Commands.GET_TRANSACTION.getName(), params);
		Transaction transaction = rpcClient.getMapper().mapToEntity(renameNarration(transactionJson),
				Transaction.class);
		return transaction;
	}

	@Override
	public TxOutSetInfo getTxOutSetInfo() throws CryptocoinException, CommunicationException {
		String txnOutSetInfoJson = rpcClient.execute(Commands.GET_TX_OUT_SET_INFO.getName());
		TxOutSetInfo txnOutSetInfo = rpcClient.getMapper().mapToEntity(txnOutSetInfoJson, 
				TxOutSetInfo.class);
		return txnOutSetInfo;
	}

	@Override
	public BigDecimal getUnconfirmedBalance() throws CryptocoinException, CommunicationException {
		String unconfirmedBalanceJson = rpcClient.execute(Commands.GET_UNCONFIRMED_BALANCE.getName());
		BigDecimal unconfirmedBalance = rpcClient.getParser().parseBigDecimal(unconfirmedBalanceJson);
		return unconfirmedBalance;
	}	

	@Override
	public WalletInfo getWalletInfo() throws CryptocoinException, CommunicationException {
		String walletInfoJson = rpcClient.execute(Commands.GET_WALLET_INFO.getName());
		WalletInfo walletInfo = rpcClient.getMapper().mapToEntity(walletInfoJson, WalletInfo.class);
		return walletInfo;
	}

	@Override
	public String help() throws CryptocoinException, CommunicationException {
		String helpJson = rpcClient.execute(Commands.HELP.getName());
		String help = rpcClient.getParser().parseString(helpJson);
		return help;
	}

	@Override
	public String help(String command) throws CryptocoinException, CommunicationException {
		String helpJson = rpcClient.execute(Commands.HELP.getName(), command);
		String help = rpcClient.getParser().parseString(helpJson);
		return help;
	}

	@Override
	public void importAddress(String address) throws CryptocoinException, CommunicationException {
		rpcClient.execute(Commands.IMPORT_ADDRESS.getName(), address);
	}

	@Override
	public void importAddress(String address, String account) throws CryptocoinException, 
			CommunicationException {
		List<Object> params = CollectionUtils.asList(address, account);
		rpcClient.execute(Commands.IMPORT_ADDRESS.getName(), params);
	}

	@Override
	public void importAddress(String address, String account, Boolean withRescan) 
			throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(address, account, withRescan);
		rpcClient.execute(Commands.IMPORT_ADDRESS.getName(), params);
	}

	@Override
	public void importPrivKey(String privateKey) throws CryptocoinException, CommunicationException {
		rpcClient.execute(Commands.IMPORT_PRIV_KEY.getName(), privateKey);
	}

	@Override
	public void importPrivKey(String privateKey, String account) throws CryptocoinException, 
			CommunicationException {
		List<Object> params = CollectionUtils.asList(privateKey, account);
		rpcClient.execute(Commands.IMPORT_PRIV_KEY.getName(), params);
	}

	@Override
	public void importPrivKey(String privateKey, String account, Boolean withRescan) 
			throws CryptocoinException, CommunicationException{
		List<Object> params = CollectionUtils.asList(privateKey, account, withRescan);
		rpcClient.execute(Commands.IMPORT_PRIV_KEY.getName(), params);
	}

	@Override
	public void importWallet(String filePath) throws CryptocoinException, CommunicationException {
		rpcClient.execute(Commands.IMPORT_WALLET.getName(), filePath);
	}

	@Override
	public void keyPoolRefill() throws CryptocoinException, CommunicationException {
		rpcClient.execute(Commands.KEY_POOL_REFILL.getName());
	}

	@Override
	public void keyPoolRefill(Integer keypoolSize) throws CryptocoinException, 
			CommunicationException {
		rpcClient.execute(Commands.KEY_POOL_REFILL.getName(), keypoolSize);
	}

	@Override
	public Map<String, BigDecimal> listAccounts() throws CryptocoinException, CommunicationException {
		String accountsJson = rpcClient.execute(Commands.LIST_ACCOUNTS.getName());
		Map<String, BigDecimal> accounts = rpcClient.getMapper().mapToMap(accountsJson, 
				String.class, BigDecimal.class);
		accounts = NumberUtils.setValueScale(accounts, Defaults.DECIMAL_SCALE);
		return accounts;
	}

	@Override
	public Map<String, BigDecimal> listAccounts(Integer confirmations) throws CryptocoinException, 
			CommunicationException {
		String accountsJson = rpcClient.execute(Commands.LIST_ACCOUNTS.getName(), confirmations);
		Map<String, BigDecimal> accounts = rpcClient.getMapper().mapToMap(accountsJson, 
				String.class, BigDecimal.class);
		accounts = NumberUtils.setValueScale(accounts, Defaults.DECIMAL_SCALE);
		return accounts;
	}

	@Override
	public Map<String, BigDecimal> listAccounts(Integer confirmations, Boolean withWatchOnly) 
			throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(confirmations, withWatchOnly);
		String accountsJson = rpcClient.execute(Commands.LIST_ACCOUNTS.getName(), params);
		Map<String, BigDecimal> accounts = rpcClient.getMapper().mapToMap(accountsJson, 
				String.class, BigDecimal.class);
		accounts = NumberUtils.setValueScale(accounts, Defaults.DECIMAL_SCALE);
		return accounts;
	}

	@Override
	public List<List<AddressOverview>> listAddressGroupings() throws CryptocoinException, 
			CommunicationException {
		String groupingsJson = rpcClient.execute(Commands.LIST_ADDRESS_GROUPINGS.getName());
		List<List<AddressOverview>> groupings = rpcClient.getMapper().mapToNestedLists(1, 
				groupingsJson, AddressOverview.class);
		return groupings;
	}

	@Override
	public List<OutputOverview> listLockUnspent() throws CryptocoinException, CommunicationException {
		String lockedOutputsJson = rpcClient.execute(Commands.LIST_LOCK_UNSPENT.getName());
		List<OutputOverview> lockedOutputs = rpcClient.getMapper().mapToList(lockedOutputsJson, 
				OutputOverview.class);
		return lockedOutputs;
	}

	@Override
	public List<Account> listReceivedByAccount() throws CryptocoinException, CommunicationException {
		String accountsJson = rpcClient.execute(Commands.LIST_RECEIVED_BY_ACCOUNT.getName());
		List<Account> accounts = rpcClient.getMapper().mapToList(accountsJson, Account.class);
		return accounts;
	}

	@Override
	public List<Account> listReceivedByAccount(Integer confirmations) throws CryptocoinException, 
			CommunicationException {
		String accountsJson = rpcClient.execute(Commands.LIST_RECEIVED_BY_ACCOUNT.getName(), 
				confirmations);
		List<Account> accounts = rpcClient.getMapper().mapToList(accountsJson, Account.class);
		return accounts;
	}

	@Override
	public List<Account> listReceivedByAccount(Integer confirmations, Boolean withUnused) 
			throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(confirmations, withUnused);
		String accountsJson = rpcClient.execute(Commands.LIST_RECEIVED_BY_ACCOUNT.getName(), 
				params);
		List<Account> accounts = rpcClient.getMapper().mapToList(accountsJson, Account.class);
		return accounts;
	}

	@Override
	public List<Account> listReceivedByAccount(Integer confirmations, Boolean withUnused, 
			Boolean withWatchOnly) throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(confirmations, withUnused, withWatchOnly);
		String accountsJson = rpcClient.execute(Commands.LIST_RECEIVED_BY_ACCOUNT.getName(), 
				params);
		List<Account> accounts = rpcClient.getMapper().mapToList(accountsJson, Account.class);
		return accounts;
	}

	@Override
	public List<Address> listReceivedByAddress() throws CryptocoinException, CommunicationException {
		String addressesJson = rpcClient.execute(Commands.LIST_RECEIVED_BY_ADDRESS.getName());
		List<Address> addresses = rpcClient.getMapper().mapToList(addressesJson, Address.class);
		return addresses;
	}

	@Override
	public List<Address> listReceivedByAddress(Integer confirmations) throws CryptocoinException, 
			CommunicationException {
		String addressesJson = rpcClient.execute(Commands.LIST_RECEIVED_BY_ADDRESS.getName(),
				confirmations);
		List<Address> addresses = rpcClient.getMapper().mapToList(addressesJson, Address.class);
		return addresses;
	}

	@Override
	public List<Address> listReceivedByAddress(Integer confirmations, Boolean withUnused) 
			throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(confirmations, withUnused);
		String addressesJson = rpcClient.execute(Commands.LIST_RECEIVED_BY_ADDRESS.getName(),
				params);
		List<Address> addresses = rpcClient.getMapper().mapToList(addressesJson, Address.class);
		return addresses;
	}

	@Override
	public List<Address> listReceivedByAddress(Integer confirmations, Boolean withUnused, 
			Boolean withWatchOnly) throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(confirmations, withUnused, withWatchOnly);
		String addressesJson = rpcClient.execute(Commands.LIST_RECEIVED_BY_ADDRESS.getName(),
				params);
		List<Address> addresses = rpcClient.getMapper().mapToList(addressesJson, Address.class);
		return addresses;
	}

	@Override
	public SinceBlock listSinceBlock() throws CryptocoinException, CommunicationException {
		String sinceBlockJson = rpcClient.execute(Commands.LIST_SINCE_BLOCK.getName());
		SinceBlock sinceBlock = rpcClient.getMapper().mapToEntity(sinceBlockJson, SinceBlock.class);
		return sinceBlock;
	}

	@Override
	public SinceBlock listSinceBlock(String headerHash) throws CryptocoinException, 
			CommunicationException {
		String sinceBlockJson = rpcClient.execute(Commands.LIST_SINCE_BLOCK.getName(), headerHash);
		SinceBlock sinceBlock = rpcClient.getMapper().mapToEntity(sinceBlockJson, SinceBlock.class);
		return sinceBlock;
	}

	@Override
	public SinceBlock listSinceBlock(String headerHash, Integer confirmations) 
			throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(headerHash, confirmations);
		String sinceBlockJson = rpcClient.execute(Commands.LIST_SINCE_BLOCK.getName(), params);
		SinceBlock sinceBlock = rpcClient.getMapper().mapToEntity(sinceBlockJson, SinceBlock.class);
		return sinceBlock;
	}

	@Override
	public SinceBlock listSinceBlock(String headerHash, Integer confirmations, 
			Boolean withWatchOnly) throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(headerHash, confirmations, withWatchOnly);
		String sinceBlockJson = rpcClient.execute(Commands.LIST_SINCE_BLOCK.getName(), params);
		SinceBlock sinceBlock = rpcClient.getMapper().mapToEntity(sinceBlockJson, SinceBlock.class);
		return sinceBlock;
	}

	@Override
	public List<Payment> listTransactions() throws CryptocoinException, CommunicationException {
		String paymentsJson = rpcClient.execute(Commands.LIST_TRANSACTIONS.getName());
		List<Payment> payments = rpcClient.getMapper().mapToList(paymentsJson, Payment.class);
		return payments;
	}

	@Override
	public List<Payment> listTransactions(String account) throws CryptocoinException, 
			CommunicationException {
		String paymentsJson = rpcClient.execute(Commands.LIST_TRANSACTIONS.getName(), account);
		List<Payment> payments = rpcClient.getMapper().mapToList(paymentsJson, Payment.class);
		return payments;
	}

	@Override
	public List<Payment> listTransactions(String account, Integer count) throws CryptocoinException, 
			CommunicationException {
		List<Object> params = CollectionUtils.asList(account, count);
		String paymentsJson = rpcClient.execute(Commands.LIST_TRANSACTIONS.getName(), params);
		List<Payment> payments = rpcClient.getMapper().mapToList(paymentsJson, Payment.class);
		return payments;
	}

	@Override
	public List<Payment> listTransactions(String account, Integer count, Integer offset)
			throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(account, count, offset);
		String paymentsJson = rpcClient.execute(Commands.LIST_TRANSACTIONS.getName(), params);
		List<Payment> payments = rpcClient.getMapper().mapToList(paymentsJson, Payment.class);
		return payments;
	}

	@Override
	public List<Payment> listTransactions(String account, Integer count, Integer offset, 
			Boolean withWatchOnly) throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(account, count, offset, withWatchOnly);
		String paymentsJson = rpcClient.execute(Commands.LIST_TRANSACTIONS.getName(), params);
		List<Payment> payments = rpcClient.getMapper().mapToList(paymentsJson, Payment.class);
		return payments;
	}

	@Override
	public List<Output> listUnspent() throws CryptocoinException, CommunicationException {
		String unspentOutputsJson = rpcClient.execute(Commands.LIST_UNSPENT.getName());
		List<Output> unspentOutputs = rpcClient.getMapper().mapToList(unspentOutputsJson,
				Output.class);
		return unspentOutputs;
	}

	@Override
	public List<Output> listUnspent(Integer minConfirmations) throws CryptocoinException, 
			CommunicationException {
		String unspentOutputsJson = rpcClient.execute(Commands.LIST_UNSPENT.getName(), 
				minConfirmations);
		List<Output> unspentOutputs = rpcClient.getMapper().mapToList(unspentOutputsJson,
				Output.class);
		return unspentOutputs;
	}

	@Override
	public List<Output> listUnspent(Integer minConfirmations, Integer maxConfirmations) 
			throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(minConfirmations, maxConfirmations);
		String unspentOutputsJson = rpcClient.execute(Commands.LIST_UNSPENT.getName(), params);
		List<Output> unspentOutputs = rpcClient.getMapper().mapToList(unspentOutputsJson,
				Output.class);
		return unspentOutputs;
	}

	@Override
	public List<Output> listUnspent(Integer minConfirmations, Integer maxConfirmations, 
			List<String> addresses) throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(minConfirmations, maxConfirmations, addresses);
		String unspentOutputsJson = rpcClient.execute(Commands.LIST_UNSPENT.getName(), params);
		List<Output> unspentOutputs = rpcClient.getMapper().mapToList(unspentOutputsJson,
				Output.class);
		return unspentOutputs;
	}

	@Override
	public Boolean lockUnspent(Boolean isUnlocked) throws CryptocoinException, 
			CommunicationException {
		String isSuccessJson = rpcClient.execute(Commands.LOCK_UNSPENT.getName(), isUnlocked);
		Boolean isSuccess = rpcClient.getParser().parseBoolean(isSuccessJson);
		return isSuccess;
	}

	@Override
	public Boolean lockUnspent(Boolean isUnlocked, List<OutputOverview> outputs) 
			throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(isUnlocked, outputs);
		String isSuccessJson = rpcClient.execute(Commands.LOCK_UNSPENT.getName(), params);
		Boolean isSuccess = rpcClient.getParser().parseBoolean(isSuccessJson);
		return isSuccess;
	}

	@Override
	public Boolean move(String fromAccount, String toAccount, BigDecimal amount) 
			throws CryptocoinException, CommunicationException {
		amount = amount.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
		List<Object> params = CollectionUtils.asList(fromAccount, toAccount, amount);
		String isSuccessJson = rpcClient.execute(Commands.MOVE.getName(), params);
		Boolean isSuccess = rpcClient.getParser().parseBoolean(isSuccessJson);
		return isSuccess;
	}

	@Override
	public Boolean move(String fromAccount, String toAccount, BigDecimal amount, Integer dummy,
			String comment) throws CryptocoinException, CommunicationException {
		amount = amount.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
		List<Object> params = CollectionUtils.asList(fromAccount, toAccount, amount, dummy, comment);
		String isSuccessJson = rpcClient.execute(Commands.MOVE.getName(), params);
		Boolean isSuccess = rpcClient.getParser().parseBoolean(isSuccessJson);
		return isSuccess;
	}

	@Override
	public void ping() throws CryptocoinException, CommunicationException {
		rpcClient.execute(Commands.PING.getName());
	}

	@Override
	public Boolean prioritiseTransaction(String txId, BigDecimal deltaPriority, Long deltaFee) 
			throws CryptocoinException, CommunicationException {
		deltaPriority = deltaPriority.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
		List<Object> params = CollectionUtils.asList(txId, deltaPriority, deltaFee);
		String isSuccessJson = rpcClient.execute(Commands.PRIORITISE_TRANSACTION.getName(), params);
		Boolean isSuccess = rpcClient.getParser().parseBoolean(isSuccessJson);
		return isSuccess;
	}

	@Override
	public String sendFrom(String fromAccount, String toAddress, BigDecimal amount) 
			throws CryptocoinException, CommunicationException {
		amount = amount.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
		List<Object> params = CollectionUtils.asList(fromAccount, toAddress, amount);
		String transactionIdJson = rpcClient.execute(Commands.SEND_FROM.getName(), params);
		String transactionId = rpcClient.getParser().parseString(transactionIdJson);
		return transactionId;
	}

	@Override
	public String sendFrom(String fromAccount, String toAddress, BigDecimal amount, 
			Integer confirmations) throws CryptocoinException, CommunicationException {
		amount = amount.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
		List<Object> params = CollectionUtils.asList(fromAccount, toAddress, amount, confirmations);
		String transactionIdJson = rpcClient.execute(Commands.SEND_FROM.getName(), params);
		String transactionId = rpcClient.getParser().parseString(transactionIdJson);
		return transactionId;
	}

	@Override
	public String sendFrom(String fromAccount, String toAddress, BigDecimal amount, 
			Integer confirmations, String comment) throws CryptocoinException, 
			CommunicationException {
		amount = amount.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
		List<Object> params = CollectionUtils.asList(fromAccount, toAddress, amount, confirmations, 
				comment);
		String transactionIdJson = rpcClient.execute(Commands.SEND_FROM.getName(), params);
		String transactionId = rpcClient.getParser().parseString(transactionIdJson);
		return transactionId;
	}

	@Override
	public String sendFrom(String fromAccount, String toAddress, BigDecimal amount, 
			Integer confirmations, String comment, String commentTo) throws CryptocoinException, 
			CommunicationException {
		amount = amount.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
		List<Object> params = CollectionUtils.asList(fromAccount, toAddress, amount, confirmations,
				comment, commentTo);
		String transactionIdJson = rpcClient.execute(Commands.SEND_FROM.getName(), params);
		String transactionId = rpcClient.getParser().parseString(transactionIdJson);
		return transactionId;
	}

	@Override
	public String sendMany(String fromAccount, Map<String, BigDecimal> toAddresses) 
			throws CryptocoinException, CommunicationException {
		toAddresses = NumberUtils.setValueScale(toAddresses, Defaults.DECIMAL_SCALE);
		List<Object> params = CollectionUtils.asList(fromAccount, toAddresses);
		String transactionIdJson = rpcClient.execute(Commands.SEND_MANY.getName(), params);
		String transactionId = rpcClient.getParser().parseString(transactionIdJson);
		return transactionId;
	}

	@Override
	public String sendMany(String fromAccount, Map<String, BigDecimal> toAddresses,	
			Integer confirmations) throws CryptocoinException, CommunicationException {
		toAddresses = NumberUtils.setValueScale(toAddresses, Defaults.DECIMAL_SCALE);
		List<Object> params = CollectionUtils.asList(fromAccount, toAddresses, confirmations);
		String transactionIdJson = rpcClient.execute(Commands.SEND_MANY.getName(), params);
		String transactionId = rpcClient.getParser().parseString(transactionIdJson);
		return transactionId;
	}

	@Override
	public String sendMany(String fromAccount, Map<String, BigDecimal> toAddresses,
			Integer confirmations, String comment) throws CryptocoinException, 
			CommunicationException {
		toAddresses = NumberUtils.setValueScale(toAddresses, Defaults.DECIMAL_SCALE);
		List<Object> params = CollectionUtils.asList(fromAccount, toAddresses, confirmations,
				comment);
		String transactionIdJson = rpcClient.execute(Commands.SEND_MANY.getName(), params);
		String transactionId = rpcClient.getParser().parseString(transactionIdJson);
		return transactionId;
	}

	@Override
	public String sendRawTransaction(String hexTransaction) throws CryptocoinException, 
			CommunicationException {
		String transactionIdJson = rpcClient.execute(Commands.SEND_RAW_TRANSACTION.getName(), 
				hexTransaction);
		String transactionId = rpcClient.getParser().parseString(transactionIdJson);
		return transactionId;
	}

	@Override
	public String sendRawTransaction(String hexTransaction, Boolean withHighFees) 
			throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(hexTransaction, withHighFees);
		String transactionIdJson = rpcClient.execute(Commands.SEND_RAW_TRANSACTION.getName(), 
				params);
		String transactionId = rpcClient.getParser().parseString(transactionIdJson);
		return transactionId;
	}

	@Override
	public String sendToAddress(String toAddress, BigDecimal amount) throws CryptocoinException, 
			CommunicationException {
		amount = amount.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
		List<Object> params = CollectionUtils.asList(toAddress, amount);
		String transactionIdJson = rpcClient.execute(Commands.SEND_TO_ADDRESS.getName(), params);
		String transactionId = rpcClient.getParser().parseString(transactionIdJson);
		return transactionId;
	}

	@Override
	public String sendToAddress(String toAddress, BigDecimal amount, String comment) 
			throws CryptocoinException, CommunicationException {
		amount = amount.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
		List<Object> params = CollectionUtils.asList(toAddress, amount, comment);
		String transactionIdJson = rpcClient.execute(Commands.SEND_TO_ADDRESS.getName(), params);
		String transactionId = rpcClient.getParser().parseString(transactionIdJson);
		return transactionId;
	}

	@Override
	public String sendToAddress(String toAddress, BigDecimal amount, String comment, 
			String commentTo) throws CryptocoinException, CommunicationException {
		amount = amount.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
		List<Object> params = CollectionUtils.asList(toAddress, amount, comment, commentTo);
		String transactionIdJson = rpcClient.execute(Commands.SEND_TO_ADDRESS.getName(), params);
		String transactionId = rpcClient.getParser().parseString(transactionIdJson);
		return transactionId;
	}

	@Override
	public void setAccount(String address, String account) throws CryptocoinException, 
			CommunicationException {
		List<Object> params = CollectionUtils.asList(address, account);
		rpcClient.execute(Commands.SET_ACCOUNT.getName(), params);
	}

	@Override
	public void setGenerate(Boolean isGenerate) throws CryptocoinException, CommunicationException {
		rpcClient.execute(Commands.SET_GENERATE.getName(), isGenerate);		
	}

	@Override
	public void setGenerate(Boolean isGenerate, Integer processors) throws CryptocoinException, 
			CommunicationException {
		List<Object> params = CollectionUtils.asList(isGenerate, processors);
		rpcClient.execute(Commands.SET_GENERATE.getName(), params);
	}

	@Override
	public Boolean setTxFee(BigDecimal txFee) throws CryptocoinException, CommunicationException {
		txFee = txFee.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
		String isSuccessJson = rpcClient.execute(Commands.SET_TX_FEE.getName(), txFee);
		Boolean isSuccess = rpcClient.getParser().parseBoolean(isSuccessJson);
		return isSuccess;
	}

	@Override
	public String signMessage(String address, String message) throws CryptocoinException, 
			CommunicationException {
		List<Object> params = CollectionUtils.asList(address, message);
		String signatureJson = rpcClient.execute(Commands.SIGN_MESSAGE.getName(), params);
		String signature = rpcClient.getParser().parseString(signatureJson);
		return signature;
	}

	@Override
	public SignatureResult signRawTransaction(String hexTransaction) throws CryptocoinException, 
			CommunicationException {
		String signatureResultJson = rpcClient.execute(Commands.SIGN_RAW_TRANSACTION.getName(), 
				hexTransaction);
		SignatureResult signatureResult = rpcClient.getMapper().mapToEntity(signatureResultJson, 
				SignatureResult.class);
		return signatureResult;
	}

	@Override
	public SignatureResult signRawTransaction(String hexTransaction, List<Output> outputs) 
			throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(hexTransaction, outputs);
		String signatureResultJson = rpcClient.execute(Commands.SIGN_RAW_TRANSACTION.getName(), 
				params);
		SignatureResult signatureResult = rpcClient.getMapper().mapToEntity(signatureResultJson, 
				SignatureResult.class);
		return signatureResult;
	}

	@Override
	public SignatureResult signRawTransaction(String hexTransaction, List<Output> outputs, 
			List<String> privateKeys) throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(hexTransaction, outputs, privateKeys);
		String signatureResultJson = rpcClient.execute(Commands.SIGN_RAW_TRANSACTION.getName(),
				params);
		SignatureResult signatureResult = rpcClient.getMapper().mapToEntity(signatureResultJson, 
				SignatureResult.class);
		return signatureResult;
	}

	@Override
	public SignatureResult signRawTransaction(String hexTransaction, List<Output> outputs, 
			List<String> privateKeys, String sigHashType) throws CryptocoinException, 
			CommunicationException {
		List<Object> params = CollectionUtils.asList(hexTransaction, outputs, privateKeys, 
				sigHashType);
		String signatureResultJson = rpcClient.execute(Commands.SIGN_RAW_TRANSACTION.getName(), 
				params);
		SignatureResult signatureResult = rpcClient.getMapper().mapToEntity(signatureResultJson,
				SignatureResult.class);
		return signatureResult;
	}

	@Override
	public String stop() throws CryptocoinException, CommunicationException {
		String noticeMsgJson = rpcClient.execute(Commands.STOP.getName());
		String noticeMsg = rpcClient.getParser().parseString(noticeMsgJson);
		return noticeMsg;
	}

	@Override
	public String submitBlock(String block) throws CryptocoinException, CommunicationException {
		String blockStatusJson = rpcClient.execute(Commands.SUBMIT_BLOCK.getName(), block);
		String blockStatus = rpcClient.getParser().parseString(blockStatusJson);
		return blockStatus;
	}

	@Override
	public String submitBlock(String block, Map<String, Object> extraParameters) 
			throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(block, extraParameters);
		String blockStatusJson = rpcClient.execute(Commands.SUBMIT_BLOCK.getName(), params);
		String blockStatus = rpcClient.getParser().parseString(blockStatusJson);
		return blockStatus;
	}

	@Override
	public AddressInfo validateAddress(String address) throws CryptocoinException, 
			CommunicationException {
		String addressInfoJson = rpcClient.execute(Commands.VALIDATE_ADDRESS.getName(), address);
		AddressInfo addressInfo = rpcClient.getMapper().mapToEntity(addressInfoJson, 
				AddressInfo.class);
		return addressInfo;
	}

	@Override
	public Boolean verifyChain() throws CryptocoinException, CommunicationException {
		String isChainValidJson = rpcClient.execute(Commands.VERIFY_CHAIN.getName());
		Boolean isChainValid = rpcClient.getParser().parseBoolean(isChainValidJson);
		return isChainValid;
	}

	@Override
	public Boolean verifyChain(Integer checkLevel) throws CryptocoinException, CommunicationException {
		String isChainValidJson = rpcClient.execute(Commands.VERIFY_CHAIN.getName(), checkLevel);
		Boolean isChainValid = rpcClient.getParser().parseBoolean(isChainValidJson);
		return isChainValid;
	}

	@Override
	public Boolean verifyChain(Integer checkLevel, Integer blocks) throws CryptocoinException,
			CommunicationException {
		List<Object> params = CollectionUtils.asList(checkLevel, blocks);
		String isChainValidJson = rpcClient.execute(Commands.VERIFY_CHAIN.getName(), params);
		Boolean isChainValid = rpcClient.getParser().parseBoolean(isChainValidJson);
		return isChainValid;
	}

	@Override
	public Boolean verifyMessage(String address, String signature, String message) 
			throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(address, signature, message);
		String isSigValidJson = rpcClient.execute(Commands.VERIFY_MESSAGE.getName(), params);
		Boolean isSigValid = rpcClient.getParser().parseBoolean(isSigValidJson);
		return isSigValid;
	}

	@Override
	public void walletLock() throws CryptocoinException, CommunicationException {
		rpcClient.execute(Commands.WALLET_LOCK.getName());
	}

	@Override
	public void walletPassphrase(String passphrase, Integer authTimeout) throws CryptocoinException, 
			CommunicationException {
		List<Object> params = CollectionUtils.asList(passphrase, authTimeout);
		rpcClient.execute(Commands.WALLET_PASSPHRASE.getName(), params);
	}

	@Override
	public void walletPassphraseChange(String curPassphrase, String newPassphrase) 
			throws CryptocoinException, CommunicationException {
		List<Object> params = CollectionUtils.asList(curPassphrase, newPassphrase);
		rpcClient.execute(Commands.WALLET_PASSPHRASE_CHANGE.getName(), params);
	}

	@Override
	public Properties getNodeConfig() {
		return configurator.getNodeConfig();
	}

	@Override
	public String getNodeVersion() {
		return configurator.getNodeVersion();
	}

	@Override
	public synchronized void close() {
		LOG.info(">> close(..): closing the 'bitcoind' core wrapper");
		rpcClient.close();
	}

	private void initialize() {
		LOG.info(">> initialize(..): initiating the 'bitcoind' core wrapper");
		configurator = new ClientConfigurator();
	}

	private String renameNarration(String JSON){
	    return JSON.replaceAll("n_[0-9]+", "n_x");
    }
}