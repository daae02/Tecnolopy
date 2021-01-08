/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Cliente.Cliente;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diemo
 */
class ThreadServidor extends Thread{
    
    public Socket socketRef;
    public int turno = 0;
    public DataInputStream reader;
    public DataOutputStream writer;
    public   ObjectOutputStream objWriter;
    private ObjectInputStream objReader;
    public String nickname;
    public String pieza;
    private boolean running = true;
    Servidor server;

    public ThreadServidor(Socket socketRef, Servidor server) throws IOException {
        this.socketRef = socketRef;
        reader = new DataInputStream(socketRef.getInputStream());
        writer = new DataOutputStream(socketRef.getOutputStream());
        objWriter = new ObjectOutputStream(socketRef.getOutputStream());
        objReader = new ObjectInputStream(socketRef.getInputStream());
        this.server = server;
    }
    public boolean revisarTurno(int [] turnoTmp){                             
        for (int i = 0; i < server.conexiones.size(); i++){
            ThreadServidor current = server.conexiones.get(i);
            if (current.turno == turnoTmp[0]+turnoTmp[1]){
                return true;
            }
        }
        return false;
    }
    public void run (){        
        int instruccionId = 0;
        while (running){
            try {
                instruccionId = reader.readInt(); // esperar hasta que reciba un entero
                
                switch (instruccionId){
                    case 1:
                        int [] turnoTmp = server.lanzarDados();
                        while (revisarTurno(turnoTmp)){
                            turnoTmp = server.lanzarDados();
                        }
                        turno = turnoTmp[0] + turnoTmp[1];
                        String [] iconDados = server.buscarDados(turnoTmp);
                        for (int i = 0; i < server.conexiones.size(); i++) {
                            ThreadServidor current = server.conexiones.get(i);
                            current.writer.writeInt(1);
                            current.writer.writeInt(turnoTmp[0]);
                            current.writer.writeInt(turnoTmp[1]); 
                            current.writer.writeUTF(iconDados[0]);
                            current.writer.writeUTF(iconDados[1]);
                            
                        }
                    break;
                    case 2:
                        System.out.println("ENTR+O 5555555");
                        int indice = reader.readInt();
                        for (int i = 0; i < server.conexiones.size(); i++) {
                            ThreadServidor current = server.conexiones.get(i);
                            current.writer.writeInt(2);
                            current.writer.writeInt(indice);
                            
                        }
                        break;
                    case 3:
                        int [] turno2Tmp = server.lanzarDados();
                        boolean iguales = turno2Tmp[0] == turno2Tmp[1];
                        String iguales2 = Boolean.toString(iguales);
                        String [] iconDados2 = server.buscarDados(turno2Tmp);
                        for (int i = 0; i < server.conexiones.size(); i++) {
                            ThreadServidor current = server.conexiones.get(i);
                            current.writer.writeInt(8);
                            //current.writer.writeInt(turno2Tmp[0]);
                            //current.writer.writeInt(turno2Tmp[1]); 
                            current.writer.writeUTF(iconDados2[0]);
                            current.writer.writeUTF(iconDados2[1]);
                            current.writer.writeUTF(iguales2);
                            
                        }
                    
                    break;
                    case 4:
                    try {
                        System.out.println("4");
                        writer.writeInt(3);
                        String nickname = reader.readUTF();
                        String pieza = reader.readUTF();
                        this.nickname = nickname;
                        this.pieza = pieza;
                        //String [] arrayTmp = {nickname,pieza};
                        server.nombres.add(nickname);
                        server.urlBotones.add(pieza);
                        /*if (server.urlBotones.size() == server.conexiones.size()){
                            
                            for (int i = 0; i < server.conexiones.size(); i++) {
                                System.out.println("entra"+ i);
                            ThreadServidor current = server.conexiones.get(i);
                            current.writer.writeInt(4);
                            current.objWriter.writeObject(server.urlBotones); 
                            }
                            for (int i = 0; i < server.conexiones.size(); i++) {
                            ThreadServidor current = server.conexiones.get(i);
                            current.writer.writeInt(5);
                            }
                            
                        }*/
                    } catch (IOException ex) {
                        System.out.println("4");
                        Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                    case 5:
                        System.out.println("entra 5");
                        String mensaje = reader.readUTF();
                        System.out.println("entra 5.2");
                        for (int i = 0; i < server.conexiones.size(); i++) {
                            ThreadServidor current = server.conexiones.get(i);
                            current.writer.writeInt(6);
                            current.writer.writeUTF(nickname);
                            current.writer.writeUTF(mensaje);
                        }
                        break;
                    case 6:
                        int index = reader.readInt();
                        String prop = server.juego.getProperties(index);
                        String dinero = server.juego.currentPlayers.get(index).money+" ";
                        writer.writeInt(7);
                        writer.writeUTF(prop);
                        writer.writeUTF(dinero);
                        break;
                        
                        
                }
            } catch (IOException ex) {
                System.out.println(":(");
            }
        }
    }
}
