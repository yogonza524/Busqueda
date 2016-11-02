/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author pichon
 */
public class Grafo implements Serializable{
    String nombre;
    Map<String,Nodo> nodos = new HashMap<>();
    Map<String,Arista> aristas = new HashMap<>();
    
    public Nodo buscarNodo(String nombre){
        return nodos.get(nombre);
    }
    
    public boolean agregarNodo(Nodo n){
        boolean result = false;
        if (n != null && !n.getNombre().isEmpty()) {
            nodos.put(n.getNombre(), n);
            result = nodos.get(n.getNombre()) != null;
        }
        return result;
    }
    
    public boolean agregarArista(Arista a){
        boolean result = false;
        if (a != null && !a.getNombre().isEmpty()) {
            aristas.put(a.getNombre(), a);
            result = aristas.get(a.getNombre()) != null;
        }
        return result;
    }
    
    public Arista buscarArista(String nombreArista){
        String[] par = nombreArista.split("-");
        String origen = par[0];
        String destino = par[1];
        return (aristas.get(origen + "-" + destino)!= null? aristas.get(origen + "-" + destino) : aristas.get(destino + "-" + origen)) ;
    }
    
    public List<Arista> nodosVecinosDistancia(String nombreNodo){
        List<Arista> result = new ArrayList<>();
        for(Map.Entry<String,Arista> entry : this.aristas.entrySet()){
            String[] par = entry.getKey().split("-");
            String nodo;

            if (par[0].equals(nombreNodo) || par[1].equals(nombreNodo)) {
                if (par[0].equals(nombreNodo)) {
                    nodo = par[1];
                }else{
                    nodo = par[0];
                }
                Arista a = new Arista(nodo, entry.getValue().getValor());
                result.add(a);
            }
        }
//        Collections.sort(result, new Comparator<Arista>() {
//            @Override
//            public int compare(Arista o1, Arista o2) {
//                if (o1.getValor() < o2.getValor()) {
//                    return 1;
//                }
//                return -1;
//            }
//        });
        return result;
    }
    
    public List<String> nodosVecinos(String nombreNodo){
        List<String> result = new ArrayList<>();
        for(Map.Entry<String,Arista> entry : this.aristas.entrySet()){
            String[] par = entry.getKey().split("-");
            String nodo;

            if (par[0].equals(nombreNodo) || par[1].equals(nombreNodo)) {
                if (par[0].equals(nombreNodo)) {
                    nodo = par[1];
                }else{
                    nodo = par[0];
                }
                result.add(nodo);
            }
        }
        return result;
    }
}
