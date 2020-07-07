package com.vcc.trackcar.ui.ptgd_chuyen_trach

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.vcc.trackcar.MainActivity
import com.vcc.trackcar.R
import com.vcc.trackcar.model.auth.AuthenticationInfo
import com.vcc.trackcar.model.getListBookCar.GetListBookCarBody
import com.vcc.trackcar.model.getListBookCar.GetListBookCarRespon
import com.vcc.trackcar.model.getListBookCar.LstBookCarDto
import com.vcc.trackcar.remote.API
import com.vcc.trackcar.ui.base.CommonVCC
import com.vcc.trackcar.ui.detail_book_car.DetailBookCarFragment
import com.vcc.trackcar.ui.list_book_added.adapter.ListBookCarAdapter
import com.vcc.trackcar.ui.list_book_added.adapter.OnItemBookListener
import com.vcc.trackcar.ui.xem_ky_duyet_tp_hanhchinh_tct.XemKyDuyetTphctctViewModel
import es.dmoral.toasty.Toasty
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import kotlinx.android.synthetic.main.xem_ky_duyet_tphctct_fragment.*

class PTGDChuyenTrachFragment : Fragment(), OnItemBookListener {

    companion object {
        const val TYPE_MENU = 10
        fun newInstance() = PTGDChuyenTrachFragment()
    }

    private lateinit var viewModel: XemKyDuyetTphctctViewModel

    private var mainActivcity: MainActivity? = null
    private lateinit var listBookCarAdapter: ListBookCarAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mainActivcity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.xem_ky_duyet_tphctct_fragment, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity!!.menuInflater.inflate(R.menu.menu_view_and_approval_manager_car_fragment, menu)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(XemKyDuyetTphctctViewModel::class.java)
        initView()
        initData()
    }

    private fun initView() {
        listBookCarAdapter = ListBookCarAdapter(activity!!, this, TYPE_MENU)
        rcv_list_book_car.run {
            layoutManager = LinearLayoutManager(mainActivcity)
            adapter = listBookCarAdapter
            itemAnimator = SlideInDownAnimator(OvershootInterpolator(1f))
            itemAnimator?.apply {
                addDuration = 500
                removeDuration = 500
                changeDuration = 500
                moveDuration = 500
            }
            setHasFixedSize(true)
        }
    }

    private fun initData() {
        mainActivcity!!.showLoading()
        val body: GetListBookCarBody = prepareBodyGetListBookCarBody()!!
        API.service.getListBookCar(body).subscribeOn(Schedulers.io()) //(*)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<GetListBookCarRespon> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(getListBookCarRespon: GetListBookCarRespon) {
                    mainActivcity!!.hideLoading()
                    if (getListBookCarRespon.resultInfo.status == CommonVCC.RESULT_STATUS_OK) {
                        viewModel.listBookCar = getListBookCarRespon.lstBookCarDto
                        listBookCarAdapter.swapData(viewModel.listBookCar)

                        if (viewModel.listBookCar.isEmpty()) tv_no_data.visibility = View.VISIBLE
                        else tv_no_data.visibility = View.GONE
                    } else {
                        Toasty.error(
                            activity!!,
                            getListBookCarRespon.resultInfo.message,
                            Toast.LENGTH_SHORT,
                            true
                        ).show()
                    }
                }

                override fun onError(e: Throwable) {
                    mainActivcity!!.hideLoading()
                    Toasty.error(
                        activity!!, getString(R.string.loi_ket_noi), Toast.LENGTH_SHORT, true
                    ).show()
                }
            })
    }

    private fun prepareBodyGetListBookCarBody(): GetListBookCarBody? {
        val userLogin = CommonVCC.getUserLogin()
        val authenticationInfo = AuthenticationInfo(userLogin.loginName, userLogin.password)
        return GetListBookCarBody(authenticationInfo, userLogin.sysUserId, TYPE_MENU, userLogin.email, userLogin.loginName)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_da_duoc_duyet -> filterListBookCar("2")
            R.id.menu_tu_choi -> filterListBookCar("3")
            R.id.menu_yeu_cau_sua -> filterListBookCar("4")
            R.id.menu_dang_cho_duyet -> filterListBookCar("1")
            else -> return false
        }
        return true
    }

    private fun filterListBookCar(statusAdministrative: String) {
        val listFillted = Observable.fromIterable<LstBookCarDto>(viewModel.listBookCar)
            .filter { lstBookCarDto: LstBookCarDto -> lstBookCarDto.statusAdministrative == statusAdministrative }
            .toList().blockingGet()
        listBookCarAdapter.swapData(listFillted)
        tv_no_data.visibility = if (listFillted.size == 0) View.VISIBLE else View.GONE
    }

    override fun omItemBookCar(bookCarDto: LstBookCarDto) {
        val listRoleCode = CommonVCC.getUserLogin().roleCode.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val bundle = Bundle()
        bundle.putSerializable(DetailBookCarFragment.EXTRA_BOOK_CAR, bookCarDto)
        bundle.putSerializable(DetailBookCarFragment.EXTRA_TYPE_MENU, TYPE_MENU)

        mainActivcity!!.navigateFragment(R.id.nav_detail_book_car, bundle)
    }

}
