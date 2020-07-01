package com.vcc.trackcar.model.getHistoryCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MakerCar {
    @SerializedName("defaultSortField")
    @Expose
    private String defaultSortField;
    @SerializedName("messageColumn")
    @Expose
    private Integer messageColumn;
    @SerializedName("sysUserId")
    @Expose
    private Integer sysUserId;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("driverId")
    @Expose
    private Integer driverId;
    @SerializedName("driverName")
    @Expose
    private String driverName;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longtitude")
    @Expose
    private String longtitude;
    @SerializedName("utcTime")
    @Expose
    private String utcTime;
    @SerializedName("content")
    @Expose
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Integer getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getUtcTime() {
        return utcTime;
    }

    public void setUtcTime(String utcTime) {
        this.utcTime = utcTime;
    }
}
