package com.github.kpacha.mafia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.NamedIndexRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

import com.github.kpacha.mafia.model.Gangster;

public interface GangsterRepository extends GraphRepository<Gangster>,
	NamedIndexRepository<Gangster>,
	RelationshipOperationsRepository<Gangster> {

    Page<Gangster> findByNameLike(String name, Pageable page);

    // @Query("
    // start gangster=node({0})
    // match gangster<-[r:MANAGER]-boss
    // where r.onDuty = true
    // return boss
    // ")
    // Set<Gangster> getBoss(Gangster convicted);
    //
    // @Query("
    // start gangster=node({0})
    // match gangster-[r:MANAGER]->subordinate
    // where r.onDuty = true
    // return subordinate
    // ")
    // Set<Gangster> getActiveSubordinates(Gangster boss);
}
