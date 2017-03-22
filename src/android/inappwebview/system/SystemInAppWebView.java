package org.apache.cordova.inappbrowser.inappwebview.system;

import android.content.Context;
import android.os.Build;
import android.view.ViewGroup;

import android.webkit.WebView;

import org.apache.cordova.inappbrowser.inappwebview.base.InAppWebView;
import org.apache.cordova.inappbrowser.inappwebview.base.InAppWebViewSettingsInterface;

/**
 * Created by tarek on 3/20/17.
 */

public class SystemInAppWebView extends InAppWebView {
    WebView webView;
    public SystemInAppWebView(Context context) {
        super(context);
        webView = new WebView(context);
        webView.setWebViewClient(new SystemInAppWebViewClient(getInAppWebViewInterfaceProxy()));
        webView.setWebChromeClient(new SystemInAppWebViewChromeClient(getInAppWebViewInterfaceProxy()));

        init(webView, new SystemInAppWebViewNavigation(webView), new SystemInAppWebViewSettings(webView.getSettings()), new SystemInAppWebViewCookieManager());
    }

    @Override
    protected void setDefaultSettings(InAppWebViewSettingsInterface settingsInterface) {
        super.setDefaultSettings(settingsInterface);
        webView.getSettings().setPluginState(android.webkit.WebSettings.PluginState.ON);
    }

    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }

    @Override
    public void setParentForAutoCustomViewHandling(ViewGroup parentForAutoCustomViewHandling) {
        super.setParentForAutoCustomViewHandling(parentForAutoCustomViewHandling);
        if(autoHandleCustomViews())
            webView.setWebChromeClient(new FullscreenSupportSystemInAppWebViewChromeClient(getInAppWebViewInterfaceProxy()));
        else
            webView.setWebChromeClient(new SystemInAppWebViewChromeClient(getInAppWebViewInterfaceProxy()));
    }

    @Override
    public void loadUrl(String url) {
        webView.loadUrl(url);
    }

    @Override
    public void evaluateJavascript(String javascript) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            // This action will have the side-effect of blurring the currently focused element
            webView.loadUrl("javascript:" + javascript);
        } else {
            webView.evaluateJavascript(javascript, null);
        }
    }

    @Override
    public void clearFormData() {
        this.webView.clearFormData();
    }

    @Override
    public void clearCache() {
        this.webView.clearCache(true);
    }

}
