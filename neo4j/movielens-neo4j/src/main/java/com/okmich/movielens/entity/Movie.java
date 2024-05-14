/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.movielens.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

/**
 *
 * @author michael.enudi
 */
@Node
@EnableNeo4jRepositories
public class Movie implements Serializable {

    @Id
    private Long id;
    private Long movieId;
    private String title;
    @Relationship(type = "IS_A", direction = Relationship.Direction.OUTGOING)
    private List<Genre> genres;

    public Movie() {
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the movieId
     */
    public Long getMovieId() {
        return movieId;
    }

    /**
     * @param movieId the movieId to set
     */
    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the genres
     */
    public List<Genre> getGenres() {
        if (this.genres == null) {
            this.genres = new ArrayList<>();
        }
        return genres;
    }

    /**
     * @param genres the genres to set
     */
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Movie{" + "movieId=" + movieId + ", title=" + title + ", genres=" + getGenres() + '}';
    }
}
