package id.delta.whatsapp.dialog;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.c;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import id.delta.whatsapp.R;
import id.delta.whatsapp.utils.Tools;


public class DialogSelect {

    Context context;
    SelectedListener mSelectedListener;

    public interface SelectedListener {
        void onSelected(int id);
    }

    public DialogSelect(Context context, SelectedListener mSelectedListener){
        this.context = context;
        this.mSelectedListener = mSelectedListener;
    }

    public void show(){
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View alertLayout = inflater.inflate(Tools.intLayout("delta_select_dialog"), null);
            final c mBottomSheetDialog = new c(context, Tools.intStyle("BottomDialog"));
            mBottomSheetDialog.setContentView(alertLayout);

            LinearLayout mSplit = alertLayout.findViewById(Tools.intId("mSplit"));
            mSplit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedListener.onSelected(Tools.intId("mSplit"));
                    mBottomSheetDialog.dismiss();
                }
            });

            LinearLayout mTrim = alertLayout.findViewById(Tools.intId("mTrim"));
            mTrim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedListener.onSelected(Tools.intId("mTrim"));
                    mBottomSheetDialog.dismiss();
                }
            });

            mBottomSheetDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
