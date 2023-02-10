package com.predictor.galaxy.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.predictor.galaxy.R;
import com.predictor.galaxy.net.RetrofitDownload;
import com.predictor.library.artanimation.library.base.Glider;
import com.predictor.library.base.CNBaseActivity;
import com.predictor.library.net.DownloadService;
import com.predictor.library.utils.CNDialog;
import com.predictor.library.utils.CNFileUtils;
import com.predictor.library.utils.CNLogUtil;

import java.io.File;
import java.util.Arrays;

public class SecondActivity extends CNBaseActivity {
    String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MANAGE_EXTERNAL_STORAGE};
    String url = "https://chesong.oss-cn-hangzhou.aliyuncs.com/chesong/multipart/form-data;charset=UTF-8/2023/02/11/d7a568fa-5b63-403b-aaf5-8f5eee28d36f1676046841089.jpg";
    String path = getExternalCacheDir().getAbsolutePath() + File.separator + "image.jpg";
    ImageView img;

    @Override
    protected void initView() {
        checkFilePermission();
        img = findViewById(R.id.img);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void initData() {
        Dialog dialog = CNDialog.showLoading(this);
        TextView textView = dialog.findViewById(R.id.tip_text_view);
        //API 30以上文件存储权限这个问题要解决一下
        if (Environment.isExternalStorageManager()) {
//            writeStorageUnChecked(pathTypes)
            //File file = CNFileUtils.createFileDir(this, "00");
//            String file2 = CNFileUtils.getSaveDirectory("00");
        }


        RetrofitDownload.start(this, url, path, new DownloadService.DownloadCallback() {
            @Override
            public void progressNotify(int progress) {
                runOnUiThread(() -> {
                    textView.setText(progress + "%");
                });
            }

            @Override
            public void finish(String filePath) {
                runOnUiThread(() -> {
                    String msg = "文件下载成功\n" + filePath;
                    CNLogUtil.i(msg);
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    CNDialog.showDialog(mContext, msg, null);
                    Glide.with(mContext).load(filePath).into(img);
                });
            }

            @Override
            public void fail(String msg) {
                msg = "下载失败\n\n可能网络不好,或者远端文件不存在\n-----------\n" + msg;
                String finalMsg = msg;
                runOnUiThread(() -> {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    CNDialog.showDialog(mContext, finalMsg, null);
                });
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_second;
    }


    @Override
    protected boolean setFullScreen() {
        return true;
    }

    private void checkFilePermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, perms, 123);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        CNLogUtil.i("onRequestPermissionsResult permissions = " + Arrays.toString(permissions) + "  grantResults = " + Arrays.toString(grantResults));
    }
}