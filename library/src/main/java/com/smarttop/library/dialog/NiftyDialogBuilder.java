package com.smarttop.library.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smarttop.library.Effectstype;
import com.smarttop.library.R;
import com.smarttop.library.callback.CallBackForT;
import com.smarttop.library.effects.BaseEffects;
import com.smarttop.library.utils.ColorUtils;
import com.smarttop.library.utils.LogUtil;
import com.smarttop.library.utils.NetworkProber;
import com.smarttop.library.utils.OkUtils;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by smartTop on 2017/1/5.
 */

public class NiftyDialogBuilder extends Dialog implements DialogInterface {
    private final String defTextColor = "#FFFFFFFF";

    private final String defDividerColor = "#11000000";

    private final String defMsgColor = "#FFFFFFFF";

    private final String defDialogColor = "#FFE74C3C";


    private static Context tmpContext;


    private Effectstype type = null;

    private LinearLayout mLinearLayoutView;

    private RelativeLayout mRelativeLayoutView;

    private LinearLayout mLinearLayoutMsgView;

    private LinearLayout mLinearLayoutTopView;

    private FrameLayout mFrameLayoutCustomView;

    private View mDialogView;

    private View mDivider;

    private TextView mTitle;

    private TextView mMessage;

    private ImageView mIcon;

    private Button mButton1;

    private Button mButton2;
    private Button mButton3;

    private int mDuration = -1;

    private static int mOrientation = 1;

    private boolean isCancelable = true;

    private static NiftyDialogBuilder instance;
    private int mForceUp;
    private int mForceUpFlag;
    private OkUtils mOkUtils = null;
    private String str_Url = "";

    public NiftyDialogBuilder(Context context) {
        super(context);
        init(context);

    }

    public NiftyDialogBuilder(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

    }

    public static NiftyDialogBuilder getInstance(Context context) {

        if (instance == null || !tmpContext.equals(context)) {
            synchronized (NiftyDialogBuilder.class) {
                if (instance == null || !tmpContext.equals(context)) {
                    instance = new NiftyDialogBuilder(context, R.style.dialog_untran);
                }
            }
        }
        tmpContext = context;
        return instance;

    }

    private void init(Context context) {

        mDialogView = View.inflate(context, R.layout.dialog_version_update, null);

        mLinearLayoutView = (LinearLayout) mDialogView.findViewById(R.id.parentPanel);
        mRelativeLayoutView = (RelativeLayout) mDialogView.findViewById(R.id.main);
        mLinearLayoutTopView = (LinearLayout) mDialogView.findViewById(R.id.topPanel);
        mLinearLayoutMsgView = (LinearLayout) mDialogView.findViewById(R.id.contentPanel);
        mFrameLayoutCustomView = (FrameLayout) mDialogView.findViewById(R.id.customPanel);

        mTitle = (TextView) mDialogView.findViewById(R.id.alertTitle);
        mMessage = (TextView) mDialogView.findViewById(R.id.message);
//        mIcon = (ImageView) mDialogView.findViewById(R.id.icon);
//        mDivider = mDialogView.findViewById(R.id.titleDivider);
        mButton1 = (Button) mDialogView.findViewById(R.id.button1);
        mButton2 = (Button) mDialogView.findViewById(R.id.button2);
        mButton3 = (Button) mDialogView.findViewById(R.id.button3);

        setContentView(mDialogView);
        mOkUtils = new OkUtils(context, mMessage);
        this.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                //强制更新
                if (mForceUp == mForceUpFlag) {
                    mButton1.setVisibility(View.GONE);
                    mButton2.setVisibility(View.GONE);
                    mButton3.setVisibility(View.VISIBLE);
                    setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                return true;
                            }
                            return false;
                        }
                    });
                }else {
                    mButton1.setVisibility(View.VISIBLE);
                    mButton2.setVisibility(View.VISIBLE);
                    mButton3.setVisibility(View.GONE);
                }
                if (type == null) {
                    type = Effectstype.Slidetop;
                }
                start(type);


            }
        });
        mRelativeLayoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCancelable) dismiss();
            }
        });
    }

    public void toDefault() {
        mTitle.setTextColor(Color.parseColor(defTextColor));
        mDivider.setBackgroundColor(Color.parseColor(defDividerColor));
        mMessage.setTextColor(Color.parseColor(defMsgColor));
        mLinearLayoutView.setBackgroundColor(Color.parseColor(defDialogColor));
    }

//    public NiftyDialogBuilder withDividerColor(String colorString) {
//        mDivider.setBackgroundColor(Color.parseColor(colorString));
//        return this;
//    }
//
//    public NiftyDialogBuilder withDividerColor(int color) {
//        mDivider.setBackgroundColor(color);
//        return this;
//    }


    public NiftyDialogBuilder withTitle(CharSequence title) {
        toggleView(mLinearLayoutTopView, title);
        mTitle.setText(title);
        return this;
    }

    public NiftyDialogBuilder withTitleColor(String colorString) {
        mTitle.setTextColor(Color.parseColor(colorString));
        return this;
    }

    public NiftyDialogBuilder withTitleColor(int color) {
        mTitle.setTextColor(color);
        return this;
    }

    public NiftyDialogBuilder withMessage(int textResId) {
        toggleView(mLinearLayoutMsgView, textResId);
        mMessage.setText(textResId);
        return this;
    }

    public NiftyDialogBuilder withMessage(CharSequence msg) {
        toggleView(mLinearLayoutMsgView, msg);
        mMessage.setText(msg);
        return this;
    }

    public NiftyDialogBuilder withMessageColor(String colorString) {
        mMessage.setTextColor(Color.parseColor(colorString));
        return this;
    }

    public NiftyDialogBuilder withMessageColor(int color) {
        mMessage.setTextColor(color);
        return this;
    }

    public NiftyDialogBuilder withDialogColor(String colorString) {
        mLinearLayoutView.getBackground().setColorFilter(ColorUtils.getColorFilter(Color
                .parseColor(colorString)));
        return this;
    }

    public NiftyDialogBuilder withDialogColor(int color) {
        mLinearLayoutView.getBackground().setColorFilter(ColorUtils.getColorFilter(color));
        return this;
    }

//    public NiftyDialogBuilder withIcon(int drawableResId) {
//        mIcon.setImageResource(drawableResId);
//        return this;
//    }
//
//    public NiftyDialogBuilder withIcon(Drawable icon) {
//        mIcon.setImageDrawable(icon);
//        return this;
//    }

    public NiftyDialogBuilder withDuration(int duration) {
        this.mDuration = duration;
        return this;
    }

    public NiftyDialogBuilder withEffect(Effectstype type) {
        this.type = type;
        return this;
    }

    public NiftyDialogBuilder withButton1Drawable(int resid) {
        mButton1.setBackgroundResource(resid);
        return this;
    }
    public NiftyDialogBuilder withButton2Drawable(int resid) {
        mButton2.setBackgroundResource(resid);
        return this;
    }
    public NiftyDialogBuilder withButton3Drawable(int resid) {
        mButton3.setBackgroundResource(resid);
        return this;
    }


    public NiftyDialogBuilder withButton1Text(CharSequence text) {
        mButton1.setVisibility(View.VISIBLE);
        mButton1.setText(text);

        return this;
    }

    public NiftyDialogBuilder withButton2Text(CharSequence text) {
        mButton2.setVisibility(View.VISIBLE);
        mButton2.setText(text);
        return this;
    }
    public NiftyDialogBuilder withButton3Text(CharSequence text) {
        mButton3.setVisibility(View.VISIBLE);
        mButton3.setText(text);
        return this;
    }

    public NiftyDialogBuilder setButton1Click(View.OnClickListener click) {
        mButton1.setOnClickListener(click);
        return this;
    }

    public NiftyDialogBuilder setButton2Click(View.OnClickListener click) {
        mButton2.setOnClickListener(click);
        return this;
    }
    public NiftyDialogBuilder setButton3Click(View.OnClickListener click) {
        mButton3.setOnClickListener(click);
        return this;
    }
    public NiftyDialogBuilder withUrl(String url){
        this.str_Url = url;
        LogUtil.d("数据","。。。"+str_Url);
        return this;
    }
    public NiftyDialogBuilder setCustomView(int resId, Context context) {
        View customView = View.inflate(context, resId, null);
        if (mFrameLayoutCustomView.getChildCount() > 0) {
            mFrameLayoutCustomView.removeAllViews();
        }
        mFrameLayoutCustomView.addView(customView);
        mFrameLayoutCustomView.setVisibility(View.VISIBLE);
        mLinearLayoutView.setVisibility(View.GONE);
        return this;
    }

    public NiftyDialogBuilder setCustomView(View view) {
        if (mFrameLayoutCustomView.getChildCount() > 0) {
            mFrameLayoutCustomView.removeAllViews();
        }
        mFrameLayoutCustomView.addView(view);
        mFrameLayoutCustomView.setVisibility(View.VISIBLE);
        mLinearLayoutView.setVisibility(View.GONE);
        return this;
    }

    public NiftyDialogBuilder isCancelableOnTouchOutside(boolean cancelable) {
        this.isCancelable = cancelable;
        this.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    public NiftyDialogBuilder isCancelable(boolean cancelable) {
        this.isCancelable = cancelable;
        this.setCancelable(cancelable);
        return this;
    }
    public NiftyDialogBuilder setForceUp(int mForceUp){
        this.mForceUp = mForceUp;
        return this;
    }
    public NiftyDialogBuilder setForceUpFlag(int mForceUpFlag){
        this.mForceUpFlag = mForceUpFlag;
        return this;
    }
    private void toggleView(View view, Object obj) {
        if (obj == null) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void show() {
        super.show();
    }

    private void start(Effectstype type) {
        BaseEffects animator = type.getAnimator();
        if (mDuration != -1) {
            animator.setDuration(Math.abs(mDuration));
        }
        animator.start(mRelativeLayoutView);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mButton1.setVisibility(View.GONE);
        mButton2.setVisibility(View.GONE);
        mButton3.setVisibility(View.GONE);
    }
    /**
     * 更新App
     */
    public void updateApp(){
        if (NetworkProber.getInstance().isNetworkAvailable(tmpContext)) {
            mButton1.setClickable(false);
            mButton2.setClickable(false);
            mButton3.setClickable(false);
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
            Toast.makeText(tmpContext, "网络断开,请检查网络状态！", Toast.LENGTH_SHORT).show();
        }
    }
}
