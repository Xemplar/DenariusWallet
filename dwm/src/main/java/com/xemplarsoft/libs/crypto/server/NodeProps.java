package com.xemplarsoft.libs.crypto.server;

import java.util.Properties;

/**
 * Created by Rohan on 8/15/2017.
 */
public enum NodeProps {
    RPC_PROTOCOL("node.rpc.protocol", "http"),
    RPC_HOST("node.rpc.host", "127.0.0.1"),
    RPC_PORT("node.rpc.port", "8332"),
    RPC_USER("node.rpc.user", "user"),
    RPC_CONFIRM("node.rpc.confirms", "3"),
    RPC_PASSWORD("node.rpc.password", "password"),
    HTTP_AUTH_SCHEME("node.http.auth_scheme", "Basic"),
    ALERT_PORT("node.notification.alert.port", "5158"),
    BLOCK_PORT("node.notification.block.port", "5159"),
    WALLET_PORT("node.notification.wallet.port", "5160"),

    LIVE_HEIGHT("node.live.height", "y"),
    LIVE_LAST_TIME("node.live.lasttime", "y"),
    LIVE_AVG_TIME("node.live.avgtime", "1000"),

    DB_ENCKEY("db.web.enckey", "ENCRYPT_KEY_NOT_NEEDED"),
    DB_USER("db.web.user", "db_user"),
    DB_PASS("db.web.pass", "SecurePass123"),
    DB_URL("db.web.url", "http://localhost/crypto.php"),
    WALLET_ADDRESS("node.account.address", "D9btaj1e87UyDsyjqe4bs6Jr5EMWqrFHhb");

    NodeProps(String key, String val){
        this.defaultValue = val;
        this.key = key;
    }

    public String getKey(){
        return key;
    }
    public String getDefaultValue(){
        return defaultValue;
    }
    private final String key;
    private final String defaultValue;

    public String getValue(Properties props){
        return props.getProperty(this.getKey(), this.getDefaultValue());
    }
}
