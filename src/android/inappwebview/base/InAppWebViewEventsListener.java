package org.apache.cordova.inappbrowser.inappwebview.base;

import android.net.Uri;
import android.view.View;
import android.webkit.ValueCallback;
/**
 * Created by tarek on 3/20/17.
 */

public interface InAppWebViewEventsListener {
    void onPageLoadFinished(String url);
    void onPageLoadStarted(String url);
    boolean onLoadUrl(String url);
    void onReceivedLoadError(int errorCode, String description, String failingUrl);
    void onReceivedHttpAuthRequest(InAppWebViewAuthHandlerInterface inAppWebViewAuthHandler, String host, String realm);
    void onShowCustomView(View view);
    void onHideCustomView();
    void onToggledFullScreen(boolean inFullscreen);
    boolean onShowFileChooser(ValueCallback<Uri[]> filePathCallback, String[] acceptTypes, boolean capture);
    void onGeolocationPermissionsShowPrompt(String origin, InAppWebViewPermissionsCallbackInterface callback);
    boolean onJsPrompt(String url, String message, String defaultValue, InAppWebViewJavascriptResultInterface result);
    void onProgressChanged(int progress);
}
