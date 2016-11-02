/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.core.entity.Arista;
import com.core.entity.Grafo;
import com.core.entity.Nodo;
import com.core.util.Util;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import ru.lanwen.verbalregex.VerbalExpression;

/**
 *
 * @author pichon
 */
public class MainTest {
    
    public MainTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     
     

    @Test
//    @Ignore
    public void testGenGrafo() throws IOException{
        
        String dir = "C:\\Users\\pichon\\Desktop\\PS\\grafoBusqueda.txt";
        if ( !Util.validar(dir) ) {
            System.out.println("Archivo incorrecto");
        } else {
            Grafo g = Util.cargarGrafo(dir);
            System.out.println("Busqueda primero en aplitud");
            busquedaAmplitud(g);
            System.out.println("Busqueda primero en profundidad");
            busquedaProfundidad(g);
            busquedaEscaladaSimple(g);
            busquedaEscaladaMaxima(g);
            aEstrella(g);
        }
    }
    
    String inicio = "A";
    String fin = "K";
    
    private void busquedaAmplitud(Grafo g){ //funcionna bien
        Queue<String> cola = new LinkedList<>();
        Queue<String> padresCola = new LinkedList<>();
        List<String> explorados = new ArrayList<>(); //LISTA-NODOS
        List<String> padresExplorados = new ArrayList<>();
        String nodoActual, nodoPadre;
        cola.add(inicio);
        padresCola.add("#");
        while(true){
            System.out.println(cola);
            if (cola.isEmpty()) {
                System.out.println("No se encontro el nodo destino");
                break;
            }
            nodoActual = cola.poll();
            nodoPadre = padresCola.poll();
            explorados.add(nodoActual);
            padresExplorados.add(nodoPadre);
            if (nodoActual.equals(fin)) {
                System.out.println("Nodo destino alcanzado"); //Mostrar camino
                String nodo = nodoActual;
                String secuenciaResultado = "";
                while(nodo != "#"){
                    secuenciaResultado = nodo + " " + secuenciaResultado;
                    nodo = padresExplorados.get(explorados.indexOf(nodo));
                }
                System.out.println("Camino solucion: " + secuenciaResultado);
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
        
    }
    
    private void busquedaProfundidad(Grafo g){  //funcionna bien
        Deque<String> pila = new ArrayDeque<>();
        Deque<String> padresPila = new ArrayDeque<>();
        List<String> explorados = new ArrayList<>();
        List<String> padresExplorados = new ArrayList<>();
        String nodoActual, nodoPadre;
        
        pila.push(inicio);
        padresPila.push("#");
        while(true){
            System.out.println(pila);
            if (pila.isEmpty()) {
                System.out.println("No se encontro el nodo destino");
                break;
            }
            nodoActual = pila.pop();
            nodoPadre = padresPila.pop();
            explorados.add(nodoActual);
            padresExplorados.add(nodoPadre);
            if (nodoActual.equals(fin)) {
                System.out.println("Nodo destino alcanzado"); //Mostrar camino
                String nodo = nodoActual;
                String secuenciaResultado = "";
                while(nodo != "#"){
                    secuenciaResultado = nodo + " " + secuenciaResultado;
                    nodo = padresExplorados.get(explorados.indexOf(nodo));
                }
                System.out.println("Camino solucion: " + secuenciaResultado);
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
    }
   
    private void busquedaEscaladaSimple(Grafo g){

        List<String> explotados = new ArrayList<>();
        String nodoActual = inicio;
        explotados.add(nodoActual);
        boolean bandera;
        while(true){
            int valorNodoActual = g.buscarNodo(nodoActual).getValor();
            int valorNodoProx;
            if (nodoActual.equals(fin)) {
                System.out.println("Se alcanzo el nodo destino");
                break;
            }
            
            List<String> vecinos = g.nodosVecinos(nodoActual);
            for (int i = 0; i < vecinos.size(); i++) {
                if(explotados.contains(vecinos.get(i))){
                    vecinos.remove(i);
                }
            }
            if (vecinos.isEmpty()) {
                System.out.println("No se alcanzo el nodo destino");
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
                System.out.println("No se alcanzo el nodo destino");
                break;
            }
        }
        System.out.println(explotados);
    }
    
    private void busquedaEscaladaMaxima(Grafo g){

        List<String> explotados = new ArrayList<>();
        String nodoActual = inicio;
        explotados.add(nodoActual);
        boolean bandera;
        while(true){
            int valorNodoActual = g.buscarNodo(nodoActual).getValor();
            int valorNodoProx;
            if (nodoActual.equals(fin)) {
                System.out.println("Se alcanzo el nodo destino");
                break;
            }
            
            List<String> vecinos = g.nodosVecinos(nodoActual);
            for (int i = 0; i < vecinos.size(); i++) {
                if(explotados.contains(vecinos.get(i))){
                    vecinos.remove(i);
                }
            }
            if (vecinos.isEmpty()) {
                System.out.println("No se alcanzo el nodo destino");
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
                System.out.println("No se alcanzo el nodo destino");
                break;
            }
            explotados.add(nodoActual);
        }
        System.out.println(explotados);
    }

    private void aEstrella(Grafo g){

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
            System.out.println(padresAbiertos);
            System.out.println(abiertos);
            System.out.println(cerrados);
            if (abiertos.isEmpty()) {
                System.out.println("Error");
                break;
            }
            if (abiertos.size() == 1) {
                mejorNodo = abiertos.get(0);
            } else {
                mejorNodo = getMejorNodo(abiertos, valorFuncion);
            }
            if (mejorNodo == fin) {
                System.out.println("Nodo fin alcanzado"); //Mostrar camino
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
    }
    
    private String getMejorNodo(List<String> abiertos, List<Integer> valorFuncion){
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