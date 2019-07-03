package mguell.sample_tmdb.domain.repository;


import java.util.List;

import io.reactivex.Observable;
import mguell.sample_tmdb.domain.model.Movie;

public interface MovieRepository {

    Observable<List<Movie>> moviesByPopularity(final int page);

    Observable<List<Movie>> moviesByQuery(final String query,
                                          final int page);
}
