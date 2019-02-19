package id.delta.whatsapp.home.stock;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.whatsapp.HomeActivity;
import com.whatsapp.TextStatusComposerActivity;
import com.whatsapp.camera.CameraActivity;

import id.delta.whatsapp.ui.views.CurvedBottom;
import id.delta.whatsapp.ui.views.FloatingActionButton;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Icons;

public class CurvedNavigationView extends FrameLayout implements ViewTreeObserver.OnGlobalLayoutListener, View.OnClickListener{

    private Context mContext;

    private int [] mImageIds = {Tools.intId("iOne"), Tools.intId("iTwo"), Tools.intId("iThree")};
    private int [] mFabIds = {Tools.intId("mFabOne"),Tools.intId("mFabTwo"),Tools.intId("mFabThree")};

    public FrameLayout fOne, fTwo, fThree;
    public LinearLayout mOne, mTwo, mThree;
    public CurvedBottom mCurvedBottom;


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
        }else if(v.getId()==mFabIds[1]){
            mContext.startActivity(new Intent(mContext, CameraActivity.class));
        }
    }

    @Override
    public void onGlobalLayout() {
        try{
            for (int i = 0; i < mImageIds.length; i++) {
                final ImageView mImage = (ImageView) findViewById(mImageIds[i]);
                FloatingActionButton mFab = (FloatingActionButton)findViewById(mFabIds[i]);
               // Picasso.with(mContext).load(iconPath+mIconName[i]+fileType).into(mImage);
               // Picasso.with(mContext).load(iconPath+mIconName[i]+fileType).into(mFab);
                Icons.isOwnIcons(mContext, i, mImage, mFab);
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

            }
            mCurvedBottom = findViewById(Tools.intId("mCurved"));

            fOne = findViewById(Tools.intId("fOne"));
            fTwo = findViewById(Tools.intId("fTwo"));
            fThree = findViewById(Tools.intId("fThree"));

            mOne = findViewById(Tools.intId("mOne"));
            mTwo = findViewById(Tools.intId("mTwo"));
            mThree = findViewById(Tools.intId("mThree"));

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
        mCurvedBottom.setPosistion(CurvedBottom.START);
        mOne.setVisibility(INVISIBLE);
        mTwo.setVisibility(VISIBLE);
        mThree.setVisibility(VISIBLE);
        fOne.setVisibility(VISIBLE);
        fTwo.setVisibility(INVISIBLE);
        fThree.setVisibility(INVISIBLE);
    }

    public void onCenterSelected(){
        mCurvedBottom.setPosistion(CurvedBottom.CENTER);
        mOne.setVisibility(VISIBLE);
        mTwo.setVisibility(INVISIBLE);
        mThree.setVisibility(VISIBLE);
        fOne.setVisibility(INVISIBLE);
        fTwo.setVisibility(VISIBLE);
        fThree.setVisibility(INVISIBLE);
    }

    public void onEndSelected(){
        mCurvedBottom.setPositionEnd();
        mOne.setVisibility(VISIBLE);
        mTwo.setVisibility(VISIBLE);
        mThree.setVisibility(INVISIBLE);
        fOne.setVisibility(INVISIBLE);
        fTwo.setVisibility(INVISIBLE);
        fThree.setVisibility(VISIBLE);
    }
}
