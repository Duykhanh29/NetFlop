package com.example.netflop.utils.listeners;

import com.example.netflop.data.models.remote.people.Cast;
import com.example.netflop.data.models.remote.movies.Movie;
import com.example.netflop.data.models.remote.people.Person;

public interface ItemTouchHelperAdapter {
    void onMovieClick(Movie movie);
    void onPersonClick(Person p);
    void onCastClick(Cast cast);
}
