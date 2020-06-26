/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.model;

import java.util.List;
import java.util.Objects;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Jeff
 */
public class Superhuman {
    
    private int superhumanId;
    @NotEmpty(message = "Please enter an alter ego.")
    @Length(max = 30, message = "Alter ego must be no more than 30 characters in length.")
    private String alterEgo;
    @NotEmpty(message = "Please enter a description for the hero/villain.")
    @Length(max = 90, message = "Description must be no more than 90 characters in length.")
    private String description;
    private boolean villain;
    private List<Superpower> superpowers;
    private List<Organization> organizations;

    public int getSuperhumanId() {
        return superhumanId;
    }

    public void setSuperhumanId(int superhumanId) {
        this.superhumanId = superhumanId;
    }

    public String getAlterEgo() {
        return alterEgo;
    }

    public void setAlterEgo(String alterEgo) {
        this.alterEgo = alterEgo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVillain() {
        return villain;
    }

    public void setVillain(boolean villain) {
        this.villain = villain;
    }

    public List<Superpower> getSuperpowers() {
        return superpowers;
    }

    public void setSuperpowers(List<Superpower> superpowers) {
        this.superpowers = superpowers;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.superhumanId;
        hash = 89 * hash + Objects.hashCode(this.alterEgo);
        hash = 89 * hash + Objects.hashCode(this.description);
        hash = 89 * hash + (this.villain ? 1 : 0);
        hash = 89 * hash + Objects.hashCode(this.superpowers);
        hash = 89 * hash + Objects.hashCode(this.organizations);
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
        final Superhuman other = (Superhuman) obj;
        if (this.superhumanId != other.superhumanId) {
            return false;
        }
        if (this.villain != other.villain) {
            return false;
        }
        if (!Objects.equals(this.alterEgo, other.alterEgo)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.superpowers, other.superpowers)) {
            return false;
        }
        if (!Objects.equals(this.organizations, other.organizations)) {
            return false;
        }
        return true;
    }

}
