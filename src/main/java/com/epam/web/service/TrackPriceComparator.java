package com.epam.web.service;

import com.epam.web.entities.Track;

import java.util.Comparator;

public class TrackPriceComparator implements Comparator<Track> {

    public int compare(Track first, Track second){
        return first.getPrice().compareTo(second.getPrice());
    }
}
