package id.delta.whatsapp.status;

import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.whatsapp.ContactStatusThumbnail;
import com.whatsapp.HomeActivity;
import com.whatsapp.StatusesFragment;
import com.whatsapp.StatusesFragment.a;
import com.whatsapp.StatusesFragment.d;
import com.whatsapp.TextEmojiLabel;
import com.whatsapp.contact.b;
import com.whatsapp.data.at;
import com.whatsapp.data.ew;
import com.whatsapp.data.ft;

import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Colors;
import id.delta.whatsapp.value.Main;

public class AdapterStatus extends RecyclerView.Adapter<AdapterStatus.ViewHolder> {

    private HomeActivity mHomeActivity;
    private View.OnClickListener mOnClickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public RelativeLayout mParentLayout;
        public ContactStatusThumbnail mContactPhoto;
        public FrameLayout mContactSelector;
       // public View mContactMark;
        public ImageView mThumbnail;
        public TextEmojiLabel mContactName;
        public View mAdd;
        public ImageView mAddIcon;

        public ViewHolder(RelativeLayout relativeLayout, View.OnClickListener onClickListener) {
            super(relativeLayout);
            mParentLayout = relativeLayout;
            mContactPhoto = mParentLayout.findViewById(Tools.intId( "contact_photo"));
            mContactSelector = mParentLayout.findViewById(Tools.intId( "contact_selector"));
            mThumbnail = mParentLayout.findViewById(Tools.intId("mThumbnail"));
            mContactName = mParentLayout.findViewById(Tools.intId("contact_name"));
            mAdd = mParentLayout.findViewById(Tools.intId( "add"));
            mAddIcon = mParentLayout.findViewById(Tools.intId("mAddIcon"));
            mParentLayout.setOnClickListener(onClickListener);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterStatus(HomeActivity homeActivity, View.OnClickListener onClickListener) {
        mHomeActivity = homeActivity;
        mOnClickListener = onClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdapterStatus.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        // create a new view
        final RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(Tools.intLayout("delta_list_status"), null);
        // set the view's size, margins, paddings and layout parameters

        final ViewHolder viewHolder = new ViewHolder(relativeLayout, mOnClickListener);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        a status = mHomeActivity.mStatusFragment.getStatusesDataSet().get(position);

        // Hide titles plox
        if (status instanceof d) {
            holder.mContactSelector.setLayoutParams(new RelativeLayout.LayoutParams(0, 0));
            return;
        } else {
            holder.mContactSelector.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        ew statusInfo = ((StatusesFragment.f) status).b;

        boolean isMe = statusInfo.a.contentEquals("");

        holder.mContactPhoto.a(statusInfo.i, statusInfo.j);

        // Let's use contact pictures instead ;)
        final ft contactInfo;

        if (isMe) {
            contactInfo = mHomeActivity.mMeManager.d();
        }
        else {
            contactInfo = at.a().b(statusInfo.a);
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                com.whatsapp.contact.a.d pictureManager = com.whatsapp.contact.a.d.a();
                Bitmap pictureBitmap = pictureManager.a(contactInfo, 200, -1.0f, true);

                if (pictureBitmap == null) {
                    pictureBitmap = b.a().b(contactInfo);
                }

                holder.mContactPhoto.setImageBitmap(pictureBitmap);
            }
        });
        thread.setName("StatusesAdapterThread");
        thread.run();

         // Show or hide add icon
        holder.mAdd.setVisibility(isMe && statusInfo.j == 0? View.VISIBLE : View.GONE);

        holder.mAddIcon.setBackground(Tools.colorDrawable("delta_circle_bg", Colors.warnaFab(), PorterDuff.Mode.SRC_ATOP));
        holder.mAddIcon.setColorFilter(Colors.warnaFabIcon());

        holder.mContactName.setTextColor(Main.statusTitle());

        if (isMe) {
            holder.mContactName.setText("You");
        } else {
            holder.mContactName.setText(contactInfo.d);
        }

        holder.mParentLayout.setVisibility(View.VISIBLE);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mHomeActivity.mStatusFragment.getStatusesDataSet().size();
    }

    /*
    private void setupThumbnail(ViewHolder holder, ew statusInfo){
        //Menampikan thumbnail dari status
       n j = statusInfo.mProtocol;

        if (j == null) {
            j = mHomeActivity.mStatusFragment.getStatusFragment().ag.c.getProtocolFromMessageId(statusInfo.mMessageId);
        }

        if (j != null) {
            MediaData mediaData = j.getMediaData();

            if (mediaData != null) {

                File file = mediaData.file;

                if (file != null && file.exists()) {
                    Bitmap bitmap = mHomeActivity.mStatusFragment.getStatusFragment().aF.a(j, false);

                    if (bitmap != null) {
                        holder.mContactPhoto.setImageBitmap(bitmap);
                    }

                }

            }
            else {
                String text = j.getTextStatusText();
                TextData textData = j.getTextData();

                c emoji = mHomeActivity.mStatusFragment.getStatusFragment().ay;
                com.whatsapp.g.d az = mHomeActivity.mStatusFragment.getStatusFragment().az;

                akl drawable = new akl(mHomeActivity, emoji, az, text, textData);

                holder.mContactPhoto.setImageDrawable(drawable);
            }

        } else {
            ColorDrawable colorDrawable = new ColorDrawable(Color.BLACK);
            holder.mContactPhoto.setImageDrawable(colorDrawable);
        }

        statusInfo.mProtocol = j;

    }*/
}
