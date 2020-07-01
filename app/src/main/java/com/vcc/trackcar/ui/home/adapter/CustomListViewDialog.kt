package com.vcc.trackcar.ui.home.adapter

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vcc.trackcar.R
import kotlinx.android.synthetic.main.custom_dialog_history_book_car_layout.*

class CustomListViewDialog(var activity: Activity, internal var adapter: RecyclerView.Adapter<*>) :
    Dialog(activity) {
    var dialog: Dialog? = null

    internal var recyclerView: RecyclerView? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_dialog_history_book_car_layout)

        recyclerView = recycler_view_book_car
        mLayoutManager = LinearLayoutManager(activity)
        recyclerView?.layoutManager = mLayoutManager
        recyclerView?.adapter = adapter
    }

    fun setTitleDialog(title: String) {
        title_dialog.text = activity.getString(R.string.lich_su_phieu_dat_xe_cua, title)
    }
}