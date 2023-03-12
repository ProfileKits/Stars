package com.predictor.galaxy.ui;

import static com.predictor.library.utils.CNPhotoUtil.CROP_CODE;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.predictor.galaxy.R;
import com.predictor.galaxy.net.RetrofitDownload;
import com.predictor.library.base.CNBaseActivity;
import com.predictor.library.bean.CNDialogInfo;
import com.predictor.library.net.DownloadService;
import com.predictor.library.utils.CNPhotoUtil;
import com.predictor.library.view.CNProgressView;
import com.predictor.library.utils.CNLogUtil;
import com.predictor.library.view.CNSeekBar;

import java.io.File;
import java.util.Arrays;

public class SecondActivity extends CNBaseActivity {
    String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MANAGE_EXTERNAL_STORAGE};
    String url = "https://chesong.oss-cn-hangzhou.aliyuncs.com/chesong/multipart/form-data;charset=UTF-8/2023/02/11/d7a568fa-5b63-403b-aaf5-8f5eee28d36f1676046841089.jpg";

    ImageView imageView;

    CNSeekBar seekBar;

    @Override
    protected void initView() {
        seekBar = findViewById(R.id.seek_bar_hor);
        checkFilePermission();
        imageView = findViewById(R.id.img);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void initData() {
        Dialog dialog = CNProgressView.showLoading(this);
        TextView textView = dialog.findViewById(R.id.tip_text_view);

        //API 30以上文件存储权限这个问题要解决一下
        if (Environment.isExternalStorageManager()) {
//            writeStorageUnChecked(pathTypes)
            //File file = CNFileUtils.createFileDir(this, "00");
//            String file2 = CNFileUtils.getSaveDirectory("00");
        }

        String path = getExternalCacheDir().getAbsolutePath() + File.separator + "image.jpg";
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

                    CNProgressView.showDialog(mContext, msg, null);
                    //显示图片
                    Glide.with(mContext).load(filePath).into(imageView);
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
                    CNProgressView.showDialog(mContext, finalMsg, null);
                });
            }
        });
    }

    @Override
    protected void initListener() {
        seekbar();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CNDialogInfo info = new CNDialogInfo();
                info.setTitle("选择头像");
                info.setShowCloseBtn(true);
                info.setHideContent(true);
                info.setOkButton("拍照");
                info.setCancelButton("相册");
                CNPhotoUtil.getInstance().showDialog(SecondActivity.this, info);
            }
        });
    }


    private Uri uri;
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CNPhotoUtil.REQUEST_CAMERA_CAPTURE && resultCode == RESULT_OK) {
            CNPhotoUtil.getInstance().takePhotoCropImage(this);
        } else if (requestCode == CROP_CODE) {
            String imagePath = CNPhotoUtil.getInstance().parseApiImagePath(this, data);
            File mPhoneFile = new File(imagePath);
            uri = CNPhotoUtil.getInstance().getUriForFile(mContext, mPhoneFile);
            Drawable drawable = CNPhotoUtil.getInstance().getRoundDrawable(mContext, uri);
            //切成圆形赋值
            imageView.setImageDrawable(drawable);
            //直接赋值
            Glide.with(this).load(imagePath).into(imageView);
        } else if (requestCode == CNPhotoUtil.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            String imagePath = CNPhotoUtil.getInstance().parseApiImagePath(this, data);
            CNPhotoUtil.getInstance().selectPhotoCropImage(this, imagePath, uri, CROP_CODE);
        }
    }


    @Override
    protected int getLayoutResID() {
        return R.layout.activity_second;
    }


    private void seekbar() {
        seekBar.setProgressChangeListener(new CNSeekBar.OnProgressChangeListener() {
            @Override
            public void progressChanged(float progress) {
                Log.i("滑动进度", progress + "");
            }
        });
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