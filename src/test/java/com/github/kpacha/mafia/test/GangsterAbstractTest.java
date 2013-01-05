package com.github.kpacha.mafia.test;

import junit.framework.Assert;

import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.kpacha.mafia.model.Gangster;
import com.github.kpacha.mafia.model.Person;
import com.github.kpacha.mafia.repository.GangsterRepository;
import com.github.kpacha.mafia.service.impl.PopulatorServiceImpl;

public class GangsterAbstractTest {
    @Autowired
    protected GangsterRepository repo;
    @Autowired
    private PopulatorServiceImpl populator;

    @After
    public void cleanDb() throws InterruptedException {
	repo.deleteAll();
	Thread.sleep(1000);
    }

    protected Gangster buildGangster() {
	return populator.buildGangster(0, 0, 0);
    }

    protected void assertEqualGangsters(Gangster expected, Gangster actual) {
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

    protected void assertEqualPerson(Person expected, Person actual) {
	Assert.assertEquals(expected.getComment(), actual.getComment());
	Assert.assertEquals(expected.getName(), actual.getName());
    }

}
