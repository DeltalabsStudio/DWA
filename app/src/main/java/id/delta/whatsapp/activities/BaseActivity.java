package id.delta.whatsapp.activities;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.whatsapp.DialogToastActivity;

import id.delta.whatsapp.implement.ToolbarInterfaces;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Colors;
import id.delta.whatsapp.value.Themes;

public class BaseActivity extends DialogToastActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Themes.setHomeTheme(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        Themes.setStatusBarView(this, Colors.alphaColor(Colors.setWarnaPrimer(), Themes.setStatusBarAlpha()));
        super.onResume();
    }

    public void setToolbar(Toolbar mToolbar){
        if(mToolbar!=null){
            setSupportActionBar(mToolbar);
            mToolbar.setBackgroundColor(Colors.setWarnaPrimer());
            mToolbar.setTitleTextColor(Colors.warnaAutoTitle());
            mToolbar.setNavigationIcon(Tools.colorDrawable("ic_back_white", Colors.warnaAutoTitle(), PorterDuff.Mode.SRC_IN));
            mToolbar.setOverflowIcon(Tools.colorDrawable("ic_more_white", Colors.warnaAutoTitle(), PorterDuff.Mode.SRC_IN));
            mToolbar.setNavigationOnClickListener(new ToolbarInterfaces(this));
        }
    }
}
