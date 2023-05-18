package com.example.playandroid.util;

import android.util.Log;

import com.example.playandroid.contract.DataCallBack;
import com.example.playandroid.contract.DataCallBackForImage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 *  网络工具类
 */
public class WebUtil {

    private static final String TAG = "WebUtil";

    /**
     * 从网路上获取数据
     * @param urlString 网址字符串
     * @return 数据
     */
    public static void getDataFromWeb(String urlString, DataCallBack dataCallBack) {
        StringBuilder response = new StringBuilder();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(urlString);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream inputStream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    dataCallBack.onSuccess(response.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "getDataFromWeb: 网络访问错误");
                    dataCallBack.onFailure(e);
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();


//        Log.v("Json解析", response.toString());
//        return response.toString();
    }

    public static void getImageData(String imageUrl, DataCallBackForImage callBack){
        final InputStream[] inputStream = new InputStream[1];
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;

                try {
                    URL url = new URL(imageUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    inputStream[0] = connection.getInputStream();
                    callBack.onSuccess(inputStream[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "getImageData: 图片访问错误");
                    callBack.onFailure(e);
                } finally {
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();

    }

}
