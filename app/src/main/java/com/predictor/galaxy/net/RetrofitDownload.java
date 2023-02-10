package com.predictor.galaxy.net;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.predictor.galaxy.R;
import com.predictor.library.net.DownloadHelper;
import com.predictor.library.net.DownloadService;
import com.predictor.library.utils.CNLogUtil;


//Retrofit 下载服务
//TODO 不要忘记在当前项目的Manifest文件中添加DownloadService下载服务
//TODO 文件访问权限 和 androidx.core.content.FileProvider配置
public class RetrofitDownload {
    /**
     * @param context 调用的Activity
     * @param url 下载地址全路径 "https://chesong.oss-cn-hangzhou.aliyuncs.com/chesong/multipart/form-data;charset=UTF-8/2023/02/11/d7a568fa-5b63-403b-aaf5-8f5eee28d36f1676046841089.jpg"
     * @param outputPath 下载路径 context.getExternalCacheDir().getAbsolutePath() + File.separator + "image.jpg";//"image.png";
     */
    public static void start(Activity context, String url, String outputPath,DownloadService.DownloadCallback callback) {
        DownloadHelper.download(context, url, outputPath, new DownloadService.DownloadCallback() {
            @Override
            public void progressNotify(int progress) {
                callback.progressNotify(progress);
            }
            @Override
            public void finish(String filePath) {
                callback.finish(filePath);
            }
            @Override
            public void fail(String msg) {
                callback.fail(msg);
            }
        });
    }


}
