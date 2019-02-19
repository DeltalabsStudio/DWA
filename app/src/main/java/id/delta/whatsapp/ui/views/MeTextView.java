package id.delta.whatsapp.ui.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import id.delta.whatsapp.utils.WaPrefs;

@SuppressLint("AppCompatCustomView")
public class MeTextView extends TextView {
    public MeTextView(Context context) {
        super(context);
        init(context);
    }

    public MeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        try{
            if(getId()==getID(context, "my_name")){
                this.setText(meInfo(context, "push_name"));
            }else if(getId()==getID(context, "my_number")){
                this.append(meInfo(context, "registration_jid"));
            }else if(getId()==getID(context, "my_status")){
                this.setText(meInfo(context, "my_current_status"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String meInfo(Context context, String key){
        return WaPrefs.getString(key, "");
    }

    public int getID(Context context, String nama) {
        return context.getResources().getIdentifier(nama, "id", context.getPackageName());
    }
}
