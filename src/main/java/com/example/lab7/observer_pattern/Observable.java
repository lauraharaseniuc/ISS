package com.example.lab7.observer_pattern;

public interface Observable <E> {
    void addObserver (Observer<E> e);
    void removeObserver (Observer<E> e);
    void notifyObservers (E e);
}
