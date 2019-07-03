package com.guell.mauricio.sample_tmbd;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import mguell.sample_tmdb.data.net.TMDBService;
import mguell.sample_tmdb.data.net.response.MovieResponse;
import mguell.sample_tmdb.data.repository.MovieDataRepository;
import mguell.sample_tmdb.data.repository.datasource.MovieDataStore;
import mguell.sample_tmdb.utils.Constants;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test for StringUtils.
 */

@RunWith(MockitoJUnitRunner.class)
public class TMBDServiceTest {

    @Mock
    private TMDBService tmdbService = mock(TMDBService.class);


    @Test
    public void shouldReturnErrorState() {

        final MovieDataRepository movieDataRepository = new MovieDataRepository();

        movieDataRepository.moviesByPopularity(anyInt());
        verify(movieCache).getMovies((MovieDataSource.LoadMoviesCallback) Mockito.any());
    }
}
