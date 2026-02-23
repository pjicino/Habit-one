package com.habit.tracker;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        // 开机后重新注册闹钟
        if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            rescheduleAlarms(context);
            return;
        }

        String type = intent.getStringExtra("type");
        if ("evening".equals(type)) {
            sendNotification(context,
                    1,
                    "📋 今日打卡提醒",
                    "今天是 🟢 🟡 🔴 ？别忘了记录今日状态。",
                    "evening");
        } else if ("weekly".equals(type)) {
            sendNotification(context,
                    2,
                    "📊 本周信号回顾",
                    "本周有几个黄灯和红灯？打开看看。",
                    "weekly");
        }
    }

    private void sendNotification(Context context, int id, String title, String body, String type) {
        Intent openIntent = new Intent(context, MainActivity.class);
        openIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        openIntent.putExtra("open_tab", type);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, id, openIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context, MainActivity.CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setVibrate(new long[]{0, 300, 100, 300})
                .setContentIntent(pendingIntent);

        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(id, builder.build());
        }
    }

    private void rescheduleAlarms(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // 重新设置晚间提醒
        Intent eveningIntent = new Intent(context, NotificationReceiver.class);
        eveningIntent.setAction("com.habit.tracker.NOTIFICATION");
        eveningIntent.putExtra("type", "evening");
        PendingIntent eveningPending = PendingIntent.getBroadcast(
                context, 100, eveningIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Calendar eveningCal = Calendar.getInstance();
        eveningCal.set(Calendar.HOUR_OF_DAY, 21);
        eveningCal.set(Calendar.MINUTE, 0);
        eveningCal.set(Calendar.SECOND, 0);
        if (eveningCal.getTimeInMillis() < System.currentTimeMillis()) {
            eveningCal.add(Calendar.DAY_OF_YEAR, 1);
        }

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                eveningCal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, eveningPending);

        // 重新设置周日提醒
        Intent weeklyIntent = new Intent(context, NotificationReceiver.class);
        weeklyIntent.setAction("com.habit.tracker.NOTIFICATION");
        weeklyIntent.putExtra("type", "weekly");
        PendingIntent weeklyPending = PendingIntent.getBroadcast(
                context, 200, weeklyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Calendar weeklyCal = Calendar.getInstance();
        weeklyCal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        weeklyCal.set(Calendar.HOUR_OF_DAY, 9);
        weeklyCal.set(Calendar.MINUTE, 0);
        weeklyCal.set(Calendar.SECOND, 0);
        if (weeklyCal.getTimeInMillis() < System.currentTimeMillis()) {
            weeklyCal.add(Calendar.WEEK_OF_YEAR, 1);
        }

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                weeklyCal.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, weeklyPending);
    }
}
