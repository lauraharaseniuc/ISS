package com.example.lab7.model;


import java.util.HashSet;
import java.util.Set;

public class Play {
    private int id;
    private String name;
    private String description;
    private String author;

    private Set<Representation> representations = new HashSet<>();

    public Play() {

    }

    public Play(int id, String name, String description, String author) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
    }

    public Play(String name, String description, String author) {
        this.name = name;
        this.description = description;
        this.author = author;
    }

    public Play(int id, String name, String description, String author, Set<Representation> representations) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
        this.representations = representations;
    }

    public Play(String name, String description, String author, Set<Representation> representations) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.representations = representations;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Set<Representation> getRepresentations() {
        return representations;
    }

    public void setRepresentations(Set<Representation> representations) {
        this.representations = representations;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
