package org.apache.cordova.inappbrowser.inappwebview.base;

/**
 * Created by tarek on 3/20/17.
 */

public interface InAppWebViewSettingsInterface {
    int LOAD_DEFAULT = -1;
    int LOAD_CACHE_ELSE_NETWORK = 1;
    int LOAD_NO_CACHE = 2;
    int LOAD_CACHE_ONLY = 3;

    void setLoadWithOverviewMode(boolean value);
    void setUseWideViewPort(boolean value);
    void setAppCacheEnabled(boolean value);
    void setSaveFormData(boolean value);
    void setSavePassword(boolean value);
    void setDomStorageEnabled(boolean value);
    void setDatabaseEnabled(boolean value);
    void setBuiltInZoomControls(boolean value);
    void setUserAgentString(String userAgent);
    String getUserAgentString();
    void setCacheMode(int cacheMode);
    void setDatabasePath(String databasePath);
    void setJavaScriptEnabled(boolean value);
    void setJavaScriptCanOpenWindowsAutomatically(boolean value);
    void setMediaPlaybackRequiresUserGesture(boolean value);
}
