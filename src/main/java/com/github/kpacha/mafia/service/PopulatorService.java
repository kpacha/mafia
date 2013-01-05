package com.github.kpacha.mafia.service;

import com.github.kpacha.mafia.model.Gangster;
import com.github.kpacha.mafia.model.Place;

public interface PopulatorService {

    public int populate(int deep);

    public Gangster buildGangster(int maxSubordinates, int level, int deep);

    public Place buildPlace(float lon, float lat);
}
