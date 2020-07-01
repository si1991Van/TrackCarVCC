package com.vcc.trackcar.ui.radar_driver.custom_infowindow_maps;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.vcc.trackcar.R;
import com.vcc.trackcar.model.getListDriverCar.LstBookCarDto;
import com.vcc.trackcar.ui.base.CommonVCC;
import com.vcc.trackcar.ui.home.custom_infowindow_maps.InfoWindowData;
import com.vcc.trackcar.utils.DistanceCalculator;

public class CustomInfoWindowGoogleMapRadar implements GoogleMap.InfoWindowAdapter {

    private final View markerItemView;
    private final Context context;
    private OnItemClickDriverMaps onItemClickDriverMaps;
    private LstBookCarDto carDieuChuyen;

    public CustomInfoWindowGoogleMapRadar(Context context, OnItemClickDriverMaps onItemClickDriverMaps, LstBookCarDto lstBookCarDto) {
        this.context = context;
        markerItemView = LayoutInflater.from(context).inflate(R.layout.map_custom_infowindow_radar, null);
        this.onItemClickDriverMaps = onItemClickDriverMaps;
        this.carDieuChuyen = lstBookCarDto;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        LstBookCarDto driver = (LstBookCarDto) marker.getTag();
        if (driver == null) {

            return null;
        }

        TextView tv_driver_code = (TextView) markerItemView.findViewById(R.id.tv_driver_code);
        tv_driver_code.setText(driver.getDriverCode());

        TextView tv_nguoi_lai = (TextView) markerItemView.findViewById(R.id.tv_nguoi_lai);
        tv_nguoi_lai.setText(driver.getDriverName());

        TextView phone_driver = (TextView) markerItemView.findViewById(R.id.phone_driver);
        phone_driver.setText(driver.getPhoneNumberDriver());

        TextView tv_command_driver = (TextView) markerItemView.findViewById(R.id.tv_command_driver);
        if (driver.getOnCommandDriver().equals("0") &&
                driver.getLatitudeDriver() != null &&
                DistanceCalculator.distance(new LatLng(carDieuChuyen.getLatitudeCar(), carDieuChuyen.getLongtitudeCar()), new LatLng(driver.getLatitudeDriver(), driver.getLongtitudeDriver())) <= CommonVCC.LIMIT_DISTANCE) {
            tv_command_driver.setText(context.getString(R.string.san_sang));
            tv_command_driver.setTextColor(Color.GREEN);
        } else {
            tv_command_driver.setText(context.getString(R.string.khong_san_sang));
            tv_command_driver.setTextColor(Color.RED);
        }


        AppCompatButton btn_chon = (AppCompatButton) markerItemView.findViewById(R.id.btn_chon);
//        btn_chon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onItemClickDriverMaps.clickSelectItemDriverMaps(driver);
//            }
//        });

        return markerItemView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    public interface OnItemClickDriverMaps {
        void clickSelectItemDriverMaps(LstBookCarDto driver);
    }
}
