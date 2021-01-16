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
import static java.lang.Boolean.parseBoolean;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

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
    public boolean dadosIguales = false;
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
        int indiceSubastador = 0;
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
                        dadosIguales = iguales;
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
                            current.writer.writeInt(turno2Tmp[0]+turno2Tmp[1]);
                            
                            
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
                    case 7:
                        if (server.turno >= server.conexiones.size()){
                            server.turno = 0;
                        }
                        if (server.indexJugadorActual >= server.conexiones.size()){
                            server.indexJugadorActual = 0;
                        }
                        for (int i = 0; i < server.conexiones.size(); i++) {
                            ThreadServidor current = server.conexiones.get(i);
                            if (i == server.indexJugadorActual){
                                current.writer.writeInt(9);
                                //validación para revisar
                            }
                            else{
                                current.writer.writeInt(10);
                            }
                        }
                        server.indexJugadorActual++;
                        server.turno++;
                        break;
                    case 8:
                        int labelActual = reader.readInt();
                        int casillaMover = reader.readInt();
                        ImageIcon icono = (ImageIcon) objReader.readObject();
                        
                       for (int i = 0; i < server.conexiones.size(); i++) {
                            ThreadServidor current = server.conexiones.get(i);
                            current.writer.writeInt(11);
                            current.writer.writeInt(labelActual);
                            current.writer.writeInt(casillaMover);
                            current.objWriter.writeObject(icono);
                            
                        }
                       break;
                    case 9:
                        int casillasAMover = reader.readInt();
                        server.juego.turnoJugador(server.conexiones.indexOf(this), casillasAMover, dadosIguales);
                        break;   
                    case 10:
                        System.out.println("entra case 10");
                        server.juego.comprarPropiedad(server.conexiones.indexOf(this));
                        break;
                    case 11:
                        String imagen = reader.readUTF();
                        for (int i = 0; i < server.conexiones.size(); i++) {
                            ThreadServidor current = server.conexiones.get(i);
                            current.writer.writeInt(14);
                            current.writer.writeUTF(imagen);
                            System.out.println("Envio dinero");
                            current.writer.writeInt(server.juego.currentPlayers.get(i).money);
                            System.out.println("Envio costo propiedad");
                            current.writer.writeInt(server.juego.getPropertyValue(server.conexiones.indexOf(this)));
                            current.objWriter.writeObject(server.nombres);
                            current.writer.writeInt(server.juego.currentPlayers.get(server.conexiones.indexOf(this)).currentIndex);
                            System.out.println("Listo :)");
                        }
                        break;
                    case 12:
                        System.out.println("Se retira: "+server.nombres.get(server.conexiones.indexOf(this)));
                        for (int i = 0; i < server.conexiones.size(); i++) {
                            ThreadServidor current = server.conexiones.get(i);
                            current.writer.writeInt(15);
                            current.writer.writeUTF(server.nombres.get(server.conexiones.indexOf(this)));
                        }
                        break;
                    case 13:
                        int nuevoMonto = reader.readInt();
                        for (int i = 0; i < server.conexiones.size(); i++) {
                            ThreadServidor current = server.conexiones.get(i);
                            current.writer.writeInt(16);
                            current.writer.writeInt(nuevoMonto);
                            current.writer.writeUTF(server.nombres.get(server.conexiones.indexOf(this)));
                        }
                        break;
                    case 14:
                        String ganador = reader.readUTF();
                        int monto = reader.readInt();
                        int indiceProp = reader.readInt();
                        for(int i= 0; i <server.nombres.size();i++){
                            if(server.nombres.get(i).equals(ganador)){
                                System.out.println("Bucando a:"+ganador);
                                for (int j = 0; j < server.conexiones.size(); j++) {
                                    ThreadServidor current = server.conexiones.get(j);
                                    current.writer.writeInt(6);
                                    current.writer.writeUTF(ganador);
                                    current.writer.writeUTF("Ha ganado la subasta");
                                }
                                System.out.println("Por: &"+monto);
                                server.juego.comprarPorSubasta(i,indiceProp,monto);
                                System.out.println("Comprado");
                            }
                                
                        }
                        break;
                    case 15:
                        int dineroTmp = server.juego.currentPlayers.get(server.conexiones.indexOf(this)).money;
                        writer.writeInt(19);
                        writer.writeInt(dineroTmp);
                        break;
                    case 16:
                        server.juego.salirCarcelBtn(server.conexiones.indexOf(this));
                        break;
                        
                }
            } catch (IOException ex) {
                System.out.println(":(");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
