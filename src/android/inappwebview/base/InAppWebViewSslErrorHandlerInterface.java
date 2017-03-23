package org.apache.cordova.inappbrowser.inappwebview.base;

import android.net.http.SslError;
import android.webkit.ValueCallback;

/**
 * Created by tarek on 3/23/17.
 */

public interface InAppWebViewSslErrorHandlerInterface {
    void handle(SslError sslError, ValueCallback<Boolean> callback);
}
