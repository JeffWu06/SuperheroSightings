/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

/**
 *
 * @author Jeff
 */
public class SuperhumanInUseException extends Exception {

    public SuperhumanInUseException(String string) {
        super(string);
    }

    public SuperhumanInUseException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }
    
}
