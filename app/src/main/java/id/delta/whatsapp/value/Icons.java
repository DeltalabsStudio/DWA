package id.delta.whatsapp.value;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Environment;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

import id.delta.whatsapp.R;
import id.delta.whatsapp.ui.views.FloatingActionButton;
import id.delta.whatsapp.utils.AssetCopier;
import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;

public class Icons {

    public static String deltaPath = "WhatsApp/DELTA/";

    private static int [] mImageIcon = {Tools.intDrawable("delta_ic_msg"), Tools.intDrawable("ic_camera_status_compose"), Tools.intDrawable("ic_action_call"), Tools.intDrawable("delta_ic_menus"), Tools.intDrawable("delta_ic_adds")};

    private static String [] mIconName = {"ic_chats","ic_cameras","ic_calls", "ic_menu", "ic_add"};

    private static String iconAssets= "file:///android_asset/icons/";

    private static String iconPath = deltaPath+"icons/";

    private static String fileType = ".png";

    public static void copyIcons(Context context){
       // int count = -1;
        try{
            if (!new File(Environment.getExternalStorageDirectory(), deltaPath).exists()){
                new File(Environment.getExternalStorageDirectory(), deltaPath).mkdir();
            }
            if (!new File(Environment.getExternalStorageDirectory(), iconPath).exists()){
                new File(Environment.getExternalStorageDirectory(), iconPath).mkdir();
            }
            new AssetCopier(context).withFileScanning().copy("icons", new File(Environment.getExternalStorageDirectory(), iconPath));
            //  count = new AssetCopier(context).withFileScanning().copy("icons", new File(Environment.getExternalStorageDirectory(), iconPath));
        }catch (Exception e){
            e.printStackTrace();
            Tools.showToast("Can't find Icons folder!");
        }
       // Tools.showToast((count==-1 ? "There was an error copying" : "files copied " + count));
    }

    public static void customIcons(Context mContext, int i, ImageView mImage, final FloatingActionButton mFab){
        if(!new File(Environment.getExternalStorageDirectory(), iconPath+mIconName[i]+fileType).exists()){
            copyIcons(mContext);
            Picasso.with(mContext).load(mImageIcon[i]).into(mImage);
            Picasso.with(mContext).load(mImageIcon[i]).into(mFab);

            return;
        }
        if(Prefs.getBoolean(Keys.KEY_CUSTOM_ICON, false)){
            Picasso.with(mContext).load(new File(Environment.getExternalStorageDirectory(), iconPath+mIconName[i]+fileType)).into(mImage);
            Picasso.with(mContext).load(new File(Environment.getExternalStorageDirectory(), iconPath+mIconName[i]+fileType)).into(mFab);
        }else {
            Picasso.with(mContext).load(mImageIcon[i]).into(mImage);
            Picasso.with(mContext).load(mImageIcon[i]).into(mFab);
        }
    }

}


