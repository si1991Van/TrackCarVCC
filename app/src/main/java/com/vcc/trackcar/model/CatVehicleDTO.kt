package com.vcc.trackcar.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CatVehicleDTO {
    @SerializedName("carId")
    @Expose
    var carId: Long? = 0
    
    @SerializedName("licenseCar")
    @Expose
    var licenseCar: String? = null
    @SerializedName("onCommand")
    @Expose
    var onCommand: String? = null
    @SerializedName("sysGroupId")
    @Expose
    var sysGroupId: Long = 0
    @SerializedName("sysGroupName")
    @Expose
    var sysGroupName: String? = null
    @SerializedName("carTypeId")
    @Expose
    var carTypeId: Long = 0
}