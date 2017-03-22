package org.apache.cordova.inappbrowser.inappwebview.system;

import android.webkit.WebView;

import org.apache.cordova.inappbrowser.inappwebview.base.InAppWebViewNavigationInterface;

/**
 * Created by tarek on 3/20/17.
 */

public class SystemInAppWebViewNavigation implements InAppWebViewNavigationInterface {
    WebView webView;

    public SystemInAppWebViewNavigation(WebView webView) {
        this.webView = webView;
    }

    @Override
    public void goBack(int steps) {
        steps = steps > 0 ? -1 * steps: steps;
        this.webView.goBackOrForward(steps);
    }

    @Override
    public void goForward(int steps) {
        this.webView.goBackOrForward(Math.abs(steps));
    }

    @Override
    public boolean canGoBack() {
        return this.webView.canGoBack();
    }

    @Override
    public boolean canGoForward() {
        return this.webView.canGoForward();
    }

    @Override
    public void clearHistory() {
        this.webView.clearHistory();
    }
}
