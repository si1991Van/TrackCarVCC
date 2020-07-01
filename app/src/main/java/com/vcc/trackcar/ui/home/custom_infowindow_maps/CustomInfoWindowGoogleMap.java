package com.vcc.trackcar.ui.home.custom_infowindow_maps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.vcc.trackcar.R;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private final View markerItemView;
    private final Context context;

    public CustomInfoWindowGoogleMap(Context context) {
        this.context = context;
        markerItemView = LayoutInflater.from(context).inflate(R.layout.map_custom_infowindow, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        InfoWindowData car = (InfoWindowData) marker.getTag();
        if (car == null) {

            return null;
        }

        TextView tv_ma_lenh = (TextView) markerItemView.findViewById(R.id.tv_ma_lenh);
        tv_ma_lenh.setText(car.getMaLenh());

        TextView tv_nguoi_lai = (TextView) markerItemView.findViewById(R.id.tv_nguoi_lai);
        tv_nguoi_lai.setText(car.getNguoiLai());

        TextView tv_nguoi_tao = (TextView) markerItemView.findViewById(R.id.tv_nguoi_tao);
        tv_nguoi_tao.setText(car.getNguoiTao());

        TextView tv_content = (TextView) markerItemView.findViewById(R.id.tv_content);
        tv_content.setText(car.getContent());

        TextView tv_thoi_gian_di = (TextView) markerItemView.findViewById(R.id.tv_thoi_gian_di);
        tv_thoi_gian_di.setText(car.getThoiGianDi());

        TextView tv_thoi_gian_du_kien_con = (TextView) markerItemView.findViewById(R.id.tv_thoi_gian_du_kien_con);
        tv_thoi_gian_du_kien_con.setText(car.getThoiGianDuKienConLai());

        return markerItemView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
