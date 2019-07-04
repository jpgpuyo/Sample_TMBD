package mguell.sample_tmdb.deps;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import mguell.sample_tmdb.presentation.MainActivity;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivity();

}
