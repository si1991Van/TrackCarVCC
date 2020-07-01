package com.vcc.trackcar.model.getDataDistrict;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyDataDistrict {
    @SerializedName("parentId")
    @Expose
    private Integer parentId;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
