package com.github.kpacha.mafia.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.NamedIndexRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

import com.github.kpacha.mafia.model.Gangster;

public interface GangsterRepository extends GraphRepository<Gangster>,
	NamedIndexRepository<Gangster>,
	RelationshipOperationsRepository<Gangster> {

    Page<Gangster> findByNameLike(String name, Pageable page);

    @Query("start gangster=node({0}) match gangster<-[r:MANAGES]-boss where boss.onDuty = true return boss")
    Set<Gangster> getCurrentBoss(Gangster gangster);

    @Query("start gangster=node({0}) match gangster-[r:MANAGES]->subordinate where subordinate.onDuty = true and r.onDuty = true return subordinate order by r.createdAt")
    Set<Gangster> getActiveSubordinates(Gangster gangster);

    @Query("start gangster=node({0}) match gangster<-[:MANAGES]-boss-[r:MANAGES]->collegue where boss.onDuty = true and collegue.onDuty = true and r.onDuty = true return collegue order by r.createdAt")
    Set<Gangster> getActiveCollegues(Gangster gangster);
}
