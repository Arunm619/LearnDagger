package io.arunbuilds.learndagger.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import io.arunbuilds.learndagger.data.Resource;
import io.arunbuilds.learndagger.data.local.dao.MovieDao;
import io.arunbuilds.learndagger.data.local.enitity.MovieEntity;
import io.arunbuilds.learndagger.data.remote.api.MovieApiService;
import io.arunbuilds.learndagger.data.repository.MovieRepository;

public class MovieListViewModel extends ViewModel {
    private MovieRepository movieRepository;

    private MutableLiveData<Resource<List<MovieEntity>>> moviesLiveData = new MutableLiveData<>();

    @Inject
    public MovieListViewModel(MovieDao movieDao, MovieApiService movieApiService) {
        movieRepository = new MovieRepository(movieDao, movieApiService);
    }


    /*
     * Method called by UI to fetch movies list
     * */
    public void loadMoreMovies() {
        movieRepository.loadMoviesType()
                .subscribe(resource ->
                        getMoviesLiveData().postValue(resource));
    }

    public MutableLiveData<Resource<List<MovieEntity>>> getMoviesLiveData() {
        return moviesLiveData;
    }
}
