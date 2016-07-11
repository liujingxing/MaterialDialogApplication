# MaterialDialog
仿Android原生的AlertDialog样式的对话框,目的在于解决原生的在Android 5.0以下手机样式丑陋问题。
初次开源项目，如有不足之处，请各位大神多多海涵，多多指导指导

效果图

![image](https://github.com/liujingxing/MaterialDialogApplication/tree/master/screenRecord/screen.gif)

<!--Gradle引用方法-->

<!--    dependencies {-->
<!--       compile 'com.common.design:Library:1.0.0'-->
<!--    }-->



1.显示一个确认对话框

    new MaterialDialog.Builder(this)
            .setTitle("提示")
            .setMessage("仿原生AlertDialog样式的对话框,目的在于解决原生的在Android 5.0下样式丑陋问题")
            .setPositiveButton(null)
            .setNeutralButton("不再提示", null)
            .setNegativeButton(null).show();
                        
2.显示一个列表对话框

    final String[] items = {"2016/01", "2016/02", "2016/03", "2016/04", "2016/05", "2016/06", "2016/07"};
    new MaterialDialog.Builder(this)
            .setItems(items, new MaterialDialog.OnClickListener() {
                @Override
                public boolean onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, items[which], Toast.LENGTH_SHORT).show();
                    return false;
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
                    return true;
                            }
            }).setPositiveButton("确定", null)
            .setNegativeButton("取消", null)
            .setNeutralButton("不在提示", null).show();

4.显示一个多选对话框

    final String[] item1 = {"2016/01", "2016/02", "2016/03", "2016/04", "2016/05", "2016/06", "2016/07"};
    new MaterialDialog.Builder(this)
            .setTitle("多选框")
            .setMultiChoiceItems(item1, new boolean[]{false, true, false, true}, new MaterialDialog.OnMultiChoiceClickListener() {
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
    

部分灵感来源：https://github.com/drakeet/MaterialDialog

单选/复选框引用：https://github.com/andyxialm/SmoothCheckBox  并加入了自己的修改


