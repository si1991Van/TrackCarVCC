package com.vcc.trackcar.ui.xep_xe_doi_truong_xe;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vcc.trackcar.model.getListBookCar.LstBookCarDto;

import java.util.List;

public class XepXeViewModel extends ViewModel {

    public List<LstBookCarDto> listBookCar;
    private MutableLiveData<String> mText;

    public XepXeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}