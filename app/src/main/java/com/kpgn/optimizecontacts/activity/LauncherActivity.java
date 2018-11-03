package com.kpgn.optimizecontacts.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kpgn.optimizecontacts.R;
import com.kpgn.optimizecontacts.entity.Contact;
import com.kpgn.optimizecontacts.fragment.ProgressDialogFragment;
import com.kpgn.optimizecontacts.utility.ContactsHelper;
import com.kpgn.optimizecontacts.view.ContactAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LauncherActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 12345;

    @BindView(R.id.tv_data_not_available)
    TextView mDataNotAvailable;

    @BindView(R.id.rv_contact_list)
    RecyclerView mContactList;

    private List<Contact> contactList;
    private ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        ButterKnife.bind(this);
        setupActionBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermissions();
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            updateList();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateList();
            } else {
                Toast.makeText(this, R.string.contact_permission_error, Toast.LENGTH_SHORT).show();
                mDataNotAvailable.setVisibility(View.VISIBLE);
                mContactList.setVisibility(View.GONE);
            }
        }
    }

    private void setupActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_launcher, menu);
        setupSearchMenu(menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupSearchMenu(Menu menu) {
        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchString) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                searchContactList(searchString);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchString) {
                searchContactList(searchString);
                return false;
            }
        });
    }

    private void updateList() {
        ProgressDialogFragment.showProgressDialog(this);
        contactList = ContactsHelper.getSortedContactList(this);
        setupRecyclerView(contactList);
        ProgressDialogFragment.hideProgressDialog();
    }

    private void setupRecyclerView(List<Contact> contactList) {
        contactAdapter = new ContactAdapter(this, contactList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mContactList.setLayoutManager(mLayoutManager);
        mContactList.setItemAnimator(new DefaultItemAnimator());
        mContactList.setAdapter(contactAdapter);
        checkIfDataAvailable();
    }

    private void checkIfDataAvailable() {
        if (contactList == null || contactList.isEmpty()) {
            mDataNotAvailable.setVisibility(View.VISIBLE);
            mContactList.setVisibility(View.GONE);
        } else {
            mDataNotAvailable.setVisibility(View.GONE);
            mContactList.setVisibility(View.VISIBLE);
        }
        contactAdapter.notifyDataSetChanged();
    }

    private void searchContactList(String searchString) {
        List<Contact> filteredContactList = new ArrayList<>();
        for (Contact contact : contactList) {
            if (contact.getDisplayName().toLowerCase().contains(searchString.toLowerCase())) {
                filteredContactList.add(contact);
            }
        }
        setupRecyclerView(filteredContactList);
    }
}
