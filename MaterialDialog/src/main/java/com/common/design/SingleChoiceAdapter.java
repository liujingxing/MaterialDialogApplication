package com.common.design;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * 单选列表适配器
 * @author liujingxing  on 16/07/03.
 */
class SingleChoiceAdapter extends BaseAdapter {
    private Context mContext;
    private List<? extends CharSequence> mDataList;

    private int mCheckItem;

    public SingleChoiceAdapter(Context context, CharSequence[] items, int checkedItem) {
        this(context, Arrays.asList(items), checkedItem);
    }

    public SingleChoiceAdapter(Context context, List<? extends CharSequence> items, int checkedItem) {
        mContext = context;
        mDataList = items;
        mCheckItem = checkedItem;
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
        holder.checkBox.setChecked(mCheckItem == position);
        holder.tvTitle.setText(mDataList.get(position));

        return convertView;
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
