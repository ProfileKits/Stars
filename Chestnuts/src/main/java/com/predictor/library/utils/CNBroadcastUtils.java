package com.predictor.library.utils;

import android.content.Context;
import android.content.Intent;

import com.predictor.library.jni.ChestnutData;

public class CNBroadcastUtils {
    public static final String BROADCAST_ACTION = "chestnut_action_broadcast_cmd";
    public static final String BROADCAST_CMD_UPDATE = "chestnut_cmd_update_broadcast";
    public static final String BROADCAST_CMD_DELETE = "chestnut_cmd_delete_broadcast";
    public static final String BROADCAST_CMD_MODIFY = "chestnut_cmd_modify_broadcast";

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

}
