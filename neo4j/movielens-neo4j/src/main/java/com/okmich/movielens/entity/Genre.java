/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.movielens.entity;

import java.io.Serializable;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

/**
 *
 * @author michael.enudi
 */
@Node
public class Genre implements Serializable {

    @Id
    private Long identity;
    private String name;

    public Genre() {
    }

    /**
     * @return the id
     */
    public Long getId() {
        return identity;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.identity = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param tag the name to set
     */
    public void setName(String tag) {
        this.name = tag;
    }

    @Override
    public String toString() {
        return "Genre{" + "name=" + name + '}';
    }
}
