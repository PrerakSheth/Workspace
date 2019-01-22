package com.konkr.Adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.konkr.Models.Contact;
import com.konkr.R;
import com.konkr.Utils.MyTextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Android on 6/6/2018.
 */

public class ContactListAdapter extends BaseAdapter {
    public List<Contact> _data;
    private ArrayList<Contact> arraylist;
    Context _c;
    ViewHolder v;

    public ContactListAdapter(List<Contact> selectUsers, Context context) {
        _data = selectUsers;
        _c = context;
        this.arraylist = new ArrayList<Contact>();
        this.arraylist.addAll(_data);
    }

    @Override
    public int getCount() {
        return _data.size();
    }

    @Override
    public Object getItem(int i) {
        return _data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.row_contact, null);
            Log.e("Inside", "here--------------------------- In view1");
        } else {
            view = convertView;
            Log.e("Inside", "here--------------------------- In view2");
        }

        v = new ViewHolder();

        v.title = (MyTextView) view.findViewById(R.id.tvContactName);
//        v.check = (CheckBox) view.findViewById(R.id.check);
        v.phone = (MyTextView) view.findViewById(R.id.tvContactNumber);
//        v.imageView = (ImageView) view.findViewById(R.id.pic);
        v.ivContact = (SimpleDraweeView) view.findViewById(R.id.ivContact);
        v.clMain = (ConstraintLayout) view.findViewById(R.id.clMain);
        v.ivSelectedContact = (ImageView) view.findViewById(R.id.ivSelectedContact);

        final Contact data = (Contact) _data.get(i);
        v.title.setText(data.getName());
//        v.check.setChecked(data.getCheckedBox());
        v.phone.setText(data.getPhone());
//        v.ivContact.setImageURI(getImageUri(data.getThumb()));

//        // Set image if exists
//        try {
//
//            if (data.getThumb() != null) {
//                v.imageView.setImageBitmap(data.getThumb());
//            } else {
//                v.imageView.setImageResource(R.drawable.image);
//            }
//            // Seting round image
//            Bitmap bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.user); // Load default image
//            roundedImage = new RoundImage(bm);
//            v.imageView.setImageDrawable(roundedImage);
//        } catch (OutOfMemoryError e) {
//            // Add default picture
//            v.imageView.setImageDrawable(this._c.getDrawable(R.drawable.image));
//            e.printStackTrace();
//        }

        // Set image if exists
        try {

            if (data.getThumb() != null) {
                v.ivContact.setImageURI(data.getPhototUri());
            } else {
                v.ivContact.setImageResource(R.drawable.profile_other_placeholder);
            }
            // Seting round image
//            Bitmap bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.user); // Load default image
//            roundedImage = new RoundImage(bm);
            v.ivContact.setImageURI(data.getPhototUri());
        } catch (OutOfMemoryError e) {
            // Add default picture
//            v.ivContact.setImageResource(R.drawable.profile_other_placeholder);
            e.printStackTrace();
        }

        Log.e("Image Thumb", "--------------" + data.getThumb());

        /*// Set check box listener android
        v.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;
                if (checkBox.isChecked()) {
                    data.setCheckedBox(true);
                  } else {
                    data.setCheckedBox(false);
                }
            }
        });*/

        if (data.isChecked()) {
            v.ivSelectedContact.setBackground(_c.getResources().getDrawable(R.drawable.selected));
        } else {
            v.ivSelectedContact.setBackground(_c.getResources().getDrawable(R.drawable.unselected));
        }

        view.setTag(data);
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        _data.clear();
        if (charText.length() == 0) {
            _data.addAll(arraylist);
        } else {
            for (Contact wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    _data.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolder {
        SimpleDraweeView ivContact;
        MyTextView title, phone;
        ConstraintLayout clMain;
        ImageView ivSelectedContact;
//        CheckBox check;
    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(_c.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
