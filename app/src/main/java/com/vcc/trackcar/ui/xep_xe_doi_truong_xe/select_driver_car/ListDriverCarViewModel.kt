package com.vcc.trackcar.ui.xep_xe_doi_truong_xe.select_driver_car

import androidx.lifecycle.ViewModel
import com.vcc.trackcar.model.getListBookCar.LstBookCarDto

class ListDriverCarViewModel : ViewModel() {
    var listDriverCar: List<com.vcc.trackcar.model.getListDriverCar.LstBookCarDto> = emptyList()
    // TODO: Implement the ViewModel
    lateinit var bookCarDto: LstBookCarDto
    var isSelected: Boolean = false

    var carDieuChuyen = com.vcc.trackcar.model.getListDriverCar.LstBookCarDto()
}
