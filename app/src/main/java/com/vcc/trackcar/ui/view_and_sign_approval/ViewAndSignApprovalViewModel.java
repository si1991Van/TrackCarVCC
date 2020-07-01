package com.vcc.trackcar.ui.view_and_sign_approval;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vcc.trackcar.model.getListBookCar.LstBookCarDto;

import java.util.List;

public class ViewAndSignApprovalViewModel extends ViewModel {

    public List<LstBookCarDto> listBookCar;
    private MutableLiveData<String> mText;

    public ViewAndSignApprovalViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}