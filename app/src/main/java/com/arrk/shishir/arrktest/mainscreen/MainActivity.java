package com.arrk.shishir.arrktest.mainscreen;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.arrk.shishir.arrktest.R;

/**
 * This is the main activity on which other fragments are depended
 */
public class MainActivity extends AppCompatActivity {

    private StarWarsCharacterDownloadPresenter starWarsCharacterDownloadPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        StarWarsCharacterFragment starWarsCharacterFragment
                = (StarWarsCharacterFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (starWarsCharacterFragment == null) {
            starWarsCharacterFragment = StarWarsCharacterFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentFrame, starWarsCharacterFragment);
            transaction.commit();
        }

        starWarsCharacterDownloadPresenter
                = new StarWarsCharacterDownloadPresenter(starWarsCharacterFragment);
        starWarsCharacterFragment.setPresenter(starWarsCharacterDownloadPresenter);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
