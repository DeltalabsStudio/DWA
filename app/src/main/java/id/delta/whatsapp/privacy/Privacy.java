package id.delta.whatsapp.privacy;

import com.whatsapp.protocol.o;

import java.lang.reflect.Field;

import id.delta.whatsapp.utils.SPriv;

public class Privacy {

    private static String JID;

    public static boolean AlwaysOnline() {
        return SPriv.getBoolean("always_online");
    }

    public static boolean AntiRevoke(final Object object) {
        final String a = ((o)object).b.a;
        boolean b;
        if(a.equals("status")){
            b = SPriv.getBoolean("Antirevoke");
        }else if (SPriv.getBoolean(a)) {
            b = SPriv.getBoolean(String.valueOf(a) + "_AR");
        } else {
            b = SPriv.getBoolean("Antirevoke");
        }
        return b;
    }


    private static String GetType(Object o) {
        for (final Field field : o.getClass().getFields()) {
            if (String.class.isAssignableFrom(field.getType())) {
                try {
                    final Object value = field.get(o);
                    if (value != null) {
                        if (value.toString().contains("@broadcast")) {
                            o = "B";
                            return (String) o;
                        } else if (value.toString().contains("@s.whatsapp.net")) {
                            o = "G";
                            return (String) o;
                        } else if (value.toString().contains("g.us")) {
                            o = "G";
                            return (String) o;
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        }
        o = "C";
        return (String) o;
    }

    private static String GetType2(final String jid) {
        String s2;
        if (jid.contains("g.us")) {
            s2 = "G";
        } else if (jid.contains("@broadcast")) {
            s2 = "B";
        } else {
            s2 = "C";
        }
        return s2;
    }

    public static boolean HideCR(final String jid, final int n) {
        boolean b;
        switch (n) {
            default: {
                if (SPriv.getBoolean(jid)) {
                    b = SPriv.getBoolean(jid + "_HideCompose");
                    break;
                }
                b = SPriv.getBoolean(GetType2(jid) + "_HideCompose");
                break;
            }
            case 1: {
                if (SPriv.getBoolean(jid)) {
                    b = SPriv.getBoolean(jid + "_HideRecord");
                    break;
                }
                b = SPriv.getBoolean(GetType2(jid) + "_HideRecord");
                break;
            }
        }
        return b;
    }

    public static boolean HidePlay(final o statusProtocol) {
        final String jid = statusProtocol.b.a;
        boolean b;
        if (SPriv.getBoolean(jid)) {
            b = SPriv.getBoolean(jid + "_HidePlay");
        } else {
            b = SPriv.getBoolean(GetType(statusProtocol) + "_HidePlay");
        }
        return b;
    }

    public static boolean HideRead(final Object jidObject) {
        final String jid = (String)jidObject;
        boolean b;
        if (SPriv.getBoolean(jid)) {
            b = SPriv.getBoolean(jid + "_HideRead");
        }
        else {
            b = SPriv.getBoolean(GetType(jidObject) + "_HideRead");
        }
        return b;
    }

    public static boolean HideReceipt(final Object jidObject) {
        final String jid = (String)jidObject;
        boolean b;
        if (SPriv.getBoolean(jid)) {
            b = SPriv.getBoolean(jid + "_HideReceipt");
        }
        else {
            b = SPriv.getBoolean(GetType(jidObject) + "_HideReceipt");
        }
        return b;
    }

    public static boolean HideSeen() {
        return SPriv.getBoolean("HideSeen");
    }

    public static boolean HideStatus() {
        final String jid = Privacy.JID;
        boolean b;
        if (SPriv.getBoolean(jid)) {
            b = (!SPriv.getBoolean(String.valueOf(jid) + "_HideStatus"));
        }
        else {
            b = (!SPriv.getBoolean("hide_status"));
        }
        return b;
    }

    public static void SetJID(final String jid) {
        Privacy.JID = jid;
    }

}
