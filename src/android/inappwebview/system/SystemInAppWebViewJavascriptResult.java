package org.apache.cordova.inappbrowser.inappwebview.system;

import android.webkit.JsPromptResult;

import org.apache.cordova.inappbrowser.inappwebview.base.InAppWebViewJavascriptResultInterface;

/**
 * Created by tarek on 3/22/17.
 */

public class SystemInAppWebViewJavascriptResult implements InAppWebViewJavascriptResultInterface {
    private JsPromptResult result;

    public SystemInAppWebViewJavascriptResult(JsPromptResult result) {
        this.result = result;
    }

    @Override
    public void cancel() {
        this.result.cancel();
    }

    @Override
    public void confirm() {
        this.result.confirm();
    }

    @Override
    public void confirm(String result) {
        this.result.confirm(result);
    }
}
