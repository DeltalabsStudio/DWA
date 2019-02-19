package id.delta.whatsapp.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.whatsapp.AppShell;
import com.whatsapp.Conversation;
import com.whatsapp.DialogToastActivity;
import com.whatsapp.ListMembersSelector;
import com.whatsapp.yv;
import com.whatsapp.ThumbnailButton;
import com.whatsapp.contact.b;
import com.whatsapp.contact.a.d;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import id.delta.whatsapp.dialog.DialogAdd;
import id.delta.whatsapp.dialog.DialogCall;
import id.delta.whatsapp.home.content.BottomNavigationView;
import id.delta.whatsapp.home.content.HomeNavigationDrawer;
import id.delta.whatsapp.home.fragments.CallsFragment;
import id.delta.whatsapp.home.fragments.ConversationsFragment;
import id.delta.whatsapp.home.fragments.HomePageFragment;
import id.delta.whatsapp.home.fragments.ProfileFragment;
import id.delta.whatsapp.updater.Info;
import id.delta.whatsapp.updater.Updater;
import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.utils.WaPrefs;
import id.delta.whatsapp.value.Colors;
import id.delta.whatsapp.value.Main;
import id.delta.whatsapp.value.Themes;

import static id.delta.whatsapp.utils.Tools.encryptionMD5;

public class MainActivity extends BaseActivity implements DialogAdd.AddListener, DialogCall.CallListener, Info.onInfo {

    public ConversationsFragment mConversationsFragment;
    public CallsFragment mCallsFragment;
    public HomePageFragment mCurrentFragment;
    public ProfileFragment mProfileFragment;

    public HomeNavigationDrawer mNavigationDrawer;
    public FragmentManager mFragmentManager;
    public Toolbar mToolbar;
    private BottomNavigationView mBottomNavigationView;
    private int mPosisi;
    public Bitmap pictureBitmap;
    public String mInfo;

    @Override
    public void setInfo(String info) {
        if(!info.equals(mInfo)){
            System.exit(0);
        }else {
            mInfo = info;
        }
    }

    public enum Fragment {
        HOME,
        CALLS ,
        PROFILE/*
        SETTING*/
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //Themes.setHomeTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(Tools.intLayout("delta_main_activity"));
        initView();
        initFragment();
        initAdd();
        initUpdater();
        setupInfo();
    }

    private void setupInfo(){
        mInfo = getResources().getString(Tools.intString("deltalabs"));
        Info mInfo = new Info(this, this);
        mInfo.checkInfo();
    }

    private void initUpdater(){
        Updater mUpdater = new Updater(this);
        mUpdater.checkUpdate();
      /*  if(!mUpdater.isUpdate()){
            mUpdater.checkUpdate();
        }*/
    }

    private void initFragment(){
        mConversationsFragment = new ConversationsFragment();
        mCallsFragment         = new CallsFragment();
        mProfileFragment       = new ProfileFragment();

        mFragmentManager  = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(Tools.intId("home_fragment"), mConversationsFragment);
        fragmentTransaction.add(Tools.intId("home_fragment"), mCallsFragment);
        fragmentTransaction.add(Tools.intId("home_fragment"), mProfileFragment);
        mCurrentFragment = mConversationsFragment;
        fragmentTransaction.replace(Tools.intId("home_fragment"), mConversationsFragment);
        fragmentTransaction.commit();
    }

    private void initView(){
        mToolbar = findViewById(Tools.intId("mToolbar"));
        mToolbar.setBackgroundColor(Colors.setWarnaPrimer());
        setToolbar(mToolbar);
        mBottomNavigationView = findViewById(Tools.intId("mNavigationBottom"));
        mNavigationDrawer = findViewById(Tools.intId("drawer"));
        initAvatar();
        ThumbnailButton mAvatar = findViewById(Tools.intId("mAvatar"));
        mAvatar.setImageBitmap(pictureBitmap);
        mAvatar.startAnimation(AnimationUtils.loadAnimation(this, Tools.intAnim("delta_anim_pulse")));
        mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToolbar.setVisibility(View.GONE);
                showFragment(Fragment.PROFILE);
            }
        });
        initTitle();
    }

    private void initTitle(){
        TextView mTitle = findViewById(Tools.intId("mTitle"));
        TextView mSubtitle = findViewById(Tools.intId("mSubtitle"));
        mTitle.setText(WaPrefs.getString("push_name", "WhatsApp"));
        mTitle.setTextColor(Main.titleColor());
        mSubtitle.setTextColor(Main.subtitleColor());
        String status = WaPrefs.getString("my_current_status", "");
        mSubtitle.setText(status);
        if(status.equals("")){
            mSubtitle.setVisibility(View.GONE);
        }else {
            mSubtitle.setVisibility(View.VISIBLE);
        }
        mSubtitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        mSubtitle.setSelected(true);
        mSubtitle.setSingleLine(true);
        mSubtitle.setMarqueeRepeatLimit(-1);
    }

    private void initAvatar(){
        yv.a user = mMeManager.d();
        d picture = d.a();
        pictureBitmap = picture.a(user, 200, -1.0f, false);
        if (pictureBitmap == null) {
            pictureBitmap = b.a().b(user);
        }
    }

    public boolean isPageOpen() {
        return mCurrentFragment.isPageOpen();
    }

    public void showFragment(Fragment fragment) {
        android.support.v4.app.Fragment fragment1 = getHomePageFragment(fragment);

        if (mCurrentFragment == fragment1 && mCurrentFragment.isPageOpen()) {
            mCurrentFragment.getOpenPage().close(true);
            return;
        }

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
      //  transaction.setCustomAnimations(Tools.intAnim( "scale_up"), 0, 0, 0);
        transaction.replace(Tools.intId("home_fragment"), fragment1);
        transaction.commit();

        mBottomNavigationView.setItemActive(fragment);

        mCurrentFragment = (HomePageFragment) fragment1;
        mCurrentFragment.onPageResume();
    }

    private HomePageFragment getHomePageFragment(Fragment fragment) {
        switch (fragment) {
            case HOME:
                return mConversationsFragment;
            case CALLS:
                return mCallsFragment;
            case PROFILE:
                return mProfileFragment;
            default:
                return null;
        }
    }

    private void initAdd(){
      /*  AutoTextView mTitle = findViewById(Tools.intId("mTitle"));
        AutoTextView mSubtitle = findViewById(Tools.intId("mSubtitle"));
        mTitle.setText(WaPrefs.getString("push_name", "WhatsApp"));
        GB.h(this, mTitle);
        String status = WaPrefs.getString("my_current_status", "");
        mSubtitle.setText(status);
        if(status.equals("")){
            mSubtitle.setVisibility(View.GONE);
        }else {
            mSubtitle.setVisibility(View.VISIBLE);
        }
        mSubtitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        mSubtitle.setSelected(true);
        mSubtitle.setSingleLine(true);
        mSubtitle.setMarqueeRepeatLimit(-1);
        Chats.setViewBackground(this);*/
    }

    @Override
    public void onAddListener(String add) {
       /* if(add==0){
            mPosisi = 0;
            requestResult(324);
        }else if(add == 1){
            mPosisi = 1;
            requestResult(324);
        }else if(add == 2){
            startActivity(new Intent(this, ListMembersSelector.class));
        }else if(add == 3){
          //  startActivity(new Intent(this, Dialer.class));
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 324 && resultCode == 324) {
            if (data != null) {
                String jid = data.getStringExtra("a_b");
                if(mPosisi ==0){
                    startActivity(Conversation.a(this, jid));
                }else if(mPosisi == 1){
                    setupCall(jid);
                }
            }
        }
    }

    private void setupCall(String number){
        new DialogCall(this, number, this).show();
    }

    private void requestResult(int requestCode){
      /*  if (GB.IsGB.equals("GB")) {
            Intent i = new Intent(this, ContactPicker.class);
            i.setType("text/plain");
            i.putExtra("sch", 0);
            startActivityForResult(i, requestCode);
            return;
        }*/
        startActivityForResult(new Intent("android.intent.action.PICK", ContactsContract.Contacts.CONTENT_URI), 1);
    }

    public void createDialog(){
        try{
            DialogAdd dialogAdd = new DialogAdd(MainActivity.this, MainActivity.this);
            dialogAdd.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onCallListener(int position, String number) {
       /* switch (position){
            case 0:
                GB.CallDialog(dg.a(), at.a().a(number), MainActivity.this, Integer.valueOf(8), false, false);
                break;
            case 1:
                GB.CallDialog(dg.a(), at.a().a(number), MainActivity.this, Integer.valueOf(8), false, true);
                break;
            case 2:
                try{
                    Intent i = new Intent("android.intent.action.DIAL");
                    i.setData(Uri.parse("tel:+" + number.substring(0, number.lastIndexOf("@"))));
                    startActivity(i);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }*/
    }

}
