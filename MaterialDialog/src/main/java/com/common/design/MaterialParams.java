package com.common.design;

import android.content.Context;
import android.view.WindowManager;

/**
 * 设置对话框的一些参数
 *
 * @author liujingxing  on 16/4/20.
 */
class MaterialParams {

    Context mContext;
    int mTitleResId = -1;//标题资源id
    CharSequence mTitle;//标题
    int mMessageResId = -1;//内容资源id
    CharSequence mMessage;//内容

    int mPositiveResId = -1;//确定按钮文本资源id
    CharSequence mPositiveText;//确定按钮文本
    int mNegativeResId = -1;//取消按钮文本资源id
    CharSequence mNegativeText;//取消按钮文本
    int mNeutralResId = -1;//中间按钮文本资源id
    CharSequence mNeutralText;//中间按钮文本

    int width;//对话框宽度
    int height;//对话框高度

    //确定、取消、中间按钮单击监听器
    MaterialDialog.OnClickListener mPositiveListener, mNegativeListener, mNeutralListener;
    MaterialDialog.OnSCResultListener mOnSCResultListener;//单选对话框确定按钮监听器，会返回最终的选择结果
    MaterialDialog.OnMCResultListener mOnMCResultListener;//多选对话框确定按钮监听器，会返回最终的选择结果

    MaterialParams(Context context) {
        mContext = context;
        width = (int) (getScreenWidth() * 0.85f);
        height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    CharSequence getTitle() {
        return mTitleResId != -1 ? getString(mTitleResId) : mTitle;
    }

    void setTitle(int titleResId) {
        mTitleResId = titleResId;
        mTitle = null;
    }

    void setTitle(CharSequence title) {
        mTitle = title;
        mTitleResId = -1;
    }

    CharSequence getMessage() {
        return mMessageResId != -1 ? getString(mMessageResId) : mMessage;
    }

    void setMessage(int messageResId) {
        mMessageResId = messageResId;
        mMessage = null;
    }

    void setMessage(CharSequence message) {
        mMessage = message;
        mMessageResId = -1;
    }

    CharSequence getNegativeText() {
        return mNegativeResId != -1 ? getString(mNegativeResId) : mNegativeText;
    }

    boolean haveButton() {
        return getPositiveText() != null || getNegativeText() != null || getNeutralText() != null;
    }

    void setNegative(int negativeResId) {
        mNegativeResId = negativeResId;
        mNegativeText = null;
    }


    void setNegative(CharSequence negativeText) {
        mNegativeText = negativeText;
        mNegativeResId = -1;
    }

    CharSequence getPositiveText() {
        return mPositiveResId != -1 ? getString(mPositiveResId) : mPositiveText;
    }


    void setPositive(int positiveResId) {
        mPositiveResId = positiveResId;
        mPositiveText = null;
    }


    void setPositive(CharSequence positiveText) {
        mPositiveText = positiveText;
        mPositiveResId = -1;
    }

    MaterialDialog.BaseListener getPositiveListener() {
        if (mOnSCResultListener != null) return mOnSCResultListener;
        if (mOnMCResultListener != null) return mOnMCResultListener;
        return mPositiveListener;
    }

    void setPositiveListener(MaterialDialog.BaseListener listener) {
        if (listener instanceof MaterialDialog.OnSCResultListener) {
            mOnSCResultListener = (MaterialDialog.OnSCResultListener) listener;
        } else if (listener instanceof MaterialDialog.OnMCResultListener) {
            mOnMCResultListener = (MaterialDialog.OnMCResultListener) listener;
        } else {
            mPositiveListener = (MaterialDialog.OnClickListener) listener;
        }
    }

    CharSequence getNeutralText() {
        return mNeutralResId != -1 ? getString(mNeutralResId) : mNeutralText;
    }


    void setNeutral(int neutralResId) {
        mNeutralResId = neutralResId;
    }


    void setNeutral(CharSequence neutralText) {
        mNeutralText = neutralText;
    }

    Context getContext() {
        return mContext;
    }

    String getString(int resId) {
        return mContext.getString(resId);
    }

    public int getScreenWidth() {
        return mContext.getResources().getDisplayMetrics().widthPixels;
    }

    public int getScreenHeight() {
        return mContext.getResources().getDisplayMetrics().heightPixels;
    }
}
