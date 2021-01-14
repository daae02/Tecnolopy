/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author diemo
 */
public class ThreadCliente extends Thread {
    
    private Socket socketRef;
    public DataInputStream reader;
    public DataOutputStream writer;
    public ObjectOutputStream objWriter;
    private ObjectInputStream objReader;
    private String nombre;
    private Cliente cliente;
    private MonopolyJF secPantalla;
    private boolean running = true;
    private ChatClient refPantalla;
    public int turnoPrimero = 0;

    public ThreadCliente(Cliente cliente,Socket socketRef, ChatClient refPantalla,MonopolyJF secPantalla) throws IOException {
        this.cliente = cliente;
        this.socketRef = socketRef;
        reader = new DataInputStream(socketRef.getInputStream());
        writer = new DataOutputStream(socketRef.getOutputStream());
        objWriter = new ObjectOutputStream(socketRef.getOutputStream());
        objReader = new ObjectInputStream(socketRef.getInputStream());
        this.secPantalla = secPantalla;
        this.refPantalla = refPantalla;
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
                        String icon1 = reader.readUTF();
                        String icon2 = reader.readUTF();
                        
                        turnoPrimero = dado1 + dado2;
                        refPantalla.pintarLanzamientoDados(dado1, dado2,icon1,icon2);
                    break;                    
                    case 2:
                        int indice = reader.readInt();
                        refPantalla.arregloBotones.get(indice).setEnabled(false);
                        break;
                    case 3:
                        writer.writeUTF(refPantalla.nickname.toString());
                        writer.writeUTF(refPantalla.pieza.toString());
                        break;
                    case 4:
                        ArrayList<String> urlBotones = (ArrayList<String>)objReader.readObject();
                        ArrayList<String> nombres = (ArrayList<String>)objReader.readObject();
                        int miIndice = reader.readInt();
                        secPantalla.myIndex = miIndice;
                        secPantalla.pieza = refPantalla.pieza.toString();
                        secPantalla.botones = urlBotones;
                        secPantalla.nombres = nombres;
                        secPantalla.setTitulo();
                        System.out.println("llega");
                        break;
                    case 5:
                        refPantalla.setVisible(false);
                        secPantalla.colocarBtnsInicio();
                        secPantalla.actualizarCBB();
                        secPantalla.setVisible(true);
                        break;
                    case 6:
                        String usuarioTmp = reader.readUTF();
                        String mensaje = reader.readUTF();
                        System.out.println(usuarioTmp+">   " + mensaje);
                        //System.out.println("CLIENTE Recibido mensaje: " + mensaje);
                        secPantalla.addMessage(usuarioTmp+">   " + mensaje);
                        
                        break;
                    case 7:
                        String propiedades = reader.readUTF();
                        String dinero = reader.readUTF();
                        secPantalla.actualizarCBBplayer(propiedades, dinero);
                        break;
                    case 8:
                        String icon1sec = reader.readUTF();
                        String icon2sec = reader.readUTF();
                        String iguales  = reader.readUTF(); 
                        int turnoLegal = reader.readInt();
                        secPantalla.pintarLanzamientoDados(icon1sec,icon2sec,turnoLegal,iguales);
                        break;
                    case 9:
                        secPantalla.habilitarBtns();
                        break;
                    case 10:
                        secPantalla.deshabilitarBtns();
                        break;
                    case 11:
                        int labelActual = reader.readInt();
                        int casillaMover = reader.readInt();
                        //int indexThread = reader.readInt();
                        ImageIcon icono = (ImageIcon) objReader.readObject();
                        secPantalla.findToken(labelActual,icono,casillaMover);
                        break;
                    case 12:
                        String url = reader.readUTF();
                        System.out.println("llega al thread cliente");
                        secPantalla.mostrarPropDisponible(url);
                        break;
                    case 13:
                        String url1 = reader.readUTF();
                        String dueño = reader.readUTF();
                        secPantalla.mostrarPropNoDisponible(url1,dueño);
                        break;
                    case 14:
                        String url2 = reader.readUTF();
                        int maximo = reader.readInt();
                        int minimo = reader.readInt();
                        secPantalla.pantallaSubastas.startSpinner(minimo, maximo);
                        secPantalla.pantallaSubastas.startList((ArrayList<String>)objReader.readObject());
                        secPantalla.mostrarSubasta(url2);
                        break;
                    case 15:
                        System.out.println("Llego a retirar");
                        secPantalla.pantallaSubastas.updateList(reader.readUTF());
                        break;
                    case 16:
                        int nuevoMonto = reader.readInt();
                        String persona = reader.readUTF();
                        secPantalla.pantallaSubastas.pujaRecibida(persona,nuevoMonto);
                        break;
                }
            } catch (IOException ex) {
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
