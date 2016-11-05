/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.controller;

import com.core.entity.Arista;
import com.core.entity.Grafo;
import com.core.entity.Nodo;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.lanwen.verbalregex.VerbalExpression;

/**
 *
 * @author gonza
 */
public class GrafoController implements Serializable{

    private final static VerbalExpression PATRON_ENTRADA;
    private static BufferedReader buffer;
    
    static{
        VerbalExpression.Builder patronNodo = VerbalExpression.regex().capt()
                                        .word()
                                        .then("(")
                                        .digit().atLeast(1)
                                        .then(");")
                                        .maybe("\n");
         VerbalExpression.Builder patronAristas = VerbalExpression.regex().capt()
                                        .word()
                                        .then("-")
                                        .word()
                                        .then("(")
                                        .digit().atLeast(1)
                                        .then(");")
                                        .maybe("\n");
         
         PATRON_ENTRADA = VerbalExpression.regex()
                                        .startOfLine()
                                        .then("GRAFO")
                            .space().atLeast(1)
                            .word()
                            .then("{").maybe("\n")
                            .add(patronNodo).atLeast(1)
                            .add(patronAristas).atLeast(1)
                            .then("}")
                            .endOfLine()
                            .build();
    }
    
    private static boolean validar(String dir) throws FileNotFoundException, IOException{
        buffer = leerFichero(dir);
        String val = "", sCadena;
        while (!(sCadena = buffer.readLine()).contains("}")) {
            val = val + sCadena;
        }
        val = val + "}";
        return PATRON_ENTRADA.testExact(val); 
    }
    
    public static Grafo desdeArchivo(String dir) throws FileNotFoundException, IOException {
        Grafo g = null;
        if (validar(dir)) {
            g = new Grafo();
            String sCadena, nombre = "", valor = "", nombreA= "", valorArista = "", nombreGrafo = "";
            buffer = leerFichero(dir);
            sCadena = buffer.readLine(); //avanzo el titulo
            
            //en esta parte leo el nombre del grafo
            sCadena = sCadena.toUpperCase().replaceAll("GRAFO", "").replaceAll("\\{", "").replaceAll("\\s+","");
            nombreGrafo = sCadena;
            System.out.println("Nombre: " + nombreGrafo);

            while (!(sCadena = buffer.readLine()).contains("}")) {
                if (!sCadena.contains("-")) { //en esta parte leo los nodos
                    for (int i = 1; i < sCadena.length(); i++) {
                        if (sCadena.charAt(i) == '(') {
                            nombre = sCadena.substring(0,i);
                            valor = sCadena.substring(i+1,sCadena.length()-2);
                            break;
                        }  
                    }
                    System.out.println(nombre + " " + valor);
                    //Aca debo crear un nuevo nodo y setearle los valores.
                    Nodo n = new Nodo(nombre, Integer.parseInt(valor));
                    g.agregarNodo(n);
                }
                if (sCadena.contains("-")) {
                    for (int i = 1; i < sCadena.length(); i++) {
                        if (sCadena.charAt(i) == '(') {
                            nombreA = sCadena.substring(0, i);
                            valorArista = sCadena.substring(i+1,sCadena.length()-2);
                            break;
                        }
                    }
//                    System.out.println(nombreA + " " + valorArista);
                    //Aca creo la conexion entro los dos nodos con los valores obtenidos
                    Arista a = crearArista(g,nombreA, valorArista);
                    g.agregarArista(a);
                }
            }
            g.setNombre(nombreGrafo);
        }
        return g;
    }
    
    public static Grafo desdeString(String grafoString){
        Grafo result = null;
        String g = grafoString.replaceAll("\n", "");
        if (PATRON_ENTRADA.testExact(g)) {
            result = new Grafo();
            Map<String, Nodo> nodos = new HashMap<>();
            Map<String, Arista> aristas = new HashMap<>();
            //Grafo con sintaxis correcta
            String nombre = grafoString
                    .toUpperCase()
                    .split("\\{")[0]
                    .replaceAll("\\s+", "")
                    .replaceAll("GRAFO", "");
            String[] values = grafoString
                    .split("\\{")[1]
                    .replaceAll("}", "")
                    .replaceAll("\\n", "")
                    .split(";");
            for(String val : values){
                String label = val.split("\\(")[0];
                Integer peso = Integer.valueOf(val.split("\\(")[1].replaceAll("\\s+", "").replaceAll("\\)", ""));
                if (val.contains("-")) {
                    //Es una arista
                    Arista a = new Arista(label, peso);
                    String origen = label.split("-")[0];
                    String destino = label.split("-")[1];
                    
                    Nodo nodoOrigen = GrafoController.desdeNombre(nodos, origen);
                    Nodo nodoDestino = GrafoController.desdeNombre(nodos, destino);
                    
                    a.setOrigen(nodoOrigen);
                    a.setDestino(nodoDestino);
                    
                    aristas.put(label,a);
                    
                }
                else{
                    //Es un nodo
                    Nodo n = new Nodo(label, peso);
                    
                    nodos.put(label,n);
                }
            }
            result.setNombre(nombre);
            result.setNodos(nodos);
            result.setAristas(aristas);
        }
        return result;
    }
    
    private static BufferedReader leerFichero(String dir) throws FileNotFoundException{
        FileReader fr = new FileReader(dir);
        BufferedReader bf = new BufferedReader(fr);
        return bf;
    }

    private static Arista crearArista(Grafo grafo, String nombreArista, String valorArista) {
        Arista result = new Arista(nombreArista, Integer.parseInt(valorArista));
        //Buscar nodos
        String origenNombre = nombreArista.split("-")[0];
        String destinoNombre = nombreArista.split("-")[1];
        
        Nodo origen = null;
        Nodo destino = null;
        
        int repetir = 0;
        for(Map.Entry<String,Nodo> entry : grafo.getNodos().entrySet()){
            if (repetir == 2) {
                break;
            }
            if (entry.getKey().equals(origenNombre)) {
                origen = new Nodo(origenNombre);
                destino = new Nodo(destinoNombre);
                repetir++;
            }
            else{
                if (entry.getKey().equals(destinoNombre)) {
                    origen = new Nodo(destinoNombre);
                    destino = new Nodo(origenNombre);
                    repetir++;
                }
            }
        }
        result.setOrigen(origen);
        result.setDestino(destino);
        return result;
    }
    
    public static Nodo desdeNombre(Map<String, Nodo> nodos, String nombre){
        Nodo n = null;
        if (!nombre.isEmpty() && nodos != null && nodos.size() > 0) {
            for(Map.Entry<String, Nodo> nodo : nodos.entrySet()){
                if (nodo.getValue().getNombre().equals(nombre)) {
                    n = nodo.getValue();
                    break;
                }
            }
        }
        return n;
    }
    
    
}
