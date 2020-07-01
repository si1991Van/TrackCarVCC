package com.vcc.trackcar.model.getHistoryCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookCarHistory {
    @SerializedName("defaultSortField")
    @Expose
    private String defaultSortField;
    @SerializedName("messageColumn")
    @Expose
    private Integer messageColumn;
    @SerializedName("bookCarId")
    @Expose
    private Integer bookCarId;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("fromAddress")
    @Expose
    private String fromAddress;
    @SerializedName("toAddress")
    @Expose
    private String toAddress;
    @SerializedName("licenseCar")
    @Expose
    private String licenseCar;
    @SerializedName("fwmodelId")
    @Expose
    private Integer fwmodelId;

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

    public Integer getBookCarId() {
        return bookCarId;
    }

    public void setBookCarId(Integer bookCarId) {
        this.bookCarId = bookCarId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getLicenseCar() {
        return licenseCar;
    }

    public void setLicenseCar(String licenseCar) {
        this.licenseCar = licenseCar;
    }

    public Integer getFwmodelId() {
        return fwmodelId;
    }

    public void setFwmodelId(Integer fwmodelId) {
        this.fwmodelId = fwmodelId;
    }
}
