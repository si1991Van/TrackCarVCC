package com.vcc.trackcar.ui.xep_xe_doi_truong_xe.detail_xep_xe

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.setActionButtonEnabled
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.shreyaspatil.MaterialDialog.MaterialDialog
import com.tedpark.tedpermission.rx2.TedRx2Permission
import com.vcc.trackcar.MainActivity
import com.vcc.trackcar.R
import com.vcc.trackcar.model.addBookCar.SysUserRequest
import com.vcc.trackcar.model.auth.AuthenticationInfo
import com.vcc.trackcar.model.captainCarApproveRejectBookCar.CaptainCarApproveRejectBookCarBody
import com.vcc.trackcar.model.captainCarApproveRejectBookCar.CaptainCarApproveRejectBookCarRespon
import com.vcc.trackcar.model.getListBookCar.LstBookCarDto
import com.vcc.trackcar.model.getListUserTogether.GetListUserTogetherBody
import com.vcc.trackcar.model.getListUserTogether.GetListUserTogetherRespon
import com.vcc.trackcar.remote.API
import com.vcc.trackcar.ui.base.CommonVCC
import com.vcc.trackcar.ui.base.PrefManager
import com.vcc.trackcar.ui.detail_book_car.adapter.PeopleTogetherAdapter
import com.vcc.trackcar.ui.login.LoginActivity
import com.vcc.trackcar.ui.xep_xe_doi_truong_xe.select_driver_car.ListDriverCarFragment.Companion.EXTRA_BOOK_CAR_LIST_DRIVER
import com.vcc.trackcar.ui.xep_xe_doi_truong_xe.select_driver_car.ListDriverCarFragment.Companion.EXTRA_CAR_PAIRING
import com.vcc.trackcar.ui.xep_xe_doi_truong_xe.select_driver_car.ListDriverCarFragment.Companion.EXTRA_SELECTED_CAR_LIST_DRIVER
import es.dmoral.toasty.Toasty
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import kotlinx.android.synthetic.main.detail_xep_xe_fragment.*
import uk.co.onemandan.materialtextview.MaterialTextView

class DetailXepXeFragment : Fragment() {

    companion object {
        const val EXTRA_BOOK_CAR = "EXTRA_BOOK_CAR"

        fun newInstance() = DetailXepXeFragment()
    }

    private lateinit var mainActivcity: MainActivity
    private lateinit var viewModel: DetailXepXeViewModel

    private var peopleTogetherAdapter = PeopleTogetherAdapter()
    private var peopleApproveAdapter = PeopleTogetherAdapter()
    private var isSelected: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mainActivcity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_xep_xe_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailXepXeViewModel::class.java)
        initView()
        initData()
    }

    private fun initView() {
        rcv_people_together.run {
            layoutManager = LinearLayoutManager(mainActivcity)
            adapter = peopleTogetherAdapter
            itemAnimator = SlideInDownAnimator(OvershootInterpolator(1f))
            itemAnimator?.apply {
                addDuration = 500
                removeDuration = 500
                changeDuration = 500
                moveDuration = 500
            }
        }

        rcv_people_approve.run {
            layoutManager = LinearLayoutManager(mainActivcity)
            adapter = peopleApproveAdapter
            itemAnimator = SlideInDownAnimator(OvershootInterpolator(1f))
            itemAnimator?.apply {
                addDuration = 500
                removeDuration = 500
                changeDuration = 500
                moveDuration = 500
            }
        }

        imv_location_pin.setOnClickListener {
            if (viewModel.carDieuChuyen.licenseCar.isNullOrEmpty()){
                Toasty.warning(
                    activity!!, getString(R.string.please_choose_car_driver), Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            var bundle = Bundle()
            bundle.putSerializable(EXTRA_SELECTED_CAR_LIST_DRIVER, viewModel.carDieuChuyen)
            mainActivcity.navigateFragment(R.id.nav_radar_driver, bundle)
        }

        layout_chon_xe.setOnClickListener {
            var bundle = Bundle()
            bundle.putSerializable(EXTRA_BOOK_CAR_LIST_DRIVER, viewModel.bookCarDto)
            bundle.putSerializable(EXTRA_SELECTED_CAR_LIST_DRIVER, viewModel.carDieuChuyen)
            bundle.putSerializable(EXTRA_CAR_PAIRING, isSelected)
            mainActivcity.navigateFragment(R.id.nav_select_car_xe, bundle)
        }

        layout_chon_lai_xe.setOnClickListener {
            var bundle = Bundle()
            bundle.putSerializable(EXTRA_BOOK_CAR_LIST_DRIVER, viewModel.bookCarDto)
            bundle.putSerializable(EXTRA_SELECTED_CAR_LIST_DRIVER, viewModel.carDieuChuyen)
            bundle.putSerializable(EXTRA_CAR_PAIRING, isSelected)
            mainActivcity.navigateFragment(R.id.nav_select_car, bundle)
        }

        btn_duyet_lenh.setOnClickListener {
            if (viewModel.carDieuChuyen.licenseCar.isNullOrEmpty()) {
                Toasty.warning(
                    activity!!, getString(R.string.please_choose_car_driver), Toast.LENGTH_SHORT
                ).show()
            } else if (viewModel.carDieuChuyen.driverName.isNullOrEmpty()) {
                Toasty.warning(
                    activity!!, getString(R.string.please_choose_driver), Toast.LENGTH_SHORT
                ).show()
            } else {
                showDialogComfirmApprove(2)
            }
        }

        btn_tu_choi.setOnClickListener {
            showDialogReason(getString(R.string.ly_do), getString(R.string.reason_reject), 3)
        }

        btn_yeu_cau_sua.setOnClickListener {
            showDialogReason(
                getString(R.string.yeu_cau_sua), getString(R.string.yeu_cau_sua_ly_do), 4
            )
        }

        cbCarPairing.setOnCheckedChangeListener { buttonView, isChecked ->
            isSelected = isChecked
        }
    }

    private fun initData() {

        viewModel.bookCarDto = arguments?.getSerializable(EXTRA_BOOK_CAR) as LstBookCarDto

        if (!viewModel.carDieuChuyen.licenseCar.isNullOrEmpty() || !viewModel.carDieuChuyen.driverName.isNullOrEmpty()) {
            layout_chon_xe_va_lai_xe.visibility = View.VISIBLE
            tv_bien_so.text = if (viewModel.carDieuChuyen.licenseCar.isNullOrEmpty()) "" else viewModel.carDieuChuyen.licenseCar
            tv_nguoi_lai.text = if (viewModel.carDieuChuyen.driverName.isNullOrEmpty()) "" else viewModel.carDieuChuyen.driverName
            tv_phone_driver.text = if (viewModel.carDieuChuyen.phoneNumberDriver.isNullOrEmpty()) "" else viewModel.carDieuChuyen.phoneNumberDriver
        } else if (!viewModel.bookCarDto?.licenseCar.isNullOrEmpty()) {
            layout_chon_xe_va_lai_xe.visibility = View.VISIBLE
            tv_bien_so.text = viewModel.bookCarDto?.licenseCar
            tv_nguoi_lai.text = viewModel.bookCarDto?.driverName
            tv_phone_driver.text = viewModel.bookCarDto?.phoneNumberDriver
        }

        if (viewModel.bookCarDto?.statusCaptainCar != "1") {
            // view lenh
            layout_duyet_tuchoi_sua.visibility = View.GONE
            layout_chon_xe.setOnClickListener(null)
            layout_chon_lai_xe.setOnClickListener(null)
            imv_next_chon_xe.visibility = View.GONE
            imv_next_chon_nguoi_lai.visibility = View.GONE
            imv_location_pin.visibility = View.GONE
            cbCarPairing.isEnabled = false
        }

        tv_phone_driver.setOnClickListener {
            TedRx2Permission.with(mainActivcity).setDeniedMessage(R.string.reject_permission)
                .setPermissions(Manifest.permission.CALL_PHONE).request()
                .subscribe { permissionResult ->
                    if (permissionResult.isGranted) {
                        val intent = Intent(Intent.ACTION_CALL)
                        intent.data = Uri.parse("tel:" + viewModel.bookCarDto?.phoneNumberDriver)
                        startActivity(intent)
                    }
                }
        }

        tv_don_vi.text =
            "${viewModel.bookCarDto?.departmentName} - ${viewModel.bookCarDto?.sysGroupName}"

        tv_ho_ten.text = viewModel.bookCarDto?.fullName

        tv_email.text = viewModel.bookCarDto?.email

        tv_phone.text = viewModel.bookCarDto?.phoneNumber

        text_content.text = viewModel.bookCarDto?.content

        tv_phone.setOnClickListener {
            TedRx2Permission.with(mainActivcity).setDeniedMessage(R.string.reject_permission)
                .setPermissions(Manifest.permission.CALL_PHONE).request()
                .subscribe { permissionResult ->
                    if (permissionResult.isGranted) {
                        val intent = Intent(Intent.ACTION_CALL)
                        intent.data = Uri.parse("tel:" + viewModel.bookCarDto?.phoneNumber)
                        startActivity(intent)
                    }
                }
        }

        text_pos_from.text = viewModel.bookCarDto?.fromAddress

        text_pos_to.text = viewModel.bookCarDto?.toAddress

        text_time_start.text = viewModel.bookCarDto?.startTime

        text_time_end.text = viewModel.bookCarDto?.endTime

        tv_type_car.setContentText(
            viewModel.bookCarDto?.carTypeName, MaterialTextView.ANIMATE_TYPE.NONE
        )

        when (viewModel.bookCarDto?.typeBookCar) {
            "1" -> tv_kieu_di.setContentText(
                getString(R.string.mot_chieu), MaterialTextView.ANIMATE_TYPE.NONE
            )
            "2" -> tv_kieu_di.setContentText(
                getString(R.string.hai_chieu), MaterialTextView.ANIMATE_TYPE.NONE
            )
            "3" -> tv_kieu_di.setContentText(
                getString(R.string.phat_sinh), MaterialTextView.ANIMATE_TYPE.NONE
            )
            "4" -> tv_kieu_di.setContentText(
                getString(R.string.dac_biet), MaterialTextView.ANIMATE_TYPE.NONE
            )
            "5" -> tv_kieu_di.setContentText(
                getString(R.string.xe_tai), MaterialTextView.ANIMATE_TYPE.NONE
            )
        }

        fetchGetListUserTogether()

        peopleApproveAdapter.swapDataPeopleApprove(viewModel.bookCarDto!!)

        if (viewModel.bookCarDto?.pairingCar != null) {
            cbCarPairing.isChecked = viewModel.bookCarDto?.pairingCar?.toInt() == 0
        }
    }

    private fun fetchGetListUserTogether() {
        mainActivcity.showLoading()

        var body = GetListUserTogetherBody().apply {
            bookCarDto = viewModel.bookCarDto
            sysUserRequest = SysUserRequest().apply {
                var userLogin = CommonVCC.getUserLogin()
                authenticationInfo = AuthenticationInfo(userLogin.loginName, userLogin.password)
                sysUserId = userLogin.sysUserId
            }
        }
        API.service.getListUserTogether(body).subscribeOn(Schedulers.io()) //(*)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<GetListUserTogetherRespon> {
                override fun onSuccess(respon: GetListUserTogetherRespon) {
                    mainActivcity.hideLoading()
                    if (respon.resultInfo.status == CommonVCC.RESULT_STATUS_OK) {
                        peopleTogetherAdapter.swapData(respon.lstBookCarDto)

                        text_so_nguoi_di_cung_da_chon.text = getString(
                            R.string.num_people_together_selected, respon.lstBookCarDto.size
                        )
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

    private fun showDialogComfirmApprove(flag: Int) {
        val mDialog = MaterialDialog.Builder(activity!!).setTitle(getString(R.string.approve))
            .setMessage(getString(R.string.approve_comfirm)).setCancelable(false)
            .setPositiveButton(getString(R.string.xac_nhan)) { dialogInterface, which ->
                fetchCaptainCarApproveRejectBookCar(flag, "")
                dialogInterface.dismiss()
            }.setNegativeButton(getString(R.string.cancel)) { dialogInterface, which ->
                dialogInterface.dismiss()
            }.build()

        mDialog.show()
    }

    private fun showDialogReason(title: String, message: String, flag: Int) {
//        val taskEditText = EditText(context)
//        val dialog =
//            AlertDialog.Builder(context).setTitle(title).setMessage(message).setView(taskEditText)
//                .setPositiveButton(getString(R.string.xac_nhan)) { dialog, which ->
//                    val ly_do_str = taskEditText.text.toString()
//                    if (ly_do_str.trim().equals("")) {
//                        Toasty.warning(
//                            activity!!, getString(R.string.ly_do_please), Toast.LENGTH_SHORT
//                        ).show()
//                    } else {
//                        fetchCaptainCarApproveRejectBookCar(flag, ly_do_str)
//                    }
//                }.setNegativeButton(getString(R.string.cancel), null).create()
//        dialog.show()

        com.afollestad.materialdialogs.MaterialDialog(mainActivcity).title(null, title)
            .message(null, message).show {
                input(waitForPositiveButton = true) { dialog, text ->
                    val inputField = dialog.getInputField()
                    val isValid = inputField.text.toString().trim().isNullOrEmpty()

                    inputField?.error = if (isValid) getString(R.string.ly_do_please) else null
                    dialog.setActionButtonEnabled(WhichButton.POSITIVE, !isValid)
                }
                positiveButton(R.string.xac_nhan) {
                    if (it.getInputField().text.toString().trim().isNullOrEmpty()) {
                        Toasty.warning(activity!!, getString(R.string.ly_do_please), Toast.LENGTH_SHORT).show()
                    } else {
                        fetchCaptainCarApproveRejectBookCar(flag, it.getInputField().text.trim().toString())
                    }
                }
                negativeButton(R.string.cancel)
            }
    }

    private fun fetchCaptainCarApproveRejectBookCar(flag: Int, reasonManage: String) {

        var body = CaptainCarApproveRejectBookCarBody().apply {
            bookCarDto = viewModel.bookCarDto.apply {
                this?.reasonCaptainCar = this?.reasonManage
                if (flag == 2) {
                    this?.carId = viewModel.carDieuChuyen.carId
                    this?.licenseCar = viewModel.carDieuChuyen.licenseCar
                    this?.driverId = viewModel.carDieuChuyen.driverId
                    this?.driverName = viewModel.carDieuChuyen.driverName
                    this?.driverCode = viewModel.carDieuChuyen.driverCode
                    this?.phoneNumberDriver = viewModel.carDieuChuyen.phoneNumberDriver
                }
            }
            sysUserRequest = SysUserRequest().apply {
                var userLogin = CommonVCC.getUserLogin()
                authenticationInfo = AuthenticationInfo(userLogin.loginName, userLogin.password)
                this.flag = flag
                sysUserId = userLogin.sysUserId
                email = userLogin.email
                loginName = userLogin.loginName
            }
        }

        mainActivcity.showLoading()

        API.service.captainCarApproveRejectBookCar(body).subscribeOn(Schedulers.io()) //(*)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<CaptainCarApproveRejectBookCarRespon> {
                override fun onSuccess(respon: CaptainCarApproveRejectBookCarRespon) {
                    mainActivcity.hideLoading()
                    if (respon.resultInfo.status == CommonVCC.RESULT_STATUS_OK) {
                        Toasty.success(
                            activity!!, respon.resultInfo.message, Toast.LENGTH_SHORT, true
                        ).show()
                        mainActivcity.popBackStackFragment()
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

}
