/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;
import java.util.Collections;

/**
 *
 * @author diemo
 */
public class Cliente{
    
    Socket socketRef;
    ChatClient refPantalla;
    public ThreadCliente hiloCliente;
    public String pieza = "";

    public Cliente(ChatClient refPantalla) {
        this.refPantalla = refPantalla;
        refPantalla.setRefCliente(this);
    }
    
    public void conectar(){
 
        try{
        
            socketRef = new Socket("localhost", 35577);
            hiloCliente = new ThreadCliente(socketRef, refPantalla);
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
