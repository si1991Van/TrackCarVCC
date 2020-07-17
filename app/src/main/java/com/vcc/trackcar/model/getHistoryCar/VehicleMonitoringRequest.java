package com.vcc.trackcar.model.getHistoryCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleMonitoringRequest {
    @SerializedName("carId")
    @Expose
    private Integer carId;

    @SerializedName("licenseCar")
    @Expose
    private String licenseCar;

    @SerializedName("sysGroupId")
    @Expose
    private long sysGroupId;

    @SerializedName("listStatus")
    @Expose
    private String listStatus;

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

    public long getSysGroupId() {
        return sysGroupId;
    }

    public void setSysGroupId(long sysGroupId) {
        this.sysGroupId = sysGroupId;
    }

    public String getListStatus() {
        return listStatus;
    }

    public void setListStatus(String listStatus) {
        this.listStatus = listStatus;
    }
}
