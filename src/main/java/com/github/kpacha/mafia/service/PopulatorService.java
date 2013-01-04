package com.github.kpacha.mafia.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.kpacha.mafia.model.Gangster;
import com.github.kpacha.mafia.repository.GangsterRepository;

@Service
@Transactional
public class PopulatorService {

    @Autowired
    private GangsterRepository repo;
    @Autowired
    private GangsterService service;

    private Random random = new Random();

    private int totalCreatedGangsters = 0;

    @Transactional
    public int populate(final int deep) {
	repo.deleteAll();
	totalCreatedGangsters = 0;
	Gangster superboss = buildGangster(deep, 0, deep);
	superboss.setName("Tony Soprano");
	superboss.setComment("Bada Bing! " + superboss.getComment());
	repo.save(superboss);

	return totalCreatedGangsters;
    }

    @Transactional
    public Gangster buildGangster(int maxSubordinates, int level, int deep) {
	Gangster gangster = new Gangster();
	gangster.setName("Sample " + random.nextInt(1000));
	gangster.setComment("Has " + ((deep > 0) ? maxSubordinates : 0)
		+ " subordinates and his level is " + level);
	gangster.setOnDuty(true);
	totalCreatedGangsters++;
	gangster = repo.save(gangster);

	if (deep > 0) {
	    for (int i = 0; i < maxSubordinates; i++) {
		Gangster subordinate = buildGangster(maxSubordinates + level,
			level + 1, deep - 1);
		gangster = service.enroleSubordinate(gangster, subordinate);
	    }
	}

	return gangster;
    }
}
