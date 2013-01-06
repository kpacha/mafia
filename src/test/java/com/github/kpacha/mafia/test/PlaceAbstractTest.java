package com.github.kpacha.mafia.test;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.kpacha.mafia.repository.PlaceRepository;

public class PlaceAbstractTest extends AbstractTest {
    @Autowired
    protected PlaceRepository repo;
}
