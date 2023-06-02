package com.example.lab7.observer_pattern;

public class Event {
    protected ChangeEventType eventType;

    public Event(ChangeEventType eventType) {
        this.eventType = eventType;
    }

    public ChangeEventType getEventType() {
        return eventType;
    }
}
