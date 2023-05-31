package com.example.playandroid.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.playandroid.interf.datacallback.DataCallBack;
import com.example.playandroid.interf.datacallback.DataCallBackForBitmap;
import com.example.playandroid.interf.datacallback.DataCallBackForImage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;


/**
 *  网络工具类
 */
public class WebUtil {

    private static final String TAG = "WebUtil";

    private final static ExecutorService threadPoolExecutor = ThreadPoolUtil.getInstance();

    /**
     * 从网络上获取数据
     * @param urlString 网址字符串
     * @return 数据
     */
    public static void getDataFromWeb(String urlString, DataCallBack dataCallBack) {
        StringBuilder response = new StringBuilder();
        threadPoolExecutor.execute(new Runnable() {
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
        });


    }

    /**
     *  通过图片路径获取图片
     * @param imageUrl 图片路径
     * @param callBack 数据回调接口
     */
    public static void getImageData(String imageUrl, DataCallBackForImage callBack){
        final InputStream[] inputStream = new InputStream[1];
        threadPoolExecutor.execute(new Runnable() {
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
        });

    }

    public static void getImageData(String imageUrl, DataCallBackForBitmap callBack){

        HttpURLConnection connection = null;
        try {
            URL url = new URL(imageUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            System.out.println(bitmap);
            callBack.onSuccess(bitmap);
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


    /**
     *  发送数据到网络
     * @param urlString 网络路径
     * @param paramMap 要发送的数据
     * @return 发送是否成功
     */
    public static void postDataToWeb(String urlString, Map<String, String> paramMap, DataCallBack callBack){
        StringBuilder response = new StringBuilder();
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                OutputStream outputStream = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(urlString);
                    connection = (HttpURLConnection) url.openConnection();
                    String paramData = paramMapToString(paramMap);

                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setRequestProperty("Content_Length", String.valueOf(paramData.length()));
                    connection.setDoOutput(true);

                    outputStream = connection.getOutputStream();
                    outputStream.write(paramData.getBytes());

                    InputStream inputStream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    Map<String, List<String>> cookies = connection.getHeaderFields();
                    List<String> setCookies = cookies.get("Set-Cookie");

                    callBack.onSuccess(response.toString());
                    callBack.getCookie(setCookies);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "postDataToWeb: 发送数据错误/" + e);
                    callBack.onFailure(e);
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        });
    }

//    /**
//     *  发送数据到网络
//     * @param urlString 网络路径
//     * @param cookie cookie值
//     * @return 发送是否成功
//     */
//    public static void postDataToWeb(String urlString, String cookie, DataCallBack callBack){
//        StringBuilder response = new StringBuilder();
//        threadPoolExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                HttpURLConnection connection = null;
//                OutputStream outputStream = null;
//                BufferedReader reader = null;
//                try {
//                    URL url = new URL(urlString);
//                    connection = (HttpURLConnection) url.openConnection();
//
//                    connection.setRequestMethod("POST");
//                    connection.setConnectTimeout(8000);
//                    connection.setRequestProperty("Cookie", cookie);
//
//                    InputStream inputStream = connection.getInputStream();
//                    reader = new BufferedReader(new InputStreamReader(inputStream));
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        response.append(line);
//                    }
//
//                    Map<String, List<String>> cookies = connection.getHeaderFields();
//                    List<String> setCookies = cookies.get("Set-Cookie");
//
//
//                    callBack.onSuccess(null);
//                    callBack.getCookie(null);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Log.e(TAG, "postDataToWeb: 发送数据错误/" + e);
//                    callBack.onFailure(e);
//                } finally {
//                    if (reader != null) {
//                        try {
//                            reader.close();
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//
//                    if (outputStream != null) {
//                        try {
//                            outputStream.close();
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//
//                    if (connection != null){
//                        connection.disconnect();
//                    }
//                }
//            }
//        });
//    }

    /**
     *  将post数据转换为相应格式字符串
     * @param paramMap post数据
     * @return 字符串
     */
    private static String paramMapToString(Map<String, String> paramMap){
        StringBuilder stringBuilder = new StringBuilder();

        Set<Map.Entry<String, String>> entries = paramMap.entrySet();

        for (Map.Entry<String, String> entry : entries) {
            stringBuilder.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }

}
