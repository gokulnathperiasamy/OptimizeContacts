package com.kpgn.optimizecontacts.utility;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.kpgn.optimizecontacts.application.Constants;
import com.kpgn.optimizecontacts.entity.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ContactsHelper {

    public static List<Contact> getSortedContactList(Context context) {
        List<Contact> contactList = getContactList(context);
        Set<Contact> headerSet = new HashSet<>();
        if (contactList != null && !contactList.isEmpty()) {
            for (Contact contact : contactList) {
                Contact headerItem = new Contact();
                headerItem.setDisplayName(String.valueOf(contact.getDisplayName().charAt(0)));
                headerItem.setType(Constants.Type.TYPE_HEADER);
                headerSet.add(headerItem);
            }
            contactList.addAll(headerSet);
            Collections.sort(contactList, new ContactComparator());
        }

        return contactList;
    }

    private static List<Contact> getContactList(Context context) {
        List<Contact> contactList = new ArrayList<>();

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Uri photoUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(id));
                Set<String> phoneNumberList = new HashSet<>();
                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor phoneNumberCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    if (phoneNumberCursor != null && phoneNumberCursor.getCount() > 0) {
                        while (phoneNumberCursor.moveToNext()) {
                            phoneNumberList.add(getFormattedNumber(phoneNumberCursor.getString(phoneNumberCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));
                        }
                        phoneNumberCursor.close();
                    }
                }

                Contact contact = new Contact();
                contact.setDisplayName(displayName);
                contact.setListPhoneNumbers(new ArrayList<>(phoneNumberList));
                contact.setPhotoUri(photoUri);
                contact.setType(Constants.Type.TYPE_ITEM);
                contactList.add(contact);
            }
            cursor.close();
        }

        return contactList;
    }

    private static String getFormattedNumber(String number) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber phoneNumber = phoneUtil.parse(number, "IN");
            if (phoneUtil.isValidNumber(phoneNumber)) {
                return phoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
            }
        } catch (NumberParseException e) {
            e.printStackTrace();
        }

        return number;
    }
}
