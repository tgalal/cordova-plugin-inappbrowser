package org.apache.cordova.inappbrowser.inappwebview.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.widget.FrameLayout;

import org.apache.cordova.LOG;

/**
 * Created by tarek on 3/20/17.
 */

public abstract class InAppWebView implements InAppWebViewInterface {
    protected static final String LOG_TAG = "InAppWebView";
    protected static final String URL_SEARCH = "http://google.com/search?q=%s";

    private InAppWebViewEventsListener inAppWebViewEventsListener;
    private InAppWebViewSslErrorHandlerInterface sslErrorHandlerInterface;
    protected InAppWebViewNavigationInterface inAppWebViewNavigationInterface;
    protected InAppWebViewSettingsInterface inAppWebViewSettingsInterface;
    protected InAppWebViewCookieManagerInterface inAppWebViewCookieManagerInterface;

    private ViewGroup parentForAutoCustomViewHandling = null;
    private View currentFullscreenView;
    private InAppWebViewInterfaceProxy inAppWebViewInterfaceProxy = new InAppWebViewInterfaceProxy(this);
    private ViewGroup webViewContainer;
    private View webView;
    private boolean isPrivateBrowsing;
    private Context context;

    public InAppWebView(Context context) {
        this.context = context;
        this.webViewContainer = new FrameLayout(context);
        this.sslErrorHandlerInterface = new InAppWebViewSSlErrorHandler();
    }

    public InAppWebViewInterfaceProxy getInAppWebViewInterfaceProxy() {
        return inAppWebViewInterfaceProxy;
    }

    public void init(View webView, InAppWebViewNavigationInterface inAppWebViewNavigationInterface, InAppWebViewSettingsInterface inAppWebViewSettingsInterface, InAppWebViewCookieManagerInterface inAppWebViewCookieManagerInterface) {
        this.webView = webView;
        this.webViewContainer.addView(webView);
        this.inAppWebViewNavigationInterface = inAppWebViewNavigationInterface;
        this.inAppWebViewSettingsInterface = inAppWebViewSettingsInterface;
        this.inAppWebViewCookieManagerInterface = inAppWebViewCookieManagerInterface;
        setDefaultSettings(inAppWebViewSettingsInterface);
    }

    protected Context getContext() {
        return context;
    }

    @SuppressLint("SetJavaScriptEnabled")
    protected void setDefaultSettings(InAppWebViewSettingsInterface settingsInterface) {
        settingsInterface.setJavaScriptEnabled(true);
        settingsInterface.setJavaScriptCanOpenWindowsAutomatically(true);
        settingsInterface.setDomStorageEnabled(true);
    }

    public void togglePrivateMode(boolean privateMode) {
        inAppWebViewSettingsInterface.setCacheMode(privateMode ? InAppWebViewSettingsInterface.LOAD_NO_CACHE : InAppWebViewSettingsInterface.LOAD_DEFAULT);
        inAppWebViewSettingsInterface.setAppCacheEnabled(!privateMode);
        inAppWebViewSettingsInterface.setSaveFormData(!privateMode);
        inAppWebViewSettingsInterface.setSavePassword(!privateMode);
        inAppWebViewSettingsInterface.setDomStorageEnabled(!privateMode);
        inAppWebViewSettingsInterface.setDatabaseEnabled(!privateMode);
        this.isPrivateBrowsing = privateMode;
    }

    @Override
    public InAppWebViewNavigationInterface getNavigation() {
        return inAppWebViewNavigationInterface;
    }

    @Override
    public InAppWebViewSettingsInterface getSettings() {
        return inAppWebViewSettingsInterface;
    }

    @Override
    public InAppWebViewCookieManagerInterface getCookieManager() {
        return inAppWebViewCookieManagerInterface;
    }

    @Override
    public View getView() {
        return webViewContainer;
    }

    protected void onDestroy() {}

    public final void destroy() {
        if(isPrivateBrowsing) {
            getNavigation().clearHistory();
            inAppWebViewCookieManagerInterface.clearCookies();
            inAppWebViewCookieManagerInterface.clearSessionCookies();
            clearFormData();
            clearCache();
        }
        onDestroy();
    }

    public void onPause(){}

    public void onResume(){}

    public void setParentForAutoCustomViewHandling(ViewGroup parentForAutoCustomViewHandling) {
        this.parentForAutoCustomViewHandling = parentForAutoCustomViewHandling;
    }

    public boolean autoHandleCustomViews() {
        return parentForAutoCustomViewHandling != null;
    }

    protected ViewGroup getParentForAutoCustomViewHandling() {
        return parentForAutoCustomViewHandling;
    }

    public void setInAppWebViewListener(InAppWebViewEventsListener inAppWebViewListener) {
        this.inAppWebViewEventsListener = inAppWebViewListener;
    }

    protected final boolean onShowFileChooser(ValueCallback<Uri[]> uploadFile, String[] acceptTypes, boolean capture){
        return inAppWebViewEventsListener != null && inAppWebViewEventsListener.onShowFileChooser(uploadFile, acceptTypes, capture);
    }

    protected final boolean onJsPrompt(String url, String message, String defaultValue, InAppWebViewJavascriptResultInterface result) {
        return inAppWebViewEventsListener != null && inAppWebViewEventsListener.onJsPrompt(url, message, defaultValue, result);
    }

    protected final void onGeolocationPermissionsShowPrompt(String origin, InAppWebViewPermissionsCallbackInterface callback) {
        if(inAppWebViewEventsListener != null)
            inAppWebViewEventsListener.onGeolocationPermissionsShowPrompt(origin, callback);
    }

    protected final void onProgressChanged(int progress) {
        if(inAppWebViewEventsListener != null)
            inAppWebViewEventsListener.onProgressChanged(progress);
    }

    protected final void onPageLoadFinished(String url) {
        if(!isPrivateBrowsing)
            getCookieManager().flush();

        // https://issues.apache.org/jira/browse/CB-11248
        webView.clearFocus();
        webView.requestFocus();

        if(inAppWebViewEventsListener != null)
            inAppWebViewEventsListener.onPageLoadFinished(url);
    }

    public void search(String term) {
        loadUrl(getSearchUrl(term));
    }

    protected String getSearchUrl(String term) {
        return String.format(URL_SEARCH, term);
    }

    public void load(String something) {
        load(something, true);
    }

    public void load(String something, boolean search) {
        if(search && (!something.contains(".") || something.contains(" "))) {
            search(something);
        } else {
            if (!something.startsWith("http") && !something.startsWith("file:")) {
                something = "http://" + something;
            }
            loadUrl(something);
        }
    }

    protected final boolean onLoadUrl(String url) {
        return inAppWebViewEventsListener != null && inAppWebViewEventsListener.onLoadUrl(url);
    }

    protected final void onReceivedError(int errorCode, String description, String failingUrl) {
        if(inAppWebViewEventsListener != null)
            inAppWebViewEventsListener.onReceivedLoadError(errorCode, description, failingUrl);
    }

    protected final void onReceivedHttpAuthRequest(InAppWebViewAuthHandlerInterface cordovaHttpAuthHandler, String host, String realm) {
        if(inAppWebViewEventsListener != null)
            inAppWebViewEventsListener.onReceivedHttpAuthRequest(cordovaHttpAuthHandler, host, realm);
    }

    protected final void onToggledFullScreen(boolean inFullScreen) {
        if(inAppWebViewEventsListener != null)
            inAppWebViewEventsListener.onToggledFullScreen(inFullScreen);
    }

    protected final void onSslError(SslError sslError, ValueCallback<Boolean> valueCallback) {
        if(this.sslErrorHandlerInterface != null)
            sslErrorHandlerInterface.handle(sslError, valueCallback);
    }

    protected final void onShowCustomView(View view) {
        if(autoHandleCustomViews()) {
            currentFullscreenView = view;
            parentForAutoCustomViewHandling.addView(
                view, 0, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            );
            view.setBackgroundColor(Color.BLACK);
            view.bringToFront();
            onToggledFullScreen(true);
        }
        if(inAppWebViewEventsListener != null)
            inAppWebViewEventsListener.onShowCustomView(view);
    }

    protected final void onHideCustomView() {
        if(autoHandleCustomViews()) {
            parentForAutoCustomViewHandling.removeView(currentFullscreenView);
            currentFullscreenView = null;
            onToggledFullScreen(false);
        }

        if(inAppWebViewEventsListener != null)
            inAppWebViewEventsListener.onHideCustomView();
    }

    protected final void onPageLoadStarted(String url) {
        if(inAppWebViewEventsListener != null) {
            String newloc = "";
            if (url.startsWith("http:") || url.startsWith("https:") || url.startsWith("file:")) {
                newloc = url;
            } else {
                // Assume that everything is HTTP at this point, because if we don't specify,
                // it really should be.  Complain loudly about this!!!
                LOG.e(LOG_TAG, "Possible Uncaught/Unknown URI");
                newloc = "http://" + url;
            }

            inAppWebViewEventsListener.onPageLoadStarted(newloc);
        }
    }

    private class InAppWebViewSSlErrorHandler implements InAppWebViewSslErrorHandlerInterface {
        @Override
        public void handle(SslError sslError, final ValueCallback<Boolean> callback) {
            webViewContainer.removeAllViews();
            SslErrorView sslErrorView = new SslErrorView(getContext(), sslError){
                @Override
                void onProceed() {
                    webViewContainer.removeAllViews();
                    webViewContainer.addView(webView);
                    callback.onReceiveValue(true);
                }

                @Override
                void onCancel() {
                    webViewContainer.removeAllViews();
                    webViewContainer.addView(webView);
                    callback.onReceiveValue(false);
                }
            };
            webViewContainer.addView(sslErrorView);
            sslErrorView.requestFocus();
        }
    }
}
