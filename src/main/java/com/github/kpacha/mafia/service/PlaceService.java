package com.github.kpacha.mafia.service;

import com.github.kpacha.mafia.model.Gangster;
import com.github.kpacha.mafia.model.Place;
import com.github.kpacha.mafia.model.Visit;

public interface PlaceService {

    public Place getPlace(float lon, float lat);

    public Place save(Place place);

    public Visit visit(Gangster visitor, Place place);

    public Visit visit(Long visitorId, Place place);

    public Visit visit(Long visitorId, float lon, float lat);

}
