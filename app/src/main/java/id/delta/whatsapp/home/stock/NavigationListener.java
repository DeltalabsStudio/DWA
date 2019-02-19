package id.delta.whatsapp.home.stock;

import android.view.View;

public class NavigationListener implements View.OnClickListener {
    NavigationDrawer mNavigationDrawer;

    public NavigationListener(NavigationDrawer mNavigationDrawer){
        this.mNavigationDrawer = mNavigationDrawer;
    }

    @Override
    public void onClick(View v) {
        mNavigationDrawer.setOpen(true);
    }
}
