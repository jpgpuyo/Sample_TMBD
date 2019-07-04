package mguell.sample_tmdb.deps;

import dagger.Module;
import dagger.Provides;
import mguell.sample_tmdb.data.repository.MovieDataRepository;
import mguell.sample_tmdb.domain.interactor.GetMoviesByPopularity;
import mguell.sample_tmdb.domain.interactor.GetMoviesByQuery;
import mguell.sample_tmdb.domain.repository.MovieRepository;
import mguell.sample_tmdb.presentation.presenter.LandingPresenter;

@Module
public class LandingFragmentModule {

    @Provides
    LandingPresenter provideLandingPresenter(GetMoviesByPopularity getMoviesByPopularity,
                                             GetMoviesByQuery getMoviesByQuery) {
        return new LandingPresenter(getMoviesByPopularity, getMoviesByQuery);
    }

    @Provides
    GetMoviesByPopularity provideGetMoviesByPopularity(MovieRepository movieRepository) {
        return new GetMoviesByPopularity(movieRepository);
    }

    @Provides
    GetMoviesByQuery provideGetMoviesByQuery(MovieRepository movieRepository) {
        return new GetMoviesByQuery(movieRepository);
    }
}
