package com.vcc.trackcar.ui.list_book_added.update_book_car;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.materialspinner.MaterialSpinner;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.tedpark.tedpermission.rx2.TedRx2Permission;
import com.vcc.trackcar.MainActivity;
import com.vcc.trackcar.R;
import com.vcc.trackcar.model.addBookCar.BookCarDto;
import com.vcc.trackcar.model.addBookCar.SysUserRequest;
import com.vcc.trackcar.model.auth.AuthenticationInfo;
import com.vcc.trackcar.model.auth.UserLogin;
import com.vcc.trackcar.model.getListManager.GetListManagerBody;
import com.vcc.trackcar.model.getListManager.GetListManagerRespon;
import com.vcc.trackcar.model.getListTypeCar.GetListTypeCarRespon;
import com.vcc.trackcar.model.getListTypeCar.LstBookCarDto;
import com.vcc.trackcar.model.getListUser.GetListUserBody;
import com.vcc.trackcar.model.getListUser.GetListUserRespon;
import com.vcc.trackcar.model.getListUser.LstUserDto;
import com.vcc.trackcar.model.getListUserTogether.GetListUserTogetherBody;
import com.vcc.trackcar.model.getListUserTogether.GetListUserTogetherRespon;
import com.vcc.trackcar.model.updateBookCar.UpdateBookCarBody;
import com.vcc.trackcar.model.updateBookCar.UpdateBookCarRespon;
import com.vcc.trackcar.remote.API;
import com.vcc.trackcar.ui.add_book_car.adapter.OnItemPeopleListener;
import com.vcc.trackcar.ui.add_book_car.adapter.PeopleSelectAdapter;
import com.vcc.trackcar.ui.base.CommonVCC;
import com.vcc.trackcar.ui.select_address.SelectAddressFragment;
import com.vcc.trackcar.ui.select_people.SelectPeopleFragment;
import com.vcc.trackcar.ui.xep_xe_doi_truong_xe.select_driver_car.ListDriverCarFragment;
import com.vcc.trackcar.utils.StringUtil;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uk.co.onemandan.materialtextview.MaterialTextView;

public class UpdateBookCarFragment extends Fragment implements View.OnClickListener, OnItemPeopleListener {

    public static final String EXTRA_BOOK_CAR = "EXTRA_BOOK_CAR";
    private List<String> list_of_items;
    private MaterialSpinner spinner_chon_xe;
    private MaterialSpinner spinner_chon_kieu_di;

    private UpdateBookCarViewModel mViewModel;
    private MainActivity mainActivcity;
    private Button btn_update;
    private RelativeLayout btn_add_nguoi_di_cung;
    private RelativeLayout btn_add_nguoi_phe_duyet;

    private ArrayAdapter adapterCarType;
    private RecyclerView rcv_people_together;
    private PeopleSelectAdapter peopleTogetherAdapter;
    private ArrayList<LstUserDto> listPeopleTogetherSelected = new ArrayList<>();

    private RecyclerView rcv_people_approve;
    private PeopleSelectAdapter peopleApproveAdapter;
    private ArrayList<LstUserDto> listPeopleApproveSelected = new ArrayList<>();

    private RelativeLayout layout_time_start;
    private TextView text_time_start;

    private RelativeLayout layout_time_end;
    private TextView text_time_end;

    private TextView tv_don_vi;
    private TextView tv_ho_ten;
    private TextView tv_phone;
    private TextView tv_email;

    SingleDateAndTimePickerDialog dialogDataTimeStart;
    SingleDateAndTimePickerDialog dialogDataTimeEnd;
    private TextView tv_bien_so, tv_nguoi_lai;
    private LinearLayout layout_chon_xe_va_lai_xe;
    private RelativeLayout layout_chon_xe, layout_chon_lai_xe;
    private ImageView imv_location_pin;

    private TextView text_so_nguoi_di_cung_da_chon;

    private RelativeLayout layout_location_from;
    private TextView text_pos_from;
    private RelativeLayout layout_location_to;
    private TextView text_pos_to;

    private EditText edit_text_content;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(UpdateBookCarViewModel.class);
        View root = inflater.inflate(R.layout.update_book_car_fragment, container, false);
        initView(root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onSaveInstanceState(savedInstanceState);
        initData();
        if (mViewModel.firsrUpdate) initFirstUpdate();
    }

    private void initFirstUpdate() {
        mViewModel.firsrUpdate = false;
        com.vcc.trackcar.model.getListBookCar.LstBookCarDto bookCarDtoUpdate = (com.vcc.trackcar.model.getListBookCar.LstBookCarDto) getArguments().getSerializable(EXTRA_BOOK_CAR);

        tv_don_vi.setText(bookCarDtoUpdate.getDepartmentName() + " - " + bookCarDtoUpdate.getSysGroupName());
        tv_ho_ten.setText(bookCarDtoUpdate.getFullName());
        tv_email.setText(bookCarDtoUpdate.getEmail());
        tv_phone.setText(bookCarDtoUpdate.getPhoneNumber());
        edit_text_content.setText(bookCarDtoUpdate.getContent());

        mViewModel.setDiem_di(bookCarDtoUpdate.getFromAddress());
        mViewModel.setDiem_den(bookCarDtoUpdate.getToAddress());
        mViewModel.setTime_start_text(bookCarDtoUpdate.getStartTime());
        mViewModel.setTime_end_text(bookCarDtoUpdate.getEndTime());

        if (bookCarDtoUpdate.getLicenseCar() != null || bookCarDtoUpdate.getDriverName() != null) {
            mViewModel.carDieuChuyen = new com.vcc.trackcar.model.getListDriverCar.LstBookCarDto();
            mViewModel.carDieuChuyen.setDefaultSortField(bookCarDtoUpdate.getDefaultSortField());
            mViewModel.carDieuChuyen.setMessageColumn(bookCarDtoUpdate.getMessageColumn());
            mViewModel.carDieuChuyen.setCarId(bookCarDtoUpdate.getCarId());
            mViewModel.carDieuChuyen.setLicenseCar(bookCarDtoUpdate.getLicenseCar());
            mViewModel.carDieuChuyen.setDriverId(bookCarDtoUpdate.getDriverId());
            mViewModel.carDieuChuyen.setDriverName(bookCarDtoUpdate.getDriverName());
            mViewModel.carDieuChuyen.setDriverCode(bookCarDtoUpdate.getDriverCode());
        }

        spinner_chon_kieu_di.getSpinner().setSelection(Integer.parseInt(bookCarDtoUpdate.getTypeBookCar()) - 1);
    }

    private void initData() {
        if (mViewModel.carDieuChuyen.getLicenseCar() != null) {
            layout_chon_xe_va_lai_xe.setVisibility(View.VISIBLE);
            tv_bien_so.setText(mViewModel.carDieuChuyen.getLicenseCar());
        }
        if (mViewModel.carDieuChuyen.getDriverName() != null) {
            layout_chon_xe_va_lai_xe.setVisibility(View.VISIBLE);
            tv_nguoi_lai.setText(mViewModel.carDieuChuyen.getDriverName());
        }

        fetchGetListTypeCar();

        if (mViewModel.listTogether.isEmpty()) {
            fetchGetListUser();
        } else {
            peopleTogetherAdapter.swapData(listPeopleTogetherSelected);
            text_so_nguoi_di_cung_da_chon.setText(getString(R.string.num_people_together_selected, listPeopleTogetherSelected.size()));
        }

        if (mViewModel.listManager.isEmpty()){
            fetchGetListManager();
        }else {
            peopleApproveAdapter.swapData(listPeopleApproveSelected);
        }

        com.vcc.trackcar.model.getListBookCar.LstBookCarDto bookCarDtoUpdate = (com.vcc.trackcar.model.getListBookCar.LstBookCarDto) getArguments().getSerializable(EXTRA_BOOK_CAR);
        tv_don_vi.setText(bookCarDtoUpdate.getDepartmentName() + " - " + bookCarDtoUpdate.getSysGroupName());
        tv_ho_ten.setText(bookCarDtoUpdate.getFullName());
        tv_email.setText(bookCarDtoUpdate.getEmail());
        tv_phone.setText(bookCarDtoUpdate.getPhoneNumber());
        edit_text_content.setText(bookCarDtoUpdate.getContent());
        tv_phone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View view) {
                TedRx2Permission.with(view.getContext())
                        .setDeniedMessage(R.string.reject_permission)
                        .setPermissions(Manifest.permission.CALL_PHONE)
                        .request()
                        .subscribe(permissionResult -> {
                            if (permissionResult.isGranted()) {
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:" + bookCarDtoUpdate.getPhoneNumber()));
                                startActivity(intent);
                            }
                        });
            }
        });

        mViewModel.getTime_start_text().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                text_time_start.setText(s);
            }
        });
        mViewModel.getTime_end_text().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                text_time_end.setText(s);
            }
        });

        mViewModel.getDiem_di().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                text_pos_from.setText(s.trim());
            }
        });
        mViewModel.getDiem_den().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                text_pos_to.setText(s.trim());
            }
        });
        if (mViewModel.dataSelectAddress.getType() != 0) {
            if (mViewModel.dataSelectAddress.getType() == 1) {
                mViewModel.setDiem_di(mViewModel.dataSelectAddress.getData());
            } else if (mViewModel.dataSelectAddress.getType() == 2) {
                mViewModel.setDiem_den(mViewModel.dataSelectAddress.getData());
            }
        }
    }

    private void fetchGetListManager() {
        mainActivcity.showLoading();
        GetListManagerBody body = new GetListManagerBody();
        body.setSysGroupId(CommonVCC.getUserLogin().getSysGroupId());
        body.setDepartmentId(CommonVCC.getUserLogin().getDepartmentId());
        API.INSTANCE.getService().getListManager(body)
                .subscribeOn(Schedulers.io()) //(*)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetListManagerRespon>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GetListManagerRespon getListManagerRespon) {
                        mainActivcity.hideLoading();
                        if (getListManagerRespon.getResultInfo().getStatus().equals(CommonVCC.RESULT_STATUS_OK)) {
                            mViewModel.listManager = (ArrayList<LstUserDto>) getListManagerRespon.getLstBookCarDto();

                            com.vcc.trackcar.model.getListBookCar.LstBookCarDto bookCarDtoUpdate = (com.vcc.trackcar.model.getListBookCar.LstBookCarDto) getArguments().getSerializable(EXTRA_BOOK_CAR);
                            for (LstUserDto userDtoAll : mViewModel.listManager) {
                                if ((bookCarDtoUpdate.getManagerStaffId() != null && userDtoAll.getSysUserId().equals(bookCarDtoUpdate.getManagerStaffId()))) {
                                    userDtoAll.setStatus(bookCarDtoUpdate.getStatusManage());
                                    listPeopleApproveSelected.add(userDtoAll);
                                }
                                if ((bookCarDtoUpdate.getCaptainCarName() != null && userDtoAll.getSysUserId().equals(bookCarDtoUpdate.getCaptainCarName()))) {
                                    userDtoAll.setStatus(bookCarDtoUpdate.getStatusCaptainCar());
                                    listPeopleApproveSelected.add(userDtoAll);
                                }
                                if ((bookCarDtoUpdate.getManagerCarName() != null && userDtoAll.getSysUserId().equals(bookCarDtoUpdate.getManagerCarName()))) {
                                    userDtoAll.setStatus(bookCarDtoUpdate.getStatusManagerCar());
                                    listPeopleApproveSelected.add(userDtoAll);
                                }
                                if ((bookCarDtoUpdate.getAdministrativeName() != null && userDtoAll.getSysUserId().equals(bookCarDtoUpdate.getAdministrativeName()))) {
                                    userDtoAll.setStatus(bookCarDtoUpdate.getStatusAdministrative());
                                    listPeopleApproveSelected.add(userDtoAll);
                                }
                                if ((bookCarDtoUpdate.getDriverBoardName() != null && userDtoAll.getSysUserId().equals(bookCarDtoUpdate.getDriverBoardName()))) {
                                    userDtoAll.setStatus(bookCarDtoUpdate.getStatusDriverBoard());
                                    listPeopleApproveSelected.add(userDtoAll);
                                }
                            }
                            peopleApproveAdapter.swapData(listPeopleApproveSelected);
                        } else {
                            Toasty.error(getActivity(), getListManagerRespon.getResultInfo().getMessage(), Toast.LENGTH_SHORT, true).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mainActivcity.hideLoading();
                        Toasty.error(getActivity(), getString(R.string.loi_ket_noi), Toast.LENGTH_SHORT, true).show();
                    }
                });
    }

    private void fetchGetListUser() {
        mainActivcity.showLoading();
        GetListUserBody body = new GetListUserBody();
        body.setSysGroupId(CommonVCC.getUserLogin().getSysGroupId());
        API.INSTANCE.getService().getListUser(body)
                .subscribeOn(Schedulers.io()) //(*)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetListUserRespon>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GetListUserRespon getListUserRespon) {
                        mainActivcity.hideLoading();
                        if (getListUserRespon.getResultInfo().getStatus().equals(CommonVCC.RESULT_STATUS_OK)) {
                            mViewModel.listTogether = (ArrayList<LstUserDto>) getListUserRespon.getLstBookCarDto();
                            fetchGetListUserTogether();
                        } else {
                            Toasty.error(getActivity(), getListUserRespon.getResultInfo().getMessage(), Toast.LENGTH_SHORT, true).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mainActivcity.hideLoading();
                        Toasty.error(getActivity(), getString(R.string.loi_ket_noi), Toast.LENGTH_SHORT, true).show();
                    }
                });
    }

    private void fetchGetListUserTogether() {
        mainActivcity.showLoading();

        com.vcc.trackcar.model.getListBookCar.LstBookCarDto bookCarDtoUpdate = (com.vcc.trackcar.model.getListBookCar.LstBookCarDto) getArguments().getSerializable(EXTRA_BOOK_CAR);
        GetListUserTogetherBody body = new GetListUserTogetherBody();
        body.setBookCarDto(bookCarDtoUpdate);
        SysUserRequest sysUserRequest = new SysUserRequest();
        UserLogin userLogin = CommonVCC.getUserLogin();
        sysUserRequest.setAuthenticationInfo(new AuthenticationInfo(userLogin.getLoginName(), userLogin.getPassword()));
        sysUserRequest.setSysUserId(userLogin.getSysUserId());
        body.setSysUserRequest(sysUserRequest);

        API.INSTANCE.getService().getListUserTogether(body)
                .subscribeOn(Schedulers.io()) //(*)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetListUserTogetherRespon>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GetListUserTogetherRespon respon) {
                        mainActivcity.hideLoading();
                        if (respon.getResultInfo().getStatus().equals(CommonVCC.RESULT_STATUS_OK)) {
                            xuLiMapDataUpdate(respon.getLstBookCarDto());

                            text_so_nguoi_di_cung_da_chon.setText(getString(R.string.num_people_together_selected, respon.getLstBookCarDto().size()));
                        } else {
                            Toasty.error(getActivity(), respon.getResultInfo().getMessage(), Toast.LENGTH_SHORT, true).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mainActivcity.hideLoading();
                        Toasty.error(getActivity(), getString(R.string.loi_ket_noi), Toast.LENGTH_SHORT, true).show();
                    }
                });
    }

    private void xuLiMapDataUpdate(List<LstUserDto> listUserSelected) {
        for (LstUserDto userDtoAll : mViewModel.listTogether) {
            for (LstUserDto userDtoSelected : listUserSelected) {
                if (userDtoAll.getSysUserId().equals(userDtoSelected.getSysUserId())) {
                    listPeopleTogetherSelected.add(userDtoAll);
                }
            }
        }

        peopleTogetherAdapter.swapData(listPeopleTogetherSelected);
        text_so_nguoi_di_cung_da_chon.setText(getString(R.string.num_people_together_selected, listPeopleTogetherSelected.size()));
    }

    private void fetchGetListTypeCar() {
//        mainActivcity.showLoading();
        API.INSTANCE.getService().getListTypeCar()
                .subscribeOn(Schedulers.io()) //(*)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetListTypeCarRespon>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GetListTypeCarRespon getListTypeCarRespon) {
//                        mainActivcity.hideLoading();
                        if (getListTypeCarRespon.getResultInfo().getStatus().equals(CommonVCC.RESULT_STATUS_OK)) {
                            mViewModel.listCarType = getListTypeCarRespon.getLstBookCarDto();

                            list_of_items.clear();
                            list_of_items.addAll(Observable.fromIterable(getListTypeCarRespon.getLstBookCarDto()).map(lstBookCarDto -> lstBookCarDto.getCarTypeName() + "").toList().blockingGet());
                            adapterCarType.notifyDataSetChanged();

                            if (mViewModel.posItemSpCarType != -1)
                                spinner_chon_xe.getSpinner().setSelection(mViewModel.posItemSpCarType);

                            if (mViewModel.firsrUpdateSp) {
                                mViewModel.firsrUpdateSp = false;
                                com.vcc.trackcar.model.getListBookCar.LstBookCarDto bookCarDtoUpdate = (com.vcc.trackcar.model.getListBookCar.LstBookCarDto) getArguments().getSerializable(EXTRA_BOOK_CAR);
                                for (int i = 0; i < mViewModel.listCarType.size(); i++) {
                                    if (mViewModel.listCarType.get(i).getCarTypeName().equals(bookCarDtoUpdate.getCarTypeName())) {
                                        spinner_chon_xe.getSpinner().setSelection(i);
                                        break;
                                    }
                                }
                            }

                        } else {
                            Toasty.error(getActivity(), getListTypeCarRespon.getResultInfo().getMessage(), Toast.LENGTH_SHORT, true).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
//                        mainActivcity.hideLoading();
                        Toasty.error(getActivity(), getString(R.string.loi_ket_noi), Toast.LENGTH_SHORT, true).show();
                    }
                });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mainActivcity = (MainActivity) context;
    }

    private void initView(View root) {
        btn_update = root.findViewById(R.id.btn_update);
        btn_update.setOnClickListener(this);
        btn_add_nguoi_di_cung = root.findViewById(R.id.btn_add_nguoi_di_cung);
        btn_add_nguoi_di_cung.setOnClickListener(this);
        btn_add_nguoi_phe_duyet = root.findViewById(R.id.btn_add_nguoi_phe_duyet);
        btn_add_nguoi_phe_duyet.setOnClickListener(this);

        peopleTogetherAdapter = new PeopleSelectAdapter(this, true);
        rcv_people_together = root.findViewById(R.id.rcv_people_together);
        rcv_people_together.setHasFixedSize(true);
        rcv_people_together.setLayoutManager(new LinearLayoutManager(mainActivcity));
        rcv_people_together.setAdapter(peopleTogetherAdapter);

        peopleApproveAdapter = new PeopleSelectAdapter(this, false);
        rcv_people_approve = root.findViewById(R.id.rcv_people_approve);
        rcv_people_approve.setHasFixedSize(true);
        rcv_people_approve.setLayoutManager(new LinearLayoutManager(mainActivcity));
        rcv_people_approve.setAdapter(peopleApproveAdapter);

        dialogDataTimeStart = initDateTimePickerDialogStart(root);
        layout_time_start = root.findViewById(R.id.layout_time_start);
        text_time_start = root.findViewById(R.id.text_time_start);
        layout_time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDataTimeStart.display();
            }
        });
        dialogDataTimeEnd = initDateTimePickerDialogEnd(root);
        layout_time_end = root.findViewById(R.id.layout_time_end);
        text_time_end = root.findViewById(R.id.text_time_end);
        layout_time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDataTimeEnd.display();
            }
        });

        initSpinnerChonXe(root);

        text_pos_from = root.findViewById(R.id.text_pos_from);
        text_pos_to = root.findViewById(R.id.text_pos_to);
        tv_don_vi = root.findViewById(R.id.tv_don_vi);
        tv_ho_ten = root.findViewById(R.id.tv_ho_ten);
        tv_phone = root.findViewById(R.id.tv_phone);
        tv_email = root.findViewById(R.id.tv_email);

        tv_bien_so = root.findViewById(R.id.tv_bien_so);
        tv_nguoi_lai = root.findViewById(R.id.tv_nguoi_lai);
        layout_chon_xe_va_lai_xe = root.findViewById(R.id.layout_chon_xe_va_lai_xe);
        layout_chon_xe = root.findViewById(R.id.layout_chon_xe);
        layout_chon_xe.setOnClickListener(this);
        layout_chon_lai_xe = root.findViewById(R.id.layout_chon_lai_xe);
        layout_chon_lai_xe.setOnClickListener(this);
        imv_location_pin = root.findViewById(R.id.imv_location_pin);
        imv_location_pin.setOnClickListener(this);

        text_so_nguoi_di_cung_da_chon = root.findViewById(R.id.text_so_nguoi_di_cung_da_chon);

        layout_location_from = root.findViewById(R.id.layout_location_from);
        layout_location_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAddressFrom();
            }
        });
        layout_location_to = root.findViewById(R.id.layout_location_to);
        layout_location_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAddressTo();
            }
        });

        edit_text_content = root.findViewById(R.id.edit_text_content);
        root.findViewById(R.id.scrollView).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mainActivcity.hideKeyBoard();
                return false;
            }
        });
    }

    private void selectAddressTo() {
        mViewModel.dataSelectAddress.setType(2);
        mViewModel.dataSelectAddress.setData(text_pos_to.getText().toString());
        Bundle bundle = new Bundle();
        bundle.putSerializable(SelectAddressFragment.EXTRA_DATA, mViewModel.dataSelectAddress);
        mainActivcity.navigateFragment(R.id.nav_select_address, bundle);
    }

    private void selectAddressFrom() {
        mViewModel.dataSelectAddress.setType(1);
        mViewModel.dataSelectAddress.setData(text_pos_from.getText().toString());
        Bundle bundle = new Bundle();
        bundle.putSerializable(SelectAddressFragment.EXTRA_DATA, mViewModel.dataSelectAddress);
        mainActivcity.navigateFragment(R.id.nav_select_address, bundle);
    }

    private SingleDateAndTimePickerDialog initDateTimePickerDialogStart(View root) {
        return new SingleDateAndTimePickerDialog.Builder(root.getContext())
                .title(root.getContext().getString(R.string.chon_lich_khoi_hanh))
                .titleTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .mainColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .bottomSheet()
                .curved()
                .displayMinutes(true)
                .displayHours(true)
                .displayDays(false)
                .displayDaysOfMonth(true)
                .displayMonth(true)
                .displayYears(true)
                .mustBeOnFuture()
                .minDateRange(Calendar.getInstance().getTime())
                .defaultDate(Calendar.getInstance().getTime())
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {
//                        if (date.getTime() < System.currentTimeMillis()) {
//                            Toasty.error(getActivity(), getString(R.string.chat_ban_phai_chon_lich_trong_tuong_lai), Toasty.LENGTH_LONG).show();
//                        } else {
                        DateFormat dateFormat;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.forLanguageTag("vn-VN"));
                        } else {
                            dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("vi", "VN"));
                        }
//                            time_start_text.setContentText(dateFormat.format(date), MaterialTextView.ANIMATE_TYPE.NONE);
                        mViewModel.dateStart = date;
                        mViewModel.setTime_start_text(dateFormat.format(date));
//                        }
                    }
                })
                .build();
    }

    private SingleDateAndTimePickerDialog initDateTimePickerDialogEnd(View root) {
        return new SingleDateAndTimePickerDialog.Builder(root.getContext())
                .title(root.getContext().getString(R.string.chon_lich_ket_thuc))
                .titleTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .mainColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .bottomSheet()
                .curved()
                .displayMinutes(true)
                .displayHours(true)
                .displayDays(false)
                .displayDaysOfMonth(true)
                .displayMonth(true)
                .displayYears(true)
                .mustBeOnFuture()
                .minDateRange(Calendar.getInstance().getTime())
                .defaultDate(Calendar.getInstance().getTime())
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {
                        if (mViewModel.dateStart != null && date.getTime() <= mViewModel.dateStart.getTime()) {
                            Toasty.error(getActivity(), getString(R.string.ban_phai_chon_thoi_gian_end_sau_start), Toasty.LENGTH_LONG).show();
                        } else {
                            DateFormat dateFormat;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.forLanguageTag("vn-VN"));
                            } else {
                                dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("vi", "VN"));
                            }
//                            time_end_text.setContentText(dateFormat.format(date), MaterialTextView.ANIMATE_TYPE.NONE);
                            mViewModel.setTime_end_text(dateFormat.format(date));
                        }
                    }
                })
                .build();
    }

    private void initSpinnerChonXe(View root) {
        list_of_items = new ArrayList<>();
        spinner_chon_xe = root.findViewById(R.id.spinner_chon_xe);
//        spinner_chon_xe.setLabel(getActivity().getString(R.string.chon_loai_xe));
        spinner_chon_xe.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mViewModel.posItemSpCarType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        adapterCarType = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list_of_items);
        adapterCarType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_chon_xe.setAdapter(adapterCarType);

//      spinner_chon_xe chon kieu di (1 chiều, 2 chiều, khẩn cấp, khác)
        List<String> list_book_car_items = new ArrayList<>();
        list_book_car_items.add(getString(R.string.mot_chieu));
        list_book_car_items.add(getString(R.string.hai_chieu));
        list_book_car_items.add(getString(R.string.phat_sinh));
        list_book_car_items.add(getString(R.string.dac_biet));
        spinner_chon_kieu_di = root.findViewById(R.id.spinner_chon_kieu_di);
//        spinner_chon_kieu_di.setLabel(getActivity().getString(R.string.chon_kieu_di));
        spinner_chon_kieu_di.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mViewModel.typeBookCar = Integer.toString(position + 1);
                if (mViewModel.typeBookCar.equals("4")) {
                    hienThiChonXe(true);
                } else {
                    hienThiChonXe(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter adapterBookType = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list_book_car_items);
        adapterBookType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_chon_kieu_di.setAdapter(adapterBookType);
    }

    private void hienThiChonXe(boolean isVisibility) {
        layout_chon_xe_va_lai_xe.setVisibility(isVisibility ? View.VISIBLE : View.GONE);
        if (mViewModel.carDieuChuyen.getLicenseCar() != null) {
            tv_bien_so.setText(mViewModel.carDieuChuyen.getLicenseCar());
        }
        if (mViewModel.carDieuChuyen.getDriverName() != null) {
            tv_nguoi_lai.setText(mViewModel.carDieuChuyen.getDriverName());
        }
    }

    private void showDialog() {
        MaterialDialog mDialog = new MaterialDialog.Builder(getActivity())
                .setTitle(getString(R.string.update))
                .setMessage(getString(R.string.question_update_comfirm))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.xac_nhan), new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        fetchUpdateBookCar();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        mDialog.show();
    }

    private void fetchUpdateBookCar() {
        mainActivcity.showLoading();
        UpdateBookCarBody body = prepareBodyUpdateBookCar();

        API.INSTANCE.getService().updateBookCar(body)
                .subscribeOn(Schedulers.io()) //(*)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<UpdateBookCarRespon>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(UpdateBookCarRespon updateBookCarRespon) {
                        mainActivcity.hideLoading();
                        if (updateBookCarRespon.getResultInfo().getStatus().equals(CommonVCC.RESULT_STATUS_OK)) {
                            Toasty.success(getActivity(), updateBookCarRespon.getResultInfo().getMessage(), Toasty.LENGTH_LONG).show();
                            mainActivcity.popBackStackFragment();
                        } else {
                            Toasty.error(getActivity(), updateBookCarRespon.getResultInfo().getMessage(), Toast.LENGTH_SHORT, true).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mainActivcity.hideLoading();
                        Toasty.error(getActivity(), getString(R.string.loi_ket_noi), Toast.LENGTH_SHORT, true).show();
                    }
                });
    }

    private UpdateBookCarBody prepareBodyUpdateBookCar() {
        com.vcc.trackcar.model.getListBookCar.LstBookCarDto bookCarDtoUpdate = (com.vcc.trackcar.model.getListBookCar.LstBookCarDto) getArguments().getSerializable(EXTRA_BOOK_CAR);

        UserLogin userLogin = CommonVCC.getUserLogin();

        BookCarDto bookCarDto = new BookCarDto();
        bookCarDto.setCreatedDateView(bookCarDtoUpdate.getCreatedDateView());
        bookCarDto.setCode(bookCarDtoUpdate.getCode());
        bookCarDto.setBookCarId(bookCarDtoUpdate.getBookCarId());
        bookCarDto.setSysUserId(userLogin.getSysUserId() + "");
        bookCarDto.setLoginName(userLogin.getLoginName());
        bookCarDto.setFullName(userLogin.getFullName());
        bookCarDto.setEmail(userLogin.getEmail());
        bookCarDto.setPhoneNumber(userLogin.getPhoneNumber());
        bookCarDto.setSysGroupId(userLogin.getSysGroupId());
        bookCarDto.setSysGroupName(userLogin.getSysGroupName());
        bookCarDto.setDepartmentId(userLogin.getDepartmentId());
        bookCarDto.setDepartmentName(userLogin.getDepartmentName());
        LstBookCarDto lstBookCarDto = mViewModel.listCarType.get(mViewModel.posItemSpCarType);
        bookCarDto.setCarTypeId(lstBookCarDto.getCarTypeId());
        bookCarDto.setCarTypeName(lstBookCarDto.getCarTypeName());
        bookCarDto.setTypeBookCar(mViewModel.typeBookCar);
        bookCarDto.setStartTime(text_time_start.getText().toString());
        bookCarDto.setEndTime(text_time_end.getText().toString());
        bookCarDto.setFromAddress(text_pos_from.getText().toString());
        bookCarDto.setToAddress(text_pos_to.getText().toString());
        bookCarDto.setNumPersonTogether(listPeopleTogetherSelected.size());
        LstUserDto lstUserDto = listPeopleApproveSelected.get(0);
        bookCarDto.setManagerStaffId(lstUserDto.getSysUserId());
        bookCarDto.setManagerStaffName(lstUserDto.getFullName());
        bookCarDto.setManagerStaffEmail(lstUserDto.getEmail());
        if (mViewModel.typeBookCar.equals("4")) {
            bookCarDto.setCarId(mViewModel.carDieuChuyen.getCarId());
            bookCarDto.setLicenseCar(mViewModel.carDieuChuyen.getLicenseCar());
            bookCarDto.setDriverId(mViewModel.carDieuChuyen.getDriverId());
            bookCarDto.setDriverName(mViewModel.carDieuChuyen.getDriverName());
            bookCarDto.setDriverCode(mViewModel.carDieuChuyen.getDriverCode());
            bookCarDto.setPhoneNumberDriver(mViewModel.carDieuChuyen.getPhoneNumberDriver());
        }

        int intTo = text_pos_to.getText().toString().lastIndexOf( " ");
        int intFrom = text_pos_from.getText().toString().lastIndexOf( " ");
        String strTo = text_pos_to.getText().toString().substring(intTo);
        String strFrom = text_pos_from.getText().toString().substring(intFrom);
        if (strTo.equals(strFrom)){
            bookCarDto.setInternalProvince(1);
        }else {
            bookCarDto.setInternalProvince(2);
        }

        bookCarDto.setContent(edit_text_content.getText().toString());

        SysUserRequest sysUserRequest = new SysUserRequest();
        sysUserRequest.setAuthenticationInfo(new AuthenticationInfo(userLogin.getLoginName(), userLogin.getPassword()));

        UpdateBookCarBody updateBookCarBody = new UpdateBookCarBody(bookCarDto, listPeopleTogetherSelected, sysUserRequest);
        return updateBookCarBody;
    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.btn_update:
                mainActivcity.hideKeyBoard();
                if (verifyBookCar()) {
                    showDialog();
                }
                break;
            case R.id.btn_add_nguoi_di_cung:
                if (mViewModel.listTogether.isEmpty()) {
                    fetchGetListUser();
                } else {
                    bundle.putSerializable(SelectPeopleFragment.LIST_USER_ALL, mViewModel.listTogether);
                    bundle.putSerializable(SelectPeopleFragment.LIST_USER_DTO, listPeopleTogetherSelected);
                    mainActivcity.navigateFragment(R.id.nav_select_people, bundle);
                }
                break;
            case R.id.btn_add_nguoi_phe_duyet:
                if (mViewModel.listManager.isEmpty()) {
                    fetchGetListManager();
                } else {
                    bundle.putSerializable(SelectPeopleFragment.LIST_USER_ALL, mViewModel.listManager);
                    bundle.putSerializable(SelectPeopleFragment.LIST_USER_DTO, listPeopleApproveSelected);
                    bundle.putBoolean(SelectPeopleFragment.ONLY_ONE_PEOPLE_APPROVE, true);
                    mainActivcity.navigateFragment(R.id.nav_select_people, bundle);
                }
                break;
            case R.id.layout_chon_xe:
                mViewModel.bookCarDto = new com.vcc.trackcar.model.getListBookCar.LstBookCarDto();
                mViewModel.bookCarDto.setCarTypeId(mViewModel.listCarType.get(mViewModel.posItemSpCarType).getCarTypeId());
                mViewModel.bookCarDto.setCarTypeName(mViewModel.listCarType.get(mViewModel.posItemSpCarType).getCarTypeName());
                bundle.putSerializable(ListDriverCarFragment.EXTRA_BOOK_CAR_LIST_DRIVER, mViewModel.bookCarDto);
                bundle.putSerializable(ListDriverCarFragment.EXTRA_SELECTED_CAR_LIST_DRIVER, mViewModel.carDieuChuyen);
                mainActivcity.navigateFragment(R.id.nav_select_car_xe, bundle);
                break;
            case R.id.layout_chon_lai_xe:
                mViewModel.bookCarDto = new com.vcc.trackcar.model.getListBookCar.LstBookCarDto();
                mViewModel.bookCarDto.setCarTypeId(mViewModel.listCarType.get(mViewModel.posItemSpCarType).getCarTypeId());
                mViewModel.bookCarDto.setCarTypeName(mViewModel.listCarType.get(mViewModel.posItemSpCarType).getCarTypeName());
                bundle.putSerializable(ListDriverCarFragment.EXTRA_BOOK_CAR_LIST_DRIVER, mViewModel.bookCarDto);
                bundle.putSerializable(ListDriverCarFragment.EXTRA_SELECTED_CAR_LIST_DRIVER, mViewModel.carDieuChuyen);
                mainActivcity.navigateFragment(R.id.nav_select_car, bundle);
                break;
            case R.id.imv_location_pin:
                if (mViewModel.carDieuChuyen.getLicenseCar() == null || mViewModel.carDieuChuyen.getLicenseCar().equals("")){
                    Toasty.error(getActivity(), getString(R.string.please_choose_car_driver), Toast.LENGTH_SHORT).show();
                    break;
                }
                bundle.putSerializable(ListDriverCarFragment.EXTRA_SELECTED_CAR_LIST_DRIVER, mViewModel.carDieuChuyen);
                mainActivcity.navigateFragment(R.id.nav_radar_driver, bundle);
                break;
        }
    }

    private boolean verifyBookCar() {
        if (StringUtil.isNullOrEmpty(text_pos_from.getText().toString())) {
            Toasty.error(getActivity(), getString(R.string.please_pos_from), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtil.isNullOrEmpty(text_pos_to.getText().toString())) {
            Toasty.error(getActivity(), getString(R.string.please_pos_to), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtil.isNullOrEmpty(text_time_start.getText().toString())) {
            Toasty.error(getActivity(), getString(R.string.please_time_start), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtil.isNullOrEmpty(text_time_end.getText().toString())) {
            Toasty.error(getActivity(), getString(R.string.please_time_end), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtil.isNullOrEmpty(edit_text_content.getText().toString())) {
            Toasty.error(getActivity(), getString(R.string.please_input_content), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mViewModel.typeBookCar.equals("4") && (mViewModel.carDieuChuyen.getLicenseCar() == null || mViewModel.carDieuChuyen.getLicenseCar().equals(""))) {
            Toasty.error(getActivity(), getString(R.string.please_choose_car_driver), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mViewModel.typeBookCar.equals("4") && (mViewModel.carDieuChuyen.getDriverName() == null || mViewModel.carDieuChuyen.getDriverName().equals(""))) {
            Toasty.error(getActivity(), getString(R.string.please_choose_driver), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (listPeopleTogetherSelected == null || listPeopleTogetherSelected.isEmpty()) {
            Toasty.error(getActivity(), getString(R.string.please_choose_people_together), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (listPeopleApproveSelected == null || listPeopleApproveSelected.isEmpty()) {
            Toasty.error(getActivity(), getString(R.string.please_choose_people_approve), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialogDataTimeStart.isDisplaying()) dialogDataTimeStart.dismiss();
        if (dialogDataTimeEnd.isDisplaying()) dialogDataTimeEnd.dismiss();
    }

    @Override
    public void onItemPeopleDelete(@NotNull LstUserDto lstUserDto, boolean isPeopleTogether) {
        if (isPeopleTogether) {
            listPeopleTogetherSelected.remove(lstUserDto);
            peopleTogetherAdapter.swapData(listPeopleTogetherSelected);
            text_so_nguoi_di_cung_da_chon.setText(getString(R.string.num_people_together_selected, listPeopleTogetherSelected.size()));
        } else {
            listPeopleApproveSelected.remove(lstUserDto);
            peopleApproveAdapter.swapData(listPeopleApproveSelected);
        }
    }
}