package mguell.sample_tmbd.deps;

import android.app.Application;

import javax.inject.Singleton;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mauricio on 15/06/2017.
 */
@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }

}
