/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.entity;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gonza
 */
public class Solucion implements Serializable{
    private String pasos;
    private List<Nodo> nodos;

    public Solucion() {
        this.pasos = "";
        this.nodos = new ArrayList<>();
    }

    public Solucion(String pasos, List<Nodo> nodos) {
        this.pasos = pasos;
        this.nodos = nodos;
    }

    public Solucion(String pasos) {
        this.pasos = pasos;
        this.nodos = new ArrayList<>();
    }

    public String getPasos() {
        return pasos;
    }

    public void setPasos(String pasos) {
        this.pasos = pasos;
    }

    public List<Nodo> getNodos() {
        return nodos;
    }

    public void setNodos(List<Nodo> nodos) {
        this.nodos = nodos;
    }
    
    public String agregarPaso(String paso){
        if (paso != null && !paso.isEmpty()) {
            pasos = this.pasos.concat("\n").concat(paso);
        }
        return pasos;
    }
    
    
    
}
