package com.example.netflop.data.data_source.remote_data_source;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.CombinedCredit;
import com.example.netflop.data.models.Credit;
import com.example.netflop.data.models.Genre;
import com.example.netflop.data.models.MovieCredit;
import com.example.netflop.data.models.MovieDetail;
import com.example.netflop.data.models.MovieImages;
import com.example.netflop.data.models.MovieVideos;
import com.example.netflop.data.models.PersonDetail;
import com.example.netflop.data.models.PersonImages;
import com.example.netflop.data.models.TVs.TVEpisodeCredit;
import com.example.netflop.data.models.TVs.TVEpisodeDetail;
import com.example.netflop.data.models.TVs.TVEpisodeImage;
import com.example.netflop.data.models.TVs.TVEpisodeVideo;
import com.example.netflop.data.models.TVs.TVSeasonsDetail;
import com.example.netflop.data.models.TVs.TVSeriesDetail;
import com.example.netflop.data.responses.NowPlayingResponse;
import com.example.netflop.data.responses.PopularPeopleResponse;
import com.example.netflop.data.responses.PopularResponse;
import com.example.netflop.data.responses.RecommendationMovieResponse;
import com.example.netflop.data.responses.ReviewResponse;
import com.example.netflop.data.responses.SearchMovieResponse;
import com.example.netflop.data.responses.SearchMultiResponse;
import com.example.netflop.data.responses.SearchPersonResponse;
import com.example.netflop.data.responses.TVs.AiringTodayResponse;
import com.example.netflop.data.responses.TVs.OnTheAirResponse;
import com.example.netflop.data.responses.TVs.PopularTVResponse;
import com.example.netflop.data.responses.TVs.TopRatedTVResponse;
import com.example.netflop.data.responses.TopRatedResponse;
import com.example.netflop.data.responses.TrendingMovieResponse;
import com.example.netflop.data.responses.TrendingPeopleResponse;
import com.example.netflop.data.responses.UpcomingResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @GET("movie/now_playing")
    Call<NowPlayingResponse> getNowPlaying();

    @GET("movie/now_playing")
    Call<NowPlayingResponse> getNowPlaying(@Query("page") int page);

    @GET("movie/popular")
    Call<PopularResponse> getPopular();
    @GET("movie/popular")
    Call<PopularResponse> getPopular(@Query("page") int page);

    @GET("movie/top_rated")
    Call<TopRatedResponse> getTopRated();

    @GET("movie/top_rated")
    Call<TopRatedResponse> getTopRated(@Query("page") int page);

    @GET("movie/upcoming")
    Call<UpcomingResponse> getUpcoming();

    @GET("movie/upcoming")
    Call<UpcomingResponse> getUpcoming(@Query("page") int page);

    @GET("movie/{movie_id}")
    Call<MovieDetail> getMovieByID(@Path("movie_id") int id);


    @GET("movie/{movie_id}/credits")
    Call<Credit> getMovieCredit(@Path("movie_id") int id);

    @GET("movie/{movie_id}/images")
    Call<MovieImages> getMovieImages(@Path("movie_id") int id);

    @GET("movie/{movie_id}/recommendations")
    Call<RecommendationMovieResponse> getRecommendationMovie(@Path("movie_id") int id);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewResponse> getReviewOfAMovie(@Path("movie_id") int id);

    @GET("movie/{movie_id}/videos")
    Call<MovieVideos> getMovieVideos(@Path("movie_id") int id);

    @GET("person/popular")
    Call<PopularPeopleResponse> getPopularPeople();

    @GET("person/popular")
    Call<PopularPeopleResponse> getPopularPeople(@Query("page") int page);

    @GET("person/{person_id}")
    Call<PersonDetail> getPersonDetail(@Path("person_id") int id);

    @GET("person/{person_id}/images")
    Call<PersonImages> getPersonImages(@Path("person_id") int id);

    @GET("person/{person_id}/movie_credits")
    Call<MovieCredit> getMoviesOfPerson(@Path("person_id") int id);

    @GET("person/{person_id}/combined_credits")
    Call<CombinedCredit> getCombinedCreditsOfPerson(@Path("person_id") int id);

    @GET("search/movie")
    Call<SearchMovieResponse> getSearchMovies( @Query("query") String query,
                                               @Query("include_adult") boolean includeAdult,
//                                               @Query("language") String language,
                                               @Query("page") int page);

    @GET("search/person")
    Call<SearchPersonResponse> getSearchPeople(@Query("query") String query,
                                               @Query("include_adult") boolean includeAdult,
//                                               @Query("language") String language,
                                               @Query("page") int page);

    @GET("search/multi")
    Call<SearchMultiResponse> getSearchMultiSource(
            @Query("query") String query,
            @Query("include_adult") boolean includeAdult,
//                                               @Query("language") String language,
            @Query("page") int page
    );

    @GET("trending/movie/day")
    Call<TrendingMovieResponse> getTrendingMovies();

    @GET("trending/movie/day")
    Call<TrendingMovieResponse> getTrendingMovies(@Query("page") int page);

    @GET("trending/person/day")
    Call<TrendingPeopleResponse> getTrendingPeople();

    @GET("trending/person/day")
    Call<TrendingPeopleResponse> getTrendingPeople(@Query("page") int page);

    @GET("genre/movie/list")
    Call<List<Genre>> getGenreMovieList();

    @GET("genre/tv/list")
    Call<List<Genre>> getGenreTVList();


    // TV
    @GET("tv/airing_today")
    Call<AiringTodayResponse> getAiringToDay(@Query("page") int page);

    @GET("tv/on_the_air")
    Call<OnTheAirResponse> getOnTheAir(@Query("page") int page);

    @GET("tv/popular")
    Call<PopularTVResponse> getPopularTV(@Query("page") int page);

    @GET("tv/top_rated")
    Call<TopRatedTVResponse> getTopRatedTV(@Query("page") int page);

    // TV Series
    @GET("tv/{series_id}")
    Call<TVSeriesDetail> getTVSeriesDetail(@Path("series_id") int id);

    // TV Season
    @GET("tv/{series_id}/season/{season_number}")
    Call<TVSeasonsDetail> getTVSeasonsDetail(@Path("series_id") int id,@Path("season_number") int number);

    // TV Episode

    @GET("tv/{series_id}/season/{season_number}/episode/{episode_number}")
    Call<TVEpisodeDetail> getTVEpisodeDetail(@Path("series_id") int id, @Path("season_number") int seasonNumber, @Path("episode_number") int episodeNumber);

    @GET("tv/{series_id}/season/{season_number}/episode/{episode_number}/images")
    Call<TVEpisodeImage> getTVEpisodeImages(@Path("series_id") int id, @Path("season_number") int seasonNumber, @Path("episode_number") int episodeNumber);

    @GET("tv/{series_id}/season/{season_number}/episode/{episode_number}/videos")
    Call<TVEpisodeVideo> getTVEpisodeVideos(@Path("series_id") int id, @Path("season_number") int seasonNumber, @Path("episode_number") int episodeNumber);

    @GET("tv/{series_id}/season/{season_number}/episode/{episode_number}/credits")
    Call<TVEpisodeCredit> getTVEpisodeCredits(@Path("series_id") int id, @Path("season_number") int seasonNumber, @Path("episode_number") int episodeNumber);
}
