package com.arrk.shishir.arrktest.mainscreen;

import com.arrk.shishir.arrktest.base.BPresenter;
import com.arrk.shishir.arrktest.base.BView;

/**
 * This is the basic interface used to communicate with presenter
 */
public interface StarWarsCharacterDownloadContract {
    interface View extends BView<Presenter> {
    }

    interface Presenter extends BPresenter {
    }
}
