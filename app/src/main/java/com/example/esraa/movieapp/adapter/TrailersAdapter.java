package com.example.esraa.movieapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.esraa.movieapp.R;
import com.example.esraa.movieapp.model.Trailer;
import com.example.esraa.movieapp.view.IDetailsActivity;

import java.util.HashMap;
import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailerViewHolder> {
    private Context context;
    private List<Trailer> trailerList;
    private IDetailsActivity detailsActivityView;

    public TrailersAdapter(Context context, List<Trailer> trailerList, IDetailsActivity detailsActivityView) {
        this.context = context;
        this.trailerList = trailerList;
        this.detailsActivityView = detailsActivityView;
    }


    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trailer_view_layout, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        final Trailer trailer = trailerList.get(position);
        holder.trailer.setImageResource(R.drawable.video_camera);
        holder.trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsActivityView.playTrailer(trailer);
            }
        });

    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public void setTrailersList(List<Trailer> trailerList) {
        this.trailerList = trailerList;
    }
}
