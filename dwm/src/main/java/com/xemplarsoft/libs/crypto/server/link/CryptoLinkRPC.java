package com.xemplarsoft.libs.crypto.server.link;

import com.xemplarsoft.libs.crypto.server.CommunicationException;
import com.xemplarsoft.libs.crypto.server.domain.*;
import com.xemplarsoft.libs.crypto.server.CryptocoinException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public interface CryptoLinkRPC {
	
	String addMultiSigAddress(Integer minSignatures, List<String> addresses) 
			throws CryptocoinException, CommunicationException;

	String addMultiSigAddress(Integer minSignatures, List<String> addresses, String account) 
			throws CryptocoinException, CommunicationException;

	void addNode(String node, String command) throws CryptocoinException, CommunicationException;

	void backupWallet(String filePath) throws CryptocoinException, CommunicationException;

	MultiSigAddress createMultiSig(Integer minSignatures, List<String> addresses)
			throws CryptocoinException, CommunicationException;

	String createRawTransaction(List<OutputOverview> outputs, Map<String, BigDecimal> toAddresses)
			throws CryptocoinException, CommunicationException;

	RawTransactionOverview decodeRawTransaction(String hexTransaction) throws CryptocoinException,
			CommunicationException;

	RedeemScript decodeScript(String hexRedeemScript) throws CryptocoinException,
			CommunicationException;

	String dumpPrivKey(String address) throws CryptocoinException, CommunicationException;

	void dumpWallet(String filePath) throws CryptocoinException, CommunicationException;

	String encryptWallet(String passphrase) throws CryptocoinException, CommunicationException;

	BigDecimal estimateFee(Integer maxBlocks) throws CryptocoinException, CommunicationException;

	BigDecimal estimatePriority(Integer maxBlocks) throws CryptocoinException, CommunicationException;

	String getAccount(String address) throws CryptocoinException, CommunicationException;

	String getAccountAddress(String account) throws CryptocoinException, CommunicationException;

	List<AddedNode> getAddedNodeInfo(Boolean withDetails) throws CryptocoinException,
			CommunicationException;

	List<AddedNode> getAddedNodeInfo(Boolean withDetails, String node) throws CryptocoinException, 
			CommunicationException;

	List<String> getAddressesByAccount(String account) throws CryptocoinException, 
			CommunicationException;

	BigDecimal getBalance() throws CryptocoinException, CommunicationException;

	BigDecimal getBalance(String account) throws CryptocoinException, CommunicationException;

	BigDecimal getBalance(String account, Integer confirmations) throws CryptocoinException, 
			CommunicationException;

	BigDecimal getBalance(String account, Integer confirmations, Boolean withWatchOnly) 
			throws CryptocoinException, CommunicationException;

	String getBestBlockHash() throws CryptocoinException, CommunicationException;

	Block getBlock(String headerHash) throws CryptocoinException, CommunicationException;

	Object getBlock(String headerHash, Boolean isDecoded) throws CryptocoinException, 
			CommunicationException;

	BlockChainInfo getBlockChainInfo() throws CryptocoinException, CommunicationException;

	Integer getBlockCount() throws CryptocoinException, CommunicationException;

	String getBlockHash(Integer blockHeight) throws CryptocoinException, CommunicationException;

	List<Tip> getChainTips() throws CryptocoinException, CommunicationException;

	Integer getConnectionCount() throws CryptocoinException, CommunicationException;

	Difficulty getDifficulty() throws CryptocoinException, CommunicationException;

	Boolean getGenerate() throws CryptocoinException, CommunicationException;

	Long getHashesPerSec() throws CryptocoinException, CommunicationException;

	Info getInfo() throws CryptocoinException, CommunicationException;

	MemPoolInfo getMemPoolInfo() throws CryptocoinException, CommunicationException;

	MiningInfo getMiningInfo() throws CryptocoinException, CommunicationException;

	NetworkTotals getNetTotals() throws CryptocoinException, CommunicationException;

	BigInteger getNetworkHashPs() throws CryptocoinException, CommunicationException;

	BigInteger getNetworkHashPs(Integer blocks) throws CryptocoinException, CommunicationException;

	BigInteger getNetworkHashPs(Integer blocks, Integer blockHeight) throws CryptocoinException, 
			CommunicationException;

	NetworkInfo getNetworkInfo() throws CryptocoinException, CommunicationException;

	String getNewAddress() throws CryptocoinException, CommunicationException;

	String getNewAddress(String account) throws CryptocoinException, CommunicationException;

	List<PeerNode> getPeerInfo() throws CryptocoinException, CommunicationException;

	String getRawChangeAddress() throws CryptocoinException, CommunicationException;

	List<String> getRawMemPool() throws CryptocoinException, CommunicationException;

	List<? extends Object> getRawMemPool(Boolean isDetailed) throws CryptocoinException, 
			CommunicationException;

	String getRawTransaction(String txId) throws CryptocoinException, CommunicationException;

	Object getRawTransaction(String txId, Integer verbosity) throws CryptocoinException, 
			CommunicationException;

	BigDecimal getReceivedByAccount(String account) throws CryptocoinException, 
			CommunicationException;

	BigDecimal getReceivedByAccount(String account, Integer confirmations) throws CryptocoinException, 
			CommunicationException;

	BigDecimal getReceivedByAddress(String address) throws CryptocoinException, CommunicationException;

	BigDecimal getReceivedByAddress(String address, Integer confirmations) throws CryptocoinException,
			CommunicationException;

	Transaction getTransaction(String txId) throws CryptocoinException, CommunicationException;

	Transaction getTransaction(String txId, Boolean withWatchOnly) throws CryptocoinException, 
			CommunicationException;

	TxOutSetInfo getTxOutSetInfo() throws CryptocoinException, CommunicationException;

	BigDecimal getUnconfirmedBalance() throws CryptocoinException, CommunicationException;

	WalletInfo getWalletInfo() throws CryptocoinException, CommunicationException;

	String help() throws CryptocoinException, CommunicationException;

	String help(String command) throws CryptocoinException, CommunicationException;

	void importAddress(String address) throws CryptocoinException, CommunicationException;

	void importAddress(String address, String account) throws CryptocoinException, 
			CommunicationException;

	void importAddress(String address, String account, Boolean withRescan) throws CryptocoinException,
			CommunicationException;

	void importPrivKey(String privateKey) throws CryptocoinException, CommunicationException;

	void importPrivKey(String privateKey, String account) throws CryptocoinException, 
			CommunicationException;

	void importPrivKey(String privateKey, String account, Boolean withRescan) 
			throws CryptocoinException, CommunicationException;

	void importWallet(String filePath) throws CryptocoinException, CommunicationException;

	void keyPoolRefill() throws CryptocoinException, CommunicationException;

	void keyPoolRefill(Integer keypoolSize) throws CryptocoinException, CommunicationException;

	Map<String, BigDecimal> listAccounts() throws CryptocoinException, CommunicationException;

	Map<String, BigDecimal> listAccounts(Integer confirmations) throws CryptocoinException, 
			CommunicationException;

	Map<String, BigDecimal> listAccounts(Integer confirmations, Boolean withWatchOnly) 
			throws CryptocoinException, CommunicationException;

	List<List<AddressOverview>> listAddressGroupings() throws CryptocoinException, 
			CommunicationException;

	List<OutputOverview> listLockUnspent() throws CryptocoinException, CommunicationException;

	List<Account> listReceivedByAccount() throws CryptocoinException, CommunicationException;

	List<Account> listReceivedByAccount(Integer confirmations) throws CryptocoinException, 
			CommunicationException;

	List<Account> listReceivedByAccount(Integer confirmations, Boolean withUnused) 
			throws CryptocoinException, CommunicationException;

	List<Account> listReceivedByAccount(Integer confirmations, Boolean withUnused,
                                        Boolean withWatchOnly) throws CryptocoinException, CommunicationException;

	List<Address> listReceivedByAddress() throws CryptocoinException, CommunicationException;

	List<Address> listReceivedByAddress(Integer confirmations) throws CryptocoinException, 
			CommunicationException;

	List<Address> listReceivedByAddress(Integer confirmations, Boolean withUnused) 
			throws CryptocoinException, CommunicationException;

	List<Address> listReceivedByAddress(Integer confirmations, Boolean withUnused,
                                        Boolean withWatchOnly) throws CryptocoinException, CommunicationException;

	SinceBlock listSinceBlock() throws CryptocoinException, CommunicationException;

	SinceBlock listSinceBlock(String headerHash) throws CryptocoinException, CommunicationException;

	SinceBlock listSinceBlock(String headerHash, Integer confirmations) throws CryptocoinException, 
			CommunicationException;

	SinceBlock listSinceBlock(String headerHash, Integer confirmations, Boolean withWatchOnly) 
			throws CryptocoinException, CommunicationException;

	List<Payment> listTransactions() throws CryptocoinException, CommunicationException;

	List<Payment> listTransactions(String account) throws CryptocoinException, CommunicationException;

	List<Payment> listTransactions(String account, Integer count) throws CryptocoinException, 
			CommunicationException;

	List<Payment> listTransactions(String account, Integer count, Integer offset) 
			throws CryptocoinException, CommunicationException;

	List<Payment> listTransactions(String account, Integer count, Integer offset,
                                   Boolean withWatchOnly) throws CryptocoinException, CommunicationException;

	List<Output> listUnspent() throws CryptocoinException, CommunicationException;

	List<Output> listUnspent(Integer minConfirmations) throws CryptocoinException, 
			CommunicationException;

	List<Output> listUnspent(Integer minConfirmations, Integer maxConfirmations) 
			throws CryptocoinException, CommunicationException;

	List<Output> listUnspent(Integer minConfirmations, Integer maxConfirmations,
                             List<String> addresses) throws CryptocoinException, CommunicationException;

	Boolean lockUnspent(Boolean isUnlocked) throws CryptocoinException, CommunicationException;

	Boolean lockUnspent(Boolean isUnlocked, List<OutputOverview> outputs) throws CryptocoinException,
			CommunicationException;

	Boolean move(String fromAccount, String toAccount, BigDecimal amount) throws CryptocoinException, 
			CommunicationException;

	Boolean move(String fromAccount, String toAccount, BigDecimal amount, Integer dummy,
                 String comment) throws CryptocoinException, CommunicationException;

	void ping() throws CryptocoinException, CommunicationException;

	Boolean prioritiseTransaction(String txId, BigDecimal deltaPriority, Long deltaFee) 
			throws CryptocoinException, CommunicationException;

	String sendFrom(String fromAccount, String toAddress, BigDecimal amount) 
			throws CryptocoinException, CommunicationException;

	String sendFrom(String fromAccount, String toAddress, BigDecimal amount, Integer confirmations) 
			throws CryptocoinException, CommunicationException;

	String sendFrom(String fromAccount, String toAddress, BigDecimal amount, Integer confirmations,
                    String comment) throws CryptocoinException, CommunicationException;

	String sendFrom(String fromAccount, String toAddress, BigDecimal amount, Integer confirmations,
                    String comment, String commentTo) throws CryptocoinException, CommunicationException;

	String sendMany(String fromAccount, Map<String, BigDecimal> toAddresses) 
			throws CryptocoinException, CommunicationException;

	String sendMany(String fromAccount, Map<String, BigDecimal> toAddresses, Integer confirmations) 
			throws CryptocoinException, CommunicationException;

	String sendMany(String fromAccount, Map<String, BigDecimal> toAddresses, Integer confirmations,
                    String comment) throws CryptocoinException, CommunicationException;

	String sendRawTransaction(String hexTransaction) throws CryptocoinException, 
			CommunicationException;

	String sendRawTransaction(String hexTransaction, Boolean withHighFees) throws CryptocoinException, 
			CommunicationException;

	String sendToAddress(String toAddress, BigDecimal amount) throws CryptocoinException, 
			CommunicationException;

	String sendToAddress(String toAddress, BigDecimal amount, String comment) 
			throws CryptocoinException, CommunicationException;

	String sendToAddress(String toAddress, BigDecimal amount, String comment, String commentTo) 
			throws CryptocoinException, CommunicationException;

	void setAccount(String address, String account) throws CryptocoinException, 
			CommunicationException;

	void setGenerate(Boolean isGenerate) throws CryptocoinException, CommunicationException;

	void setGenerate(Boolean isGenerate, Integer processors) throws CryptocoinException, 
			CommunicationException;

	Boolean setTxFee(BigDecimal txFee) throws CryptocoinException, CommunicationException;

	String signMessage(String address, String message) throws CryptocoinException, 
			CommunicationException;

	SignatureResult signRawTransaction(String hexTransaction) throws CryptocoinException, 
			CommunicationException;

	SignatureResult signRawTransaction(String hexTransaction, List<Output> outputs) 
			throws CryptocoinException, CommunicationException;

	SignatureResult signRawTransaction(String hexTransaction, List<Output> outputs,
                                       List<String> privateKeys) throws CryptocoinException, CommunicationException;

	SignatureResult signRawTransaction(String hexTransaction, List<Output> outputs,
                                       List<String> privateKeys, String sigHashType) throws CryptocoinException,
			CommunicationException;

	String stop() throws CryptocoinException, CommunicationException;

	String submitBlock(String block) throws CryptocoinException, CommunicationException;

	String submitBlock(String block, Map<String, Object> extraParameters) throws CryptocoinException, 
			CommunicationException;

	AddressInfo validateAddress(String address) throws CryptocoinException, CommunicationException;

	Boolean verifyChain() throws CryptocoinException, CommunicationException;

	Boolean verifyChain(Integer checkLevel) throws CryptocoinException, CommunicationException;

	Boolean verifyChain(Integer checkLevel, Integer blocks) throws CryptocoinException, 
			CommunicationException;

	Boolean verifyMessage(String address, String signature, String message) 
			throws CryptocoinException, CommunicationException;

	void walletLock() throws CryptocoinException, CommunicationException;

	void walletPassphrase(String passphrase, Integer authTimeout) throws CryptocoinException, 
			CommunicationException;

	void walletPassphraseChange(String curPassphrase, String newPassphrase) 
			throws CryptocoinException, CommunicationException;

	Properties getNodeConfig();

	String getNodeVersion();

	void close();
}