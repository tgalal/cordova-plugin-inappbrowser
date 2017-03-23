package org.apache.cordova.inappbrowser.inappwebview.system;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage;
import android.webkit.WebView;

import org.apache.cordova.LOG;
import org.apache.cordova.inappbrowser.inappwebview.base.InAppWebViewInterfaceProxy;
import org.apache.cordova.inappbrowser.inappwebview.base.InAppWebViewPermissionsCallbackInterface;

/**
 * Created by tarek on 3/20/17.
 */

class SystemInAppWebViewChromeClient extends WebChromeClient {
    protected static final String LOG_TAG = "SystemInAppWebViewChromeClient";
    protected static final long MAX_QUOTA = 100 * 1024 * 1024;
    protected InAppWebViewInterfaceProxy inAppWebViewInterfaceProxy;

    public SystemInAppWebViewChromeClient(InAppWebViewInterfaceProxy inAppWebViewInterfaceProxy) {
        super();
        this.inAppWebViewInterfaceProxy = inAppWebViewInterfaceProxy;
    }

    /**
     * Handle database quota exceeded notification.
     *
     * @param url
     * @param databaseIdentifier
     * @param currentQuota
     * @param estimatedSize
     * @param totalUsedQuota
     * @param quotaUpdater
     */
    @Override
    public void onExceededDatabaseQuota(String url, String databaseIdentifier, long currentQuota, long estimatedSize,
                                        long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater)
    {
        LOG.d(LOG_TAG, "onExceededDatabaseQuota estimatedSize: %d  currentQuota: %d  totalUsedQuota: %d", estimatedSize, currentQuota, totalUsedQuota);
        quotaUpdater.updateQuota(MAX_QUOTA);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        return inAppWebViewInterfaceProxy.onJsPrompt(url, message, defaultValue, new SystemInAppWebViewJavascriptResult(result))
                ||  super.onJsPrompt(view, url, message, defaultValue, result);
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin, final GeolocationPermissions.Callback callback) {
        super.onGeolocationPermissionsShowPrompt(origin, callback);
        inAppWebViewInterfaceProxy.onGeolocationPermissionsShowPrompt(origin, new InAppWebViewPermissionsCallbackInterface() {
            @Override
            public void invoke(String origin, boolean allow, boolean retain) {
                callback.invoke(origin, allow, retain);
            }
        });
    }

    // For Android 5.0+
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public boolean onShowFileChooser (WebView webView, final ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams)
    {
        LOG.d(LOG_TAG, "File Chooser 5.0+");
        return inAppWebViewInterfaceProxy.onShowFileChooser(filePathCallback, fileChooserParams.getAcceptTypes(), fileChooserParams.isCaptureEnabled())
                || super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
    }

    // For Android 4.1+
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture)
    {
        LOG.d(LOG_TAG, "File Chooser 4.1+");
        // Call file chooser for Android 3.0+
        openFileChooser(uploadMsg, acceptType);
    }

    // For Android 3.0+
    public void openFileChooser(final ValueCallback<Uri> uploadMsg, String acceptType)
    {
        LOG.d(LOG_TAG, "File Chooser 3.0+");
        inAppWebViewInterfaceProxy.onShowFileChooser(new ValueCallback<Uri[]>() {
            @Override
            public void onReceiveValue(Uri[] value) {
                if(value != null && value.length > 0)
                    uploadMsg.onReceiveValue(value[0]);
                else
                    uploadMsg.onReceiveValue(null);
            }
        }, new String[]{acceptType}, false);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        inAppWebViewInterfaceProxy.onProgressChanged(newProgress);
    }
}

class FullscreenSupportSystemInAppWebViewChromeClient extends SystemInAppWebViewChromeClient {
    public FullscreenSupportSystemInAppWebViewChromeClient(InAppWebViewInterfaceProxy inAppWebViewInterfaceProxy) {
        super(inAppWebViewInterfaceProxy);
    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        super.onShowCustomView(view, callback);
        inAppWebViewInterfaceProxy.onShowCustomView(view);
    }

    @Override
    public void onHideCustomView() {
        super.onHideCustomView();
        inAppWebViewInterfaceProxy.onHideCustomView();
    }
}
