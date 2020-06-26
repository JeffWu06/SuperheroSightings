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
public class SuperpowerInvalidDataException extends Exception {

    public SuperpowerInvalidDataException(String message) {
        super(message);
    }

    public SuperpowerInvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
