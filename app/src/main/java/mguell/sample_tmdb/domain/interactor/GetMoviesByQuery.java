package mguell.sample_tmdb.domain.interactor;

import java.util.List;

import io.reactivex.Observable;
import mguell.sample_tmdb.data.repository.MovieDataRepository;
import mguell.sample_tmdb.domain.model.Movie;
import mguell.sample_tmdb.domain.repository.MovieRepository;

public class GetMoviesByQuery extends UseCase<List<Movie>, GetMoviesByQuery.Params> {

    private final MovieRepository movieRepository;

    public GetMoviesByQuery(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    Observable<List<Movie>> buildUseCaseObservable(final Params params) {
        return movieRepository.moviesByQuery(params.query, params.page);
    }

    public static class Params {

        private final String query;
        private final int page;

        public Params(final String query,
                      final int page) {
            this.query = query;
            this.page = page;
        }
    }
}
