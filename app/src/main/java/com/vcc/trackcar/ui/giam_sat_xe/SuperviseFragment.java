package com.vcc.trackcar.ui.giam_sat_xe;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.arsy.maps_library.MapRipple;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.kienht.bottomsheetbehavior.BottomSheetBehavior;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.vcc.trackcar.MainActivity;
import com.vcc.trackcar.R;
import com.vcc.trackcar.model.CatVehicleDTO;
import com.vcc.trackcar.model.CatVehicleReponseDTO;
import com.vcc.trackcar.model.addBookCar.BookCarDto;
import com.vcc.trackcar.model.addBookCar.SysUserRequest;
import com.vcc.trackcar.model.api_matrix.Element;
import com.vcc.trackcar.model.api_matrix.MatrixRespon;
import com.vcc.trackcar.model.auth.AuthenticationInfo;
import com.vcc.trackcar.model.auth.UserLogin;
import com.vcc.trackcar.model.getHistoryCar.BookCarHistory;
import com.vcc.trackcar.model.getHistoryCar.CarInfoHistory;
import com.vcc.trackcar.model.getHistoryCar.GetHistoryCarBody;
import com.vcc.trackcar.model.getHistoryCar.GetHistoryCarRespon;
import com.vcc.trackcar.model.getHistoryCar.MakerCar;
import com.vcc.trackcar.model.getHistoryCar.VehicleMonitoringRequest;
import com.vcc.trackcar.model.getHistoryDetailCar.GetHistoryDetailCarBody;
import com.vcc.trackcar.model.getHistoryDetailCar.GetHistoryDetailCarRespon;
import com.vcc.trackcar.model.getListCar.GetListCarBody;
import com.vcc.trackcar.model.getListCar.GetListCarBodyDto;
import com.vcc.trackcar.model.getListCar.GetListCarRespon;
import com.vcc.trackcar.model.getListDriverCar.LstBookCarDto;
import com.vcc.trackcar.model.getListManager.GetListManagerBody;
import com.vcc.trackcar.model.request_body.BranchRequestBody;
import com.vcc.trackcar.model.request_body.VehicleMonitoringRequestBody;
import com.vcc.trackcar.model.response.BranchReponseDTO;
import com.vcc.trackcar.remote.API;
import com.vcc.trackcar.remote.APIGG;
import com.vcc.trackcar.ui.base.CommonVCC;
import com.vcc.trackcar.ui.direction.DirectionFinder;
import com.vcc.trackcar.ui.direction.DirectionFinderListener;
import com.vcc.trackcar.ui.direction.Route;
import com.vcc.trackcar.ui.home.HomeViewModel;
import com.vcc.trackcar.ui.home.adapter.AutoCarAdapter;
import com.vcc.trackcar.ui.home.adapter.BookCarHistoryAdapter;
import com.vcc.trackcar.ui.home.adapter.CatVehicleAdapter;
import com.vcc.trackcar.ui.home.adapter.CustomListViewDialog;
import com.vcc.trackcar.ui.home.adapter.TypeCarTruckAdapter;
import com.vcc.trackcar.ui.home.custom_infowindow_maps.CustomInfoWindowGoogleMap;
import com.vcc.trackcar.ui.home.custom_infowindow_maps.InfoWindowData;

import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import es.dmoral.toasty.Toasty;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SuperviseFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener, BookCarHistoryAdapter.RecyclerViewItemClickListener, DirectionFinderListener {

    private static final int COLOR_BLACK_ARGB = 0xffd58431;

    private HomeViewModel homeViewModel;

    private MainActivity mainActivcity;

    private AutoCompleteTextView text_Unit;
    private AutoCompleteTextView txt_car_license;
    private AutoCompleteTextView txt_car_status;
    private ImageView image_clear_search;
    private ImageView img_clear_search_car_license;
    private ImageView img_clear_search_car_status;

    private LinearLayout layout_location_from;
    private LinearLayout layout_location_to;
    private Button btn_tim_kiem_xe;

    private BottomSheetBehavior bottomSheetBehavior;

    private TextView marker_title;
    private GoogleMap mMap;

    private CustomListViewDialog customDialog;
    private BookCarHistoryAdapter bookCarHistoryAdapter;
    private ImageView imv_list_book_car_history;

    private final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 100;
    private LocationTracker locationTrackObj;
    private LatLng latLng = new LatLng(21.024673, 105.789692);
    private static MapRipple mapRipple;

    private Polyline polylineEstimate;
    private static final int PATTERN_GAP_LENGTH_PX = 10;
    private static final Gap GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    private static final Dot DOT = new Dot();
    private static final List<PatternItem> PATTERN_DOTTED = Arrays.asList(DOT, GAP);
//    private SuperviseViewModel viewModel = new SuperviseViewModel();
    private List<BookCarDto> listUnit  = new ArrayList<>();
    private List<CatVehicleDTO> listCar = new ArrayList();
    private TypeCarTruckAdapter typeCarTruckAdapter;
    private CatVehicleAdapter catVehicleAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mainActivcity = (MainActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationTrackObj = new LocationTracker(mainActivcity);
        if (!locationTrackObj.canGetLocation()) {
            locationTrackObj.showSettingsAlert();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkLocationPermission();
            }
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_supervise, container, false);
        initBottomSheet(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        bookCarHistoryAdapter = new BookCarHistoryAdapter(this);
        customDialog = new CustomListViewDialog(getActivity(), bookCarHistoryAdapter);
        customDialog.setCanceledOnTouchOutside(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initBottomSheet(View root) {
        imv_list_book_car_history = root.findViewById(R.id.imv_list_book_car_history);
        imv_list_book_car_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.show();
            }
        });



        btn_tim_kiem_xe = root.findViewById(R.id.btn_tim_kiem_xe);
        btn_tim_kiem_xe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivcity.hideKeyBoard();
                if (!isHasRoleCode()) {
                    Toasty.error(getContext(), getString(R.string.no_permission_user), Toasty.LENGTH_SHORT).show();
                } else if (homeViewModel.carDtoSelected == null && homeViewModel.catVehicleDTO == null) {
                    Toasty.warning(getContext(), getString(R.string.please_chon_xe), Toasty.LENGTH_SHORT).show();
                }
                else {
                    updateInfoCar(homeViewModel.catVehicleDTO.getCarId(), homeViewModel.catVehicleDTO.getLicenseCar(), homeViewModel.catVehicleDTO.getSysGroupId(),"1");
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        text_Unit = root.findViewById(R.id.text_Unit);
        typeCarTruckAdapter = new TypeCarTruckAdapter(root.getContext(), R.layout.item_car_search_home, listUnit, carDto -> {
            text_Unit.setText(carDto.getSysGroupName());
            text_Unit.dismissDropDown();
            mainActivcity.hideKeyBoard();
//            getListAutoCar(carDto.getSysGroupId());
            getListAutoCar(400000);
        });
        text_Unit.setAdapter(typeCarTruckAdapter);
        text_Unit.setThreshold(0);
        text_Unit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                text_Unit.showDropDown();
                return false;
            }
        });
        text_Unit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.length() != 0) {
                    image_clear_search.setVisibility(View.VISIBLE);
                    text_Unit.showDropDown();
                } else {
                    image_clear_search.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        image_clear_search = root.findViewById(R.id.image_clear_search);
        image_clear_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_Unit.setText("");
                image_clear_search.setVisibility(View.GONE);
            }
        });


        txt_car_license = root.findViewById(R.id.txt_car_license);
        txt_car_status = root.findViewById(R.id.txt_car_status);
        img_clear_search_car_license = root.findViewById(R.id.img_clear_search_car_license);
        img_clear_search_car_status = root.findViewById(R.id.img_clear_search_car_status);
        initAutoCarAdapter(root);
        initCarStatusAdapter(root);

        // =============
        marker_title = root.findViewById(R.id.marker_title);

        LinearLayout bottom_sheet = root.findViewById(R.id.bottom_sheet);
        ImageView expand_icon = root.findViewById(R.id.expand_icon);
        NestedScrollView description_scrollview = root.findViewById(R.id.description_scrollview);
        View sheet_header_shadow = root.findViewById(R.id.sheet_header_shadow);
        RelativeLayout clickable = root.findViewById(R.id.clickable);

        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setSkipCollapsed(true);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NotNull View view, int newState) {
                float rotation;

                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        rotation = 0f;
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                    case BottomSheetBehavior.STATE_HIDDEN:
                        rotation = 180f;
                        mainActivcity.hideKeyBoard();
                        break;
                    default:
                        return;
                }

                expand_icon.animate().rotationX(rotation).start();
            }

            @Override
            public void onSlide(@NotNull View view, float v) {

            }
        });

        clickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            description_scrollview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                    sheet_header_shadow.setActivated(view.canScrollVertically(-1));
                }
            });
        }

    }

    private void initAutoCarAdapter(View root){

        catVehicleAdapter = new CatVehicleAdapter(root.getContext(), R.layout.item_car_search_home, listCar, carDto -> {
            txt_car_license.setText(carDto.getLicenseCar());
            txt_car_license.dismissDropDown();
            mainActivcity.hideKeyBoard();
             homeViewModel.catVehicleDTO  = carDto;
        });
        txt_car_license.setAdapter(catVehicleAdapter);
        txt_car_license.setThreshold(0);
        txt_car_license.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                txt_car_license.showDropDown();
                return false;
            }
        });
        txt_car_license.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.length() != 0) {
                    img_clear_search_car_license.setVisibility(View.VISIBLE);
                    txt_car_license.showDropDown();
                } else {
                    img_clear_search_car_license.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        img_clear_search_car_license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_car_license.setText("");
                img_clear_search_car_license.setVisibility(View.GONE);
            }
        });
    }

    private void initCarStatusAdapter(View root){

//        catVehicleAdapter = new CatVehicleAdapter(root.getContext(), R.layout.item_car_search_home, listCar, carDto -> {
//            txt_car_license.setText(carDto.getLicenseCar());
//            txt_car_license.dismissDropDown();
//            mainActivcity.hideKeyBoard();
//        });
//        txt_car_license.setAdapter(adapterAutoCar);
//        txt_car_license.setThreshold(0);
        txt_car_status.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                txt_car_status.showDropDown();
                return false;
            }
        });
        txt_car_status.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.length() != 0) {
                    img_clear_search_car_status.setVisibility(View.VISIBLE);
                    txt_car_status.showDropDown();
                } else {
                    img_clear_search_car_status.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        img_clear_search_car_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_car_status.setText("");
                img_clear_search_car_status.setVisibility(View.GONE);
            }
        });
    }

    private boolean isHasRoleCode() {
        String[] listRoleCode = CommonVCC.getUserLogin().getRoleCode().split(";");
        for (String roleCode : listRoleCode) {
            if (roleCode.equals("THUTRUONGXE") || roleCode.equals("DOITRUONGXE") ||
                    roleCode.equals("BANXETCT")) return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initializeMap(mMap);
                } else {
                }
                return;
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                initializeMap(mMap);
            }
        } else {
            initializeMap(mMap);
        }
    }

    private void initializeMap(GoogleMap mMap) {
        if (mMap != null) {
            mMap.setMyLocationEnabled(true);
            Location location = mMap.getMyLocation();
            if (location == null)
                location = locationTrackObj.getLocation();
            try {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new
                        LatLng(location.getLatitude(),
                        location.getLongitude()), 14));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (location != null)
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
            else {
                latLng = new LatLng(0.0, 0.0);
            }
            mapRipple = new MapRipple(mMap, latLng, mainActivcity.getApplicationContext());
            mapRipple.withFillColor(Color.parseColor("#FFA3D2E4"));
            mapRipple.withDistance(3000);      // 2000 metres radius
            mapRipple.withRippleDuration(12000);    //12000ms
            mapRipple.withTransparency(0.5f);
            mapRipple.startRippleMapAnimation();

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }

            return false;
        } else {
            return true;
        }
    }

    private void initData() {
        if (listUnit == null || listUnit.size() == 0) {
            getListTypeCarTruck();
        }
    }

    private void fetchDirections(String origin, String destination) {
        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void getListTypeCarTruck(){
        BranchRequestBody body = new BranchRequestBody();
        body.setSysUserRequest(CommonVCC.getSysUserRequest());
        body.setBookCarDto(new BookCarDto());
        API.INSTANCE.getService().getBranch(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<BranchReponseDTO>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(BranchReponseDTO response) {
                if (response.getResultInfo().getStatus().equals(CommonVCC.RESULT_STATUS_OK)){
                    if (response.getLstBookCarDto() != null) {
                        if (listUnit != null && listUnit.size() > 0) {
                            listUnit.clear();
                        }
                        listUnit.addAll(response.getLstBookCarDto());
                        typeCarTruckAdapter.setList(listUnit);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    private void getListAutoCar(Integer sysGroupId) {
        GetListManagerBody body = new GetListManagerBody();
        body.setSysGroupId(sysGroupId);
        API.INSTANCE.getService().searchCatVehicle(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<CatVehicleReponseDTO>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(CatVehicleReponseDTO respon) {
                if (respon.getResultInfo().getStatus().equals(CommonVCC.RESULT_STATUS_OK)) {
                    if (respon.getCatVehicleDTO() != null) {
                        if (listCar != null && listCar.size() > 0) {
                            listCar.clear();
                        }
                        listCar.addAll(respon.getCatVehicleDTO());
                        catVehicleAdapter.setList(listCar);
                    }
                } else {
                    Toasty.error(getActivity(), respon.getResultInfo().getMessage(), Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                Toasty.error(getActivity(), getString(R.string.loi_ket_noi), Toast.LENGTH_LONG, true).show();
            }
        });
    }

    private void setInfoPos(MakerCar makerFirst, MakerCar makerEnd) {

        InfoWindowData info = new InfoWindowData();
        info.setMaLenh(makerEnd.getCode() == null ? "" : makerEnd.getCode());
        info.setNguoiLai(makerEnd.getDriverName() == null ? "" : makerEnd.getDriverName());
        info.setNguoiTao(makerEnd.getFullName() == null ? "" : makerEnd.getFullName());

        info.setThoiGianDuKienConLai("Chưa biết");
        info.setContent(makerEnd.getContent());

        CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(getActivity());
        mMap.setInfoWindowAdapter(customInfoWindow);

        homeViewModel.makerFirst = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(makerFirst.getLatitude()), Double.parseDouble(makerFirst.getLongtitude())))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_maker))
                .title(getString(R.string.diem_bat_dau)));

        homeViewModel.makerEnd = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(makerEnd.getLatitude()), Double.parseDouble(makerEnd.getLongtitude())))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car_connection)));
        homeViewModel.makerEnd.setTag(info);
        homeViewModel.makerEnd.showInfoWindow();
    }


    @Override
    public void onPolylineClick(Polyline polyline) {
    }

    private void updateInfoCar(Integer carId, String licenseCar, long sysGroupId,String carStatus) {
        mainActivcity.showLoading();
        VehicleMonitoringRequestBody body = new VehicleMonitoringRequestBody();

        VehicleMonitoringRequest vehicleMonitoringRequest = new VehicleMonitoringRequest();

        vehicleMonitoringRequest.setCarId(carId);
        vehicleMonitoringRequest.setLicenseCar(licenseCar);
        vehicleMonitoringRequest.setSysGroupId(sysGroupId);
        vehicleMonitoringRequest.setListStatus(carStatus);

        body.setBookCarDto(vehicleMonitoringRequest);
        body.setSysUserRequest(CommonVCC.getSysUserRequest());
        API.INSTANCE.getService().getVehicleMonitoring(body)
                .subscribeOn(Schedulers.io()) //(*)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetHistoryCarRespon>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GetHistoryCarRespon respon) {
                        mainActivcity.hideLoading();
                        if (respon.getResultInfo().getStatus().equals(CommonVCC.RESULT_STATUS_OK)) {
                            if (respon.getLstBookCarDto() == null || respon.getLstBookCarDto().size() == 0) {
                                Toasty.info(getActivity(), getString(R.string.no_book_history_car), Toast.LENGTH_SHORT, true).show();
                            } else {
                                homeViewModel.listBookCarHistory.clear();
                                homeViewModel.listBookCarHistory.addAll(respon.getLstBookCarDto());
                                bookCarHistoryAdapter.swapData(homeViewModel.listBookCarHistory);
                                customDialog.show();
                                customDialog.setTitleDialog(licenseCar);
                                imv_list_book_car_history.setVisibility(View.VISIBLE);
                                marker_title.setText(getString(R.string.thong_tin_xe, licenseCar));
                            }
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

    private void drawPolyline(List<MakerCar> makerCarList) {

        PolylineOptions polylineOptions = new PolylineOptions().clickable(true);

        for (MakerCar makerCar : makerCarList) {
            polylineOptions.add(new LatLng(Double.parseDouble(makerCar.getLatitude()), Double.parseDouble(makerCar.getLongtitude())));
        }

        if (homeViewModel.polyCar != null) {
            homeViewModel.polyCar.remove();
        }
        if (homeViewModel.makerFirst != null) {
            homeViewModel.makerFirst.remove();
            homeViewModel.makerEnd.remove();
        }

        homeViewModel.polyCar = mMap.addPolyline(polylineOptions);
        homeViewModel.polyCar.setColor(COLOR_BLACK_ARGB);
//        stylePolyline(homeViewModel.polyCar);
        setInfoPos(makerCarList.get(0), makerCarList.get(makerCarList.size() - 1));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(makerCarList.get(makerCarList.size() - 1).getLatitude()), Double.parseDouble(makerCarList.get(makerCarList.size() - 1).getLongtitude())), 12));

    }


    @Override
    public void onStop() {
        super.onStop();

        if (locationTrackObj != null) locationTrackObj.stopUsingGPS();
        try {
            if (mapRipple.isAnimationRunning()) {
                mapRipple.stopRippleMapAnimation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void clickOnItemBookCarHistory(@NotNull BookCarHistory bookCarHistory) {
        mainActivcity.showLoading();
        GetHistoryDetailCarBody body = new GetHistoryDetailCarBody();
        body.setBookCarDto(bookCarHistory);
        body.setSysUserRequest(CommonVCC.getSysUserRequest());
        API.INSTANCE.getService().getHistoryDetailCar(body)
                .subscribeOn(Schedulers.io()) //(*)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetHistoryDetailCarRespon>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GetHistoryDetailCarRespon respon) {
                        mainActivcity.hideLoading();
                        if (customDialog.isShowing()) customDialog.dismiss();
                        if (respon.getLstBookCarDto() == null || respon.getLstBookCarDto().size() == 0) {
                            Toasty.info(getActivity(), getString(R.string.no_history_car), Toast.LENGTH_SHORT, true).show();
                        } else {
                            drawPolyline(respon.getLstBookCarDto());
                            fetchDirections(bookCarHistory.getFromAddress(), bookCarHistory.getToAddress());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mainActivcity.hideLoading();
                        Toasty.error(getActivity(), getString(R.string.loi_ket_noi), Toast.LENGTH_SHORT, true).show();
                    }
                });
    }

    @Override
    public void onDirectionFinderStart() {
        mainActivcity.showLoading();
    }

    public static PolylineOptions getDefaultPolyLines(List<LatLng> points) {
        PolylineOptions polylineOptions = new PolylineOptions().color(Color.GREEN);
        for (LatLng point : points) polylineOptions.add(point);
        polylineOptions.pattern(PATTERN_DOTTED);
        return polylineOptions;
    }


    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        mainActivcity.hideLoading();
        if (!routes.isEmpty() && polylineEstimate != null) polylineEstimate.remove();
        if (homeViewModel.makerTarget != null) homeViewModel.makerTarget.remove();
        try {
            for (Route route : routes) {
                PolylineOptions polylineOptions = getDefaultPolyLines(route.points);
                polylineEstimate = mMap.addPolyline(polylineOptions);
            }
            homeViewModel.makerTarget = mMap.addMarker(new MarkerOptions()
                    .position(routes.get(0).endLocation)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_goal))
                    .title(getString(R.string.diem_ket_thuc)));
//            mMap.animateCamera(buildCameraUpdate(routes.get(0).endLocation), 10, null);
        } catch (Exception e) {
            Toasty.info(getActivity(), getString(R.string.error_occurred_on_finding_the_directions), Toast.LENGTH_SHORT).show();
        }
    }

    private class LocationTracker implements LocationListener {

        private final Context mContext;

        // flag for GPS status
        private boolean isGPSEnabled = false;

        // flag for network status
        private boolean isNetworkEnabled = false;

        // flag for GPS status
        private boolean canGetLocation = false;

        private Location location; // location
        private double latitude; // latitude
        private double longitude; // longitude

        // The minimum distance to change Updates in meters
        private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 1 meters

        // The minimum time between updates in milliseconds
        private static final long MIN_TIME_BW_UPDATES = 1000; // 1 sec

        private final String TAG = "LocationTracker";
        // Declaring a Location Manager
        protected LocationManager locationManager;

        public LocationTracker(Context context) {
            this.mContext = context;
            getLocation();
        }

        @SuppressLint("MissingPermission")
        public Location getLocation() {
            try {
                locationManager = (LocationManager) mContext
                        .getSystemService(Context.LOCATION_SERVICE);

                // getting GPS status
                isGPSEnabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);

                // getting network status
                isNetworkEnabled = locationManager
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!isGPSEnabled && !isNetworkEnabled) {
                    // no network provider is enabled
                    this.canGetLocation = false;
                } else {
                    this.canGetLocation = true;
                    // First get location from Network Provider
                    if (isNetworkEnabled) {
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("Network", "Network");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                    // if GPS Enabled get lat/long using GPS Services
                    if (isGPSEnabled) {
                        if (location == null) {
                            locationManager.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER,
                                    MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                            Log.d("GPS Enabled", "GPS Enabled");
                            if (locationManager != null) {
                                location = locationManager
                                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }

            return location;
        }

        /**
         * Stop using GPS listener
         * Calling this function will stop using GPS in your app
         */
        public void stopUsingGPS() {
            if (locationManager != null) {
                locationManager.removeUpdates(LocationTracker.this);
            }
        }

        /**
         * Function to get latitude
         */
        public double getLatitude() {
            if (location != null) {
                latitude = location.getLatitude();
            }

            // return latitude
            return latitude;
        }

        /**
         * Function to get longitude
         */
        public double getLongitude() {
            if (location != null) {
                longitude = location.getLongitude();
            }

            // return longitude
            return longitude;
        }

        /**
         * Function to check GPS/wifi enabled
         *
         * @return boolean
         */
        public boolean canGetLocation() {
            return this.canGetLocation;
        }

        /**
         * Function to show settings alert dialog
         * On pressing Settings button will lauch Settings Options
         */
        public void showSettingsAlert() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

            // Setting Dialog Title
            alertDialog.setTitle(getString(R.string.gps_settings));

            // Setting Dialog Message
            alertDialog.setMessage(getString(R.string.alter_enable_gps));
            alertDialog.setCancelable(false);

            // On pressing Settings button
            alertDialog.setPositiveButton(getString(R.string.settings), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    mContext.startActivity(intent);
                }
            });


            // Showing Alert Message
            alertDialog.show();
        }

        Random rand = new Random();

        @Override
        public void onLocationChanged(Location location) {
            //            mapRipple.withNumberOfRipples(3);
            this.location = location;
//            Toast.makeText(context, "  " + location.getLatitude() + ",  " + location.getLongitude(), Toast.LENGTH_SHORT).show();
            if (mapRipple != null && mapRipple.isAnimationRunning())
                mapRipple.withLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
//            if (mapRadar.isAnimationRunning())
//                mapRadar.withLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
            location = getLocation();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }
}