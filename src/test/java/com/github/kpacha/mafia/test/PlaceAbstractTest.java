package com.github.kpacha.mafia.test;

import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.kpacha.mafia.repository.PlaceRepository;

public class PlaceAbstractTest extends AbstractTest {
    @Autowired
    protected PlaceRepository repo;

    @After
    public void cleanDb() throws InterruptedException {
	repo.deleteAll();
	Thread.sleep(1000);
    }
}
