package com.arrk.shishir.arrktest.mainscreen;

import com.arrk.shishir.arrktest.taskdownloder.model.Result;
import com.arrk.shishir.arrktest.taskdownloder.model.StarWars;

import java.util.List;

/**
 * This model class stores data from API
 */
public class StartWarsRepository {
    public StarWars getStarWars() {
        return starWars;
    }

    public void setStarWars(StarWars starWars) {
        if (StartWarsRepository.starWars != null
                && StartWarsRepository.starWars.getResults().size() > 0
                && !StartWarsRepository.starWars.getNext().equals(starWars.getNext())) {
            List<Result> list = StartWarsRepository.starWars.getResults();
            list.addAll(starWars.getResults());
            StartWarsRepository.starWars.setResults(list);
            StartWarsRepository.starWars.setNext(starWars.getNext());
            StartWarsRepository.starWars.setPrevious(starWars.getPrevious());
        } else {
            StartWarsRepository.starWars = starWars;
        }
    }

    private static StarWars starWars;
    private static StartWarsRepository startWarsRepository;

    public static StartWarsRepository getInstance() {
        if (startWarsRepository == null) {
            startWarsRepository = new StartWarsRepository();
        }

        return startWarsRepository;
    }
}
