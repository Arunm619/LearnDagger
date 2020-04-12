package io.arunbuilds.learndagger.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.arunbuilds.learndagger.local.enitity.MovieEntity;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertMovies(List<MovieEntity> movies);

    @Query("select * from 'MovieEntity';")
    List<MovieEntity> getMoviesByPage();
}
