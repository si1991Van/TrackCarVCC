package com.vcc.trackcar.model.closeBookCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookCarClose {
    @SerializedName("bookCarId")
    @Expose
    private Integer bookCarId;
    @SerializedName("carId")
    @Expose
    private Integer carId;
    @SerializedName("typeBookCar")
    @Expose
    private String typeBookCar;

    public Integer getBookCarId() {
        return bookCarId;
    }

    public void setBookCarId(Integer bookCarId) {
        this.bookCarId = bookCarId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getTypeBookCar() {
        return typeBookCar;
    }

    public void setTypeBookCar(String typeBookCar) {
        this.typeBookCar = typeBookCar;
    }
}
