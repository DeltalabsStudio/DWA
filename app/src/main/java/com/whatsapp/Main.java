package com.whatsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import id.delta.whatsapp.utils.Actions;


/**
 * Created by DELTALABS on 6/18/2017.
 */

public class Main extends DialogToastActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  setContentView(Tools.intLayout("activity_start"));

      //  Prefs.putInt("delta_version_code", Updater.VERSION_CODE);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(500);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                    Intent intent = new Intent(Main.this, HomeActivity.class);
                    startActivity(intent);

                }
            }
        };
        timerThread.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
