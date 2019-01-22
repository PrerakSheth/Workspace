package com.konkr.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.konkr.Adapters.ContactListAdapter;
import com.konkr.Models.Contact;
import com.konkr.R;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.Headerbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity implements View.OnClickListener {


//    private static final String TAG = ContactListActivity.class.getSimpleName();
//    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
//    private Uri uriContact;
//    private String contactID;     // contacts unique ID

//    private ActivityContactListBinding binding;
//    private RecyclerView rvContact;

    // ArrayList
    ArrayList<Contact> selectUsers;
    List<Contact> temp;
    // Contact List
    ListView listView;
    // Cursor to load contacts list
    Cursor phones, email;

    // Pop up
    ContentResolver resolver;
    //    SearchView search;
    ContactListAdapter adapter;

    private static final int PERMISSION_REQUEST_CONTACT = 1213;

    private Headerbar headerBar;

    ArrayList<String> alNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

//        bindViews();
        selectUsers = new ArrayList<Contact>();
        resolver = this.getContentResolver();
        headerBar = (Headerbar) findViewById(R.id.headerBar);
        listView = (ListView) findViewById(R.id.contacts_list);

        setHeaderBar();
        setListner();
        askForContactPermission();

    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.ibtnLeftOne:
                    finish();
                    break;
                case R.id.tvRight:
                    alNames.clear();
                    for (int i = 0; i < selectUsers.size(); i++) {
                        if (selectUsers.get(i).isChecked()) {
                            alNames.add(selectUsers.get(i).getPhone());
                        }
                    }
                    if (alNames.size() == 0) {
                        AlertDialogUtility.showSnakeBar("Please select altleast one contact", headerBar.tvRight, ContactListActivity.this);
                        return;
                    }
                    Intent intent = new Intent(ContactListActivity.this, SocialSharingViaSmsActivity.class);
                    intent.putStringArrayListExtra(GlobalData.CONTACT_NAME, alNames);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListner() {
        try {
            headerBar.ibtnLeftOne.setOnClickListener(this);
            headerBar.tvRight.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHeaderBar() {
        try {
            headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
            headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
            headerBar.tvTitle.setVisibility(View.VISIBLE);
            headerBar.tvTitle.setText(R.string.select_contact);
            headerBar.tvRight.setVisibility(View.VISIBLE);
            headerBar.tvRight.setText("DONE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load data on background
    class LoadContact extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Get Contact list from Phone

            if (phones != null) {
                Log.e("count", "" + phones.getCount());
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
                        } else {
                            Log.e("No Image Thumb", "--------------");
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
            } else {
                Log.e("Cursor close 1", "----------------");
            }
            //phones.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new ContactListAdapter(selectUsers, ContactListActivity.this);
            listView.setAdapter(adapter);

            // Select item on listclick
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Log.e("search", "here---------------- listener");

                    Contact data = selectUsers.get(i);

                    if (selectUsers.get(i).isChecked()) {
                        selectUsers.get(i).setChecked(false);
                    } else {
                        selectUsers.get(i).setChecked(true);
                    }
//                    data.setChecked(true);

//                    for (int j = 0; j < selectUsers.size(); j++) {
//                        if (selectUsers.get(j).isChecked()) {
//                            String name = selectUsers.get(j).getName();
//                            alNames.add(name);
//                        }
//                    }

                    adapter.notifyDataSetChanged();


                }
            });

//            listView.setFastScrollEnabled(true);
        }
    }

    public void askForContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(ContactListActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(ContactListActivity.this,
                        Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ContactListActivity.this);
                    builder.setTitle("Contacts access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("please confirm Contacts access");//TODO put real question
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {Manifest.permission.READ_CONTACTS}
                                    , PERMISSION_REQUEST_CONTACT);
                        }
                    });
                    builder.show();
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(ContactListActivity.this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            PERMISSION_REQUEST_CONTACT);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                getContact();
            }
        } else {
            getContact();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CONTACT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContact();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(ContactListActivity.this, "No permission for contacts", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }

    }

    private void getContact() {
        phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        LoadContact loadContact = new LoadContact();
        loadContact.execute();
    }

}
