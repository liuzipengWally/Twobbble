package com.twobbble.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import com.twobbble.R
import com.twobbble.application.App
import com.twobbble.tools.toast
import kotlinx.android.synthetic.main.create_bucket_dialog.view.*


/**
 * Created by liuzipeng on 16/7/12.
 */

class DialogManager(private val mContext: Context) {
    private var mDialog: Dialog? = null

    /**
     * 圆形进度条dialog
     * @param text
     */
    fun showCircleProgressDialog(text: String = mContext.resources.getString(R.string.please_wait)) {
        val dialog = ProgressDialog(mContext)
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        dialog.isIndeterminate = true
        dialog.setMessage(text)
        dialog.show()
        mDialog = dialog
    }

    fun showOptionDialog(title: String,
                         content: String,
                         CancelText: String = mContext.resources.getString(R.string.cancel),
                         confirmText: String = mContext.resources.getString(R.string.confirm),
                         onConfirm: () -> Unit,
                         onCancel: () -> Unit) {
        val dialog = AlertDialog.Builder(mContext)
        dialog.setTitle(title).setMessage(content).setPositiveButton(confirmText) { _, _ ->
            onConfirm.invoke()
        }.setNegativeButton(CancelText, { _, _ ->
            onCancel.invoke()
        }).setCancelable(false)

        mDialog = dialog.show()
    }

    fun showEditBucketDialog(name: String = "", description: String? = "", title: String? = mContext.resources.getString(R.string.create_bucket), onConfirm: (String, String?) -> Unit) {
        val dialog = AlertDialog.Builder(mContext)
        val view = LayoutInflater.from(mContext).inflate(R.layout.create_bucket_dialog, null)
        view.mBucketNameEdit.setText(name)
        view.mBucketDescriptionEdit.setText(description)
        dialog.setTitle(title).setPositiveButton(R.string.create) { _, _ ->
            if (!view.mBucketNameEdit.text.isNullOrBlank()) {
                onConfirm.invoke(view.mBucketNameEdit.text.toString(), view.mBucketDescriptionEdit.text.toString())
            } else {
                toast(R.string.bucket_name_null)
            }
        }.setNegativeButton(R.string.cancel, null).setView(view)
        mDialog = dialog.show()
    }

    fun dismissAll() {
        if (mDialog != null && mDialog!!.isShowing) {
            mDialog!!.dismiss()
            mDialog = null
        }
    }

    val isShowing: Boolean
        get() = mDialog!!.isShowing
}
