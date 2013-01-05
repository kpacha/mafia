package com.github.kpacha.mafia.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

@NodeEntity
public class Gangster extends Person {
    @GraphId
    Long nodeId;
    boolean onDuty;
    @RelatedTo(type = "MANAGES", direction = Direction.INCOMING)
    Set<Gangster> bosses = new HashSet<Gangster>();
    @RelatedTo(type = "MANAGES", direction = Direction.OUTGOING)
    Set<Gangster> subordinates = new HashSet<Gangster>();
    @RelatedTo(type = "KNOWS", direction = Direction.BOTH)
    Set<Gangster> known = new HashSet<Gangster>();
    @Fetch
    @RelatedToVia(type = "MANAGES", direction = Direction.INCOMING)
    Set<Manager> managers = new HashSet<Manager>();
    @Fetch
    @RelatedToVia(type = "MANAGES", direction = Direction.OUTGOING)
    Set<Manager> managed = new HashSet<Manager>();

    /**
     * @param known
     *            the known gangster to add
     */
    public void addKnown(Gangster known) {
	this.known.add(known);
    }

    /**
     * @param known
     *            the known gangsters to add
     */
    public void addKnown(Collection<Gangster> known) {
	this.known.addAll(known);
    }

    /**
     * @return the nodeId
     */
    public Long getNodeId() {
	return nodeId;
    }

    /**
     * @return the onDuty
     */
    public boolean isOnDuty() {
	return onDuty;
    }

    /**
     * @return the bosses
     */
    public Set<Gangster> getBosses() {
	return bosses;
    }

    /**
     * @return the subordinates
     */
    public Set<Gangster> getSubordinates() {
	return subordinates;
    }

    /**
     * @return the known
     */
    public Set<Gangster> getKnown() {
	return known;
    }

    /**
     * @return the managers
     */
    public Set<Manager> getManagers() {
	return managers;
    }

    /**
     * @return the managed
     */
    public Set<Manager> getManaged() {
	return managed;
    }

    /**
     * @param nodeId
     *            the nodeId to set
     */
    public void setNodeId(Long nodeId) {
	this.nodeId = nodeId;
    }

    /**
     * @param onDuty
     *            the onDuty to set
     */
    public void setOnDuty(boolean onDuty) {
	this.onDuty = onDuty;
    }

    /**
     * @param bosses
     *            the bosses to set
     */
    public void setBosses(Collection<Gangster> bosses) {
	this.bosses = new HashSet<Gangster>(bosses);
    }

    /**
     * @param subordinates
     *            the subordinates to set
     */
    public void setSubordinates(Collection<Gangster> subordinates) {
	this.subordinates = new HashSet<Gangster>(subordinates);
    }

    /**
     * @param managers
     *            the managers to set
     */
    public void setManagers(Collection<Manager> managers) {
	this.managers = new HashSet<Manager>(managers);
    }

    /**
     * @param managed
     *            the managed to set
     */
    public void setManaged(Collection<Manager> managed) {
	this.managed = new HashSet<Manager>(managed);
    }

}
