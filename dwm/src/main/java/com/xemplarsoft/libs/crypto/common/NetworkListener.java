package com.xemplarsoft.libs.crypto.common;

import com.xemplarsoft.libs.crypto.server.net.PaymentHandler;
import com.xemplarsoft.libs.crypto.server.net.domain.Request;

/**
 * Created by Rohan on 8/18/2017.
 */
public final class NetworkListener {
    public interface CryptoService {}
    public interface CryptoServerService extends CryptoService{}
    public interface ServerPaymentListener extends CryptoServerService {
        public abstract void paymentReceived(PaymentHandler handler, String txID);
        public abstract void paymentCanceled(PaymentHandler handler);
    }
    public interface ServerRequestListener extends CryptoServerService {
        public abstract void requestsFound(Request[] requests);
        public abstract void noRequests();
    }

    public interface CryptoClientService extends CryptoService{}
    public interface ClientPaymentListener extends CryptoClientService {
        public abstract void onConfirmUpdate(String key, int confirms);
        public abstract void onPinReceived(String key, String pin);
        public abstract void onPayReceived(String key, String txid);
        public abstract void onPayCanceled(String key);
        public abstract void onPayError(String key, Exception e);
    }
    public static class ClientPaymentAdapter implements ClientPaymentListener{
        public void onPayReceived(String key, String txid) { }
        public void onPayError(String key, Exception e) { }
        public void onConfirmUpdate(String key, int confirms) { }
        public void onPayCanceled(String key) { }
        public void onPinReceived(String key, String pin) { }
    }
    public interface ClientRequestListener extends CryptoClientService {
        public abstract void onRequestSent(int requestID);
    }
}
