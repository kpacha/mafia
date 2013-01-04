package com.github.kpacha.mafia.service;

import java.util.Date;
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

    // public Gangster sendToJail(Gangster convicted) {
    // Gangster storedConvicted = getUpdatedInstance(convicted);
    // Iterator<Gangster> iterator = repo.getBoss(storedConvicted).iterator();
    // Gangster boss = repo.findOne(iterator.next().getNodeId());
    // return null;
    // }
}
