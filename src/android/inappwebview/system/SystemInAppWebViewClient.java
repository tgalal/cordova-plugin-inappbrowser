package org.apache.cordova.inappbrowser.inappwebview.system;

import android.graphics.Bitmap;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.apache.cordova.inappbrowser.inappwebview.base.InAppWebViewAuthHandlerInterface;
import org.apache.cordova.inappbrowser.inappwebview.base.InAppWebViewInterfaceProxy;

/**
 * Created by tarek on 3/20/17.
 */

public class SystemInAppWebViewClient extends WebViewClient {

    protected InAppWebViewInterfaceProxy inAppWebViewInterfaceProxy;

    public SystemInAppWebViewClient(InAppWebViewInterfaceProxy inAppWebViewInterfaceProxy) {
        this.inAppWebViewInterfaceProxy = inAppWebViewInterfaceProxy;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        // CB-10395 InAppBrowser's WebView not storing cookies reliable to local device storage
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager.getInstance().sync();
        }
        inAppWebViewInterfaceProxy.onPageLoadFinished(url);
    }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return this.inAppWebViewInterfaceProxy.onLoadUrl(url) || super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            this.inAppWebViewInterfaceProxy.onPageLoadStarted(url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            this.inAppWebViewInterfaceProxy.onReceivedLoadError(errorCode, description, failingUrl);
        }

        @Override
        public void onReceivedHttpAuthRequest(final WebView view, final HttpAuthHandler handler, final String host, final String realm) {
            this.inAppWebViewInterfaceProxy.onReceivedHttpAuthRequest(new InAppWebViewAuthHandlerInterface() {
                @Override
                public void onUnhandled() {
                    SystemInAppWebViewClient.super.onReceivedHttpAuthRequest(view, handler,host, realm);
                }

                @Override
                public void cancel() {
                    handler.cancel();
                }

                @Override
                public void proceed(String username, String password) {
                    handler.proceed(username, password);

                }
            }, host, realm);
        }
}
