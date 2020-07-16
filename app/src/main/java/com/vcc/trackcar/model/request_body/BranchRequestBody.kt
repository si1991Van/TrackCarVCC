package com.vcc.trackcar.model.request_body

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.vcc.trackcar.model.addBookCar.BookCarDto
import com.vcc.trackcar.model.addBookCar.SysUserRequest
import com.vcc.trackcar.model.getListBookCar.LstBookCarDto

class BranchRequestBody {
    @SerializedName("bookCarDto")
    @Expose
    var bookCarDto: BookCarDto? = null
    @SerializedName("sysUserRequest")
    @Expose
    var sysUserRequest: SysUserRequest? = null
}