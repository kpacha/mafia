package com.github.kpacha.mafia.model;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.github.kpacha.mafia.repository.GangsterRepository;
import com.github.kpacha.mafia.service.GangsterService;
import com.github.kpacha.mafia.service.PopulatorService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/gangster-test-context.xml" })
@Transactional
public class GangsterTest {

    @Autowired
    private GangsterRepository repo;
    @Autowired
    private GangsterService service;
    @Autowired
    private PopulatorService populator;

    @After
    public void cleanDb() throws InterruptedException {
	repo.deleteAll();
	Thread.sleep(1000);
    }

    @Test
    @Transactional
    public void persistedGangsterShouldBeRetrievableFromGraphDb() {
	Gangster tonySoprano = repo.save(buildGangster());
	Gangster retrievedGangster = repo.findOne(tonySoprano.getNodeId());
	assertEqualGangsters(tonySoprano, retrievedGangster);
    }

    @Test
    @Transactional
    public void enrolingASubordinateShouldAlterTheBossEntity() {
	Gangster paulie = buildGangster();
	paulie.setName("Paulie 'Walnuts' Gualtieri");
	Gangster persitedPaulie = repo.save(paulie);

	Gangster tonySoprano = buildGangster();
	Gangster persitedTonySoprano = repo.save(tonySoprano);

	service.enroleSubordinate(persitedTonySoprano, persitedPaulie);

	persitedTonySoprano = repo.findOne(persitedTonySoprano.getNodeId());
	// new entities had been added to the related collections
	Assert.assertEquals(tonySoprano.getManaged().size() + 1,
		persitedTonySoprano.getManaged().size());
	Assert.assertEquals(tonySoprano.getSubordinates().size() + 1,
		persitedTonySoprano.getSubordinates().size());

	// the subordinate is accessible throught the rellated collections
	persitedPaulie = repo.findOne(persitedPaulie.getNodeId());
	assertEqualGangsters(
		persitedPaulie,
		repo.findOne(persitedTonySoprano.getManaged().iterator().next()
			.getSubordinate().getNodeId()));
	assertEqualGangsters(
		persitedPaulie,
		repo.findOne(persitedTonySoprano.getSubordinates().iterator()
			.next().getNodeId()));
    }

    @Test
    @Transactional
    public void enrolingASubordinateShouldAlterTheSubordinateEntity() {
	Gangster paulie = buildGangster();
	paulie.setName("Paulie 'Walnuts' Gualtieri");
	Gangster persitedPaulie = repo.save(paulie);

	Gangster tonySoprano = buildGangster();
	Gangster persitedTonySoprano = repo.save(tonySoprano);

	service.enroleSubordinate(persitedTonySoprano, persitedPaulie);

	persitedPaulie = repo.findOne(persitedPaulie.getNodeId());
	// new entities had been added to the related collections
	Assert.assertEquals(paulie.getManagers().size() + 1, persitedPaulie
		.getManagers().size());
	Assert.assertEquals(paulie.getBosses().size() + 1, persitedPaulie
		.getBosses().size());

	// the boss is accessible throught the rellated collections
	persitedTonySoprano = repo.findOne(persitedTonySoprano.getNodeId());
	assertEqualGangsters(
		persitedTonySoprano,
		repo.findOne(persitedPaulie.getManagers().iterator().next()
			.getBoss().getNodeId()));
	assertEqualGangsters(
		persitedTonySoprano,
		repo.findOne(persitedPaulie.getBosses().iterator().next()
			.getNodeId()));
    }

    private Gangster buildGangster() {
	return populator.buildGangster(0, 0, 0);
    }

    private void assertEqualGangsters(Gangster expected, Gangster actual) {
	assertEqualPerson(expected, actual);
	Assert.assertEquals(expected.isOnDuty(), actual.isOnDuty());

	Assert.assertEquals(expected.getBosses().size(), actual.getBosses()
		.size());
	Assert.assertEquals(expected.getSubordinates().size(), actual
		.getSubordinates().size());

	Assert.assertEquals(expected.getManagers().size(), actual.getManagers()
		.size());
	Assert.assertEquals(expected.getManaged().size(), actual.getManaged()
		.size());
    }

    private void assertEqualPerson(Person expected, Person actual) {
	Assert.assertEquals(expected.getComment(), actual.getComment());
	Assert.assertEquals(expected.getName(), actual.getName());
    }

}
