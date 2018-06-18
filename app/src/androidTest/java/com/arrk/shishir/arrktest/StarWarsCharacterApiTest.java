package com.arrk.shishir.arrktest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.arrk.shishir.arrktest.mainscreen.StarWarsCharacterDownloadPresenter;
import com.arrk.shishir.arrktest.mainscreen.StartWarsRepository;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class StarWarsCharacterApiTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.arrk.shishir.arrktest", appContext.getPackageName());
    }

    @Test
    public void getCharacterApiTest() {

        StarWarsCharacterDownloadPresenter.CharacterRetrivalTask task
                = new StarWarsCharacterDownloadPresenter.CharacterRetrivalTask();
        task.execute("");

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(StartWarsRepository.getInstance().getStarWars().getResults().size() > 0, true);
    }
}
