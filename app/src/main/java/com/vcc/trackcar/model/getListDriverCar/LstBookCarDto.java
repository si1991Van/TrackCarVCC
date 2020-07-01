package com.vcc.trackcar.model.getListDriverCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class LstBookCarDto implements Serializable {

    @SerializedName("defaultSortField")
    @Expose
    private String defaultSortField;
    @SerializedName("messageColumn")
    @Expose
    private Integer messageColumn;
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
    @SerializedName("driverCode")
    @Expose
    private String driverCode;
    @SerializedName("longtitudeCar")
    @Expose
    private Double longtitudeCar;
    @SerializedName("latitudeCar")
    @Expose
    private Double latitudeCar;
    @SerializedName("onCommandCar")
    @Expose
    private String onCommandCar;
    @SerializedName("onCommandDriver")
    @Expose
    private String onCommandDriver;
    @SerializedName("longtitudeDriver")
    @Expose
    private Double longtitudeDriver;
    @SerializedName("latitudeDriver")
    @Expose
    private Double latitudeDriver;
    @SerializedName("phoneNumberDriver")
    @Expose
    private String phoneNumberDriver;
    @SerializedName("driverEmail")
    @Expose
    private String driverEmail;

    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public String getDefaultSortField() {
        return defaultSortField;
    }

    public void setDefaultSortField(String defaultSortField) {
        this.defaultSortField = defaultSortField;
    }

    public Integer getMessageColumn() {
        return messageColumn;
    }

    public void setMessageColumn(Integer messageColumn) {
        this.messageColumn = messageColumn;
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

    public String getDriverCode() {
        return driverCode;
    }

    public void setDriverCode(String driverCode) {
        this.driverCode = driverCode;
    }

    public Double getLongtitudeCar() {
        return longtitudeCar;
    }

    public void setLongtitudeCar(Double longtitudeCar) {
        this.longtitudeCar = longtitudeCar;
    }

    public Double getLatitudeCar() {
        return latitudeCar;
    }

    public void setLatitudeCar(Double latitudeCar) {
        this.latitudeCar = latitudeCar;
    }

    public String getOnCommandCar() {
        return onCommandCar;
    }

    public void setOnCommandCar(String onCommandCar) {
        this.onCommandCar = onCommandCar;
    }

    public String getOnCommandDriver() {
        return onCommandDriver;
    }

    public void setOnCommandDriver(String onCommandDriver) {
        this.onCommandDriver = onCommandDriver;
    }

    public Double getLongtitudeDriver() {
        return longtitudeDriver;
    }

    public void setLongtitudeDriver(Double longtitudeDriver) {
        this.longtitudeDriver = longtitudeDriver;
    }

    public Double getLatitudeDriver() {
        return latitudeDriver;
    }

    public void setLatitudeDriver(Double latitudeDriver) {
        this.latitudeDriver = latitudeDriver;
    }

    public String getPhoneNumberDriver() {
        return phoneNumberDriver;
    }

    public void setPhoneNumberDriver(String phoneNumberDriver) {
        this.phoneNumberDriver = phoneNumberDriver;
    }
}