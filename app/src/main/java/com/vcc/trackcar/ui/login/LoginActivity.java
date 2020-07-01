package com.vcc.trackcar.ui.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.scwang.wave.MultiWaveHeader;
import com.vcc.trackcar.MainActivity;
import com.vcc.trackcar.R;
import com.vcc.trackcar.model.auth.AuthBody;
import com.vcc.trackcar.model.auth.AuthRespon;
import com.vcc.trackcar.model.auth.AuthenticationInfo;
import com.vcc.trackcar.remote.API;
import com.vcc.trackcar.ui.base.CommonVCC;
import com.vcc.trackcar.ui.base.PrefManager;
import com.vcc.trackcar.ui.base.ValidationUtil;

import es.dmoral.toasty.Toasty;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_visibility_pass;
    private Boolean isCheckedPass = false;
    private EditText edt_password;
    private Button btn_login;
    private EditText edt_user_name;
//    private CustomProgress progress_loading;
//    private MultiWaveHeader wave_header;
private KProgressHUD hud;

    private final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        hideKeyBoard();
        initView();
        initData();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
    }

    public boolean checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }

            return false;
        } else {
            return true;
        }
    }

    private void initData() {
        edt_user_name.setText(PrefManager.getInstance().getString(CommonVCC.USER_NAME_LOGIN));
    }

    @Override
    protected void onResume() {
//        wave_header.start();
        super.onResume();
    }

    @Override
    protected void onStop() {
//        wave_header.stop();
        super.onStop();
    }

    private void initView() {
        edt_password = findViewById(R.id.edt_password);
        img_visibility_pass = findViewById(R.id.img_visibility_pass);
        img_visibility_pass.setOnClickListener(this);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        edt_user_name = findViewById(R.id.edt_user_name);
        findViewById(R.id.layout_content).setOnClickListener(this);
//        progress_loading = findViewById(R.id.progress_loading);
//        progress_loading.setLoading(true);
//        wave_header = findViewById(R.id.wave_header);
        initProgressLoading();

        // debug
        if (CommonVCC.debug) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
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

    private void showPassword() {
        hideKeyBoard();
        if (isCheckedPass) isCheckedPass = false;
        else isCheckedPass = true;

        if (isCheckedPass) {
            edt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            img_visibility_pass.setImageResource(R.drawable.ic_visibility_off);
        } else {
            edt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            img_visibility_pass.setImageResource(R.drawable.ic_visibility_on);
        }
    }

    public void hideKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_visibility_pass:
                showPassword();
                break;
            case R.id.btn_login:
                login();
                hideKeyBoard();
                break;
            case R.id.layout_content:
                hideKeyBoard();
                break;
        }
    }

    private void login() {
        if (validateLogin()) {
            fetchAuth();
        }
    }

    private void fetchAuth() {
//        progress_loading.setVisibility(View.VISIBLE);
        hud.show();
        String userName = edt_user_name.getText().toString().trim();
        String passWord = edt_password.getText().toString();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(userName, passWord);
        AuthBody body = new AuthBody(authenticationInfo);
        API.INSTANCE.getService().auth(body)
                .subscribeOn(Schedulers.io()) //(*)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<AuthRespon>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(AuthRespon authRespon) {
//                        progress_loading.setVisibility(View.GONE);
                        hud.dismiss();
                        if (authRespon.getResultInfo().getStatus().equals(CommonVCC.RESULT_STATUS_OK)) {
                            String userLogin = new Gson().toJson(authRespon.getUserLogin());
                            PrefManager.getInstance().put(CommonVCC.USER_LOGIN, userLogin);
                            PrefManager.getInstance().put(CommonVCC.USER_NAME_LOGIN, userName);

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toasty.error(LoginActivity.this, getString(R.string.auth_reject), Toast.LENGTH_SHORT, true).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
//                        progress_loading.setVisibility(View.GONE);
                        hud.dismiss();
                        Toasty.error(LoginActivity.this, getString(R.string.loi_ket_noi), Toast.LENGTH_SHORT, true).show();
                    }
                });
    }

    private boolean validateLogin() {
        if (ValidationUtil.isEmpty(edt_user_name)) {
            ValidationUtil.setInputTypeErrorState(edt_user_name, getString(R.string.please_input_info), this);
            edt_user_name.setText("");
            return false;
        }
        if (ValidationUtil.isEmpty(edt_password)) {
            ValidationUtil.setInputTypeErrorState(edt_password, getString(R.string.please_input_info), this);
            edt_password.setText("");
            return false;
        }
        return true;
    }
}
