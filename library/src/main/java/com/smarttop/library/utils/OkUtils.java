package com.smarttop.library.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.smarttop.library.callback.CallBackForT;
import com.smarttop.library.dialog.VersionUpdateDialog;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * @auther smartTop
 * @date 2016/4/27 0027 18:15
 * @qq 1273436145
 * @describe 文件下载工具类 基于okhttp
 */
public class OkUtils {
    private Context activity;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    private TextView textView;
    private String tmpFileSize;//已经下载的文件的大小 显示的在下载量的地方 10M/15M
    private int progress;
    private long size;
    private Handler mHandler;
    private ProgressDialog mProgressDlg;
    private String mAppName; // 下载到本地给这个APP命名
    private Thread downLoadThread;
    private Runnable mdownApkRunnable;
    private static final String savePath = FileUtil.SDPATH + File.separator + FileUtil.PATH + File.separator;
    private static final String saveFileName = savePath+ "versionupdate.apk";
    private boolean interceptFlag = false;
    private String str_Url = "";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE://tmpFileSize + "/" + size
                    textView.setText(progress + "%");
                    break;
                case DOWN_OVER:
                    down();
                    break;
            }
        }
    };

    public OkUtils(Context activity, TextView textView) {
        this.activity = activity;
        this.textView = textView;
        mHandler = new Handler();
        mProgressDlg = new ProgressDialog(activity);
        mProgressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDlg.setIndeterminate(false);
        mAppName = "versionupdate.apk";
    }

    public void downFile(String url) {
        mProgressDlg.setTitle("正在下载");
        mProgressDlg.setMessage("请稍候...");
        //设置点击进度对话框外的区域对话框不消失
        mProgressDlg.setCanceledOnTouchOutside(false);
        mProgressDlg.show();
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
        downloadApk(url);
    }

    public void downloadApk(final String downUrl) {
        mdownApkRunnable = new Runnable() {

            public void run() {
                try {

                    URL url = new URL(downUrl);

                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.connect();
                    int length = conn.getContentLength();
                    mProgressDlg.setMax((int) length);// 设置进度条的最大值
                    InputStream is = conn.getInputStream();

                    File file = new File(savePath);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    String apkFile = saveFileName;
                    File ApkFile = new File(apkFile);
                    FileOutputStream fos = new FileOutputStream(ApkFile);

                    int count = 0;
                    byte buf[] = new byte[1024];

                    do {
                        int numread = is.read(buf);
                        count += numread;
                        // 更新进度
                        mProgressDlg.setProgress(count);
                        mHandler.sendEmptyMessage(DOWN_UPDATE);
                        if (numread <= 0) {
                            // 下载完成通知安装
                            mProgressDlg.cancel();
                            VersionUpdateDialog.getInstance().dismiss();
                            mHandler.sendEmptyMessage(DOWN_OVER);
                            break;
                        }
                        fos.write(buf, 0, numread);
                    } while (!interceptFlag);// 点击取消就停止下载.
                    fos.close();
                    is.close();
                    down();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
    }

    /**
     * 告诉HANDER已经下载完成了，可以安装了
     */
    public void down() {
        mHandler.post(new Runnable() {
            public void run() {
                mProgressDlg.cancel();
                update();
            }
        });
    }

    /**
     * 安装程序
     */
    void update() {
        File apkFile = new File(saveFileName);
        if (!apkFile.exists()) {
            return;
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(apkFile),
                "application/vnd.android.package-archive");
        activity.startActivity(intent);
    }

    /**
     * 下载文件
     *
     * @param url
     * @param successCallBack
     */
    public void downFile(final String url, final CallBackForT<Response> successCallBack) {

        final OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Toast.makeText(activity, "网络请求出错", Toast.LENGTH_SHORT).show();
                Log.d("数据","网络请求出错");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                successCallBack.finish(response);
            }
        });
    }

    /**
     * 更新下载的进度
     *
     * @param response
     * @throws IOException
     */
    public void updataProgress(Response response) throws IOException {
        InputStream inputStream = null;
        size = response.body().contentLength();
        DecimalFormat df = new DecimalFormat("0.00");
//        String SIZE = df.format((float) size / 1024 / 1024) + "MB";
        int downloadedSize = 0;
        inputStream = response.body().byteStream();
        byte buf[] = new byte[1024 * 1024];
        int numBytesRead;
        /*路径要修改*/
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdir();
        }
        String apkFile = saveFileName;
//        File ApkFile = new File(apkFile);
        BufferedOutputStream fos = new BufferedOutputStream(
                new FileOutputStream(apkFile));
        do {
            numBytesRead = inputStream.read(buf);
            if (numBytesRead > 0) {
                fos.write(buf, 0, numBytesRead);
                downloadedSize += numBytesRead;
                // updateProgress(downloadedSize, totalSize);
                tmpFileSize = df
                        .format((float) downloadedSize / 1024 / 1024)
                        + "MB";
                // 当前进度值
                progress = (int) (((float) downloadedSize / size) * 100);

                // 更新进度
                handler.sendEmptyMessage(DOWN_UPDATE);
            }
        } while (numBytesRead > 0);
        if (progress == 100) {
            handler.sendEmptyMessage(DOWN_OVER);
        }
        inputStream.close();
        fos.flush();
        fos.close();

    }
}

