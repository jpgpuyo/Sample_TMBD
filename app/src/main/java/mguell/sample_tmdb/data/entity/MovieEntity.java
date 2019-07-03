package mguell.sample_tmdb.data.entity;

import com.google.gson.annotations.SerializedName;

public class MovieEntity {

    @SerializedName("title")
    private final String title;
    @SerializedName("overview")
    private final String overview;
    @SerializedName("release_date")
    private final String releaseDate;
    @SerializedName("poster_path")
    private final String posterPath;

    public MovieEntity(final String title,
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
