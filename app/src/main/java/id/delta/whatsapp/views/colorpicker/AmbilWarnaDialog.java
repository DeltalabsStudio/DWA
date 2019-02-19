package id.delta.whatsapp.views.colorpicker;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Colors;
import id.delta.whatsapp.views.palette.ColorPickerPalette;
import id.delta.whatsapp.views.palette.ColorPickerSwatch;
import id.delta.whatsapp.views.palette.Palette;


public class AmbilWarnaDialog {

	public interface OnAmbilWarnaListener {
		void onCancel(AmbilWarnaDialog dialog);

		void onOk(AmbilWarnaDialog dialog, int color);
	}

	final AlertDialog dialog;
	private final boolean supportsAlpha;
	final OnAmbilWarnaListener listener;
	final View viewHue;
	final AmbilWarnaSquare viewSatVal;
	final ImageView viewCursor;
	final ImageView viewAlphaCursor;
	final View viewOldColor;
	final View viewNewColor;
	final View viewAlphaOverlay;
	final EditText viewInput;
	final ImageView viewTarget;
	final ImageView viewAlphaCheckered;
	final ViewGroup viewContainer;

	final ColorPickerPalette mPallete;
	int warnaTerpilih;

	final float[] currentColorHsv = new float[3];
	int alpha;

	public AmbilWarnaDialog(final Context context, int color, OnAmbilWarnaListener listener) {
		this(context, color, true, listener);
	}

	@SuppressLint("ClickableViewAccessibility")
	public AmbilWarnaDialog(final Context context, int color, boolean supportsAlpha, OnAmbilWarnaListener listener) {
		this.supportsAlpha = supportsAlpha;
		this.listener = listener;

		if (!supportsAlpha) { // remove alpha if not supported
			color = color | 0xff000000;
		}

		Color.colorToHSV(color, currentColorHsv);
		alpha = Color.alpha(color);

		final View view = LayoutInflater.from(context).inflate(Tools.intLayout("delta_aw_dialog"), null);
		viewHue = view.findViewById(Tools.intId("ambilwarna_viewHue"));
		viewSatVal = (AmbilWarnaSquare) view.findViewById(Tools.intId("ambilwarna_viewSatBri"));
		viewCursor = (ImageView) view.findViewById(Tools.intId("ambilwarna_cursor"));
		viewOldColor = view.findViewById(Tools.intId("ambilwarna_oldColor"));
		viewNewColor = view.findViewById(Tools.intId("ambilwarna_newColor"));
		viewTarget = (ImageView) view.findViewById(Tools.intId("ambilwarna_target"));
		viewContainer = (ViewGroup) view.findViewById(Tools.intId("ambilwarna_viewContainer"));
		viewAlphaOverlay = view.findViewById(Tools.intId("ambilwarna_overlay"));
		viewAlphaCursor = (ImageView) view.findViewById(Tools.intId("ambilwarna_alphaCursor"));
		viewAlphaCheckered = (ImageView) view.findViewById(Tools.intId("ambilwarna_alphaCheckered"));
		viewInput = (EditText)view.findViewById(Tools.intId("input_warna"));

		final LinearLayout mTabView = (LinearLayout)view.findViewById(Tools.intId("mTabView"));
		final View argbView = (View)view.findViewById(Tools.intId("argbView"));
		final View palleteView = (View)view.findViewById(Tools.intId("palleteView"));
		final TextView tPalette = (TextView)view.findViewById(Tools.intId("tPalette"));
		final TextView tArgb = (TextView)view.findViewById(Tools.intId("tArgb"));
		final View mPalleteView = (View) view.findViewById(Tools.intId("mPalleteView"));
		final FrameLayout mPalleteBtn = (FrameLayout)view.findViewById(Tools.intId("mBtnPallete"));
		final LinearLayout mArgbView = (LinearLayout)view.findViewById(Tools.intId("mArgbView"));
		final FrameLayout mArgbBtn = (FrameLayout)view.findViewById(Tools.intId("mBtnArgb"));
		mPalleteBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mPalleteView.setVisibility(View.VISIBLE);
				mArgbView.setVisibility(View.GONE);
				argbView.setVisibility(View.GONE);
				palleteView.setVisibility(View.VISIBLE);
			}
		});
		mArgbBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mPalleteView.setVisibility(View.GONE);
				mArgbView.setVisibility(View.VISIBLE);
				palleteView.setVisibility(View.GONE);
				argbView.setVisibility(View.VISIBLE);
			}
		});


		{ // hide/show alpha
			viewAlphaOverlay.setVisibility(supportsAlpha? View.VISIBLE: View.GONE);
			viewAlphaCursor.setVisibility(supportsAlpha? View.VISIBLE: View.GONE);
			viewAlphaCheckered.setVisibility(supportsAlpha? View.VISIBLE: View.GONE);
		}

		TextWatcher textWatcher = new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				updateColor();
			}

			private void updateColor() {
				if (isValidColor(viewInput.getText().toString()) == true){
					final String colourValue = viewInput.getText().toString();
					int colour = getColorFromString(colourValue);
					viewNewColor.setBackgroundDrawable(new CircleColorDrawable(colour));
					warnaTerpilih = colour;
					mTabView.setBackgroundColor(colour);
					if(Colors.isDarken(colour)){
						palleteView.setBackgroundColor(0x80ffffff);
						argbView.setBackgroundColor(0x80ffffff);
						tArgb.setTextColor(Color.WHITE);
						tPalette.setTextColor(Color.WHITE);
					}else {
						palleteView.setBackgroundColor(0x40000000);
						argbView.setBackgroundColor(0x40000000);
						tArgb.setTextColor(Color.BLACK);
						tPalette.setTextColor(Color.BLACK);
					}

				}

			}

		};

		viewInput.addTextChangedListener(textWatcher);
		viewInput.setText(String.format("#%08x", color));

		viewSatVal.setHue(getHue());
		viewOldColor.setBackgroundDrawable(new CircleColorDrawable(color));
		viewNewColor.setBackgroundDrawable(new CircleColorDrawable(color));
		mTabView.setBackgroundColor(color);

        //Penambahan Palette Metode
		mPallete = (ColorPickerPalette)view.findViewById(Tools.intId("mPallete"));
		int swatchColor = Palette.getSwatchColor(getColor());
		mPallete.init(19, 4, new ColorPickerSwatch.OnColorSelectedListener() {
			@Override
			public void onColorSelected(int color) {
				mPallete.init(Palette.getSwatch(color).length, 4, new ColorPickerSwatch.OnColorSelectedListener() {
					@Override
					public void onColorSelected(int color) {
						warnaTerpilih = color;
						viewNewColor.setBackgroundDrawable(new CircleColorDrawable(color));
						viewInput.setText(String.format("#%08x", color));
						mTabView.setBackgroundColor(color);

						if (color != warnaTerpilih) {
							warnaTerpilih = color;
							mPallete.drawPalette(Palette.PALETTE, warnaTerpilih);
						}
					}
				});
				mPallete.drawPalette(Palette.getSwatch(color), warnaTerpilih);
				warnaTerpilih = color;
				viewNewColor.setBackgroundDrawable(new CircleColorDrawable(color));
			}
		});
		mPallete.drawPalette(Palette.PALETTE, swatchColor);



		viewHue.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_MOVE
						|| event.getAction() == MotionEvent.ACTION_DOWN
						|| event.getAction() == MotionEvent.ACTION_UP) {

						float y = event.getY();
						if (y < 0.f) y = 0.f;
						if (y > viewHue.getMeasuredHeight()) {
							y = viewHue.getMeasuredHeight() - 0.001f; // to avoid jumping the cursor from bottom to top.
						}
						float hue = 360.f - 360.f / viewHue.getMeasuredHeight() * y;
						if (hue == 360.f) hue = 0.f;
						setHue(hue);

						// update view
						viewSatVal.setHue(getHue());
						moveCursor();
						viewNewColor.setBackgroundDrawable(new CircleColorDrawable(getColor()));
						mTabView.setBackgroundColor(getColor());
						viewInput.setText(String.format("#%08x", getColor()));
						updateAlphaView();
						return true;
					}
					return false;
				}
			});

		if (supportsAlpha) viewAlphaCheckered.setOnTouchListener(new View.OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						if ((event.getAction() == MotionEvent.ACTION_MOVE)
							|| (event.getAction() == MotionEvent.ACTION_DOWN)
							|| (event.getAction() == MotionEvent.ACTION_UP)) {

							float y = event.getY();
							if (y < 0.f) {
								y = 0.f;
							}
							if (y > viewAlphaCheckered.getMeasuredHeight()) {
								y = viewAlphaCheckered.getMeasuredHeight() - 0.001f; // to avoid jumping the cursor from bottom to top.
							}
							final int a = Math.round(255.f - ((255.f / viewAlphaCheckered.getMeasuredHeight()) * y));
							AmbilWarnaDialog.this.setAlpha(a);

							// update view
							moveAlphaCursor();
							int col = AmbilWarnaDialog.this.getColor();
							int c = a << 24 | col & 0x00ffffff;
							viewNewColor.setBackgroundDrawable(new CircleColorDrawable(c));
							mTabView.setBackgroundColor(c);
							viewInput.setText(String.format("#%08x", c));
							return true;
						}
						return false;
					}
				});
		viewSatVal.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_MOVE
						|| event.getAction() == MotionEvent.ACTION_DOWN
						|| event.getAction() == MotionEvent.ACTION_UP) {

						float x = event.getX(); // touch event are in dp units.
						float y = event.getY();

						if (x < 0.f) x = 0.f;
						if (x > viewSatVal.getMeasuredWidth()) x = viewSatVal.getMeasuredWidth();
						if (y < 0.f) y = 0.f;
						if (y > viewSatVal.getMeasuredHeight()) y = viewSatVal.getMeasuredHeight();

						setSat(1.f / viewSatVal.getMeasuredWidth() * x);
						setVal(1.f - (1.f / viewSatVal.getMeasuredHeight() * y));

						// update view
						moveTarget();
						viewNewColor.setBackgroundDrawable(new CircleColorDrawable(getColor()));
						mTabView.setBackgroundColor(getColor());
						viewInput.setText(String.format("#%08x",getColor()));

						return true;
					}
					return false;
				}
			});

		dialog = new AlertDialog.Builder(context)
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (AmbilWarnaDialog.this.listener != null) {
						AmbilWarnaDialog.this.listener.onOk(AmbilWarnaDialog.this, warnaTerpilih);
					}
				}
			})
			.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (AmbilWarnaDialog.this.listener != null) {
						AmbilWarnaDialog.this.listener.onCancel(AmbilWarnaDialog.this);
					}
				}
			})
			.setOnCancelListener(new OnCancelListener() {
				// if back button is used, call back our listener.
				@Override
				public void onCancel(DialogInterface paramDialogInterface) {
					if (AmbilWarnaDialog.this.listener != null) {
						AmbilWarnaDialog.this.listener.onCancel(AmbilWarnaDialog.this);
					}

				}
			})
			.create();
		// kill all padding from the dialog window
		dialog.setView(view, 0, 0, 0, 0);

		// move cursor & target on first draw
		ViewTreeObserver vto = view.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					moveCursor();
					if (AmbilWarnaDialog.this.supportsAlpha) moveAlphaCursor();
					moveTarget();
					if (AmbilWarnaDialog.this.supportsAlpha) updateAlphaView();
					view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				}
			});
	}

	private static int getColorFromString(String str) {
		try {
			return Color.parseColor(str);
		} catch (Exception e1) {
			try {
				return Color.parseColor("#" + str);
			} catch (Exception e2) {
			}
		}
		return 0;
	}


	private static boolean isValidColor(String str) {
		try {
			Color.parseColor(str);
			return true;
		} catch (Exception e1) {
			try {
				Color.parseColor("#" + str);
				return true;
			} catch (Exception e2) {
			}
		}
		return false;
	}

	protected void moveCursor() {
		float y = viewHue.getMeasuredHeight() - (getHue() * viewHue.getMeasuredHeight() / 360.f);
		if (y == viewHue.getMeasuredHeight()) y = 0.f;
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewCursor.getLayoutParams();
		layoutParams.leftMargin = (int) (viewHue.getLeft() - Math.floor(viewCursor.getMeasuredWidth() / 2) - viewContainer.getPaddingLeft());
		layoutParams.topMargin = (int) (viewHue.getTop() + y - Math.floor(viewCursor.getMeasuredHeight() / 2) - viewContainer.getPaddingTop());
		viewCursor.setLayoutParams(layoutParams);
	}

	protected void moveTarget() {
		float x = getSat() * viewSatVal.getMeasuredWidth();
		float y = (1.f - getVal()) * viewSatVal.getMeasuredHeight();
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewTarget.getLayoutParams();
		layoutParams.leftMargin = (int) (viewSatVal.getLeft() + x - Math.floor(viewTarget.getMeasuredWidth() / 2) - viewContainer.getPaddingLeft());
		layoutParams.topMargin = (int) (viewSatVal.getTop() + y - Math.floor(viewTarget.getMeasuredHeight() / 2) - viewContainer.getPaddingTop());

		viewTarget.setLayoutParams(layoutParams);
	}

	protected void moveAlphaCursor() {
		final int measuredHeight = this.viewAlphaCheckered.getMeasuredHeight();
		float y = measuredHeight - ((this.getAlpha() * measuredHeight) / 255.f);
		final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.viewAlphaCursor.getLayoutParams();
		layoutParams.leftMargin = (int) (this.viewAlphaCheckered.getLeft() - Math.floor(this.viewAlphaCursor.getMeasuredWidth() / 2) - this.viewContainer.getPaddingLeft());
		layoutParams.topMargin = (int) ((this.viewAlphaCheckered.getTop() + y) - Math.floor(this.viewAlphaCursor.getMeasuredHeight() / 2) - this.viewContainer.getPaddingTop());

		this.viewAlphaCursor.setLayoutParams(layoutParams);
	}

	private int getColor() {
		final int argb = Color.HSVToColor(currentColorHsv);
		int color = alpha << 24 | (argb & 0x00ffffff);
		warnaTerpilih = color;
		return warnaTerpilih;
	}

	private int getColor2(){
		final String value = viewInput.getText().toString();
		final int hex = getColorFromString(value);
		int color = alpha << 24 | (hex & 0x00ffffff);
		warnaTerpilih = color;
		return warnaTerpilih;
	}

	private float getHue() {
		return currentColorHsv[0];
	}

	private float getAlpha() {
		return this.alpha;
	}

	private float getSat() {
		return currentColorHsv[1];
	}

	private float getVal() {
		return currentColorHsv[2];
	}

	private void setHue(float hue) {
		currentColorHsv[0] = hue;
	}

	private void setSat(float sat) {
		currentColorHsv[1] = sat;
	}

	private void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	private void setVal(float val) {
		currentColorHsv[2] = val;
	}

	public void show() {
		dialog.show();
	}

	public AlertDialog getDialog() {
		return dialog;
	}

	private void updateAlphaView() {
		final GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
															 Color.HSVToColor(currentColorHsv), 0x0
														 });
		viewAlphaOverlay.setBackgroundDrawable(gd);
	}


}
