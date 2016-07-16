package com.common.design;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * 单选列表适配器
 *
 * @author liujingxing  on 16/07/03.
 */
class SingleChoiceAdapter extends BaseAdapter {
    private Context mContext;
    private List<? extends CharSequence> mDataList;

    private MaterialParams P;


    public SingleChoiceAdapter(MaterialParams p, CharSequence[] items, int checkedItem) {
        this(p, Arrays.asList(items), checkedItem);
    }

    public SingleChoiceAdapter(MaterialParams p, List<? extends CharSequence> items, int checkedItem) {
        mContext = p.getContext();
        mDataList = items;
        p.mSingleChoiceItem = checkedItem;
        this.P = p;
    }


    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.material_dialog_single_choice_item_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.checkBox.setChecked(P.mSingleChoiceItem == position);
        holder.tvTitle.setText(mDataList.get(position));

        return convertView;
    }

    public void refreshData(AdapterView<?> parent, View convertView, int position) {
        boolean isSet = false;
        SmoothCheckBox checkBox;
        for (int i = 0; i < parent.getChildCount(); i++) {
            View v = parent.getChildAt(i);
            checkBox = (SmoothCheckBox) v.findViewById(R.id.single_choice_item_checkBox);
            boolean isCheck = checkBox.isChecked();
            if (i == position && !isCheck) {
                checkBox.setChecked(true, true);
                isSet = true;
            } else if (i != position && isCheck) {//将上次选中的置为false
                checkBox.setChecked(false, true);
                break;
            }
        }
        if (!isSet) {
            checkBox = (SmoothCheckBox) convertView.findViewById(R.id.single_choice_item_checkBox);
            checkBox.setChecked(true, true);
        }
        P.mSingleChoiceItem = position;
    }

    class ViewHolder {
        SmoothCheckBox checkBox;
        TextView tvTitle;

        public ViewHolder(View view) {
            checkBox = (SmoothCheckBox) view.findViewById(R.id.single_choice_item_checkBox);
            tvTitle = (TextView) view.findViewById(R.id.single_choice_item_title);
        }
    }
}
