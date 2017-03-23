package org.apache.cordova.inappbrowser.inappwebview.base;

import android.content.Context;
import android.graphics.Color;
import android.net.http.SslError;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by tarek on 3/23/17.
 */

public abstract class SslErrorView extends RelativeLayout {
    SslError sslError;
    public SslErrorView(Context context, SslError sslError) {
        super(context);
        this.sslError = sslError;
        init();
    }

    abstract void onProceed();
    abstract void onCancel();

    void init() {
        setBackgroundColor(Color.WHITE);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout containerLayout = new LinearLayout(getContext());
        containerLayout.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(500, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(CENTER_HORIZONTAL);
        layoutParams.addRule(ALIGN_PARENT_TOP);
        layoutParams.topMargin = 30;
        containerLayout.setLayoutParams(layoutParams);
        addView(containerLayout);

        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(50, 50);
        linearLayoutParams.bottomMargin = 30;
//        imageView.setImageDrawable(R.drawable.ic_warning);
        imageView.setLayoutParams(linearLayoutParams);
        containerLayout.addView(imageView);



        TextView warningText = new TextView(getContext());
        warningText.setText("Your connection is not private");
        warningText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);

        linearLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutParams.bottomMargin = 30;
        warningText.setLayoutParams(linearLayoutParams);
        containerLayout.addView(warningText);



        TextView errorText = new TextView(getContext());
        errorText.setText("ERR");
        linearLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutParams.bottomMargin = 10;
        errorText.setLayoutParams(linearLayoutParams);
        containerLayout.addView(errorText);


        TextView errorDesc = new TextView(getContext());
        errorDesc.setText("Attackers might be trying to steal your information from tv.eurosport.com (for example, passwords, messages, or credit cards).");
        linearLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutParams.bottomMargin = 50;
        errorDesc.setLayoutParams(linearLayoutParams);
        containerLayout.addView(errorDesc);



        RelativeLayout actionsLayout = new RelativeLayout(getContext());
        actionsLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        containerLayout.addView(actionsLayout);

        Button safetyButton = new Button(getContext());
        safetyButton.setText("Back to safety");
        RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeLayoutParams.addRule(ALIGN_PARENT_RIGHT);
        relativeLayoutParams.addRule(ALIGN_PARENT_TOP);
        safetyButton.setLayoutParams(relativeLayoutParams);
        actionsLayout.addView(safetyButton);

        TextView proceedButton = new TextView(getContext());
        relativeLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeLayoutParams.addRule(ALIGN_PARENT_LEFT);
        relativeLayoutParams.addRule(CENTER_VERTICAL);
        proceedButton.setText("Proceed (unsafe)");
        proceedButton.setLayoutParams(relativeLayoutParams);
        actionsLayout.addView(proceedButton);

        proceedButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onProceed();
            }
        });
        safetyButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancel();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            onCancel();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
