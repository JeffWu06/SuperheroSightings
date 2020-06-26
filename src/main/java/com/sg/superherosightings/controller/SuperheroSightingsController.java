/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controller;

import com.sg.superherosightings.service.SightingInUseException;
import com.sg.superherosightings.service.OrganizationInUseException;
import com.sg.superherosightings.service.LocationInUseException;
import com.sg.superherosightings.model.Location;
import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Sighting;
import com.sg.superherosightings.model.Superhuman;
import com.sg.superherosightings.model.Superpower;
import com.sg.superherosightings.service.LocationInvalidDataException;
import com.sg.superherosightings.service.OrganizationInvalidDataException;
import com.sg.superherosightings.service.SightingInvalidDataException;
import com.sg.superherosightings.service.SuperHeroSightingsService;
import com.sg.superherosightings.service.SuperhumanInUseException;
import com.sg.superherosightings.service.SuperhumanInvalidDataException;
import com.sg.superherosightings.service.SuperpowerInUseException;
import com.sg.superherosightings.service.SuperpowerInvalidDataException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Jeff
 */
@Controller
public class SuperheroSightingsController {
    
    private SuperHeroSightingsService service;
    
    @Inject
    public SuperheroSightingsController(SuperHeroSightingsService service) {
        this.service = service;
    }
    
    @RequestMapping(value="/displayMainPage", method=RequestMethod.GET)
    public String displayIndexPage(Model model) {
        List<Sighting> sightings = service.getRecentSightings();
        model.addAttribute("lastTenSightings", sightings);
        return "home";
    }    
    
    @RequestMapping(value="/displaySuperheroesPage", method=RequestMethod.GET)
    public String displaySuperheroesPage(Model model) {
        List<Superhuman> superhumans = service.getAllSuperhumans();
        model.addAttribute("heroesList", superhumans);
        return "superheroes";
    }
    
    @RequestMapping(value="/displayCreateHeroPage", method=RequestMethod.GET)
    public String displayCreateHeroPage(Model model) {
        Superhuman superhuman = new Superhuman();
        model.addAttribute("superhuman", superhuman);
        List<Superpower> superpowers = service.getAllSuperpowers();
        model.addAttribute("superpowers", superpowers);
        List<Organization> organizations = service.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        return "addHero";
    }
    
    @RequestMapping(value="/createHero", method=RequestMethod.POST)
    public String createHero(Model model, HttpServletRequest request, 
            @Valid @ModelAttribute("superhuman") Superhuman superhuman, 
            BindingResult result) throws SuperhumanInvalidDataException {
        
        superhuman.setAlterEgo(request.getParameter("alterEgo"));
        superhuman.setDescription(request.getParameter("description"));
        String isVillain = request.getParameter("villain");
        if (isVillain.equalsIgnoreCase("yes")) {
            superhuman.setVillain(true);
        } else {
            superhuman.setVillain(false);
        }
        
        String[] superpowerIds = request.getParameterValues("powers");
        List<Superpower> superpowers = getSuperpowersFromParamVals(superpowerIds);
        superhuman.setSuperpowers(superpowers);
        String[] orgIds = request.getParameterValues("orgs");
        List<Organization> organizations = getOrgsFromParamVals(orgIds);
        superhuman.setOrganizations(organizations);
        
        if (result.hasErrors()) {
            model.addAttribute("superhuman", superhuman);
            /*List<Superpower>*/ superpowers = service.getAllSuperpowers();
            model.addAttribute("superpowers", superpowers);
            /*List<Organization>*/ organizations = service.getAllOrganizations();
            model.addAttribute("organizations", organizations);
            return "addHero";
        }
        service.addSuperhuman(superhuman);        
        return "redirect:displaySuperheroesPage";
    }
    
    private List<Superpower> getSuperpowersFromParamVals(String[] superpowerIds) {
        List<Superpower> superpowers = new ArrayList<>();
        try {
            for (String powerId : superpowerIds) {
                int idAsInt = Integer.parseInt(powerId);
                Superpower superpower = service.getSuperpowerById(idAsInt);
                superpowers.add(superpower);
            }
        } catch (NullPointerException e) {
            return superpowers;
        }
        return superpowers;
    }
    
    private List<Organization> getOrgsFromParamVals(String[] orgIds) {
        List<Organization> organizations = new ArrayList<>();
        try {
            for (String orgId : orgIds) {
                int idAsInt = Integer.parseInt(orgId);
                Organization organization = service.getOrganizationById(idAsInt);
                organizations.add(organization);
            }
        } catch (NullPointerException e) {
            return organizations;
        }
        return organizations;
    }
    
    @RequestMapping(value="/displaySuperhumanDetails", method=RequestMethod.GET)
    public String displaySuperhumanDetails(HttpServletRequest request, Model model) {
        String superhumanIdParam = request.getParameter("superhumanId");
        int superhumanId = Integer.parseInt(superhumanIdParam);
        Superhuman superhuman = service.getSuperhumanById(superhumanId);
        model.addAttribute("superhuman", superhuman);
        return "viewHero";
    }
    
    @RequestMapping(value="/displayEditSuperhumanPage", method=RequestMethod.GET)
    public String displayEditSuperhumanPage(HttpServletRequest request, Model model) {
        String superhumanIdParam = request.getParameter("superhumanId");
        int superhumanId = Integer.parseInt(superhumanIdParam);
        Superhuman superhumanToEdit = service.getSuperhumanById(superhumanId);
        List<Superpower> unpossessedPowers = service.getSuperpowersNotPossessedBySuperhuman(superhumanToEdit);
        List<Organization> nonmemberOrganizations = service.getNonmemberOrganizationsBySuperhuman(superhumanToEdit);
        model.addAttribute("superhumanToEdit", superhumanToEdit);
        model.addAttribute("unpossessedPowers", unpossessedPowers);
        model.addAttribute("nonmemberOrganizations", nonmemberOrganizations);
        return "editHero";
    }
    
    @RequestMapping(value="/editHero", method=RequestMethod.POST)
    public String editSuperhuman(HttpServletRequest request, 
            @Valid @ModelAttribute("superhumanToEdit") Superhuman superhumanToEdit,
            BindingResult result, Model model) throws SuperhumanInvalidDataException {
        String[] superpowerIds = request.getParameterValues("powers");
        List<Superpower> superpowers = getSuperpowersFromParamVals(superpowerIds);
        superhumanToEdit.setSuperpowers(superpowers);
        String[] orgIds = request.getParameterValues("orgs");
        List<Organization> organizations = getOrgsFromParamVals(orgIds);
        superhumanToEdit.setOrganizations(organizations);
        if (result.hasErrors()){
            model.addAttribute("superhumanToEdit", superhumanToEdit);
            model.addAttribute("unpossessedPowers", 
                    service.getSuperpowersNotPossessedBySuperhuman(superhumanToEdit));
            model.addAttribute("nonmemberOrganizations", 
                    service.getNonmemberOrganizationsBySuperhuman(superhumanToEdit));
            return "editHero";
        }
        service.updateSuperhuman(superhumanToEdit);
        return "redirect:displaySuperheroesPage";
    }
    
    @RequestMapping(value="/superhuman/{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteSuperhuman(@PathVariable("id") int superhumanId, Model model) 
            throws SuperhumanInUseException {
        service.deleteSuperhuman(superhumanId);
        return "redirect:displaySuperheroesPage";
    }
    
    @RequestMapping(value="/displaySuperpowersPage", method=RequestMethod.GET)
    public String displaySuperpowersPage(Model model) {
        List<Superpower> superpowers = service.getAllSuperpowers();
        model.addAttribute("powersList", superpowers);
        return "superpowers";
    }
    
    @RequestMapping(value="/displayAddSuperpowerPage", method=RequestMethod.GET)
    public String displayAddSuperpowerPage(Model model) {
        model.addAttribute("superpower", new Superpower());
        return "addPower";
    }
    
    @RequestMapping(value="/addPower", method=RequestMethod.POST)
    public String addPower(HttpServletRequest request, 
            @Valid @ModelAttribute("superpower") Superpower superpower, 
            BindingResult result) throws SuperpowerInvalidDataException {
        superpower.setSuperpowerDescription(request.getParameter("superpowerDescription"));
        if (result.hasErrors()) {
            return "addPower";
        }
        service.addSuperpower(superpower);
        return "redirect:displaySuperpowersPage";
    }

    @RequestMapping(value="/displayEditSuperpowerPage", method=RequestMethod.GET)
    public String displayEditSuperpowerPage(HttpServletRequest request, Model model) {
        String superpowerIdParam = request.getParameter("superpowerId");
        int superpowerId = Integer.parseInt(superpowerIdParam);
        Superpower superpowerToEdit = service.getSuperpowerById(superpowerId);
        model.addAttribute("superpowerToEdit", superpowerToEdit);
        return "editPower";
    }
    
    @RequestMapping(value="/editPower", method=RequestMethod.POST)
    public String editSuperpower(@Valid @ModelAttribute("superpowerToEdit") Superpower superpowerToEdit,
            BindingResult result, Model model) throws SuperpowerInvalidDataException {
        if (result.hasErrors()){
            return "editPower";
        }    
        service.updateSuperpower(superpowerToEdit);
        return "redirect:displaySuperpowersPage";
    }
    
    @RequestMapping(value="/superpower/{id}", method=RequestMethod.DELETE)
    public String deleteSuperpower(@PathVariable("id") int superpowerId) 
            throws SuperpowerInUseException {
        service.deleteSuperpower(superpowerId);
        return "redirect:displaySuperpowersPage";
    }
    
    @RequestMapping(value="/displayLocationsPage", method=RequestMethod.GET)
    public String displayLocationsPage(Model model) {
        List<Location> locations = service.getAllLocations();
        model.addAttribute("locationsList", locations);
        return "locations";
    }
    
    @RequestMapping(value="/displayLocationDetails", method=RequestMethod.GET)
    public String displayLocationDetails(HttpServletRequest request, Model model) {
        String locationIdParam = request.getParameter("locationId");
        long locationId = Long.parseLong(locationIdParam);
        Location location = service.getLocationById(locationId);
        model.addAttribute("location", location);
        return "viewLocation";
    }
    
    @RequestMapping(value="/displayAddLocationPage", method=RequestMethod.GET)
    public String displayAddLocationPage(Model model) {
        model.addAttribute("location", new Location());
        return "addLocation";
    }
    
    @RequestMapping(value="/addLocation", method=RequestMethod.POST)
    public String addLocation(HttpServletRequest request, 
            @Valid @ModelAttribute("location") Location location, 
            BindingResult result) throws LocationInvalidDataException {
        
        if (result.hasErrors()) {
            return "addLocation";
        }
        location.setLocationName(request.getParameter("locationName"));
        location.setLocationDescription(request.getParameter("locationDescription"));
        location.setStreet(request.getParameter("street"));
        location.setCity(request.getParameter("city"));
        location.setState(request.getParameter("state"));
        location.setZip(request.getParameter("zip"));
        location.setCountry(request.getParameter("country"));
        try {
            location.setLatitude(new BigDecimal(request.getParameter("latitude")));
            location.setLongitude(new BigDecimal(request.getParameter("longitude")));
        } catch (NumberFormatException e) {
            throw new LocationInvalidDataException("Coordinates must be numeric.");
        }
        service.addLocation(location);
        return "redirect:displayLocationsPage";
    }

    @RequestMapping(value="/displayEditLocationPage", method=RequestMethod.GET)
    public String displayEditLocationPage(HttpServletRequest request, Model model) {
        String locationIdParam = request.getParameter("locationId");
        long locationId = Long.parseLong(locationIdParam);
        Location locationToEdit = service.getLocationById(locationId);
        model.addAttribute("locationToEdit", locationToEdit);
        return "editLocation";
    }
    
    @RequestMapping(value="/editLocation", method=RequestMethod.POST)
    public String editLocation(@Valid @ModelAttribute("locationToEdit") Location locationToEdit,
            BindingResult result, Model model) throws LocationInvalidDataException {
        if (result.hasErrors()){
            return "editLocation";
        }    
        service.updateLocation(locationToEdit);
        return "redirect:displayLocationsPage";
    }
    
    @RequestMapping(value="/location/{id}", method=RequestMethod.DELETE)
    public String deleteLocation(@PathVariable("id") long locationId) 
            throws LocationInUseException {
        service.deleteLocation(locationId);
        return "redirect:displayLocationsPage";
    }
    
    @RequestMapping(value="/displayOrganizationsPage", method=RequestMethod.GET)
    public String displayOrganizationsPage(Model model) {
        List<Organization> organizations = service.getAllOrganizations();
        model.addAttribute("orgsList", organizations);
        return "organizations";
    }
    
    @RequestMapping(value="/displayOrganizationDetails", method=RequestMethod.GET)
    public String displayOrganizationDetails(HttpServletRequest request, Model model) {
        String orgIdParam = request.getParameter("organizationId");
        int orgId = Integer.parseInt(orgIdParam);
        Organization organization = service.getOrganizationById(orgId);
        model.addAttribute("organization", organization);
        return "viewOrganization";
    }
    
    @RequestMapping(value="/displayAddOrganizationPage", method=RequestMethod.GET)
    public String displayAddOrganizationPage(Model model) {
        model.addAttribute("organization", new Organization());
        List<Location> existingLocations = service.getAllLocations();
        model.addAttribute("existingLocations", existingLocations);
        return "addOrganization";
    }
    
    @RequestMapping(value="/addOrganization", method=RequestMethod.POST)
    public String addOrganization(Model model, HttpServletRequest request, 
            @Valid @ModelAttribute("organization") Organization organization, 
            BindingResult result) throws OrganizationInvalidDataException {
        
        if (result.hasErrors()) {
            List<Location> locations = service.getAllLocations();
            model.addAttribute("existingLocations", locations);
            return "addOrganization";
        }
        organization.setOrganizationName(request.getParameter("organizationName"));
        organization.setOrgDescription(request.getParameter("orgDescription"));
        String isVillain = request.getParameter("villain");
        if (isVillain.equalsIgnoreCase("yes")) {
            organization.setVillain(true);
        } else {
            organization.setVillain(false);
        }
        organization.setPhone(request.getParameter("phone"));
        organization.setEmail(request.getParameter("email"));
        String locationIdParam = request.getParameter("orgLocation");
        Location location = getLocationFromParam(locationIdParam);
        organization.setLocation(location);
        service.addOrganization(organization);
        return "redirect:displayOrganizationsPage";
    }
    
    private Location getLocationFromParam(String locationId) {
        Location location = new Location();
        try {
            long idAsLong = Long.parseLong(locationId);
            location = service.getLocationById(idAsLong);            
        } catch (NullPointerException e) {
            return null;
        }
        return location;
    }
    
    @RequestMapping(value="/displayEditOrganizationPage", method=RequestMethod.GET)
    public String displayEditOrganizationPage(HttpServletRequest request, Model model) {
        List<Location> existingLocations = service.getAllLocations();
        model.addAttribute("existingLocations", existingLocations);
        String orgIdParam = request.getParameter("organizationId");
        int orgId = Integer.parseInt(orgIdParam);
        Organization organizationToEdit = service.getOrganizationById(orgId);
        model.addAttribute("organizationToEdit", organizationToEdit);
        return "editOrganization";
    }
    
    @RequestMapping(value="/editOrganization", method=RequestMethod.POST)
    public String editOrganization(HttpServletRequest request, 
            @Valid @ModelAttribute("organizationToEdit") Organization organizationToEdit, BindingResult result, Model model) 
            throws OrganizationInvalidDataException {
        if (result.hasErrors()){
            List<Location> existingLocations = service.getAllLocations();
            model.addAttribute("existingLocations", existingLocations);
            return "editOrganization";
        }
        String locationIdParam = request.getParameter("orgLocation");
        Long locationId = Long.parseLong(locationIdParam);
        organizationToEdit.setLocation(service.getLocationById(locationId));
        service.updateOrganization(organizationToEdit);
        return "redirect:displayOrganizationsPage";
    }
    
    @RequestMapping(value="/organization/{id}", method=RequestMethod.DELETE)
    public String deleteOrganization(@PathVariable("id") int organizationId) 
            throws OrganizationInUseException {
        service.deleteOrganization(organizationId);
        return "redirect:displayOrganizationsPage";
    }
    
    @RequestMapping(value="/displaySightingsPage", method=RequestMethod.GET)
    public String displaySightingsPage(Model model) {
        List<Sighting> sightings = service.getAllSightings();
        model.addAttribute("sightingsList", sightings);
        return "sightings";
    }
    
    @RequestMapping(value="/displaySightingDetails", method=RequestMethod.GET)
    public String displaySightingDetails(HttpServletRequest request, Model model) {
        String sightingIdParam = request.getParameter("sightingId");
        long sightingId = Long.parseLong(sightingIdParam);
        Sighting sighting = service.getSightingById(sightingId);
        model.addAttribute("sighting", sighting);
        return "viewSighting";
    }
    
    @RequestMapping(value="/displayAddSightingPage", method=RequestMethod.GET)
    public String displayAddSightingPage(Model model) {
        model.addAttribute("sighting", new Sighting());
        List<Location> existingLocations = service.getAllLocations();
        List<Superhuman> existingHeroes = service.getAllSuperhumans();
        model.addAttribute("existingLocations", existingLocations);
        model.addAttribute("existingHeroes", existingHeroes);
        return "addSighting";
    }
    
    @RequestMapping(value="/addSighting", method=RequestMethod.POST)
    public String addSighting(Model model, HttpServletRequest request, 
            @Valid @ModelAttribute("sighting") Sighting sighting, 
            BindingResult result) throws DateTimeParseException, 
            SightingInvalidDataException {
        
        if (result.hasErrors()) {
        List<Location> existingLocations = service.getAllLocations();
        List<Superhuman> existingHeroes = service.getAllSuperhumans();
        model.addAttribute("existingLocations", existingLocations);
        model.addAttribute("existingHeroes", existingHeroes);
            return "addSighting";
        }
        String dateString = request.getParameter("sightingDate");
        LocalDate sightingDate = LocalDate.parse(dateString);
        String locationIdParam = request.getParameter("sightingLocation");
        Location location = getLocationFromParam(locationIdParam);
        sighting.setLocation(location);
        String[] heroParams = request.getParameterValues("superhumans");
        sighting.setHeroes(getSuperhumansFromParamVals(heroParams));
        service.addSighting(sighting);
        return "redirect:displaySightingsPage";
    }
    
    @RequestMapping(value="/displayEditSightingPage", method=RequestMethod.GET)
    public String displayEditSightingPage(HttpServletRequest request, Model model) {
        List<Location> existingLocations = service.getAllLocations();
        List<Superhuman> existingHeroes = service.getAllSuperhumans();
        model.addAttribute("existingLocations", existingLocations);
        model.addAttribute("existingHeroes", existingHeroes);
        String sightingIdParam = request.getParameter("sightingId");
        long sightingId = Long.parseLong(sightingIdParam);
        Sighting sightingToEdit = service.getSightingById(sightingId);
        model.addAttribute("sightingToEdit", sightingToEdit);
        return "editSighting";
    }
    
    @RequestMapping(value="/editSighting", method=RequestMethod.POST)
    public String editSighting(HttpServletRequest request, 
            @Valid @ModelAttribute("sightingToEdit") Sighting sightingToEdit, 
            BindingResult result, Model model) 
            throws SightingInvalidDataException {
        if (result.hasErrors()){
            List<Location> existingLocations = service.getAllLocations();
            List<Superhuman> existingHeroes = service.getAllSuperhumans();
            model.addAttribute("existingLocations", existingLocations);
            model.addAttribute("existingHeroes", existingHeroes);
            return "editSighting";
        }
        String locationIdParam = request.getParameter("sightingLocation");
        Long locationId = Long.parseLong(locationIdParam);
        sightingToEdit.setLocation(service.getLocationById(locationId));
        String[] heroParams = request.getParameterValues("superhumans");
        sightingToEdit.setHeroes(getSuperhumansFromParamVals(heroParams));        
        service.updateSighting(sightingToEdit);
        return "redirect:displaySightingsPage";
    }
    
    @RequestMapping(value="/sighting/{id}", method=RequestMethod.DELETE)
    public String deleteSighting(@PathVariable("id") long sightingId) 
            throws SightingInUseException {
        service.deleteSighting(sightingId);
        return "redirect:displaySightingsPage";
    }
    
    private List<Superhuman> getSuperhumansFromParamVals(String[] superhumanIds) {
        List<Superhuman> superhumans = new ArrayList<>();
        try {
            for (String heroId : superhumanIds) {
                int idAsInt = Integer.parseInt(heroId);
                Superhuman superhuman = service.getSuperhumanById(idAsInt);
                superhumans.add(superhuman);
            }
        } catch (NullPointerException e) {
            return superhumans;
        }
        return superhumans;
    }
    
    @RequestMapping(value="/displayErrorPage", method=RequestMethod.GET)
    public String displayErrorPage(Model model){
        return "error";
    }
}
