package com.vcc.trackcar.utils

import com.vcc.trackcar.model.addBookCar.BookCarDto

object CommonUtils {

    fun checkInternalProvince(strTo: String?, strFrom: String?, bookCarDto: BookCarDto?){
        if (strTo == strFrom) {
            bookCarDto?.internalProvince = 1
        } else {
            bookCarDto?.internalProvince = 2
        }
    }
}