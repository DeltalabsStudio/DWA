package id.delta.whatsapp.utils;

import android.app.Activity;
import android.app.AlertDialog;

import com.whatsapp.data.ft;
import com.whatsapp.dw;

import id.delta.whatsapp.implement.z3;

public class Call {

    public static void CallDialog(dw gy, ft ej, Activity activity, Integer m, boolean z, boolean zz){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setPositiveButton("OK", new z3(gy, ej, activity, m, z, zz));
        builder.setTitle("Call");
        builder.setMessage("Are you sure to make a call?");
        builder.create().show();
    }
}
