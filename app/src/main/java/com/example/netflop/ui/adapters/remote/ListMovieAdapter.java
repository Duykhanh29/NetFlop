package com.example.netflop.ui.adapters.remote;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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
import com.example.netflop.constants.enums.WatchStatus;
import com.example.netflop.data.models.local.FavouriteMedia;
import com.example.netflop.data.models.remote.movies.Movie;
import com.example.netflop.utils.CommonMethods;
import com.example.netflop.utils.listeners.ItemTouchHelperAdapter;
import com.example.netflop.viewmodel.local.FavouriteMediaViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.ViewHolder> implements Filterable{
    List<Movie> listMovie;
    Context context;
    int layout;
    ItemTouchHelperAdapter itemTouchMovieHelper;
    FavouriteMediaViewModel favouriteMediaViewModel;
    private List<FavouriteMedia> favouriteData = new ArrayList<>();

    public ListMovieAdapter(List<Movie> listMovie, Context context,int layout,ItemTouchHelperAdapter itemTouchMovieHelper,FavouriteMediaViewModel favouriteMediaViewModel) {
        this.listMovie = listMovie;
        this.context = context;
        this.layout=layout;
        this.itemTouchMovieHelper=itemTouchMovieHelper;
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
        View view= LayoutInflater.from(context).inflate(layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie=listMovie.get(position);
        holder.titleTextView.setText(movie.getTitle());
        holder.voteRatedTextView.setText(movie.getVoteAverage()+"");
        String year= CommonMethods.getYearByReleaseDate(movie.getReleaseDate());
        holder.releaseDateTV.setText(year);
        String imageURL= URLConstants.imageURL+movie.getPosterPath();
        Log.d("TAG","URL is"+imageURL);
        Glide.with(context).load(imageURL).placeholder(R.drawable.place_holder).into(holder.posterView);
        holder.movieCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemTouchMovieHelper.onMovieClick(movie);
            }
        });


        Optional<FavouriteMedia> optionalFavouriteMedia=this.favouriteData.stream().filter(favouriteMedia -> favouriteMedia.getMediaID()==movie.getID()).findFirst();
        if(optionalFavouriteMedia.isPresent()){
            holder.addMovieButton.setImageResource(R.drawable.remove);
        }else {
            holder.addMovieButton.setImageResource(R.drawable.add);
        }
        holder.addMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(optionalFavouriteMedia.isPresent()){
                    favouriteMediaViewModel.deleteFavouriteMedia(optionalFavouriteMedia.get().getId());
                }else{
                    favouriteMediaViewModel.insertFavouriteMedia(movie.getID(),movie.getTitle(), TypeOfMedia.movie,null,null,movie.getPosterPath(), WatchStatus.UNWATCH);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public  static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView titleTextView,releaseDateTV,voteRatedTextView;
        CardView movieCardView;
        ImageView posterView;
        ImageButton addMovieButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView=(TextView) itemView.findViewById(R.id.movieTitleTextView);
            releaseDateTV=(TextView) itemView.findViewById(R.id.dateReleaseTextView);
            voteRatedTextView=(TextView) itemView.findViewById(R.id.voteRatedTextView);
            movieCardView=(CardView) itemView.findViewById(R.id.singleMovieCardView);
            posterView=(ImageView) itemView.findViewById(R.id.posterMovieView);
            addMovieButton=(ImageButton) itemView.findViewById(R.id.addMovieButton);
        }
    }
}
