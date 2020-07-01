package com.vcc.trackcar.ui.giao_viec.detail_giao_viec

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
import com.vcc.trackcar.MainActivity
import com.vcc.trackcar.R
import com.vcc.trackcar.model.addBookCar.SysUserRequest
import com.vcc.trackcar.model.auth.AuthenticationInfo
import com.vcc.trackcar.model.closeDriverBookCar.CloseDriverBookCarBody
import com.vcc.trackcar.model.closeDriverBookCar.CloseDriverBookCarRespon
import com.vcc.trackcar.model.getListBookCar.LstBookCarDto
import com.vcc.trackcar.model.getListUserTogether.GetListUserTogetherBody
import com.vcc.trackcar.model.getListUserTogether.GetListUserTogetherRespon
import com.vcc.trackcar.remote.API
import com.vcc.trackcar.ui.base.CommonVCC
import com.vcc.trackcar.ui.detail_book_car.DetailBookCarFragment
import com.vcc.trackcar.ui.detail_book_car.adapter.PeopleTogetherAdapter
import es.dmoral.toasty.Toasty
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import kotlinx.android.synthetic.main.detail_giao_viec_fragment.*
import kotlinx.android.synthetic.main.detail_giao_viec_fragment.layout_duyet_tuchoi_sua
import kotlinx.android.synthetic.main.detail_giao_viec_fragment.rcv_people_approve
import kotlinx.android.synthetic.main.detail_giao_viec_fragment.rcv_people_together
import kotlinx.android.synthetic.main.detail_giao_viec_fragment.text_so_nguoi_di_cung_da_chon
import kotlinx.android.synthetic.main.detail_giao_viec_fragment.tv_kieu_di
import kotlinx.android.synthetic.main.detail_giao_viec_fragment.tv_type_car
import uk.co.onemandan.materialtextview.MaterialTextView
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class DetailGiaoViecFragment : Fragment() {

    companion object {
        const val EXTRA_BOOK_CAR = "EXTRA_BOOK_CAR"
        const val EXTRA_TYPE_MENU = "EXTRA_TYPE_MENU"

        fun newInstance() = DetailGiaoViecFragment()
    }

    private lateinit var mainActivcity: MainActivity
    private lateinit var viewModel: DetailGiaoViecViewModel

    private var peopleTogetherAdapter = PeopleTogetherAdapter()
    private var peopleApproveAdapter = PeopleTogetherAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mainActivcity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_giao_viec_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailGiaoViecViewModel::class.java)
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
    }

    private fun initData() {
        viewModel.bookCarDto =
            arguments?.getSerializable(DetailBookCarFragment.EXTRA_BOOK_CAR) as LstBookCarDto
        viewModel.typeMenu = arguments?.getInt(DetailBookCarFragment.EXTRA_TYPE_MENU, 0)

        if (viewModel.bookCarDto.typeBookCar == "1" && viewModel.bookCarDto.status == "5" && viewModel.bookCarDto.statusDriver != "5") {
            layout_duyet_tuchoi_sua.visibility = View.VISIBLE
        }

        btn_close.setOnClickListener {
            mainActivcity.showLoading()

            var body = CloseDriverBookCarBody().apply {
                bookCarClose = viewModel.bookCarDto

                sysUserRequest = SysUserRequest().apply {
                    var userLogin = CommonVCC.getUserLogin()
                    authenticationInfo = AuthenticationInfo(userLogin.loginName, userLogin.password)
                    sysUserId = userLogin.sysUserId
                    email = userLogin.email
                    loginName = userLogin.loginName
                }
            }
            API.service.closeDriverBookCar(body).subscribeOn(Schedulers.io()) //(*)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<CloseDriverBookCarRespon> {
                    override fun onSuccess(respon: CloseDriverBookCarRespon) {
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

        // fetch people together

        text_pos_from.text = viewModel.bookCarDto.fromAddress

        text_pos_to.text = viewModel.bookCarDto.toAddress

        text_time_start.text = viewModel.bookCarDto.startTime

        text_time_end.text = viewModel.bookCarDto.endTime

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

        tv_type_car.setContentText(
            viewModel.bookCarDto.carTypeName, MaterialTextView.ANIMATE_TYPE.NONE
        )

        imv_info_maps_du_kien_ban_dau.setOnClickListener {
            var fromAddress = viewModel.bookCarDto.fromAddress.trim()
            var toAddress = viewModel.bookCarDto.toAddress.trim()

            getApiMatricTest(fromAddress, toAddress)

        }

        imv_info_maps_du_kien_hien_tai.setOnClickListener {
            var toAddress = viewModel.bookCarDto.toAddress.trim()
            openNavigationCurrent(toAddress)
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

    private fun openNavigationCurrent(toAddress: String) {
        var gmmIntentUri = Uri.parse("google.navigation:q=" + URLEncoder.encode(toAddress, "UTF-8"))
        var mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")

        if (mapIntent.resolveActivity(activity!!.packageManager) != null) {
            startActivity(mapIntent)
        } else {
            Toasty.warning(
                context!!, getString(R.string.please_install_maps_application), Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun getApiMatricTest(origin: String, destination: String) {
        var intent: Intent?
        try {
            intent = Intent(
                Intent.ACTION_VIEW, Uri.parse(
                    "https://www.google.com/maps/dir/?api=1" + "&origin=" + URLEncoder.encode(
                        origin, "UTF-8"
                    ) + "&destination=" + URLEncoder.encode(
                        destination, "UTF-8"
                    ) + "&travelmode=driving"
                )
            )
            intent.setPackage("com.google.android.apps.maps")
            if (intent.resolveActivity(activity!!.packageManager) != null) {
                startActivity(intent)
            } else {
                Toasty.warning(
                    context!!,
                    getString(R.string.please_install_maps_application),
                    Toast.LENGTH_LONG
                ).show()
            }
            startActivity(intent)
        } catch (e: UnsupportedEncodingException) {
            Toasty.warning(context!!, getString(R.string.error_parse_address), Toast.LENGTH_LONG)
                .show()
        }
    }

}
