/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.util;

/**
 *
 * @author gonza
 */
public enum Algoritmos {
    
    PRIMERO_EN_PROFUNDIDAD("Primero en profundidad"),
    PRIMERO_EN_AMPLITUD("Primero en amplitud"),
    ESCALADA_SIMPLE("Escalada simple"),
    ESCALADA_MAXIMA("Escalada maxima"),
    A_ESTRELLA("A*")
    ;
    
    private final String value;
    
    public String getValue(){
        return this.value;
    }

    Algoritmos(String value){
        this.value = value;
    }
}
