package mguell.sample_tmdb.presentation.view.no_results;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.guell.mauricio.sample_tmbd.R;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import mguell.sample_tmdb.utils.Constants;

/**
 * Represents the no results Fragment, with a message explaining that the current query
 * has not any linked movie.
 */

public class NoResultsFragment extends Fragment {

    @BindView(R.id.no_results_found_text)
    TextView noResultsTextView;

    @BindString(R.string.no_results_found_for)
    String noResultsForText;

    public static NoResultsFragment newInstance() {
        return new NoResultsFragment();
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.no_results_found_fragment, container, false);
        ButterKnife.bind(this, view);
        final String noResultsForCompleteText = String.format("%s %s",
                noResultsForText,
                getArguments().getString(Constants.QUERY_TEXT_KEY, ""));
        noResultsTextView.setText(noResultsForCompleteText);
        return view;
    }
}
