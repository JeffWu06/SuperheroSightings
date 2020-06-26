/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.model;

import java.util.Objects;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Jeff
 */
public class Superpower {
    
    private int superpowerId;
   @NotEmpty(message = "Please enter superpower description.")
    @Length(max = 30, message = "Superpower description must be no more than 30 characters in length.")
    private String superpowerDescription;

    public int getSuperpowerId() {
        return superpowerId;
    }

    public void setSuperpowerId(int superpowerId) {
        this.superpowerId = superpowerId;
    }

    public String getSuperpowerDescription() {
        return superpowerDescription;
    }

    public void setSuperpowerDescription(String superpowerDescription) {
        this.superpowerDescription = superpowerDescription;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.superpowerId;
        hash = 89 * hash + Objects.hashCode(this.superpowerDescription);
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
        final Superpower other = (Superpower) obj;
        if (this.superpowerId != other.superpowerId) {
            return false;
        }
        if (!Objects.equals(this.superpowerDescription, other.superpowerDescription)) {
            return false;
        }
        return true;
    }
}
