package mguell.sample_tmdb.data.repository;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.List;

import io.reactivex.Observable;
import mguell.sample_tmdb.data.entity.mapper.MovieMapper;
import mguell.sample_tmdb.data.repository.datasource.MovieDataStore;
import mguell.sample_tmdb.domain.model.Movie;
import mguell.sample_tmdb.domain.repository.MovieRepository;

public class MovieDataRepository implements MovieRepository {

    private final MovieMapper movieMapper;

    public MovieDataRepository() {
        this.movieMapper = new MovieMapper();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Observable<List<Movie>> moviesByPopularity(final int page) {
        return new MovieDataStore().moviesByPopularity(page).map(movieMapper::transformList);
    }

    @Override
    public Observable<List<Movie>> moviesByQuery(final String query, final int page) {
        return new MovieDataStore().moviesByQuery(query, page).map(movieMapper::transformList);
    }
}
