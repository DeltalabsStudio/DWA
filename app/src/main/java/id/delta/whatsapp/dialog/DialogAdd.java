package id.delta.whatsapp.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.design.widget.c;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.whatsapp.TextStatusComposerActivity;

import id.delta.whatsapp.activities.VideoActivity;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Themes;

import static id.delta.whatsapp.utils.Tools.intId;
import static id.delta.whatsapp.utils.Tools.intLayout;

public class DialogAdd {

    Context context;
    AddListener mAddListener;

    public interface AddListener {
        void onAddListener(String add);
    }

    public DialogAdd(Context context, AddListener addListener){
        this.context = context;
        this.mAddListener = addListener;
    }

    public void show(){
        try{
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View alertLayout = inflater.inflate(intLayout("delta_create_dialog"), null);
            final c mBottomSheetDialog = new c(context, Tools.intStyle("BottomDialog"));
            mBottomSheetDialog.setContentView(alertLayout);

            LinearLayout mContent = (LinearLayout)alertLayout.findViewById(intId("mHolder"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mContent.setBackground(Tools.colorDrawable("delta_dialog_bg", Themes.sheetBackground(), PorterDuff.Mode.SRC_IN));
            }else {
                mContent.setBackgroundDrawable(Tools.colorDrawable("delta_dialog_bg", Themes.sheetBackground(), PorterDuff.Mode.SRC_IN));
            }

            LinearLayout mCustom = (LinearLayout)alertLayout.findViewById(intId("mCustom"));
            LinearLayout mGroup = (LinearLayout)alertLayout.findViewById(intId("mGroup"));
            LinearLayout mBroadcast = (LinearLayout)alertLayout.findViewById(intId("mBroadcast"));
            LinearLayout mStatus = (LinearLayout)alertLayout.findViewById(Tools.intId("mStatus"));

            mCustom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAddListener.onAddListener("custom");
                    mBottomSheetDialog.dismiss();
                }
            });

            mGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAddListener.onAddListener("group");
                    mBottomSheetDialog.dismiss();
                }
            });

            mBroadcast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAddListener.onAddListener("broadcast");
                    mBottomSheetDialog.dismiss();
                }
            });

            mStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBottomSheetDialog.dismiss();
                    context.startActivity(new Intent(context, VideoActivity.class));
                }
            });

            mBottomSheetDialog.show();
        }catch (Exception e){
            Tools.showToast("Error showing dialog, please contact developer");
            e.printStackTrace();
        }
    }
}
