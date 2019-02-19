package id.delta.whatsapp.value;

import android.content.Context;
import android.os.Environment;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

import id.delta.whatsapp.ui.views.FloatingActionButton;
import id.delta.whatsapp.utils.AssetCopier;
import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;

public class Icons {

    private static int [] mImageResource = {Tools.intDrawable("ic_action_compose"), Tools.intDrawable("ic_camera_status_compose"), Tools.intDrawable("ic_action_call")};

    private static String [] mIconName = {"ic_chats","ic_cameras","ic_calls"};

    private static String iconAssets= "file:///android_asset/icons/";

    private static String iconPath = "WhatsApp/DELTABackup/icons/";

    private static String fileType = ".png";

    public static void copyIcons(Context context){
       // int count = -1;
        try{
            if (!new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup").exists()){
                new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup").mkdir();
            }
            if (!new File(Environment.getExternalStorageDirectory(), iconPath).exists()){
                new File(Environment.getExternalStorageDirectory(), iconPath).mkdir();
            }
            new AssetCopier(context).withFileScanning().copy("icons", new File(Environment.getExternalStorageDirectory(), iconPath));
            //  count = new AssetCopier(context).withFileScanning().copy("icons", new File(Environment.getExternalStorageDirectory(), iconPath));
            Prefs.putBoolean(Keys.KEY_CUSTOM_ICON_COPYED, true);
        }catch (Exception e){
            e.printStackTrace();
            Tools.showToast("Can't find Icons folder!");
        }
       // Tools.showToast((count==-1 ? "There was an error copying" : "files copied " + count));
    }

    public static void customIcons(Context mContext, int i, ImageView mImage, FloatingActionButton mFab){
        if(!new File(Environment.getExternalStorageDirectory(), iconPath+mIconName[i]+fileType).exists()){
            copyIcons(mContext);
            Picasso.with(mContext).load(mImageResource[i]).into(mImage);
            Picasso.with(mContext).load(mImageResource[i]).into(mFab);
        }
        if(Prefs.getBoolean(Keys.KEY_CUSTOM_ICON, false)){
            Picasso.with(mContext).load(new File(Environment.getExternalStorageDirectory(), iconPath+mIconName[i]+fileType)).into(mImage);
            Picasso.with(mContext).load(new File(Environment.getExternalStorageDirectory(), iconPath+mIconName[i]+fileType)).into(mFab);
        }else {
           /* Picasso.with(mContext).load(iconAssets+mIconName[i]+fileType).into(mImage);
            Picasso.with(mContext).load(iconAssets+mIconName[i]+fileType).into(mFab);
            */
            Picasso.with(mContext).load(mImageResource[i]).into(mImage);
            Picasso.with(mContext).load(mImageResource[i]).into(mFab);
        }
    }

}


