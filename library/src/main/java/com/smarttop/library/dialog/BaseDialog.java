package com.smarttop.library.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.smarttop.library.Effectstype;
import com.smarttop.library.R;
import com.smarttop.library.effects.BaseEffects;
import com.smarttop.library.utils.LogUtil;


/**
 * @auther smartTop
 * @date 2016/9/7 15:57
 * @qq 1273436145
 * @describe Dialog弹框基本类
 */
public class BaseDialog extends DialogFragment implements DialogInterface.OnDismissListener, DialogInterface.OnCancelListener {

    private static final String TAG = BaseDialog.class.getSimpleName();

    private View contentView;
    private int resId;
    public Dialog mDialog;
    private int _width, _height, _gravity;
    private float dimAmount = -1;
    private Effectstype type;
    private DialogInterface.OnDismissListener onDismissListener = null;

    private double dialog_width_ratio;
    private double dialog_height_ratio;
    private int mDuration = -1;
    private View rootView;
    private Window window;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(this.resId, container, false);
        if (onCreateView != null) {
            onCreateView.onCreateViewOk(contentView);
        }
        return contentView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDialog = new Dialog(getActivity(), R.style.Dialog);
        return mDialog;
    }


    @Override
    public void onResume() {
        super.onResume();

        //设置参数
        window = mDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        // 设置动画效果
//        if(type==null){
//            LogUtil.d("数据","type等于空...");
//            window.setWindowAnimations(R.style.AnimUpInDownOut);
//        }else{
//            LogUtil.d("数据","type不等于空...");
//            //监听对话框开始显示的事件，然后设置动画类型，之后调用start方法将动画效果运用到dialog的contentview中：
//        if(mDialog!=null){
//            LogUtil.d("数据","mDialog不等于空...");
//            mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//                @Override
//                public void onShow(DialogInterface dialogInterface) {
////                if (type == null) {
////                    type = Effectstype.Slidetop;
////                }
//
//                    if(type!=null){
//                        LogUtil.d("数据","type="+type);
//                        start(type);
//                    }
//                }
//            });
//        }
//        }
        //设置宽度
        setWidthRatio(this.dialog_width_ratio == 0 ? 0.85 : this.dialog_width_ratio);
        lp.width = this._width;

        //设置高度
        if (this.dialog_height_ratio > 0) {
            setHeightRatio(this.dialog_height_ratio);
            lp.height = this._height;
        } else if (this.dialog_height_ratio == -2) {
            lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
        } else {
            lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        }

        //设置位置
        if (_gravity != 0) {
            lp.gravity = _gravity;
        }

        if (dimAmount != -1) {
            lp.dimAmount = dimAmount;
        }

        window.setAttributes(lp);

    }
    private void start(Effectstype type) {
        BaseEffects animator = type.getAnimator();
        LogUtil.d("数据","rootView="+rootView);
        if (mDuration != -1) {
            animator.setDuration(Math.abs(mDuration));
        }
        animator.start(rootView);
    }
    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    /**
     * 显示Dialog
     *
     * @param context
     */
    public void showDialog(FragmentActivity context) {
        this.show(context.getSupportFragmentManager(), "");
        // 设置动画效果
        if(type==null){
            LogUtil.d("数据","type等于空...");
            window.setWindowAnimations(R.style.AnimUpInDownOut);
        }else{
            LogUtil.d("数据","type不等于空...");
            //监听对话框开始显示的事件，然后设置动画类型，之后调用start方法将动画效果运用到dialog的contentview中：
            if(mDialog!=null){
                LogUtil.d("数据","mDialog不等于空...");
                mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
//                if (type == null) {
//                    type = Effectstype.Slidetop;
//                }

                        if(type!=null){
                            LogUtil.d("数据","type="+type);
                            start(type);
                        }
                    }
                });
            }
        }
    }


    public void setCanceledOnTouchOutSide(boolean cancle) {
        if (mDialog != null) {
            mDialog.setCanceledOnTouchOutside(cancle);
        }
    }


    /**
     * 创建UI后的回调事件
     */
    private OnCreateView onCreateView;

    public interface OnCreateView {
        void onCreateViewOk(View view);
    }

    /**
     * 设置资源
     *
     * @param resId
     */
    public void setContentView(int resId, OnCreateView onCreateView) {
        this.resId = resId;
        this.onCreateView = onCreateView;
    }

    /**
     * 获取资源对象
     *
     * @return
     */
    public View getContentView() {
        return contentView;
    }

    /**
     * 查找组件
     *
     * @param id
     * @return
     */
    public View findViewById(int id) {
        return contentView.findViewById(id);
    }

    /**
     * 设置透明度dialog的背景透明度
     *
     * @param dimAmount 0f:完全透明；1f:完全不透明
     */
    public void setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
    }


    /**
     * 设置显示位置
     *
     * @param gravity Gravity.BOTTOM  or  Gravity.TOP  or Gravity.CENTER
     */
    public void setGravity(int gravity) {
        this._gravity = gravity;
    }

    /**
     * 设置运行动画
     *
     */
    public void setWindowAnimations(Effectstype type) {
        this.type = type;
    }
    public void setRootView(View rootView){
        this.rootView = rootView;
    }
    /**
     * 设置Dialog的屏幕占比
     *
     * @param dialog_width_ratio
     * @param dialog_height_ratio
     * @return
     */
    public void setDialogSizeRatio(double dialog_width_ratio, double dialog_height_ratio) {
        this.dialog_width_ratio = dialog_width_ratio;
        this.dialog_height_ratio = dialog_height_ratio;
    }

    /**
     * 设置宽度屏占比
     *
     * @param dialog_width_ratio
     */
    public void setDialogWidthSizeRatio(double dialog_width_ratio) {
        this.dialog_width_ratio = dialog_width_ratio;
    }

    /**
     * 设置宽度屏占比
     *
     * @param dialog_width_ratio
     */
    private void setWidthRatio(double dialog_width_ratio) {
        DisplayMetrics dm = getDisplayMetrics();
        _width = (int) (dm.widthPixels * dialog_width_ratio);
    }

    /**
     * 设置高度屏占比
     *
     * @param dialog_height_ratio
     */
    private void setHeightRatio(double dialog_height_ratio) {
        DisplayMetrics dm = getDisplayMetrics();
        _height = (int) (dm.heightPixels * dialog_height_ratio);
    }

    /**
     * 获取宽高对象
     *
     * @return
     */
    private DisplayMetrics getDisplayMetrics() {
        WindowManager windowManager = getActivity().getWindow().getWindowManager();
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public void setOnDissmissLintener(DialogInterface.OnDismissListener onDissmissLintener) {
        this.onDismissListener = onDissmissLintener;
    }
}
