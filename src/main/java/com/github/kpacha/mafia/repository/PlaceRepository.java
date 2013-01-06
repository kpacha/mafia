package com.github.kpacha.mafia.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.NamedIndexRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;
import org.springframework.data.neo4j.repository.SpatialRepository;

import com.github.kpacha.mafia.model.Place;

public interface PlaceRepository extends GraphRepository<Place>,
	NamedIndexRepository<Place>, RelationshipOperationsRepository<Place>,
	SpatialRepository<Place> {

}
