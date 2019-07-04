package mguell.sample_tmdb.presentation;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.guell.mauricio.sample_tmbd.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import mguell.sample_tmdb.presentation.view.landing.LandingFragment;
import mguell.sample_tmdb.presentation.view.no_internet.NoInternetFragment;
import mguell.sample_tmdb.presentation.view.no_results.NoResultsFragment;
import mguell.sample_tmdb.utils.Constants;

/**
 * Manager of the application. Contains the main layout that will be filled with
 * the different Fragment objects.
 */
public class MainActivity extends BaseActivity implements NoInternetFragment.RetryClickListener {

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    private SearchView mSearchView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            openLandingFragment();
        }
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(final String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                final Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
                if (currentFragment instanceof LandingFragment) {
                    ((LandingFragment) currentFragment).onQueryTextChange(newText);
                } else if (currentFragment instanceof NoResultsFragment) {
                    openLandingFragment();
                }
                return false;
            }
        });
        return true;
    }

    /**
     * Opens a new LandingFragment in order to try to connect to the TMBD API, passing
     * the current text query at the SearchView.
     */
    @Override
    public void retryConnection() {
        openLandingFragment();
    }

    private void openLandingFragment() {
        final LandingFragment landingFragment = LandingFragment.newInstance();
        final Bundle args = new Bundle();
        if (mSearchView == null || mSearchView.getQuery() == null) {
            args.putString(Constants.QUERY_TEXT_KEY, "");
        } else {
            args.putString(Constants.QUERY_TEXT_KEY, mSearchView.getQuery().toString());
        }
        landingFragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, landingFragment)
                .commit();
    }

    public void openNoResultsFragment(final String queryText) {
        final NoResultsFragment noResultsFragment = NoResultsFragment.newInstance();
        final Bundle args = new Bundle();
        args.putString(Constants.QUERY_TEXT_KEY, queryText);
        noResultsFragment.setArguments(args);
        try {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, noResultsFragment)
                    .commit();
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void openConnectionError() {
        try {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, NoInternetFragment.newInstance())
                    .commit();
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }
}
