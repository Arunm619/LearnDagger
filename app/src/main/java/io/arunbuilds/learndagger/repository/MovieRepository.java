package io.arunbuilds.learndagger.repository;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Singleton;

import io.arunbuilds.learndagger.NetworkBoundResource;
import io.arunbuilds.learndagger.Resource;
import io.arunbuilds.learndagger.local.dao.MovieDao;
import io.arunbuilds.learndagger.local.enitity.MovieEntity;
import io.arunbuilds.learndagger.remote.api.MovieApiService;
import io.arunbuilds.learndagger.remote.model.MovieApiResponse;
import io.reactivex.Flowable;
import io.reactivex.Observable;

@Singleton
public class MovieRepository {
    private MovieDao movieDao;
    private MovieApiService movieApiService;

    public MovieRepository(MovieDao movieDao, MovieApiService movieApiService) {
        this.movieDao = movieDao;
        this.movieApiService = movieApiService;
    }

    /*
     * We are using this method to fetch the movies list
     * NetworkBoundResource is part of the Android architecture
     * components. You will notice that this is a modified version of
     * that class. That class is based on LiveData but here we are
     * using Observable from RxJava.
     *
     * There are three methods called:
     * a. fetch data from server
     * b. fetch data from local
     * c. save data from api in local
     *
     * So basically we fetch data from server, store it locally
     * and then fetch data from local and update the UI with
     * this data.
     *
     * */

    public Observable<Resource<List<MovieEntity>>> loadMoviesType() {
        return new NetworkBoundResource<List<MovieEntity>, MovieApiResponse>() {

            @Override
            protected Flowable<List<MovieEntity>> loadFromDb() {
                List<MovieEntity> movies = movieDao.getMoviesByPage();
                if (movies == null || movies.isEmpty()) {
                    return Flowable.empty();
                }
                return Flowable.just(movies);
            }

            @Override
            protected void saveCallResult(@NonNull MovieApiResponse item) {
                movieDao.insertMovies(item.getResults());
            }

            @NonNull
            @Override
            protected Observable<Resource<MovieApiResponse>> createCall() {
                return movieApiService
                        .fetchMovies()
                        .flatMap(response ->
                                Observable.just(response == null ?
                                        Resource.error(null, "Error") :
                                        Resource.success(response)));
            }

            @Override
            protected boolean shouldFetch() {
                return true;
            }
        }.getAsObservable();
    }
}
