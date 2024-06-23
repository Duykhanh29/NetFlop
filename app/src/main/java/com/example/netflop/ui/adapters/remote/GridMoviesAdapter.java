package com.example.netflop.ui.adapters.remote;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.netflop.R;
import com.example.netflop.constants.URLConstants;
import com.example.netflop.constants.enums.TypeOfMedia;
import com.example.netflop.data.models.local.FavouriteMedia;
import com.example.netflop.data.models.remote.movies.Movie;
import com.example.netflop.utils.CommonMethods;
import com.example.netflop.utils.listeners.ItemTouchHelperAdapter;
import com.example.netflop.viewmodel.local.FavouriteMediaViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GridMoviesAdapter extends RecyclerView.Adapter<GridMoviesAdapter.ViewHolder> {
    List<Movie> listMovie;
    Context context;
    ItemTouchHelperAdapter itemTouchMovieHelper;
    FavouriteMediaViewModel favouriteMediaViewModel;
    private List<FavouriteMedia> favouriteData = new ArrayList<>();

    public GridMoviesAdapter(List<Movie> listMovie, Context context, ItemTouchHelperAdapter itemTouchMovieHelper,FavouriteMediaViewModel favouriteMediaViewModel) {
        this.listMovie = listMovie;
        this.context = context;
        this.itemTouchMovieHelper = itemTouchMovieHelper;
        this.favouriteMediaViewModel=favouriteMediaViewModel;
        observeFavouriteData();
    }
    private void observeFavouriteData(){
        favouriteMediaViewModel.getFavouriteMovies().observe((LifecycleOwner) context, favouriteMedia -> {
            favouriteData = favouriteMedia;
            notifyDataSetChanged();
        });
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

        Optional<FavouriteMedia> optionalFavouriteMedia=favouriteData.stream().filter(favouriteMedia -> favouriteMedia.getMediaID()==movie.getID()).findFirst();
        if(optionalFavouriteMedia.isPresent()){
            holder.addMovieImageButton.setImageResource(R.drawable.remove);
        }else {
            holder.addMovieImageButton.setImageResource(R.drawable.add);
        }
        holder.addMovieImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(optionalFavouriteMedia.isPresent()){
                    favouriteMediaViewModel.deleteFavouriteMedia(optionalFavouriteMedia.get().getId());
                }else{
                    favouriteMediaViewModel.insertFavouriteMedia(movie.getID(),movie.getTitle(), TypeOfMedia.movie,null,null, movie.getPosterPath());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView posterView;
        TextView voteRatedTV,movieTitleTV,releaseDateTV;
        CardView cardView;
        ImageButton addMovieImageButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            posterView=(ImageView) itemView.findViewById(R.id.posterGridMovieView);
            voteRatedTV=(TextView) itemView.findViewById(R.id.voteRatedGridTextView);
            movieTitleTV=(TextView) itemView.findViewById(R.id.movieTitleGridTextView);
            releaseDateTV=(TextView) itemView.findViewById(R.id.dateReleaseGridTextView);
            cardView=(CardView) itemView.findViewById(R.id.singleMovieGridCardView);
            addMovieImageButton=(ImageButton) itemView.findViewById(R.id.addMovieImageButton);
        }
    }
}
