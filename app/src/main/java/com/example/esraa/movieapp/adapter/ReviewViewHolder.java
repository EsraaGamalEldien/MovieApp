package com.example.esraa.movieapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.esraa.movieapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_review_author)
    TextView reviewAuthorTextView;
    @BindView(R.id.tv_review_content)
    TextView reviewContentTextView;

    public ReviewViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
