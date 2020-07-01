package com.vcc.trackcar.ui.select_address.model;

import java.io.Serializable;

public class DataSelectAddress implements Serializable {
    String data;
    Integer type;

    public DataSelectAddress() {
        this.data = "";
        this.type = 0;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
