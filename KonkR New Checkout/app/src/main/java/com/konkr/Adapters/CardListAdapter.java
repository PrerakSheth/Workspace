package com.konkr.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.konkr.Models.CardList;
import com.konkr.R;
import com.konkr.Utils.LogM;
import com.stripe.android.model.Card;

import java.util.ArrayList;

/**
 * Created by Android on 6/6/2018.
 */

public class CardListAdapter extends RecyclerView.Adapter <CardListAdapter.ViewHolder> {
    Context context;
    ArrayList <CardList.CardListBean> cardListArray;
    private ItemClickListener itemClickListener;

    public CardListAdapter(ItemClickListener itemClickListener, Context context, ArrayList <CardList.CardListBean> listArray) {
        this.context = context;
        this.cardListArray = listArray;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public CardListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.row_card, parent, false);

        return new ViewHolder (itemView);

    }

    public interface ItemClickListener {
        void onItemClick(View view, int pos);
        void onCardNumberClick(View view, int pos);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardList.CardListBean cardList = cardListArray.get (position);
        String strCardNo;
        String strCardNoOriginal = cardList.getCardNumber ();
        int numDigits = strCardNoOriginal.length ();
        int rem;

        if (numDigits == 12) {
            strCardNo = "XXXX-XXXX-";
            holder.cardNumber.setText (strCardNo + strCardNoOriginal.substring (strCardNoOriginal.length () - 4, strCardNoOriginal.length ()));
        } else if (numDigits > 16) {
            strCardNo = "XXXX-XXXX-XXXX-XXXX-";
            rem = numDigits - 16;
            holder.cardNumber.setText (strCardNo + strCardNoOriginal.substring (strCardNoOriginal.length () - rem, strCardNoOriginal.length ()));
        } else if (numDigits > 12 || numDigits == 16) {
            strCardNo = "XXXX-XXXX-XXXX-";
            rem = numDigits - 12;
            holder.cardNumber.setText (strCardNo + strCardNoOriginal.substring (strCardNoOriginal.length () - rem, strCardNoOriginal.length ()));
        }

//        holder.cardNumber.setText (strCardNo + strCardNoOriginal.substring (strCardNoOriginal.length () - 4, strCardNoOriginal.length ()));
        holder.tvDate.setText ("" + cardList.getExpiry ());


        Card card = new Card (cardList.getCardNumber (), 0, 0, "123");
        LogM.LogV ("BRAND: " + card.getBrand ());
        if (card.getBrand () == null) {
            holder.ivCard.setImageDrawable (null);
            return;
        }
        if (card.getBrand ().equalsIgnoreCase ("Visa")) {
            holder.ivCard.setImageDrawable (ContextCompat.getDrawable (context, R.drawable.visa));
        }

        if (card.getBrand ().equalsIgnoreCase ("MasterCard")) {
            holder.ivCard.setImageDrawable (ContextCompat.getDrawable (context, R.drawable.mastercard));
        }
        if (card.getBrand ().equalsIgnoreCase ("American Express")) {
            holder.ivCard.setImageDrawable (ContextCompat.getDrawable (context, R.drawable.american_express));
        }

        holder.itemView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick (view, position);

            }
        });

        holder.cardNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onCardNumberClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardListArray.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView ivCard;
        TextView cardNumber, tvDate;

        public ViewHolder(View v) {
            super (v);

            ivCard = v.findViewById (R.id.ivCard);
            cardNumber = v.findViewById (R.id.tvCardNo);
            tvDate = v.findViewById (R.id.tvDate);
        }
    }
}
