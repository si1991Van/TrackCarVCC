package com.vcc.trackcar.utils

import android.content.Context
import com.vcc.trackcar.MainActivity
import com.vcc.trackcar.model.getAppVersion.GetAppVersionRespon
import com.vcc.trackcar.remote.API
import com.vcc.trackcar.ui.base.CommonVCC
import es.dmoral.toasty.Toasty
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun Context.checkAppVersion(){
    API.service.getAppVersion()
        .subscribeOn(Schedulers.io()) //(*)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : SingleObserver<GetAppVersionRespon> {
            override fun onSuccess(respon: GetAppVersionRespon) {
                if (respon.resultInfo.status == CommonVCC.RESULT_STATUS_OK &&
                    !respon.lstBookCarDto.isNullOrEmpty() &&
                    (respon.lstBookCarDto[0].version.toFloat() > StringUtil.getVersionApp(this@checkAppVersion)) ){
                    (this@checkAppVersion as MainActivity).showNewVersionDialog(respon.lstBookCarDto[0].version, StringUtil.getVersionApp(this@checkAppVersion).toString())
                }
            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onError(e: Throwable) {

            }
        })
}