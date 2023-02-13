package com.predictor.library.utils;

import static android.content.Context.VIBRATOR_SERVICE;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

import com.predictor.library.R;


/**
 * 提示音播放 + 手机振动
 */

public class CNNoticeVoicePlayer {
    private static CNNoticeVoicePlayer instance;
    // 当手机开启静音，部分手机的多媒体不会被管控，MediaPlayer改为Ringtone播放
    private Ringtone ringtone;
    private Vibrator vibrator;// 振动

    public CNNoticeVoicePlayer(Context context, Uri ringTone) {
        ringtone = initRingtone(context, ringTone);
        vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
    }


    public static CNNoticeVoicePlayer getInstance(Context context) {
        if (instance == null) {
            instance = new CNNoticeVoicePlayer(context, null);
        }
        return instance;
    }

    /**
     * @param context
     * @param ringTone 例如：Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.msg);
     * @return
     */
    public static CNNoticeVoicePlayer getInstance(Context context, Uri ringTone) {
        if (instance == null) {
            instance = new CNNoticeVoicePlayer(context, ringTone);
        }
        return instance;
    }


    /**
     * @param context Activity
     * @return Ringtone 例如：Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.msg);
     */
    private Ringtone initRingtone(Context context, Uri tone) {
        Uri ringTone;
        if (tone != null) {
            ringTone = tone;
        } else {
            // 系统默认通知提示音
            // RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            ringTone = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.msg);
        }
        return RingtoneManager.getRingtone(context, ringTone);
    }

    /**
     * 播放声音并震动
     */
    public void start() {
        ringtone.play();
        // 停止 开启 停止 开启
        long[] pattern = {100, 400, 100, 400};
        // 重复两次上面的pattern 如果只想震动一次，index设为-1
        // vibrator.vibrate(pattern, 2);
        // 判断该用户是否开启振动
        vibrator.vibrate(pattern, -1);
    }

    /**
     * 只震动一下
     */
    public void vibrator() {
        // 停止 开启 停止 开启
        long[] pattern = {100, 400};
        // 重复两次上面的pattern 如果只想震动一次，index设为-1
//         vibrator.vibrate(pattern, 2);
        vibrator.vibrate(pattern, -1);
    }


    /**
     * 播放声音并震动
     * @param pattern 播放这个队列震动
     */
    public void start(long[] pattern) {
        ringtone.play();
        vibrator.vibrate(pattern, -1);
    }

    /**
     * 只震动
     * @param pattern 播放这个队列震动
     */
    public void vibrator(long[] pattern) {
        vibrator.vibrate(pattern, -1);
    }

    /**
     * 停止提示音及震动
     */
    public void stop() {
        ringtone.stop();
        vibrator.cancel();
    }
}
