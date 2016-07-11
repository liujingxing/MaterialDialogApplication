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


    public MultiChoiceAdapter(Context context, CharSequence[] items, boolean[] checkedItems) {
        this(context, Arrays.asList(items), checkedItems);
    }

    public MultiChoiceAdapter(Context context, List<? extends CharSequence> items, boolean[] checkedItems) {
        mContext = context;
        mDataList = items;
        mCheckItems = new boolean[items.size()];
        if (checkedItems != null && checkedItems.length > 0) {
            System.arraycopy(checkedItems, 0, mCheckItems, 0, checkedItems.length);
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

    class ViewHolder {
        SmoothCheckBox checkBox;
        TextView tvTitle;

        public ViewHolder(View view) {
            checkBox = (SmoothCheckBox) view.findViewById(R.id.multi_choice_item_checkBox);
            tvTitle = (TextView) view.findViewById(R.id.multi_choice_item_title);
        }
    }
}
