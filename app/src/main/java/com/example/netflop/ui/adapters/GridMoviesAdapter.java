package com.example.netflop.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.netflop.R;
import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.Movie;
import com.example.netflop.utils.CommonMethods;
import com.example.netflop.utils.ItemTouchHelperAdapter;

import java.util.List;

public class GridMoviesAdapter extends RecyclerView.Adapter<GridMoviesAdapter.ViewHolder> {
    List<Movie> listMovie;
    Context context;
    ItemTouchHelperAdapter itemTouchMovieHelper;

    public GridMoviesAdapter(List<Movie> listMovie, Context context, ItemTouchHelperAdapter itemTouchMovieHelper) {
        this.listMovie = listMovie;
        this.context = context;
        this.itemTouchMovieHelper = itemTouchMovieHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.a_grid_movie_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie=listMovie.get(position);
        holder.movieTitleTV.setText(movie.getTitle());
        holder.voteRatedTV.setText(movie.getVoteAverage()+"");
        String year= CommonMethods.getYearByReleaseDate(movie.getReleaseDate());
        holder.releaseDateTV.setText(year);
        String imageURL= URLConstants.imageURL+movie.getPosterPath();
        Log.d("TAG","URL is"+imageURL);
        Glide.with(context).load(imageURL).placeholder(R.drawable.place_holder).into(holder.posterView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemTouchMovieHelper.onMovieClick(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView posterView;
        TextView voteRatedTV,movieTitleTV,releaseDateTV;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            posterView=(ImageView) itemView.findViewById(R.id.posterGridMovieView);
            voteRatedTV=(TextView) itemView.findViewById(R.id.voteRatedGridTextView);
            movieTitleTV=(TextView) itemView.findViewById(R.id.movieTitleGridTextView);
            releaseDateTV=(TextView) itemView.findViewById(R.id.dateReleaseGridTextView);
            cardView=(CardView) itemView.findViewById(R.id.singleMovieGridCardView);
        }
    }
}
