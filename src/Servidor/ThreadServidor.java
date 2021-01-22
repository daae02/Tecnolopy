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
    public boolean rendido = false;

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
                        int gratis = reader.readInt();
                        server.juego.salirCarcelBtn(server.conexiones.indexOf(this),gratis);
                        break;
                    case 17:
                        int indiceInvitado = reader.readInt();
                        server.conexiones.get(indiceInvitado).writer.writeInt(20);
                        server.conexiones.get(indiceInvitado).writer.writeInt(server.conexiones.indexOf(this));
                        server.writeInThreadOwner(server.conexiones.indexOf(this),"Ha invitado a un intercambio a: "+server.nombres.get(indiceInvitado));
                        break;                        
                     case 18:
                         int indiceAnfitrion = reader.readInt();
                         int resultado = reader.readInt();
                         server.conexiones.get(indiceAnfitrion).writer.writeInt(21);
                         server.conexiones.get(indiceAnfitrion).writer.writeInt(resultado);
                         server.conexiones.get(indiceAnfitrion).writer.writeInt(server.conexiones.indexOf(this));
                         break;
                     case 19:
                         System.out.println("Envia persona");
                         int persona = reader.readInt();
                         writer.writeInt(22);
                         writer.writeInt(persona);
                         System.out.println("Empiza a enviar");
                         objWriter.writeObject(server.juego.currentPlayers.get(server.conexiones.indexOf(this)).getPropertiesNames());
                         System.out.println("Sale primero");
                         objWriter.writeObject(server.juego.currentPlayers.get(persona).getPropertiesNames());
                         System.out.println("Sale segundo");
                         writer.writeInt(server.juego.currentPlayers.get(server.conexiones.indexOf(this)).money);
                         System.out.println("Sale tercero");
                         writer.writeInt(server.juego.currentPlayers.get(persona).money);   
                         System.out.println("Sale ultimo");
                         break;
                     case 20:
                         int persona2 = reader.readInt();
                         ArrayList<String> prop1 = (ArrayList<String>) objReader.readObject();
                         ArrayList<String> prop2 = (ArrayList<String>) objReader.readObject();
                         int din1 = reader.readInt();
                         int din2 = reader.readInt();
                         ThreadServidor current = server.conexiones.get(persona2);
                         current.writer.writeInt(23);
                         current.writer.writeInt(server.conexiones.indexOf(this));
                         current.objWriter.writeObject(server.juego.currentPlayers.get(server.conexiones.indexOf(this)).getPropertiesNames());
                         current.objWriter.writeObject(server.juego.currentPlayers.get(persona2).getPropertiesNames());;
                         current.writer.writeInt(server.juego.currentPlayers.get(server.conexiones.indexOf(this)).money);
                         current.writer.writeInt(server.juego.currentPlayers.get(persona2).money);   
                         current.objWriter.writeObject(prop1);
                         current.objWriter.writeObject(prop2);
                         current.writer.writeInt(din1);
                         current.writer.writeInt(din2);
                         break;
                     case 21:
                         System.out.println("Awebo que si alv");
                         int rechazado = reader.readInt();
                         server.conexiones.get(rechazado).writer.writeInt(24);
                         server.conexiones.get(rechazado).writer.writeInt(server.conexiones.indexOf(this));
                         break;
                     case 22:
                         System.out.println("Llamando al case 22 2");
                         int personaAceptada = reader.readInt();
                         ArrayList<String> propAceptada1 = (ArrayList<String>) objReader.readObject();
                         ArrayList<String> propAceptada2 = (ArrayList<String>) objReader.readObject();
                         int dinAceptado1 = reader.readInt();
                         int dinAceptado2 = reader.readInt();
                         server.juego.intercambio(propAceptada1, dinAceptado1, server.conexiones.indexOf(this), propAceptada2, dinAceptado2, personaAceptada);
                         server.writeInThreadOwner(server.conexiones.indexOf(this),"Ha aceptado el intercambio de"+server.nombres.get(personaAceptada));
                         break;
                     case 23:
                         int opcion = reader.readInt();
                         if (opcion == 0){
                            int [] turno3Tmp = server.lanzarDados();
                            String [] iconDados3 = server.buscarDados(turno3Tmp);
                            int [] datosDinero = server.juego.getDatosDinero(server.conexiones.indexOf(this),turno3Tmp[0],turno3Tmp[1]);
                            writer.writeInt(25);
                            writer.writeInt(opcion);
                            writer.writeUTF(iconDados3[0]);
                            writer.writeUTF(iconDados3[1]);
                            writer.writeInt(datosDinero[0]);
                            writer.writeInt(datosDinero[1]);
                            //writer.writeInt(turno3Tmp[0]+turno3Tmp[1]);
                            break;
                         }
                         else{
                             System.out.println("Esta llegando al case 23 de server");
                                int [] datosDinero = server.juego.getDatosDinero(server.conexiones.indexOf(this),0,0);
                                writer.writeInt(25);
                                writer.writeInt(opcion);
                                writer.writeInt(datosDinero[0]);
                                writer.writeInt(datosDinero[1]);
                                //writer.writeInt(turno3Tmp[0]+turno3Tmp[1]);
                                break;                             
                         }
                     case 24:
                         int plataPagar = reader.readInt();
                         server.juego.pagarPorPropiedad(server.conexiones.indexOf(this),plataPagar);
                         break;
                     case 25:
                        int dondeEstoy = reader.readInt();
                        int dondeVoy = reader.readInt();
                        ImageIcon micono = (ImageIcon) objReader.readObject();
                        
                        for (int i = 0; i < server.conexiones.size(); i++) {
                            ThreadServidor current25 = server.conexiones.get(i);
                            current25.writer.writeInt(27);
                            current25.writer.writeInt(dondeEstoy);
                            current25.writer.writeInt(dondeVoy);
                            current25.objWriter.writeObject(micono);
                            
                        }
                       break;
                     case 26:
                        int adonde = reader.readInt();
                        server.juego.turnoJugadorAux(server.conexiones.indexOf(this), adonde);
                        break;
                     case 27:
                         int cantidad = reader.readInt();
                         int sumar = reader.readInt();
                         if (sumar == 0)
                            server.juego.currentPlayers.get(server.conexiones.indexOf(this)).money+=cantidad;
                         else {
                             if (server.juego.currentPlayers.get(server.conexiones.indexOf(this)).money >= cantidad)
                                server.juego.currentPlayers.get(server.conexiones.indexOf(this)).money-=cantidad;
                             else{
                                 writer.writeInt(31);
                                 writer.writeInt(server.juego.currentPlayers.get(server.conexiones.indexOf(this)).money);
                                 writer.writeInt(cantidad);
                             }
                         }
                         break;
                     case 28:
                         int seleccionada = reader.readInt();
                         int jugador = server.conexiones.indexOf(this);
                         boolean hipotecada = server.juego.estaHipotecada(seleccionada,jugador);
                         boolean puedeDeshipotecar = true;
                         if (hipotecada){
                             puedeDeshipotecar = server.juego.puedeDeshipotecar(seleccionada,jugador);
                         }
                         writer.writeInt(30);
                         writer.writeBoolean(server.juego.estaHipotecada(seleccionada,jugador));
                         writer.writeBoolean(puedeDeshipotecar);
                         break;
                     case 29:
                         writer.writeInt(29);
                         objWriter.writeObject(server.juego.currentPlayers.get(server.conexiones.indexOf(this)).getPropertiesNames());
                         break;
                     case 30:
                         int seleccionada1 = reader.readInt();
                         int jugador1 = server.conexiones.indexOf(this);
                         server.juego.hipotecar(seleccionada1,jugador1);
                         break;
                     case 31:
                         writer.writeInt(32);
                         writer.writeInt(server.juego.currentPlayers.get(server.conexiones.indexOf(this)).money);
                         break;
                     case 32:
                          System.out.println("lala");
                          writer.writeInt(33);
                          writer.writeInt(server.juego.houses);
                          writer.writeInt(server.juego.hotel);
                          objWriter.writeObject(server.juego.currentPlayers.get(server.conexiones.indexOf(this)).getPropertiesNames());
                          break;
                     case 33:
                        int propBuild = reader.readInt();
                        int datosCasas[] = server.juego.datosCasas(propBuild, server.conexiones.indexOf(this));
                        writer.writeInt(34);
                        if(datosCasas == null){
                            for (int i = 0; i <3; i++)
                                writer.writeInt(0);
                            break;
                        }
                        writer.writeInt(1);
                        writer.writeInt(datosCasas[0]);
                        writer.writeInt(datosCasas[1]);
                        writer.writeInt(server.juego.currentPlayers.get(server.conexiones.indexOf(this)).money);
                        writer.writeBoolean(server.juego.canBuild(propBuild,server.conexiones.indexOf(this)));
                        break;
                     case 34:
                         int[] datosVender = server.juego.venderCasas(reader.readInt(), server.conexiones.indexOf(this));
                         for (int j = 0; j < server.conexiones.size(); j++) {
                                    ThreadServidor current2 = server.conexiones.get(j);
                                    current2.writer.writeInt(6);
                                    current2.writer.writeUTF(server.nombres.get(server.conexiones.indexOf(this)));
                                    current2.writer.writeUTF("Ha vendido una casa");
                                    current2.writer.writeInt(36);
                                    current2.writer.writeInt(datosVender[0]);
                                    current2.writer.writeInt(datosVender[1]);
                                }
                         break;
                     case 35:
                         int indicePropiedad = server.juego.comprarCasas(reader.readInt(), server.conexiones.indexOf(this));
                         for (int j = 0; j < server.conexiones.size(); j++) {
                                    ThreadServidor current2 = server.conexiones.get(j);
                                    current2.writer.writeInt(6);
                                    current2.writer.writeUTF(server.nombres.get(server.conexiones.indexOf(this)));
                                    current2.writer.writeUTF("Ha comprado una casa"); 
                                    current2.writer.writeInt(35);
                                    current2.writer.writeInt(indicePropiedad);
                         }
                         break;
                     case 36:
                         rendido = true;
                         break;
                     case 37:
                         writer.writeInt(36);
                         writer.writeInt(server.juego.currentPlayers.get(server.conexiones.indexOf(this)).money);
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
