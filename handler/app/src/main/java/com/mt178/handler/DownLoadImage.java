package com.mt178.handler;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

import java.net.URL;
import java.util.logging.LogRecord;

/**
 * Created by llh on 2014/12/2.
 */
public class DownLoadImage {

    String image_path;

    public DownLoadImage(String image_path) {
        this.image_path = image_path;
    }

    public void loadImage(final ImageCallBack imageCallBack) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Drawable drawable= (Drawable) msg.obj;
                imageCallBack.getImage(drawable);
            }

        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Drawable drawable = Drawable.createFromStream(new URL(image_path).openStream(), "");
                    Message message=Message.obtain();
                    message.obj=drawable;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //接口的回调方式
    public interface ImageCallBack {
        public void getImage(Drawable drawable);
    }
}
