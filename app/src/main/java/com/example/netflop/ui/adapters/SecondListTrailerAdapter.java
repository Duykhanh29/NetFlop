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
import com.example.netflop.utils.OnTrailerClickListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

public class SecondListTrailerAdapter extends RecyclerView.Adapter<SecondListTrailerAdapter.ViewHolder> {
    List<Video> listVideo;
    Context context;
    OnTrailerClickListener onTrailerClickListener;

    public SecondListTrailerAdapter(List<Video> listVideo, Context context, OnTrailerClickListener onTrailerClickListener) {
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
        YouTubePlayerView youTubePlayerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=(CardView) itemView.findViewById(R.id.single_trailer_view);
            youTubePlayerView=(YouTubePlayerView) itemView.findViewById(R.id.youtubePlayerSingleView);
        }
    }
}
