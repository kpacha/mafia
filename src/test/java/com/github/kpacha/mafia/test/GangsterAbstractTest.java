package com.github.kpacha.mafia.test;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.kpacha.mafia.repository.GangsterRepository;

public class GangsterAbstractTest extends AbstractTest {
    @Autowired
    protected GangsterRepository repo;

}
