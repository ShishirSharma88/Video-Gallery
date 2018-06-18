package com.arrk.shishir.arrktest.mainscreen;

import android.os.AsyncTask;
import android.util.Log;

import com.arrk.shishir.arrktest.rest.RetroClient;
import com.arrk.shishir.arrktest.rest.RetroInterface;
import com.arrk.shishir.arrktest.taskdownloder.model.StarWars;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This class is presenter class take care about api related task and to update UI.
 */

public class StarWarsCharacterDownloadPresenter implements StarWarsCharacterDownloadContract.Presenter {

    private static String TAG = StarWarsCharacterDownloadPresenter.class.getSimpleName();

    private static StarWarsCharacterDownloadContract.View mdownloadView;

    public StarWarsCharacterDownloadPresenter(StarWarsCharacterDownloadContract.View view) {
        mdownloadView = view;
    }

    @Override
    public void start() {
        ((StarWarsCharacterFragment) mdownloadView).startSpin();
        CharacterRetrivalTask task = new CharacterRetrivalTask();
        task.execute("");
    }

    @Override
    public void getData() {
        ((StarWarsCharacterFragment) mdownloadView).startSpin();
        CharacterRetrivalTask task = new CharacterRetrivalTask();
        task.execute(StartWarsRepository.getInstance().getStarWars().getNext());
    }

    @Override
    public StarWars getCharacterData() {
        return StartWarsRepository.getInstance().getStarWars();
    }

    public static class CharacterRetrivalTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            Call<StarWars> call;
            RetroInterface apiService =
                    RetroClient.getClient().create(RetroInterface.class);

            if (strings == null || strings[0] == null) {
                return true;
            }

            if (strings[0].equals("") && StartWarsRepository.getInstance().getStarWars() == null) {
                call = apiService.getAllCharacters();
            } else {
                call = apiService.getCharacters(strings[0]);
            }

            call.enqueue(new Callback<StarWars>() {
                @Override
                public void onResponse(Call<StarWars> call, Response<StarWars> response) {
                    if (response != null && response.isSuccessful()) {
                        StartWarsRepository.getInstance().setStarWars(response.body());
                        try {
                            ((StarWarsCharacterFragment) mdownloadView).updateUI();
                        } catch (NullPointerException e) {
                            Log.i(TAG, "null pointer exception occured");
                        }
                    } else {
                        Log.i(TAG, "getCharacter api response is null");
                    }

                    if (StartWarsRepository.getInstance().getStarWars() == null) {
                        try {
                            ((StarWarsCharacterFragment) mdownloadView).updateUIforTryAgainPositive();
                        } catch (NullPointerException e) {
                            Log.i(TAG, "null pointer exception occured");
                        }
                    } else {
                        ((StarWarsCharacterFragment) mdownloadView).updateUIforTryAgainNegative();
                    }
                }

                @Override
                public void onFailure(Call<StarWars> call, Throwable t) {
                    Log.i(TAG, "getCharacter api failure, " + t.getMessage());

                    if (StartWarsRepository.getInstance().getStarWars() == null) {
                        try {
                            ((StarWarsCharacterFragment) mdownloadView).updateUIforTryAgainPositive();
                        } catch (NullPointerException e) {
                            Log.i(TAG, "null pointer exception occured");
                        }
                    } else {
                        ((StarWarsCharacterFragment) mdownloadView).updateUIforTryAgainNegative();
                    }
                }
            });

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            try {
                ((StarWarsCharacterFragment) mdownloadView).stopSpin();
            } catch (NullPointerException e) {
                Log.i(TAG, "null pointer exception");
            }
        }
    }
}
