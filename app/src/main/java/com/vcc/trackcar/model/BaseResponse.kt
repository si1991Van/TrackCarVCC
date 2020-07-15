package com.vcc.trackcar.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.vcc.trackcar.model.auth.ResultInfo

open class BaseResponse<T> {
    @SerializedName("resultInfo")
    @Expose
    var resultInfo: ResultInfo? = null

    @SerializedName("data")
    @Expose
    var data: List<T>? = null
}