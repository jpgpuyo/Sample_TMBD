package mguell.sample_tmdb.deps;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mguell.sample_tmdb.data.repository.MovieDataRepository;
import mguell.sample_tmdb.data.repository.datasource.MovieDataStore;
import mguell.sample_tmdb.domain.repository.MovieRepository;

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    MovieRepository provideMovieRepository() {
        return new MovieDataRepository(new MovieDataStore());
    }

}