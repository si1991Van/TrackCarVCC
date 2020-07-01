package com.vcc.trackcar.model.captainCarApproveRejectBookCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookCarDto {

    @SerializedName("bookCarId")
    @Expose
    private Integer bookCarId;
    @SerializedName("carId")
    @Expose
    private Integer carId;
    @SerializedName("licenseCar")
    @Expose
    private String licenseCar;
    @SerializedName("driverId")
    @Expose
    private Integer driverId;
    @SerializedName("driverName")
    @Expose
    private String driverName;
    @SerializedName("reasonCaptainCar")
    @Expose
    private String reasonCaptainCar;

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

    public String getLicenseCar() {
        return licenseCar;
    }

    public void setLicenseCar(String licenseCar) {
        this.licenseCar = licenseCar;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getReasonCaptainCar() {
        return reasonCaptainCar;
    }

    public void setReasonCaptainCar(String reasonCaptainCar) {
        this.reasonCaptainCar = reasonCaptainCar;
    }

}
