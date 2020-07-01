package com.vcc.trackcar.ui.detail_book_car.open_command;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.vcc.trackcar.model.getListBookCar.LstBookCarDto;


public class OpenCommandViewModel extends ViewModel {

    public LstBookCarDto bookCarDto;

    private MutableLiveData<String> time_end_text;

    private MutableLiveData<String> diem_den;

    public OpenCommandViewModel() {
        time_end_text = new MutableLiveData<>();

        diem_den = new MutableLiveData<>();
    }



    public LiveData<String> getTime_end_text() {
        return time_end_text;
    }

    public void setTime_end_text(String time_end_text) {
        this.time_end_text.setValue(time_end_text);
    }


    public LiveData<String> getDiem_den() {
        return diem_den;
    }

    public void setDiem_den(String diem_den) {
        this.diem_den.setValue(diem_den);
    }

}