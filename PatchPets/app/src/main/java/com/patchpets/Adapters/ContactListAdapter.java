package com.patchpets.Adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.patchpets.R;
import com.patchpets.model.CardList;
import com.patchpets.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 6/6/2018.
 */

public class ContactListAdapter extends BaseAdapter {
//    Activity context;
//    ArrayList<Contact> alContact;
//    private ItemClickListener itemClickListener;

    public List<Contact> _data;
    private ArrayList<Contact> arraylist;
    Context _c;
    ViewHolder v;

//    public ContactListAdapter(ItemClickListener itemClickListener, Activity context, ArrayList<Contact> alContact) {
//        this.context = context;
//        this.alContact = alContact;
//        this.itemClickListener = itemClickListener;
//    }

    public ContactListAdapter(List<Contact> selectUsers, Context context) {
        _data = selectUsers;
        _c = context;
        this.arraylist = new ArrayList<Contact>();
        this.arraylist.addAll(_data);
    }

//    @Override
//    public ContactListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.row_contact, parent, false);
//
//        return new ViewHolder(itemView);
//
//    }

//    public interface ItemClickListener {
//        void onItemClick(View view, int pos);
//
//        void onCardNumberClick(View view, int pos);
//    }

//    @Override
//    public void onBindViewHolder(ViewHolder holder, final int position) {
//        holder.tvContactNumber.setText("+61 12345 67890");
//        if (position % 3 == 0) {
//            holder.tvContactName.setText("Johne Doe");
//            holder.ivSelectContact.setImageResource(R.drawable.select_contact_check_green);
//        } else if (position % 3 == 1) {
//            holder.tvContactName.setText("Johne Doedd");
//            holder.ivSelectContact.setImageResource(R.drawable.select_contact_check_grey);
//        } else {
//            holder.tvContactName.setText("Johne Doe");
//            holder.ivSelectContact.setImageResource(R.drawable.select_contact_check_green);
//        }
//        // final Contact contact = alContact.get(position);
//
//        //  holder.tvContactName.setText(contact.getContactName());
//        // holder.tvContactNumber.setText(contact.getContactNumber());
//
//
////        holder.itemView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                itemClickListener.onItemClick(view, position);
////
////            }
////        });
////
////        holder.cardNumber.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                itemClickListener.onCardNumberClick(view, position);
////            }
////        });
//    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.row_contact, null);
        } else {
            view = convertView;
        }

        v = new ViewHolder();

        v.tvContactName = (TextView) view.findViewById(R.id.tvContactName);
//        v.check = (CheckBox) view.findViewById(R.id.check);
        v.tvContactNumber = (TextView) view.findViewById(R.id.tvContactNumber);
//        v.imageView = (ImageView) view.findViewById(R.id.pic);
        v.ivSelectContact = (ImageView) view.findViewById(R.id.ivSelectContact);

        final Contact data = (Contact) _data.get(i);
        v.tvContactName.setText(data.getName());
//        v.check.setChecked(data.getCheckedBox());
        v.tvContactNumber.setText(data.getPhone());
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
//        try {
//
//            if (data.getThumb() != null) {
//                v.ivSelectContact.setImageURI(data.getPhototUri());
//            } else {
//                v.ivSelectContact.setImageResource(R.drawable.profile_other_placeholder);
//            }
//            // Seting round image
////            Bitmap bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.user); // Load default image
////            roundedImage = new RoundImage(bm);
//            v.ivSelectContact.setImageURI(data.getPhototUri());
//        } catch (OutOfMemoryError e) {
//            // Add default picture
////            v.ivContact.setImageResource(R.drawable.profile_other_placeholder);
//            e.printStackTrace();
//        }
//
//        Log.e("Image Thumb", "--------------" + data.getThumb());

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
            v.ivSelectContact.setBackground(_c.getResources().getDrawable(R.drawable.select_contact_check_green));
        } else {
            v.ivSelectContact.setBackground(_c.getResources().getDrawable(R.drawable.select_contact_check_grey));
        }

        view.setTag(data);
        return view;
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

    static class ViewHolder {
//        MyTextView title, phone;
//        ConstraintLayout clMain;
//        ImageView ivSelectedContact;
//        CheckBox check;

        TextView tvContactName, tvContactNumber;
        ImageView ivSelectContact;

    }

//    public class ViewHolder extends RecyclerView.ViewHolder {
//        // each data item is just a string in this case
//        TextView tvContactName, tvContactNumber;
//ImageView ivSelectContact;
//        public ViewHolder(View v) {
//            super(v);
//
//            tvContactName = v.findViewById(R.id.tvContactName);
//            tvContactNumber = v.findViewById(R.id.tvContactNumber);
//            ivSelectContact=v.findViewById(R.id.ivSelectContact);
//        }
//    }
}
