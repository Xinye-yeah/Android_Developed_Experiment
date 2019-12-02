package com.casper.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.casper.model.Shop;
import com.casper.model.ShopLoader;
import com.casper.testdrivendevelopment.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapViewFragment extends Fragment {


    public MapViewFragment() {
        // Required empty public constructor
    }

    private MapView mapView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_view, container, false);
        mapView = view.findViewById(R.id.bmapView);

        BaiduMap baiduMap = mapView.getMap();

        //设定中心点坐标
        LatLng cenPt = new LatLng(22.2559,113.541112);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenPt)
                .zoom(17)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        baiduMap.setMapStatus(mMapStatusUpdate);

        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.raw.jnu);
        MarkerOptions markerOptions = new MarkerOptions().icon(bitmap).position(cenPt);
        baiduMap.addOverlay(markerOptions);


        //添加文字
        OverlayOptions textOption = new TextOptions().bgColor(0xAAFFFF00).fontSize(50)
                .fontColor(0xFFFF00FF).text("暨南大学珠海校区").rotate(0).position(cenPt);
        baiduMap.addOverlay(textOption);

        //响应事件
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getContext(),"Marker被点击！",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        final ShopLoader shopLoader = new ShopLoader();
        Handler handler = new Handler()
        {
            public void handleMessage(Message msg){
                drawShops(shopLoader.getShops());
            }
        };
        shopLoader.load(handler,"http://file.nidama.net/class/mobile_develop/data/bookstore.json");
        return view;
    }

    private void drawShops(ArrayList<Shop>shops)
    {
        if(mapView == null)return;
        BaiduMap baiduMap = mapView.getMap();
        for(int i = 0; i < shops.size() ; i++) {
            Shop shop = shops.get(i);

            //设定中心点坐标
            LatLng cenPt = new LatLng(shop.getLatitude(),shop.getLongitude());

            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.raw.shop);
            MarkerOptions markerOptions = new MarkerOptions().icon(bitmap).position(cenPt);
            baiduMap.addOverlay(markerOptions);

            //添加文字
            OverlayOptions textOption = new TextOptions().bgColor(0xAAFFFF00).fontSize(50)
                    .fontColor(0xFFFF00FF).text(shop.getName()).rotate(0).position(cenPt);
            baiduMap.addOverlay(textOption);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        if(mapView != null)mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        if(mapView != null)mapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if(mapView != null)mapView.onDestroy();
    }

}


