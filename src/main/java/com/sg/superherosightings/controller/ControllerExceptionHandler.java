/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controller;

import com.sg.superherosightings.service.LocationInUseException;
import com.sg.superherosightings.service.LocationInvalidDataException;
import com.sg.superherosightings.service.OrganizationInUseException;
import com.sg.superherosightings.service.OrganizationInvalidDataException;
import com.sg.superherosightings.service.SightingInUseException;
import com.sg.superherosightings.service.SightingInvalidDataException;
import com.sg.superherosightings.service.SuperhumanInUseException;
import com.sg.superherosightings.service.SuperhumanInvalidDataException;
import com.sg.superherosightings.service.SuperpowerInUseException;
import com.sg.superherosightings.service.SuperpowerInvalidDataException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author Jeff
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    
    @ExceptionHandler({SuperhumanInvalidDataException.class, 
            SuperhumanInUseException.class, SuperpowerInvalidDataException.class,
            SuperpowerInUseException.class, LocationInvalidDataException.class,
            LocationInUseException.class, OrganizationInvalidDataException.class,
            OrganizationInUseException.class, SightingInvalidDataException.class,
            SightingInUseException.class, NullPointerException.class, 
            IllegalStateException.class})
    public /*ModelAndView*/ String processInvalidDataAndInUseExceptions(
            Exception e, Model model) {
//        ModelAndView model = new ModelAndView();
//        model.addObject("errorMessages", e.getMessage());
//        model.setViewName("superheroes");
//        return model;
        model.addAttribute("errorMessages", e.getMessage());
        return "error";
    }
}