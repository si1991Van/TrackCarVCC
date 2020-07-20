package com.vcc.trackcar.ui.add_book_car;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vcc.trackcar.model.addBookCar.BookCarDto;
import com.vcc.trackcar.model.getListTypeCar.LstBookCarDto;
import com.vcc.trackcar.model.getListUser.LstUserDto;
import com.vcc.trackcar.ui.base.ValidationUtil;
import com.vcc.trackcar.ui.select_address.model.DataSelectAddress;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddBookCarViewModel extends ViewModel {

    public ArrayList<LstUserDto> listTogether = new ArrayList<>();
    public ArrayList<LstUserDto> listManager = new ArrayList<>();

    public List<LstBookCarDto> listCarType = new ArrayList<>();
    public int posItemSpCarType = -1;

    public String typeBookCar = "";
    public Date dateStart = null;
    public DataSelectAddress dataSelectAddress = new DataSelectAddress();

    private MutableLiveData<String> time_start_text;
    private MutableLiveData<String> time_end_text;

    private MutableLiveData<String> diem_di;
    private MutableLiveData<String> diem_den;

    public AddBookCarViewModel() {
        time_start_text = new MutableLiveData<>();
        time_end_text = new MutableLiveData<>();

        diem_di = new MutableLiveData<>();
        diem_den = new MutableLiveData<>();
    }

    public LiveData<String> getTime_start_text() {
        return time_start_text;
    }

    public void setTime_start_text(String time_start_text) {
        this.time_start_text.setValue(time_start_text);
    }

    public LiveData<String> getTime_end_text() {
        return time_end_text;
    }

    public void setTime_end_text(String time_end_text) {
        this.time_end_text.setValue(time_end_text);
    }

    public LiveData<String> getDiem_di() {
        return diem_di;
    }

    public void setDiem_di(String diem_di) {
        this.diem_di.setValue(diem_di);
    }

    public LiveData<String> getDiem_den() {
        return diem_den;
    }

    public void setDiem_den(String diem_den) {
        this.diem_den.setValue(diem_den);
    }

    public com.vcc.trackcar.model.getListBookCar.LstBookCarDto bookCarDto = new com.vcc.trackcar.model.getListBookCar.LstBookCarDto();
    public com.vcc.trackcar.model.getListDriverCar.LstBookCarDto carDieuChuyen = new com.vcc.trackcar.model.getListDriverCar.LstBookCarDto();
}