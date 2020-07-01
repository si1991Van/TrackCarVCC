package com.vcc.trackcar.ui.list_book_added;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vcc.trackcar.model.getListBookCar.LstBookCarDto;

import java.util.List;

public class ListBookAddedViewModel extends ViewModel {

    public List<LstBookCarDto> listBookCar;
    private MutableLiveData<String> mText;

    public ListBookAddedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}