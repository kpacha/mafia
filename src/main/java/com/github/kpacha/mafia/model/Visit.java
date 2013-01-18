package com.github.kpacha.mafia.model;

import java.util.Date;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type = "VISITS")
public class Visit {
    @GraphId
    private Long id;
    @StartNode
    private Person visitor;
    @EndNode
    private Place place;
    private Date visitedAt;
    private String reason;

    /**
     * @return the id
     */
    public Long getId() {
	return id;
    }

    /**
     * @return the visitor
     */
    public Person getVisitor() {
	return visitor;
    }

    /**
     * @return the place
     */
    public Place getPlace() {
	return place;
    }

    /**
     * @return the visitedAt
     */
    public Date getVisitedAt() {
	return visitedAt;
    }

    /**
     * @return the reason
     */
    public String getReason() {
	return reason;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
	this.id = id;
    }

    /**
     * @param visitor
     *            the visitor to set
     */
    public void setVisitor(Person visitor) {
	this.visitor = visitor;
    }

    /**
     * @param place
     *            the place to set
     */
    public void setPlace(Place place) {
	this.place = place;
    }

    /**
     * @param visitedAt
     *            the visitedAt to set
     */
    public void setVisitedAt(Date visitedAt) {
	this.visitedAt = visitedAt;
    }

    /**
     * @param reason
     *            the reason to set
     */
    public void setReason(String reason) {
	this.reason = reason;
    }
}
