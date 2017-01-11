# VersionUpdate

一个 Android 版软件更新功能的工具类
     ![image](https://github.com/smartTop/VersionUpdate/blob/master/screenshots/screenshort1.gif)

## 添加依赖

在`build.gradle` 中：

    dependencies {
        ...
    compile 'com.smarttop:android-version-update:1.0.2'


    }
    
## 使用方法

       需要先请求服务器，获取版本更新的内容以及是否强制更新
         private void reqData(){
                       //这里是测试数据，在真正的项目中这些数据都是后台给配置的
                       VersionUpdateBean versionUpdateBean = new VersionUpdateBean();
                       versionUpdateBean.versionStatus = "2";//1:不需要更新 2：建议更新 3：强制更新
       //              versionUpdateBean.versionStatus = "3";//强制更新
                       versionUpdateBean.versionDesc = "请关注:\n1.smartTop的github\n 2.记得点赞"; //描述
                       versionUpdateBean.url = "http://jifenshangcheng-test.oss-cn-beijing.aliyuncs.com/app/android/naiping_2016-12-27-01.apk";
                       setVersionInfo(versionUpdateBean);
           }
            public void setVersionInfo(VersionUpdateBean obj) {
                   int status = -1;
                   if (obj != null) {
           //			1:不需要更新 2：建议更新 3：强制更新
                       if (obj.versionStatus != null) {
                           status = Integer.parseInt(obj.versionStatus);
                       }
                       if (status == 2 || status == 3) {
                          dialogBuilder=NiftyDialogBuilder.getInstance(MainActivity.this);
                           dialogBuilder
                                   .withDuration(700)              //时间
                                   .withEffect(effect)             //动画
                                   .withMessage(obj.versionDesc)   //描述
                                   .withButton1Text("暂不更新")     //按钮1
                                   .withButton2Text("软件更新")     //按钮2
                                   .setForceUp(status)                  //是否强制更新
                                   .setForceUpFlag(status)              //强制更新的标志
                                   .withButton3Text("软件更新")     //强制更新的文字
                                   .withUrl(obj.url)               //更新软件的地址
           //                        .setCustomView(R.layout.custom_view,this) //自定义布局
                                   .setButton1Click(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Toast.makeText(v.getContext(), "i'm btn1", Toast.LENGTH_SHORT).show();
                                           dialogBuilder.dismiss();
                                       }
                                   })
                                   .setButton2Click(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Toast.makeText(v.getContext(), "i'm btn2", Toast.LENGTH_SHORT).show();
                                           dialogBuilder.updateApp();
                                       }
                                   })
                                   .setButton3Click(new View.OnClickListener(){

                                       @Override
                                       public void onClick(View v) {
                                           Toast.makeText(v.getContext(), "i'm btn3", Toast.LENGTH_SHORT).show();
                                           dialogBuilder.updateApp();
                                       }
                                   })
                                   .show();

                       }
                   }

               }





## 关于我

**smartTop**

- 博客 http://blog.csdn.net/qq_30740239
- gitHub https://github.com/smartTop/VersionUpdate
- QQ 1273436145
