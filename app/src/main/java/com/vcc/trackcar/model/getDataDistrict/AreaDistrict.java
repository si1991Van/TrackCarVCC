package com.vcc.trackcar.model.getDataDistrict;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AreaDistrict {

    @SerializedName("defaultSortField")
    @Expose
    private String defaultSortField;
    @SerializedName("messageColumn")
    @Expose
    private Integer messageColumn;
    @SerializedName("areaId")
    @Expose
    private Integer areaId;
    @SerializedName("codeLocation")
    @Expose
    private String codeLocation;
    @SerializedName("nameLocation")
    @Expose
    private String nameLocation;
    @SerializedName("provinceId")
    @Expose
    private Integer provinceId;
    @SerializedName("areaLevel")
    @Expose
    private String areaLevel;

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

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getCodeLocation() {
        return codeLocation;
    }

    public void setCodeLocation(String codeLocation) {
        this.codeLocation = codeLocation;
    }

    public String getNameLocation() {
        return nameLocation;
    }

    public void setNameLocation(String nameLocation) {
        this.nameLocation = nameLocation;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(String areaLevel) {
        this.areaLevel = areaLevel;
    }

}
