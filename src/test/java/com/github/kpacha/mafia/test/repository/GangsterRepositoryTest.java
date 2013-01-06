package com.github.kpacha.mafia.test.repository;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.github.kpacha.mafia.model.Gangster;
import com.github.kpacha.mafia.model.Manager;
import com.github.kpacha.mafia.test.GangsterAbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/gangster-test-context.xml" })
@Transactional
public class GangsterRepositoryTest extends GangsterAbstractTest {

    private Gangster tonySoprano;
    private Gangster paulie;
    private Gangster soldier;

    @Before
    public void setUp() throws InterruptedException {
	tonySoprano = buildGangster();
	paulie = buildGangster();
	soldier = buildGangster();
    }

    @Test
    @Transactional
    public void persistedGangsterShouldBeRetrievableFromGraphDb() {
	Gangster retrievedGangster = repo.findOne(tonySoprano.getNodeId());
	assertEqualGangsters(tonySoprano, retrievedGangster);
    }

    @Test
    @Transactional
    public void theGangsterIsOmitedFromItsCollegueList() {
	Set<Manager> managed = new HashSet<Manager>();
	managed.add(buildManager(tonySoprano, paulie, true));
	managed.add(buildManager(tonySoprano, soldier, true));
	tonySoprano.setManaged(managed);

	tonySoprano = repo.save(tonySoprano);

	Set<Gangster> collegues = repo.getActiveCollegues(paulie);

	Assert.assertEquals(1, collegues.size());
	Assert.assertEquals(soldier.getNodeId(), collegues.iterator().next()
		.getNodeId());
    }

    @Test
    @Transactional
    public void theConvictedAreOmitedFromSubordinateList() {
	paulie.setOnDuty(false);
	paulie = repo.save(paulie);

	Set<Manager> managed = new HashSet<Manager>();
	managed.add(buildManager(tonySoprano, paulie, false));
	managed.add(buildManager(tonySoprano, soldier, true));
	tonySoprano.setManaged(managed);

	tonySoprano = repo.save(tonySoprano);

	Set<Gangster> subordinates = repo.getActiveSubordinates(tonySoprano);

	Assert.assertEquals(1, subordinates.size());
	Assert.assertEquals(soldier.getNodeId(), subordinates.iterator().next()
		.getNodeId());
    }

    @Test
    @Transactional
    public void theConvictedAreOmitedFromBossesList() {
	paulie.setOnDuty(false);
	paulie = repo.save(paulie);

	Set<Manager> managers = new HashSet<Manager>();
	managers.add(buildManager(paulie, soldier, false));
	managers.add(buildManager(tonySoprano, soldier, true));
	soldier.setManagers(managers);

	soldier = repo.save(soldier);

	Set<Gangster> bosses = repo.getCurrentBoss(soldier);

	Assert.assertEquals(1, bosses.size());
	Assert.assertEquals(tonySoprano.getNodeId(), bosses.iterator().next()
		.getNodeId());
    }

    private Manager buildManager(Gangster boss, Gangster subordinate,
	    boolean onDuty) {
	Manager manager = new Manager();
	manager.setBoss(boss);
	manager.setSubordinate(subordinate);
	manager.setCreatedAt(new Date());
	manager.setOnDuty(onDuty);
	return manager;
    }
}
