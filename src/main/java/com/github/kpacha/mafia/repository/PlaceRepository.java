package com.github.kpacha.mafia.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.NamedIndexRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;
import org.springframework.data.neo4j.repository.SpatialRepository;

import com.github.kpacha.mafia.model.Gangster;
import com.github.kpacha.mafia.model.MostVisitedResult;
import com.github.kpacha.mafia.model.Place;

public interface PlaceRepository extends GraphRepository<Place>,
	NamedIndexRepository<Place>, RelationshipOperationsRepository<Place>,
	SpatialRepository<Place> {

    @Query("start visitor=node({0}) match visitor-[:VISITS]->place "
	    + " return place, count(*) order by count(*) desc limit 10")
    List<MostVisitedResult> getMostVisitedPlaces(Gangster visitor);

}
