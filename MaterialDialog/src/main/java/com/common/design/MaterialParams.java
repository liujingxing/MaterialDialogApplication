package com.common.design;

import android.content.Context;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujingxing  on 16/4/20.
 */
class MaterialParams {

    Context mContext;
    int mTitleResId = -1;
    CharSequence mTitle;
    int mMessageResId = -1;
    CharSequence mMessage;

    int mPositiveResId = -1;
    CharSequence mPositiveText;
    int mNegativeResId = -1;
    CharSequence mNegativeText;
    int mNeutralResId = -1;
    CharSequence mNeutralText;

    int width;
    int height;

    int mSingleChoiceItem;
    List<Integer> mMultiChoiceItems;

    MaterialDialog.OnClickListener mPositiveListener, mNegativeListener, mNeutralListener;
    MaterialDialog.OnSCResultListener mOnSCResultListener;
    MaterialDialog.OnMCResultListener mOnMCResultListener;

    MaterialParams(Context context) {
        mContext = context;
        width = (int) (getScreenWidth() * 0.85f);
        height = WindowManager.LayoutParams.WRAP_CONTENT;
        mMultiChoiceItems = new ArrayList<>();
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
