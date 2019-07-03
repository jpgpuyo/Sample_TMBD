package mguell.sample_tmdb.utils;

import android.text.Html;

import androidx.annotation.NonNull;

/**
 * Class created for group methods that will be commonly used to work with String values.
 */

public final class StringUtils {

    private StringUtils() {
    }

    /**
     * Processes an html text to have the standards of an Android text.
     *
     * @param htmlString html text that is going to be processed.
     * @return String with the text processed as Android text.
     */
    public static String processHtmlString(@NonNull final String htmlString) {
        final String processedString;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            processedString = Html.fromHtml(htmlString, Html.FROM_HTML_MODE_COMPACT).toString();
        } else {
            processedString = Html.fromHtml(htmlString).toString();
        }
        return processedString;
    }
}
