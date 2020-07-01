package com.vcc.trackcar.ui.detail_book_car

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.setActionButtonEnabled
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.shreyaspatil.MaterialDialog.MaterialDialog
import com.tedpark.tedpermission.rx2.TedRx2Permission
import com.vcc.trackcar.MainActivity
import com.vcc.trackcar.R
import com.vcc.trackcar.model.addBookCar.SysUserRequest
import com.vcc.trackcar.model.administrativeApproveRejectBookCar.AdministrativeApproveRejectBookCarBody
import com.vcc.trackcar.model.administrativeApproveRejectBookCar.AdministrativeApproveRejectBookCarRespon
import com.vcc.trackcar.model.auth.AuthenticationInfo
import com.vcc.trackcar.model.closeBookCar.CloseBookCarBody
import com.vcc.trackcar.model.closeBookCar.CloseBookCarRespon
import com.vcc.trackcar.model.closeManagerBookCar.CloseManagerBookCarBody
import com.vcc.trackcar.model.closeManagerBookCar.CloseManagerBookCarRespon
import com.vcc.trackcar.model.driverBoardApproveRejectBookCar.DriverBoardApproveRejectBookCarBody
import com.vcc.trackcar.model.driverBoardApproveRejectBookCar.DriverBoardApproveRejectBookCarRespon
import com.vcc.trackcar.model.getListBookCar.LstBookCarDto
import com.vcc.trackcar.model.getListUserTogether.GetListUserTogetherBody
import com.vcc.trackcar.model.getListUserTogether.GetListUserTogetherRespon
import com.vcc.trackcar.model.manageApproveRejectBookCar.ManageApproveRejectBookCarBody
import com.vcc.trackcar.model.manageApproveRejectBookCar.ManageApproveRejectBookCarRespon
import com.vcc.trackcar.model.managerCarApproveRejectBookCar.ManagerCarApproveRejectBookCarBody
import com.vcc.trackcar.model.managerCarApproveRejectBookCar.ManagerCarApproveRejectBookCarRespon
import com.vcc.trackcar.remote.API
import com.vcc.trackcar.ui.base.CommonVCC
import com.vcc.trackcar.ui.detail_book_car.adapter.PeopleTogetherAdapter
import com.vcc.trackcar.ui.detail_book_car.open_command.OpenCommandFragment
import com.vcc.trackcar.ui.list_book_added.ListBookAddedFragment
import com.vcc.trackcar.ui.view_and_sign_approval.ViewAndSignApprovalFragment
import com.vcc.trackcar.ui.view_and_sign_approval_manager_car.ViewAndApprovalManagerCarFragment
import com.vcc.trackcar.ui.xem_ky_duyet_tp_hanhchinh_tct.XemKyDuyetTPHCTCTFragment
import com.vcc.trackcar.ui.xep_xe_ban_tct.XepXeBanTCTFragment
import es.dmoral.toasty.Toasty
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import kotlinx.android.synthetic.main.detail_book_car_fragment.*
import uk.co.onemandan.materialtextview.MaterialTextView

class DetailBookCarFragment : Fragment() {

    companion object {
        const val EXTRA_BOOK_CAR = "EXTRA_BOOK_CAR"
        const val EXTRA_TYPE_MENU = "EXTRA_TYPE_MENU"

        fun newInstance() = DetailBookCarFragment()
    }

    private lateinit var mainActivcity: MainActivity
    private lateinit var viewModel: DetailBookCarViewModel

    private var peopleTogetherAdapter = PeopleTogetherAdapter()
    private var peopleApproveAdapter = PeopleTogetherAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mainActivcity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_book_car_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailBookCarViewModel::class.java)
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

        btn_duyet_lenh.setOnClickListener {
            showDialogComfirmApprove(2)
        }

        btn_tu_choi.setOnClickListener {
            showDialogReason(getString(R.string.ly_do), getString(R.string.reason_reject), 3)
        }

        btn_yeu_cau_sua.setOnClickListener {
            showDialogReason(
                getString(R.string.yeu_cau_sua), getString(R.string.yeu_cau_sua_ly_do), 4
            )
        }

        btn_mo_lenh.setOnClickListener {
            // mo man hinh mo lenh.
            val bundle = Bundle()
            bundle.putSerializable(OpenCommandFragment.EXTRA_OPEN_COMMAND, viewModel.bookCarDto)
            mainActivcity.navigateFragment(R.id.nav_open_command, bundle)
        }
    }

    private fun initData() {
        viewModel.bookCarDto = arguments?.getSerializable(EXTRA_BOOK_CAR) as LstBookCarDto
        viewModel.typeMenu = arguments?.getInt(EXTRA_TYPE_MENU, 0)

        if (!viewModel.bookCarDto.licenseCar.isNullOrEmpty() || !viewModel.bookCarDto.driverName.isNullOrEmpty()) {
            layout_chon_xe_va_lai_xe.visibility = View.VISIBLE
            tv_bien_so.text = viewModel.bookCarDto.licenseCar
            tv_nguoi_lai.text = viewModel.bookCarDto.driverName
            tv_phone_driver.text = viewModel.bookCarDto.phoneNumberDriver
            tv_phone_driver.setOnClickListener {
                TedRx2Permission.with(mainActivcity)
                    .setDeniedMessage(R.string.reject_permission)
                    .setPermissions(Manifest.permission.CALL_PHONE)
                    .request()
                    .subscribe { permissionResult ->
                        if (permissionResult.isGranted) {
                            val intent = Intent(Intent.ACTION_CALL)
                            intent.data = Uri.parse("tel:" + viewModel.bookCarDto.phoneNumberDriver)
                            startActivity(intent)
                        }
                    }
            }
        }

        if (viewModel.typeMenu == ListBookAddedFragment.TYPE_MENU) {

            var bookCarDto = viewModel.bookCarDto

            if ((bookCarDto.typeBookCar == "1" || bookCarDto.typeBookCar == "2") && bookCarDto.status == "2" && bookCarDto.statusManagerCar == "2") {
                closeBookCar()
            } else if (bookCarDto.typeBookCar == "3" && bookCarDto.status == "2" && bookCarDto.statusCaptainCar == "2") {
                closeBookCar()
            } else viewBookCar()
        }

        if (viewModel.typeMenu == ViewAndSignApprovalFragment.TYPE_MENU) {

            if (viewModel.bookCarDto.statusManage == "1") { // duyet tu cho sua
            } else if (viewModel.bookCarDto.statusManage == "3" || viewModel.bookCarDto.statusManage == "4" || viewModel.bookCarDto.statusManage == "5" || viewModel.bookCarDto.statusManage == "6") {
                viewBookCar()
            } else if (viewModel.bookCarDto.statusManage == "2" && viewModel.bookCarDto.statusAdministrative != "2") {
                viewBookCar()
            } else if (viewModel.bookCarDto.statusManage == "2" && viewModel.bookCarDto.statusAdministrative == "2") {
                closeBookCar()
            }
        }

        if (viewModel.typeMenu == ViewAndApprovalManagerCarFragment.TYPE_MENU) {

            if (viewModel.bookCarDto.statusManagerCar == "1" && (viewModel.bookCarDto.typeBookCar == "1" || viewModel.bookCarDto.typeBookCar == "2")) {
                // duyet, tu choi, yeu cau sua
                btn_duyet_lenh.text = getString(R.string.duyet_lenh)
            } else if (viewModel.bookCarDto.statusManagerCar == "1" && viewModel.bookCarDto.typeBookCar == "3" && viewModel.bookCarDto.status == "5") {
                // duyet lenh
                layout_duyet_tuchoi_sua.visibility = View.VISIBLE
                btn_duyet_lenh.visibility = View.VISIBLE
                btn_duyet_lenh.text = getString(R.string.duyet_lenh)
                btn_tu_choi.visibility = View.GONE
                btn_yeu_cau_sua.visibility = View.GONE
                btn_dong_lenh.visibility = View.GONE
            } else if (viewModel.bookCarDto.statusManagerCar == "1" && viewModel.bookCarDto.typeBookCar == "3" && viewModel.bookCarDto.status != "5") {
                // view lenh
                viewBookCar()
            } else if (viewModel.bookCarDto.statusManagerCar != "1") {
                // view lenh
                viewBookCar()
            }
        }

        if (viewModel.typeMenu == XepXeBanTCTFragment.TYPE_MENU) {
            if (viewModel.bookCarDto.statusDriverBoard != "1") {
                viewBookCar()
            }
        }

        if (viewModel.typeMenu == XemKyDuyetTPHCTCTFragment.TYPE_MENU) {
            if (viewModel.bookCarDto.statusAdministrative != "1") {
                // view lenh
                viewBookCar()
            }else{
                btn_duyet_lenh.text = getString(R.string.duyet_lenh)
            }
        }


        tv_don_vi.text = "${viewModel.bookCarDto.departmentName} - ${viewModel.bookCarDto.sysGroupName}"
        tv_ho_ten.text = viewModel.bookCarDto.fullName
        tv_email.text = viewModel.bookCarDto.email
        tv_phone.text = viewModel.bookCarDto.phoneNumber
        text_content.text = viewModel.bookCarDto.content

        tv_phone.setOnClickListener {
            TedRx2Permission.with(mainActivcity)
                .setDeniedMessage(R.string.reject_permission)
                .setPermissions(Manifest.permission.CALL_PHONE)
                .request()
                .subscribe { permissionResult ->
                    if (permissionResult.isGranted) {
                        val intent = Intent(Intent.ACTION_CALL)
                        intent.data = Uri.parse("tel:" + viewModel.bookCarDto.phoneNumber)
                        startActivity(intent)
                    }
                }
        }

        text_pos_from.text = viewModel.bookCarDto.fromAddress

        text_pos_to.text = viewModel.bookCarDto.toAddress

        text_time_start.text = viewModel.bookCarDto.startTime

        text_time_end.text = viewModel.bookCarDto.endTime

        tv_type_car.setContentText(
            viewModel.bookCarDto.carTypeName, MaterialTextView.ANIMATE_TYPE.NONE
        )

        when (viewModel.bookCarDto.typeBookCar) {
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
        }

        fetchGetListUserTogether()

        peopleApproveAdapter.swapDataPeopleApprove(viewModel.bookCarDto)
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
                swichFetchApi(flag, "")
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
//                    val ly_do_dong_viec = taskEditText.text.toString()
//                    if (ly_do_dong_viec.trim().equals("")) {
//                        Toasty.warning(
//                            activity!!, getString(R.string.ly_do_please), Toast.LENGTH_SHORT
//                        ).show()
//                    } else {
//                        swichFetchApi(flag, ly_do_dong_viec)
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
                        swichFetchApi(flag, it.getInputField().text.trim().toString())
                    }
                }
                negativeButton(R.string.cancel)
            }
    }

    private fun swichFetchApi(flag: Int, lyDoDongViec: String) {
        if (viewModel.typeMenu == ViewAndSignApprovalFragment.TYPE_MENU) {
            fetchManageApproveRejectBookCar(flag, lyDoDongViec)
        } else if (viewModel.typeMenu == ViewAndApprovalManagerCarFragment.TYPE_MENU) {
            fetchManagerCarApproveRejectBookCar(flag, lyDoDongViec)
        } else if (viewModel.typeMenu == XepXeBanTCTFragment.TYPE_MENU) {
            fetchDriverBoardApproveRejectBookCar(flag, lyDoDongViec)
        } else if (viewModel.typeMenu == XemKyDuyetTPHCTCTFragment.TYPE_MENU) {
            fetchAdministrativeApproveRejectBookCar(flag, lyDoDongViec)
        }

    }

    private fun fetchAdministrativeApproveRejectBookCar(flag: Int, lyDoDongViec: String) {
        var body = AdministrativeApproveRejectBookCarBody().apply {
            bookCarDto = viewModel.bookCarDto.apply {
                this.reasonAdministrative = reasonManage
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

        API.service.administrativeApproveRejectBookCar(body).subscribeOn(Schedulers.io()) //(*)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<AdministrativeApproveRejectBookCarRespon> {
                override fun onSuccess(respon: AdministrativeApproveRejectBookCarRespon) {
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

    private fun fetchDriverBoardApproveRejectBookCar(flag: Int, lyDoDongViec: String) {
        var body = DriverBoardApproveRejectBookCarBody().apply {
            bookCarDto = viewModel.bookCarDto.apply {
                this.reasonDriverBoard = reasonManage
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

        API.service.driverBoardApproveRejectBookCar(body).subscribeOn(Schedulers.io()) //(*)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<DriverBoardApproveRejectBookCarRespon> {
                override fun onSuccess(respon: DriverBoardApproveRejectBookCarRespon) {
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

    private fun fetchManageApproveRejectBookCar(flag: Int, reasonManage: String) {

        var body = ManageApproveRejectBookCarBody().apply {
            bookCarDto = viewModel.bookCarDto.apply {
                this.reasonManage = reasonManage
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

        API.service.manageApproveRejectBookCar(body).subscribeOn(Schedulers.io()) //(*)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ManageApproveRejectBookCarRespon> {
                override fun onSuccess(respon: ManageApproveRejectBookCarRespon) {
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

    private fun fetchManagerCarApproveRejectBookCar(flag: Int, reasonManage: String) {

        var body = ManagerCarApproveRejectBookCarBody().apply {
            bookCarDto = viewModel.bookCarDto.apply {
                this.reasonManagerCar = reasonManage
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

        API.service.managerCarApproveRejectBookCar(body).subscribeOn(Schedulers.io()) //(*)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ManagerCarApproveRejectBookCarRespon> {
                override fun onSuccess(respon: ManagerCarApproveRejectBookCarRespon) {
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

    private fun viewBookCar() {
        layout_duyet_tuchoi_sua.visibility = View.GONE
        btn_mo_lenh.visibility = View.VISIBLE
    }

    private fun closeBookCar() {
        layout_duyet_tuchoi_sua.visibility = View.VISIBLE
        btn_mo_lenh.visibility = View.GONE
        btn_duyet_lenh.visibility = View.GONE
        btn_tu_choi.visibility = View.GONE
        btn_yeu_cau_sua.visibility = View.GONE
        btn_dong_lenh.visibility = View.VISIBLE

        btn_dong_lenh.setOnClickListener {
            if (viewModel.typeMenu == ViewAndSignApprovalFragment.TYPE_MENU && viewModel.bookCarDto.typeBookCar == "4") { // close kieu di dac biet T/P Bo Phan
                closeManagerBookCar()
                return@setOnClickListener
            }

            mainActivcity.showLoading()

            var body = CloseBookCarBody().apply {
                bookCarClose = viewModel.bookCarDto

                sysUserRequest = SysUserRequest().apply {
                    var userLogin = CommonVCC.getUserLogin()
                    authenticationInfo = AuthenticationInfo(userLogin.loginName, userLogin.password)
                    sysUserId = userLogin.sysUserId
                    email = userLogin.email
                    loginName = userLogin.loginName
                }
            }
            API.service.closeBookCar(body).subscribeOn(Schedulers.io()) //(*)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<CloseBookCarRespon> {
                    override fun onSuccess(respon: CloseBookCarRespon) {
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

    private fun closeManagerBookCar() {
        mainActivcity.showLoading()

        var body = CloseManagerBookCarBody().apply {
            bookCarDto = viewModel.bookCarDto

            sysUserRequest = SysUserRequest().apply {
                var userLogin = CommonVCC.getUserLogin()
                authenticationInfo = AuthenticationInfo(userLogin.loginName, userLogin.password)
                sysUserId = userLogin.sysUserId
                email = userLogin.email
                loginName = userLogin.loginName
            }
        }

        API.service.closeManagerBookCar(body).subscribeOn(Schedulers.io()) //(*)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<CloseManagerBookCarRespon> {
                override fun onSuccess(respon: CloseManagerBookCarRespon) {
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
