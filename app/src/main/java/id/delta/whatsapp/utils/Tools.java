package id.delta.whatsapp.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.whatsapp.AppShell;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;


/**
 * Created by DELTALABS on 5/15/2017.
 */

public class Tools {

    private static DisplayMetrics mDisplayMetrics = null;

    public static int dpToPx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * scale);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int spToPx(Context context, float sp) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getDisplayMetrics(context));
        return px;
    }

    public static Point getScreenSize(Context context) {
        Point point = new Point();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(point);
        return point;
    }

    private static DisplayMetrics getDisplayMetrics(Context context) {
        if (mDisplayMetrics == null) {
            mDisplayMetrics = context.getResources().getDisplayMetrics();
        }
        return mDisplayMetrics;
    }

    public static int intAttr(String atts) {
        return AppShell.ctx.getResources().getIdentifier(atts, "attr", AppShell.ctx.getPackageName());
    }

    public static int getID(String nama, String type) {
        return AppShell.ctx.getResources().getIdentifier(nama, type, AppShell.ctx.getPackageName());
    }

    public static int intDimen(String nama) {
        return AppShell.ctx.getResources().getIdentifier(nama, "dimen", AppShell.ctx.getPackageName());
    }

    public static int intAnim(String nama) {
        return AppShell.ctx.getResources().getIdentifier(nama, "anim", AppShell.ctx.getPackageName());
    }

    public static int intStyleable(String nama) {
        return AppShell.ctx.getResources().getIdentifier(nama, "styleable", AppShell.ctx.getPackageName());
    }

    public static int intLayout (String nama) {
        return AppShell.ctx.getResources().getIdentifier(nama, "layout", AppShell.ctx.getPackageName());
    }

    public static int intId (String nama) {
        return AppShell.ctx.getResources().getIdentifier(nama, "id", AppShell.ctx.getPackageName());
    }

    public static int intXml (String nama) {
        return AppShell.ctx.getResources().getIdentifier(nama, "xml", AppShell.ctx.getPackageName());
    }

    public static int intStyle(String nama) {
        return AppShell.ctx.getResources().getIdentifier(nama, "style", AppShell.ctx.getPackageName());
    }

    public static int intColor(String nama) {
        return AppShell.ctx.getResources().getIdentifier(nama, "color", AppShell.ctx.getPackageName());
    }

    public static int intDrawable(String nama) {
        return AppShell.ctx.getResources().getIdentifier(nama, "drawable", AppShell.ctx.getPackageName());
    }

    public static int intString(String nama) {
        return AppShell.ctx.getResources().getIdentifier(nama, "string", AppShell.ctx.getPackageName());
    }

    public static String getString(String string) {
        return AppShell.ctx.getString(AppShell.ctx.getResources().getIdentifier(string, "string", AppShell.ctx.getPackageName()));
    }

    public static Drawable getDrawable(String string) {
        return AppShell.ctx.getResources().getDrawable(intDrawable(string));
    }

    public static Drawable colorDrawable(String drawable, int color, PorterDuff.Mode mode){
        Drawable cd = getDrawable(drawable);
        cd.setColorFilter(color, mode);
        return cd;
    }

    public static Drawable colorDrawable(int drawable, int color, PorterDuff.Mode mode){
        Drawable cd = AppShell.ctx.getResources().getDrawable(drawable);
        cd.setColorFilter(color, mode);
        return cd;
    }

    public static int getColor(String color) {
        return AppShell.ctx.getResources().getColor(intColor(color));
    }

    public static void showToast(String string){
        Toast.makeText(AppShell.ctx, string, Toast.LENGTH_SHORT).show();
    }

    // https://stackoverflow.com/questions/3035692/how-to-convert-a-drawable-to-a-bitmap
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    // https://stackoverflow.com/questions/4821488/bad-image-quality-after-resizing-scaling-bitmap/7468636#7468636
    public static Bitmap resizeBitmap(Bitmap bitmap,int newWidth,int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float ratioX = newWidth / (float) bitmap.getWidth();
        float ratioY = newHeight / (float) bitmap.getHeight();
        float middleX = newWidth / 2.0f;
        float middleY = newHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;

    }

    // https://stackoverflow.com/questions/20531516/how-do-i-add-selectableitembackground-to-an-imagebutton-programmatically/31292299
    public static Drawable getSelectableItemBackgroundBorderless(Context context) {
        // Create an array of the attributes we want to resolve
        // using values from a theme
        // android.R.attr.selectableItemBackground requires API LEVEL 11
        int[] attrs = new int[] { android.R.attr.selectableItemBackgroundBorderless /* index 0 */};

        // Obtain the styled attributes. 'themedContext' is a context with a
        // theme, typically the current Activity (i.e. 'this')
        TypedArray ta = context.obtainStyledAttributes(attrs);

        // Now get the value of the 'listItemBackground' attribute that was
        // set in the theme used in 'themedContext'. The parameter is the index
        // of the attribute in the 'attrs' array. The returned Drawable
        // is what you are after
        Drawable drawableFromTheme = ta.getDrawable(0 /* index */);

        // Finally free resources used by TypedArray
        ta.recycle();

        return drawableFromTheme;
    }

    // https://stackoverflow.com/questions/20531516/how-do-i-add-selectableitembackground-to-an-imagebutton-programmatically/31292299
    public static Drawable getSelectableItemBackground(Context context) {
        int[] attrs = new int[] { android.R.attr.selectableItemBackground /* index 0 */};
        TypedArray ta = context.obtainStyledAttributes(attrs);
        Drawable drawableFromTheme = ta.getDrawable(0);
        ta.recycle();
        return drawableFromTheme;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static String encryptionMD5(byte[] byteStr) {
        MessageDigest messageDigest = null;
        StringBuffer md5StrBuff = new StringBuffer();
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(byteStr);
            byte[] byteArray = messageDigest.digest();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                } else {
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5StrBuff.toString();
    }



    public static String getMd5OfFile(String filePath)
    {
        String returnVal = "";
        try
        {
            InputStream input   = new FileInputStream(filePath);
            byte[]        buffer  = new byte[1024];
            MessageDigest md5Hash = MessageDigest.getInstance("MD5");
            int           numRead = 0;
            while (numRead != -1)
            {
                numRead = input.read(buffer);
                if (numRead > 0)
                {
                    md5Hash.update(buffer, 0, numRead);
                }
            }
            input.close();

            byte [] md5Bytes = md5Hash.digest();
            for (int i=0; i < md5Bytes.length; i++)
            {
                returnVal += Integer.toString( ( md5Bytes[i] & 0xff ) + 0x100, 16).substring( 1 );
            }
        }
        catch(Throwable t) {t.printStackTrace();}
        return returnVal.toUpperCase();
    }

    public static boolean checkInternetNow() {
        checkInternet();
        return isInternetActive();
    }

    private static boolean internet;

    public static void checkInternet() {
        ConnectivityManager f490b = (ConnectivityManager) AppShell.ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = f490b.getActiveNetworkInfo();
        boolean z = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        internet = z;
    }

    public static boolean isInternetActive() {
        return internet;
    }
}
