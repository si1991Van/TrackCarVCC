package com.vcc.trackcar.model.getListCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetListCarBody {
    @SerializedName("bookCarDto")
    @Expose
    private GetListCarBodyDto getListCarBodyDto;

    public GetListCarBodyDto getGetListCarBodyDto() {
        return getListCarBodyDto;
    }

    public void setGetListCarBodyDto(GetListCarBodyDto getListCarBodyDto) {
        this.getListCarBodyDto = getListCarBodyDto;
    }
}
