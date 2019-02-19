package com.begaldev;

public class BegalDev {

    public static boolean GetSharedBool(String key, boolean def){
        return def;
    }

    public static boolean disableInternet() {
        return GetSharedBool("key_disable_internet", false);
    }
}
