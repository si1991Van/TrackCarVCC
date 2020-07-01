package com.vcc.trackcar.model.getDataDistrict;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vcc.trackcar.model.auth.ResultInfo;
import com.vcc.trackcar.model.getDataProvinceCity.AreaProvinceCity;

import java.util.List;

public class GetDataDistrictRespon {
    @SerializedName("resultInfo")
    @Expose
    private ResultInfo resultInfo;
    @SerializedName("areaDistrict")
    @Expose
    private List<AreaProvinceCity> areaDistrict = null;
    @SerializedName("areaWard")
    @Expose
    private Object areaWard;

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public List<AreaProvinceCity> getAreaDistrict() {
        return areaDistrict;
    }

    public void setAreaDistrict(List<AreaProvinceCity> areaDistrict) {
        this.areaDistrict = areaDistrict;
    }

    public Object getAreaWard() {
        return areaWard;
    }

    public void setAreaWard(Object areaWard) {
        this.areaWard = areaWard;
    }
}
