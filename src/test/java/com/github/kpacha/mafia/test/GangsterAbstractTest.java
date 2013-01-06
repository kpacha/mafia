package com.github.kpacha.mafia.test;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.kpacha.mafia.model.Gangster;
import com.github.kpacha.mafia.repository.GangsterRepository;

public class GangsterAbstractTest extends AbstractTest {
    @Autowired
    protected GangsterRepository repo;

    protected Gangster tonySoprano;
    protected Gangster paulie;
    protected Gangster soldier;

    @Before
    public void setUp() throws InterruptedException {
	tonySoprano = buildGangster();
	tonySoprano.setName("Tony Soprano");
	tonySoprano = repo.save(tonySoprano);

	paulie = buildGangster();
	paulie.setName("Paulie 'Walnuts' Gualtieri");
	paulie = repo.save(paulie);

	soldier = buildGangster();
    }

}
