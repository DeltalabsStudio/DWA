package id.delta.whatsapp.dialog;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.design.widget.c;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whatsapp.data.ft;
import com.whatsapp.data.at;

import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Themes;

import static id.delta.whatsapp.utils.Tools.intId;
import static id.delta.whatsapp.utils.Tools.intLayout;

public class DialogCall {

    Context context;
    CallListener mCallListener;
    String number;

    public interface CallListener {
        void onCallListener(int position, String jid);
    }

    public DialogCall(Context context, String number, CallListener mCallListener){
        this.context = context;
        this.mCallListener = mCallListener;
        this.number = number;
    }

    public void show(){
        try{
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View alertLayout = inflater.inflate(intLayout("delta_call_content"), null);
            final c mBottomSheetDialog = new c(context, Tools.intStyle("NewDialog"));
            mBottomSheetDialog.setContentView(alertLayout);

            LinearLayout mContent = (LinearLayout)alertLayout.findViewById(intId("mHolder"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mContent.setBackground(Tools.colorDrawable("delta_dialog_add", Themes.sheetBackground(), PorterDuff.Mode.SRC_IN));
            }else {
                mContent.setBackgroundDrawable(Tools.colorDrawable("delta_dialog_add", Themes.sheetBackground(), PorterDuff.Mode.SRC_IN));
            }

            TextView mTitle = (TextView)alertLayout.findViewById(intId("mTitle"));
           // mTitle.setText(mContext.getResources().getString(Tools.intString("title_call"), number));
            setName(mTitle, number);
            LinearLayout mChat = (LinearLayout)alertLayout.findViewById(intId("mAudioCall"));
            LinearLayout mCall = (LinearLayout)alertLayout.findViewById(intId("mVideoCall"));
            LinearLayout mBroadcast = (LinearLayout)alertLayout.findViewById(intId("mPhoneCall"));

            mChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallListener.onCallListener(0, number);
                    mBottomSheetDialog.dismiss();
                }
            });

            mCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallListener.onCallListener(1, number);
                    mBottomSheetDialog.dismiss();
                }
            });

            mBroadcast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallListener.onCallListener(2, number);
                    mBottomSheetDialog.dismiss();
                }
            });

            mBottomSheetDialog.show();
        }catch (Exception e){
            Tools.showToast("Error showing dialog, please contact developer");
            e.printStackTrace();
        }
    }

    private void setName(TextView mName, String mJid){
        ft contactInfo = at.a().b(mJid);
        mName.setText(context.getResources().getString(Tools.intString("title_call"), contactInfo.d));
    }
}
