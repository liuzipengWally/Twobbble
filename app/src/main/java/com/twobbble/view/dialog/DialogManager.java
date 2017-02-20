package com.twobbble.view.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


/**
 * Created by liuzipeng on 16/7/12.
 */

public class DialogManager {
    private Context mContext;
    private Dialog mDialog;

    public DialogManager(Context mContext) {
        this.mContext = mContext;
    }

    public interface ConfirmBtnCLickListener {
        void onConfirm();

        void onCancel();
    }

    public interface OnSelectItemListener {
        void select(int position);
    }

    public interface onDateSelectListener {
        void onDateSelect(String date);
    }

    /**
     * 圆形进度条dialog
     *
     * @param text
     * @return
     */
    public ProgressDialog showCircleProgressDialog(String text) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage(text);
        dialog.show();
        mDialog = dialog;
        return dialog;
    }

    /**
     * 横向进度条dialog
     *
     * @param progress
     * @param listener
     * @return
     */
    public ProgressDialog showHorizontalDialog(int progress, final ConfirmBtnCLickListener listener) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setIndeterminate(false);
        dialog.setTitle("下载新版本中");
        dialog.setCancelable(false);
        dialog.setMax(100);
        dialog.setProgress(progress);
        dialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                listener.onCancel();
            }
        });
        dialog.show();
        mDialog = dialog;
        return dialog;
    }


    public void showOptionDialog(final ConfirmBtnCLickListener listener, int titleResId, int textResId, int leftTextResId, int rightTextResId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(titleResId);
        builder.setCancelable(false);
        builder.setMessage(textResId);
        builder.setNegativeButton(leftTextResId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onCancel();
            }
        });
        builder.setPositiveButton(rightTextResId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onConfirm();
            }
        });

        mDialog = builder.show();
    }

    public void showOptionDialog(final ConfirmBtnCLickListener listener, int textResId, int leftTextResId, int rightTextResId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("提示");
        builder.setCancelable(false);
        builder.setMessage(textResId);
        builder.setNegativeButton(leftTextResId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onCancel();
            }
        });
        builder.setPositiveButton(rightTextResId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onConfirm();
            }
        });

        mDialog = builder.show();
    }


    public void showOptionDialog(final ConfirmBtnCLickListener listener, String title, String text, String leftText, String rightText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setMessage(text);
        builder.setNegativeButton(leftText, null);
        builder.setPositiveButton(rightText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onConfirm();
            }
        });

        mDialog = builder.show();
    }

    /**
     * 单个按钮的提示dialog
     * 无事件抛出
     *
     * @param titleResId
     * @param textResId
     * @param btnTextResId
     */
    public void showAlertDialogSingleBtn(int titleResId, int textResId, int btnTextResId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(titleResId);
        builder.setMessage(textResId);
        builder.setCancelable(false);
        builder.setPositiveButton(btnTextResId, null);
        mDialog = builder.show();
    }

    /**
     * 单个按钮的提示dialog
     * 抛出一个确定事件
     *
     * @param titleResId
     * @param textResId
     * @param btnTextResId
     */
    public void showAlertDialogSingleBtn(final ConfirmBtnCLickListener listener, int titleResId, int textResId, int btnTextResId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(titleResId);
        builder.setMessage(textResId);
        builder.setCancelable(false);
        builder.setPositiveButton(btnTextResId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onConfirm();
            }
        });
        mDialog = builder.show();
    }

    /**
     * 单个按钮的提示dialog
     * 无事件抛出
     *
     * @param title
     * @param content
     * @param btnText
     */
    public void showAlertDialogSingleBtn(String title, String content, String btnText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setCancelable(false);
        builder.setPositiveButton(btnText, null);
        mDialog = builder.show();
    }


    public void dismissAll() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public boolean isShowing() {
        return mDialog.isShowing();
    }
}
