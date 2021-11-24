package com.example.mediaplayer;

import com.example.mediaplayer.Observer;

public interface Observable {
    public void registerObserver(Observer observer);

    public void removeObserver(Observer observer);

    public void notifyObservers();

}


