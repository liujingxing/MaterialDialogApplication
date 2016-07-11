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
 * @author liujingxing  on 16/4/21.
 */
class ListItemAdapter extends BaseAdapter {
    private Context mContext;
    private List<? extends CharSequence> mDataList;

    public ListItemAdapter(Context context, CharSequence[] data) {
        this(context, Arrays.asList(data));
    }

    public ListItemAdapter(Context context, List<? extends CharSequence> data) {
        mContext = context;
        mDataList = data;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.material_dialog_list_item_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTextView.setText(mDataList.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView mTextView;

        public ViewHolder(View view) {
            mTextView = (TextView) view.findViewById(R.id.text);
        }
    }
}
