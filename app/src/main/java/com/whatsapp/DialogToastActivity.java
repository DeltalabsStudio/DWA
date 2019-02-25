package com.whatsapp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.utils.WaPrefs;
import id.delta.whatsapp.value.Wallpaper;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by DELTA on 10/2/2017.
 */

public class DialogToastActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 3;
    public boolean isRequest = false;

    public yv mMeManager = new yv();
    public static String Me = "RGVsdGFsYWJzIFN0dWRpbw==";

    public static String c(String encoded) {
        byte[] dataDec = Base64.decode(encoded, Base64.DEFAULT);
        String decodedString = "";
        try {

            decodedString = new String(dataDec, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        } finally {

            return decodedString;
        }
    }

    //Anti Maling
    public void z(){
        String m = getResources().getString(getResources().getIdentifier("deltalabs", "string", getPackageName()));
        if(!m.equals(c(Me))){
            System.exit(0);
        }
    }

    public void setSign(Activity activity, String sign){
        PackageManager pm = activity.getPackageManager();
        String packageName = activity.getPackageName();
        int flags = PackageManager.GET_SIGNATURES;

        PackageInfo packageInfo = null;

        try {
            packageInfo = pm.getPackageInfo(packageName, flags);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Signature[] signatures = packageInfo.signatures;

        byte[] cert = signatures[0].toByteArray();

        InputStream input = new ByteArrayInputStream(cert);

        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        X509Certificate c = null;
        try {
            c = (X509Certificate) cf.generateCertificate(input);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(c.getPublicKey().getEncoded());
            StringBuffer hexString = new StringBuffer();
            for (int i=0;i<publicKey.length;i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i]);
                if(appendString.length()==1)hexString.append("0");
                hexString.append(appendString);
            }

            WaPrefs.putString("cert",hexString.toString()); //Untuk mengetahui kode sign kita

            //App akan blank putih / keluar
            if(!hexString.toString().equals(sign))System.exit(0);

        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

