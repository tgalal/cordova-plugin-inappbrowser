package org.apache.cordova.inappbrowser.inappwebview.base;

import android.net.Uri;
import android.net.http.SslError;
import android.view.View;
import android.webkit.ValueCallback;

/**
 * Created by tarek on 3/22/17.
 */

public class InAppWebViewInterfaceProxy implements InAppWebViewEventsListener {
    private InAppWebView inAppWebView;

    public InAppWebViewInterfaceProxy(InAppWebView inAppWebView) {
        this.inAppWebView = inAppWebView;
    }

    public void onSslError(SslError sslError, ValueCallback<Boolean> valueCallback) {
        this.inAppWebView.onSslError(sslError, valueCallback);
    }

    @Override
    public void onPageLoadFinished(String url) {
        this.inAppWebView.onPageLoadFinished(url);
    }

    @Override
    public void onPageLoadStarted(String url) {
        this.inAppWebView.onPageLoadStarted(url);
    }

    @Override
    public boolean onLoadUrl(String url) {
        return this.inAppWebView.onLoadUrl(url);
    }

    @Override
    public void onReceivedLoadError(int errorCode, String description, String failingUrl) {
        this.inAppWebView.onReceivedError(errorCode, description, failingUrl);
    }

    @Override
    public void onReceivedHttpAuthRequest(InAppWebViewAuthHandlerInterface inAppWebViewAuthHandler, String host, String realm) {
        this.inAppWebView.onReceivedHttpAuthRequest(inAppWebViewAuthHandler, host, realm);
    }

    @Override
    public void onShowCustomView(View view) {
        this.inAppWebView.onShowCustomView(view);
    }

    @Override
    public void onHideCustomView() {
        this.inAppWebView.onHideCustomView();
    }

    @Override
    public void onToggledFullScreen(boolean inFullscreen) {
        this.inAppWebView.onToggledFullScreen(inFullscreen);
    }

    @Override
    public boolean onShowFileChooser(ValueCallback<Uri[]> filePathCallback, String[] acceptTypes, boolean capture) {
        return this.inAppWebView.onShowFileChooser(filePathCallback, acceptTypes, capture);
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin, InAppWebViewPermissionsCallbackInterface callback) {
        this.inAppWebView.onGeolocationPermissionsShowPrompt(origin, callback);
    }

    @Override
    public boolean onJsPrompt(String url, String message, String defaultValue, InAppWebViewJavascriptResultInterface result) {
        return this.inAppWebView.onJsPrompt(url, message, defaultValue, result);
    }

    @Override
    public void onProgressChanged(int progress) {
        this.inAppWebView.onProgressChanged(progress);
    }
}
