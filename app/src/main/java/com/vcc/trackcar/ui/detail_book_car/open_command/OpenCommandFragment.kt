package com.vcc.trackcar.ui.detail_book_car.open_command

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog
import com.shreyaspatil.MaterialDialog.MaterialDialog
import com.vcc.trackcar.MainActivity
import com.vcc.trackcar.R
import com.vcc.trackcar.model.addBookCar.AddBookCarBody
import com.vcc.trackcar.model.addBookCar.AddBookCarRespon
import com.vcc.trackcar.model.addBookCar.BookCarDto
import com.vcc.trackcar.model.addBookCar.SysUserRequest
import com.vcc.trackcar.model.auth.AuthenticationInfo
import com.vcc.trackcar.model.getListBookCar.LstBookCarDto
import com.vcc.trackcar.model.getListUser.LstUserDto
import com.vcc.trackcar.model.updateBookCar.UpdateBookCarBody
import com.vcc.trackcar.remote.API
import com.vcc.trackcar.ui.add_book_car.AddBookCarViewModel
import com.vcc.trackcar.ui.base.CommonVCC
import com.vcc.trackcar.ui.select_address.SelectAddressFragment
import es.dmoral.toasty.Toasty
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_open_command.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class OpenCommandFragment : Fragment() {

    companion object {
        const val EXTRA_OPEN_COMMAND = "EXTRA_OPEN_COMMAND"
        const val EXTRA_LIST_USER = "EXTRA_LIST_USER"

        fun newInstance() = OpenCommandFragment()
    }

    private lateinit var layout_location_from: RelativeLayout
    private lateinit var layout_time_end: RelativeLayout
    private lateinit var btn_done: AppCompatButton
    private lateinit var btn_tu_choi: AppCompatButton


    private var listPeopleTogetherSelected = ArrayList<LstUserDto>()

    private lateinit var mainActivcity: MainActivity
    private lateinit var viewModel: AddBookCarViewModel
    private lateinit var dialogDataTimeEnd: SingleDateAndTimePickerDialog

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mainActivcity = context as MainActivity
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_open_command, container, false)
        initView(root)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



    }

    private fun initView(root: View) {
        viewModel = ViewModelProviders.of(this).get(AddBookCarViewModel::class.java)
        viewModel.bookCarDto = arguments?.getSerializable(EXTRA_OPEN_COMMAND) as LstBookCarDto
        listPeopleTogetherSelected = arguments?.getSerializable(EXTRA_LIST_USER) as ArrayList<LstUserDto>
        dialogDataTimeEnd = initDateTimePickerDialogEnd(root)

        viewModel.getDiem_den().observe(viewLifecycleOwner, Observer<String> {s ->
            Log.e("Tag: ", s)
            viewModel.dataSelectAddress.data
            text_pos_from.text = s
        })

        viewModel.time_end_text.observe(viewLifecycleOwner, Observer { s -> text_time_end.setText(s) })
        layout_location_from = root.findViewById(R.id.layout_location_from)
        layout_time_end = root.findViewById(R.id.layout_time_end)
        btn_done = root.findViewById(R.id.btn_done)
        btn_tu_choi = root.findViewById(R.id.btn_tu_choi)

        layout_location_from.setOnClickListener {
            selectAddressTo()
        }
        layout_time_end.setOnClickListener { dialogDataTimeEnd.display() }
        btn_done.setOnClickListener { showDialog() }
    }

    private fun selectAddressTo() {
        viewModel.dataSelectAddress.setType(1)
        viewModel.dataSelectAddress.setData(text_pos_from.text.toString())
        val bundle = Bundle()
        bundle.putSerializable(SelectAddressFragment.EXTRA_DATA, viewModel.dataSelectAddress)
        mainActivcity.navigateFragment(R.id.nav_select_address, bundle)

    }

    private fun fetchUpdateBookCar() {
        mainActivcity.showLoading()
        val body = bodyBookCar()

        API.service.extentBookCar(body)
                .subscribeOn(Schedulers.io()) //(*)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<AddBookCarRespon> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onSuccess(respon: AddBookCarRespon) {
                        mainActivcity.hideLoading()
                        if (respon.getResultInfo().getStatus() == CommonVCC.RESULT_STATUS_OK) {
                            activity?.let { Toasty.success(it, respon.getResultInfo().getMessage(), Toasty.LENGTH_LONG).show() }
                            mainActivcity.popBackStackFragment()
                        } else {
                            activity?.let { Toasty.error(it, respon.getResultInfo().getMessage(), Toast.LENGTH_SHORT, true).show() }
                        }
                    }

                    override fun onError(e: Throwable) {
                        mainActivcity.hideLoading()
                        activity?.let { Toasty.error(it, getString(R.string.loi_ket_noi), Toast.LENGTH_SHORT, true).show() }
                    }
                })
    }

    private fun showDialog() {
        val mDialog = MaterialDialog.Builder(activity!!)
                .setTitle(getString(R.string.dat_xe))
                .setMessage(getString(R.string.question_dat_xe_comfirm))
                .setCancelable(false)

                .setPositiveButton(getString(R.string.xac_nhan)) { dialogInterface, _ ->
                    fetchUpdateBookCar()
                    dialogInterface.dismiss()
                }
                .setNegativeButton(getString(R.string.cancel)) { dialogInterface, _ -> dialogInterface.dismiss() }
                .build()

        mDialog.show()
    }

    private fun initDateTimePickerDialogEnd(root: View): SingleDateAndTimePickerDialog {
        return SingleDateAndTimePickerDialog.Builder(root.context)
                .title(root.context.getString(R.string.chon_lich_ket_thuc))
                .titleTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                .mainColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                .bottomSheet()
                .curved()
                .displayMinutes(true)
                .displayHours(true)
                .displayDays(false)
                .displayDaysOfMonth(true)
                .displayMonth(true)
                .displayYears(true)
                .mustBeOnFuture()
                .minDateRange(Calendar.getInstance().time)
                .defaultDate(Calendar.getInstance().time)
                .listener { date ->
                    if (viewModel.dateStart != null && date.time <= viewModel?.dateStart.getTime()) {
                        Toasty.error(activity!!, getString(R.string.ban_phai_chon_thoi_gian_end_sau_start), Toasty.LENGTH_LONG).show()
                    } else {
                        val dateFormat: DateFormat
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.forLanguageTag("vn-VN"))
                        } else {
                            dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("vi", "VN"))
                        }
                        //                            time_end_text.setContentText(dateFormat.format(date), MaterialTextView.ANIMATE_TYPE.NONE);
                        viewModel.setTime_end_text(dateFormat.format(date))
                    }
                }
                .build()
    }


    private fun bodyBookCar(): AddBookCarBody {

        val userLogin = CommonVCC.getUserLogin()

        val bookCarDto = BookCarDto()

        bookCarDto.bookCarId = viewModel.bookCarDto.getBookCarId()

        val intTo = viewModel.bookCarDto.toAddress.lastIndexOf(" ")
        val intFrom = viewModel.bookCarDto.fromAddress.lastIndexOf(" ")
        val strTo = viewModel.bookCarDto.toAddress.substring(intTo)
        val strFrom = viewModel.bookCarDto.fromAddress.substring(intFrom)
        if (strTo == strFrom) {
            bookCarDto.internalProvince = 1
        } else {
            bookCarDto.internalProvince = 2
        }
        bookCarDto.toAddressExtend = viewModel.bookCarDto.fromAddress
        bookCarDto.endTimeExtend = text_time_end.text.toString()
        bookCarDto.content = text_content.text.toString()


        val sysUserRequest = SysUserRequest()
        sysUserRequest.authenticationInfo = AuthenticationInfo(userLogin.loginName, userLogin.password)

        return AddBookCarBody(bookCarDto, listPeopleTogetherSelected, sysUserRequest)
    }
}




