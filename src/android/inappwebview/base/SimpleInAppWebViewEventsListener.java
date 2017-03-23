package org.apache.cordova.inappbrowser.inappwebview.base;

import android.net.Uri;
import android.view.View;
import android.webkit.ValueCallback;

/**
 * Created by tarek on 3/21/17.
 */

public class SimpleInAppWebViewEventsListener implements InAppWebViewEventsListener {
    @Override
    public void onPageLoadFinished(String url) {

    }

    @Override
    public void onPageLoadStarted(String url) {

    }

    @Override
    public boolean onLoadUrl(String url) {
        return false;
    }

    @Override
    public void onReceivedLoadError(int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onReceivedHttpAuthRequest(InAppWebViewAuthHandlerInterface cordovaHttpAuthHandler, String host, String realm) {

    }

    @Override
    public void onShowCustomView(View view) {

    }

    @Override
    public void onHideCustomView() {

    }

    @Override
    public void onToggledFullScreen(boolean inFullscreen) {

    }

    @Override
    public boolean onShowFileChooser(ValueCallback<Uri[]> filePathCallback, String[] acceptTypes, boolean capture) {
        return false;
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin, InAppWebViewPermissionsCallbackInterface callback) {

    }

    @Override
    public boolean onJsPrompt(String url, String message, String defaultValue, InAppWebViewJavascriptResultInterface result) {
        return false;
    }

    @Override
    public void onProgressChanged(int progress) {

    }
}
