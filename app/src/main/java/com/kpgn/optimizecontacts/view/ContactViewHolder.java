package com.kpgn.optimizecontacts.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kpgn.optimizecontacts.R;
import com.kpgn.optimizecontacts.entity.Contact;

import java.io.BufferedInputStream;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ContactViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_contact_image)
    ImageView mContactImage;

    @BindView(R.id.tv_display_name)
    TextView tvDisplayName;

    @BindView(R.id.tv_phone_number)
    TextView mPhoneNumber;

    public ContactViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindView(Context context, Contact contact) {
        if (contact.getPhotoUri() != null) {
            mContactImage.setImageDrawable(context.getDrawable(R.drawable.ic_contact));
            try {
                InputStream photoStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(), contact.getPhotoUri());
                if (photoStream != null) {
                    mContactImage.setImageBitmap(BitmapFactory.decodeStream(new BufferedInputStream(photoStream)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        tvDisplayName.setText(contact.getDisplayName());
        mPhoneNumber.setText(TextUtils.join(", ", contact.getListPhoneNumbers()));
    }
}