package org.apache.cordova.inappbrowser.inappwebview.system;

import android.webkit.WebSettings;

import org.apache.cordova.inappbrowser.inappwebview.base.InAppWebViewSettingsInterface;

/**
 * Created by tarek on 3/20/17.
 */

public class SystemInAppWebViewSettings implements InAppWebViewSettingsInterface{
    WebSettings webSettings;

    public SystemInAppWebViewSettings(WebSettings webSettings) {
        this.webSettings = webSettings;
    }

    @Override
    public void setLoadWithOverviewMode(boolean value) {
        this.webSettings.setLoadWithOverviewMode(value);
    }

    @Override
    public void setUseWideViewPort(boolean value) {
        this.webSettings.setUseWideViewPort(value);
    }

    @Override
    public void setAppCacheEnabled(boolean value) {
        this.webSettings.setAppCacheEnabled(value);
    }

    @Override
    public void setSaveFormData(boolean value) {
        this.webSettings.setSaveFormData(value);
    }

    @Override
    public void setSavePassword(boolean value) {
        this.webSettings.setSavePassword(value);
    }

    @Override
    public void setDomStorageEnabled(boolean value) {
        this.webSettings.setDomStorageEnabled(value);
    }

    @Override
    public void setDatabaseEnabled(boolean value) {
        this.webSettings.setDatabaseEnabled(value);
    }

    @Override
    public void setBuiltInZoomControls(boolean value) {
        this.webSettings.setBuiltInZoomControls(value);
    }

    @Override
    public void setUserAgentString(String userAgent) {
        this.webSettings.setUserAgentString(userAgent);
    }

    @Override
    public String getUserAgentString() {
        return this.webSettings.getUserAgentString();
    }

    @Override
    public void setCacheMode(int cacheMode) {
        switch (cacheMode) {
            case LOAD_DEFAULT:
                this.webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
                break;
            case LOAD_CACHE_ONLY:
                this.webSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
                break;
            case LOAD_CACHE_ELSE_NETWORK:
                this.webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                break;
            case LOAD_NO_CACHE:
                this.webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
                break;
        }
    }

    @Override
    public void setDatabasePath(String databasePath) {
        this.webSettings.setDatabasePath(databasePath);
    }

    @Override
    public void setJavaScriptEnabled(boolean value) {
        this.webSettings.setJavaScriptEnabled(value);
    }

    @Override
    public void setJavaScriptCanOpenWindowsAutomatically(boolean value) {
        this.webSettings.setJavaScriptCanOpenWindowsAutomatically(value);
    }

    @Override
    public void setMediaPlaybackRequiresUserGesture(boolean value) {
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            this.webSettings.setMediaPlaybackRequiresUserGesture(value);
        }
    }
}
