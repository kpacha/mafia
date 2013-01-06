package com.github.kpacha.mafia.model;

import java.util.Formatter;
import java.util.Locale;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.support.index.IndexType;

@NodeEntity
public class Place {
    @GraphId
    private Long id;
    @Indexed(indexType = IndexType.FULLTEXT, indexName = "place")
    private String name;
    private String comment;
    @Indexed(indexType = IndexType.POINT, indexName = "placeLocation")
    private String wkt;
    @Fetch
    @RelatedToVia(type = "VISITS", direction = Direction.OUTGOING)
    private Set<Visit> visits;

    public void setLocation(float lon, float lat) {
	StringBuilder stringBuilder = new StringBuilder();
	Formatter formatter = new Formatter(stringBuilder, Locale.US);
	formatter.format("POINT( %.2f %.2f )", lon, lat);
	this.wkt = stringBuilder.substring(0);
    }

    /**
     * @return the id
     */
    public Long getId() {
	return id;
    }

    /**
     * @return the visits
     */
    public Set<Visit> getVisits() {
	return visits;
    }

    /**
     * @param visits
     *            the visits to set
     */
    public void setVisits(Set<Visit> visits) {
	this.visits = visits;
    }

    /**
     * @param visit
     *            the visit to add
     */
    public void addVisit(Visit visit) {
	this.visits.add(visit);
    }

    /**
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @return the comment
     */
    public String getComment() {
	return comment;
    }

    /**
     * @return the wkt
     */
    public String getWkt() {
	return wkt;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
	this.id = id;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @param comment
     *            the comment to set
     */
    public void setComment(String comment) {
	this.comment = comment;
    }

    /**
     * @param wkt
     *            the wkt to set
     */
    public void setWkt(String wkt) {
	this.wkt = wkt;
    }
}
