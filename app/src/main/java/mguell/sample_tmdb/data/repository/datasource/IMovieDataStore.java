package mguell.sample_tmdb.data.repository.datasource;

import io.reactivex.Observable;
import mguell.sample_tmdb.data.net.response.MovieResponse;

public interface IMovieDataStore {

    Observable<MovieResponse> moviesByPopularity(final int page);

    Observable<MovieResponse> moviesByQuery(final String query,
                                            final int page);
}
