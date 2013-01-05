package com.github.kpacha.mafia.service;

import com.github.kpacha.mafia.model.Gangster;

public interface PopulatorService {

    public int populate(int deep);

    public Gangster buildGangster(int maxSubordinates, int level, int deep);
}
