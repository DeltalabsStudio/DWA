package id.delta.whatsapp.value;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import id.delta.whatsapp.activities.SettingsActivity;
import id.delta.whatsapp.dialog.DialogDnd;
import id.delta.whatsapp.utils.Actions;
import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;

import static android.graphics.PorterDuff.Mode.SRC_IN;

public class Dnd {

    public static int DNDMENU = 100;
    public static int DELTAMENU = 200;

    public static void addMenu(Menu menu){
        try{
            if(Prefs.getBoolean(Keys.KEY_DND_MENU, false)){
                menu.add(0, DNDMENU, Menu.NONE, Tools.intString("menu_dnd")).setIcon(Dnd.dndIcon()).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            }
            menu.add(0, DELTAMENU, Menu.NONE, Tools.intString("delta_settings")).setIcon(Tools.colorDrawable("delta_ic_settings",  Colors.warnaAutoTitle(), SRC_IN)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean disableInternet() {
        return Prefs.getBoolean(Keys.KEY_DISABLE_INTERNET, false);
    }

    public static Drawable dndIcon(){
        if(Prefs.getBoolean(Keys.KEY_DISABLE_INTERNET, false)){
            return Tools.colorDrawable("delta_ic_offline", Colors.warnaAutoTitle(), SRC_IN);
        }else {
            return Tools.colorDrawable("delta_ic_online", Colors.warnaAutoTitle(), SRC_IN);
        }
    }

    public static void onDndClicked(Activity activity, MenuItem item){
        try{
            if(item.getItemId()==DNDMENU){
                if(Prefs.getBoolean(Keys.KEY_DISABLE_INTERNET, false)){
                    Prefs.putBoolean(Keys.KEY_DISABLE_INTERNET, false);
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Actions.restartApp();
                        }
                    }, 200);
                }else {
                    DialogDnd dnd = new DialogDnd(activity);
                    dnd.show();
                }
            }else if(item.getItemId()==DELTAMENU){
                Actions.startSettings(activity, SettingsActivity.STOCK);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void dndHeader(Activity activity, final ListView listView) {
        if(Prefs.getBoolean(Keys.KEY_DISABLE_INTERNET, false)){
            try {
                final View headerView = activity.getLayoutInflater().inflate(Tools.intLayout("delta_dnd_listheader"), null, false);
                headerView.setBackgroundColor(Colors.setWarnaAksen());
                headerView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        listView.removeHeaderView(headerView);
                    }
                });
                listView.addHeaderView(headerView, null, false);
            } catch (Exception e) {
            }
        }
    }
}
