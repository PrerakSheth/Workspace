package com.patchpets.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.patchpets.R;
import com.patchpets.interfaces.OnItemClickListener;
import com.patchpets.model.CardList;
import com.patchpets.model.responseModel.CardListResponse;
import com.patchpets.utils.DateTime;

import java.util.ArrayList;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<CardListResponse.CardListBean> cardListArray;
    private OnItemClickListener mListener;

    public CardListAdapter(Activity context, ArrayList<CardListResponse.CardListBean> listArray, OnItemClickListener mListener) {
        this.context = context;
        this.cardListArray = listArray;
        this.mListener = mListener;
    }

    @Override
    public CardListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardListResponse.CardListBean cardList = cardListArray.get(position);
        String cardNumber = cardList.getCardNumber();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.cardNumber.setText(Html.fromHtml(context.getResources().getString(R.string.card_asterisk) + " " + cardNumber.substring(cardNumber.length() - 4), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.cardNumber.setText(Html.fromHtml(context.getResources().getString(R.string.card_asterisk) + " " + cardNumber.substring(cardNumber.length() - 4)));
        }
        holder.tvExpires.setText(context.getResources().getString(R.string.expires) + " " + DateTime.getDateForCardExpiry(cardList.getExpiry()));
    }

    @Override
    public int getItemCount() {
        return cardListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ConstraintLayout clCard;
        ImageView ivCard;
        TextView cardNumber, tvExpires;

        public ViewHolder(View itemView) {
            super(itemView);
            clCard = itemView.findViewById(R.id.clCard);
            ivCard = itemView.findViewById(R.id.ivCard);
            cardNumber = itemView.findViewById(R.id.tvCardNo);
            tvExpires = itemView.findViewById(R.id.tvExpires);
            clCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClicked(getAdapterPosition());
        }
    }
}
