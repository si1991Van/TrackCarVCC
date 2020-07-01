package com.vcc.trackcar.model.manageApproveRejectBookCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookCarDto {

    @SerializedName("bookCarId")
    @Expose
    private Integer bookCarId;
    @SerializedName("reasonManage")
    @Expose
    private String reasonManage;
    @SerializedName("reasonManagerCar")
    @Expose
    private String reasonManagerCar;

    public Integer getBookCarId() {
        return bookCarId;
    }

    public void setBookCarId(Integer bookCarId) {
        this.bookCarId = bookCarId;
    }

    public String getReasonManage() {
        return reasonManage;
    }

    public void setReasonManage(String reasonManage) {
        this.reasonManage = reasonManage;
    }

    public String getReasonManagerCar() {
        return reasonManagerCar;
    }

    public void setReasonManagerCar(String reasonManagerCar) {
        this.reasonManagerCar = reasonManagerCar;
    }

}
