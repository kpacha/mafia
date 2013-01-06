package com.github.kpacha.mafia.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.github.kpacha.mafia.model.Gangster;
import com.github.kpacha.mafia.model.Place;
import com.github.kpacha.mafia.model.Visit;
import com.github.kpacha.mafia.repository.PlaceRepository;
import com.github.kpacha.mafia.service.GangsterService;
import com.github.kpacha.mafia.service.PlaceService;

@Service
@Transactional
public class PlaceServiceImpl implements PlaceService {

    private static final Logger log = LoggerFactory
	    .getLogger(PlaceServiceImpl.class);

    private static final float COORDINATE_TOLERANCE = (float) 0.5;

    @Autowired
    private PlaceRepository repo;
    @Autowired
    private Neo4jOperations template;
    @Autowired
    private GangsterService gangsterService;

    @Override
    public Place getPlace(float lon, float lat) {
	log.debug("Looking for places near [" + lon + "," + lat + "]");
	EndResult<Place> candidatePlace = repo.findWithinBoundingBox(
		"placeLocation", lat - COORDINATE_TOLERANCE, lon
			- COORDINATE_TOLERANCE, lat + COORDINATE_TOLERANCE, lon
			+ COORDINATE_TOLERANCE);
	Place place = candidatePlace.singleOrNull();
	if (place == null) {
	    place = save(buildNewPlace(lon, lat));
	}
	return place;
    }

    private Place buildNewPlace(float lon, float lat) {
	log.debug("Creating a place at [" + lon + "," + lat + "]");
	Place place = new Place();
	place.setLocation(lon, lat);
	place.setName("Place @" + place.getWkt());
	return place;
    }

    @Override
    public Visit visit(Long visitorId, float lon, float lat) {
	return visit(visitorId, getPlace(lon, lat));
    }

    @Override
    public Visit visit(Long visitorId, Place place) {
	Assert.notNull(place);
	return visit(gangsterService.find(visitorId), place);
    }

    @Override
    public Visit visit(Gangster visitor, Place place) {
	log.debug("Visiting the place [" + place.getId() + "] "
		+ place.getName());
	Visit visit = new Visit();
	visit.setVisitor(visitor);
	visit.setPlace(place);
	visit.setVisitedAt(new Date());
	visit.setReason("Place " + place.getWkt() + " is visited by " + visitor);
	return template.save(visit);
    }

    @Override
    public Place save(Place place) {
	return repo.save(place);
    }

}
