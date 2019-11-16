package com.casper.model;

import android.os.Handler;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ShopLoader {
    public ArrayList<Shop> getShops() {
        return shops;
    }

    private ArrayList<Shop> shops = new ArrayList<>();

    private String download(String urlString)
    {
        try{
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置连接属性。
            conn.setConnectTimeout(5000);//设置超时
            conn.setUseCaches(false);

            conn.connect();
            //这里才真正获取到了数据
            InputStream inputStream = conn.getInputStream();//获得输入流
            InputStreamReader input = new InputStreamReader(inputStream);
            BufferedReader buffer = new BufferedReader(input);
            //成功返回网页，读取全部数据
            if (conn.getResponseCode() == 200) {//200意味着返回的是"OK"
                String inputLine;
                StringBuilder resultData = new StringBuilder();
                while ((inputLine = buffer.readLine()) != null) {
                    resultData.append(inputLine);
                }
                //全部数据联结在一起后作为函数结果返回
                String text = resultData.toString();
                Log.v("out---------------->", text);
                return (text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //出错或其他情况则返回空字符串
        return "";
    }

    private void parseJson(String text)
    {
        try {
            JSONObject jsonObject = new JSONObject(text);
            JSONArray jsonData = jsonObject.getJSONArray("shops");
            int length = jsonData.length();
            //按照数组长度循环获得每一个商店对象，并设置其属性
            for (int i = 0; i < length; i++) {
                JSONObject shopJson = jsonData.getJSONObject(i);
                Shop shop = new Shop();
                shop.setName(shopJson.getString("name"));
                shop.setLatitude(shopJson.getDouble("latitude"));
                shop.setLongitude(shopJson.getDouble("longitude"));
                shop.setMemo(shopJson.getString("memo"));
                shops.add(shop);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load(final Handler handler, final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String content = download(url);
                parseJson(content);
                handler.sendEmptyMessage(1);
            }
        }).start();
    }

}
