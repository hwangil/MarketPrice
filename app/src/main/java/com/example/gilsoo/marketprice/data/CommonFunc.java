package com.example.gilsoo.marketprice.data;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * Created by gilsoo on 2016-11-27.
 */
public class CommonFunc {
    public static Map<String, Boolean> getAllPreferences(Context context){
        SharedPreferences pref = context.getSharedPreferences("favorite", Context.MODE_PRIVATE);
        return (Map<String, Boolean>)pref.getAll();

    }
    public static boolean getPreferences(Context context, String name){
        SharedPreferences pref = context.getSharedPreferences("favorite", Context.MODE_PRIVATE);
        return pref.getBoolean(name, false);
    }
    public static void savePreferences(Context context, String name){
        SharedPreferences pref = context.getSharedPreferences("favorite", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(name, true);
        editor.commit();
    }
    public static void removePreferences(Context context, String name){
        SharedPreferences pref = context.getSharedPreferences("favorite", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(name);
        editor.commit();
    }
}
