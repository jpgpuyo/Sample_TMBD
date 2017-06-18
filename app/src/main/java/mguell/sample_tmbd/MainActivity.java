package mguell.sample_tmbd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.guell.mauricio.sample_tmbd.R;

import mguell.sample_tmbd.ui.view.LandingFragment;
import mguell.sample_tmbd.ui.view.NoInternetFragment;
import mguell.sample_tmbd.ui.view.NoResultsFragment;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Manager of the application. Contains the main layout that will be filled with
 * the different Fragment objects.
 */
public class MainActivity extends AppCompatActivity implements NoInternetFragment.RetryClickListener{

    private final String TAG = getClass().getSimpleName();

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindString(R.string.query_text_key)
    public String queryTextKey;

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
                if(newText.isEmpty()) {
                    Log.i("Typing", "empty");
                }
                Log.i("Typing", newText);
                final Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
                if(currentFragment instanceof LandingFragment) {
                    ((LandingFragment) currentFragment).getLandingPresenter().setQueryText(newText);
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
        if(mSearchView == null) {
            args.putString(queryTextKey, "");
        } else {
            args.putString(queryTextKey, mSearchView.getQuery().toString());
        }
        landingFragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, landingFragment)
                .commit();
    }
}
