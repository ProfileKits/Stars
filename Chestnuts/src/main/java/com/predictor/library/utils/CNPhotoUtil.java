package com.predictor.library.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.predictor.library.bean.CNDialogInfo;
import com.predictor.library.interfaces.DialogCallBack;
import com.predictor.library.jni.ChestnutData;
import com.predictor.library.view.CNDialog;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 选择图片或拍照工具类，适配Android11以上版本
 */
public class CNPhotoUtil {
    private static final String TAG = "VIJOZ";
    private static CNPhotoUtil mInstance;
    public static final int REQUEST_IMAGE_CAPTURE = 1;//相册选择
    public static final int REQUEST_CAMERA_CAPTURE = 2;//摄像头拍照
    public static final int CROP_CODE = 19;
    public String photoPath;
    public Uri photoURI;
    private File photoFile;
    private Uri outPutUri;

    public void showDialog(Activity activity, CNDialogInfo info) {
        if(!ChestnutData.getPermission()){
            return;
        }
        CNDialog.show(activity, info, new DialogCallBack() {
            @Override
            public void onClick(boolean isOk, Dialog v) {
                if (isOk) {
                    takePhoto(activity);
                } else {
                    selectPicture(activity);
                }
            }
        });
    }


    //TODO 调用示例
    //1、manifest加入android:requestLegacyExternalStorage="true",添加权限，并在APP中获取到权限
    //  <uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE"/>
    //  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    //  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    //
    //2、provider加入android:exported="false"  android:grantUriPermissions="true"
    //3、provider示例：
    /**
     *  <provider
     *      android:name="androidx.core.content.FileProvider"
     *      android:authorities="${APP_ID}.fileProvider"
     *      android:exported="false"
     *      android:grantUriPermissions="true">
     *      <meta-data
     *      android:name="android.support.FILE_PROVIDER_PATHS"
     *      android:resource="@xml/file_paths" />
     *  </provider>
     */
    //4、file_paths示例：
    /**
     * <?xml version="1.0" encoding="utf-8"?>
     * <paths>
     *     <external-files-path
     *         name="opensdk"
     *         path="Images/tmp/" />
     *     <root-path
     *         name="opensdk"
     *         path="" />
     *     <root-path
     *         name="root"
     *         path="" />
     *     <files-path
     *         name="files"
     *         path="/" />
     *     <cache-path
     *         name="cache"
     *         path="/" />
     *     <external-path
     *         name="external"
     *         path="/" />
     *     <external-files-path
     *         name="name"
     *         path="path" />
     *     <external-cache-path
     *         name="name"
     *         path="path" />
     * </paths>
     *
     */
    //5、调用此工具类中的showDialog方法，示例：
    /**
     * imageView.setOnClickListener(new View.OnClickListener() {
     *             @Override
     *             public void onClick(View v) {
     *                 CNDialogInfo info = new CNDialogInfo();
     *                 info.setTitle("选择头像");
     *                 info.setShowCloseBtn(true);
     *                 info.setHideContent(true);
     *                 info.setOkButton("拍照");
     *                 info.setCancelButton("相册");
     *                 CNPhotoUtil.getInstance().showDialog(SecondActivity.this, info);
     *             }
     *         });
     */

    //6、对应页面重写onActivityResult方法
//   private Uri uri;
//    @RequiresApi(api = Build.VERSION_CODES.Q)
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CNPhotoUtil.REQUEST_CAMERA_CAPTURE && resultCode == RESULT_OK) {
//            CNPhotoUtil.getInstance().takePhotoCropImage(this);
//        } else if (requestCode == CROP_CODE) {
//            String imagePath = CNPhotoUtil.getInstance().parseApiImagePath(this, data);
//            File mPhoneFile = new File(imagePath);
//            uri = CNPhotoUtil.getInstance().getUriForFile(mContext, mPhoneFile);
//            Drawable drawable = CNPhotoUtil.getInstance().getRoundDrawable(mContext, uri);
//            //切成圆形赋值
//            imageView.setImageDrawable(drawable);
//            //直接赋值
//            Glide.with(this).load(imagePath).into(imageView);
//        } else if (requestCode == CNPhotoUtil.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            String imagePath = CNPhotoUtil.getInstance().parseApiImagePath(this, data);
//            CNPhotoUtil.getInstance().selectPhotoCropImage(this, imagePath, uri, CROP_CODE);
//        }
//    }

    public static CNPhotoUtil getInstance() {
        if (mInstance == null) {
            mInstance = new CNPhotoUtil();
        }
        return mInstance;
    }

    public void takePhotoCropImage(Activity context) {
        if (TextUtils.isEmpty(photoPath) || !ChestnutData.getPermission()) {
            return;
        }
        Log.d("裁剪的Url", "cropRawPhoto: " + photoURI.toString());
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoURI, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 设置裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 2000);
        intent.putExtra("outputY", 2000);
        intent.putExtra("return-data", false);
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //android11 分区存储
        if (Build.VERSION.SDK_INT >= 29) {
            File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            Log.e("TAG", "裁剪公域：：" + storageDir);
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            outPutUri = Uri.fromFile(photoFile);
            //裁剪后输出路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(
                    new File(context.getExternalCacheDir(), "face-cropped.jpg")));
        }
        context.startActivityForResult(intent, CROP_CODE);
    }

    /**
     * 通过uri  获取文件路径 只适合Android11及以上
     *
     * @param context 上下文
     * @return 路径
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public String getFileAbsolutePath(Context context) {
        if (context == null) {
            return null;
        }
        return uriToFileApiQ(context, outPutUri);
    }


    /**
     * Android 10 以上适配
     *
     * @param context 上下文
     * @param uri     Uri
     * @return 路径
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private String uriToFileApiQ(Context context, Uri uri) {
        if(!ChestnutData.getPermission()){
            return "";
        }
        File file = null;
        //android10以上转换
        if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            file = new File(uri.getPath());
        } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //把文件复制到沙盒目录
            ContentResolver contentResolver = context.getContentResolver();
            @SuppressLint("Recycle")
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                try {
                    InputStream is = contentResolver.openInputStream(uri);
                    File cache = new File(context.getExternalCacheDir().getAbsolutePath(), Math.round((Math.random() + 1) * 1000) + displayName);
                    FileOutputStream fos = new FileOutputStream(cache);
                    FileUtils.copy(is, fos);
                    file = cache;
                    fos.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return file.getAbsolutePath();
    }


    /**
     * 调用相机拍照
     *
     * @param activity
     */
    public void takePhoto(Activity activity) {
        if(!ChestnutData.getPermission()){
            return;
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            File photoFile = null;
            photoFile = createImageFile();
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(activity,
                        activity.getPackageName() + ".fileProvider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activity.startActivityForResult(takePictureIntent, REQUEST_CAMERA_CAPTURE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        photoPath = image.getAbsolutePath();
        return image;
    }

    /**
     * 调用选择照片
     */
    public void selectPicture(Activity activity) {
        if(!ChestnutData.getPermission()){
            return;
        }
        Log.i(TAG, "选择照片");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }


    /**
     * 获取图片路径
     *
     * @param context
     * @param intent
     * @return
     */
    public String parseApiImagePath(Context context, Intent intent) {
        if(!ChestnutData.getPermission()){
            return "";
        }
        String result = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            result = handleImageOnKitkat(context, intent);
        } else {
            result = handleImageBeforeKitkat(intent);
        }
        Log.i(TAG, "选择照片路径:" + result);
        return result;
    }

    /**
     * 获取大于19版本的图片
     *
     * @param data
     */
    @TargetApi(19)
    private String handleImageOnKitkat(Context context, Intent data) {
        String imagePath = "";
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                //解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(context, uri, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(context, contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(context, uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    private String handleImageBeforeKitkat(Intent data) {
        String imagePath = "";
        Uri uri = data.getData();
        imagePath = uri.getPath();
        return imagePath;
    }

    @SuppressLint("Range")
    private String getImagePath(Context context, Uri uri, String selection) {
        String path = null;
        /**
         * 通过uri和seletion来获取真实的图片路径
         */
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 调用系统裁减功能，裁减某张指定的图片，并输出到指定的位置
     *
     * @param activity
     * @param imagePath     原始图片位置
     * @param outputFileUri 裁减后图片的输出位置，两个地址最好不一样。如果一样的话，有的手机上面无法保存裁减的结果
     * @return
     */
    public void selectPhotoCropImage(Activity activity, String imagePath, Uri outputFileUri, int requestCode, int aspectX, int aspectY, int outputX,
                                     int outputY) {
        if(!ChestnutData.getPermission()){
            return;
        }
        Uri originalFileUri = getUriForFile(activity, new File(imagePath));
        if (originalFileUri == null) {
            return;
        }
        final Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(originalFileUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);

        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true); // 部分机型没有设置该参数截图会有黑边
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 不启用人脸识别
        intent.putExtra("noFaceDetection", false);
        activity.startActivityForResult(intent, requestCode);
    }


    /**
     * 调用系统裁减功能，裁减某张指定的图片，并输出到指定的位置
     *
     * @param activity
     * @param imagePath     原始图片位置
     * @param outputFileUri 裁减后图片的输出位置，两个地址最好不一样。如果一样的话，有的手机上面无法保存裁减的结果
     * @return
     */
    public void selectPhotoCropImage(Activity activity, String imagePath, Uri outputFileUri, int requestCode) {
        Uri originalFileUri = getUriForFile(activity, new File(imagePath));
        if (originalFileUri == null) {
            return;
        }
        final Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(originalFileUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);

        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true); // 部分机型没有设置该参数截图会有黑边
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 不启用人脸识别
        intent.putExtra("noFaceDetection", false);
        activity.startActivityForResult(intent, requestCode);
    }

    /*
     * 将照片添加到图库
     * */
    private void addInGallery(Context context) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        if (photoPath.isEmpty()) {
            File f = new File(photoPath);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            context.sendBroadcast(mediaScanIntent);
        }
    }

    /**
     * 保存图片到文件File。
     *
     * @param src     源图片
     * @param file    要保存到的文件
     * @param format  格式
     * @param recycle 是否回收
     * @return true 成功 false 失败
     */
    public boolean save(Bitmap src, File file, Bitmap.CompressFormat format, boolean recycle) {
        if (src == null || !ChestnutData.getPermission())
            return false;

        OutputStream os;
        boolean ret = false;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            ret = src.compress(format, 100, os);
            if (recycle && !src.isRecycled())
                src.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 获取uri 适用Android11，Android10可以使用FileProvider
     *
     * @param context context
     * @param file    文件路径
     * @return
     */
    public Uri getUriForFile(Context context, File file) {
        Uri fileUri = null;
        String filePath = file.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{"_id"}, "_data=? ", new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            fileUri = Uri.withAppendedPath(baseUri, "" + id);
        } else if (file.exists()) {
            ContentValues values = new ContentValues();
            values.put("_data", filePath);
            fileUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } else {
            fileUri = null;
        }
        return fileUri;
    }

    /**
     * 通过图片uri获取圆形图片的Drawable对象
     *
     * @param mContext
     * @param mContentUri
     * @return
     */
    public RoundedBitmapDrawable getRoundDrawable(Context mContext, Uri mContentUri) {
        RoundedBitmapDrawable drawable = null;
        if (mContentUri != null) {
            Bitmap bitmap = null;
            try {
                InputStream is = mContext.getContentResolver().openInputStream(
                        mContentUri);
                bitmap = BitmapFactory.decodeStream(is);
                drawable = RoundedBitmapDrawableFactory.create(mContext.getResources(), bitmap);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
                drawable.setCircular(true);
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                Log.e("sms", "Failed to loaded content " + mContentUri, e);
            }
        }
        return drawable;
    }


    /**
     * 通过图片uri转成Drawable对象
     *
     * @param mContext
     * @param mContentUri
     * @return
     */
    public Drawable getDrawable(Context mContext, Uri mContentUri) {
        Drawable drawable = null;
        if (mContentUri != null) {
            Bitmap bitmap = null;
            try {
                InputStream is = mContext.getContentResolver().openInputStream(
                        mContentUri);
                bitmap = BitmapFactory.decodeStream(is);
                drawable = new BitmapDrawable(mContext.getResources(), bitmap);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                Log.e("sms", "Failed to loaded content " + mContentUri, e);
            }
        }
        return drawable;
    }

}

