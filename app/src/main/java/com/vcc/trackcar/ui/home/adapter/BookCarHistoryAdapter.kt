package com.vcc.trackcar.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vcc.trackcar.R
import com.vcc.trackcar.model.getHistoryCar.BookCarHistory
import kotlinx.android.synthetic.main.item_history_book_car.view.*

class BookCarHistoryAdapter(var recyclerViewItemClickListener: RecyclerViewItemClickListener) : RecyclerView.Adapter<BookCarHistoryAdapter.BookCarViewHolder>() {

    private var listData: ArrayList<BookCarHistory> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookCarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history_book_car, parent, false)
        return BookCarViewHolder(view)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: BookCarViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    fun swapData(listBookCarHistory: List<BookCarHistory>) {
        listData.clear()
        listData.addAll(listBookCarHistory)
        notifyDataSetChanged()
    }

    inner class BookCarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bookCarHistory: BookCarHistory) {
            itemView.tv_ma_lenh.text = bookCarHistory.code
            itemView.tv_diem_di.text = bookCarHistory.fromAddress
            itemView.tv_diem_den.text = bookCarHistory.toAddress

            itemView.setOnClickListener {
                recyclerViewItemClickListener.clickOnItemBookCarHistory(bookCarHistory)
            }
        }

    }

    interface RecyclerViewItemClickListener {
        fun clickOnItemBookCarHistory(bookCarHistory: BookCarHistory)
    }
}