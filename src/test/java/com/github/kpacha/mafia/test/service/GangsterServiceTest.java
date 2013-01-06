package com.github.kpacha.mafia.test.service;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.github.kpacha.mafia.model.Gangster;
import com.github.kpacha.mafia.service.GangsterService;
import com.github.kpacha.mafia.test.GangsterAbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class GangsterServiceTest extends GangsterAbstractTest {

    @Autowired
    private GangsterService service;

    @Test
    public void enrolingASubordinateShouldAddAManagerToTheBoss() {
	int initialManagedSize = tonySoprano.getManaged().size();
	service.enroleSubordinate(tonySoprano, paulie);
	// new entities had been added to the related collections
	Assert.assertEquals(initialManagedSize + 1, tonySoprano.getManaged()
		.size());
    }

    @Test
    public void enrolingASubordinateShouldAddASubordinate() {
	int initialSubordinateSize = tonySoprano.getSubordinates().size();
	tonySoprano = service.save(service.enroleSubordinate(tonySoprano,
		paulie));
	// new entities had been added to the related collections
	Assert.assertEquals(initialSubordinateSize + 1, tonySoprano
		.getSubordinates().size());
    }

    @Test
    public void enrolingASubordinateShouldAddAManagerWithTheRightSubordinate() {
	service.enroleSubordinate(tonySoprano, paulie);
	paulie = service.getUpdatedInstance(paulie);
	// the subordinate is accessible through the related collections
	assertEqualGangsters(
		paulie,
		service.find(tonySoprano.getManaged().iterator().next()
			.getSubordinate().getNodeId()));
    }

    @Test
    public void enrolingASubordinateShouldAddsTheRightSubordinate() {
	tonySoprano = service.save(service.enroleSubordinate(tonySoprano,
		paulie));
	paulie = service.getUpdatedInstance(paulie);
	// the subordinate is accessible through the related collections
	assertEqualGangsters(
		paulie,
		service.find(tonySoprano.getSubordinates().iterator().next()
			.getNodeId()));
    }

    @Test
    public void enrolingASubordinateShouldAddAManagerToTheSubordinate() {
	service.save(service.enroleSubordinate(tonySoprano, paulie));
	Gangster persitedPaulie = service.getUpdatedInstance(paulie);

	// new entities had been added to the related collections
	Assert.assertEquals(paulie.getManagers().size() + 1, persitedPaulie
		.getManagers().size());
    }

    @Test
    public void enrolingASubordinateShouldAddABoss() {
	service.save(service.enroleSubordinate(tonySoprano, paulie));
	Gangster persitedPaulie = service.getUpdatedInstance(paulie);

	// new entities had been added to the related collections
	Assert.assertEquals(paulie.getBosses().size() + 1, persitedPaulie
		.getBosses().size());
    }

    @Test
    public void enrolingASubordinateShouldAddAManagerWithTheRightBoss() {
	tonySoprano = service.save(service.enroleSubordinate(tonySoprano,
		paulie));
	Gangster persitedPaulie = service.getUpdatedInstance(paulie);

	// the boss is accessible throught the rellated collections
	assertEqualGangsters(
		tonySoprano,
		service.find(persitedPaulie.getManagers().iterator().next()
			.getBoss().getNodeId()));
    }

    @Test
    public void enrolingASubordinateShouldAddTheRightBoss() {
	tonySoprano = service.save(service.enroleSubordinate(tonySoprano,
		paulie));
	Gangster persitedPaulie = service.getUpdatedInstance(paulie);

	// the boss is accessible throught the rellated collections
	assertEqualGangsters(
		tonySoprano,
		service.find(persitedPaulie.getBosses().iterator().next()
			.getNodeId()));
    }

    @Test
    public void testGetLevel() {
	Assert.assertTrue(0 == service.getLevel(tonySoprano));
	Assert.assertTrue(0 == service.getLevel(paulie));

	Gangster persitedTonySoprano = service.save(service.enroleSubordinate(
		tonySoprano, paulie));
	Gangster persitedPaulie = service.getUpdatedInstance(paulie);

	Assert.assertTrue(0 == service.getLevel(persitedTonySoprano));
	Assert.assertTrue(1 == service.getLevel(persitedPaulie));
    }

    @Test
    public void testFind() {
	Gangster persistedTonySoprano = service.find(tonySoprano.getNodeId());
	assertEqualGangsters(tonySoprano, persistedTonySoprano);
    }
}
