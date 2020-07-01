package com.vcc.trackcar.ui.giao_viec.detail_giao_viec

import androidx.lifecycle.ViewModel
import com.vcc.trackcar.model.getListBookCar.LstBookCarDto

class DetailGiaoViecViewModel : ViewModel() {
    var typeMenu: Int? = 0
    // TODO: Implement the ViewModel
    lateinit var bookCarDto: LstBookCarDto
}
