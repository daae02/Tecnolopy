/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import javax.swing.JOptionPane;
import java.util.Collections;

/**
 *
 * @author diemo
 */
public class Cliente implements Serializable{//,Comparable<Cliente>{
    
    public transient Socket socketRef;
    public transient MonopolyJF secPantalla;
    public transient ChatClient refPantalla;
    public transient ThreadCliente hiloCliente;
    public String pieza = "null";
    public String nickname = "null";
    public int turno = 0;

    public Cliente(ChatClient refPantalla,MonopolyJF secPantalla) {
        this.refPantalla = refPantalla;
        refPantalla.setRefCliente(this);
        this.secPantalla = secPantalla;
        secPantalla.setRefCliente(this);
    }

    public void conectar(){
 
        try{
        
            socketRef = new Socket("localhost", 35577);
            hiloCliente = new ThreadCliente(this,socketRef, refPantalla, secPantalla);
            hiloCliente.start();
            //String nombre = JOptionPane.showInputDialog("Introduzca un Nick:");
            //refPantalla.setTitle(nombre);
             //instruccion para el switch del thraed servidor
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        
        
    }
    
}
