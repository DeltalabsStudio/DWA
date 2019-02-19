package id.delta.whatsapp.dialog;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.c;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.whatsapp.HomeActivity;

import id.delta.whatsapp.ui.accent.AccentImageView;
import id.delta.whatsapp.utils.Actions;
import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Colors;
import id.delta.whatsapp.value.Themes;

import static id.delta.whatsapp.utils.Tools.intId;
import static id.delta.whatsapp.utils.Tools.intLayout;

public class DialogDnd {

    Context context;

    public DialogDnd(Context context){
        this.context = context;
    }

    public void show(){
        try{
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View alertLayout = inflater.inflate(intLayout("delta_dnd_dialog"), null);
            final c mBottomSheetDialog = new c(context, Tools.intStyle("BottomDialog"));
            mBottomSheetDialog.setContentView(alertLayout);

            LinearLayout mContent = (LinearLayout)alertLayout.findViewById(intId("mHolder"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mContent.setBackground(Tools.colorDrawable("delta_dialog_bg", Themes.sheetBackground(), PorterDuff.Mode.SRC_IN));
            }else {
                mContent.setBackgroundDrawable(Tools.colorDrawable("delta_dialog_bg", Themes.sheetBackground(), PorterDuff.Mode.SRC_IN));
            }

            AccentImageView mIcon = alertLayout.findViewById(Tools.intId("mIcon"));
            mIcon.startAnimation(AnimationUtils.loadAnimation(context, Tools.intAnim("delta_anim_pulse")));

            Button mCancel = alertLayout.findViewById(Tools.intId("mDisable"));
            mCancel.setTextColor(Colors.setWarnaAksen());
            mCancel.setOnClickListener(new DndListener("mDisable", mBottomSheetDialog));

            Button mEnable = alertLayout.findViewById(Tools.intId("mEnable"));
            Drawable mBackground = mEnable.getBackground();
            mBackground.setColorFilter(Colors.setWarnaAksen(), PorterDuff.Mode.MULTIPLY);
            mEnable.setBackgroundDrawable(mBackground);
            mEnable.setOnClickListener(new DndListener("mEnable", mBottomSheetDialog));

            mBottomSheetDialog.show();
        }catch (Exception e){
            Tools.showToast("Error showing dialog, please contact developer");
            e.printStackTrace();
        }
    }

    public class DndListener implements View.OnClickListener{
        String id;
        c mBottoomDialog;

        public DndListener(String id, c mBootomDialog){
            this.id = id;
            this.mBottoomDialog = mBootomDialog;
        }

        @Override
        public void onClick(View v) {

            if(id.equals("mEnable")){
                Prefs.putBoolean(Keys.KEY_DISABLE_INTERNET, true);
                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Actions.restartApp();
                    }
                }, 200);
            }
            mBottoomDialog.dismiss();
        }
    }
}
