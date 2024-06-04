package com.example.netflop.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netflop.R;
import com.example.netflop.data.models.Video;
import com.example.netflop.utils.listeners.OnTrailerClickListener;

import java.util.List;

public class ListTrailerAdapter extends RecyclerView.Adapter<ListTrailerAdapter.ViewHolder> {
    List<Video> listVideo;
    Context context;
    OnTrailerClickListener onTrailerClickListener;

    public ListTrailerAdapter(List<Video> listVideo, Context context, OnTrailerClickListener onTrailerClickListener) {
        this.listVideo = listVideo;
        this.context = context;
        this.onTrailerClickListener = onTrailerClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.a_trailer_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Video video= listVideo.get(position);
        holder.trailerNameTextView.setText(video.getName()+" - "+video.getType());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTrailerClickListener.onCLickVideo(video);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listVideo.size();
    }

    public  static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView trailerNameTextView;
        ImageButton playImageButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=(CardView) itemView.findViewById(R.id.a_trailer_view);
            trailerNameTextView=(TextView) itemView.findViewById(R.id.nameTrailerView);
            playImageButton=(ImageButton) itemView.findViewById(R.id.playTrailerImageButton);
        }
    }
}
