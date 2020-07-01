package com.vcc.trackcar.model.getDataProvinceCity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vcc.trackcar.model.auth.ResultInfo;

import java.util.List;

public class GetDataProvinceCityRespon {
    @SerializedName("resultInfo")
    @Expose
    private ResultInfo resultInfo;
    @SerializedName("lstBookCarDto")
    @Expose
    private Object lstBookCarDto;
    @SerializedName("bookCarDto")
    @Expose
    private Object bookCarDto;
    @SerializedName("areaProvinceCity")
    @Expose
    private List<AreaProvinceCity> areaProvinceCity = null;
    @SerializedName("areaDistrict")
    @Expose
    private Object areaDistrict;
    @SerializedName("areaWard")
    @Expose
    private Object areaWard;

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public Object getLstBookCarDto() {
        return lstBookCarDto;
    }

    public void setLstBookCarDto(Object lstBookCarDto) {
        this.lstBookCarDto = lstBookCarDto;
    }

    public Object getBookCarDto() {
        return bookCarDto;
    }

    public void setBookCarDto(Object bookCarDto) {
        this.bookCarDto = bookCarDto;
    }

    public List<AreaProvinceCity> getAreaProvinceCity() {
        return areaProvinceCity;
    }

    public void setAreaProvinceCity(List<AreaProvinceCity> areaProvinceCity) {
        this.areaProvinceCity = areaProvinceCity;
    }

    public Object getAreaDistrict() {
        return areaDistrict;
    }

    public void setAreaDistrict(Object areaDistrict) {
        this.areaDistrict = areaDistrict;
    }

    public Object getAreaWard() {
        return areaWard;
    }

    public void setAreaWard(Object areaWard) {
        this.areaWard = areaWard;
    }
}
