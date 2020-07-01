package com.vcc.trackcar.model.getListCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetListCarBodyDto {
    @SerializedName("sysGroupId")
    @Expose
    private Integer sysGroupId;

    public Integer getSysGroupId() {
        return sysGroupId;
    }

    public void setSysGroupId(Integer sysGroupId) {
        this.sysGroupId = sysGroupId;
    }
}
