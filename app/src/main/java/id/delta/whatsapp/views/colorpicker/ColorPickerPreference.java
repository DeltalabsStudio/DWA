package id.delta.whatsapp.views.colorpicker;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import id.delta.whatsapp.utils.Tools;


public class ColorPickerPreference extends Preference {
	int value;

	public ColorPickerPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		setWidgetLayoutResource(Tools.intLayout("delta_color_preference"));

	}

	@Override
	protected void onBindView(View view) {
		super.onBindView(view);

		View colorView = (View)view.findViewById(Tools.intId("color_view"));
		colorView.setBackgroundDrawable(new CircleColorDrawable(value));
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
		new AmbilWarnaDialog(getContext(), value, new AmbilWarnaDialog.OnAmbilWarnaListener() {
			@Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
				if (!callChangeListener(color)) return; // They don't want the value to be set
				value = color;
				persistInt(value);
				notifyChanged();

				ColorPickerPreference.this.value = color;
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
