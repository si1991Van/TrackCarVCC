package com.vcc.trackcar.ui.xep_xe_doi_truong_xe.select_car

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.vcc.trackcar.MainActivity

import com.vcc.trackcar.R
import com.vcc.trackcar.model.addBookCar.SysUserRequest
import com.vcc.trackcar.model.auth.AuthenticationInfo
import com.vcc.trackcar.model.getListBookCar.LstBookCarDto
import com.vcc.trackcar.model.getListCar.GetListCarBody
import com.vcc.trackcar.model.getListCar.GetListCarBodyDto
import com.vcc.trackcar.model.getListCar.GetListCarRespon
import com.vcc.trackcar.remote.API
import com.vcc.trackcar.ui.base.CommonVCC
import com.vcc.trackcar.ui.xep_xe_doi_truong_xe.select_car.adapter.ListCarAdapter
import com.vcc.trackcar.ui.xep_xe_doi_truong_xe.select_driver_car.ListDriverCarFragment
import com.vcc.trackcar.ui.xep_xe_doi_truong_xe.select_driver_car.adapter.OnItemCar
import com.vcc.trackcar.utils.DistanceCalculator
import com.vcc.trackcar.utils.StringUtil
import es.dmoral.toasty.Toasty
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import kotlinx.android.synthetic.main.list_car_fragment.*
import kotlinx.android.synthetic.main.search_box.*

class ListCarFragment : Fragment(), OnItemCar {

    companion object {
        const val EXTRA_SELECTED_CAR_LIST_DRIVER = "EXTRA_SELECTED_CAR_LIST_DRIVER"
        const val EXTRA_BOOK_CAR_LIST_DRIVER = "EXTRA_BOOK_CAR_LIST_DRIVER"
        const val EXTRA_CAR_PAIRING = "EXTRA_CAR_PAIRING"

        fun newInstance() = ListCarFragment()
    }

    private lateinit var mainActivity: MainActivity
    private lateinit var listCarAdapter: ListCarAdapter

    private lateinit var viewModel: ListCarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_car_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListCarViewModel::class.java)
        // TODO: Use the ViewModel
        initView()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivity.hideKeyBoard()
    }

    private fun initView() {
        listCarAdapter = ListCarAdapter(activity!!, viewModel.isSelected,this)
        rcv_car_select.run {
            layoutManager = LinearLayoutManager(mainActivity)
            adapter = listCarAdapter
            itemAnimator = SlideInDownAnimator(OvershootInterpolator(1f))
            itemAnimator?.apply {
                addDuration = 500
                removeDuration = 500
                changeDuration = 500
                moveDuration = 500
            }
        }

        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                var keySearch = StringUtil.removeAccentStr(p0.toString().trim()).toLowerCase()
                var resultSearch = Observable.fromIterable(viewModel.listDriverCar).filter { t ->
                    StringUtil.removeAccentStr(t.licenseCar.trim()).toLowerCase().contains(
                        keySearch
                    )
                }.toList().blockingGet()
                listCarAdapter.swapData(resultSearch, viewModel.isSelected)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        imgClearTextSearch.setOnClickListener {
            edtSearch.setText("")
            listCarAdapter.swapData(viewModel.listDriverCar, viewModel.isSelected)
        }
    }

    private fun initData() {
        viewModel.carDieuChuyen =
            arguments?.getSerializable(EXTRA_SELECTED_CAR_LIST_DRIVER) as com.vcc.trackcar.model.getListDriverCar.LstBookCarDto
        viewModel.bookCarDto = arguments?.getSerializable(EXTRA_BOOK_CAR_LIST_DRIVER) as LstBookCarDto
        viewModel.isSelected = arguments?.getBoolean(EXTRA_CAR_PAIRING)!!

//        viewModel.listDriverCar = mainActivity.listCar
        if (viewModel.listDriverCar.isEmpty()) fetchGetListCar()
        else listCarAdapter.swapData(viewModel.listDriverCar, viewModel.isSelected)
    }

    private fun fetchGetListCar() {
        mainActivity.showLoading()

        var body = GetListCarBody().apply {
            getListCarBodyDto = GetListCarBodyDto().apply {
                sysGroupId = CommonVCC.getUserLogin().sysGroupId
            }
        }
        API.service.getListCar(body).subscribeOn(Schedulers.io()) //(*)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<GetListCarRespon> {
                override fun onSuccess(respon: GetListCarRespon) {
                    mainActivity.hideLoading()
                    if (respon.resultInfo.status == CommonVCC.RESULT_STATUS_OK) {
                        viewModel.listDriverCar = respon.lstBookCarDto
                        listCarAdapter.swapData(viewModel.listDriverCar, viewModel.isSelected)
                    } else {
                        Toasty.error(
                            activity!!, respon.resultInfo.message, Toast.LENGTH_SHORT, true
                        ).show()
                    }
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    mainActivity.hideLoading()
                    Toasty.error(
                        activity!!, getString(R.string.loi_ket_noi), Toast.LENGTH_SHORT, true
                    ).show()
                }

            })
    }

    override fun onItemDriverCar(carDto: com.vcc.trackcar.model.getListDriverCar.LstBookCarDto) {
        viewModel.carDieuChuyen.apply {
            carId = carDto.carId
            licenseCar = carDto.licenseCar
            latitudeCar = carDto.latitudeCar
            longtitudeCar = carDto.longtitudeCar

            if (carDto.onCommandDriver == "0" && carDto.latitudeDriver != null && DistanceCalculator.distance(LatLng(carDto.latitudeCar, carDto.longtitudeCar), LatLng(carDto.latitudeDriver, carDto.longtitudeDriver)) <= CommonVCC.LIMIT_DISTANCE){
                driverId = carDto.driverId
                driverName = carDto.driverName
                driverCode = carDto.driverCode
                phoneNumberDriver = carDto.phoneNumberDriver
            }
        }

        mainActivity.popBackStackFragment()
    }

}
