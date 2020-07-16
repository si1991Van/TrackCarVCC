package com.vcc.trackcar.ui.home;

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
import android.text.TextUtils;
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
import com.vcc.trackcar.model.getHistoryDetailCar.GetHistoryDetailCarBody;
import com.vcc.trackcar.model.getHistoryDetailCar.GetHistoryDetailCarRespon;
import com.vcc.trackcar.model.getListCar.GetListCarBody;
import com.vcc.trackcar.model.getListCar.GetListCarBodyDto;
import com.vcc.trackcar.model.getListCar.GetListCarRespon;
import com.vcc.trackcar.model.getListDriverCar.LstBookCarDto;
import com.vcc.trackcar.model.getListManager.GetListManagerBody;
import com.vcc.trackcar.model.request_body.BranchRequestBody;
import com.vcc.trackcar.model.response.BranchReponseDTO;
import com.vcc.trackcar.model.response.TypeCarTruckReponseDTO;
import com.vcc.trackcar.remote.API;
import com.vcc.trackcar.remote.APIGG;
import com.vcc.trackcar.ui.base.CommonVCC;
import com.vcc.trackcar.ui.direction.DirectionFinder;
import com.vcc.trackcar.ui.direction.DirectionFinderListener;
import com.vcc.trackcar.ui.direction.Route;
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

public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener, AutoCarAdapter.OnItemCarClick, BookCarHistoryAdapter.RecyclerViewItemClickListener, DirectionFinderListener {

    private static final int COLOR_BLACK_ARGB = 0xffd58431;

    private static final int POLYLINE_STROKE_WIDTH_PX = 12;

    private HomeViewModel homeViewModel;

    private MainActivity mainActivcity;

    private AutoCompleteTextView text_auto_car;
    private ImageView image_clear_search;
    private AutoCarAdapter adapterAutoCar;
    private CatVehicleAdapter catVehicleAdapter;
    private TypeCarTruckAdapter typeCarTruckAdapter;

    private RelativeLayout layout_location_from;
    private RelativeLayout layout_location_to;
    SingleDateAndTimePickerDialog dialogDataTimeStart;
    SingleDateAndTimePickerDialog dialogDataTimeEnd;
    private TextView text_pos_to;
    private TextView text_pos_from;
    private Button btn_tim_kiem_xe;

    private BottomSheetBehavior bottomSheetBehavior;

    private TextView marker_title;
    private GoogleMap mMap;
    private RelativeLayout rlDepartment;
    private AutoCompleteTextView text_department;
    private ImageView image_clear_search_department;

    private CustomListViewDialog customDialog;
    private BookCarHistoryAdapter bookCarHistoryAdapter;
    private ImageView imv_list_book_car_history;

    private final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 100;
    private LocationTracker locationTrackObj;
    private LatLng latLng = new LatLng(21.024673, 105.789692);
    private static MapRipple mapRipple;

    private Polyline polylineEstimate;
    private static final int ZOOM_LEVEL = 18;
    private static final int TILT_LEVEL = 25;
    private static final int PATTERN_GAP_LENGTH_PX = 10;
    private static final Gap GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    private static final Dot DOT = new Dot();
    private static final List<PatternItem> PATTERN_DOTTED = Arrays.asList(DOT, GAP);
    private String[] listRoleCode = CommonVCC.getUserLogin().getRoleCode().split(";");
    private List<BookCarDto> listTypeCarTruck = new ArrayList<>();

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
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkLocationPermission();
            }
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
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

        layout_location_from = root.findViewById(R.id.layout_location_from);
        layout_location_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDataTimeStart.display();
            }
        });
        dialogDataTimeStart = initDateTimePickerDialogStart(root);
        text_pos_from = root.findViewById(R.id.text_pos_from);
        layout_location_to = root.findViewById(R.id.layout_location_to);
        layout_location_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDataTimeEnd.display();
            }
        });
        dialogDataTimeEnd = initDateTimePickerDialogEnd(root);
        text_pos_to = root.findViewById(R.id.text_pos_to);

        btn_tim_kiem_xe = root.findViewById(R.id.btn_tim_kiem_xe);
        btn_tim_kiem_xe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivcity.hideKeyBoard();
                if (!isHasRoleCodeHanhTrinh()) {
                    Toasty.error(getContext(), getString(R.string.no_permission_user), Toasty.LENGTH_SHORT).show();
                }else if (isHasRoleCodeBANXETCT() && TextUtils.isEmpty(text_auto_car.getText().toString())){
                    Toasty.warning(getContext(), "Vui long chọn đơn vị ", Toasty.LENGTH_SHORT).show();
                }else if (homeViewModel.carDtoSelected == null && homeViewModel.catVehicleDTO == null) {
                    Toasty.warning(getContext(), getString(R.string.please_chon_xe), Toasty.LENGTH_SHORT).show();
                } else if (text_pos_from.getText().equals("")) {
                    Toasty.warning(getContext(), getString(R.string.please_chon_thoi_gian_tu), Toasty.LENGTH_SHORT).show();
                } else if (text_pos_to.getText().equals("")) {
                    Toasty.warning(getContext(), getString(R.string.please_chon_thoi_gian_den), Toasty.LENGTH_SHORT).show();
                } else {
                    if (isHasRoleCodeBANXETCT()){
                        updateInfoCar(homeViewModel.catVehicleDTO.getCarId(), homeViewModel.catVehicleDTO.getLicenseCar());
                    }else {
                        updateInfoCar(homeViewModel.carDtoSelected.getCarId(), homeViewModel.carDtoSelected.getLicenseCar());
                    }
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        text_auto_car = root.findViewById(R.id.text_auto_car);
        if (!isHasRoleCodeBANXETCT()){
            adapterAutoCar = new AutoCarAdapter(root.getContext(), R.layout.item_car_search_home, mainActivcity.listCar, this);
            text_auto_car.setAdapter(adapterAutoCar);
            text_auto_car.setThreshold(0);
        }else {
            catVehicleAdapter = new CatVehicleAdapter(root.getContext(), R.layout.item_car_search_home, mainActivcity.listCatVihicle, carDto -> {
                text_auto_car.setText(carDto.getLicenseCar());
                text_auto_car.dismissDropDown();
                mainActivcity.hideKeyBoard();
                homeViewModel.catVehicleDTO = carDto;
            });
            text_auto_car.setAdapter(catVehicleAdapter);
            text_auto_car.setThreshold(0);
        }

        text_auto_car.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                text_auto_car.showDropDown();
                return false;
            }
        });
        text_auto_car.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.length() != 0) {
                    image_clear_search.setVisibility(View.VISIBLE);
                    text_auto_car.showDropDown();
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
                text_auto_car.setText("");
                image_clear_search.setVisibility(View.GONE);
            }
        });
        rlDepartment = root.findViewById(R.id.rlDepartment);
        text_department = root.findViewById(R.id.text_department);
        image_clear_search_department = root.findViewById(R.id.image_clear_search_department);
        rlDepartment.setVisibility(isHasRoleCodeBANXETCT() ? View.VISIBLE : View.GONE);
        typeCarTruckAdapter = new TypeCarTruckAdapter(root.getContext(), R.layout.item_car_search_home,
                listTypeCarTruck, carDto -> {
            text_department.setText(carDto.getSysGroupName());
            text_department.dismissDropDown();
            mainActivcity.hideKeyBoard();
            getGetListCatVihicle(carDto.getSysGroupId());
        });
        text_department.setAdapter(typeCarTruckAdapter);
        text_department.setThreshold(0);
        text_department.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                text_department.showDropDown();
                return false;
            }
        });
        text_department.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.length() != 0) {
                    image_clear_search_department.setVisibility(View.VISIBLE);
                    text_department.showDropDown();
                } else {
                    image_clear_search_department.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        image_clear_search_department.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_department.setText("");
                image_clear_search_department.setVisibility(View.GONE);
            }
        });

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

    private boolean isHasRoleCodeHanhTrinh() {
//        String[] listRoleCode = CommonVCC.getUserLogin().getRoleCode().split(";");
        for (String roleCode : listRoleCode) {
            if (roleCode.equals(MainActivity.HANHTRINH)) return true;
        }
        return false;
    }

    private boolean isHasRoleCodeBANXETCT() {
        for (String roleCode : listRoleCode) {
            if (roleCode.equals("BANXETCT")) return true;
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

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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

//            mapRipple.withNumberOfRipples(3);
            mapRipple.withFillColor(Color.parseColor("#FFA3D2E4"));
//            mapRipple.withStrokeColor(Color.BLACK);
//            mapRipple.withStrokewidth(0);      // 10dp
            mapRipple.withDistance(3000);      // 2000 metres radius
            mapRipple.withRippleDuration(12000);    //12000ms
            mapRipple.withTransparency(0.5f);
            mapRipple.startRippleMapAnimation();

//            mapRadar = new MapRadar(mMap, latLng, context);
//            //mapRadar.withClockWiseAnticlockwise(true);
//            mapRadar.withDistance(2000);
//            mapRadar.withClockwiseAnticlockwiseDuration(2);
//            //mapRadar.withOuterCircleFillColor(Color.parseColor("#12000000"));
//            mapRadar.withOuterCircleStrokeColor(Color.parseColor("#fccd29"));
//            //mapRadar.withRadarColors(Color.parseColor("#00000000"), Color.parseColor("#ff000000"));  //starts from transparent to fuly black
//            mapRadar.withRadarColors(Color.parseColor("#00fccd29"), Color.parseColor("#fffccd29"));  //starts from transparent to fuly black
//            //mapRadar.withOuterCircleStrokewidth(7);
//            //mapRadar.withRadarSpeed(5);
//            mapRadar.withOuterCircleTransparency(0.5f);
//            mapRadar.withRadarTransparency(0.5f);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                requestPermissions(
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
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
        if (isHasRoleCodeBANXETCT()){
            if (listTypeCarTruck == null || listTypeCarTruck.size() == 0){
                getListTypeCarTruck();
            }
        }else {
            if (mainActivcity.listCar.size() == 0) {
//            fetchGetListDriverCar();
                getchGetListCar();
            }
        }
        text_pos_from.setText(getNgayDauCuoiThang(1));
        text_pos_to.setText(getNgayDauCuoiThang(2));
    }

    private void fetchDirections(String origin, String destination) {
        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private String getNgayDauCuoiThang(int type) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int endDay = cal.getActualMaximum(Calendar.DATE);

        String fixMonth = "";
        if (month / 10 == 0) fixMonth = "0" + month;
        else fixMonth = month + "";

        if (type == 2) return endDay + "/" + fixMonth + "/" + year + " 23:59";
        else return "01/" + fixMonth + "/" + year + " 00:00";
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
                        if (listTypeCarTruck != null && listTypeCarTruck.size() > 0) {
                            listTypeCarTruck.clear();
                        }
                        listTypeCarTruck.addAll(response.getLstBookCarDto());
                        typeCarTruckAdapter.setList(listTypeCarTruck);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    private void getGetListCatVihicle(Integer sysGroupId){
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
                        if (mainActivcity.listCatVihicle != null && mainActivcity.listCatVihicle.size() > 0) {
                            mainActivcity.listCatVihicle.clear();
                        }
                        mainActivcity.listCatVihicle.addAll(respon.getCatVehicleDTO());
                        catVehicleAdapter.setList(mainActivcity.listCatVihicle);
                    }
                } else {
                    Toasty.error(getActivity(), respon.getResultInfo().getMessage(), Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                showErrorDialog(getString(R.string.loi_ket_noi), getString(R.string.please_check_connect_again));
            }
        });
    }

    private void getchGetListCar() {
        mainActivcity.showLoading();

        GetListCarBody body = new GetListCarBody();
        GetListCarBodyDto getListCarBodyDto = new GetListCarBodyDto();
        getListCarBodyDto.setSysGroupId(CommonVCC.getUserLogin().getSysGroupId());
        body.setGetListCarBodyDto(getListCarBodyDto);
        API.INSTANCE.getService().getListCar(body)
                .subscribeOn(Schedulers.io()) //(*)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetListCarRespon>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GetListCarRespon respon) {
                        mainActivcity.hideLoading();
                        if (respon.getResultInfo().getStatus().equals(CommonVCC.RESULT_STATUS_OK)) {
                            mainActivcity.listCar.clear();
                            mainActivcity.listCar.addAll(respon.getLstBookCarDto());
                            adapterAutoCar.notifyDataSetChanged();
                        } else {
                            Toasty.error(getActivity(), respon.getResultInfo().getMessage(), Toast.LENGTH_SHORT, true).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mainActivcity.hideLoading();
//                        Toasty.error(getActivity(), getString(R.string.loi_ket_noi), Toast.LENGTH_SHORT, true).show();
                        showErrorDialog(getString(R.string.loi_ket_noi), getString(R.string.please_check_connect_again));
                    }
                });
    }

    public void showErrorDialog(String title, String content) {
        MaterialDialog mDialog = new MaterialDialog.Builder(mainActivcity)
                .setTitle(title)
                .setMessage(content)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.thu_lai), new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        getchGetListCar();
                    }
                })
                .setNegativeButton(getString(R.string.dong_app), new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        mainActivcity.finish();
                    }
                })
                .build();

        mDialog.show();
    }

    private void setInfoPos(MakerCar makerFirst, MakerCar makerEnd) {

        InfoWindowData info = new InfoWindowData();
        info.setMaLenh(makerEnd.getCode() == null ? "" : makerEnd.getCode());
        info.setNguoiLai(makerEnd.getDriverName() == null ? "" : makerEnd.getDriverName());
        info.setNguoiTao(makerEnd.getFullName() == null ? "" : makerEnd.getFullName());
        info.setThoiGianDi(tinhThoiGianDaDi(makerFirst.getUtcTime(), makerEnd.getUtcTime()));
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

    private String tinhThoiGianDaDi(String dateStart, String dateStop) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date d1 = null;
        Date d2 = null;
        long diffSeconds = 0, diffMinutes = 0, diffHours = 0, diffDays = 0;
        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            long diff = d2.getTime() - d1.getTime();

            diffSeconds = diff / 1000 % 60;
            diffMinutes = diff / (60 * 1000) % 60;
            diffHours = diff / (60 * 60 * 1000) % 24;
            diffDays = diff / (24 * 60 * 60 * 1000);

        } catch (Exception e) {
            e.printStackTrace();
        }

        String result = "";
        if (diffDays > 0) {
            result += diffDays + " ngày ";
        }
        if (diffHours > 0) {
            result += diffHours + " giờ ";
        }
        if (diffMinutes > 0) {
            result += diffMinutes + " phút ";
        }
        if (diffSeconds > 0) {
            result += diffSeconds + " giây ";
        }
        return result;
    }

    private void stylePolyline(Polyline polyline) {
        polyline.setEndCap(new RoundCap());
        polyline.setWidth(POLYLINE_STROKE_WIDTH_PX);
        polyline.setColor(COLOR_BLACK_ARGB);
        polyline.setJointType(JointType.ROUND);
    }

    @Override
    public void onPolylineClick(Polyline polyline) {
    }

    private void getApiMatricTest() {

        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps/dir/?api=1" +
                            "&origin=" + URLEncoder.encode("xom O, Tam Sơn, Từ Sơn, Bắc Ninh", "UTF-8") +
                            "&destination=" + URLEncoder.encode("Ngo Gia Tu, Long Biên, Hà Nội", "UTF-8") +
                            "&travelmode=driving"));

            intent.setPackage("com.google.android.apps.maps");

            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toasty.warning(getContext(), getString(R.string.please_install_maps_application), Toast.LENGTH_LONG).show();
            }
            startActivity(intent);
        } catch (UnsupportedEncodingException e) {
            Toasty.warning(getContext(), getString(R.string.error_parse_address), Toast.LENGTH_LONG).show();
        }
    }

    private void getApiMatricTest1() {
        mainActivcity.showLoading();
        Map<String, String> mapQuery = new HashMap<>();
        mapQuery.put("units", "imperial");
        mapQuery.put("origins", "21.025965, 105.788488");
        mapQuery.put("destinations", "Ngo+gia+tu,Long+Bien|Ha+Noi,VN");
        mapQuery.put("key", getString(R.string.google_maps_key));

        APIGG.INSTANCE.getService().getDistanceInfo(mapQuery)
                .subscribeOn(Schedulers.io()) //(*)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MatrixRespon>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(MatrixRespon response) {
                        mainActivcity.hideLoading();
                        if (response != null &&
                                response.getRows() != null &&
                                response.getRows().size() > 0 &&
                                response.getRows().get(0) != null &&
                                response.getRows().get(0).getElements() != null &&
                                response.getRows().get(0).getElements().size() > 0 &&
                                response.getRows().get(0).getElements().get(0) != null &&
                                response.getRows().get(0).getElements().get(0).getDistance() != null &&
                                response.getRows().get(0).getElements().get(0).getDuration() != null) {

                            Element element = response.getRows().get(0).getElements().get(0);

                            Toasty.error(getActivity(), element.getDistance().getText() + "\n" + element.getDuration().getText(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mainActivcity.hideLoading();
                        Toasty.error(getActivity(), getString(R.string.loi_ket_noi), Toast.LENGTH_SHORT, true).show();
                    }
                });
    }

    private LatLng getCurrentCoordinate() {
        Location location = getCurrentLocation();
        if (location == null) return new LatLng(21.024673, 105.789692); // so 6 Pham Van Bach
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    private Location getCurrentLocation() {
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) return null;

        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        return location;
    }

    @Override
    public void onClickItemCarSearch(LstBookCarDto carDto) {
        text_auto_car.setText(carDto.getLicenseCar());
        text_auto_car.dismissDropDown();
        mainActivcity.hideKeyBoard();

        homeViewModel.carDtoSelected = carDto;
    }

    private void updateInfoCar(Integer carId, String licenseCar) {
        mainActivcity.showLoading();
        GetHistoryCarBody body = new GetHistoryCarBody();

        CarInfoHistory bookCarDto = new CarInfoHistory();
        bookCarDto.setCarId(carId);
        bookCarDto.setLicenseCar(licenseCar);
        bookCarDto.setFromTimeSearch(text_pos_from.getText().toString());
        bookCarDto.setToTimeSearch(text_pos_to.getText().toString());
        body.setBookCarDto(bookCarDto);

        UserLogin userLogin = CommonVCC.getUserLogin();
        SysUserRequest sysUserRequest = new SysUserRequest();
        sysUserRequest.setAuthenticationInfo(new AuthenticationInfo(userLogin.getLoginName(), userLogin.getPassword()));
        sysUserRequest.setSysUserId(CommonVCC.getUserLogin().getSysUserId());
        body.setSysUserRequest(sysUserRequest);

        API.INSTANCE.getService().getHistoryCar(body)
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

    private SingleDateAndTimePickerDialog initDateTimePickerDialogStart(View root) {
        return new SingleDateAndTimePickerDialog.Builder(root.getContext())
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
//                .mustBeOnFuture()
//                .minDateRange(Calendar.getInstance().getTime())
                .defaultDate(Calendar.getInstance().getTime())
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {
                        DateFormat dateFormat;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.forLanguageTag("vn-VN"));
                        } else {
                            dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("vi", "VN"));
                        }
                        homeViewModel.dateStart = date;
                        text_pos_from.setText(dateFormat.format(date));
                    }
                })
                .build();
    }

    private SingleDateAndTimePickerDialog initDateTimePickerDialogEnd(View root) {
        return new SingleDateAndTimePickerDialog.Builder(root.getContext())
                .title(root.getContext().getString(R.string.theo_doi_lo_trinh_den))
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
//                .mustBeOnFuture()
//                .minDateRange(Calendar.getInstance().getTime())
                .defaultDate(Calendar.getInstance().getTime())
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {
                        if (homeViewModel.dateStart != null && date.getTime() <= homeViewModel.dateStart.getTime()) {
                            Toasty.error(getActivity(), getString(R.string.ban_phai_chon_thoi_gian_end_sau_start), Toasty.LENGTH_LONG).show();
                        } else {
                            DateFormat dateFormat;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.forLanguageTag("vn-VN"));
                            } else {
                                dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("vi", "VN"));
                            }
                            text_pos_to.setText(dateFormat.format(date));
                        }
                    }
                })
                .build();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (dialogDataTimeStart.isDisplaying()) dialogDataTimeStart.dismiss();
        if (dialogDataTimeEnd.isDisplaying()) dialogDataTimeEnd.dismiss();

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
        if (dialogDataTimeStart.isDisplaying()) dialogDataTimeStart.dismiss();
        if (dialogDataTimeEnd.isDisplaying()) dialogDataTimeEnd.dismiss();
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

    public static CameraUpdate buildCameraUpdate(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .tilt(TILT_LEVEL)
                .zoom(ZOOM_LEVEL)
                .build();
        return CameraUpdateFactory.newCameraPosition(cameraPosition);
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