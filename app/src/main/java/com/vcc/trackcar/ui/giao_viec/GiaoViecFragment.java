package com.vcc.trackcar.ui.giao_viec;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.vcc.trackcar.ui.giao_viec.detail_giao_viec.DetailGiaoViecFragment;
import com.vcc.trackcar.ui.list_book_added.ListBookAddedViewModel;
import com.vcc.trackcar.ui.list_book_added.adapter.ListBookCarAdapter;
import com.vcc.trackcar.ui.list_book_added.adapter.OnItemBookListener;

import org.jetbrains.annotations.NotNull;

import es.dmoral.toasty.Toasty;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class GiaoViecFragment extends Fragment implements OnItemBookListener {

    public static final Integer TYPE_MENU = 6;

    private GiaoViecViewModel giaoViecViewModel;
    private MainActivity mainActivcity;
    private RecyclerView rcv_list_book_car;
    private ListBookCarAdapter listBookCarAdapter;
    private TextView tv_no_data;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mainActivcity = (MainActivity) context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        giaoViecViewModel = ViewModelProviders.of(this).get(GiaoViecViewModel.class);
        View root = inflater.inflate(R.layout.fragment_giao_viec, container, false);
//        final TextView textView = root.findViewById(R.id.text_share);
//        giaoViecViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        initView(root);
        return root;
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
                            giaoViecViewModel.listBookCar = getListBookCarRespon.getLstBookCarDto();
                            listBookCarAdapter.swapData(giaoViecViewModel.listBookCar);

                            if (giaoViecViewModel.listBookCar.isEmpty())
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
        GetListBookCarBody getListBookCarBody = new GetListBookCarBody(authenticationInfo, userLogin.getSysUserId(), TYPE_MENU, userLogin.getEmail(), userLogin.getLoginName());
        return getListBookCarBody;
    }

    private void initView(View root) {
        listBookCarAdapter = new ListBookCarAdapter(getActivity(), this, TYPE_MENU);
        rcv_list_book_car = root.findViewById(R.id.rcv_list_book_car);
        rcv_list_book_car.setHasFixedSize(true);
        rcv_list_book_car.setLayoutManager(new LinearLayoutManager(mainActivcity));
        rcv_list_book_car.setAdapter(listBookCarAdapter);

        tv_no_data = root.findViewById(R.id.tv_no_data);
    }

    @Override
    public void omItemBookCar(@NotNull LstBookCarDto bookCarDto) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DetailGiaoViecFragment.EXTRA_BOOK_CAR, bookCarDto);
        bundle.putSerializable(DetailGiaoViecFragment.EXTRA_TYPE_MENU, TYPE_MENU);
        mainActivcity.navigateFragment(R.id.nav_detail_giao_viec, bundle);
    }
}