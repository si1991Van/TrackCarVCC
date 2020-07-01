package com.vcc.trackcar.ui.base;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Patterns;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.vcc.trackcar.R;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;

public class ValidationUtil {

    //bacnd4
    public static boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString().trim();
        return TextUtils.isEmpty(str);
    }

    //bacnd4
    public static boolean isValidEmail(EditText text) {
        CharSequence email = text.getText().toString().trim();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    //bacnd4
    public static boolean isValidPassword(String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    //bacnd4
    public static void setInputTypeErrorState(EditText et, String message, Context context) {
        if (et == null)
            return;
        et.setTextColor(ContextCompat.getColor(context, R.color.mine_shaft));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            et.setBackgroundTintList(getColorStateList(R.color.c3));
            et.setError(message);
        }
        et.requestFocus();
//        hideKeyBoard();
    }

    //bacnd4
    public static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCkzdJ2WpJ1Hu5n8gU+MJAefAhBCzr4sMqhPrPCCYy02JBbibfXJmc3y3Kf79O6jLl2uynzP5LKrwFX+C1FgcUyMzYsV5HPbM+w6+hmwxuSdHJt+tTk4+l9vc+3Aetz9pM/N65MSELcc90ugSdghpw9L8xktp/GLeFJUYyZ7/9eXQIDAQAB";
    public static final String ALGORITHM = "RSA";

    public static PublicKey getPublicKey() throws Exception {
        byte[] keyBytes = new byte[0];
        keyBytes = Base64.decode(PUBLIC_KEY, Base64.NO_WRAP);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String encryptText(String msg) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());
        return Base64.encodeToString(cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8)), Base64.NO_WRAP);
    }
}
