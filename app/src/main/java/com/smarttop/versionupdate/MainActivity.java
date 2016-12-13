package com.smarttop.versionupdate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.smarttop.library.dialog.VersionUpdateDialog;
import com.smarttop.versionupdate.bean.VersionUpdateBean;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_update = (TextView) findViewById(R.id.tv_update);
        tv_update.setOnClickListener(this);
        VersionUpdateDialog instance = VersionUpdateDialog.getInstance();
        //设置自定义布局
//        instance.setUpdateView(R.layout.dialog_update);//设置自定义布局
//          instance.setDescribe(R.id.tv_dia_version_update);//设置更新的描述
//        instance.setCancleOnclick(R.id.btn_cancle);//设置取消监听
//        instance.setSureOnclick(R.id.btn_sure);//设置确定监听
        instance.initialize(this);/*版本升级Dialog 【MQ】*/


    }

    @Override
    public void onClick(View view) {
        //这里是测试数据，在真正的项目中这些数据都是后台给配置的
        VersionUpdateBean versionUpdateBean = new VersionUpdateBean();
        versionUpdateBean.versionStatus = "2";//1:不需要更新 2：建议更新 3：强制更新
//        versionUpdateBean.versionStatus = "3";//强制更新
        versionUpdateBean.versionDesc = "请关注:\n1.smartTop的github\n 2.记得点赞"; //描述
        versionUpdateBean.url = "http://192.168.5.190:8080/versionupdate.apk";
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
                VersionUpdateDialog.getInstance().showDialog(obj.url, obj.versionDesc, status);
            }
        }

    }
}
