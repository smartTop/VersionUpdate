# VersionUpdate

一个 Android 版软件更新功能的工具类
     ![image](https://github.com/smartTop/VersionUpdate/blob/master/screenshots/screenshort1.gif)

## 添加依赖

在`build.gradle` 中：

    dependencies {
        ...
      compile 'com.smartTop:version-update:1.0.0'


    }
    
## 使用方法

   默认布局
    ![image](https://github.com/smartTop/VersionUpdate/blob/master/screenshots/screenshort3.png)




        使用默认布局
         VersionUpdateDialog.getInstance().initialize(this);

      还可以使用自定义布局

       VersionUpdateDialog instance = VersionUpdateDialog.getInstance();

        //设置自定义布局
        instance.setUpdateView(R.layout.dialog_update);//设置自定义布局

        instance.setDescribe(R.id.tv_dia_version_update);//设置更新的描述

        instance.setCancleOnclick(R.id.btn_cancle);//设置取消监听

        instance.setSureOnclick(R.id.btn_sure);//设置确定监听

        instance.initialize(this);/*版本升级Dialog 【MQ】*/

        弹出对话框
            弹出对话框之前先请求服务器，获取弹出内容
              //这里是测试数据，在真正的项目中这些数据都是后台给配置的
                    VersionUpdateBean versionUpdateBean = new VersionUpdateBean();

                    versionUpdateBean.versionStatus = "2";//1:不需要更新 2：建议更新 3：强制更新

            //      versionUpdateBean.versionStatus = "3";//强制更新

                    versionUpdateBean.versionDesc = "请关注:\n1.smartTop的github\n 2.记得点赞"; //描述

                    versionUpdateBean.url = "http://192.168.5.190:8080/versionupdate.apk";

                    setVersionInfo(versionUpdateBean);

         public void setVersionInfo(VersionUpdateBean obj) {

                int status = -1;

                if (obj != null) {

                //1:不需要更新 2：建议更新 3：强制更新
                if (obj.versionStatus != null) {

                    status = Integer.parseInt(obj.versionStatus);

                }

                if (status == 2 || status == 3) {

                    VersionUpdateDialog.getInstance().showDialog(obj.url, obj.versionDesc, status);

                }
        }



## 关于我

**smartTop**

- 博客 http://blog.csdn.net/qq_30740239
- gitHub https://github.com/smartTop/VersionUpdate
- QQ 1273436145
