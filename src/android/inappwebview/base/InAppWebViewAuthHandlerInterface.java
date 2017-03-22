package org.apache.cordova.inappbrowser.inappwebview.base;

import org.apache.cordova.ICordovaHttpAuthHandler;

/**
 * Created by tarek on 3/20/17.
 */

public interface InAppWebViewAuthHandlerInterface extends ICordovaHttpAuthHandler {
    void onUnhandled();
}
