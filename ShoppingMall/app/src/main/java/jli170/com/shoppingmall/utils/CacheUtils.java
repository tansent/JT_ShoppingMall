package jli170.com.shoppingmall.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Jingtian(Tansent).
 */

public class CacheUtils {

    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("shoppingCart", Context.MODE_PRIVATE);
        return sp.getString(key,""); //do not offer the default value as null
    }

    public static void saveString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("shoppingCart", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,value);
        editor.commit();
    }
}
