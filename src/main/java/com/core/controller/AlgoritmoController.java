/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.controller;

import com.core.entity.Grafo;
import com.core.entity.Nodo;
import com.core.entity.Solucion;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.apache.commons.lang.ArrayUtils;

/**
 *
 * @author gonza
 */
public class AlgoritmoController {
    
    public static Solucion busquedaAmplitud(Grafo g, String nodoInicioNombre, String nodoFinNombre){ //funcionna bien
        Solucion result = new Solucion();
        result.setPasos("Algoritmo de Busqueda Primero en Amplitud");
        result.agregarPaso("Cantidad de nodos: " + g.getNodos().size());
        result.agregarPaso("Cantidad de aristas: " + g.getAristas().size());
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
                result.agregarPaso("No se encontro el nodo destino");
                break;
            }
            nodoActual = cola.poll();
            nodoPadre = padresCola.poll();
            explorados.add(nodoActual);
            padresExplorados.add(nodoPadre);
            if (nodoActual.equals(nodoFinNombre)) {
                result.agregarPaso("Nodo fin alcanzado"); //Mostrar camino
                String nodo = nodoActual;
                String secuenciaResultado = "";
                while(nodo != "#"){
                    secuenciaResultado = nodo + " " + secuenciaResultado;
                    nodo = padresExplorados.get(explorados.indexOf(nodo));
                }
                result.agregarPaso("Camino solucion: " + secuenciaResultado);
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
        //Verifico la solucion y la guardo en Solucion
        if (result.getPasos().contains("Nodo fin alcanzado")) {
            String solucion = result.getPasos().split("Nodo fin alcanzado\n")[1];
            String[] array = solucion.split(" ");
//            ArrayUtils.reverse(array);
            for(String nombreNodo : array){
                System.out.println("--------------------------------------");
                for(Map.Entry<String, Nodo> n : g.getNodos().entrySet()){
                    System.out.println("Comparando " + n.getKey() + " con " + nombreNodo);
                    if (n.getKey().equals(nombreNodo)) {
                        System.out.println("Son iguales! Agregando " + nombreNodo + " a la lista");
                        result.getNodos().add(n.getValue());
                    }
                }
            }
            
            System.out.println("Nodos del resultado final en la lista: " + Arrays.toString(result.getNodos().toArray()));
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
    
    public static Solucion busquedaProfundidadConSolucion(Grafo g, String inicio, String fin){  
        Deque<String> pila = new ArrayDeque<>();
        Deque<String> padresPila = new ArrayDeque<>();
        List<String> explorados = new ArrayList<>();
        List<String> padresExplorados = new ArrayList<>();
        String nodoActual, nodoPadre;
        Solucion result = new Solucion("Algoritmo de Busqueda Primero en Profundidad");
        result.agregarPaso("Cantidad de nodos: " + g.getNodos().size());
        result.agregarPaso("Cantidad de aristas: " + g.getAristas().size());
        pila.push(inicio);
        padresPila.push("#");
        while(true){
            result.agregarPaso("Pila: " + Arrays.toString(pila.toArray()));
            if (pila.isEmpty()) {
                result.agregarPaso("No se encontro el nodo destino");
                break;
            }
            nodoActual = pila.pop();
            nodoPadre = padresPila.pop();
            explorados.add(nodoActual);
            padresExplorados.add(nodoPadre);
            if (nodoActual.equals(fin)) {
                result.agregarPaso("Nodo fin alcanzado"); //Mostrar camino
                String nodo = nodoActual;
                String secuenciaResultado = "";
                while(nodo != "#"){
                    secuenciaResultado = nodo + " " + secuenciaResultado;
                    nodo = padresExplorados.get(explorados.indexOf(nodo));
                }
                result.agregarPaso("Camino solucion: " + secuenciaResultado);
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
        //Verifico la solucion y la guardo en Solucion
        if (result.getPasos().contains("Nodo fin alcanzado")) {
            String solucion = result.getPasos().split("Nodo fin alcanzado\n")[1];
            System.out.println("Solucion: " + solucion);
            String[] array = solucion.split(" ");
//            ArrayUtils.reverse(array);
            for(String nombreNodo : array){
                System.out.println("--------------------------------------");
                for(Map.Entry<String, Nodo> n : g.getNodos().entrySet()){
                    System.out.println("Comparando " + n.getKey() + " con " + nombreNodo);
                    if (n.getKey().equals(nombreNodo)) {
                        System.out.println("Son iguales! Agregando " + nombreNodo + " a la lista");
                        result.getNodos().add(n.getValue());
                    }
                }
            }
            
            System.out.println("Nodos del resultado final en la lista: " + Arrays.toString(result.getNodos().toArray()));
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
    
    public static Solucion busquedaEscaladaSimpleConSolucion(Grafo g, String inicio, String fin){
        Solucion result = new Solucion("Algoritmo de Busqueda Escalada Simple");
        result.agregarPaso("Cantidad de nodos: " + g.getNodos().size());
        result.agregarPaso("Cantidad de aristas: " + g.getAristas().size());
        List<String> explotados = new ArrayList<>();
        String nodoActual = inicio;
        explotados.add(nodoActual);
        boolean bandera;
        while(true){
            int valorNodoActual = g.buscarNodo(nodoActual).getValor();
            int valorNodoProx;
            if (nodoActual.equals(fin)) {
                result.agregarPaso("Nodo fin alcanzado");
                break;
            }
            
            List<String> vecinos = g.nodosVecinos(nodoActual);
            for (int i = 0; i < vecinos.size(); i++) {
                if(explotados.contains(vecinos.get(i))){
                    vecinos.remove(i);
                }
            }
            if (vecinos.isEmpty()) {
                result.agregarPaso("No se alcanzo el nodo destino");
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
                result.agregarPaso("No se alcanzo el nodo destino");
                break;
            }
        }
        result.agregarPaso("Explotados: " + Arrays.toString(explotados.toArray()));
        //Verifico la solucion y la guardo en Solucion
        if (result.getPasos().contains("Nodo fin alcanzado")) {
            if (result.getPasos().split("Nodo fin alcanzado\n").length > 1) {
                String solucion = Arrays.toString(explotados.toArray());
                String[] array = solucion.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").split(",");
//                ArrayUtils.reverse(array);
                for(String nombreNodo : array){
                    System.out.println("--------------------------------------");
                    for(Map.Entry<String, Nodo> n : g.getNodos().entrySet()){
                        System.out.println("Comparando " + n.getKey() + " con " + nombreNodo);
                        if (n.getKey().equals(nombreNodo)) {
                            System.out.println("Son iguales! Agregando " + nombreNodo + " a la lista");
                            result.getNodos().add(n.getValue());
                        }
                    }
                }

                System.out.println("Nodos del resultado final en la lista: " + Arrays.toString(result.getNodos().toArray()));
            }
            else{
                System.out.println("No se encontro la solucion");
            }
        }
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
    
    public static Solucion busquedaEscaladaMaximaConSolucion(Grafo g, String inicio, String fin){
        Solucion result = new Solucion("Algoritmo de Busqueda Escalada Maxima");
        result.agregarPaso("\nCantidad de nodos: " + g.getNodos().size());
        result.agregarPaso("\nCantidad de aristas: " + g.getAristas().size());
        List<String> explotados = new ArrayList<>();
        String nodoActual = inicio;
        explotados.add(nodoActual);
        boolean bandera;
        while(true){
            int valorNodoActual = g.buscarNodo(nodoActual).getValor();
            int valorNodoProx;
            if (nodoActual.equals(fin)) {
                result.agregarPaso("\nNodo fin alcanzado");
                break;
            }
            
            List<String> vecinos = g.nodosVecinos(nodoActual);
            for (int i = 0; i < vecinos.size(); i++) {
                if(explotados.contains(vecinos.get(i))){
                    vecinos.remove(i);
                }
            }
            if (vecinos.isEmpty()) {
                result.agregarPaso("\nNo se alcanzo el nodo destino");
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
                result.agregarPaso("\nNo se alcanzo el nodo destino");
                break;
            }
            explotados.add(nodoActual);
        }
        result.agregarPaso("\nExplotados" + Arrays.toString(explotados.toArray()));
        //Verifico la solucion y la guardo en Solucion
        if (result.getPasos().contains("Nodo fin alcanzado")) {
            String solucion = Arrays.toString(explotados.toArray());
            String[] array = solucion.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").split(",");
//            ArrayUtils.reverse(array);
            for(String nombreNodo : array){
                System.out.println("--------------------------------------");
                for(Map.Entry<String, Nodo> n : g.getNodos().entrySet()){
                    System.out.println("Comparando " + n.getKey() + " con " + nombreNodo);
                    if (n.getKey().equals(nombreNodo)) {
                        System.out.println("Son iguales! Agregando " + nombreNodo + " a la lista");
                        result.getNodos().add(n.getValue());
                    }
                }
            }

            System.out.println("Nodos del resultado final en la lista: " + Arrays.toString(result.getNodos().toArray()));
        }
        return result;
    }
    
    public static String aEstrella(Grafo g, String inicio, String fin){
        String result = "Algoritmo de Busqueda A*";
        List<String> abiertos = new ArrayList<>();
        List<String> padresAbiertos = new ArrayList<>();
        List<Integer> valorFuncionAbiertos = new ArrayList<>();
        List<String> cerrados = new ArrayList<>();
        List<String> padresCerrados = new ArrayList<>();
        List<String> sucesores;
        String mejorNodo;
        int index;
        
        //Algoritmo A*
        abiertos.add(inicio);
        padresAbiertos.add("#");
        valorFuncionAbiertos.add(g.buscarNodo(inicio).getValor()+0);
        while(true){
            result += "\nPadresCerrados: " + padresCerrados;
            result += "\nCerrados: " + cerrados;            
            result += "\nAbiertos: " + abiertos;
            result += "\nValorFun: " + valorFuncionAbiertos;
            int costoHastaNodoActual, heuristicaNodo, valorFun;
            String padre;

            //Si esta vacia devuelve error
            if (abiertos.isEmpty()) {
                result += "\nError";
                break;
            }
            //Obtengo el mejor nodo de abiertos
            if (abiertos.size() == 1) {
                mejorNodo = abiertos.get(0);
            } else {
                mejorNodo = getMejorNodo(abiertos, valorFuncionAbiertos);
                result += "\nMejor nodo: " + mejorNodo;
            }
            //Pregunto si es el nodo final
            if (mejorNodo.equals(fin)) {
                result += "\nNodo fin alcanzado"; //Mostrar camino
                cerrados.add(mejorNodo);
                padresCerrados.add(padresAbiertos.get(abiertos.indexOf(mejorNodo)));
                String nodo = mejorNodo;
                ArrayList<String> secuenciaResultado = new ArrayList<>();
                //Mostrar el camino hasta la solucion
                while(true){
                    secuenciaResultado.add(nodo);
                    nodo = padresCerrados.get(cerrados.indexOf(nodo));
                    if (nodo.equals("#")){
                        result += "\n" + secuenciaResultado;
                        break;
                    }
                }
                break;
            }
            index = abiertos.indexOf(mejorNodo);
            padre = padresAbiertos.get(index);
            //obtengo el costo hasta mejorNodo
            heuristicaNodo = g.buscarNodo(mejorNodo).getValor();
            costoHastaNodoActual = (valorFuncionAbiertos.get(index) - heuristicaNodo);
            //remuevo el nodo a ser explotado
            abiertos.remove(index);
            padresAbiertos.remove(index);
            valorFuncionAbiertos.remove(index);
            //lo agrego en cerrados
            cerrados.add(mejorNodo);
            padresCerrados.add(padre);
            //obtenemos los sucesores de mejor nodo
            sucesores = g.nodosVecinos(mejorNodo);
            
            for (int i = 0; i < sucesores.size(); i++) {
                heuristicaNodo = g.buscarNodo(sucesores.get(i)).getValor();
                valorFun = costoHastaNodoActual + (g.buscarArista(mejorNodo+"-"+sucesores.get(i))).getValor() + heuristicaNodo;
                //pregunto si ya existe el noso en abiertos
                if(abiertos.contains(sucesores.get(i))){
                    index = abiertos.indexOf(sucesores.get(i));
                    //Si ya existe pregunto si el nuevo nodo es mejor
                    if (valorFuncionAbiertos.get(index) < valorFun) {
                        valorFuncionAbiertos.set(index, valorFun);
                        padresAbiertos.set(index, mejorNodo);
                    }
                } else {
                    //pregunto si esta en cerrados
                    if (cerrados.contains(sucesores.get(i))) {
                        //no hacer nada
                    } else {
                        abiertos.add(sucesores.get(i));
                        padresAbiertos.add(mejorNodo);
                        valorFuncionAbiertos.add(valorFun);
                    }
                }
            } //fin for
        } //fin while
        return result;
}
    
    public static Solucion aEstrellaConNodos(Grafo g, String inicio, String fin){
        Solucion result = new Solucion("Algoritmo de busqueda A*");
        List<String> abiertos = new ArrayList<>();
        List<String> padresAbiertos = new ArrayList<>();
        List<Integer> valorFuncionAbiertos = new ArrayList<>();
        List<String> cerrados = new ArrayList<>();
        List<String> padresCerrados = new ArrayList<>();
        List<String> sucesores;
        String mejorNodo;
        int index;
        
        //Algoritmo A*
        abiertos.add(inicio);
        padresAbiertos.add("#");
        valorFuncionAbiertos.add(g.buscarNodo(inicio).getValor()+0);
        while(true){
            result.agregarPaso("PadresCerrados: " + padresCerrados);
            result.agregarPaso("Cerrados: " + cerrados);            
            result.agregarPaso("Abiertos: " + abiertos);
            result.agregarPaso("ValorFun: " + valorFuncionAbiertos);
            int costoHastaNodoActual, heuristicaNodo, valorFun;
            String padre;

            //Si esta vacia devuelve error
            if (abiertos.isEmpty()) {
                result.agregarPaso("Error");
                break;
            }
            //Obtengo el mejor nodo de abiertos
            if (abiertos.size() == 1) {
                mejorNodo = abiertos.get(0);
            } else {
                mejorNodo = getMejorNodo(abiertos, valorFuncionAbiertos);
                result.agregarPaso("Mejor nodo: " + mejorNodo);
            }
            //Pregunto si es el nodo final
            if (mejorNodo.equals(fin)) {
                result.agregarPaso("Nodo fin alcanzado"); //Mostrar camino
                cerrados.add(mejorNodo);
                padresCerrados.add(padresAbiertos.get(abiertos.indexOf(mejorNodo)));
                String nodo = mejorNodo;
                ArrayList<String> secuenciaResultado = new ArrayList<>();
                //Mostrar el camino hasta la solucion
                while(true){
                    secuenciaResultado.add(nodo);
                    nodo = padresCerrados.get(cerrados.indexOf(nodo));
                    if (nodo.equals("#")){
                        String[] r = new String[secuenciaResultado.size()];
                        for (int i = 0; i < r.length; i++) {
                            r[i] = secuenciaResultado.get(i);
                        }
                        ArrayUtils.reverse(r);
                        result.agregarPaso(Arrays.toString(r));
                        break;
                    }
                }
                break;
            }
            index = abiertos.indexOf(mejorNodo);
            padre = padresAbiertos.get(index);
            //obtengo el costo hasta mejorNodo
            heuristicaNodo = g.buscarNodo(mejorNodo).getValor();
            costoHastaNodoActual = (valorFuncionAbiertos.get(index) - heuristicaNodo);
            //remuevo el nodo a ser explotado
            abiertos.remove(index);
            padresAbiertos.remove(index);
            valorFuncionAbiertos.remove(index);
            //lo agrego en cerrados
            cerrados.add(mejorNodo);
            padresCerrados.add(padre);
            //obtenemos los sucesores de mejor nodo
            sucesores = g.nodosVecinos(mejorNodo);
            
            for (int i = 0; i < sucesores.size(); i++) {
                heuristicaNodo = g.buscarNodo(sucesores.get(i)).getValor();
                valorFun = costoHastaNodoActual + (g.buscarArista(mejorNodo+"-"+sucesores.get(i))).getValor() + heuristicaNodo;
                //pregunto si ya existe el noso en abiertos
                if(abiertos.contains(sucesores.get(i))){
                    index = abiertos.indexOf(sucesores.get(i));
                    //Si ya existe pregunto si el nuevo nodo es mejor
                    if (valorFuncionAbiertos.get(index) < valorFun) {
                        valorFuncionAbiertos.set(index, valorFun);
                        padresAbiertos.set(index, mejorNodo);
                    }
                } else {
                    //pregunto si esta en cerrados
                    if (cerrados.contains(sucesores.get(i))) {
                        //no hacer nada
                    } else {
                        abiertos.add(sucesores.get(i));
                        padresAbiertos.add(mejorNodo);
                        valorFuncionAbiertos.add(valorFun);
                    }
                }
            } //fin for
        } //fin while
        //Verifico la solucion y la guardo en Solucion
        if (result.getPasos().contains("Nodo fin alcanzado")) {
            String solucion = result.getPasos().split("Nodo fin alcanzado\n")[1];
            String[] array = solucion.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").split(",");
//            ArrayUtils.reverse(array);
            for(String nombreNodo : array){
                System.out.println("--------------------------------------");
                for(Map.Entry<String, Nodo> n : g.getNodos().entrySet()){
                    if (n.getKey().equals(nombreNodo)) {
                        System.out.println("Son iguales! Agregando " + nombreNodo + " a la lista");
                        result.getNodos().add(n.getValue());
                    }
                }
            }
            
            System.out.println("Nodos del resultado final en la lista: " + Arrays.toString(result.getNodos().toArray()));
        }
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
