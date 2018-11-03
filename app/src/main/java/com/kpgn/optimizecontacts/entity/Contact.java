package com.kpgn.optimizecontacts.entity;

import android.net.Uri;

import java.util.List;

public class Contact {

    private String displayName;
    private List<String> listPhoneNumbers;
    private Uri photoUri;
    private int type;

    public Contact() {
        // Default constructor...
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getListPhoneNumbers() {
        return listPhoneNumbers;
    }

    public void setListPhoneNumbers(List<String> listPhoneNumbers) {
        this.listPhoneNumbers = listPhoneNumbers;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Contact) && this.getDisplayName().equalsIgnoreCase(((Contact) obj).getDisplayName());
    }

    @Override
    public int hashCode() {
        return this.getDisplayName().hashCode();
    }
}
