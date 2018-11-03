package com.kpgn.optimizecontacts.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kpgn.optimizecontacts.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HeaderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_header)
    TextView mHeader;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindView(String headerString) {
        mHeader.setText(headerString);
    }
}