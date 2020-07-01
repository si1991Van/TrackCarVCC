package com.vcc.trackcar.model.getListDriverCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookCarDto {

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
