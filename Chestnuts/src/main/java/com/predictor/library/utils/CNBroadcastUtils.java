package com.predictor.library.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.predictor.library.bean.BroadcastBean;
import com.predictor.library.jni.ChestnutData;

import java.io.Serializable;

public class CNBroadcastUtils {
    public static final String BROADCAST_ACTION = "chestnut_action_broadcast_cmd";
    public static final String BROADCAST_CMD_UPDATE = "chestnut_cmd_update_broadcast";
    public static final String BROADCAST_CMD_DELETE = "chestnut_cmd_delete_broadcast";
    public static final String BROADCAST_CMD_MODIFY = "chestnut_cmd_modify_broadcast";
    public static final String BROADCAST_CMD_STRING = "chestnut_cmd_string_broadcast";
    public static final String BROADCAST_CMD_OBJECT = "chestnut_cmd_object_broadcast";

    public static final String BROADCAST_ACTION_DOWNLOAD = "chestnut_action_download_broadcast";
    public static final String BROADCAST_CMD_DOWNLOAD_NOW = "chestnut_cmd_download_now_broadcast";


    //发送更新广播
    public static void sendUpdateCmd(Context context) {
        if (ChestnutData.getPermission()) {
            Intent intent = new Intent();
            intent.setAction(BROADCAST_ACTION);
            intent.putExtra(BROADCAST_CMD_UPDATE, true);
            context.sendBroadcast(intent);
        }
    }

    //发送删除广播
    public static void sendDeleteCmd(Context context) {
        if (ChestnutData.getPermission()) {
            Intent intent = new Intent();
            intent.setAction(BROADCAST_ACTION);
            intent.putExtra(BROADCAST_CMD_DELETE, true);
            context.sendBroadcast(intent);
        }
    }


    //发送修改广播
    public static void sendModifyCmd(Context context) {
        if (ChestnutData.getPermission()) {
            Intent intent = new Intent();
            intent.setAction(BROADCAST_ACTION);
            intent.putExtra(BROADCAST_CMD_MODIFY, true);
            context.sendBroadcast(intent);
        }
    }


    //发送字符串数据广播
    public static void sendStringCmd(Context context,String data) {
        if (ChestnutData.getPermission()) {
            Intent intent = new Intent();
            intent.setAction(BROADCAST_ACTION);
            intent.putExtra(BROADCAST_CMD_STRING, data);
            context.sendBroadcast(intent);
        }
    }


    //发送可序列化对象数据广播

    /**
     *
     * @param context
     * @param obj
     * import java.io.Serializable; 对象代码例子
     *
     * public class MyObject implements Serializable {
     *     private String name;
     *     private int age;
     *
     *     public MyObject(String name, int age) {
     *         this.name = name;
     *         this.age = age;
     *     }
     *
     *     public String getName() {
     *         return name;
     *     }
     *
     *     public int getAge() {
     *         return age;
     *     }
     * }
     *
     */
    public static void sendObjectCmd(Context context, BroadcastBean obj) {
        if (ChestnutData.getPermission()) {
            Intent intent = new Intent();
            intent.setAction(BROADCAST_ACTION);
            intent.putExtra(BROADCAST_CMD_OBJECT,obj);
            context.sendBroadcast(intent);
        }
    }


    //发送下载广播
    public static void sendDownloadCmd(Context context) {
        if (ChestnutData.getPermission()) {
            Intent intent = new Intent();
            intent.setAction(BROADCAST_ACTION_DOWNLOAD);
            intent.putExtra(BROADCAST_CMD_DOWNLOAD_NOW, true);
            context.sendBroadcast(intent);
        }
    }

}
