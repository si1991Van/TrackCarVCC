package com.vcc.trackcar.ui.add_book_car.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vcc.trackcar.R
import com.vcc.trackcar.model.getListUser.LstUserDto
import com.vcc.trackcar.ui.base.App
import kotlinx.android.synthetic.main.people_together_item.view.*

class PeopleSelectAdapter(
    var onItemPeopleListener: OnItemPeopleListener,
    var isPeopleTogether: Boolean
) :
    RecyclerView.Adapter<PeopleSelectAdapter.PeopleViewHolder>() {

    private var listData: ArrayList<LstUserDto> = arrayListOf()

    inner class PeopleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(lstUserDto: LstUserDto) {
            itemView.text_name.text = "${adapterPosition + 1}. ${lstUserDto.fullName}"
            when (lstUserDto.status) {
                "1" -> {
                    itemView.text_status.visibility = View.VISIBLE
                    itemView.imv_delete.visibility = View.GONE
                    itemView.text_status.text = itemView.context.getString(R.string.dang_cho_duyet)
                    itemView.text_status.setTextColor(Color.GRAY)
                }
                "2" -> {
                    itemView.text_status.visibility = View.VISIBLE
                    itemView.imv_delete.visibility = View.GONE
                    itemView.text_status.text = itemView.context.getString(R.string.da_duoc_duyet)
                    itemView.text_status.setTextColor(Color.GREEN)
                }
                "3" -> {
                    itemView.text_status.visibility = View.VISIBLE
                    itemView.imv_delete.visibility = View.GONE
                    itemView.text_status.text = itemView.context.getString(R.string.tu_choi)
                    itemView.text_status.setTextColor(Color.RED)
                }
                "4" -> {
                    itemView.text_status.visibility = View.VISIBLE
                    itemView.imv_delete.visibility = View.GONE
                    itemView.text_status.text = itemView.context.getString(R.string.yeu_cau_sua)
                    itemView.text_status.setTextColor(
                        ContextCompat.getColor(
                            App.getContext(), R.color.sunshade
                        )
                    )
                }
                "5" -> {
                    itemView.text_status.visibility = View.VISIBLE
                    itemView.imv_delete.visibility = View.GONE
                    itemView.text_status.text = itemView.context.getString(R.string.da_dong_lenh)
                    itemView.text_status.setTextColor(Color.GREEN)
                }
                "6" -> {
                    itemView.text_status.visibility = View.VISIBLE
                    itemView.imv_delete.visibility = View.GONE
                    itemView.text_status.text = itemView.context.getString(R.string.cho_dong_lenh)
                    itemView.text_status.setTextColor(Color.GRAY)
                }
            }

            itemView.imv_delete.setOnClickListener {
                onItemPeopleListener.onItemPeopleDelete(lstUserDto, isPeopleTogether)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.people_together_item, parent, false)
        return PeopleViewHolder(view)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    fun swapData(listPeopleTogetherSelected: List<LstUserDto>) {
        listData.clear()
        listData.addAll(listPeopleTogetherSelected)
        notifyDataSetChanged()
    }
}

interface OnItemPeopleListener {
    fun onItemPeopleDelete(lstUserDto: LstUserDto, isPeopleTogether: Boolean)
}
