package com.vcc.trackcar.model.getListManager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetListManagerBody {
    @SerializedName("sysGroupId")
    @Expose
    private Integer sysGroupId;
    @SerializedName("departmentId")
    @Expose
    private Integer departmentId;

    public Integer getSysGroupId() {
        return sysGroupId;
    }

    public void setSysGroupId(Integer sysGroupId) {
        this.sysGroupId = sysGroupId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
}
