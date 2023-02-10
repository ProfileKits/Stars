package com.predictor.library.net;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.predictor.library.listener.DownloadInterface;
import com.predictor.library.utils.CNFileUtils;
import com.predictor.library.utils.CNLogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Retrofit 下载服务
 */
public class DownloadService extends JobIntentService {
    /**
     * 下载url
     */
    public static final String WORK_URL = "work_url";
    /**
     *下载完成后的本地文件存放路径
     */
    public static final String TARGET_FILE_PATH_URL = "target_file_path_url";
    /**
     * 这个Service 唯一的id
     */
    static final int JOB_ID = 16870;
    private static final String TAG = "debug";

    private static ConcurrentHashMap<String,DownloadCallback> callbacks = new ConcurrentHashMap<>();

    static void enqueueWork(Context context, Intent work) {
        CNLogUtil.i("enqueueWork --- " +  Thread.currentThread().getName());
        enqueueWork(context, DownloadInterface.class, JOB_ID, work);
    }
    
    /**
     * Convenience method for enqueuing work in to this service.
     */
    static void enqueueWork(Context context, Intent work, DownloadCallback notifyCallback) {
        CNLogUtil.i("enqueueWork --- " +  Thread.currentThread().getName());
        String url = work.getStringExtra(WORK_URL);
        if(TextUtils.isEmpty(url) && notifyCallback != null){
            notifyCallback.fail("下载url不能为空");
            return;
        }

        callbacks.put(url,notifyCallback);
        enqueueWork(context, DownloadService.class, JOB_ID, work);
    }
    Retrofit retrofit2 = new Retrofit.Builder().baseUrl("https://www.baidu.com/").addConverterFactory(GsonConverterFactory.create()).build();
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        String url = intent.getStringExtra(WORK_URL);
        CNLogUtil.i("DownloadService mp3 url = " + url);
        if(TextUtils.isEmpty(url)){
            DownloadCallback callback = callbacks.get(url);
            if(callback != null){
                callback.fail("url不能为空");
            }
            callbacks.remove(url);
            return;
        }

        DownloadInterface versionService = retrofit2.create(DownloadInterface.class);


        Call<ResponseBody> call = versionService.downLoad(url);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(!ServiceHelper.checkObjectResponseCommon(response)){
                    DownloadCallback callback = callbacks.get(url);
                    if(callback !=null){
                        callback.fail(response.raw().networkResponse().toString().toString());
                        callbacks.remove(url);
                    }
                    return;
                }
                String fff = CNFileUtils.getSaveDirectory("123123");
                CNLogUtil.i("保存目录："+fff);
                String path = intent.getStringExtra(TARGET_FILE_PATH_URL);
                CNLogUtil.i("Environment.getDownloadCacheDirectory().getAbsolutePath() = " + Environment.getDownloadCacheDirectory().getAbsolutePath());
                if(TextUtils.isEmpty(path)){

                    path = getExternalCacheDir().getAbsolutePath() + File.separator + "tmp" + File.separator + "temp.mp3";
                }
                CNLogUtil.i("path = " + path);
                File file = new File(path);
                if(!file.exists()){
                    boolean b = new File(file.getParent()).mkdirs();
                }

                String finalPath = path;
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        writeResponseBodyToDisk(url,response.body(), finalPath);
                    }
                });

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String url = call.request().url().toString();
                DownloadCallback callback = callbacks.get(url);
                if(callback !=null){
                    callback.fail("文件下载失败，请检查网络连接状态");
                    callbacks.remove(url);
                }
            }
        });

    }
    private boolean writeResponseBodyToDisk(String url,ResponseBody body,String path) {
        CNLogUtil.i("0writeResponseBodyToDisk --- path---" + path);
        try {
            // todo change the file location/name according to your needs
            File targetFile = new File(path);//new File(getExternalFilesDir(null) + File.separator + "Future Studio Icon.png");

           boolean delete = false;
            if(targetFile.exists()){
                delete = targetFile.delete();
            }
            //AppCNLogUtil.i("targetFile.delete() = " + delete);
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                int currentProgress = 0;
                Double totalSize = Double.valueOf(Long.toString(fileSize));
                Double currentSize = 0d;


                inputStream = body.byteStream();
                outputStream = new FileOutputStream(targetFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    currentSize = Double.valueOf(Long.toString(fileSizeDownloaded));
                    int progress = (int) ((currentSize/totalSize)*100);
                    if(currentProgress != progress){
                        currentProgress = progress;
                        DownloadCallback callback = callbacks.get(url);
                        if(callback != null ){//&& callback.isNeedNotify()
                            callback.progressNotify(progress);
                        }
                    }
                    if(fileSizeDownloaded == fileSize){
                        DownloadCallback callback = callbacks.get(url);
                        if(callback !=null){
                            callback.finish(targetFile.getAbsolutePath());
                            callbacks.remove(url);
                        }
                    }}

                outputStream.flush();

                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }

        } catch (IOException e) {
            return false;
        }
    }


    /**
     * 下载进度通知回调
     */
    public interface DownloadCallback {
        /**
         * 下载进度通知
         * @param progress
         */
        void progressNotify(int progress);

        /**
         * 工作已完成
         */
        void finish(String filePath);

        /**
         * 网络下载失败
         * @param msg
         */
        void fail(String msg);
    }

}
