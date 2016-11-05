/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.controller;

import com.core.entity.Grafo;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
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
        result = "Algoritmo de Busqueda Primero en Amplitud";
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
    
    public static String busquedaProfundidad(Grafo g, String inicio, String fin){  
        Deque<String> pila = new ArrayDeque<>();
        Deque<String> padresPila = new ArrayDeque<>();
        List<String> explorados = new ArrayList<>();
        List<String> padresExplorados = new ArrayList<>();
        String nodoActual, nodoPadre;
        String result = "Algoritmo de Busqueda Primero en Profundidad";
        result += "\nCantidad de nodos: " + g.getNodos().size();
        result += "\nCantidad de aristas: " + g.getAristas().size();
        pila.push(inicio);
        padresPila.push("#");
        while(true){
            result += "\nPila: " + Arrays.toString(pila.toArray());
            if (pila.isEmpty()) {
                result += "\nNo se encontro el nodo destino";
                break;
            }
            nodoActual = pila.pop();
            nodoPadre = padresPila.pop();
            explorados.add(nodoActual);
            padresExplorados.add(nodoPadre);
            if (nodoActual.equals(fin)) {
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
            for (int i = vecinos.size()-1; i >= 0; i--) {
                String a = vecinos.get(i);
                if ( !explorados.contains(a)) {
                    if (pila.contains(a)) {
                        pila.remove(a);
                        padresPila.remove(nodoActual);
                    }
                    pila.push(a);
                    padresPila.push(nodoActual);
                }
            }
        }
        return result;
    }
    
    public static String busquedaEscaladaSimple(Grafo g, String inicio, String fin){
        String result = "Algoritmo de Busqueda Escalada Simple";
        result += "\nCantidad de nodos: " + g.getNodos().size();
        result += "\nCantidad de aristas: " + g.getAristas().size();
        List<String> explotados = new ArrayList<>();
        String nodoActual = inicio;
        explotados.add(nodoActual);
        boolean bandera;
        while(true){
            int valorNodoActual = g.buscarNodo(nodoActual).getValor();
            int valorNodoProx;
            if (nodoActual.equals(fin)) {
                result +="\nSe alcanzo el nodo destino";
                break;
            }
            
            List<String> vecinos = g.nodosVecinos(nodoActual);
            for (int i = 0; i < vecinos.size(); i++) {
                if(explotados.contains(vecinos.get(i))){
                    vecinos.remove(i);
                }
            }
            if (vecinos.isEmpty()) {
                result += "\nNo se alcanzo el nodo destino";
                break;
            }
            bandera = true;
            for (int i = 0; i < vecinos.size(); i++) {
                valorNodoProx = g.buscarNodo(vecinos.get(i)).getValor();
                if(valorNodoActual > valorNodoProx){
                    nodoActual = vecinos.get(i);
                    explotados.add(nodoActual);
                    bandera = false;
                    break;
                }
            }
            if (bandera) {
                result += "\nNo se alcanzo el nodo destino";
                break;
            }
        }
        result += "\nExplotados: " + Arrays.toString(explotados.toArray());
        return result;
    }
    
    public static String busquedaEscaladaMaxima(Grafo g, String inicio, String fin){
        String result = "Algoritmo de Busqueda Escalada Maxima";
        result += "\nCantidad de nodos: " + g.getNodos().size();
        result += "\nCantidad de aristas: " + g.getAristas().size();
        List<String> explotados = new ArrayList<>();
        String nodoActual = inicio;
        explotados.add(nodoActual);
        boolean bandera;
        while(true){
            int valorNodoActual = g.buscarNodo(nodoActual).getValor();
            int valorNodoProx;
            if (nodoActual.equals(fin)) {
                result += "\nSe alcanzo el nodo destino";
                break;
            }
            
            List<String> vecinos = g.nodosVecinos(nodoActual);
            for (int i = 0; i < vecinos.size(); i++) {
                if(explotados.contains(vecinos.get(i))){
                    vecinos.remove(i);
                }
            }
            if (vecinos.isEmpty()) {
                result += "\nNo se alcanzo el nodo destino";
                break;
            }
            bandera = true;
            for (int i = 0; i < vecinos.size(); i++) {
                valorNodoProx = g.buscarNodo(vecinos.get(i)).getValor();
                if(valorNodoActual > valorNodoProx){
                    nodoActual = vecinos.get(i);
                    valorNodoActual = valorNodoProx;
                    bandera = false;
                }
            }
            if (bandera) {
                result += "\nNo se alcanzo el nodo destino";
                break;
            }
            explotados.add(nodoActual);
        }
        result += "\nExplotados" + Arrays.toString(explotados.toArray());
        return result;
    }
    
    public static String aEstrella(Grafo g, String inicio, String fin){
        String result = "Algoritmo de Busqueda A*";
        result += "\nCantidad de nodos: " + g.getNodos().size();
        result += "\nCantidad de aristas: " + g.getAristas().size();
        List<String> abiertos = new ArrayList<>();
        List<Integer> valorFuncion = new ArrayList<>();
        List<String> cerrados = new ArrayList<>();
        List<String> padresCerrados = new ArrayList<>();
        List<String> padresAbiertos = new ArrayList<>();
        List<String> sucesores = new ArrayList<>();
        List<Integer> valorFuncionSucesores = new ArrayList<>();
        String mejorNodo, nodoActual;
        int index;
        
        abiertos.add(inicio);
        padresAbiertos.add("#");
        valorFuncion.add(g.buscarNodo(inicio).getValor()+0);
        while(true){
            result += "\nPadres abiertos" + Arrays.toString(padresAbiertos.toArray());
            result += "\nAbiertos: " + Arrays.toString(abiertos.toArray());
            result += "\nCerrados: " + Arrays.toString(cerrados.toArray());
            if (abiertos.isEmpty()) {
                result += "\nError";
                break;
            }
            if (abiertos.size() == 1) {
                mejorNodo = abiertos.get(0);
            } else {
                mejorNodo = getMejorNodo(abiertos, valorFuncion);
            }
            if (mejorNodo == fin) {
                result += "\nNodo fin alcanzado"; //Mostrar camino
                index = abiertos.indexOf(mejorNodo);
                cerrados.add(mejorNodo);
                padresCerrados.add(abiertos.get(index));
                String nodo = mejorNodo;
                String secuenciaResultado = "";
                while(nodo != "#"){
                    secuenciaResultado = nodo + secuenciaResultado;
                    nodo = padresCerrados.get(cerrados.indexOf(nodo));
                }
            }
            index = abiertos.indexOf(mejorNodo);
            abiertos.remove(index);
            valorFuncion.remove(index);
            cerrados.add(mejorNodo);
            padresCerrados.add(padresAbiertos.get(index));
            padresAbiertos.remove(index);
            
            sucesores = g.nodosVecinos(mejorNodo);
            int heuristicaNodo;
            int costoHastaNodoActual = g.buscarNodo(mejorNodo).getValor();
            for (int i = 0; i < sucesores.size(); i++) {
                heuristicaNodo = g.buscarNodo(sucesores.get(i)).getValor();
                valorFuncionSucesores.add(costoHastaNodoActual + heuristicaNodo);
                if(abiertos.contains(sucesores.get(i))){
                    index = abiertos.indexOf(sucesores.get(i));
                    if (valorFuncion.get(index) < valorFuncionSucesores.get(i)) {
                        valorFuncion.add(index, valorFuncionSucesores.get(i));
                        padresAbiertos.add(index, mejorNodo);
                    }
                } else {
                    if (cerrados.contains(sucesores.get(i))) {
//                        if (valorFuncion.get(index) < valorFuncionSucesores.get(i)) {
//                            valorFuncion.add(index, valorFuncionSucesores.get(i));
//                            padresAbiertos.add(index, mejorNodo);
//                        }
//                      Nose que cojones hacer aca
                    } else {
                        abiertos.add(sucesores.get(i));
                        padresAbiertos.add(mejorNodo);
                        valorFuncion.add(valorFuncionSucesores.get(i));
                    }
                }
            } //fin for
        } //fin while
        return result;
    }
    
    private static String getMejorNodo(List<String> abiertos, List<Integer> valorFuncion){
        int min = valorFuncion.get(0);
        int posicion = 0;
        for (int i = 1; i < valorFuncion.size(); i++) {
            if (min > valorFuncion.get(i)) {
                min = valorFuncion.get(i);
                posicion = i;
            }
        }
        return abiertos.get(posicion);
    }
}
