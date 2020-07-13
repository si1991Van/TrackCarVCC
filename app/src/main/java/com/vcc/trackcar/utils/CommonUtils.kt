package com.vcc.trackcar.utils

import com.vcc.trackcar.model.addBookCar.BookCarDto
import com.vcc.trackcar.model.getListBookCar.LstBookCarDto
import java.util.ArrayList

object CommonUtils {

    fun checkInternalProvince(strTo: String?, strFrom: String?, bookCarDto: BookCarDto?){
        if (strTo == strFrom) {
            bookCarDto?.internalProvince = 1
        } else {
            bookCarDto?.internalProvince = 2
        }
    }

    fun searchBookCarDTO(keyWord: String, listData: List<LstBookCarDto>): List<LstBookCarDto> {
        var keyWord = keyWord
        val dataSearch = ArrayList<LstBookCarDto>()
        keyWord = StringUtil.removeAccentStr(keyWord.trim { it <= ' ' }).toLowerCase()
        for (dto in listData) {
            var licenseCar = ""
            val code = ""
            if (dto.licenseCar != null) {
                licenseCar = dto.licenseCar.toLowerCase()
            }
            if (dto.code != null) {
                licenseCar = dto.code.toLowerCase()
            }
            if (licenseCar.contains(keyWord) || code.contains(keyWord))
                dataSearch.add(dto)

        }
        return dataSearch
    }
}