package mguell.sample_tmbd;

import android.app.Application;

import mguell.sample_tmbd.deps.AppComponent;
import mguell.sample_tmbd.deps.AppModule;
import mguell.sample_tmbd.deps.DaggerAppComponent;

/**
 * Created by Mauricio on 15/06/2017.
 */

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
