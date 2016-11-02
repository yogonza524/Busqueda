/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.util;

import com.core.entity.Arista;
import com.core.entity.Grafo;
import com.core.entity.Nodo;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import ru.lanwen.verbalregex.VerbalExpression;

/**
 *
 * @author pichon
 */
public class Util {
    
    private static VerbalExpression patronEntrada;
    private static BufferedReader bf;
    
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
         
         patronEntrada = VerbalExpression.regex()
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
    
    public static boolean validar(String dir) throws FileNotFoundException, IOException{
        bf = leerFichero(dir);
        String val = "", sCadena;
        while (!(sCadena = bf.readLine()).contains("}")) {
            val = val + sCadena;
        }
        val = val + "}";
        return patronEntrada.testExact(val); 
    }
    
    public static Grafo cargarGrafo(String dir) throws FileNotFoundException, IOException {
        Grafo g = new Grafo();
        String sCadena, nombre = "", valor = "", nombreA= "", valorArista = "";
        int auxi = 0;
        bf = leerFichero(dir);
        bf.readLine(); //avanzo el titulo
        
        while (!(sCadena = bf.readLine()).contains("}")) {
//            System.out.println(sCadena);
            
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
                System.out.println(nombreA + " " + valorArista);
                //Aca creo la conexion entro los dos nodos con los valores obtenidos
                Arista a = new Arista(nombreA, Integer.parseInt(valorArista));
                g.agregarArista(a);
            }
        }
        return g;
    }
    
    private static BufferedReader leerFichero(String dir) throws FileNotFoundException{
        FileReader fr = new FileReader(dir);
        BufferedReader bf = new BufferedReader(fr);
        return bf;
    }
}
