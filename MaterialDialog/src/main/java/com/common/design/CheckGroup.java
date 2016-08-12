package com.common.design;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.common.design.entity.Option;
import com.common.design.entity.OptionWrapper;

import java.util.List;

/**
 * 获取本实例后调用setOptionWrapper方法传入选项集合即可
 *
 * @author liujingxing  on 16/7/19.
 * @see #setOptionWrapper(OptionWrapper)
 */
public class CheckGroup extends ListView implements AdapterView.OnItemClickListener {

    public static final int CIRCLE = 0;//画圆
    public static final int SQUARE = 1;//画正方形

    public int mShape = CIRCLE;

    private Context mContext;

    private OptionWrapper mOptionWrapper;

    /**
     * 选项适配器
     */
    private OptionsAdapter mAdapter;

    /**
     * 选中时的颜色
     */
    private int mCheckedColor = -1;

    private int leftPadding;


    public CheckGroup(Context context) {
        this(context, null);
    }

    public CheckGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setDividerHeight(0);
        setOnItemClickListener(this);
        mOptionWrapper = new OptionWrapper();
        mAdapter = new OptionsAdapter(mOptionWrapper.getOptions(), attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CheckBox);
        mShape = typedArray.getInt(R.styleable.CheckBox_shape, CIRCLE);
        typedArray.recycle();
    }

    /**
     * 传入一个选项集合的封装类后,就可正常显示CheckGroup
     *
     * @param wrapper 选项集合的封装类
     */
    public void setOptionWrapper(OptionWrapper wrapper) {
        if (wrapper == null) return;
        mOptionWrapper.set(wrapper);
        setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!mOptionWrapper.isEnabled()) return;
        if (mOptionWrapper.isSingleChoice()) {
            handleSingleChoice(parent, view, position);
        } else {
            handleMultiChoice((CheckBox) view, position);
        }
        if (mOnChangeListener != null)
            mOnChangeListener.onChange(parent, view, position);
    }

    private void handleMultiChoice(CheckBox checkBox, int position) {
        checkBox.toggle(true);
        mOptionWrapper.getOptionAt(position).toggle();
    }

    /**
     * 处理单选时的情况,可能有人会问：<p>
     * 为什么要那么麻烦,为什么不直接循环实体类集合,并将当前单击的item设为true,其他一概设为false,然后刷新适配器即可
     * <p>原因:重新刷新适配器的话，所有的item都需要重新绘制一遍，这样就会加大cup的工作量
     * <p>而通过以下方法的话，只要刷新两个item即可
     */
    private void handleSingleChoice(AdapterView<?> parent, View view, int position) {
        CheckBox checkBox;
        boolean isSet = false;//用于判断有没有将当前单击的item设为true
        int chileCount = parent.getChildCount();//得到item条数
        for (int i = 0; i < chileCount; i++) {
            View v = parent.getChildAt(i);
            checkBox = (CheckBox) v;
            boolean isCheck = checkBox.isChecked();
            if (i == position && !isCheck) {
                //如果是当前单击的item且没有被选中,则设为选中
                checkBox.setChecked(true, true);
                mOptionWrapper.getOptionAt(i).setCheck(true);
                isSet = true;
            } else if (i != position && isCheck) {
                //如果不是当前单击的item且被选中,则设为不选中,即将上次选中的置为false,并结束循环
                checkBox.setChecked(false, true);
                mOptionWrapper.getOptionAt(i).setCheck(false);
                break;
            }
        }
        if (!isSet) {//如果没有将当前单击的item设为true,则直接通过View找到并设为true
            checkBox = (CheckBox) view;
            checkBox.setChecked(true, true);
            mOptionWrapper.getOptionAt(position).setCheck(true);
        }
    }

    /**
     * 设置选中时CheckBox的颜色
     *
     * @param checkedColor 选中时的颜色
     */
    public void setCheckedColor(int checkedColor) {
        mCheckedColor = checkedColor;
    }

    /**
     * @return 选中的item集合
     */
    public List<Option> getChecked() {
        return mOptionWrapper.getChecked();
    }


    /**
     * @return 选中item的文本集合
     */
    public List<CharSequence> getCheckedText() {
        return mOptionWrapper.getCheckedText();
    }

    /**
     * @return 选中item的position 集合
     */
    public List<Integer> getCheckedIndex() {
        return mOptionWrapper.getCheckedIndex();
    }

    public void setShape(int shape) {
        mShape = shape;
    }

    public void setLeftPadding(int leftPadding) {
        this.leftPadding = leftPadding;
    }

    class OptionsAdapter extends BaseAdapter {

        List<Option> mOptions;

        AttributeSet attrs;

        public OptionsAdapter(List<Option> options, AttributeSet attrs) {
            mOptions = options;
            this.attrs = attrs;
        }

        @Override
        public int getCount() {
            return mOptions.size();
        }

        @Override
        public Object getItem(int position) {
            return mOptions.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.checkbox, parent, false);
                convertView.setPadding(leftPadding,convertView.getPaddingTop(),convertView.getPaddingRight(),convertView.getPaddingBottom());
                holder = new ViewHolder(convertView);
                holder.checkBox.setShape(mShape);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Option option = mOptions.get(position);
            if (mCheckedColor != -1)
                holder.checkBox.setCheckedColor(mCheckedColor);
            holder.checkBox.setChecked(option.isCheck());
            holder.checkBox.setText(option.getTitle());
            holder.checkBox.setShape(mShape);
            return convertView;
        }
    }

    class ViewHolder {
        CheckBox checkBox;

        public ViewHolder(View view) {
            this.checkBox = (CheckBox) view;
        }
    }


    /**
     * 外界调用的item监听器
     */
    public OnChangeListener mOnChangeListener;

    interface OnChangeListener {
        void onChange(AdapterView<?> parent, View view, int position);
    }

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        mOnChangeListener = onChangeListener;
    }

}
