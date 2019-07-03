package mguell.sample_tmdb;

import android.app.Application;

import mguell.sample_tmdb.deps.AppComponent;
import mguell.sample_tmdb.deps.AppModule;
import mguell.sample_tmdb.deps.DaggerAppComponent;


public class App extends Application {

    private AppComponent appComponent;

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = initDagger(this);
    }

    protected AppComponent initDagger(App application) {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .build();
    }
}
