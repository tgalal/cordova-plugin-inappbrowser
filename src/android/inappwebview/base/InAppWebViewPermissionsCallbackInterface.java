package org.apache.cordova.inappbrowser.inappwebview.base;

/**
 * Created by tarek on 3/22/17.
 */

public interface InAppWebViewPermissionsCallbackInterface {
    void invoke(String origin, boolean allow, boolean retain);
}
