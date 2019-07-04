package mguell.sample_tmdb.presentation.view.no_internet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.guell.mauricio.sample_tmbd.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

/**
 * Represents the connection error Fragment, with an error message and a clickable text
 * to retry the connection.
 */

public class NoInternetFragment extends Fragment {

    @BindView(R.id.retry_text)
    TextView retryText;

    private RetryClickListener mCallback;

    public static NoInternetFragment newInstance() {
        return new NoInternetFragment();
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.no_internet_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        try {
            mCallback = (RetryClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    /**
     * Action of touching the retry TextView, if the event is the start of the touch event,
     * changes the color of the TextView (in order to give some feedback to the user), if the
     * event is the finish, tries to connect to the API via the MainActivity.
     *
     * @param view        clicked TextView.
     * @param motionEvent press or stop pressing the TextView event.
     * @return true if the method is correctly finished, false otherwise.
     */
    @OnTouch(R.id.retry_text)
    boolean retry(final View view, final MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            retryText.setTextColor(ContextCompat.getColor(getContext(), R.color.accent_dark));
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            mCallback.retryConnection();
        }
        return true;
    }

    public interface RetryClickListener {
        void retryConnection();
    }

}
