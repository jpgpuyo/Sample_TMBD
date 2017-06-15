package mguell.sample_tmbd.deps;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mguell.sample_tmbd.network.RestClient;

/**
 * Created by Mauricio on 15/06/2017.
 */

@Module
class TMBDModule {

    @Provides
    @Singleton
    RestClient.TMDBService provideTMBDService() {
        return RestClient.getTMBDService();
    }
}
