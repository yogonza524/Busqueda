/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.controller;

import com.core.entity.Grafo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author gonza
 */
public class AlgoritmoController {
    
    public static String busquedaAmplitud(Grafo g, String result, String nodoInicioNombre, String nodoFinNombre){ //funcionna bien
        result = "Algoritmo Primero en Amplitud";
        result += "\nCantidad de nodos: " + g.getNodos().size();
        result += "\nCantidad de aristas: " + g.getAristas().size();
        Queue<String> cola = new LinkedList<>();
        Queue<String> padresCola = new LinkedList<>();
        List<String> explorados = new ArrayList<>(); //LISTA-NODOS
        List<String> padresExplorados = new ArrayList<>();
        String nodoActual, nodoPadre;
        cola.add(nodoInicioNombre);
        padresCola.add("#");
        while(true){
            System.out.println(cola);
            if (cola.isEmpty()) {
                result += "\nNo se encontro el nodo destino";
                break;
            }
            nodoActual = cola.poll();
            nodoPadre = padresCola.poll();
            explorados.add(nodoActual);
            padresExplorados.add(nodoPadre);
            if (nodoActual.equals(nodoFinNombre)) {
                result += "\nNodo destino alcanzado"; //Mostrar camino
                String nodo = nodoActual;
                String secuenciaResultado = "";
                while(nodo != "#"){
                    secuenciaResultado = nodo + " " + secuenciaResultado;
                    nodo = padresExplorados.get(explorados.indexOf(nodo));
                }
                result += "\nCamino solucion: " + secuenciaResultado;
                break;
            }
            List<String> vecinos = g.nodosVecinos(nodoActual);
            Iterator<String> i = vecinos.iterator();
            while(i.hasNext()){
                String a = i.next();
                if ( !explorados.contains(a) && !cola.contains(a)) {
                    cola.add(a);
                    padresCola.add(nodoActual);
                }
            }
        }
        return result;
    }
    
}
