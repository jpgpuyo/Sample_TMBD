package mguell.sample_tmdb.data.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import mguell.sample_tmdb.data.entity.MovieEntity;
import mguell.sample_tmdb.data.net.response.MovieResponse;
import mguell.sample_tmdb.data.repository.datasource.MovieDataStore;
import mguell.sample_tmdb.domain.repository.MovieRepository;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class MovieDataRepositoryTest {

    @Mock
    private MovieDataStore networkMovieDataStore;

    private MovieRepository movieDataRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        movieDataRepository = new MovieDataRepository(networkMovieDataStore);
    }

    @Test
    public void should_return_a_list_with_popular_movies() {
        MovieResponse popularMoviesRespose = givenMoviesByPopularity();
        when(networkMovieDataStore.moviesByPopularity(anyInt())).thenReturn(Observable.just(popularMoviesRespose));

        movieDataRepository.moviesByPopularity(0)
                .test()
                .assertComplete()
                .assertValue(movies -> !movies.isEmpty());
    }

    @Test
    public void should_return_a_list_with_the_movie_that_matches_with_query() {
        MovieResponse moviesByQueryResponse = givenMoviesByPopularity();
        when(networkMovieDataStore.moviesByQuery(anyString(), anyInt())).thenReturn(Observable.just(moviesByQueryResponse));

        movieDataRepository.moviesByQuery("spiderman", 0)
                .test()
                .assertComplete()
                .assertValue(movies -> movies.get(0).getTitle().equals("Spider-Man: Far from Home"));
    }

    private MovieResponse givenMoviesByPopularity() {
        MovieResponse movieResponse = new MovieResponse();
        List<MovieEntity> movieEntityList = new ArrayList<>();

        MovieEntity spiderman = new MovieEntity(
                "Spider-Man: Far from Home",
                "Peter Parker and his friends go on a summer trip to Europe. However, they will hardly be able to rest - Peter will have to agree to help Nick Fury uncover the mystery of creatures that cause natural disasters and destruction throughout the continent.",
                "2019-06-28",
                "/2cAc4qH9Uh2NtSujJ90fIAMrw7T.jpg"
        );

        MovieEntity toyStory = new MovieEntity(
                "Toy Story 4",
                "Woody has always been confident about his place in the world and that his priority is taking care of his kid, whether that's Andy or Bonnie. But when Bonnie adds a reluctant new toy called \"Forky\" to her room, a road trip adventure alongside old and new friends will show Woody how big the world can be for a toy.",
                "2019-06-19",
                "/w9kR8qbmQ01HwnvK4alvnQ2ca0L.jpg"
        );

        MovieEntity shazam = new MovieEntity(
                "Shazam!",
                "A boy is given the ability to become an adult superhero in times of need with a single magic word.",
                "2019-03-23",
                "/xnopI5Xtky18MPhK40cZAGAOVeV.jpg"
        );

        movieEntityList.add(spiderman);
        movieEntityList.add(toyStory);
        movieEntityList.add(shazam);

        movieResponse.setMovies(movieEntityList);

        return movieResponse;
    }

    private MovieResponse givenMoviesByQuery() {
        MovieResponse movieResponse = new MovieResponse();
        List<MovieEntity> movieEntityList = new ArrayList<>();

        MovieEntity spiderman = new MovieEntity(
                "Spider-Man: Far from Home",
                "Peter Parker and his friends go on a summer trip to Europe. However, they will hardly be able to rest - Peter will have to agree to help Nick Fury uncover the mystery of creatures that cause natural disasters and destruction throughout the continent.",
                "2019-06-28",
                "/2cAc4qH9Uh2NtSujJ90fIAMrw7T.jpg"
        );

        movieEntityList.add(spiderman);

        movieResponse.setMovies(movieEntityList);

        return movieResponse;
    }

}