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
public class SuperpowerInUseException extends Exception {

    public SuperpowerInUseException(String string) {
        super(string);
    }

    public SuperpowerInUseException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }
}
