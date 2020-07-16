package com.vcc.trackcar.model.getHistoryCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarInfoHistory {
    @SerializedName("carId")
    @Expose
    private Integer carId;
    @SerializedName("licenseCar")
    @Expose
    private String licenseCar;
    @SerializedName("fromTimeSearch")
    @Expose
    private String fromTimeSearch;
    @SerializedName("toTimeSearch")
    @Expose
    private String toTimeSearch;
    @SerializedName("sysGroupId")
    @Expose
    private String sysGroupId;

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

    public String getFromTimeSearch() {
        return fromTimeSearch;
    }

    public void setFromTimeSearch(String fromTimeSearch) {
        this.fromTimeSearch = fromTimeSearch;
    }

    public String getToTimeSearch() {
        return toTimeSearch;
    }

    public void setToTimeSearch(String toTimeSearch) {
        this.toTimeSearch = toTimeSearch;
    }
}
