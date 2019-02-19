package id.delta.whatsapp.updater;

import android.app.Activity;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

public class Info {

    private Activity mContext;
    private String mInfo = "";
    private onInfo getInfo;

    public interface onInfo {
        void setInfo(String info);

    }

    public Info(Activity context, onInfo getInfo){
        this.mContext = context;
        this.getInfo = getInfo;
    }

    public class loadInfo extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall("https://raw.githubusercontent.com/DeltalabsStudio/api/master/mod/deltainfo.json");
            if (jsonStr != null){
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    mInfo = jsonObj.getString("modder");
                }catch (final JSONException e){
                    // Tools.showToast("Error");
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(!mInfo.equals("")){
                getInfo.setInfo(mInfo);
            }
        }
    }

    public void checkInfo(){
        new loadInfo().execute();
    }
}
