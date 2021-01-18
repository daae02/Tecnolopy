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
    ObjectInputStream objReader;
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
                        secPantalla.pantallaSubastas.indiceProp = reader.readInt();
                        secPantalla.pantallaSubastas.setTitle(nombre);
                        secPantalla.pantallaSubastas.playing = true;
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
                    case 17:
                        secPantalla.cayoCarcel();
                        break;
                    case 18:
                        secPantalla.salioCarcel();
                        break;
                    case 19:
                        secPantalla.noDinero();
                        break;
                    case 20:
                        int anfitrion = reader.readInt();
                        secPantalla.showDialog(anfitrion);
                        break;
                    case 21:
                        System.out.println("Empiza solicitando informacion");
                        int resultado = reader.readInt();
                        int personaIntercambio = reader.readInt();
                        if (resultado==0){
                            System.out.println("Envia la 19");
                            writer.writeInt(19);
                            writer.writeInt(personaIntercambio);
                            System.out.println("Envio info");
                            break;
                        }
                        else secPantalla.showDialogRejected(personaIntercambio);
                        break;
                    case 22:
                        System.out.println("Intenta obtener data");
                        int otraPersona = reader.readInt();
                        ArrayList<String> misProps = (ArrayList<String>) objReader.readObject();
                        System.out.println("Lee primero");
                        ArrayList<String> susProps = (ArrayList<String>) objReader.readObject();
                        System.out.println("Lee segundo");
                        int miDinero = reader.readInt();
                        System.out.println("Lee tercero");
                        int suDinero = reader.readInt();
                        System.out.println("Lee ultimo");
                        secPantalla.cargarVentanaIntercambio(misProps, susProps, miDinero, suDinero, secPantalla.nombres.get(otraPersona),otraPersona); 
                        System.out.println("lets go");
                        break;
                    case 23:
                        System.out.println("Intenta obtener data");
                        int otraPersona2 = reader.readInt();
                        ArrayList<String> susProps2 = (ArrayList<String>) objReader.readObject();
                        System.out.println("Lee primero");
                        ArrayList<String> misProps2 = (ArrayList<String>) objReader.readObject();
                        System.out.println("Lee segundo");
                        int suDinero2 = reader.readInt();
                        System.out.println("Lee tercero");
                        int miDinero2 = reader.readInt();
                        System.out.println("Lee ultimo");
                        secPantalla.cargarVentanaIntercambio(misProps2, susProps2, miDinero2, suDinero2, secPantalla.nombres.get(otraPersona2),otraPersona2); 
                         ArrayList<String> meOfrece = (ArrayList<String>) objReader.readObject();
                         ArrayList<String> mePide = (ArrayList<String>) objReader.readObject();
                         int dinOfrece = reader.readInt();
                         int dinPide = reader.readInt();
                         secPantalla.intercambios.mostrarSolicitud(meOfrece,mePide,dinOfrece,dinPide);
                         break;
                    case 24:
                        System.out.println("jeje se mamaron alv");
                        int rechazador = reader.readInt();
                        secPantalla.showDialogRejected(rechazador);
                        break;
                    case 25:
                        String icon1ter = reader.readUTF();
                        String icon2ter = reader.readUTF();
                        int plataActual = reader.readInt();
                        int plataPagar = reader.readInt();
                        secPantalla.pantalla.pintarLanzamientoDados(icon1ter, icon2ter,plataActual,plataPagar);
                        break;
                    case 26:
                        int cartaChance = reader.readInt();
                        secPantalla.cartaChance(cartaChance);
                        break;
                    case 27:
                        int dondeEstoy = reader.readInt();
                        int dondeVoy = reader.readInt();
                        //int indexThread = reader.readInt();
                        ImageIcon micono = (ImageIcon) objReader.readObject();
                        secPantalla.findTokenAux(dondeEstoy,micono,dondeVoy);
                        break;
                    case 28:
                        int arca = reader.readInt();
                        secPantalla.cartaArca(arca);
                        break;   
                    case 29:
                          ArrayList <String> elementos = (ArrayList <String>) objReader.readObject();
                          secPantalla.refHipotecas.updateComboBox(elementos);
                          break;
                            
                }
            } catch (IOException ex) {
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
