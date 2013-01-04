package com.github.kpacha.mafia.model;

import java.util.Date;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type = "MANAGES")
public class Manager {
    @GraphId
    Long id;
    @StartNode
    Gangster boss;
    @EndNode
    Gangster subordinate;
    Date createdAt;
    boolean onDuty;
    Date updatedAt;

    /**
     * @return the id
     */
    public Long getId() {
	return id;
    }

    /**
     * @return the boss
     */
    public Gangster getBoss() {
	return boss;
    }

    /**
     * @return the subordinate
     */
    public Gangster getSubordinate() {
	return subordinate;
    }

    /**
     * @return the createdAt
     */
    public Date getCreatedAt() {
	return createdAt;
    }

    /**
     * @return the onDuty
     */
    public boolean isOnDuty() {
	return onDuty;
    }

    /**
     * @return the updatedAt
     */
    public Date getUpdatedAt() {
	return updatedAt;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
	this.id = id;
    }

    /**
     * @param boss
     *            the boss to set
     */
    public void setBoss(Gangster boss) {
	this.boss = boss;
    }

    /**
     * @param subordinate
     *            the subordinate to set
     */
    public void setSubordinate(Gangster subordinate) {
	this.subordinate = subordinate;
    }

    /**
     * @param createdAt
     *            the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
	this.createdAt = createdAt;
    }

    /**
     * @param onDuty
     *            the onDuty to set
     */
    public void setOnDuty(boolean onDuty) {
	this.onDuty = onDuty;
    }

    /**
     * @param updatedAt
     *            the updatedAt to set
     */
    public void setUpdatedAt(Date updatedAt) {
	this.updatedAt = updatedAt;
    }

}
