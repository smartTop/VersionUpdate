package com.smarttop.library.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.smarttop.library.R;
import com.smarttop.library.callback.CallBackForT;
import com.smarttop.library.utils.NetworkProber;
import com.smarttop.library.utils.OkUtils;

import java.io.IOException;

import okhttp3.Response;

/**
 * @auther smartTop
 * @date 2016/9/7 18:51
 * @qq 1273436145
 * @describe 版本更新Dialog
 */

public class VersionUpdateDialog extends BaseDialog implements BaseDialog.OnCreateView, View.OnClickListener {

    private Context mContext;

    private static VersionUpdateDialog instance = null;
    private TextView tv_Prompt;
    private Button btn_Cancel, btn_Sure;
    private String mPrompt = "";
    private String str_Url = "";
    private OkUtils mOkUtils = null;
    private int mForceUp = -1;
    private int resId;

    public VersionUpdateDialog() {
    }

    public static VersionUpdateDialog getInstance() {
        if (instance == null) {
            instance = new VersionUpdateDialog();
        }
        return instance;
    }

    public void initialize(Context context) {
        mContext = context;
        setUpDialog();
    }

    private void setUpDialog() {
        setWindowAnimations(R.style.AnimUpInDownOut);
        if(resId ==0){
            setContentView(R.layout.dialog_version_update, this);
        }else{
            setContentView(resId, this);
        }
        setGravity(Gravity.CENTER);
        setDimAmount(0.4f);
    }

    /**
     * 设置更新的自定义布局
     */
    public void setUpdateView(int resId){
        this.resId = resId;
    }
    @Override
    public void onCreateViewOk(View view) {
        tv_Prompt = (TextView) view.findViewById(R.id.tv_dia_version_update);
        btn_Cancel = (Button) view.findViewById(R.id.btn_dia_is_close_update_cancel);
        btn_Sure = (Button) view.findViewById(R.id.btn_dia_is_close_update_sure);
        mOkUtils = new OkUtils(getActivity(), tv_Prompt);
        tv_Prompt.setText(mPrompt);
        btn_Sure.setOnClickListener(this);
        btn_Cancel.setOnClickListener(this);
        //强制更新
        if (mForceUp == 3) {
            btn_Cancel.setVisibility(View.GONE);
            btn_Sure.setGravity(Gravity.CENTER);
            this.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                    return false;
                }
            });
        }
        instance.setCanceledOnTouchOutSide(false);
    }

    /**
     * 展示Dialog
     *
     * @param url
     * @param prompt
     */
    public void showDialog(String url, String prompt, int forceUp) {
        super.showDialog((FragmentActivity) mContext);
        str_Url = url;
        mPrompt = prompt;
        mForceUp = forceUp;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_dia_is_close_update_cancel) {
            this.dismiss();
        } else if (id == R.id.btn_dia_is_close_update_sure) {
            if (NetworkProber.getInstance().isNetworkAvailable(getActivity())) {
                btn_Sure.setClickable(false);
                btn_Cancel.setClickable(false);

                mOkUtils.downFile(str_Url, new CallBackForT<Response>() {
                    @Override
                    public void finish(Response response) {
                        try {
                            mOkUtils.updataProgress(response);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                Toast.makeText(getActivity(), "网络断开,请检查网络状态！", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
