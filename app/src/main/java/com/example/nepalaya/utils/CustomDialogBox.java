package com.example.nepalaya.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.nepalaya.R;

public class CustomDialogBox {
    private Activity activity;

    public CustomDialogBox(Activity activity) {
        this.activity = activity;
    }

    private Dialog buidDialogView(int layout, int styleAnimation) {
        final Dialog dialog = new Dialog(activity, R.style.DialogLevelCompleted);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        dialog.setCancelable(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (styleAnimation == 1)
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation1;
        else
            dialog.getWindow().getAttributes().windowAnimations = R.style.MoreDialogAnimation;
        return dialog;
    }

    //dialog layout
    public Dialog buidDialogExit(final CallbackMessage callbackMessage, String message) {
        final Dialog dialog = buidDialogView(R.layout.dialog_exit, 1);
        ((TextView) dialog.findViewById(R.id.tvMsg)).setText(message);

        ((TextView) dialog.findViewById(R.id.tvYes)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackMessage.onSuccess(dialog);
            }
        });
        ((TextView) dialog.findViewById(R.id.tvNo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackMessage.onCancel(dialog);
            }
        });
        return dialog;
    }
}
