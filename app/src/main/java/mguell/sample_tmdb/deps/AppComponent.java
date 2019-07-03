package mguell.sample_tmdb.deps;


import javax.inject.Singleton;

import dagger.Component;
import mguell.sample_tmdb.presentation.view.landing.LandingView;
import mguell.sample_tmdb.presentation.view.no_internet.NoInternetFragment;


@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(LandingView landingView);
    void inject(NoInternetFragment noInternetFragment);
}