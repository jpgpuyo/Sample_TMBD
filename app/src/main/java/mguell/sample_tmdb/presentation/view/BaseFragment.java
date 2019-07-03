package mguell.sample_tmdb.presentation.view;


import androidx.fragment.app.Fragment;

import mguell.sample_tmdb.App;


public abstract class BaseFragment extends Fragment {

    protected App getApp() {
        return ((App) getContext().getApplicationContext());
    }
}
