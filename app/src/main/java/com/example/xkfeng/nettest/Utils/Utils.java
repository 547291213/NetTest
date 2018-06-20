package com.example.xkfeng.nettest.Utils;

import android.util.Log;

import com.example.xkfeng.nettest.JavaBean.SimpleClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by initializing on 2018/6/12.
 * 封装的工具类集合
 */


public class Utils {

    public static <T> T  parseJsonObjectWithGson(String jsonData, Class<T> type)
    {

        Gson gson = new Gson() ;
        T result = gson.fromJson(jsonData , type) ;

        return result ;
    }



//    public static< T>列表< T> stringToArray（String s，Class< T []> clazz）{
//        T [] arr = new Gson（）。fromJson（s，clazz）;
//        return Arrays.asList（arr）; //或者返回Arrays.asList（new Gson（）。fromJson（s，clazz））;
//    }

    public static <T> List<T> parseJsonArrayWithGson(String jsonData , Class<T[]>type) {
        Gson gson = new Gson();
        T[] result = gson.fromJson(jsonData, type) ;
        return (List<T>) Arrays.asList(result);
    }
}
