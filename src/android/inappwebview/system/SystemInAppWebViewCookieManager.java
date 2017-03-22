package org.apache.cordova.inappbrowser.inappwebview.system;

import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;

import org.apache.cordova.inappbrowser.inappwebview.base.InAppWebViewCookieManagerInterface;

/**
 * Created by tarek on 3/21/17.
 */

public class SystemInAppWebViewCookieManager implements InAppWebViewCookieManagerInterface {
    CookieManager cookieManager;

    public SystemInAppWebViewCookieManager() {
        this.cookieManager = CookieManager.getInstance();
    }

    @Override
    public void setCookiesEnabled(boolean accept) {
        cookieManager.setAcceptCookie(false);
    }

    @Override
    public void setCookie(String url, String value) {
        cookieManager.setCookie(url, value);
    }

    @Override
    public String getCookie(String url) {
        return cookieManager.getCookie(url);
    }

    @Override
    public void clearCookies() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
                @Override
                public void onReceiveValue(Boolean value) {}
            });
        } else {
            cookieManager.removeAllCookie();
        }
    }

    @Override
    public void clearSessionCookies() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeSessionCookies(new ValueCallback<Boolean>() {
                @Override
                public void onReceiveValue(Boolean value) {}
            });
        } else {
            cookieManager.removeSessionCookie();
        }
    }

    @Override
    public void flush() {
        // CB-10395 InAppBrowser's WebView not storing cookies reliable to local device storage
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush();
        } else {
            CookieSyncManager.getInstance().sync();
        }
    }
}
