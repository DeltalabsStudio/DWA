package id.delta.whatsapp.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.design.widget.c;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;
import com.whatsapp.DialogToastActivity;
import com.whatsapp.HomeActivity;
import com.whatsapp.Me;
import com.whatsapp.ThumbnailButton;

import java.io.File;

import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Colors;
import id.delta.whatsapp.value.Themes;
import id.delta.whatsapp.value.Wallpaper;

import static id.delta.whatsapp.utils.Tools.intId;
import static id.delta.whatsapp.utils.Tools.intLayout;

public class DialogAbout {

    Activity context;

    public DialogAbout(Activity context){
        this.context = context;
    }

    public void show(){
        try{
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View alertLayout = inflater.inflate(intLayout("delta_about_dialog"), null);
            final c mBottomSheetDialog = new c(context, Tools.intStyle("BottomDialog"));
            mBottomSheetDialog.setContentView(alertLayout);
            View mHolder = alertLayout.findViewById(Tools.intId("mHolder"));
            mHolder.setBackgroundColor(Themes.sheetBackground());

            ThumbnailButton mProfil = alertLayout.findViewById(Tools.intId("mProfile"));
            TextView mInfo = alertLayout.findViewById(Tools.intId("mInfo"));
            if(context instanceof HomeActivity){
                mProfil.setImageBitmap(((HomeActivity) context).pictureBitmap);
                mInfo.setText(DialogToastActivity.c(DialogToastActivity.Me));
            }

            KenBurnsView mCover = alertLayout.findViewById(Tools.intId("mCover"));
            try{
                File coverPath = new File(Wallpaper.coverPath);
                if(coverPath.exists()){
                    Picasso.with(context).load(coverPath).into(mCover);
                }else {
                    Picasso.with(context).load(Tools.intDrawable("cover")).into(mCover);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            mBottomSheetDialog.show();
        }catch (Exception e){
            Tools.showToast("Error showing dialog, please contact developer");
            e.printStackTrace();
        }
    }
}
