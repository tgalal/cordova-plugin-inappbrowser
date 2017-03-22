package org.apache.cordova.inappbrowser.inappwebview.base;

/**
 * Created by tarek on 3/22/17.
 */

public interface InAppWebViewJavascriptResultInterface {
    void cancel();
    void confirm();
    void confirm(String result);
}
