package com.vcc.trackcar.ui.select_address

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.vcc.trackcar.MainActivity
import com.vcc.trackcar.R
import com.vcc.trackcar.model.addBookCar.SysUserRequest
import com.vcc.trackcar.model.auth.AuthenticationInfo
import com.vcc.trackcar.model.getDataDistrict.BodyDataDistrict
import com.vcc.trackcar.model.getDataDistrict.GetDataDistrictBody
import com.vcc.trackcar.model.getDataDistrict.GetDataDistrictRespon
import com.vcc.trackcar.model.getDataProvinceCity.AreaProvinceCity
import com.vcc.trackcar.model.getDataProvinceCity.GetDataProvinceCityRespon
import com.vcc.trackcar.model.getDataWard.GetDataWardBody
import com.vcc.trackcar.model.getDataWard.GetDataWardRespon
import com.vcc.trackcar.remote.API
import com.vcc.trackcar.ui.base.CommonVCC
import com.vcc.trackcar.ui.select_address.adapter.CountriesAdaper
import com.vcc.trackcar.ui.select_address.model.DataSelectAddress
import es.dmoral.toasty.Toasty
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.select_address_fragment.*
import kotlin.math.E

class SelectAddressFragment : Fragment(), CountriesAdaper.IClickItem {

    companion object {
        const val EXTRA_DATA = "EXTRA_DATA_SELECT_ADDRESS"
        const val TYPE_PROVINCE = 1
        const val TYPE_DISTRICT = 2
        const val TYPE_WARD = 3

        fun newInstance() = SelectAddressFragment()
    }

    private var typeSelectAddress: Int = 0
    private lateinit var viewModel: SelectAddressViewModel

    private lateinit var mAdapterProvince: CountriesAdaper
    private lateinit var mAdapterDistrcit: CountriesAdaper
    private lateinit var mAdapterWard: CountriesAdaper

    private val lstProvince: ArrayList<AreaProvinceCity> = arrayListOf()
    private val lstDistrict: ArrayList<AreaProvinceCity> = arrayListOf()
    private val lstWard: ArrayList<AreaProvinceCity> = arrayListOf()

    private lateinit var mainActivcity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mainActivcity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.select_address_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SelectAddressViewModel::class.java)
        initView()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivcity.hideKeyBoard()
    }

    private fun initView() {
        mAdapterProvince =
            CountriesAdaper(context, R.layout.spinner_item_layout, lstProvince, this, TYPE_PROVINCE)
        mAdapterDistrcit =
            CountriesAdaper(context, R.layout.spinner_item_layout, lstDistrict, this, TYPE_DISTRICT)
        mAdapterWard =
            CountriesAdaper(context, R.layout.spinner_item_layout, lstWard, this, TYPE_WARD)

        auto_complete_province.apply {
            setAdapter(mAdapterProvince)
            threshold = 0
            setOnTouchListener { view, event ->
                showDropDown()
                false
            }
        }
        auto_complete_district.apply {
            setAdapter(mAdapterDistrcit)
            threshold = 0
            setOnTouchListener { view, event ->
                showDropDown()
                false
            }
        }
        auto_complete_ward.apply {
            setAdapter(mAdapterWard)
            threshold = 0
            setOnTouchListener { view, event ->
                showDropDown()
                false
            }
        }

        btn_xan_nhan_address.setOnClickListener {
            mainActivcity.hideKeyBoard()
            var data = edtAdress.text.toString() + " " + auto_complete_ward.text + " " + auto_complete_district.text + " " + auto_complete_province.text
            viewModel.dataSelectAddress.data = data
            mainActivcity.popBackStackFragment()
        }
    }

    private fun initData() {
        viewModel.dataSelectAddress = arguments?.getSerializable(EXTRA_DATA) as DataSelectAddress

         getDataProvinceCity()
    }

    private fun getDataProvinceCity(){
        mainActivcity.showLoading()
        API.service.getDataProvinceCity().subscribeOn(Schedulers.io()) //(*)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<GetDataProvinceCityRespon> {
                override fun onSuccess(respon: GetDataProvinceCityRespon) {
                    mainActivcity.hideLoading()
                    if (respon.resultInfo.status == CommonVCC.RESULT_STATUS_OK) {
                        lstProvince.clear()
                        lstProvince.addAll(respon.areaProvinceCity)
                        mAdapterProvince.notifyDataSetChanged()
                    } else {
                        Toasty.error(
                            activity!!, respon.resultInfo.message, Toast.LENGTH_SHORT, true
                        ).show()
                    }
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    mainActivcity.hideLoading()
                    Toasty.error(
                        activity!!, getString(R.string.loi_ket_noi), Toast.LENGTH_SHORT, true
                    ).show()
                }

            })
    }

    private fun getDataDistrict(areaId: Int) {
        mainActivcity.showLoading()

        var body = GetDataDistrictBody().apply {
            bookCarDto = BodyDataDistrict().apply { parentId = areaId }
            sysUserRequest = SysUserRequest().apply {
                var userLogin = CommonVCC.getUserLogin()
                authenticationInfo = AuthenticationInfo(userLogin.loginName, userLogin.password)
                sysUserId = userLogin.sysUserId
            }
        }

        API.service.getDataDistrict(body).subscribeOn(Schedulers.io()) //(*)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<GetDataDistrictRespon> {
                override fun onSuccess(respon: GetDataDistrictRespon) {
                    mainActivcity.hideLoading()
                    if (respon.resultInfo.status == CommonVCC.RESULT_STATUS_OK) {
                        lstDistrict.clear()
                        lstDistrict.addAll(respon.areaDistrict)
                        mAdapterDistrcit.setList(lstDistrict)
                    } else {
                        Toasty.error(
                            activity!!, respon.resultInfo.message, Toast.LENGTH_SHORT, true
                        ).show()
                    }
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    mainActivcity.hideLoading()
                    Toasty.error(
                        activity!!, getString(R.string.loi_ket_noi), Toast.LENGTH_SHORT, true
                    ).show()
                }

            })
    }

    private fun getDataWard(areaId: Int) {
        mainActivcity.showLoading()

        var body = GetDataWardBody().apply {
            bookCarDto = BodyDataDistrict().apply { parentId = areaId }
            sysUserRequest = SysUserRequest().apply {
                var userLogin = CommonVCC.getUserLogin()
                authenticationInfo = AuthenticationInfo(userLogin.loginName, userLogin.password)
                sysUserId = userLogin.sysUserId
            }
        }

        API.service.getDataWard(body).subscribeOn(Schedulers.io()) //(*)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<GetDataWardRespon> {
                override fun onSuccess(respon: GetDataWardRespon) {
                    mainActivcity.hideLoading()
                    if (respon.resultInfo.status == CommonVCC.RESULT_STATUS_OK) {
                        lstWard.clear()
                        lstWard.addAll(respon.areaWard)
                        mAdapterWard.setList(lstWard)
                    } else {
                        Toasty.error(
                            activity!!, respon.resultInfo.message, Toast.LENGTH_SHORT, true
                        ).show()
                    }
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    mainActivcity.hideLoading()
                    Toasty.error(
                        activity!!, getString(R.string.loi_ket_noi), Toast.LENGTH_SHORT, true
                    ).show()
                }

            })
    }

    override fun onClickProvince(newContract: AreaProvinceCity, type: Int) {
        if (type == TYPE_PROVINCE) {
            auto_complete_province.setText(newContract.nameLocation)
            auto_complete_province.dismissDropDown()

            auto_complete_district.setText("")
            auto_complete_ward.setText("")

            getDataDistrict(newContract.areaId)
        } else if (type == TYPE_DISTRICT) {
            auto_complete_district.setText(newContract.nameLocation)
            auto_complete_district.dismissDropDown()

            auto_complete_ward.setText("")

            getDataWard(newContract.areaId)
        } else if (type == TYPE_WARD) {
            auto_complete_ward.setText(newContract.nameLocation)
            auto_complete_ward.dismissDropDown()
        }
        mainActivcity.hideKeyBoard()
    }

}


