/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Jeff
 */
public class Sighting {
    
    private long sightingId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate sightingDate;
    private Location location;
    private List<Superhuman> heroes;

    public long getSightingId() {
        return sightingId;
    }

    public void setSightingId(long sightingId) {
        this.sightingId = sightingId;
    }

    public LocalDate getSightingDate() {
        return sightingDate;
    }

    public void setSightingDate(LocalDate sightingDate) {
        this.sightingDate = sightingDate;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    
    public List<Superhuman> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Superhuman> heroes) {
        this.heroes = heroes;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (int) (this.sightingId ^ (this.sightingId >>> 32));
        hash = 97 * hash + Objects.hashCode(this.sightingDate);
        hash = 97 * hash + Objects.hashCode(this.location);
        hash = 97 * hash + Objects.hashCode(this.heroes);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sighting other = (Sighting) obj;
        if (this.sightingId != other.sightingId) {
            return false;
        }
        if (!Objects.equals(this.sightingDate, other.sightingDate)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.heroes, other.heroes)) {
            return false;
        }
        return true;
    }
}
