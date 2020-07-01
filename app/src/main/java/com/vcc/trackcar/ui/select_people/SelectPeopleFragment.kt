package com.vcc.trackcar.ui.select_people

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.vcc.trackcar.model.getListUser.LstUserDto
import com.vcc.trackcar.ui.select_people.adapter.ListUserAdapter
import com.vcc.trackcar.ui.select_people.adapter.OnItemListener
import com.vcc.trackcar.utils.StringUtil
import es.dmoral.toasty.Toasty
import io.reactivex.Observable
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import kotlinx.android.synthetic.main.search_box.*
import kotlinx.android.synthetic.main.select_people_fragment.*

class SelectPeopleFragment : Fragment(), OnItemListener {

    private lateinit var mainActivity: MainActivity
    private var listUserAdapter = ListUserAdapter(this)

    companion object {
        const val LIST_USER_ALL: String = "LIST_USER_ALL"
        const val LIST_USER_DTO: String = "LIST_USER_DTO"
        const val ONLY_ONE_PEOPLE_APPROVE: String = "ONLY_ONE_PEOPLE_APPROVE"

        fun newInstance() = SelectPeopleFragment()
    }

    private lateinit var viewModel: SelectPeopleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.select_people_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivity.hideKeyBoard()
    }

    private fun initData() {
        viewModel = ViewModelProviders.of(this).get(SelectPeopleViewModel::class.java)
        viewModel.listUserAll = arguments?.getSerializable(LIST_USER_ALL) as ArrayList<LstUserDto>
        viewModel.listUserSelect = arguments?.getSerializable(LIST_USER_DTO) as ArrayList<LstUserDto>
        viewModel.isOnlyOnePeopleApprove = arguments?.getBoolean(ONLY_ONE_PEOPLE_APPROVE, false)

        listUserAdapter.swapData(viewModel.listUserAll)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    private fun initView() {
        rcv_people_select.run {
            layoutManager = LinearLayoutManager(mainActivity)
            adapter = listUserAdapter
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
                var resultSearch = Observable.fromIterable(viewModel.listUserAll).filter { t ->
                    (t.fullName != null && StringUtil.removeAccentStr(t.fullName.trim()).toLowerCase().contains(
                        keySearch
                    )) || (t.email != null && StringUtil.removeAccentStr(t.email.trim()).toLowerCase().contains(
                        keySearch
                    ))
                }.toList().blockingGet()
                listUserAdapter.swapData(resultSearch)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        imgClearTextSearch.setOnClickListener {
            edtSearch.setText("")
            listUserAdapter.swapData(viewModel.listUserAll)
        }
    }

    override fun onItemUserSelected(userDto: LstUserDto) {
        if (viewModel.listUserSelect.contains(userDto)) {
            Toasty.success(
                mainActivity,
                userDto.fullName + " " + getString(R.string.da_duoc_chon),
                Toast.LENGTH_SHORT,
                true
            ).show()
        } else {
            if (viewModel.isOnlyOnePeopleApprove!!) {
                viewModel.listUserSelect.clear()
            }
            viewModel.listUserSelect.add(userDto)
            mainActivity.popBackStackFragment()
        }
    }

}
