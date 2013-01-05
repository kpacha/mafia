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

    public int DEFAULT_DEEP = 5;

    Page<Gangster> findByNameLike(String name, Pageable page);

    @Query("start gangster=node({0}) match gangster<-[r:MANAGES]-boss where boss.onDuty = true return boss")
    Set<Gangster> getCurrentBoss(Gangster gangster);

    @Query("start gangster=node({0}) match gangster-[r:MANAGES]->subordinate where subordinate.onDuty = true return subordinate order by r.createdAt")
    Set<Gangster> getActiveSubordinates(Gangster gangster);

    @Query("start gangster=node({0}) match gangster<-[:MANAGES]-boss-[r:MANAGES]->collegue where boss.onDuty = true and collegue.onDuty = true return collegue order by r.createdAt")
    Set<Gangster> getActiveCollegues(Gangster gangster);

    @Query("start sub=node({0}) match sub<-[r:MANAGES*1.."
	    + DEFAULT_DEEP
	    + "]-boss where boss.onDuty=true and NOT (boss<-[:MANAGES]-()) return distinct boss")
    Set<Gangster> findRoot(Gangster gangster);

    @Query("start sub=node({0}) match sub<-[r:MANAGES*1..{1}]-boss where boss.onDuty=true and NOT (boss<-[:MANAGES]-()) return distinct boss")
    Set<Gangster> findRoot(Gangster gangster, int deep);

    @Query("start gangster=node({0}) match gangster-[:KNOWS*1.." + DEFAULT_DEEP
	    + "]-known return count(*)")
    Set<Integer> countKnown(Gangster gangster);

    @Query("start gangster=node({0}) match gangster-[:KNOWS*1..{1}]-known return count(*)")
    Set<Integer> countKnown(Gangster gangster, int deep);

    @Query("start sub=node({0}) match p=sub<-[r:MANAGES*0.."
	    + DEFAULT_DEEP
	    + "]-boss where boss.onDuty=true and NOT (boss<-[:MANAGES]-()) return MIN(LENGTH(p))")
    Set<Integer> getLevel(Gangster gangster);

    @Query("start sub=node({0}) match p=sub<-[r:MANAGES*0..{1}]-boss where boss.onDuty=true and NOT (boss<-[:MANAGES]-()) return MIN(LENGTH(p))")
    Set<Integer> getLevel(Gangster gangster, int deep);

    @Query("start gangster=node({0}) match gangster-[:MANAGES*1.."
	    + DEFAULT_DEEP + "]->subordinate return count(subordinate)")
    Set<Integer> countAllSubordinates(Gangster gangster);

    @Query("start gangster=node({0}) match gangster-[:MANAGES*1..{1}]->subordinate return count(subordinate)")
    Set<Integer> countAllSubordinates(Gangster gangster, int deep);
}
