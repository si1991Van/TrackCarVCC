package com.vcc.trackcar.model.request_body

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.vcc.trackcar.model.addBookCar.BookCarDto
import com.vcc.trackcar.model.addBookCar.SysUserRequest
import com.vcc.trackcar.model.getHistoryCar.VehicleMonitoringRequest
import com.vcc.trackcar.model.getListBookCar.LstBookCarDto

class VehicleMonitoringRequestBody {
    @SerializedName("bookCarDto")
    @Expose
    var bookCarDto: VehicleMonitoringRequest? = null
    @SerializedName("sysUserRequest")
    @Expose
    var sysUserRequest: SysUserRequest? = null

}