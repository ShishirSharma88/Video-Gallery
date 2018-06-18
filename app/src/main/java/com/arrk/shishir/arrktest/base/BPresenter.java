package com.arrk.shishir.arrktest.base;

import com.arrk.shishir.arrktest.taskdownloder.model.StarWars;

public interface BPresenter {
    void start();
    void getData();
    StarWars getCharacterData();
}
