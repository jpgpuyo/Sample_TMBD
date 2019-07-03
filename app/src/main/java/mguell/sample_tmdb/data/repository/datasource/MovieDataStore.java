package mguell.sample_tmdb.data.repository.datasource;

import io.reactivex.Observable;
import mguell.sample_tmdb.data.net.RestClient;
import mguell.sample_tmdb.data.net.response.MovieResponse;
import mguell.sample_tmdb.utils.Constants;


public class MovieDataStore implements IMovieDataStore {

    @Override
    public Observable<MovieResponse> moviesByPopularity(final int page) {
        return RestClient.getTMBDService().moviesByPopularity(Constants.TMDB_API_KEY, page);
    }

    @Override
    public Observable<MovieResponse> moviesByQuery(final String query, final int page) {
        return RestClient.getTMBDService().moviesByQuery(Constants.TMDB_API_KEY, query, page);
    }
}
