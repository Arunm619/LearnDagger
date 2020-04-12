package io.arunbuilds.learndagger.remote.api;

import io.reactivex.Observable;
import retrofit2.http.GET;


public interface MovieApiService {
    @GET("movie/popular?language=en-US&region=US&page=1")
    Observable<MovieApiService> fetchMovies();
}
