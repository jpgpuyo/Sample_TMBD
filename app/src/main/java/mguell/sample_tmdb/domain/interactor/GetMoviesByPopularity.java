package mguell.sample_tmdb.domain.interactor;


import java.util.List;

import io.reactivex.Observable;
import mguell.sample_tmdb.domain.model.Movie;
import mguell.sample_tmdb.domain.repository.MovieRepository;

public class GetMoviesByPopularity extends UseCase<List<Movie>, Integer> {

    private final MovieRepository movieRepository;

    public GetMoviesByPopularity(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    Observable<List<Movie>> buildUseCaseObservable(final Integer page) {
        return this.movieRepository.moviesByPopularity(page);
    }
}
