# MaterialDialog
仿Android原生的AlertDialog样式的对话框,目的在于解决原生的在Android 5.0以下手机样式丑陋问题。
初次开源项目，如有不足之处，请各位大神多多海涵，多多指导指导

效果图

![image](https://github.com/liujingxing/MaterialDialogApplication/blob/master/screenRecord/screen.gif)

Gradle引用方法

    dependencies {
       compile 'com.android.design:MaterialDialog:1.1.2'
    }




1.显示一个确认对话框

    new MaterialDialog.Builder(this)
            .setTitle("提示")
            .setMessage("仿原生AlertDialog样式的对话框,目的在于解决原生的在Android 5.0下样式丑陋问题")
            .setPositiveButton(null)
            .setNeutralButton("不再提示", null)
            .setNegativeButton(new MaterialDialog.OnClickListener() {
                @Override
                public boolean onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, items[which], Toast.LENGTH_SHORT).show();
                    return false;//默认返回false,返回true Dialog不消失，返回false Dialog消失
                }
            }).show();
                        
2.显示一个列表对话框

    final String[] items = {"2016/01", "2016/02", "2016/03", "2016/04", "2016/05", "2016/06", "2016/07"};
    new MaterialDialog.Builder(this)
            .setItems(items, new MaterialDialog.OnClickListener() {
                @Override
                public boolean onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, items[which], Toast.LENGTH_SHORT).show();
                    return false;//默认返回false,返回true Dialog不消失，返回false Dialog消失
                }
            }).create().show();
                        
3.显示一个单选对话框
 
    final String[] item2 = {"2016/01", "2016/02", "2016/03", "2016/04", "2016/05", "2016/06", "2016/07"};
    new MaterialDialog.Builder(this)
            .setTitle("单选框")
            .setSingleChoiceItems(item2, 2, new MaterialDialog.OnClickListener() {
                @Override
                public boolean onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, item2[which], Toast.LENGTH_SHORT).show();
                    return true;//默认返回false,返回true Dialog不消失，返回false Dialog消失
                            }
            }).setPositiveButton("确定", null)
            .setNegativeButton("取消", null)
            .setNeutralButton("不在提示", null).show();

4.显示一个多选对话框

    new MaterialDialog.Builder(this)
            .setTitle("多选框")
    .setMultiChoiceItems(item1, new int[]{1, 2, 3}, new MaterialDialog.OnMultiChoiceClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
            Toast.makeText(MainActivity.this, which + "--" + isChecked, Toast.LENGTH_SHORT).show();
        }
    }).setPositiveButton("确定", null)
    .setNegativeButton("取消", null)
    .setNeutralButton("不在提示", null).show();

5.自定义对话框显示的view

    new MaterialDialog.Builder(this).setTitle("自定义View")
            .setContentView(view)
            .setLayout(0.8f,0.8f)
            .show();

6.特殊方法介绍

    setLayout(float widthPercent, float heightPercent) 
    通过百分比传一个0~1之间的浮点数设置Dialog的宽高，例如:setLayout(0.8f,0.9f)表示宽为屏幕宽的80%，高为屏幕高90%
    
    setLayout(int width, int height)
    同样设置Dialog的宽高，却别是传入准确的像素值，单位：px 例如setLayout(500,600)表示宽500个像素，高600个像素
    同样可以调用setWidth和setHeight单独设置宽高，同样支持百分比和像素值

    setAdapter(ListAdapter adapter, OnClickListener listener)
    当需要自定义ListView的item时，调用此方法传了一个自己实现的Adapter即可，如还需要监听item的单击事件，传一个OnClickListener即可。

    setSingleChoiceItems(List<? extends CharSequence> items, int checkItem, OnClickListener listener)
    设置单选框列表时，有一系列重载方法，最终都是调用此方法
    items：表示单选列表的集合
    checkItem：表示默认选中item的位置
    listener：单机item时的监听器

    setSCResultButton(OnSCResultListener listener)
    如果不想监听单选对话框单击item的动作，只想获取单选框最后的选择结果，调用此方法传入一个最后结果的监听器，监听方法中会有最终的选择结果

    setMultiChoiceItems(List<? extends CharSequence> items, int[] checkedItems, OnMultiChoiceClickListener listener)
    设置复选框列表时，也有一系列重载方法，最终都是调用此方法
    items：表示复选列表的集合
    checkedItems：默认选择item的位置的集合
    listener：单机item时的监听器
    
    setMCResultButton(OnMCResultListener listener)
    如果不想监听复选对话框单击item的动作，只想获取复选框最后的选择结果，调用此方法传入一个最后结果的监听器，监听方法中会返回选择的列表集合
           
    

部分灵感来源：https://github.com/drakeet/MaterialDialog

单选/复选框引用：https://github.com/andyxialm/SmoothCheckBox  并加入了自己的修改


