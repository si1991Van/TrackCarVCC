package com.vcc.trackcar.ui.giam_sat_xe

import androidx.lifecycle.ViewModel
import com.vcc.trackcar.model.CatVehicleDTO
import com.vcc.trackcar.model.addBookCar.BookCarDto

class SuperviseViewModel: ViewModel() {

    var listUnit : List<BookCarDto>? = ArrayList()
    var listCar : List<CatVehicleDTO>? = ArrayList()
    fun listCarStatus(): List<CarStatusDTO>?{
        val listCarStatus : List<CarStatusDTO>? = ArrayList()
        listCarStatus?.toMutableList()?.add(CarStatusDTO(0, "Đang chạy"))
        listCarStatus?.toMutableList()?.add(CarStatusDTO(1, "Dừng"))
        listCarStatus?.toMutableList()?.add(CarStatusDTO(2, "Đỗ"))
        listCarStatus?.toMutableList()?.add(CarStatusDTO(4, "Mất GPRS"))
        return listCarStatus
    }
}

class CarStatusDTO(
    var id: Int? = 0,
    var name: String? = null
)