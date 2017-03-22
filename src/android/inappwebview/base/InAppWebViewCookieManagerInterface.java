package org.apache.cordova.inappbrowser.inappwebview.base;

/**
 * Created by tarek on 3/21/17.
 */

public interface InAppWebViewCookieManagerInterface {
    void setCookiesEnabled(boolean accept);
    void setCookie(final String url, final String value);
    String getCookie(final String url);
    void clearCookies();
    void clearSessionCookies();
    void flush();
}
