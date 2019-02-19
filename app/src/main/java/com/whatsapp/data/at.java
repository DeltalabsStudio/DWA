package com.whatsapp.data;

//rename from ContactsManager
public class at {

    //rename from getContactsManager
    public static at a() {
        return new at();
    }

    //rename from getContactByJabberId
    public ft b(String jabberId) {
        ft contactInfo = new ft();

        switch (jabberId) {
            case "":
                contactInfo.mJabberId = "";
                contactInfo.d = "Deltalabs Studio";
                break;
            case "someone":
                contactInfo.mJabberId = "someone";
                contactInfo.p = "Del";
                contactInfo.d = "Deltalabs Studio";
                break;
            case "5491100000000@s.whatsapp.net":
                contactInfo.p = "Push Name";
                contactInfo.mJabberId = "5491100000000@s.whatsapp.net";
                break;
        }

        return contactInfo;
    }

}
