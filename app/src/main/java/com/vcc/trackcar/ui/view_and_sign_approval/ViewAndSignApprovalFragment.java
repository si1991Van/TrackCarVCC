package com.vcc.trackcar.ui.view_and_sign_approval;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vcc.trackcar.MainActivity;
import com.vcc.trackcar.R;
import com.vcc.trackcar.model.auth.AuthenticationInfo;
import com.vcc.trackcar.model.auth.UserLogin;
import com.vcc.trackcar.model.getListBookCar.GetListBookCarBody;
import com.vcc.trackcar.model.getListBookCar.GetListBookCarRespon;
import com.vcc.trackcar.model.getListBookCar.LstBookCarDto;
import com.vcc.trackcar.remote.API;
import com.vcc.trackcar.ui.base.CommonVCC;
import com.vcc.trackcar.ui.detail_book_car.DetailBookCarFragment;
import com.vcc.trackcar.ui.list_book_added.adapter.ListBookCarAdapter;
import com.vcc.trackcar.ui.list_book_added.adapter.OnItemBookListener;
import com.vcc.trackcar.utils.CommonUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ViewAndSignApprovalFragment extends Fragment implements OnItemBookListener {

    public static final Integer TYPE_MENU = 3;
    private ViewAndSignApprovalViewModel viewAndSignApprovalViewModel;
    private RecyclerView rcv_list_book_car;
    private MainActivity mainActivcity;
    private ListBookCarAdapter listBookCarAdapter;
    private TextView tv_no_data;
    private EditText edtSearch;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mainActivcity = (MainActivity) context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewAndSignApprovalViewModel =
                ViewModelProviders.of(this).get(ViewAndSignApprovalViewModel.class);
        View root = inflater.inflate(R.layout.fragment_view_and_sign_approval, container, false);
        setHasOptionsMenu(true);
        initView(root);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_list_book_added_fragment, menu);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        mainActivcity.showLoading();
        GetListBookCarBody body = prepareBodyGetListBookCarBody();
        API.INSTANCE.getService().getListBookCar(body)
                .subscribeOn(Schedulers.io()) //(*)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetListBookCarRespon>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GetListBookCarRespon getListBookCarRespon) {
                        mainActivcity.hideLoading();
                        if (getListBookCarRespon.getResultInfo().getStatus().equals(CommonVCC.RESULT_STATUS_OK)) {
                            viewAndSignApprovalViewModel.listBookCar = getListBookCarRespon.getLstBookCarDto();
                            listBookCarAdapter.swapData(viewAndSignApprovalViewModel.listBookCar);

                            if (viewAndSignApprovalViewModel.listBookCar.isEmpty())
                                tv_no_data.setVisibility(View.VISIBLE);
                            else tv_no_data.setVisibility(View.GONE);
                        } else {
                            Toasty.error(getActivity(), getListBookCarRespon.getResultInfo().getMessage(), Toast.LENGTH_SHORT, true).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mainActivcity.hideLoading();
                        Toasty.error(getActivity(), getString(R.string.loi_ket_noi), Toast.LENGTH_SHORT, true).show();
                    }
                });
    }

    private GetListBookCarBody prepareBodyGetListBookCarBody() {
        UserLogin userLogin = CommonVCC.getUserLogin();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(userLogin.getLoginName(), userLogin.getPassword());
        GetListBookCarBody getListBookCarBody = new GetListBookCarBody(authenticationInfo, userLogin.getSysUserId(), TYPE_MENU, userLogin.getEmail(), userLogin.getLoginName()); // 5064
        return getListBookCarBody;
    }

    private void initView(View root) {
        listBookCarAdapter = new ListBookCarAdapter(getActivity(), this, TYPE_MENU);
        rcv_list_book_car = root.findViewById(R.id.rcv_list_book_car);
        rcv_list_book_car.setHasFixedSize(true);
        rcv_list_book_car.setLayoutManager(new LinearLayoutManager(mainActivcity));
        rcv_list_book_car.setAdapter(listBookCarAdapter);

        tv_no_data = root.findViewById(R.id.tv_no_data);
        edtSearch = root.findViewById(R.id.edtSearch);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewAndSignApprovalViewModel.listBookCar = CommonUtils.INSTANCE.searchBookCarDTO(s.toString(), viewAndSignApprovalViewModel.listBookCar);
                listBookCarAdapter.swapData(viewAndSignApprovalViewModel.listBookCar);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_da_duoc_duyet:
                filterListBookCar("2");
                break;
            case R.id.menu_tu_choi:
                filterListBookCar("3");
                break;
            case R.id.menu_yeu_cau_sua:
                filterListBookCar("4");
                break;
            case R.id.menu_dang_cho_duyet:
                filterListBookCar("1");
                break;
            case R.id.menu_nhan_vien_dong_lenh:
                filterListBookCar("5");
                break;
            case R.id.menu_lai_xe_dong_lenh:
                filterListBookCar("6");
                break;
            default:
                return false;
        }
        return true;
    }

    private void filterListBookCar(String status) {
        List<LstBookCarDto> listFillted = Observable.fromIterable(viewAndSignApprovalViewModel.listBookCar).filter(lstBookCarDto -> lstBookCarDto.getStatus().equals(status)).toList().blockingGet();
        listBookCarAdapter.swapData(listFillted);
        tv_no_data.setVisibility(listFillted.size() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void omItemBookCar(@NotNull LstBookCarDto bookCarDto) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DetailBookCarFragment.EXTRA_BOOK_CAR, bookCarDto);
        bundle.putSerializable(DetailBookCarFragment.EXTRA_TYPE_MENU, TYPE_MENU);
        mainActivcity.navigateFragment(R.id.nav_detail_book_car, bundle);
    }
}