package id.delta.whatsapp.implement;

import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.c;
import android.view.View;

import com.whatsapp.Conversation;
import com.whatsapp.MentionableEntry;
import com.whatsapp.TextStatusComposerActivity;

import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.views.colorpicker.AmbilWarnaDialog;

public class OnFancySelected implements View.OnClickListener {
    private String entry;
    private Conversation activity;
    private c mBottomSheetDialog;

    public OnFancySelected(Conversation activity, String entry, c mBottomSheet){
        this.activity = activity;
        this.entry = entry;
        this.mBottomSheetDialog = mBottomSheet;
    }

    @Override
    public void onClick(View v) {
        MentionableEntry mEntry = activity.findViewById(Tools.intId("entry"));
        mEntry.setText(entry);
        mBottomSheetDialog.dismiss();
    }
}
