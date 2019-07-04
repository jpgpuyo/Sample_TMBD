package mguell.sample_tmdb.deps;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import mguell.sample_tmdb.presentation.view.landing.LandingFragment;

@Module
public abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = LandingFragmentModule.class)
    abstract LandingFragment landingFragment();
}
