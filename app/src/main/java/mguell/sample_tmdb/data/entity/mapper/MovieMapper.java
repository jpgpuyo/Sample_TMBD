package mguell.sample_tmdb.data.entity.mapper;


import java.util.ArrayList;
import java.util.List;

import mguell.sample_tmdb.data.entity.MovieEntity;
import mguell.sample_tmdb.data.net.response.MovieResponse;
import mguell.sample_tmdb.domain.model.Movie;
import mguell.sample_tmdb.utils.Constants;
import mguell.sample_tmdb.utils.StringUtils;

public class MovieMapper {

    public MovieMapper() {
    }

    public List<Movie> transformList(final MovieResponse movieResponse) {
        final List<Movie> townships = new ArrayList<>();
        for (MovieEntity movieEntity : movieResponse.getMovies()) {
            if (movieEntity != null) {
                townships.add(transform(movieEntity));
            }
        }
        return townships;
    }

    private Movie transform(MovieEntity movieEntity) {
        Movie movie = null;
        if (movieEntity != null) {
            movie = new Movie(
                    parseTitle(movieEntity.getTitle()),
                    parseOverview(movieEntity.getOverview()),
                    movieEntity.getReleaseDate(),
                    parsePosterPath(movieEntity.getPosterPath()));
        }
        return movie;
    }

    private String parseTitle(final String title) {
        return StringUtils.processHtmlString(title);
    }

    private String parseOverview(final String overview) {
        return StringUtils.processHtmlString(overview);
    }

    /**
     * Generates the movie poster URL by adding the root URL to the movie poster path.
     * If there is not any movie poster path available, returns an empty String.
     *
     * @return String with the movie poster URL.
     */
    private String parsePosterPath(final String posterPath) {
        if (posterPath == null) {
            return "";
        }
        return String.format("%s%s", Constants.TMDB_POSTER_URL, posterPath);
    }
}
