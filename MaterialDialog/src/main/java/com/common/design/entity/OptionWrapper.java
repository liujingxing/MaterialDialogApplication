package com.common.design.entity;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 选项集合的封装类,默认多选
 *
 * @author liujingxing on 16/7/24.
 */
public class OptionWrapper {
    /**
     * 选项集合
     */
    private List<Option> mOptions = new ArrayList<>();

    private boolean enabled = true;

    /**
     * 是否为单选
     */
    private boolean isSingleChoice;


    public OptionWrapper() {
    }

    public OptionWrapper(boolean isSingleChoice) {
        this.isSingleChoice = isSingleChoice;
    }

    public void set(OptionWrapper wrapper) {
        mOptions.clear();
        mOptions.addAll(wrapper.getOptions());
        enabled = wrapper.isEnabled();
        isSingleChoice = wrapper.isSingleChoice;
    }

    /**
     * 设置选项和默认选中
     *
     * @param options   选项集合
     * @param positions 选中item的position集合
     */
    public void setOptionsAndChecked(List<? extends CharSequence> options, Integer... positions) {
        setOptions(options);
        setChecked(positions);
    }

    /**
     * 设置选项
     *
     * @param options 选项集合
     */
    public void setOptions(CharSequence... options) {
        if (options == null || options.length == 0) return;
        setOptions(Arrays.asList(options));
    }

    /**
     * 设置选项
     *
     * @param options 选项集合
     */
    public void setOptions(List<? extends CharSequence> options) {
        if (options == null || options.size() == 0) return;
        Option option;
        for (CharSequence title : options) {
            if (TextUtils.isEmpty(title)) continue;
            option = new Option(title);
            mOptions.add(option);
        }
    }

    /**
     * 设置默认选中的item<br>
     * 注:此方法一定要在setOptions之后调用才会有效<br>
     * 单选时,默认取第一个有效值做为默认的选中
     *
     * @param positions 选中item的position集合
     */
    public void setChecked(Integer... positions) {
        if (positions == null || positions.length == 0) return;
        setChecked(Arrays.asList(positions));
    }

    /**
     * 设置默认选中的item<br>
     * 注:此方法一定要在setOptions之后调用才会有效<br>
     * 单选时,默认取第一个有效值做为默认的选中
     *
     * @param positions 选中item的position集合
     */
    public void setChecked(List<Integer> positions) {
        if (positions == null || positions.size() == 0) return;
        for (int position : positions) {
            //当用户传过来的position大于等于集合的长度时,认为是无效值
            if (position >= mOptions.size()) continue;
            mOptions.get(position).setCheck(true);
            if (isSingleChoice) break;
        }
    }

    /**
     * @return 所有选项
     */
    public List<Option> getOptions() {
        return mOptions;
    }

    /**
     * 获取指定position的选项
     *
     * @param position 位置
     * @return position对应的选项
     */
    public Option getOptionAt(int position) {
        return mOptions.get(position);
    }

    /**
     * @return 选中item的集合
     */
    public List<Option> getChecked() {
        List<Option> options = new ArrayList<>();
        for (Option option : mOptions) {
            if (option == null || !option.isCheck()) continue;
            options.add(option);
        }
        return options;
    }

    /**
     * @return 选中item的文本集合
     */
    public List<CharSequence> getCheckedText() {
        List<CharSequence> titles = new ArrayList<>();
        for (Option option : mOptions) {
            if (option == null || !option.isCheck()) continue;
            titles.add(option.getTitle());
        }
        return titles;
    }

    /**
     * @return 选中item的position集合
     */
    public List<Integer> getCheckedIndex() {
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < mOptions.size(); i++) {
            Option option = mOptions.get(i);
            if (option == null || !option.isCheck()) continue;
            positions.add(i);
        }
        return positions;
    }


    public void setEnabled(boolean enabled, int index) {
        mOptions.get(index).setEnabled(enabled);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isSingleChoice() {
        return isSingleChoice;
    }
}
