从网络异步加载图片和文字的项目。
------------------------------


### 1，这是一个异步下载图片的类，其中用到了接口回调的方法

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


 ![image](https://github.com/huahua-cangsang/asynctask_handler_callback/blob/master/handler/handle.gif)


### 链接  
1.[eoe社区](http://www.eoeandroid.com/?1222543)<br />  


