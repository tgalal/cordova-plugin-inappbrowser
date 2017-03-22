package org.apache.cordova.inappbrowser.inappwebview.base;

import android.view.View;

/**
 * Created by tarek on 3/20/17.
 */

public interface InAppWebViewInterface {
    void loadUrl(String url);
    InAppWebViewNavigationInterface getNavigation();
    InAppWebViewSettingsInterface getSettings();
    InAppWebViewCookieManagerInterface getCookieManager();
    View getView();
    void evaluateJavascript(String javascript);
    void clearFormData();
    void clearCache();
}
