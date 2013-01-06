package com.github.kpacha.mafia.model;

import org.springframework.data.neo4j.annotation.MapResult;
import org.springframework.data.neo4j.annotation.ResultColumn;

@MapResult
public interface MostVisitedResult {
    @ResultColumn("place")
    Place getPlace();

    @ResultColumn("count(*)")
    int getVisits();
}
