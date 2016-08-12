package com.common.design.entity;

/**
 * 选项实体类
 *
 * @author liujingxing on 16/7/24.
 */
public class Option {
    private CharSequence title;
    private boolean isCheck;

    private boolean enabled = true;

    public Option() {
    }

    public Option(CharSequence title) {
        this.title = title;
    }

    public Option(CharSequence title, boolean isCheck) {
        this.title = title;
        this.isCheck = isCheck;
    }

    public Option(boolean enabled, boolean isCheck, CharSequence title) {
        this.enabled = enabled;
        this.isCheck = isCheck;
        this.title = title;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public void toggle() {
        isCheck = !isCheck;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public CharSequence getTitle() {
        return title;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
    }
}
