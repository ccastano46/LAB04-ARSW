/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence;

/**
 *
 * @author hcadavid
 */
public class BlueprintNotFoundException extends Exception{

    public static final String NO_AUTOR = "No existe ningun Blueprint con el Autor: ";
    public static final String NO_BLUEPRINT = "No existe ningun Blueprint con dichas caracteristicas";

    public BlueprintNotFoundException(String message) {
        super(message);
    }

    public BlueprintNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
