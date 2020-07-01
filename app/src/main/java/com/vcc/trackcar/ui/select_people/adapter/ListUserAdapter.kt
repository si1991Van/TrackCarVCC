package com.vcc.trackcar.ui.select_people.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vcc.trackcar.R
import com.vcc.trackcar.model.getListUser.LstUserDto
import kotlinx.android.synthetic.main.select_people_item.view.*

class ListUserAdapter(var onItemListener: OnItemListener) :
    RecyclerView.Adapter<ListUserAdapter.UserViewHolder>() {

    private var listData: ArrayList<LstUserDto> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.select_people_item, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    fun swapData(listUserSelect: List<LstUserDto>) {
        listData.clear()
        listData.addAll(listUserSelect)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(userDto: LstUserDto) {
            itemView.tv_name_email.text = userDto.fullName
            itemView.tv_email.text = userDto.email
            itemView.tv_phone.text = userDto.phoneNumber

            itemView.setOnClickListener {
                onItemListener.onItemUserSelected(userDto)
            }
        }

    }
}

interface OnItemListener {
    fun onItemUserSelected(userDto: LstUserDto)
}