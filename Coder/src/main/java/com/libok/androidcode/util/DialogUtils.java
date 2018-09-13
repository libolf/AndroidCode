package com.libok.androidcode.util;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.libok.androidcode.R;

/**
 * @author liboK  2018/09/03 上午 11:38
 */
public class DialogUtils {

    public static final int TYPE_WHITE_CONFIRM = 1;
    public static final int TYPE_BLUE_CONFIRM = 2;
    public static final int TYPE_SINGLE_POSITIVE = 3;

    public static void showNormalDialog(Context context, int type, String dialogTitle, String dialogMessage, OnButtonClickListener listener) {
        showNormalDialog(context, type, dialogTitle, dialogMessage, "确定", true, true, listener);
    }

    public static void showNormalDialog(Context context, int type, String dialogTitle, String dialogMessage, String dialogPositiveText, boolean isCancelable, boolean isTouchOutSideCancelable, final OnButtonClickListener buttonClickListener) {
        if (context == null || type > TYPE_SINGLE_POSITIVE || TextUtils.isEmpty(dialogTitle) || TextUtils.isEmpty(dialogMessage) || TextUtils.isEmpty(dialogPositiveText)) {
            throw new IllegalArgumentException();
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View view = null;
        TextView titleText = null;
        TextView messageText = null;
        Button negativeButton = null;
        Button positiveButton = null;
        if (type == TYPE_WHITE_CONFIRM) {
            view = LayoutInflater.from(context).inflate(R.layout.dialog_universal, null);
            titleText = view.findViewById(R.id.dialog_universal_title_text);
            messageText = view.findViewById(R.id.dialog_universal_message_text);
            negativeButton = view.findViewById(R.id.dialog_universal_negative_button);
            positiveButton = view.findViewById(R.id.dialog_universal_positive_button);
        } else if (type == TYPE_BLUE_CONFIRM) {
            view = LayoutInflater.from(context).inflate(R.layout.dialog_universal_1, null);
            titleText = view.findViewById(R.id.dialog_ensure_title_text);
            messageText = view.findViewById(R.id.dialog_ensure_message_text);
            negativeButton = view.findViewById(R.id.dialog_ensure_negative_button);
            positiveButton = view.findViewById(R.id.dialog_ensure_positive_button);
        } else if (type == TYPE_SINGLE_POSITIVE) {
            view = LayoutInflater.from(context).inflate(R.layout.dialog_hint, null);
            titleText = view.findViewById(R.id.dialog_hint_title_text);
            messageText = view.findViewById(R.id.dialog_hint_message_text);
            positiveButton = view.findViewById(R.id.dialog_hint_confirm_button);
        } else {
            throw new IllegalArgumentException();
        }

        titleText.setText(dialogTitle);
        messageText.setText(dialogMessage);
        if (negativeButton != null) {
            negativeButton.setText("取消");
        }
        positiveButton.setText(dialogPositiveText);
        builder.setView(view);
        final AlertDialog dialog = builder.show();
        dialog.setCancelable(isCancelable);
        dialog.setCanceledOnTouchOutside(isTouchOutSideCancelable);

        if (negativeButton != null) {
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (buttonClickListener != null) {
                        buttonClickListener.onNegativeClick();
                    }
                    dialog.dismiss();
                }
            });
        }
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonClickListener != null) {
                    buttonClickListener.onPositiveClick();
                }
                dialog.dismiss();
            }
        });

    }


    public interface OnButtonClickListener {
        void onPositiveClick();

        void onNegativeClick();
    }
}
