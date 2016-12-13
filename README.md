# VersionUpdate

一个 Android 版软件更新功能的工具类




     ![image](https://github.com/smartTop/VersionUpdate/blob/master/screenshots/screenshort1.gif)



## 添加依赖

在`build.gradle` 中：

    dependencies {
        ...
      compile 'com.smartTop:version-update:1.0.1'


    }
    
## 使用方法

   默认布局




    ![image](https://github.com/smartTop/VersionUpdate/blob/master/screenshots/screenshort3.png)


      还可以使用自定义布局

       VersionUpdateDialog instance = VersionUpdateDialog.getInstance();

        //设置自定义布局
        instance.setUpdateView(R.layout.dialog_update);//设置自定义布局

        instance.setDescribe(R.id.tv_dia_version_update);//设置更新的描述

        instance.setCancleOnclick(R.id.btn_cancle);//设置取消监听

        instance.setSureOnclick(R.id.btn_sure);//设置确定监听

        instance.initialize(this);/*版本升级Dialog 【MQ】*/


## 关于我

**smartTop**

- 博客 http://blog.csdn.net/qq_30740239
- gitHub https://github.com/smartTop/AddressSelector
- QQ 1273436145
