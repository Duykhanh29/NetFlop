package com.example.netflop.ui.adapters.remote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netflop.R;
import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.remote.movies.Video;

import java.util.List;

public class SecondListTrailerAdapter extends RecyclerView.Adapter<SecondListTrailerAdapter.ViewHolder> {
    List<Video> listVideo;
    Context context;
//    YouTubePlayer currentYouTubePlayer;
    Lifecycle lifecycle;

    public SecondListTrailerAdapter(List<Video> listVideo, Context context,Lifecycle lifecycle) {
        this.listVideo = listVideo;
        this.context = context;
        this.lifecycle=lifecycle;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_trailer_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Video video= listVideo.get(position);
//        lifecycle.addObserver(holder.youTubePlayerView);
        String youtubeURL= URLConstants.embedYoutubeURL+video.getKey();
        holder. webView.getSettings().setJavaScriptEnabled(true);
        holder.webView.setWebChromeClient(new WebChromeClient());
        String url="<iframe width=\"100%\" height=\"100%\" src=\""+youtubeURL +"\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        holder.webView.loadData(url, "text/html","utf-8");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(currentYouTubePlayer!=null){
//                    currentYouTubePlayer.pause();
//                }
//                holder.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//
//                    @Override
//                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                        super.onReady(youTubePlayer);
//                        youTubePlayer.loadVideo(video.getKey(),0);
//                        currentYouTubePlayer = youTubePlayer;
//                    }
//                });
                holder.webView.loadData(url, "text/html","utf-8");

            }
        });
    }

    @Override
    public int getItemCount() {
        return listVideo.size();
    }

    public  static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
//        YouTubePlayerView youTubePlayerView;
        WebView webView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=(CardView) itemView.findViewById(R.id.single_trailer_view);
//            youTubePlayerView=(YouTubePlayerView) itemView.findViewById(R.id.youtubePlayerSingleView);
            webView=(WebView) itemView.findViewById(R.id.webView);
        }
    }
}
