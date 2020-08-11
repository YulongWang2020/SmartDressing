package com.example.smartdressing.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    private static Toast shortToast = null;

    public static void makeShortText(Context context,String msg){
        if(context == null){
            return;
        }

        if(shortToast==null){
            shortToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }else{
            shortToast.setText(msg);
        }
        shortToast.show();
    }

    /**
     * 在页面中显示短时消息
     * @param context
     * @param msg
     */
    public static void showShort(Context context, String msg){
        makeShortText(context,msg);
    }

    private static Toast longToast = null;

    public static void makeLongText(Context context, String msg){
        if(context==null){
            return;
        }

        if(longToast==null){
            longToast=Toast.makeText(context,msg,Toast.LENGTH_LONG);
        }else{
            longToast.setText(msg);
        }
        longToast.show();
    }

    /**
     * 在页面中显示长时消息
     * @param context
     * @param msg
     */
    public static void showLong(Context context, String msg){
            makeLongText(context, msg);
    }
}
