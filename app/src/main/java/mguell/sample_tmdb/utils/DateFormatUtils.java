package mguell.sample_tmdb.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public final class DateFormatUtils {

    private DateFormatUtils() {
    }

    /**
     * Formats the movie release date from the TMDB API and returns its year as an int value.
     * If the date is not available, returns the current year.
     *
     * @return int with the release year of the movie.
     */
    public static int getYear(final String releaseDate) {
        final SimpleDateFormat sdf = new SimpleDateFormat(
                Constants.TMDB_DATE_FORMAT,
                Locale.getDefault());
        final Calendar calendar = new GregorianCalendar();
        try {
            calendar.setTime(sdf.parse(releaseDate));
        } catch (final ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        return calendar.get(Calendar.YEAR);
    }
}
