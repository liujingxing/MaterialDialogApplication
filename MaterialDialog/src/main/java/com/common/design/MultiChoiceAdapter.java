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
 * 多选列表适配器
 *
 * @author liujingxing  on 16/07/03.
 */
class MultiChoiceAdapter extends BaseAdapter {
    private Context mContext;
    private List<? extends CharSequence> mDataList;

    private boolean[] mCheckItems;

    private MaterialParams P;


    public MultiChoiceAdapter(MaterialParams p, CharSequence[] items, int[] checkedItems) {
        this(p, Arrays.asList(items), checkedItems);
    }

    public MultiChoiceAdapter(MaterialParams p, List<? extends CharSequence> items, int[] checkedItems) {
        this.P = p;
        mContext = p.getContext();
        mDataList = items;
        mCheckItems = new boolean[items.size()];
        if (checkedItems == null || checkedItems.length == 0) return;
        for (Integer position : checkedItems) {
            if (position >= mCheckItems.length) continue;
            p.mMultiChoiceItems.add(position);
            mCheckItems[position] = true;
        }
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.material_dialog_multi_choice_item_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.checkBox.setChecked(mCheckItems[position]);
        holder.tvTitle.setText(mDataList.get(position));
        return convertView;
    }

    public void refreshData(int position) {
        if (P.mMultiChoiceItems.contains(position)) {
            mCheckItems[position] = false;
            P.mMultiChoiceItems.remove((Integer) position);
        } else {
            P.mMultiChoiceItems.add(position);
            mCheckItems[position] = true;
        }
    }

    class ViewHolder {
        SmoothCheckBox checkBox;
        TextView tvTitle;

        public ViewHolder(View view) {
            checkBox = (SmoothCheckBox) view.findViewById(R.id.multi_choice_item_checkBox);
            tvTitle = (TextView) view.findViewById(R.id.multi_choice_item_title);
        }
    }
}
