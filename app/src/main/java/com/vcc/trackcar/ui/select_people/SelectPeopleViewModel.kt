package com.vcc.trackcar.ui.select_people

import androidx.lifecycle.ViewModel
import com.vcc.trackcar.model.getListUser.LstUserDto

class SelectPeopleViewModel : ViewModel() {
    lateinit var listUserAll: ArrayList<LstUserDto>
    var isOnlyOnePeopleApprove: Boolean? = false
    lateinit var listUserSelect: ArrayList<LstUserDto>
}
