package com.example.netflop.ui.favourite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.netflop.R;
import com.example.netflop.databinding.FavouriteCardViewBinding;
import com.example.netflop.databinding.FragmentFavouriteBinding;
import com.example.netflop.databinding.FragmentHomeBinding;
import com.example.netflop.viewmodel.local.FavouriteMediaViewModel;

public class FavouriteFragment extends Fragment {

    private FragmentFavouriteBinding binding;
    FavouriteCardViewBinding viewMovieCard,viewPeopleCard,viewTVSeriesCard,viewTVSeasonCard,viewTVEpisodeCard;
//    View viewPeopleCard;
//    View viewTVSeriesCard;
//    View viewTVSeasonCard;
//    View viewTVEpisodeCard;
    ImageView nextImageView;

    // view model
    FavouriteMediaViewModel favouriteMediaViewModel;
    //

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getBinding();
        display();
        onHandleTapping();
        return root;
    }
    private void getBinding(){
        viewMovieCard=FavouriteCardViewBinding.bind(binding.favouriteMovieCard.getRoot());
        viewPeopleCard=FavouriteCardViewBinding.bind(binding.favouritePeopleCard.getRoot());
        viewTVSeriesCard=FavouriteCardViewBinding.bind(binding.favouriteTVSeriesCard.getRoot());
        viewTVSeasonCard=FavouriteCardViewBinding.bind(binding.favouriteTVSeasonCard.getRoot());
        viewTVEpisodeCard=FavouriteCardViewBinding.bind(binding.favouriteTVEpisodeCard.getRoot());
        nextImageView=binding.seeAllFavourite;
    }
    private void display(){
        // movie
        viewMovieCard.iconFavouriteView.setImageResource(R.drawable.movie);
        viewMovieCard.titleFavouriteCardView.setText("Favourite movies");
        // people
        viewPeopleCard.iconFavouriteView.setImageResource(R.drawable.people);
        viewPeopleCard.titleFavouriteCardView.setText("Favourite people");
        // tv series
        viewTVSeriesCard.iconFavouriteView.setImageResource(R.drawable.tv_series);
        viewTVSeriesCard.titleFavouriteCardView.setText("Favourite TV Series");
        // tv season
        viewTVSeasonCard.iconFavouriteView.setImageResource(R.drawable.tv_season);
        viewTVSeasonCard.titleFavouriteCardView.setText("Favourite TV Seasons");
        // tv episodes
        viewTVEpisodeCard.iconFavouriteView.setImageResource(R.drawable.tv_episode);
        viewTVEpisodeCard.titleFavouriteCardView.setText("Favourite TV Episodes");
    }
    private void onHandleTapping(){
        // movie
        binding.favouriteMovieCard.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), AllFavouriteMovieActivity.class);
                startActivity(intent);
            }
        });
        // people
        binding.favouritePeopleCard.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), AllFavouritePeopleActivity.class);
                startActivity(intent);
            }
        });
        // tv series
        binding.favouriteTVSeriesCard.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), AllFavouriteTVSeriesActivity.class);
                startActivity(intent);
            }
        });
        // tv season
        binding.favouriteTVSeasonCard.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), AllFavouriteTVSeasonActivity.class);
                startActivity(intent);
            }
        });
        // tv episodes
        binding.favouriteTVEpisodeCard.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), AllFavouriteEpisodeActivity.class);
                startActivity(intent);
            }
        });
        // next
       nextImageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(getActivity(), AllFavouriteMediaActivity.class);
               startActivity(intent);
           }
       });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}