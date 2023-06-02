package com.example.lab7.observer_pattern;

import com.example.lab7.model.Representation;

public class RepresentationEntityChangeEvent extends Event{
    private Representation representation;

    public RepresentationEntityChangeEvent(ChangeEventType eventType, Representation representation) {
        super(eventType);
        this.representation = representation;
    }

    public Representation getRepresentation() {
        return representation;
    }
}
