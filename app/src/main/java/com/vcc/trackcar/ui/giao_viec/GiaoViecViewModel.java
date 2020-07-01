package com.vcc.trackcar.ui.giao_viec;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vcc.trackcar.model.getListBookCar.LstBookCarDto;

import java.util.List;

public class GiaoViecViewModel extends ViewModel {

    public List<LstBookCarDto> listBookCar;
//    private MutableLiveData<String> mText;
//
//    public GiaoViecViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is share fragment");
//    }
//
//    public LiveData<String> getText() {
//        return mText;
//    }
}