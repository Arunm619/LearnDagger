package io.arunbuilds.learndagger.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import io.arunbuilds.learndagger.local.dao.MovieDao;
import io.arunbuilds.learndagger.local.enitity.MovieEntity;

@Database(entities = {MovieEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao getMovieDao();
}
