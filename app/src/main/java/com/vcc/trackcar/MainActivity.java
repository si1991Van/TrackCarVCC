package com.vcc.trackcar;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.vcc.trackcar.model.addBookCar.SysUserRequest;
import com.vcc.trackcar.model.auth.AuthenticationInfo;
import com.vcc.trackcar.model.auth.UserLogin;
import com.vcc.trackcar.model.getBookCarToken.GetBookCarTokenBody;
import com.vcc.trackcar.model.getBookCarToken.GetBookCarTokenRespon;
import com.vcc.trackcar.model.getListBookCar.LstBookCarDto;
import com.vcc.trackcar.model.getListUser.LstUserDto;
import com.vcc.trackcar.model.updateLocation.UpdateLocationBody;
import com.vcc.trackcar.model.updateLocation.UpdateLocationRespon;
import com.vcc.trackcar.model.updateTokenUser.UpdateTokenUserBody;
import com.vcc.trackcar.model.updateTokenUser.UpdateTokenUserRespon;
import com.vcc.trackcar.remote.API;
import com.vcc.trackcar.ui.base.CommonVCC;
import com.vcc.trackcar.ui.base.PrefManager;
import com.vcc.trackcar.ui.detail_book_car.DetailBookCarFragment;
import com.vcc.trackcar.ui.giao_viec.GiaoViecFragment;
import com.vcc.trackcar.ui.giao_viec.detail_giao_viec.DetailGiaoViecFragment;
import com.vcc.trackcar.ui.list_book_added.ListBookAddedFragment;
import com.vcc.trackcar.ui.list_book_added.update_book_car.UpdateBookCarFragment;
import com.vcc.trackcar.ui.login.LoginActivity;
import com.vcc.trackcar.ui.view_and_sign_approval.ViewAndSignApprovalFragment;
import com.vcc.trackcar.ui.view_and_sign_approval_manager_car.ViewAndApprovalManagerCarFragment;
import com.vcc.trackcar.ui.xem_ky_duyet_tp_hanhchinh_tct.XemKyDuyetTPHCTCTFragment;
import com.vcc.trackcar.ui.xep_xe_ban_tct.XepXeBanTCTFragment;
import com.vcc.trackcar.ui.xep_xe_doi_truong_xe.XepXeFragment;
import com.vcc.trackcar.ui.xep_xe_doi_truong_xe.detail_xep_xe.DetailXepXeFragment;
import com.vcc.trackcar.utils.UtilsExtensionKt;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {
    private AppBarConfiguration mAppBarConfiguration;

    private NavigationView navigationView;
    private NavController navController;

    public static final String EXTRA_TYPE_MENU = "EXTRA_TYPE_MENU";
    public static final String EXTRA_BOOK_CAR_ID = "EXTRA_BOOK_CAR_ID";

    public List<com.vcc.trackcar.model.getListDriverCar.LstBookCarDto> listCar = new ArrayList<>();
    public List<com.vcc.trackcar.model.getListDriverCar.LstBookCarDto> listDriver = new ArrayList<>();

    // ================
    private static final String TAG = "resPMain";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private MyReceiver myReceiver;
    private LocationUpdatesService mService = null;
    private boolean mBound = false;
    private Switch switch_location;
    private LinearLayout layout_switch_location;
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationUpdatesService.LocalBinder binder = (LocationUpdatesService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };

    private KProgressHUD hud;

    private static final int LAP_PHIEU_DAT_XE = 1;
    private static final int DANH_SACH_PHIEU_DA_LAP = 2;
    private static final int XEM_VA_KY_DUYET_XE_TP_BP = 3;
    private static final int XEP_XE_QL_DX = 4;
    private static final int XEM_VA_KY_DUYET_XE_TT_XE = 5;
    private static final int GIAO_VIEC_LAI_XE = 6;
    private static final int XEP_XE_BAN_TCT = 7;
    private static final int XEM_VA_KY_DUYET_XE_TPHC = 8;
    private static final String NHANVIEN = "NHANVIEN";
    private static final String TRUONGPHONG = "TRUONGPHONG";
    private static final String DOITRUONGXE = "DOITRUONGXE";
    private static final String THUTRUONGXE = "THUTRUONGXE";
    private static final String BANXETCT = "BANXETCT";
    private static final String TPHANHCHINH = "TPHANHCHINH";
    private static final String LAIXE = "LAIXE";
    public static String HANHTRINH = "HANHTRINH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myReceiver = new MyReceiver();
        setContentView(R.layout.activity_main);
        initProgressLoading();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        initViewMenu();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_xep_xe, R.id.nav_view_and_approval,
                R.id.nav_share, R.id.nav_xep_xe_ban_tct, R.id.nav_view_and_approval_tphc_tct,
                R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        initView();
        initFireBaseFcmToken();

        if (getIntent().hasExtra(EXTRA_TYPE_MENU)) {
            Integer typeMenu = getIntent().getIntExtra(EXTRA_TYPE_MENU, -1);
            Integer bookCarId = getIntent().getIntExtra(EXTRA_BOOK_CAR_ID, -1);

            getBookCarToken(typeMenu, bookCarId);

            // DetailBookCarFragment : 2,3,5,8,9
//            navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//            getBookCarToken(typeMenu, bookCarId);
        }

        // Check that the user hasn't revoked permissions by going to Settings.
        if (Utils.requestingLocationUpdates(this)) {
            if (!checkPermissions()) {
                requestPermissions();
            }
        }
    }

    private void initViewMenu() {
        for (int i = 1; i <= 8; i++) {
            navigationView.getMenu().getItem(i).setVisible(false);
        }
        if (CommonVCC.getUserLogin().getRoleCode() == null) return;

        String[] listRoleCode = CommonVCC.getUserLogin().getRoleCode().split(";");
        for (String roleCode : listRoleCode) {
            switch (roleCode) {
                case NHANVIEN:
                    navigationView.getMenu().getItem(LAP_PHIEU_DAT_XE).setVisible(true);
                    navigationView.getMenu().getItem(DANH_SACH_PHIEU_DA_LAP).setVisible(true);
                    break;
                case TRUONGPHONG:
                    navigationView.getMenu().getItem(XEM_VA_KY_DUYET_XE_TP_BP).setVisible(true);
                    break;
                case DOITRUONGXE:
                    navigationView.getMenu().getItem(XEP_XE_QL_DX).setVisible(true);
                    break;
                case THUTRUONGXE:
                    navigationView.getMenu().getItem(XEM_VA_KY_DUYET_XE_TT_XE).setVisible(true);
                    break;
                case LAIXE:
                    navigationView.getMenu().getItem(GIAO_VIEC_LAI_XE).setVisible(true);
                    break;
                case BANXETCT:
                    navigationView.getMenu().getItem(XEP_XE_BAN_TCT).setVisible(true);
                    break;
                case TPHANHCHINH:
                    navigationView.getMenu().getItem(XEM_VA_KY_DUYET_XE_TPHC).setVisible(true);
                    break;
            }
        }
    }

    private void initProgressLoading() {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(R.drawable.spin_animation);
        AnimationDrawable drawable = (AnimationDrawable) imageView.getBackground();
        drawable.start();
        hud = KProgressHUD.create(this)
                .setCustomView(imageView)
                .setLabel(getString(R.string.pleasae_wait))
                .setDimAmount(0.5f);
    }

    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);

        bindService(new Intent(this, LocationUpdatesService.class), mServiceConnection,
                Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
                new IntentFilter(LocationUpdatesService.ACTION_BROADCAST));
        UtilsExtensionKt.checkAppVersion(this);
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        super.onStop();
    }

    private void initView() {
        View hView = navigationView.getHeaderView(0);

        layout_switch_location = hView.findViewById(R.id.layout_switch_location);
        layout_switch_location.setVisibility(isHasRoleCodeLaiXe() ? View.VISIBLE : View.GONE);
        switch_location = hView.findViewById(R.id.switch_location);
        switch_location.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    if (!checkPermissions()) {
                        requestPermissions();
                    } else {
                        mService.requestLocationUpdates();
                    }
                } else {
                    // The toggle is disabled
                    if (mService != null)
                        mService.removeLocationUpdates();
                }
            }
        });

        setButtonsState(Utils.requestingLocationUpdates(this));

        navigationView.getMenu().findItem(R.id.bac_test).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                showLogout();
                return false;
            }
        });

        if (!CommonVCC.debug) {
            UserLogin userLogin = CommonVCC.getUserLogin();
            TextView fullName = hView.findViewById(R.id.fullName);
            TextView email = hView.findViewById(R.id.email);
            fullName.setText(userLogin.getFullName());
            email.setText(userLogin.getEmail());
        }

    }

    private boolean isHasRoleCodeLaiXe() {
        if (CommonVCC.getUserLogin().getRoleCode() != null) {
            String[] listRoleCode = CommonVCC.getUserLogin().getRoleCode().split(";");
            for (String roleCode : listRoleCode) {
                if (roleCode.equals(MainActivity.LAIXE)) return true;
            }
        }
        return false;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onSupportNavigateUp() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void navigateFragment(int resId, Bundle bundle) {
        if (navController != null)
            navController.navigate(resId, bundle);
        else
            Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(resId, bundle);
    }

    public void popBackStackFragment() {
        if (navController != null)
            navController.popBackStack();
        else Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).popBackStack();
    }

    public void popBackStackFragment(int destinationId) {
        navController.popBackStack(destinationId, true);
    }

    public void showLoading() {
//        progress_loading.setVisibility(View.VISIBLE);
//        progress_loading.setLoading(true);
        hud.show();
    }

    public void hideLoading() {
//        progress_loading.setVisibility(View.GONE);
//        progress_loading.setLoading(false);
        hud.dismiss();
    }

    private void showLogout() {
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle(getString(R.string.dang_xuat))
                .setMessage(getString(R.string.question_dang_xuat_comfirm))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.xac_nhan), new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        PrefManager.getInstance().put(CommonVCC.USER_LOGIN, null);
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
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

    private void initFireBaseFcmToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            showErrorDialog(getString(R.string.su_co), getString(R.string.gap_loi_khi_khoi_tao_trung_tam_thong_bao));
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        Log.d("bacnd4 - token", token);
                        if (CommonVCC.getUserLogin().getToken() == null || !CommonVCC.getUserLogin().getToken().equals(token))
                            updateTokenUser(token);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showErrorDialog(getString(R.string.su_co), getString(R.string.gap_loi_khi_khoi_tao_trung_tam_thong_bao));
                        return;
                    }
                });
    }

    private void updateTokenUser(String token) {
        showLoading();

        UserLogin userLogin = CommonVCC.getUserLogin();
        UpdateTokenUserBody body = new UpdateTokenUserBody();
        body.setAuthenticationInfo(new AuthenticationInfo(userLogin.getLoginName(), userLogin.getPassword()));
        body.setSysUserId(userLogin.getSysUserId());
        body.setToken(token);
        API.INSTANCE.getService().updateTokenUser(body)
                .subscribeOn(Schedulers.io()) //(*)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<UpdateTokenUserRespon>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(UpdateTokenUserRespon updateTokenUserRespon) {
                        hideLoading();
                        if (!updateTokenUserRespon.getResultInfo().getStatus().equals(CommonVCC.RESULT_STATUS_OK)) {
                            Toasty.error(MainActivity.this, updateTokenUserRespon.getResultInfo().getMessage(), Toast.LENGTH_SHORT, true).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                        Toasty.error(MainActivity.this, getString(R.string.loi_ket_noi), Toast.LENGTH_SHORT, true).show();
                    }
                });
    }

    private void getBookCarToken(Integer typeMenu, Integer bookCarId) {
        showLoading();

        UserLogin userLogin = CommonVCC.getUserLogin();
        GetBookCarTokenBody body = new GetBookCarTokenBody();

        LstBookCarDto lstBookCarDto = new LstBookCarDto();
        lstBookCarDto.setBookCarId(bookCarId);
        body.setBookCarDto(lstBookCarDto);

        SysUserRequest sysUserRequest = new SysUserRequest();
        sysUserRequest.setAuthenticationInfo(new AuthenticationInfo(userLogin.getLoginName(), userLogin.getPassword()));
        sysUserRequest.setSysUserId(CommonVCC.getUserLogin().getSysUserId());
        body.setSysUserRequest(sysUserRequest);

        API.INSTANCE.getService().getBookCarToken(body)
                .subscribeOn(Schedulers.io()) //(*)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetBookCarTokenRespon>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GetBookCarTokenRespon getBookCarTokenRespon) {
                        hideLoading();
                        if (getBookCarTokenRespon.getResultInfo().getStatus().equals(CommonVCC.RESULT_STATUS_OK)) {
                            handleViewFcm(typeMenu, getBookCarTokenRespon.getLstBookCarDto().get(0));
                        } else {
                            Toasty.error(MainActivity.this, getBookCarTokenRespon.getResultInfo().getMessage(), Toast.LENGTH_SHORT, true).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                        Toasty.error(MainActivity.this, getString(R.string.loi_ket_noi), Toast.LENGTH_SHORT, true).show();
                    }
                });
    }

    private void handleViewFcm(Integer typeMenu, LstBookCarDto bookCarDto) { // 2, 3, 5, 8, 9 - 4, 6
        if (typeMenu.equals(ListBookAddedFragment.TYPE_MENU)) {
            String status = bookCarDto.getStatus();
            String typeBookCar = bookCarDto.getTypeBookCar();
            String statusManagerCar = bookCarDto.getStatusManagerCar();
            String statusCaptainCar = bookCarDto.getStatusCaptainCar();
            String statusDriverBoard = bookCarDto.getStatusDriverBoard();
            String statusAdministrative = bookCarDto.getStatusAdministrative();

            if (status.equals("1") || status.equals("4")) suaLenh(bookCarDto);
            else if ((typeBookCar.equals("1") || typeBookCar.equals("2")) && status.equals("2") && statusManagerCar.equals("4"))
                suaLenh(bookCarDto);
            else if ((typeBookCar.equals("1") || typeBookCar.equals("2")) && status.equals("2") && statusCaptainCar.equals("4"))
                suaLenh(bookCarDto);
            else if (typeBookCar.equals("3") && status.equals("2") && statusCaptainCar.equals("4"))
                suaLenh(bookCarDto);
            else if (typeBookCar.equals("4") && status.equals("2") && statusDriverBoard.equals("4"))
                suaLenh(bookCarDto);
            else if (typeBookCar.equals("4") && status.equals("2") && statusAdministrative.equals("4"))
                suaLenh(bookCarDto);
            else detailBookCar(bookCarDto, typeMenu);

        } else if (typeMenu.equals(ViewAndSignApprovalFragment.TYPE_MENU)) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(DetailBookCarFragment.EXTRA_BOOK_CAR, bookCarDto);
            bundle.putSerializable(DetailBookCarFragment.EXTRA_TYPE_MENU, typeMenu);
            Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_detail_book_car, bundle);
        } else if (typeMenu.equals(ViewAndApprovalManagerCarFragment.TYPE_MENU)) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(DetailBookCarFragment.EXTRA_BOOK_CAR, bookCarDto);
            bundle.putSerializable(DetailBookCarFragment.EXTRA_TYPE_MENU, typeMenu);
            Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_detail_book_car, bundle);
        } else if (typeMenu.equals(XepXeBanTCTFragment.TYPE_MENU)) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(DetailBookCarFragment.EXTRA_BOOK_CAR, bookCarDto);
            bundle.putSerializable(DetailBookCarFragment.EXTRA_TYPE_MENU, typeMenu);
            Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_detail_book_car, bundle);
        } else if (typeMenu.equals(XemKyDuyetTPHCTCTFragment.TYPE_MENU)) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(DetailBookCarFragment.EXTRA_BOOK_CAR, bookCarDto);
            bundle.putSerializable(DetailBookCarFragment.EXTRA_TYPE_MENU, typeMenu);
            Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_detail_book_car, bundle);
        } else if (typeMenu.equals(XepXeFragment.TYPE_MENU)) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(DetailXepXeFragment.EXTRA_BOOK_CAR, bookCarDto);
            Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_detail_xep_xe, bundle);
        } else if (typeMenu.equals(GiaoViecFragment.TYPE_MENU)) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(DetailGiaoViecFragment.EXTRA_BOOK_CAR, bookCarDto);
            bundle.putSerializable(DetailGiaoViecFragment.EXTRA_TYPE_MENU, typeMenu);
            Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_detail_giao_viec, bundle);
        }

    }

    private void detailBookCar(LstBookCarDto bookCarDto, Integer typeMenu) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DetailBookCarFragment.EXTRA_BOOK_CAR, bookCarDto);
        bundle.putSerializable(DetailBookCarFragment.EXTRA_TYPE_MENU, typeMenu);

        Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_detail_book_car, bundle);
    }

    private void suaLenh(LstBookCarDto bookCarDto) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(UpdateBookCarFragment.EXTRA_BOOK_CAR, bookCarDto);

        Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_update_book_car, bundle);
    }

    public void showErrorDialog(String title, String content) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText(getString(R.string.thu_lai))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        initFireBaseFcmToken();
                    }
                })
                .show();
    }

    public void showNewVersionDialog(String versionNew, String versionOld) {
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle(getString(R.string.thong_bao))
                .setMessage(getString(R.string.update_version, versionNew, versionOld))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.update), new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        dialogInterface.dismiss();
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.dong_app), new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        finish();
                    }
                })
                .build();

        mDialog.show();
    }

    public void hideKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(Utils.KEY_REQUESTING_LOCATION_UPDATES)) {
            setButtonsState(sharedPreferences.getBoolean(Utils.KEY_REQUESTING_LOCATION_UPDATES,
                    false));
        }
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location = intent.getParcelableExtra(LocationUpdatesService.EXTRA_LOCATION);
            if (location != null) {
//                Toast.makeText(MainActivity.this, Utils.getLocationText(location), Toast.LENGTH_SHORT).show();

                UpdateLocationBody body = new UpdateLocationBody();
                body.setLoginName(CommonVCC.getUserLogin().getLoginName());
                body.setLatitude(Double.toString(location.getLatitude()));
                body.setLongitude(Double.toString(location.getLongitude()));
                API.INSTANCE.getService().updateLocation(body)
                        .subscribeOn(Schedulers.io()) //(*)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<UpdateLocationRespon>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onSuccess(UpdateLocationRespon updateLocationRespon) {
                            }

                            @Override
                            public void onError(Throwable e) {
                            }
                        });
            }
        }
    }

    private boolean checkPermissions() {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            Snackbar.make(
                    findViewById(R.id.drawer_layout),
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    })
                    .show();
        } else {
            Log.i(TAG, "Requesting permission");
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted.
                mService.requestLocationUpdates();
            } else {
                // Permission denied.
                setButtonsState(false);
                Snackbar.make(
                        findViewById(R.id.drawer_layout),
                        R.string.permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        }
    }

    private void setButtonsState(boolean requestingLocationUpdates) {
        if (requestingLocationUpdates) {
            switch_location.setChecked(true);
        } else {
            switch_location.setChecked(false);
        }
    }
}
