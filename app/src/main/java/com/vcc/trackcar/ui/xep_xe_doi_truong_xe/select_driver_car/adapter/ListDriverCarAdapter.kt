package com.vcc.trackcar.ui.xep_xe_doi_truong_xe.select_driver_car.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vcc.trackcar.R
import com.vcc.trackcar.model.getListDriverCar.LstBookCarDto
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.select_drive_car_item.view.*

class ListDriverCarAdapter(var context: Context,var onItemCar: OnItemCar) :
    RecyclerView.Adapter<ListDriverCarAdapter.CarViewHolder>() {

    private var listData: ArrayList<LstBookCarDto> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.select_drive_car_item, parent, false)
        return CarViewHolder(view)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    fun swapData(driverCar: List<LstBookCarDto>) {
        listData.clear()
        listData.addAll(driverCar)
        notifyDataSetChanged()
    }

    inner class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(carDto: LstBookCarDto) {
            itemView.tv_pos.text = "${adapterPosition + 1}. "
            itemView.tv_driver_name.text = carDto.driverName
            itemView.tv_driver_email.text = carDto.driverEmail

            if (carDto.onCommandDriver == "0") itemView.layout_car.setBackgroundColor(
                ContextCompat.getColor(
                    context, R.color.magic_mint
                )
            )
            else itemView.layout_car.setBackgroundColor(
                ContextCompat.getColor(
                    context, R.color.alto
                )
            )

            itemView.setOnClickListener {
                if (carDto.onCommandDriver == "0") {
                    onItemCar.onItemDriverCar(carDto)
                } else {
                    Toasty.error(
                        context,
                        context.getString(R.string.warning_choose_driver),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }
}

interface OnItemCar {
    fun onItemDriverCar(carDto: LstBookCarDto)
}