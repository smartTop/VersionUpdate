package com.smarttop.versionupdate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.smarttop.library.Effectstype;
import com.smarttop.library.dialog.NiftyDialogBuilder;
import com.smarttop.versionupdate.bean.VersionUpdateBean;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Effectstype effect;
    private NiftyDialogBuilder dialogBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.fadein).setOnClickListener(this);
        findViewById(R.id.slideright).setOnClickListener(this);
        findViewById(R.id.slideleft).setOnClickListener(this);
        findViewById(R.id.slidetop).setOnClickListener(this);
        findViewById(R.id.slideBottom).setOnClickListener(this);
        findViewById(R.id.newspager).setOnClickListener(this);
        findViewById(R.id.fall).setOnClickListener(this);
        findViewById(R.id.sidefall).setOnClickListener(this);
        findViewById(R.id.fliph).setOnClickListener(this);
        findViewById(R.id.flipv).setOnClickListener(this);
        findViewById(R.id.rotatebottom).setOnClickListener(this);
        findViewById(R.id.rotateleft).setOnClickListener(this);
        findViewById(R.id.slit).setOnClickListener(this);
        findViewById(R.id.shake).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fadein:
                effect = Effectstype.Fadein;
                break;
            case R.id.slideright:
                effect = Effectstype.Slideright;
                break;
            case R.id.slideleft:
                effect = Effectstype.Slideleft;
                break;
            case R.id.slidetop:
                effect = Effectstype.Slidetop;
                break;
            case R.id.slideBottom:
                effect = Effectstype.Slidebottom;
                break;
            case R.id.newspager:
                effect = Effectstype.Newspager;
                break;
            case R.id.fall:
                effect = Effectstype.fall;
                break;
            case R.id.sidefall:
                effect = Effectstype.Sidefill;
                break;
            case R.id.fliph:
                effect = Effectstype.Fliph;
                break;
            case R.id.flipv:
                effect = Effectstype.Flipv;
                break;
            case R.id.rotatebottom:
                effect = Effectstype.Rotatebottom;
                break;
            case R.id.rotateleft:
                effect = Effectstype.Rotateleft;
                break;
            case R.id.slit:
                effect = Effectstype.slit;
                break;
            case R.id.shake:
                effect = Effectstype.shake;
                break;
        }
        reqData();
    }
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
                        .setForceUp(2)                  //是否强制更新
                        .setForceUpFlag(3)              //强制更新的标志
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

}
