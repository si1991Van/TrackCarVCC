package com.vcc.trackcar.model.getDataWard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vcc.trackcar.model.auth.ResultInfo;
import com.vcc.trackcar.model.getDataProvinceCity.AreaProvinceCity;

import java.util.List;

public class GetDataWardRespon {
    @SerializedName("resultInfo")
    @Expose
    private ResultInfo resultInfo;
    @SerializedName("areaWard")
    @Expose
    private List<AreaProvinceCity> areaWard = null;

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public List<AreaProvinceCity> getAreaWard() {
        return areaWard;
    }

    public void setAreaWard(List<AreaProvinceCity> areaWard) {
        this.areaWard = areaWard;
    }
}
