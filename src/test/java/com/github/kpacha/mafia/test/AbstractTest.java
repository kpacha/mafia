package com.github.kpacha.mafia.test;

import junit.framework.Assert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.neo4j.support.node.Neo4jHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.BeforeTransaction;

import com.github.kpacha.mafia.model.Gangster;
import com.github.kpacha.mafia.model.Person;
import com.github.kpacha.mafia.model.Place;
import com.github.kpacha.mafia.service.PopulatorService;

@ContextConfiguration({ "/gangster-test-context.xml" })
public class AbstractTest {
    @Autowired
    private PopulatorService populator;

    @Autowired
    private Neo4jTemplate neo4jTemplate;

    @BeforeTransaction
    public void cleanDb() {
	Neo4jHelper.cleanDb(neo4jTemplate);
    }

    protected Gangster buildGangster() {
	return populator.buildGangster(0, 0, 0);
    }

    protected Place buildPlace(float lon, float lat) {
	return populator.buildPlace(lon, lat);
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
