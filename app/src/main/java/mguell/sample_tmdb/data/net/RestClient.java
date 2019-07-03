package mguell.sample_tmdb.data.net;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import mguell.sample_tmdb.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Client of the TMDB API, groups all the methods related to the interaction with it.
 */

public class RestClient {

    private static TMDBService mTMBDService;

    public static TMDBService getTMBDService() {
        if (mTMBDService == null) {
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.TMDB_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            mTMBDService = retrofit.create(TMDBService.class);
        }
        return mTMBDService;
    }
}
