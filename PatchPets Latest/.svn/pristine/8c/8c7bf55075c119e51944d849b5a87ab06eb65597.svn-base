package com.patchpets.Activitys;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.patchpets.Adapters.CardListAdapter;
import com.patchpets.Adapters.ContactListAdapter;
import com.patchpets.R;
import com.patchpets.databinding.ActivityContactListBinding;
import com.patchpets.interfaces.OnItemClickListener;
import com.patchpets.model.CardList;
import com.patchpets.model.Contact;
import com.patchpets.utils.AlertDialogUtility;
import com.patchpets.utils.Constants;
import com.patchpets.utils.HeaderBar;
import com.patchpets.utils.LogM;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityContactListBinding binding;
    private HeaderBar headerBar;
    private TextView tvEmptyView;
    private RecyclerView rvContactList;
    private LinearLayoutManager linearLayoutManager;
    private ContactListAdapter contactListAdapter;
    private Activity context;
    private TextView tvSend;

    private ArrayList<Contact> selectUsers;
    private List<Contact> temp;
    private ListView listView;
    private Cursor phones, email;
    private ContentResolver resolver;
    private ContactListAdapter adapter;

    private static final int PERMISSION_REQUEST_CONTACT = 1213;

    ArrayList<String> alNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_list);

        selectUsers = new ArrayList<Contact>();
        resolver = this.getContentResolver();
        listView = (ListView) findViewById(R.id.contacts_list);

        bindViews();
        setHeaderBar();
        setListener();
        setAdapter();

        askForContactPermission();
    }

    private void setListener() {
        headerBar.ibLeft.setOnClickListener(this);
        tvSend.setOnClickListener(this);
    }

    private void bindViews() {
        try {
            headerBar = binding.headerBar;
//            rvContactList = binding.rvContactList;
            tvEmptyView = binding.tvEmpty;
            tvSend = binding.tvSend;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHeaderBar() {
        try {
            headerBar.ibLeft.setVisibility(View.VISIBLE);
            headerBar.ibLeft.setImageResource(R.drawable.back);
            headerBar.tvTitle.setVisibility(View.VISIBLE);
            headerBar.tvTitle.setText(R.string.select_contact);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapter() {
//        ArrayList<Contact> cardDetails = new ArrayList();
//        Contact contact = new Contact();
//        contact.setContactName("Johne Doe");
//        contact.setContactNumber("+61 12345 67890");
//        cardDetails.add(contact);
//
//        Contact contactTwo = new Contact();
//        contactTwo.setContactName("Johne Doe");
//        contactTwo.setContactNumber("+61 12345 12345");
//        cardDetails.add(contactTwo);
//
//        Contact contactThree = new Contact();
//        contactThree.setContactName("Johne Doe");
//        contactThree.setContactNumber("+61 67890 67890");
//        cardDetails.add(contactThree);
//
//        Contact contactFour = new Contact();
//        contactFour.setContactName("Johne Doedd");
//        contactFour.setContactNumber("+61 67890 67890");
//        cardDetails.add(contactFour);

//        contactListAdapter = new ContactListAdapter(this, context, cardDetails);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
//        rvContactList.setLayoutManager(linearLayoutManager);
//        rvContactList.setAdapter(contactListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSend:
//                onBackPressed();
                alNames.clear();
                for (int i = 0; i < selectUsers.size(); i++) {
                    if (selectUsers.get(i).isChecked()) {
                        alNames.add(selectUsers.get(i).getPhone());
                    }
                }
                if (alNames.size() == 0) {
                    AlertDialogUtility.showSnakeBar("Please select at least one contact", tvSend, ContactListActivity.this);
                    return;
                } else {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < alNames.size(); i++) {
                        sb.append(alNames.get(i) + ", ");
                    }
                    Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + sb.toString()));
                    if (getIntent() != null) {
                        String strMessage = getIntent().getStringExtra(Constants.WRITE_MESSAGE);
                        smsIntent.putExtra("sms_body", strMessage);
                        startActivity(smsIntent);
                    }
                    break;
                }

            case R.id.ibLeft:
                onBackPressed();
                break;
        }
    }

    public void askForContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(ContactListActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(ContactListActivity.this, Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ContactListActivity.this);
                    builder.setTitle("Contacts access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("please confirm Contacts access");
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_CONTACT);
                        }
                    });
                    builder.show();
                } else {
                    ActivityCompat.requestPermissions(ContactListActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_CONTACT);
                }
            } else {
                getContact();
            }
        } else {
            getContact();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CONTACT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContact();
                } else {
                    Toast.makeText(ContactListActivity.this, "No permission for contacts", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void getContact() {
        phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        LoadContact loadContact = new LoadContact();
        loadContact.execute();
    }

    class LoadContact extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (phones != null) {
                LogM.e("count : " + phones.getCount());
                if (phones.getCount() == 0) {
                    Toast.makeText(ContactListActivity.this, "No contacts in your contact list.", Toast.LENGTH_LONG).show();
                }

                while (phones.moveToNext()) {
                    Bitmap bit_thumb = null;
                    Uri photoURI = null;
                    String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String EmailAddr = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA2));
                    String image_thumb = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
                    try {
                        if (image_thumb != null) {
                            photoURI = Uri.parse(image_thumb);
                            bit_thumb = MediaStore.Images.Media.getBitmap(resolver, Uri.parse(image_thumb));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Contact selectUser = new Contact();
                    selectUser.setThumb(bit_thumb);
                    selectUser.setName(name);
                    selectUser.setPhone(phoneNumber);
                    selectUser.setEmail(id);
                    selectUser.setCheckedBox(false);
                    selectUser.setPhototUri(photoURI);
                    selectUsers.add(selectUser);
                }
            }
            //phones.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new ContactListAdapter(selectUsers, ContactListActivity.this);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Contact data = selectUsers.get(i);

                    if (selectUsers.get(i).isChecked()) {
                        selectUsers.get(i).setChecked(false);
                    } else {
                        selectUsers.get(i).setChecked(true);
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
}
