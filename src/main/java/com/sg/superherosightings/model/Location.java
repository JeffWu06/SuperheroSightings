/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.model;

import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Jeff
 */
public class Location {
    
    private long locationId;
    @NotEmpty(message = "Please enter a name for the location.")
    @Length(max = 45, message = "Location name must be no more than 45 characters.")
    private String locationName;
    @NotEmpty(message = "Please enter a description for the location.")
    @Length(max = 90, message = "Description must be no more than 90 characters.")
    private String locationDescription;
    @NotEmpty(message = "Please enter value for house and street.")
    @Length(max = 52, message = "House and street must be no more than 52 characters.")
    private String street;
    @NotEmpty(message = "Please enter a city.")
    @Length(max = 45, message = "City must be no more than 46 characters.")
    private String city;
    @Length(max = 2, message = "State or province must be exactly 2 characters.")
    private String state;
    @NotEmpty(message = "Please enter the postal code.")
    @Length(min = 5, max = 10, message = "State or province must be between 5-10 characters.")
    private String zip;
    @NotEmpty(message = "Please enter the 2-character country code.")
    @Length(min = 2, max = 2, message = "Country code must be exactly 2 characters.")
    private String country;
    @DecimalMin(value = "-90.000000", inclusive = true, message = "Latitude must be "
            + "between -90 and 90 degrees.")
    @DecimalMax(value = "90.000000", inclusive = true, message = "Latitude must be "
            + "between -90 and 90 degrees.")
    @Digits(integer=2, fraction=6, message = "Latitude must be no more than 6 decimal places.")
    private BigDecimal latitude;
    @DecimalMin(value = "-180.000000", inclusive = true, message = "Longitude must be "
            + "between -180 and 180 degrees.")
    @DecimalMax(value = "180.000000", inclusive = true, message = "Longitude must be "
            + "between -180 and 180 degrees.")
    @Digits(integer=3, fraction=6, message = "Longitude must be no more than 6 decimal places.")
    private BigDecimal longitude;

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (int) (this.locationId ^ (this.locationId >>> 32));
        hash = 17 * hash + Objects.hashCode(this.locationName);
        hash = 17 * hash + Objects.hashCode(this.locationDescription);
        hash = 17 * hash + Objects.hashCode(this.street);
        hash = 17 * hash + Objects.hashCode(this.city);
        hash = 17 * hash + Objects.hashCode(this.state);
        hash = 17 * hash + Objects.hashCode(this.zip);
        hash = 17 * hash + Objects.hashCode(this.country);
        hash = 17 * hash + Objects.hashCode(this.latitude);
        hash = 17 * hash + Objects.hashCode(this.longitude);
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
        final Location other = (Location) obj;
        if (this.locationId != other.locationId) {
            return false;
        }
        if (!Objects.equals(this.locationName, other.locationName)) {
            return false;
        }
        if (!Objects.equals(this.locationDescription, other.locationDescription)) {
            return false;
        }
        if (!Objects.equals(this.street, other.street)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.zip, other.zip)) {
            return false;
        }
        if (!Objects.equals(this.country, other.country)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        return true;
    }
    
}
