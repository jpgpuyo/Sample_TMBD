package mguell.sample_tmdb.domain.model;

public class Movie {

    private final String title;
    private final String overview;
    private final String releaseDate;
    private final String posterPath;

    public Movie(final String title,
                 final String overview,
                 final String releaseDate,
                 final String posterPath) {
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
