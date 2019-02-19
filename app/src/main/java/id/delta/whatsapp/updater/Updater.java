package id.delta.whatsapp.updater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.c;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Themes;

public class Updater {

    private String mUrl = "https://raw.githubusercontent.com/DeltalabsStudio/api/master/mod/deltawa.json"; //Url for Json File
    private String mVersion = "";
    private String mChangelog = "";
    private String mUrlDownload = "";
    private int mVersionCode = 0;
    private Activity mContext;
    private String mInfo = "";
   // private final static String KEY_UPDATER = "key_delta_updater";

    //Harus diganti saat update
    private final static int VERSION_CODE = 2;

    public enum CallBack{
        CANCEL,
        REMIND,
        UPDATE
    }

    public Updater(Activity context){
        this.mContext = context;
    }

    public void checkUpdate(){
        new loadUpdate().execute();
    }

    public void show(String mVersion, String mChangelog, final String mUrlDownload, int mVersionCode){
        try{
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View alertLayout = inflater.inflate(Tools.intLayout("delta_content_updater"), null);
            final c mBottomSheetDialog = new c(mContext, Tools.intStyle("BottomDialog"));
            mBottomSheetDialog.setContentView(alertLayout);
           // mBottomSheetDialog.setCancelable(false);

            LinearLayout mContent = (LinearLayout)alertLayout.findViewById(Tools.intId("mHolder"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mContent.setBackground(Tools.colorDrawable("delta_dialog_bg", Themes.sheetBackground(), PorterDuff.Mode.SRC_IN));
            }else {
                mContent.setBackgroundDrawable(Tools.colorDrawable("delta_dialog_bg", Themes.sheetBackground(), PorterDuff.Mode.SRC_IN));
            }

            TextView mTitle = alertLayout.findViewById(Tools.intId("mVersion"));
            TextView mChangelogText = alertLayout.findViewById(Tools.intId("mChangelog"));
            LinearLayout mCancel = alertLayout.findViewById(Tools.intId("mCancelButton"));
            LinearLayout mRemind = alertLayout.findViewById(Tools.intId("mRemindButton"));
            LinearLayout mUpdate = alertLayout.findViewById(Tools.intId("mUpdateButton"));

            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Prefs.putString(KEY_UPDATER, String.valueOf(CallBack.CANCEL));
                    mBottomSheetDialog.dismiss();
                }
            });
            mUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startBrowserIntent(mUrlDownload);
                    mBottomSheetDialog.dismiss();
                }
            });
            mRemind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Prefs.putString(KEY_UPDATER, String.valueOf(CallBack.REMIND));
                    mBottomSheetDialog.dismiss();
                }
            });

            mTitle.setText(mVersion);
            mChangelogText.setText(mChangelog);

            if(mVersionCode != VERSION_CODE){
                mBottomSheetDialog.show();
            }
        }catch (Exception e){
            Tools.showToast("error");
            e.printStackTrace();
        }
    }

    private void startBrowserIntent(final String baseUrl) {
        try{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(baseUrl));
            mContext.startActivity(browserIntent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void startBrowser(final String baseUrl, Activity activity) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(baseUrl));
        activity.startActivity(browserIntent);
    }

    public class loadUpdate extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(mUrl);
            if (jsonStr != null){
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    mVersion = jsonObj.getString("version");
                    mUrlDownload = jsonObj.getString("url_download");
                    mChangelog = jsonObj.getString("changelog");
                    mVersionCode = jsonObj.getInt("version_code");
                }catch (final JSONException e){
                   // Tools.showToast("Error");
                }
            }else {
               /* mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // Tools.showToast("null");
                    }
                });*/
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(mVersionCode!=0){
                show(mVersion, mChangelog, mUrlDownload, mVersionCode);
            }
        }
    }

    public boolean isUpdate(){
        return mContext.getSharedPreferences(mContext.getPackageName()+"_preferences", Context.MODE_PRIVATE).getBoolean("disable_checking_updates", false);
    }


}
