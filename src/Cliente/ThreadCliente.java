/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author diemo
 */
public class ThreadCliente extends Thread implements Comparable<ThreadCliente> {
    
    private Socket socketRef;
    public DataInputStream reader;
    public DataOutputStream writer;
    private String nombre;
    private boolean running = true;
    private ChatClient refPantalla;
    public int turnoPrimero = 0;

    public ThreadCliente(Socket socketRef, ChatClient refPantalla) throws IOException {
        this.socketRef = socketRef;
        reader = new DataInputStream(socketRef.getInputStream());
        writer = new DataOutputStream(socketRef.getOutputStream());
        this.refPantalla = refPantalla;
    }
    
    @Override
    public int compareTo(ThreadCliente e){
        if(e.turnoPrimero > turnoPrimero){
            return 1;
        }else if(e.turnoPrimero == turnoPrimero){
            return 0;
        }else{
            return -1;
        }
    }
    
    public void run (){
        
        int instruccionId = 1;
        while (running){
            try {
                String usuario = "";
                instruccionId = reader.readInt(); // esperar hasta que reciba un entero
                
                switch (instruccionId){
                    case 1: 
                        int dado1 = reader.readInt();
                        int dado2 = reader.readInt();
                        
                        turnoPrimero = dado1 + dado2;
                        refPantalla.pintarLanzamientoDados(dado1, dado2);
                    break;                    
                    case 2:
                        int indice = reader.readInt();
                        refPantalla.arregloBotones.get(indice).setEnabled(false);
                        break;
                    
                }
            } catch (IOException ex) {
                
            }
        }
    }
}
