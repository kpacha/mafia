package com.github.kpacha.mafia.model;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.support.index.IndexType;

public class Person {
    @Indexed(indexType = IndexType.FULLTEXT, indexName = "person")
    private String name;
    private String comment;
    @RelatedTo(type = "VISITS", direction = Direction.OUTGOING)
    private Set<Place> visitedPlaces;
    @RelatedToVia(type = "VISITS", direction = Direction.OUTGOING)
    private Set<Visit> visites;

    /**
     * @return the comment
     */
    public String getComment() {
	return comment;
    }

    /**
     * @param comment
     *            the comment to set
     */
    public void setComment(String comment) {
	this.comment = comment;
    }

    /**
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @return the visitedPlaces
     */
    public Set<Place> getVisitedPlaces() {
	return visitedPlaces;
    }

    /**
     * @param visitedPlaces
     *            the visitedPlaces to set
     */
    public void setVisitedPlaces(Set<Place> visitedPlaces) {
	this.visitedPlaces = visitedPlaces;
    }

    /**
     * @return the visites
     */
    public Set<Visit> getVisites() {
	return visites;
    }

    /**
     * @param visites
     *            the visites to set
     */
    public void setVisites(Set<Visit> visites) {
	this.visites = visites;
    }
}
