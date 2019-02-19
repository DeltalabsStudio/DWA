package id.delta.whatsapp.views.colorpicker;


import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import id.delta.whatsapp.ui.accent.AccentCheckBox;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;


public class ColorCheckPreference extends Preference {
	int value;
	private ImageView mImageView;
	View mView;
	private float mDensity = 0.0f;

	public ColorCheckPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		// setWidgetLayoutResource(Tools.intLayout("delta_color_preference"));
		try {
			updateValues();
			return;
		}
		catch (NumberFormatException var4_4) {
			return;
		}
	}

	@Override
	protected void onBindView(View view) {
		super.onBindView(view);
		this.mView = view;
		showWidget();

	//	View colorView = (View)view.findViewById(Tools.intId("color_view"));
	//	colorView.setBackgroundDrawable(new CircleColorDrawable(value));
	/*	View colorView = (View)view.findViewById(Tools.intId("color_view"));
		colorView.setBackgroundDrawable(new CircleColorDrawable(value));

		CheckBox checkBox =(CheckBox)view.findViewById(Tools.intId("checkbox"));
		checkBox.setChecked(this.getCheckKey());
		this.updateValues();
		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

			public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
				if (bl) {
					ColorCheckPreference.this.checkBoxChecked();
					return;
				}
				ColorCheckPreference.this.checkBoxUnchecked();
			}
		});

		*/

	}

	private void showWidget(){
		if (this.mView == null) {
			return;
		}
		this.mImageView = new ImageView(this.getContext());
		LinearLayout linearLayout = (LinearLayout)mView.findViewById(android.R.id.widget_frame);
		AccentCheckBox checkBox = new AccentCheckBox(this.getContext());
		this.mView.setOnClickListener(new View.OnClickListener(){

			public void onClick(View view) {
				showColorSelector();
			}
		});
		if (linearLayout == null) return;
		linearLayout.setVisibility(View.VISIBLE);
		linearLayout.setPadding(linearLayout.getPaddingLeft(), linearLayout.getPaddingTop(), (int)(8.0f * this.mDensity), linearLayout.getPaddingBottom());
		int n = linearLayout.getChildCount();
		if (n > 0) {
			linearLayout.removeViews(0, n);
		}
		linearLayout.addView((View)this.mImageView);
		linearLayout.setMinimumWidth(0);
		linearLayout.setGravity(Gravity.CENTER_VERTICAL);
		this.mImageView.setImageResource(Tools.intDrawable("delta_ic_circle"));
		this.mImageView.setBackgroundDrawable(new CircleColorDrawable(value));
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);
		checkBox.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
		linearLayout.addView((View)checkBox, 0);
		checkBox.setChecked(this.getCheckKey());
		this.updateValues();
		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

			public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
				if (bl) {
					checkBoxChecked();
					return;
				}
				checkBoxUnchecked();
			}
		});

	}

	private void setCheckKey(boolean bl) {
		String string2 = "";
		if (this.getKey().contains((CharSequence)"picker")) {
			string2 = this.getKey().replace((CharSequence)"_picker", (CharSequence)"_check");
		}
		Prefs.putBoolean(string2, bl);
	}

	private boolean getCheckKey() {
		String string2 = "";
		if (this.getKey().contains((CharSequence)"picker")) {
			string2 = this.getKey().replace((CharSequence)"_picker", (CharSequence)"_check");
		}
		return Prefs.getBoolean(string2, false);
	}

	private void updateValues() {
		if (!this.getCheckKey()) {
			this.setEnabled(false);
			return;
		}
		this.setEnabled(true);
	}
	public void checkBoxChecked() {
		this.setCheckKey(true);
		this.updateValues();
	}

	public void checkBoxUnchecked() {
		this.setCheckKey(false);
		this.updateValues();
	}

	@Override
	public CharSequence getSummary() {
		CharSequence summary = super.getSummary();
		if (summary == null) {
			return null;
		} else {
			return String.format(summary.toString(), String.format("#%08x", value));
		}
	}

	public CharSequence getRawSummary() {
		return super.getSummary();
	}

	public void setValue(int value) {
		if (callChangeListener(value)) {
			persistInt(value);
			notifyChanged();
		}
	}

	@Override
    protected void onClick() {
		showColorSelector();
	}

	private void showColorSelector(){
		new AmbilWarnaDialog(getContext(), value, new AmbilWarnaDialog.OnAmbilWarnaListener() {
			@Override
			public void onOk(AmbilWarnaDialog dialog, int color) {
				if (!callChangeListener(color)) return;
				value = color;
				persistInt(value);
				notifyChanged();

				ColorCheckPreference.this.value = color;
			}

			@Override
			public void onCancel(AmbilWarnaDialog dialog) {}
		}).show();
	}

	public void forceSetValue(int value) {
		this.value = value;
		persistInt(value);
		notifyChanged();
	}

	@Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
		// This preference type's value type is Integer, so we read the default value from the attributes as an Integer.
		return a.getInteger(index, 0);
	}

	@Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
		if (restoreValue) { // Restore state
			value = getPersistedInt(value);
		} else { // Set state
			int value = (Integer) defaultValue;
			this.value = value;
			persistInt(value);
		}
	}

	/*
	 * Suppose a client uses this preference type without persisting. We
	 * must save the instance state so it is able to, for example, survive
	 * orientation changes.
	 */
	@Override
    protected Parcelable onSaveInstanceState() {
		final Parcelable superState = super.onSaveInstanceState();
		if (isPersistent()) return superState; // No need to save instance state since it's persistent

		final SavedState myState = new SavedState(superState);
		myState.value = value;
		return myState;
	}

	@Override
    protected void onRestoreInstanceState(Parcelable state) {
		if (!state.getClass().equals(SavedState.class)) {
			// Didn't save state for us in onSaveInstanceState
			super.onRestoreInstanceState(state);
			return;
		}

		// Restore the instance state
		SavedState myState = (SavedState) state;
		super.onRestoreInstanceState(myState.getSuperState());
		this.value = myState.value;
		notifyChanged();
	}

	/**
	 * SavedState, a subclass of {@link BaseSavedState}, will store the state
	 * of MyPreference, a subclass of Preference.
	 * <p>
	 * It is important to always call through to super methods.
	 */
	private static class SavedState extends BaseSavedState {
		int value;

		public SavedState(Parcel source) {
			super(source);
			value = source.readInt();
		}

		@Override
        public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			dest.writeInt(value);
		}

		public SavedState(Parcelable superState) {
			super(superState);
		}

		@SuppressWarnings("unused") public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}

			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};
	}
}
