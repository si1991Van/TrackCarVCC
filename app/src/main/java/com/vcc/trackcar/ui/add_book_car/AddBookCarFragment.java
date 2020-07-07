package com.vcc.trackcar.ui.add_book_car;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.materialspinner.MaterialSpinner;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.tedpark.tedpermission.rx2.TedRx2Permission;
import com.vcc.trackcar.MainActivity;
import com.vcc.trackcar.R;
import com.vcc.trackcar.model.addBookCar.AddBookCarBody;
import com.vcc.trackcar.model.addBookCar.AddBookCarRespon;
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
import com.vcc.trackcar.remote.API;
import com.vcc.trackcar.ui.base.CommonVCC;
import com.vcc.trackcar.ui.add_book_car.adapter.OnItemPeopleListener;
import com.vcc.trackcar.ui.add_book_car.adapter.PeopleSelectAdapter;
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

public class AddBookCarFragment extends Fragment implements View.OnClickListener, OnItemPeopleListener {

    private List<String> list_of_items;
    private MaterialSpinner spinner_chon_xe;
    private MaterialSpinner spinner_chon_kieu_di;

    private AddBookCarViewModel addBookCarViewModel;
    private MainActivity mainActivcity;
    private Button btn_dat_xe;
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
    private LinearLayout lnInternalProvince;
    private TextView txtInternalProvince;
    private EditText edit_text_goodsWeight;
    private String strTo = "";
    private String strFrom = "";
    private EditText edit_text_content;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addBookCarViewModel = ViewModelProviders.of(this).get(AddBookCarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_book_car, container, false);
        initView(root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onSaveInstanceState(savedInstanceState);
        initData();
    }

    private void initData() {
        if (addBookCarViewModel.carDieuChuyen.getLicenseCar() != null) {
            layout_chon_xe_va_lai_xe.setVisibility(View.VISIBLE);
            tv_bien_so.setText(addBookCarViewModel.carDieuChuyen.getLicenseCar());
        }
        if (addBookCarViewModel.carDieuChuyen.getDriverName() != null) {
            layout_chon_xe_va_lai_xe.setVisibility(View.VISIBLE);
            tv_nguoi_lai.setText(addBookCarViewModel.carDieuChuyen.getDriverName());
        }

        fetchGetListTypeCar();

        if (addBookCarViewModel.listTogether.isEmpty()) {
            fetchGetListUser();
        }else {
            peopleTogetherAdapter.swapData(listPeopleTogetherSelected);
            text_so_nguoi_di_cung_da_chon.setText(getString(R.string.num_people_together_selected, listPeopleTogetherSelected.size()));
        }

        if (addBookCarViewModel.listManager.isEmpty()){
            fetchGetListManager();
        }else {
            peopleApproveAdapter.swapData(listPeopleApproveSelected);
        }

        UserLogin userLogin = CommonVCC.getUserLogin();
        tv_don_vi.setText(userLogin.getDepartmentName() + " - " + userLogin.getSysGroupName());
        tv_ho_ten.setText(userLogin.getFullName());
        tv_email.setText(userLogin.getEmail());
        tv_phone.setText(userLogin.getPhoneNumber());
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
                                intent.setData(Uri.parse("tel:" + userLogin.getPhoneNumber()));
                                startActivity(intent);
                            }
                        });
            }
        });

        addBookCarViewModel.getTime_start_text().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                text_time_start.setText(s);
            }
        });
        addBookCarViewModel.getTime_end_text().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                text_time_end.setText(s);
            }
        });

        addBookCarViewModel.getDiem_di().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                text_pos_from.setText(s.trim());
            }
        });
        addBookCarViewModel.getDiem_den().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                lnInternalProvince.setVisibility(View.VISIBLE);
                text_pos_to.setText(s.trim());
                int intTo = text_pos_to.getText().toString().lastIndexOf( " ");
                int intFrom = text_pos_from.getText().toString().lastIndexOf( " ");
                strTo = text_pos_to.getText().toString().substring(intTo);
                strFrom = text_pos_from.getText().toString().substring(intFrom);
                if (strTo.equals(strFrom)){
                    txtInternalProvince.setText("Đi nội tỉnh");
                }else {
                    txtInternalProvince.setText("Đi ngoại tỉnh");
                }
            }
        });
        if (addBookCarViewModel.dataSelectAddress.getType() != 0) {
            if (addBookCarViewModel.dataSelectAddress.getType() == 1) {
                addBookCarViewModel.setDiem_di(addBookCarViewModel.dataSelectAddress.getData());
            } else if (addBookCarViewModel.dataSelectAddress.getType() == 2) {
                addBookCarViewModel.setDiem_den(addBookCarViewModel.dataSelectAddress.getData());
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
                            addBookCarViewModel.listManager = (ArrayList<LstUserDto>) getListManagerRespon.getLstBookCarDto();
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
                            addBookCarViewModel.listTogether = (ArrayList<LstUserDto>) getListUserRespon.getLstBookCarDto();
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
                            addBookCarViewModel.listCarType = getListTypeCarRespon.getLstBookCarDto();
                            list_of_items.clear();
                            list_of_items.addAll(Observable.fromIterable(getListTypeCarRespon.getLstBookCarDto()).map(lstBookCarDto -> lstBookCarDto.getCarTypeName() + "").toList().blockingGet());
                            adapterCarType.notifyDataSetChanged();

                            if (addBookCarViewModel.posItemSpCarType != -1)
                                spinner_chon_xe.getSpinner().setSelection(addBookCarViewModel.posItemSpCarType);
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
        btn_dat_xe = root.findViewById(R.id.layout_duyet_tuchoi_sua);
        btn_dat_xe.setOnClickListener(this);
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
        lnInternalProvince = root.findViewById(R.id.lnInternalProvince);
        txtInternalProvince = root.findViewById(R.id.txtInternalProvince);
        edit_text_goodsWeight = root.findViewById(R.id.edit_text_goodsWeight);


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
        addBookCarViewModel.dataSelectAddress.setType(2);
        addBookCarViewModel.dataSelectAddress.setData(text_pos_to.getText().toString());
        Bundle bundle = new Bundle();
        bundle.putSerializable(SelectAddressFragment.EXTRA_DATA, addBookCarViewModel.dataSelectAddress);
        mainActivcity.navigateFragment(R.id.nav_select_address, bundle);
    }

    private void selectAddressFrom() {
        addBookCarViewModel.dataSelectAddress.setType(1);
        addBookCarViewModel.dataSelectAddress.setData(text_pos_from.getText().toString());
        Bundle bundle = new Bundle();
        bundle.putSerializable(SelectAddressFragment.EXTRA_DATA, addBookCarViewModel.dataSelectAddress);
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
                        addBookCarViewModel.dateStart = date;
                        addBookCarViewModel.setTime_start_text(dateFormat.format(date));
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
                        if (addBookCarViewModel.dateStart != null && date.getTime() <= addBookCarViewModel.dateStart.getTime()) {
                            Toasty.error(getActivity(), getString(R.string.ban_phai_chon_thoi_gian_end_sau_start), Toasty.LENGTH_LONG).show();
                        } else {
                            DateFormat dateFormat;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.forLanguageTag("vn-VN"));
                            } else {
                                dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("vi", "VN"));
                            }
//                            time_end_text.setContentText(dateFormat.format(date), MaterialTextView.ANIMATE_TYPE.NONE);
                            addBookCarViewModel.setTime_end_text(dateFormat.format(date));
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
                addBookCarViewModel.posItemSpCarType = position;
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
                addBookCarViewModel.typeBookCar = Integer.toString(position + 1);
                if (addBookCarViewModel.typeBookCar.equals("4")) {
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
        if (addBookCarViewModel.carDieuChuyen.getLicenseCar() != null) {
            tv_bien_so.setText(addBookCarViewModel.carDieuChuyen.getLicenseCar());
        }
        if (addBookCarViewModel.carDieuChuyen.getDriverName() != null) {
            tv_nguoi_lai.setText(addBookCarViewModel.carDieuChuyen.getDriverName());
        }
    }

    private void showDialog() {
        MaterialDialog mDialog = new MaterialDialog.Builder(getActivity())
                .setTitle(getString(R.string.dat_xe))
                .setMessage(getString(R.string.question_dat_xe_comfirm))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.xac_nhan), new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        fetchAddBookCar();
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

    private void fetchAddBookCar() {
        mainActivcity.showLoading();
        AddBookCarBody body = prepareBodyAddBookCar();
        API.INSTANCE.getService().addBookCar(body)
                .subscribeOn(Schedulers.io()) //(*)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<AddBookCarRespon>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(AddBookCarRespon addBookCarRespon) {
                        mainActivcity.hideLoading();
                        if (addBookCarRespon.getResultInfo().getStatus().equals(CommonVCC.RESULT_STATUS_OK)) {
                            Toasty.success(getActivity(), getString(R.string.success_add_book_car), Toasty.LENGTH_LONG).show();
                            mainActivcity.popBackStackFragment();
                            mainActivcity.navigateFragment(R.id.nav_slideshow, null);
                        } else {
                            Toasty.error(getActivity(), addBookCarRespon.getResultInfo().getMessage(), Toast.LENGTH_SHORT, true).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mainActivcity.hideLoading();
                        Toasty.error(getActivity(), getString(R.string.loi_ket_noi), Toast.LENGTH_SHORT, true).show();
                    }
                });
    }

    private AddBookCarBody prepareBodyAddBookCar() {
        UserLogin userLogin = CommonVCC.getUserLogin();

        BookCarDto bookCarDto = new BookCarDto();
        bookCarDto.setSysUserId(userLogin.getSysUserId() + "");
        bookCarDto.setLoginName(userLogin.getLoginName());
        bookCarDto.setFullName(userLogin.getFullName());
        bookCarDto.setEmail(userLogin.getEmail());
        bookCarDto.setPhoneNumber(userLogin.getPhoneNumber());
        bookCarDto.setSysGroupId(userLogin.getSysGroupId());
        bookCarDto.setSysGroupName(userLogin.getSysGroupName());
        bookCarDto.setDepartmentId(userLogin.getDepartmentId());
        bookCarDto.setDepartmentName(userLogin.getDepartmentName());
        LstBookCarDto lstBookCarDto = addBookCarViewModel.listCarType.get(addBookCarViewModel.posItemSpCarType);
        bookCarDto.setCarTypeId(lstBookCarDto.getCarTypeId());
        bookCarDto.setCarTypeName(lstBookCarDto.getCarTypeName());
        bookCarDto.setTypeBookCar(addBookCarViewModel.typeBookCar);
        bookCarDto.setStartTime(text_time_start.getText().toString());
        bookCarDto.setEndTime(text_time_end.getText().toString());
        bookCarDto.setFromAddress(text_pos_from.getText().toString());
        bookCarDto.setToAddress(text_pos_to.getText().toString());
        bookCarDto.setNumPersonTogether(listPeopleTogetherSelected.size());
        LstUserDto lstUserDto = listPeopleApproveSelected.get(0);
        bookCarDto.setManagerStaffId(lstUserDto.getSysUserId());
        bookCarDto.setManagerStaffName(lstUserDto.getFullName());
        bookCarDto.setManagerStaffEmail(lstUserDto.getEmail());
        if (addBookCarViewModel.typeBookCar.equals("4")) {
            bookCarDto.setCarId(addBookCarViewModel.carDieuChuyen.getCarId());
            bookCarDto.setLicenseCar(addBookCarViewModel.carDieuChuyen.getLicenseCar());
            bookCarDto.setDriverId(addBookCarViewModel.carDieuChuyen.getDriverId());
            bookCarDto.setDriverName(addBookCarViewModel.carDieuChuyen.getDriverName());
            bookCarDto.setDriverCode(addBookCarViewModel.carDieuChuyen.getDriverCode());
            bookCarDto.setPhoneNumberDriver(addBookCarViewModel.carDieuChuyen.getPhoneNumberDriver());
        }
        if (strTo.equals(strFrom)){
            bookCarDto.setInternalProvince(1);
        }else {
            bookCarDto.setInternalProvince(2);
        }
        bookCarDto.setContent(edit_text_content.getText().toString());
        bookCarDto.setGoodsWeight(Double.parseDouble(edit_text_goodsWeight.getText().toString()));

        SysUserRequest sysUserRequest = new SysUserRequest();
        sysUserRequest.setAuthenticationInfo(new AuthenticationInfo(userLogin.getLoginName(), userLogin.getPassword()));

        AddBookCarBody addBookCarBody = new AddBookCarBody(bookCarDto, listPeopleTogetherSelected, sysUserRequest);
        return addBookCarBody;
    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.layout_duyet_tuchoi_sua:
                mainActivcity.hideKeyBoard();
                if (verifyBookCar()) {
                    showDialog();
                }
                break;
            case R.id.btn_add_nguoi_di_cung:
                if (addBookCarViewModel.listTogether.isEmpty()) {
                    fetchGetListUser();
                } else {
                    bundle.putSerializable(SelectPeopleFragment.LIST_USER_ALL, addBookCarViewModel.listTogether);
                    bundle.putSerializable(SelectPeopleFragment.LIST_USER_DTO, listPeopleTogetherSelected);
                    mainActivcity.navigateFragment(R.id.nav_select_people, bundle);
                }
                break;
            case R.id.btn_add_nguoi_phe_duyet:
                if (addBookCarViewModel.listManager.isEmpty()) {
                    fetchGetListManager();
                } else {
                    bundle.putSerializable(SelectPeopleFragment.LIST_USER_ALL, addBookCarViewModel.listManager);
                    bundle.putSerializable(SelectPeopleFragment.LIST_USER_DTO, listPeopleApproveSelected);
                    bundle.putBoolean(SelectPeopleFragment.ONLY_ONE_PEOPLE_APPROVE, true);
                    mainActivcity.navigateFragment(R.id.nav_select_people, bundle);
                }
                break;
            case R.id.layout_chon_xe:
                addBookCarViewModel.bookCarDto = new com.vcc.trackcar.model.getListBookCar.LstBookCarDto();
                addBookCarViewModel.bookCarDto.setCarTypeId(addBookCarViewModel.listCarType.get(addBookCarViewModel.posItemSpCarType).getCarTypeId());
                addBookCarViewModel.bookCarDto.setCarTypeName(addBookCarViewModel.listCarType.get(addBookCarViewModel.posItemSpCarType).getCarTypeName());
                bundle.putSerializable(ListDriverCarFragment.EXTRA_BOOK_CAR_LIST_DRIVER, addBookCarViewModel.bookCarDto);
                bundle.putSerializable(ListDriverCarFragment.EXTRA_SELECTED_CAR_LIST_DRIVER, addBookCarViewModel.carDieuChuyen);
                mainActivcity.navigateFragment(R.id.nav_select_car_xe, bundle);
                break;
            case R.id.layout_chon_lai_xe:
                addBookCarViewModel.bookCarDto = new com.vcc.trackcar.model.getListBookCar.LstBookCarDto();
                addBookCarViewModel.bookCarDto.setCarTypeId(addBookCarViewModel.listCarType.get(addBookCarViewModel.posItemSpCarType).getCarTypeId());
                addBookCarViewModel.bookCarDto.setCarTypeName(addBookCarViewModel.listCarType.get(addBookCarViewModel.posItemSpCarType).getCarTypeName());
                bundle.putSerializable(ListDriverCarFragment.EXTRA_BOOK_CAR_LIST_DRIVER, addBookCarViewModel.bookCarDto);
                bundle.putSerializable(ListDriverCarFragment.EXTRA_SELECTED_CAR_LIST_DRIVER, addBookCarViewModel.carDieuChuyen);
                mainActivcity.navigateFragment(R.id.nav_select_car, bundle);
                break;
            case R.id.imv_location_pin:
                if (addBookCarViewModel.carDieuChuyen.getLicenseCar() == null || addBookCarViewModel.carDieuChuyen.getLicenseCar().equals("")){
                    Toasty.error(getActivity(), getString(R.string.please_choose_car_driver), Toast.LENGTH_SHORT).show();
                    break;
                }
                bundle.putSerializable(ListDriverCarFragment.EXTRA_SELECTED_CAR_LIST_DRIVER, addBookCarViewModel.carDieuChuyen);
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
        if (addBookCarViewModel.typeBookCar.equals("4") && (addBookCarViewModel.carDieuChuyen.getLicenseCar() == null || addBookCarViewModel.carDieuChuyen.getLicenseCar().equals(""))) {
            Toasty.error(getActivity(), getString(R.string.please_choose_car_driver), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (addBookCarViewModel.typeBookCar.equals("4") && (addBookCarViewModel.carDieuChuyen.getDriverName() == null || addBookCarViewModel.carDieuChuyen.getDriverName().equals(""))) {
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