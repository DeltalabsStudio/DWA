package id.delta.whatsapp.split.service;

public class DateUtil {
    private static final DateUtil ourInstance = new DateUtil();

    private DateUtil() {
    }

    public static DateUtil getInstance() {
        return ourInstance;
    }

    public String timeConversion(long j) {
        long j2 = j % 60;
        long j3 = (j / 60) % 60;
        return (j3 > 9 ? Long.valueOf(j3) : "0" + j3) + ":" + (j2 > 9 ? Long.valueOf(j2) : "0" + j2);
    }
}
