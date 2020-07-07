package com.vcc.trackcar.ui.detail_book_car.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.Window
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.Toast
import com.vcc.trackcar.R
import kotlinx.android.synthetic.main.dialog_rate_book_car.*


class DialogRateBookCar(var activity: Activity, var onClickOk : (Int, String)-> Unit) : Dialog(activity) {

    var ratting: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_rate_book_car)
        rbBookCar.isEnabled = true
        rbBookCar.onRatingBarChangeListener = OnRatingBarChangeListener { ratingBar, rating, fromUser ->

            ratting = rating.toInt()
        }


        btnOK.setOnClickListener {
            if (ratting <= 0){
                Toast.makeText(activity, "Bạn vui lòng đánh giá chuyến đi!", Toast.LENGTH_LONG).show()
            }
            if (ratting < 4){
                if (TextUtils.isEmpty(edContent.text.toString())){
                    Toast.makeText(activity, "Bạn phải nhập lý do!", Toast.LENGTH_LONG).show()
                }else{
                    onClickOk(ratting, edContent.text.toString())
                    dismiss()
                }
            }else{
                onClickOk(ratting, edContent.text.toString())
                dismiss()
            }



        }
        btnCancel.setOnClickListener {
            dismiss()
        }
    }
}