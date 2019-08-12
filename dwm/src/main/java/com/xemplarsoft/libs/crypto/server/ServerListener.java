package com.xemplarsoft.libs.crypto.server;

import java.math.BigDecimal;

/**
 * Created by Rohan on 9/2/2017.
 */
public interface ServerListener {
    public abstract void onRequestReceived(int id, String desc, BigDecimal amount);
    public abstract void onRequestGenerated(int id, String pin);
    public abstract void onRequestUpdate(int id, String status);
    public abstract void onRequestFilled(int id);
    public abstract void onRequestCanceled(int id);
}
