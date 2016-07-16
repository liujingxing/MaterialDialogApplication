package com.common.design;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * @author liujingxing on 2016/04/20
 */
public class MaterialDialog extends Dialog {

    public MaterialDialog(Context context) {
        this(context, R.style.MaterialDialogStyle);
    }

    public MaterialDialog(Context context, int theme) {
        super(context, theme);
    }

    private static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    interface BaseListener {
    }

    /**
     * 可用于ListView点击item的监听，以及mPositiveButton，mNegativeButton，mNeutralButton3个按钮的监听
     */
    public interface OnClickListener extends BaseListener {
        /**
         * @param dialog MaterialDialog接口对象
         * @param which  作为ListView中item的监听方法时,代表单击item的位置,即position
         *               作为mPositiveButton，mNegativeButton，mNeutralButton3个按钮的监听时,代表按钮的类型,即代表哪一个按钮
         * @return true 代表拦截后续的操作,即Dialog不会消失;反之,Dialog消失
         */
        boolean onClick(DialogInterface dialog, int which);
    }

    /**
     * 单选框最后选择结果监听器
     */
    public interface OnSCResultListener extends BaseListener {
        /**
         * 单选框最后选择结果监听方法
         *
         * @param dialog    MaterialDialog接口对象
         * @param checkItem 单选框为true的位置,即选中的item的position
         * @return true 代表自己拦截后续的操作，即Dialog不会消失，反之，Dialog消失
         */
        boolean onClick(DialogInterface dialog, int checkItem);
    }

    /**
     * 复选框最后选择结果监听器
     */
    public interface OnMCResultListener extends BaseListener {
        /**
         * 复选框最后选择结果监听方法
         *
         * @param dialog     MaterialDialog接口对象
         * @param checkItems 所有为true的复选框的位置的集合
         * @return true 代表自己拦截后续的操作，即Dialog不会消失，反之，Dialog消失
         */
        boolean onClick(DialogInterface dialog, List<Integer> checkItems);
    }

    /**
     * 复选框单击item时的监听器
     */
    public interface OnMultiChoiceClickListener extends BaseListener {
        /**
         * 复选框单击item时的监听方法
         *
         * @param dialog    MaterialDialog接口对象
         * @param position  单击item的位置
         * @param isChecked 单击item后复选框的状态
         */
        void onClick(DialogInterface dialog, int position, boolean isChecked);
    }

    public static class Builder {
        private MaterialParams P;

        private MaterialDialog mMaterialDialog;

        private View mContentView;

        private LinearLayout mMaterialLayout;
        private TextView mTitleView;
        //        private ScrollView mScrollContentLayout;
        private LinearLayout mBottomLayout;
        private Button mPositiveButton;
        private Button mNegativeButton;
        private Button mNeutralButton;

        private TextView mMessageView;
        private LinearLayout mContentLayout;


        public Builder(Context context) {
            P = new MaterialParams(context);

            mContentView = LayoutInflater.from(context).inflate(R.layout.material_dialog_layout, null);

            mMaterialDialog = new MaterialDialog(context);
            mMaterialDialog.setContentView(mContentView);


            mMaterialLayout = (LinearLayout) mContentView.findViewById(R.id.ll_material_layout);
            mTitleView = (TextView) mContentView.findViewById(R.id.tv_title);

//            mScrollContentLayout = (ScrollView) mContentView.findViewById(R.id.content_scroll_layout);
            mContentLayout = (LinearLayout) mContentView.findViewById(R.id.ll_content_layout);
            mMessageView = (TextView) mContentLayout.findViewById(R.id.tv_message);

            mBottomLayout = (LinearLayout) mContentView.findViewById(R.id.ll_bottom_layout);
            mPositiveButton = (Button) mBottomLayout.findViewById(R.id.btn_positive);
            mNegativeButton = (Button) mBottomLayout.findViewById(R.id.btn_negative);
            mNeutralButton = (Button) mBottomLayout.findViewById(R.id.btn_neutral);
        }


        public Builder setTitle(int resId) {
            P.setTitle(resId);
            return this;
        }

        public Builder setTitle(CharSequence title) {
            P.setTitle(title);
            return this;
        }

        public Builder setMessage(int resId) {
            P.setMessage(resId);
            return this;
        }

        public Builder setMessage(CharSequence message) {
            P.setMessage(message);
            return this;
        }

        public Builder setPositiveButton(OnClickListener listener) {
            return setPositiveButton(R.string.confirm, listener);
        }

        /**
         * 如果想知道多选框最后的选择结果,调用本方法传入一个监听器即可,实际上是一个变相的PositiveButton,只是传的监听器不一样
         *
         * @param listener 多选框最后结果监听器
         */
        public Builder setMCResultButton(OnMCResultListener listener) {
            P.setPositive(R.string.confirm);
            P.setPositiveListener(listener);
            return this;
        }

        /**
         * 如果想知道单选框最后的选择结果,调用本方法传入一个监听器即可,实际上是一个变相的PositiveButton,只是传的监听器不一样
         *
         * @param listener 单选框最后结果监听器
         */
        public Builder setSCResultButton(OnSCResultListener listener) {
            P.setPositive(R.string.confirm);
            P.setPositiveListener(listener);
            return this;
        }

        public Builder setPositiveButton(int resId, OnClickListener listener) {
            P.setPositive(resId);
            P.setPositiveListener(listener);
            return this;
        }

        public Builder setPositiveButton(CharSequence text, OnClickListener listener) {
            P.setPositive(text);
            P.setPositiveListener(listener);
            return this;
        }

        public Builder setNegativeButton(final OnClickListener listener) {
            return setNegativeButton(R.string.cancel, listener);
        }

        public Builder setNegativeButton(int resId, final OnClickListener listener) {
            P.setNegative(resId);
            P.mNegativeListener = listener;
            return this;
        }

        public Builder setNegativeButton(CharSequence text, final OnClickListener listener) {
            P.setNegative(text);
            P.mNegativeListener = listener;
            return this;
        }

        public Builder setNeutralButton(int resId, final OnClickListener listener) {
            P.setNeutral(resId);
            P.mNeutralListener = listener;
            return this;
        }

        public Builder setNeutralButton(CharSequence text, final OnClickListener listener) {
            P.setNeutral(text);
            P.mNeutralListener = listener;
            return this;
        }

        public Builder setItems(int itemsId, OnClickListener listener) {
            return setItems(P.getContext().getResources().getStringArray(itemsId), listener);
        }

        public Builder setItems(CharSequence[] items, final OnClickListener listener) {
            return setItems(Arrays.asList(items), listener);
        }

        public Builder setItems(List<? extends CharSequence> items, final OnClickListener listener) {
            ListItemAdapter adapter = new ListItemAdapter(P.getContext(), items);
            return setAdapter(adapter, listener);
        }


        public Builder setSingleChoiceItems(int itemsId, OnClickListener listener) {
            return setSingleChoiceItems(itemsId, 0, listener);
        }

        public Builder setSingleChoiceItems(int itemsId, int checkItem, OnClickListener listener) {
            return setSingleChoiceItems(P.getContext().getResources().getStringArray(itemsId), checkItem, listener);
        }

        public Builder setSingleChoiceItems(CharSequence[] items, OnClickListener listener) {
            return setSingleChoiceItems(items, 0, listener);
        }

        public Builder setSingleChoiceItems(CharSequence[] items, int checkItem, OnClickListener listener) {
            return setSingleChoiceItems(Arrays.asList(items), checkItem, listener);
        }

        public Builder setSingleChoiceItems(List<? extends CharSequence> items, OnClickListener listener) {
            return setSingleChoiceItems(items, 0, listener);
        }

        public Builder setSingleChoiceItems(List<? extends CharSequence> items, int checkItem, OnClickListener listener) {
            SingleChoiceAdapter singleChoiceAdapter = new SingleChoiceAdapter(P, items, checkItem);
            setAdapter(singleChoiceAdapter, listener);
            return this;
        }

        public Builder setMultiChoiceItems(int itemsId, OnMultiChoiceClickListener listener) {
            return setMultiChoiceItems(itemsId, null, listener);
        }

        public Builder setMultiChoiceItems(int itemsId, int[] checkedItems, OnMultiChoiceClickListener listener) {
            return setMultiChoiceItems(P.getContext().getResources().getStringArray(itemsId), checkedItems, listener);
        }

        public Builder setMultiChoiceItems(CharSequence[] items, OnMultiChoiceClickListener listener) {
            return setMultiChoiceItems(items, null, listener);
        }

        public Builder setMultiChoiceItems(CharSequence[] items, int[] checkedItems, OnMultiChoiceClickListener listener) {
            return setMultiChoiceItems(Arrays.asList(items), checkedItems, listener);
        }

        public Builder setMultiChoiceItems(List<? extends CharSequence> items, OnMultiChoiceClickListener listener) {
            return setMultiChoiceItems(items, null, listener);
        }

        public Builder setMultiChoiceItems(List<? extends CharSequence> items, int[] checkedItems, OnMultiChoiceClickListener listener) {
            MultiChoiceAdapter multiChoiceAdapter = new MultiChoiceAdapter(P, items, checkedItems);
            setAdapter(multiChoiceAdapter, listener);
            return this;
        }

        public Builder setAdapter(ListAdapter adapter, final OnClickListener listener) {
            return setAdapter(adapter, (BaseListener) listener);
        }

        private Builder setAdapter(ListAdapter listAdapter, final BaseListener listener) {
            ListView listView = (ListView) LayoutInflater.from(P.getContext()).inflate(R.layout.material_dialog_listview_layout, null);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Adapter adapter = parent.getAdapter();
                    if (adapter instanceof MultiChoiceAdapter) {
                        SmoothCheckBox checkBox = (SmoothCheckBox) view.findViewById(R.id.multi_choice_item_checkBox);
                        checkBox.toggle(true);
                        ((MultiChoiceAdapter) adapter).refreshData(position);
                        if (listener == null) return;
                        ((OnMultiChoiceClickListener) listener).onClick(mMaterialDialog, position, checkBox.isChecked());
                    } else if (adapter instanceof SingleChoiceAdapter) {
                        ((SingleChoiceAdapter) adapter).refreshData(parent, view, position);
                        if (listener == null) return;
                        if (!((OnClickListener) listener).onClick(mMaterialDialog, position)) {
                            mMaterialDialog.dismiss();
                        }
                    } else if (adapter instanceof ListItemAdapter) {
                        if (listener == null || !((OnClickListener) listener).onClick(mMaterialDialog, position)) {
                            mMaterialDialog.dismiss();
                        }
                    }
                }
            });
            listView.setAdapter(listAdapter);
            setContentView(listView, false);
            return this;
        }

        public Builder setContentView(View contentView, boolean needSpace) {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            contentView.setLayoutParams(layoutParams);
            if (!needSpace) {
                mContentLayout.setPadding(0, 0, 0, 0);
//                setListViewHeightBasedOnChildren((ListView) contentView);
            }
            if (mContentLayout != null) {
                mContentLayout.removeAllViews();
                mContentLayout.addView(contentView);
            }
            return this;
        }

        public Builder setContentView(View contentView) {
            return setContentView(contentView, true);
        }

        public Builder setContentView(int layoutResId) {
            setContentView(LayoutInflater.from(P.getContext()).inflate(layoutResId, null));
            return this;
        }

        public Builder setBackground(Drawable drawable) {
            mMaterialLayout.setBackgroundDrawable(drawable);
            return this;
        }

        public Builder setBackground(int resId) {
            mMaterialLayout.setBackgroundResource(resId);
            return this;
        }

        public Builder setLayout(int width, int height) {
            P.width = width;
            P.height = height;
            return this;
        }

        /**
         * 通过比例设置Dialog的宽高
         *
         * @param widthPercent  0～1之间
         * @param heightPercent 0～1之间
         */
        public Builder setLayout(float widthPercent, float heightPercent) {
            P.width = (int) (P.getScreenWidth() * widthPercent);
            P.height = (int) (P.getScreenHeight() * heightPercent);
            return this;
        }

        public Builder setWidth(int width) {
            P.width = width;
            return this;
        }

        /**
         * 通过比例设置Dialog的宽度
         *
         * @param percent 0～1之间
         */
        public Builder setWidth(float percent) {
            P.width = (int) (P.getScreenWidth() * percent);
            return this;
        }

        public Builder setHeight(int height) {
            P.height = height;
            return this;
        }

        /**
         * 通过比例设置Dialog的高度
         *
         * @param percent 0～1之间
         */
        public Builder setHeight(float percent) {
            P.height = (int) (P.getScreenHeight() * percent);
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            mMaterialDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            mMaterialDialog.setCancelable(cancelable);
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener listener) {
            mMaterialDialog.setOnDismissListener(listener);
            return this;
        }

        public MaterialDialog create() {
            setAttributes(mMaterialDialog.getWindow());

            setText(P.getTitle(), mTitleView);
            setText(P.getMessage(), mMessageView);
            setText(P.getPositiveText(), mPositiveButton, P.getPositiveListener());
            setText(P.getNegativeText(), mNegativeButton, P.mNegativeListener);
            setText(P.getNeutralText(), mNeutralButton, P.mNeutralListener);

            if (!P.haveButton()) {
                mBottomLayout.setVisibility(View.GONE);
            }

            return mMaterialDialog;
        }

        private void setAttributes(Window window) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                    WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MASK_STATE);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(P.width, P.height);
            if (Build.VERSION.SDK_INT >= 14) {
                window.setDimAmount(0.6f);
            } else {
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.dimAmount = 0.6f;
                window.setAttributes(attributes);
            }
        }

        public void show() {
            create().show();
        }

        private void setText(CharSequence text, TextView textView) {
            setText(text, textView, null);
        }

        private void setText(CharSequence text, TextView textView, final BaseListener listener) {
            if (TextUtils.isEmpty(text)) {
                textView.setVisibility(View.GONE);
                return;
            }
            textView.setText(text);
            if (!(textView instanceof Button)) return;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int which;
                    if (v == mPositiveButton) {
                        which = DialogInterface.BUTTON_POSITIVE;
                    } else if (v == mNegativeButton) {
                        which = DialogInterface.BUTTON_NEGATIVE;
                    } else {
                        which = DialogInterface.BUTTON_NEUTRAL;
                    }
                    if (listener == null) mMaterialDialog.dismiss();
                    boolean isIntercept = false;
                    if (listener instanceof OnSCResultListener) {
                        isIntercept = ((OnSCResultListener) listener).onClick(mMaterialDialog, P.mSingleChoiceItem);
                    } else if (listener instanceof OnMCResultListener) {
                        isIntercept = ((OnMCResultListener) listener).onClick(mMaterialDialog, P.mMultiChoiceItems);
                    } else if (listener instanceof OnClickListener) {
                        isIntercept = ((OnClickListener) listener).onClick(mMaterialDialog, which);
                    }
                    if (!isIntercept) {
                        mMaterialDialog.dismiss();
                    }
                }
            });
        }
    }
}

