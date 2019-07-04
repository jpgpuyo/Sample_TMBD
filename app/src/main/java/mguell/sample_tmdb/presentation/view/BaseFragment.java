package mguell.sample_tmdb.presentation.view;

import android.content.Context;

import androidx.fragment.app.Fragment;

import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        initDagger();
        super.onAttach(context);
    }

    private void initDagger() {
        AndroidSupportInjection.inject(this);
    }
}
