package com.vcc.trackcar.ui.detail_book_car.open_command

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.shreyaspatil.MaterialDialog.AbstractDialog
import com.shreyaspatil.MaterialDialog.MaterialDialog
import com.vcc.trackcar.MainActivity
import com.vcc.trackcar.R
import com.vcc.trackcar.model.addBookCar.AddBookCarBody
import com.vcc.trackcar.model.addBookCar.AddBookCarRespon
import com.vcc.trackcar.model.addBookCar.BookCarDto
import com.vcc.trackcar.model.getListBookCar.LstBookCarDto
import com.vcc.trackcar.remote.API
import com.vcc.trackcar.ui.base.CommonVCC
import com.vcc.trackcar.ui.select_address.SelectAddressFragment
import es.dmoral.toasty.Toasty
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_open_command.*

class OpenCommandFragment : Fragment() {

    companion object {
        const val EXTRA_OPEN_COMMAND = "EXTRA_OPEN_COMMAND"

        fun newInstance() = OpenCommandFragment()
    }

    private lateinit var mainActivcity: MainActivity
    private lateinit var viewModel: OpenCommandViewModel


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mainActivcity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_open_command, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OpenCommandViewModel::class.java)
        viewModel.bookCarDto = arguments?.getSerializable(EXTRA_OPEN_COMMAND) as LstBookCarDto
        initView()
    }

    private fun initView(){
        viewModel.diem_den.observe(viewLifecycleOwner, Observer<String> { s -> text_pos_from.text = s })
        viewModel.time_end_text.observe(viewLifecycleOwner, Observer { s -> text_time_end.text = s })
        layout_location_from.setOnClickListener {
            selectAddressTo()
        }
        btn_done.setOnClickListener { showDialog() }
    }

    private fun selectAddressTo() {
//        addBookCarViewModel.dataSelectAddress.setType(2)
//        addBookCarViewModel.dataSelectAddress.setData(text_pos_to.getText().toString())
        val bundle = Bundle()
//        bundle.putSerializable(SelectAddressFragment.EXTRA_DATA, addBookCarViewModel.dataSelectAddress)
        mainActivcity.navigateFragment(R.id.nav_select_address, bundle)
    }

    private fun openCommand(){
        mainActivcity.showLoading()

        val bookCarDto = BookCarDto()
        val body = AddBookCarBody(bookCarDto)
        API.service.addBookCar(body)
                .subscribeOn(Schedulers.io()) //(*)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<AddBookCarRespon> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onSuccess(addBookCarRespon: AddBookCarRespon) {
                        mainActivcity.hideLoading()
                        if (addBookCarRespon.resultInfo.status == CommonVCC.RESULT_STATUS_OK) {
                            Toasty.success(activity!!, getString(R.string.success_add_book_car), Toasty.LENGTH_LONG).show()
                            mainActivcity.popBackStackFragment()
                            mainActivcity.navigateFragment(R.id.nav_slideshow, null)
                        } else {
                            Toasty.error(activity!!, addBookCarRespon.resultInfo.message, Toast.LENGTH_SHORT, true).show()
                        }
                    }

                    override fun onError(e: Throwable) {
                        mainActivcity.hideLoading()
                        Toasty.error(activity!!, getString(R.string.loi_ket_noi), Toast.LENGTH_SHORT, true).show()
                    }
                })
    }

    private fun showDialog() {
        val mDialog = MaterialDialog.Builder(activity!!)
                .setTitle(getString(R.string.dat_xe))
                .setMessage(getString(R.string.question_dat_xe_comfirm))
                .setCancelable(false)

                .setPositiveButton(getString(R.string.xac_nhan)) { dialogInterface, which ->
                    openCommand()
                    dialogInterface.dismiss()
                }
                .setNegativeButton(getString(R.string.cancel)) { dialogInterface, which -> dialogInterface.dismiss() }
                .build()

        mDialog.show()
    }
}


