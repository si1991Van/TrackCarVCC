package com.vcc.trackcar.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.vcc.trackcar.model.getHistoryCar.BookCarHistory;
import com.vcc.trackcar.model.getHistoryCar.MakerCar;
import com.vcc.trackcar.model.getListDriverCar.LstBookCarDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeViewModel extends ViewModel {

    public Date dateStart = new Date();
    public LstBookCarDto carDtoSelected = null;
    public Polyline polyCar;
    public Marker makerFirst;
    public Marker makerEnd;
    public Marker makerTarget;
    public List<BookCarHistory> listBookCarHistory = new ArrayList<>();

    private MutableLiveData<String> mText;
    private  MutableLiveData<LatLng> viTriCuaBan;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
        viTriCuaBan = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }

//    List<LstBookCarDto> listCar = new ArrayList<>();

    public LiveData<LatLng> getViTriCuaBan() {
        return viTriCuaBan;
    }

    public void setViTriCuaBan(LatLng viTriCuaBan) {
        this.viTriCuaBan.setValue(viTriCuaBan);
    }
}