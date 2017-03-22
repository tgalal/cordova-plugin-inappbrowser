package org.apache.cordova.inappbrowser.inappwebview.base;

/**
 * Created by tarek on 3/20/17.
 */

public interface InAppWebViewNavigationInterface {
    void goBack(int steps);
    void goForward(int steps);
    boolean canGoBack();
    boolean canGoForward();
    void clearHistory();

}
