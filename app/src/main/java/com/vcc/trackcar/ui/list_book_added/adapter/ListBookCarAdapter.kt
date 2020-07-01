package com.vcc.trackcar.ui.list_book_added.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vcc.trackcar.R
import com.vcc.trackcar.model.getListBookCar.LstBookCarDto
import com.vcc.trackcar.ui.base.App.getContext
import com.vcc.trackcar.ui.giao_viec.GiaoViecFragment
import com.vcc.trackcar.ui.view_and_sign_approval.ViewAndSignApprovalFragment
import com.vcc.trackcar.ui.view_and_sign_approval_manager_car.ViewAndApprovalManagerCarFragment
import com.vcc.trackcar.ui.xem_ky_duyet_tp_hanhchinh_tct.XemKyDuyetTPHCTCTFragment
import com.vcc.trackcar.ui.xep_xe_ban_tct.XepXeBanTCTFragment
import com.vcc.trackcar.ui.xep_xe_doi_truong_xe.XepXeFragment
import kotlinx.android.synthetic.main.book_car_item_new.view.*

class ListBookCarAdapter(
    var mContext: Context, var onItemBookListener: OnItemBookListener, var typeMenu: Int
) : RecyclerView.Adapter<ListBookCarAdapter.BookCarViewHolder>() {

    private var listData: ArrayList<LstBookCarDto> = arrayListOf()

    inner class BookCarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bookCarDto: LstBookCarDto) {
            itemView.tv_pos.text = "${adapterPosition + 1}. "
            itemView.tv_code.text = bookCarDto.code
            itemView.tv_name_email.text = bookCarDto.fullName
            itemView.tv_from.text = bookCarDto.fromAddress
            itemView.tv_to.text = bookCarDto.toAddress
            itemView.tv_time_start.text = bookCarDto.startTime
            itemView.tv_time_end.text = bookCarDto.endTime

            if (typeMenu == XepXeFragment.TYPE_MENU) { // Xếp xe( Quản lý đội xe)
                when (bookCarDto.statusCaptainCar) {
                    "1" -> {
                        itemView.tv_state.text = mContext.getString(R.string.dang_cho_xep)
                        itemView.tv_state.setTextColor(Color.GRAY)
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_grey_24dp)

                        itemView.layout_reason.visibility = View.GONE
                        itemView.view_layout_reason.visibility = View.GONE
                    }
                    "2" -> {
                        itemView.tv_state.text = mContext.getString(R.string.da_duoc_xep)
                        itemView.tv_state.setTextColor(Color.GREEN)
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_green_16dp)

                        itemView.layout_reason.visibility = View.GONE
                        itemView.view_layout_reason.visibility = View.GONE
                    }
                    "3" -> {
                        itemView.tv_state.text = mContext.getString(R.string.tu_choi)
                        itemView.tv_state.setTextColor(Color.RED)
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_red_24dp)
                    }
                    "4" -> {
                        itemView.tv_state.text = mContext.getString(R.string.yeu_cau_sua)
                        itemView.tv_state.setTextColor(
                            ContextCompat.getColor(getContext(), R.color.sunshade)
                        )
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_yellow_24dp)
                    }
                }
                itemView.tv_reason.text = bookCarDto.reasonCaptainCar
                if (bookCarDto.reasonCaptainCar.isNullOrEmpty()) {
                    itemView.layout_reason.visibility = View.GONE
                    itemView.view_layout_reason.visibility = View.GONE
                }
            } else if (typeMenu == XepXeBanTCTFragment.TYPE_MENU) { // Xếp xe( Ban xe TCT)
                when (bookCarDto.statusDriverBoard) {
                    "1" -> {
                        itemView.tv_state.text = mContext.getString(R.string.dang_cho_duyet)
                        itemView.tv_state.setTextColor(Color.GRAY)
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_grey_24dp)

                        itemView.tv_reason.visibility = View.GONE
                    }
                    "2" -> {
                        itemView.tv_state.text = mContext.getString(R.string.da_duoc_duyet)
                        itemView.tv_state.setTextColor(Color.GREEN)
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_green_16dp)

                        itemView.tv_reason.visibility = View.GONE
                    }
                    "3" -> {
                        itemView.tv_state.text = mContext.getString(R.string.tu_choi)
                        itemView.tv_state.setTextColor(Color.RED)
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_red_24dp)
                    }
                    "4" -> {
                        itemView.tv_state.text = mContext.getString(R.string.yeu_cau_sua)
                        itemView.tv_state.setTextColor(
                            ContextCompat.getColor(getContext(), R.color.sunshade)
                        )
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_yellow_24dp)
                    }
                }
                itemView.tv_reason.text = bookCarDto.reasonDriverBoard
                if (bookCarDto.reasonDriverBoard.isNullOrEmpty()) {
                    itemView.layout_reason.visibility = View.GONE
                    itemView.view_layout_reason.visibility = View.GONE
                }
            } else if (typeMenu == XemKyDuyetTPHCTCTFragment.TYPE_MENU) { // Xem và ký duyệt xe(TP Hành chính TCT)
                when (bookCarDto.statusAdministrative) {
                    "1" -> {
                        itemView.tv_state.text = mContext.getString(R.string.dang_cho_duyet)
                        itemView.tv_state.setTextColor(Color.GRAY)
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_grey_24dp)

                        itemView.tv_reason.visibility = View.GONE
                    }
                    "2" -> {
                        itemView.tv_state.text = mContext.getString(R.string.da_duoc_duyet)
                        itemView.tv_state.setTextColor(Color.GREEN)
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_green_16dp)

                        itemView.tv_reason.visibility = View.GONE
                    }
                    "3" -> {
                        itemView.tv_state.text = mContext.getString(R.string.tu_choi)
                        itemView.tv_state.setTextColor(Color.RED)
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_red_24dp)
                    }
                    "4" -> {
                        itemView.tv_state.text = mContext.getString(R.string.yeu_cau_sua)
                        itemView.tv_state.setTextColor(
                            ContextCompat.getColor(getContext(), R.color.sunshade)
                        )
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_yellow_24dp)
                    }
                }
                itemView.tv_reason.text = bookCarDto.reasonAdministrative
                if (bookCarDto.reasonAdministrative.isNullOrEmpty()) {
                    itemView.layout_reason.visibility = View.GONE
                    itemView.view_layout_reason.visibility = View.GONE
                }
            } else if (typeMenu == ViewAndApprovalManagerCarFragment.TYPE_MENU) { // Xem và ký duyệt xe(Thủ trưởng đơn vị xe)
                when (bookCarDto.statusManagerCar) {
                    "1" -> {
                        itemView.tv_state.text = mContext.getString(R.string.dang_cho_duyet)
                        itemView.tv_state.setTextColor(Color.GRAY)
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_grey_24dp)

                        itemView.tv_reason.visibility = View.GONE
                    }
                    "2" -> {
                        itemView.tv_state.text = mContext.getString(R.string.da_duoc_duyet)
                        itemView.tv_state.setTextColor(Color.GREEN)
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_green_16dp)

                        itemView.tv_reason.visibility = View.GONE
                    }
                    "3" -> {
                        itemView.tv_state.text = mContext.getString(R.string.tu_choi)
                        itemView.tv_state.setTextColor(Color.RED)
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_red_24dp)
                    }
                    "4" -> {
                        itemView.tv_state.text = mContext.getString(R.string.yeu_cau_sua)
                        itemView.tv_state.setTextColor(
                            ContextCompat.getColor(getContext(), R.color.sunshade)
                        )
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_yellow_24dp)
                    }
                }
                itemView.tv_reason.text = bookCarDto.reasonManagerCar
                if (bookCarDto.reasonManagerCar.isNullOrEmpty()) {
                    itemView.layout_reason.visibility = View.GONE
                    itemView.view_layout_reason.visibility = View.GONE
                }
            } else if (typeMenu == GiaoViecFragment.TYPE_MENU) { // Giao viêc (lái xe)
                if (!bookCarDto.statusDriver.isNullOrEmpty()) {
                    itemView.layout_status.visibility = View.VISIBLE

                    if (bookCarDto.typeBookCar == "1") {
                        if (bookCarDto.statusDriver == "3") {
                            itemView.tv_state.text = mContext.getString(R.string.da_tu_choi)
                            itemView.tv_state.setTextColor(Color.RED)
                        } else if (bookCarDto.statusDriver == "4") {
                            itemView.tv_state.text = mContext.getString(R.string.yeu_cau_sua)
                            itemView.tv_state.setTextColor(
                                ContextCompat.getColor(getContext(), R.color.sunshade)
                            )
                        } else if (bookCarDto.statusDriver == "5") {
                            itemView.tv_state.text = mContext.getString(R.string.da_dong_lenh)
                            itemView.tv_state.setTextColor(Color.GREEN)
                        } else if (bookCarDto.statusDriver == "2" && bookCarDto.statusManagerCar == "2") {
                            itemView.tv_state.text = mContext.getString(R.string.cho_dong_lenh)
                            itemView.tv_state.setTextColor(Color.GRAY)
                        } else {
                            itemView.tv_state.text = mContext.getString(R.string.da_duoc_duyet)
                            itemView.tv_state.setTextColor(Color.GREEN)
                        }
                    } else {
                        if (bookCarDto.statusDriver == "3") {
                            itemView.tv_state.text = mContext.getString(R.string.da_tu_choi)
                            itemView.tv_state.setTextColor(Color.RED)
                        } else if (bookCarDto.statusDriver == "4") {
                            itemView.tv_state.text = mContext.getString(R.string.yeu_cau_sua)
                            itemView.tv_state.setTextColor(
                                ContextCompat.getColor(getContext(), R.color.sunshade)
                            )
                        } else {
                            itemView.tv_state.text = mContext.getString(R.string.da_duoc_duyet)
                            itemView.tv_state.setTextColor(Color.GREEN)
                        }
                    }

                } else {
                    itemView.layout_status.visibility = View.GONE
                    itemView.view_layout_status.visibility = View.GONE
                }

                itemView.layout_reason.visibility = View.GONE
                itemView.view_layout_reason.visibility = View.GONE

                itemView.layout_nguoi_yeu_cau.visibility = View.VISIBLE
                itemView.view_layout_nguoi_yeu_cau.visibility = View.GONE

                itemView.layout_nguoi_tao.visibility = View.GONE

                itemView.tv_nguoi_yeu_cau.text = bookCarDto.managerCarName
            } else if (typeMenu == ViewAndSignApprovalFragment.TYPE_MENU) { // Xem va ky duyet T/P phong
                when (bookCarDto.statusManage) {
                    "1" -> {
                        itemView.tv_state.text = mContext.getString(R.string.dang_cho_duyet)
                        itemView.tv_state.setTextColor(Color.GRAY)
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_grey_24dp)

                        itemView.tv_reason.visibility = View.GONE
                    }
                    "2" -> {
                        itemView.tv_state.text = mContext.getString(R.string.da_duoc_duyet)
                        itemView.tv_state.setTextColor(Color.GREEN)
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_green_16dp)

                        itemView.tv_reason.visibility = View.GONE
                    }
                    "3" -> {
                        itemView.tv_state.text = mContext.getString(R.string.tu_choi)
                        itemView.tv_state.setTextColor(Color.RED)
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_red_24dp)
                    }
                    "4" -> {
                        itemView.tv_state.text = mContext.getString(R.string.yeu_cau_sua)
                        itemView.tv_state.setTextColor(
                            ContextCompat.getColor(getContext(), R.color.sunshade)
                        )
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_yellow_24dp)
                    }
                    "5" -> {
                        itemView.tv_state.text = mContext.getString(R.string.da_dong_lenh)
                        itemView.tv_state.setTextColor(Color.GRAY)
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_nv_close_24dp)

                        itemView.layout_reason.visibility = View.GONE
                        itemView.view_layout_reason.visibility = View.GONE
                    }
                }
                itemView.tv_reason.text = bookCarDto.reasonManage
                if (bookCarDto.reasonManage.isNullOrEmpty()) {
                    itemView.layout_reason.visibility = View.GONE
                    itemView.view_layout_reason.visibility = View.GONE
                }
            } else { // Danh sach lenh da lap nv
                when (bookCarDto.status) {
                    "1" -> {
                        itemView.tv_state.text = mContext.getString(R.string.dang_cho_duyet)
                        itemView.tv_state.setTextColor(Color.GRAY)
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_grey_24dp)

                        itemView.layout_reason.visibility = View.GONE
                        itemView.view_layout_reason.visibility = View.GONE
                    }
                    "2" -> {
                        itemView.tv_state.text = mContext.getString(R.string.da_duoc_duyet)
                        itemView.tv_state.setTextColor(Color.GREEN)
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_green_16dp)

                        itemView.layout_reason.visibility = View.GONE
                        itemView.view_layout_reason.visibility = View.GONE
                    }
                    "3" -> {
                        itemView.tv_state.text = mContext.getString(R.string.tu_choi)
                        itemView.tv_state.setTextColor(Color.RED)
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_red_24dp)
                    }
                    "4" -> {
                        itemView.tv_state.text = mContext.getString(R.string.yeu_cau_sua)
                        itemView.tv_state.setTextColor(
                            ContextCompat.getColor(getContext(), R.color.sunshade)
                        )
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_yellow_24dp)
                    }
                    "5" -> {
                        itemView.tv_state.text = mContext.getString(R.string.nhan_vien_dong_lenh)
                        itemView.tv_state.setTextColor(Color.GRAY)
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_nv_close_24dp)

                        itemView.layout_reason.visibility = View.GONE
                        itemView.view_layout_reason.visibility = View.GONE
                    }
                    "6" -> {
                        itemView.tv_state.text = mContext.getString(R.string.lai_xe_dong_lenh)
                        itemView.tv_state.setTextColor(Color.GRAY)
                        itemView.imv_radio_status.setImageResource(R.drawable.ic_radio_button_lx_close_24dp)

                        itemView.layout_reason.visibility = View.GONE
                        itemView.view_layout_reason.visibility = View.GONE
                    }
                }

                if (!bookCarDto.reasonManage.isNullOrEmpty()) {
                    itemView.tv_reason.text = bookCarDto.reasonManage
                } else if (!bookCarDto.reasonManagerCar.isNullOrEmpty()) {
                    itemView.tv_reason.text = bookCarDto.reasonManagerCar
                } else if (!bookCarDto.reasonCaptainCar.isNullOrEmpty()) {
                    itemView.tv_reason.text = bookCarDto.reasonCaptainCar
                } else if (!bookCarDto.reasonDriverBoard.isNullOrEmpty()) {
                    itemView.tv_reason.text = bookCarDto.reasonDriverBoard
                } else if (!bookCarDto.reasonAdministrative.isNullOrEmpty()) {
                    itemView.tv_reason.text = bookCarDto.reasonAdministrative
                } else {
                    itemView.layout_reason.visibility = View.GONE
                    itemView.view_layout_reason.visibility = View.GONE
                }
            }

            itemView.setOnClickListener {
                onItemBookListener.omItemBookCar(bookCarDto)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookCarViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.book_car_item_new, parent, false)
        return BookCarViewHolder(view)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: BookCarViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    fun swapData(listBookCar: List<LstBookCarDto>) {
        this.listData.clear()
        this.listData.addAll(listBookCar)
        notifyDataSetChanged()
    }
}

interface OnItemBookListener {
    fun omItemBookCar(bookCarDto: LstBookCarDto)
}