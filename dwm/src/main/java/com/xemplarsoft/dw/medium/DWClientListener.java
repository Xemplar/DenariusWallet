package com.xemplarsoft.dw.medium;

import java.net.Socket;

public interface DWClientListener {
    public void dataReceived(long UID, String data);
}
