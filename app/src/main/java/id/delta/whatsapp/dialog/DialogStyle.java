package id.delta.whatsapp.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.design.widget.c;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whatsapp.Conversation;

import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.FancyText;
import id.delta.whatsapp.value.Themes;

import static id.delta.whatsapp.utils.Tools.intId;
import static id.delta.whatsapp.utils.Tools.intLayout;

public class DialogStyle {

    private Activity mContext;
    private String mSource;
    private StyleListener mStyleListener;

    public interface StyleListener {
        void onStyleSelected(int style, String value);
    }

    public DialogStyle(Activity context, String source, StyleListener onSelected){
        this.mContext = context;
        this.mSource = source;
        this.mStyleListener = onSelected;
    }

    public void show(){
        try{
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View alertLayout = inflater.inflate(intLayout("delta_fancy_dialog"), null);
            final c mBottomSheetDialog = new c(mContext, Tools.intStyle("BottomDialog"));
            mBottomSheetDialog.setContentView(alertLayout);

            LinearLayout mContent = (LinearLayout)alertLayout.findViewById(intId("mHolder"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mContent.setBackground(Tools.colorDrawable("delta_dialog_bg", Themes.sheetBackground(), PorterDuff.Mode.SRC_IN));
            }else {
                mContent.setBackgroundDrawable(Tools.colorDrawable("delta_dialog_bg", Themes.sheetBackground(), PorterDuff.Mode.SRC_IN));
            }
            TextView mFancy = alertLayout.findViewById(Tools.intId("mFancy"));
            mFancy.setTextColor(Themes.themedTextColor());
            final String fancy = FancyText.convertText(mSource, FancyText.styleArray(FancyText.FANCY));
            mFancy.setText(fancy);
            mFancy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mStyleListener.onStyleSelected(FancyText.FANCY, fancy);
                    mBottomSheetDialog.dismiss();
                }
            });

            TextView mJungkel = alertLayout.findViewById(Tools.intId("mJungkel"));
            mJungkel.setTextColor(Themes.themedTextColor());
            final String jungkel = FancyText.convertText(mSource, FancyText.styleArray(FancyText.JUNGKEL));
            mJungkel.setText(jungkel);
            mJungkel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mStyleListener.onStyleSelected(FancyText.JUNGKEL, jungkel);
                    mBottomSheetDialog.dismiss();
                }
            });
            TextView mSquare = alertLayout.findViewById(Tools.intId("mSquare"));
            mSquare.setTextColor(Themes.themedTextColor());
            final String square = FancyText.convertText(mSource, FancyText.styleArray(FancyText.SQUARE));
            mSquare.setText(square);
            mSquare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mStyleListener.onStyleSelected(FancyText.SQUARE, square);
                    mBottomSheetDialog.dismiss();
                }
            });
            TextView mBlack = alertLayout.findViewById(Tools.intId("mBlack"));
            mBlack.setTextColor(Themes.themedTextColor());
            final String black = FancyText.convertText(mSource, FancyText.styleArray(FancyText.BLACKBUBBLE));
            mBlack.setText(black);
            mBlack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mStyleListener.onStyleSelected(FancyText.BLACKBUBBLE, black);
                    mBottomSheetDialog.dismiss();
                }
            });
            TextView mBubble = alertLayout.findViewById(Tools.intId("mBubble"));
            mBubble.setTextColor(Themes.themedTextColor());
            final String bubble = FancyText.convertText(mSource, FancyText.styleArray(FancyText.TEXTBUBBLE));
            mBubble.setText(bubble);
            mBubble.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mStyleListener.onStyleSelected(FancyText.TEXTBUBBLE, bubble);
                    mBottomSheetDialog.dismiss();
                }
            });
            TextView mBig = alertLayout.findViewById(Tools.intId("mBig"));
            mBig.setTextColor(Themes.themedTextColor());
            final String big = FancyText.convertText(mSource, FancyText.styleArray(FancyText.BIG));
            mBig.setText(big);
            mBig.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mStyleListener.onStyleSelected(FancyText.BIG, big);
                    mBottomSheetDialog.dismiss();
                }
            });

            TextView mWhiteBracket = alertLayout.findViewById(Tools.intId("mWhiteBracket"));
            mWhiteBracket.setTextColor(Themes.themedTextColor());
            final String whitebracket = FancyText.convertText(mSource, FancyText.styleArray(FancyText.WHITEBRACKET));
            mWhiteBracket.setText(whitebracket);
            mWhiteBracket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mStyleListener.onStyleSelected(FancyText.WHITEBRACKET, whitebracket);
                    mBottomSheetDialog.dismiss();
                }
            });

            TextView mBlackBracket = alertLayout.findViewById(Tools.intId("mBlackBracket"));
            mBlackBracket.setTextColor(Themes.themedTextColor());
            final String blackbracket = FancyText.convertText(mSource, FancyText.styleArray(FancyText.BLACKBRACKET));
            mBlackBracket.setText(blackbracket);
            mBlackBracket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mStyleListener.onStyleSelected(FancyText.BLACKBRACKET, blackbracket);
                    mBottomSheetDialog.dismiss();
                }
            });

            mBottomSheetDialog.show();
        }catch (Exception e){
            Tools.showToast("Error showing dialog, please contact developer");
            e.printStackTrace();
        }
    }

}
