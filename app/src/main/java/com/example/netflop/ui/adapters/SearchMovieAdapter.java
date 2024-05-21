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

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.ViewHolder> {
    List<Movie> listMovie;
    Context context;
    ItemTouchHelperAdapter itemTouchMovieHelper;

    public SearchMovieAdapter(List<Movie> listMovie, Context context, ItemTouchHelperAdapter itemTouchMovieHelper) {
        this.listMovie = listMovie;
        this.context = context;
        this.itemTouchMovieHelper = itemTouchMovieHelper;
    }
    public void clearData() {
        if (listMovie != null) {
            listMovie.clear();
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_search_movie_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie=listMovie.get(position);
        holder.nameMovieTV.setText(movie.getTitle());
        holder.starTextView.setText(movie.getVoteAverage()+"");
        String year= CommonMethods.getYearByReleaseDate(movie.getReleaseDate());
        String imageURL= URLConstants.imageURL+movie.getPosterPath();
        Log.d("TAG","URL is"+imageURL);
        Glide.with(context).load(imageURL).placeholder(R.drawable.place_holder).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemTouchMovieHelper.onMovieClick(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        TextView nameMovieTV,starTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=(CardView) itemView.findViewById(R.id.search_movie_card_view);
            imageView=(ImageView) itemView.findViewById(R.id.searchMovieImageView);
            nameMovieTV=(TextView) itemView.findViewById(R.id.nameMovieSearchView);
            starTextView=(TextView) itemView.findViewById(R.id.starSearchMovieView);
        }
    }
}
