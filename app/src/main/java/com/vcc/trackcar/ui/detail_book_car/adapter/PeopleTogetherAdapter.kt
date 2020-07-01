package com.vcc.trackcar.ui.detail_book_car.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vcc.trackcar.R
import com.vcc.trackcar.model.getListBookCar.LstBookCarDto
import com.vcc.trackcar.model.getListUser.LstUserDto
import com.vcc.trackcar.ui.base.App
import kotlinx.android.synthetic.main.people_together_detail_item.view.*

class PeopleTogetherAdapter : RecyclerView.Adapter<PeopleTogetherAdapter.PeopleViewHolder>() {

    private var listData: ArrayList<LstUserDto> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): PeopleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.people_together_detail_item, parent, false)
        return PeopleViewHolder(view)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    fun swapData(lstBookCarDto: List<LstUserDto>) {
        listData.clear()
        listData.addAll(lstBookCarDto)
        notifyDataSetChanged()
    }

    fun swapDataPeopleApprove(bookCarDto: LstBookCarDto) {
        listData.clear()
        if (!bookCarDto.managerStaffName.isNullOrEmpty()) {
            if (!bookCarDto.statusAdministrative.isNullOrEmpty() && bookCarDto.statusAdministrative == "2") { // statusAdministrative da duyet lenh
                listData.add(LstUserDto().apply {
                    fullName = bookCarDto.managerStaffName
                    if (bookCarDto.statusManage == "5") {
                        status = bookCarDto.statusManage
                    } else {
                        status = "6"
                    }
                })
            } else {
                listData.add(LstUserDto().apply {
                    fullName = bookCarDto.managerStaffName
                    status = bookCarDto.statusManage
                })
            }
        }
        if (!bookCarDto.captainCarName.isNullOrEmpty()) {
            listData.add(LstUserDto().apply {
                fullName = bookCarDto.captainCarName
                status = bookCarDto.statusCaptainCar
            })
        }
        if (!bookCarDto.managerCarName.isNullOrEmpty()) {
            if (bookCarDto.typeBookCar == "3" ){ // kieu di phat sinh
                if(bookCarDto.statusCaptainCar =="2"){
                    // hien thi nv
                    listData.add(LstUserDto().apply {
                        fullName = bookCarDto.fullName
                        if (bookCarDto.status == "5") {
                            status = bookCarDto.status
                        } else {
                            status = "6"
                        }
                    })
                }

                if (bookCarDto.status == "5"){
                    // hien thi thu truong
                    listData.add(LstUserDto().apply {
                        fullName = bookCarDto.managerCarName
                        status = bookCarDto.statusManagerCar
                    })
                }

            } else {
                listData.add(LstUserDto().apply {
                    fullName = bookCarDto.managerCarName
                    status = bookCarDto.statusManagerCar
                })
            }
        }
        if (!bookCarDto.driverBoardName.isNullOrEmpty()) {
            listData.add(LstUserDto().apply {
                fullName = bookCarDto.driverBoardName
                status = bookCarDto.statusDriverBoard
            })
        }
        if (!bookCarDto.administrativeName.isNullOrEmpty()) {
            listData.add(LstUserDto().apply {
                fullName = bookCarDto.administrativeName
                status = bookCarDto.statusAdministrative
            })
        }

        if (bookCarDto.typeBookCar == "2") { // kieu di 2 chieu
            if (!bookCarDto.managerCarName.isNullOrEmpty() && bookCarDto.statusManagerCar == "2") { // managerCarName da phe duyet
                listData.add(LstUserDto().apply {
                    fullName = bookCarDto.fullName
                    if (bookCarDto.status == "5") {
                        status = bookCarDto.status
                    } else {
                        status = "6"
                    }
                })
            }
        } else if (bookCarDto.typeBookCar == "1") { // kieu di 1 chieu
            if (!bookCarDto.managerCarName.isNullOrEmpty() && bookCarDto.statusManagerCar == "2") { // managerCarName da phe duyet
                listData.add(LstUserDto().apply {
                    fullName = bookCarDto.fullName
                    if (bookCarDto.status == "5") {
                        status = bookCarDto.status
                    } else {
                        status = "6"
                    }
                })
            }
            if (!bookCarDto.status.isNullOrEmpty() && bookCarDto.status == "5") { // nhan vien dong lenh
                listData.add(LstUserDto().apply {
                    fullName = bookCarDto.driverName
                    if (!bookCarDto.statusDriver.isNullOrEmpty() && bookCarDto.statusDriver == "5") {
                        status = bookCarDto.statusDriver
                    } else {
                        status = "6"
                    }
                })
            }
        }

        notifyDataSetChanged()
    }

    inner class PeopleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(userDto: LstUserDto) {
            itemView.text_name.text = "${adapterPosition + 1}. ${userDto.fullName}"
            when (userDto.status) {
                "1" -> {
                    itemView.text_status.text = itemView.context.getString(R.string.dang_cho_duyet)
                    itemView.text_status.setTextColor(Color.GRAY)
                }
                "2" -> {
                    itemView.text_status.text = itemView.context.getString(R.string.da_duoc_duyet)
                    itemView.text_status.setTextColor(Color.GREEN)
                }
                "3" -> {
                    itemView.text_status.text = itemView.context.getString(R.string.tu_choi)
                    itemView.text_status.setTextColor(Color.RED)
                }
                "4" -> {
                    itemView.text_status.text = itemView.context.getString(R.string.yeu_cau_sua)
                    itemView.text_status.setTextColor(
                        ContextCompat.getColor(
                            App.getContext(), R.color.sunshade
                        )
                    )
                }
                "5" -> {
                    itemView.text_status.text = itemView.context.getString(R.string.da_dong_lenh)
                    itemView.text_status.setTextColor(Color.GREEN)
                }
                "6" -> {
                    itemView.text_status.text = itemView.context.getString(R.string.cho_dong_lenh)
                    itemView.text_status.setTextColor(Color.GRAY)
                }
            }
        }

    }
}