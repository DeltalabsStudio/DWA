package id.delta.whatsapp.home.stock;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whatsapp.HomeActivity;
import com.whatsapp.TextStatusComposerActivity;
import com.whatsapp.camera.CameraActivity;

import id.delta.whatsapp.R;
import id.delta.whatsapp.ui.views.CurvedBottom;
import id.delta.whatsapp.ui.views.FloatingActionButton;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Colors;
import id.delta.whatsapp.value.Icons;

public class CurvedNavigationView extends FrameLayout implements ViewTreeObserver.OnGlobalLayoutListener, View.OnClickListener{

    private Context mContext;

    private int [] mImageIds = {Tools.intId("iOne"), Tools.intId("iTwo"), Tools.intId("iThree"), Tools.intId("iMenu"), Tools.intId("iAdd")};
    private int [] mFabIds = {Tools.intId("mFabOne"),Tools.intId("mFabTwo"),Tools.intId("mFabThree"), Tools.intId("mFabMenu"), Tools.intId("mFabAdd")};
    private int [] mLabelIds = {Tools.intId("tOne"),Tools.intId("tTwo"),Tools.intId("tThree"), Tools.intId("tMenu"), Tools.intId("tAdd")};

    public FrameLayout fOne, fTwo, fThree;
    public LinearLayout mOne, mTwo, mThree;
    public CurvedBottom mCurvedBottom;
    public LinearLayout mMenu, mAdd;


    public CurvedNavigationView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CurvedNavigationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CurvedNavigationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mOne){
            onStartSelected();
            if(mContext instanceof HomeActivity){
                ((HomeActivity) mContext).selectPage(1);
            }
        }else if(v == mTwo){
            onCenterSelected();
            if(mContext instanceof HomeActivity){
                ((HomeActivity) mContext).selectPage(2);
            }
        }else if(v == mThree){
            onEndSelected();
            if(mContext instanceof HomeActivity){
                ((HomeActivity) mContext).selectPage(3);
            }
        }else if(v == mMenu){
            if(mContext instanceof HomeActivity){
                ((HomeActivity) mContext).mNavigationDrawer.setOpen(true);
            }
        }else if(v == mAdd){
            if(mContext instanceof HomeActivity){
                ((HomeActivity) mContext).createDialog();
            }
        }else if(v.getId()==mFabIds[1]){
            mContext.startActivity(new Intent(mContext, CameraActivity.class));
        }
    }

    @Override
    public void onGlobalLayout() {
        try{
            for (int i = 0; i < mImageIds.length; i++) {
                final ImageView mImage = (ImageView) findViewById(mImageIds[i]);
                mImage.setColorFilter(Colors.naviconColor(0));

                FloatingActionButton mFab = (FloatingActionButton)findViewById(mFabIds[i]);
                mFab.setOnClickListener(this);

                if(mFab.getId()==mFabIds[1]){
                    mFab.setOnLongClickListener(new OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            mContext.startActivity(new Intent(mContext, TextStatusComposerActivity.class));
                            return false;
                        }
                    });
                }

                TextView mLabel = (TextView)findViewById(mLabelIds[i]);
                mLabel.setTextSize(11.0f);
                mLabel.setTextColor(Colors.naviconColor(0));

                Icons.customIcons(mContext, i, mImage, mFab);

            }
            mCurvedBottom = findViewById(Tools.intId("mCurved"));

            fOne = findViewById(Tools.intId("fOne"));
            fTwo = findViewById(Tools.intId("fTwo"));
            fThree = findViewById(Tools.intId("fThree"));

            mOne = findViewById(Tools.intId("mOne"));
            mTwo = findViewById(Tools.intId("mTwo"));
            mThree = findViewById(Tools.intId("mThree"));

            mMenu = findViewById(Tools.intId("mMenu"));
            mAdd = findViewById(Tools.intId("mAdd"));

            mMenu.setOnClickListener(this);
            mAdd.setOnClickListener(this);
            mOne.setOnClickListener(this);
            mTwo.setOnClickListener(this);
            mThree.setOnClickListener(this);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                getViewTreeObserver().removeOnGlobalLayoutListener(this);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onStartSelected(){
        try{
            mMenu.setVisibility(INVISIBLE);
            mAdd.setVisibility(VISIBLE);

            mCurvedBottom.setPosistion(CurvedBottom.START);
            mOne.setVisibility(INVISIBLE);
            mTwo.setVisibility(VISIBLE);
            mThree.setVisibility(VISIBLE);
            fOne.setVisibility(VISIBLE);
            fTwo.setVisibility(INVISIBLE);
            fThree.setVisibility(INVISIBLE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onCenterSelected(){
        try{
            mMenu.setVisibility(VISIBLE);
            mAdd.setVisibility(VISIBLE);

            mCurvedBottom.setPosistion(CurvedBottom.CENTER);
            mOne.setVisibility(VISIBLE);
            mTwo.setVisibility(INVISIBLE);
            mThree.setVisibility(VISIBLE);
            fOne.setVisibility(INVISIBLE);
            fTwo.setVisibility(VISIBLE);
            fThree.setVisibility(INVISIBLE);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onEndSelected(){
        try{
            mMenu.setVisibility(VISIBLE);
            mAdd.setVisibility(INVISIBLE);

            mCurvedBottom.setPositionEnd();
            mOne.setVisibility(VISIBLE);
            mTwo.setVisibility(VISIBLE);
            mThree.setVisibility(INVISIBLE);
            fOne.setVisibility(INVISIBLE);
            fTwo.setVisibility(INVISIBLE);
            fThree.setVisibility(VISIBLE);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
