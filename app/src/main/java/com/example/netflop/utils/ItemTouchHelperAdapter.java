package com.example.netflop.utils;

import com.example.netflop.data.models.Cast;
import com.example.netflop.data.models.Movie;
import com.example.netflop.data.models.Person;

public interface ItemTouchHelperAdapter {
    void onMovieClick(Movie movie);
    void onPersonClick(Person p);
    void onCastClick(Cast cast);
}
