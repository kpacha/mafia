package com.github.kpacha.mafia.test.service;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.github.kpacha.mafia.model.Gangster;
import com.github.kpacha.mafia.model.Place;
import com.github.kpacha.mafia.model.Visit;
import com.github.kpacha.mafia.service.PlaceService;
import com.github.kpacha.mafia.test.PlaceAbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PlaceServiceTest extends PlaceAbstractTest {

    @Autowired
    private PlaceService service;

    private static final float BADABING_LON = (float) -74.04755;
    private static final float BADABING_LAT = (float) 40.877696;

    private Place badaBing;

    @Before
    public void setUp() throws InterruptedException {
	badaBing = repo.save(buildPlace(BADABING_LON, BADABING_LAT));
    }

    @Test
    @Transactional
    public void getPlaceByCoordinates() {
	Place receivedBadaBing = service.getPlace(BADABING_LON, BADABING_LAT);
	Assert.assertEquals(badaBing.getWkt(), receivedBadaBing.getWkt());
	Assert.assertEquals(badaBing.getId(), receivedBadaBing.getId());
    }

    @Test
    @Transactional
    public void getPlaceByCoordinatesGetTheNearPlace() {
	Place receivedBadaBing = service.getPlace(
		(float) (BADABING_LON + 0.01), (float) (BADABING_LAT - 0.01));
	Assert.assertEquals(badaBing.getWkt(), receivedBadaBing.getWkt());
	Assert.assertEquals(badaBing.getId(), receivedBadaBing.getId());
    }

    @Test
    @Transactional
    public void getPlaceByCoordinatesReturnsNewWhenNoNearPlaceIsFound() {
	Place receivedPlace = service
		.getPlace(BADABING_LON + 100, BADABING_LAT);
	Assert.assertFalse(badaBing.getWkt().equals(receivedPlace.getWkt()));
    }

    @Test
    @Transactional
    public void theVisitMethodCreatesAVisitRelationship() {
	Gangster tonySoprano = buildGangster();
	Visit visit = service.visit(tonySoprano, badaBing);
	Assert.assertEquals(badaBing.getId(), visit.getPlace().getId());
	Assert.assertEquals(tonySoprano.getNodeId(),
		((Gangster) (visit.getVisitor())).getNodeId());
	Assert.assertNotNull(visit.getId());
    }

}
