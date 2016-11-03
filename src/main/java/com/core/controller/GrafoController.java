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
                    Arista a = new Arista(nombreA, Integer.parseInt(valorArista));
                    g.agregarArista(a);
                }
            }
            g.setNombre(nombreGrafo);
        }
        return g;
    }
    
    private static BufferedReader leerFichero(String dir) throws FileNotFoundException{
        FileReader fr = new FileReader(dir);
        BufferedReader bf = new BufferedReader(fr);
        return bf;
    }
    
}
