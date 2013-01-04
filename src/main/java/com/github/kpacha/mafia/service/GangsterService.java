package com.github.kpacha.mafia.service;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.kpacha.mafia.model.Gangster;
import com.github.kpacha.mafia.model.Manager;
import com.github.kpacha.mafia.repository.GangsterRepository;

@Service
@Transactional
public class GangsterService {

    @Autowired
    private GangsterRepository repo;

    @Transactional
    public Gangster enroleSubordinate(final Gangster boss,
	    final Gangster subordinate) {
	Gangster storedBoss = getUpdatedInstance(boss);
	Gangster storedSubordinate = getUpdatedInstance(subordinate);

	Manager manager = new Manager();
	manager.setBoss(storedBoss);
	manager.setSubordinate(storedSubordinate);
	manager.setCreatedAt(new Date());
	manager.setOnDuty(true);
	Set<Manager> managed = storedBoss.getManaged();
	managed.add(manager);
	storedBoss.setManaged(managed);

	return repo.save(storedBoss);
    }

    @Transactional
    private Gangster getUpdatedInstance(Gangster gangster) {
	Gangster storedGangster = null;
	if (gangster != null) {
	    if (gangster.getNodeId() == null) {
		storedGangster = repo.save(gangster);
	    } else {
		storedGangster = repo.findOne(gangster.getNodeId());
		if (storedGangster == null) {
		    storedGangster = repo.save(gangster);
		}
	    }
	}
	return storedGangster;
    }

    @Transactional
    public Gangster sendToJail(Gangster convicted) {
	Gangster storedConvicted = getUpdatedInstance(convicted);
	Gangster boss = getBoss(storedConvicted);
	Gangster candidate = null;
	Set<Gangster> subordinates = repo
		.getActiveSubordinates(storedConvicted);

	// look for a candidate
	if (boss != null) {
	    candidate = getCandidateFromSet(repo
		    .getActiveCollegues(storedConvicted));
	}
	if (candidate == null) {
	    candidate = getCandidateFromSet(subordinates);
	}

	// release the subordinates and enrole them with the candidate
	if (candidate != null) {
	    candidate = endoseSubordinates(storedConvicted, candidate,
		    subordinates);

	    // if the candidate was a subordinate, promote him
	    if (repo.getCurrentBoss(candidate).isEmpty() && boss != null) {
		candidate = enroleSubordinate(boss, candidate);
	    }
	}

	// update the convicted properties
	storedConvicted.setOnDuty(false);
	repo.save(storedConvicted);
	return candidate;
    }

    private Gangster endoseSubordinates(Gangster storedConvicted,
	    Gangster candidate, Set<Gangster> subordinates) {
	for (Gangster subordinate : subordinates) {
	    Manager managerToUpdate = null;
	    Set<Manager> managers = subordinate.getManagers();
	    // look for the manager
	    for (Manager manager : managers) {
		if (manager.getBoss().getNodeId()
			.equals(storedConvicted.getNodeId())) {
		    managerToUpdate = manager;
		    break;
		}
	    }
	    if (managerToUpdate != null) {
		managerToUpdate.setOnDuty(false);
		managerToUpdate.setUpdatedAt(new Date());
		managers.add(managerToUpdate);
	    }
	    if (!subordinate.getNodeId().equals(candidate.getNodeId())) {
		subordinate.setManagers(managers);
		candidate = enroleSubordinate(candidate, subordinate);
	    } else {
		candidate.setManagers(managers);
		candidate = repo.save(candidate);
	    }
	}
	return candidate;
    }

    private Gangster getCandidateFromSet(Set<Gangster> gangsters) {
	Gangster candidate = null;
	Iterator<Gangster> gangstersIterator = gangsters.iterator();
	if (gangstersIterator.hasNext()) {
	    candidate = gangstersIterator.next();
	}
	return candidate;
    }

    private Gangster getBoss(Gangster gangster) {
	Gangster boss = null;
	Set<Gangster> bosses = repo.getCurrentBoss(gangster);
	if (!bosses.isEmpty()) {
	    boss = (Gangster) bosses.iterator().next();
	}
	return boss;
    }
}
