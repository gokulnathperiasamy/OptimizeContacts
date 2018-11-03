package com.kpgn.optimizecontacts.utility;

import com.kpgn.optimizecontacts.entity.Contact;

import java.util.Comparator;

public class ContactComparator implements Comparator<Contact> {
    @Override
    public int compare(Contact contactA, Contact contactB) {
        return contactA.getDisplayName().compareTo(contactB.getDisplayName());
    }
}