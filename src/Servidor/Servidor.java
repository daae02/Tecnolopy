/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Cliente.Cliente;
import java.util.Collections;
import java.util.Comparator;
import com.sun.webkit.ThemeClient;
import java.io.DataOutput;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import tecnopoly.Juego;

/**
 *
 * @author diemo
 */
public class Servidor{
    
    PantallaServer refPantalla;
    public Juego juego;
    public ArrayList<ThreadServidor> conexiones;
    public ArrayList<Cliente> clientes;
    public ArrayList<String> nombres;
    public ArrayList<String> urlBotones;
    private boolean running = true;
    private ServerSocket srv;
    public int turno = 0;
    private boolean partidaIniciada = false;
    private boolean pause = false;
    public int indexJugadorActual = 1;

    public Servidor(PantallaServer refPantalla) {
        this.refPantalla = refPantalla;
        conexiones = new ArrayList<ThreadServidor>();
        clientes = new ArrayList<Cliente>();
        nombres = new ArrayList();
        urlBotones = new ArrayList();
        this.refPantalla.setSrv(this);
    }

    public void iniciarPartida() {
        this.partidaIniciada = true;
    }
    public void escogerGanador() throws IOException{
        int ganador = 0;
        ThreadServidor idxGanador = conexiones.get(0);
        for (int i = 0; i < conexiones.size(); i++) {
            ThreadServidor tmp = conexiones.get(i);
            if (!tmp.rendido){
                int tmpValor = juego.currentPlayers.get(i).getValorTotal();
                if (tmpValor > ganador){
                    ganador = tmpValor;
                    idxGanador = tmp;
                }
            }     
        }
        idxGanador.writer.writeInt(37);
    }
    
    public void stopserver(){
        running = false;
    }
    
    public String getNextTurno(){
        if ( ++turno >= conexiones.size())
            turno = 0;
        
        return conexiones.get(turno).nickname;
    }
    public void cayoCarcel(int posicionThread){
        
    }
    public void funcionesCarcel (int posicion,int caso) throws IOException{
        switch (caso){
            case 1://cayo carcel primera vez
                conexiones.get(posicion).writer.writeInt(17);
                break;
            case 2://sale de la carcel
                conexiones.get(posicion).writer.writeInt(18);
                break;
            case 3:
                conexiones.get(posicion).writer.writeInt(19);
                break;
        }
    }
    public void writeInThreadAP(int position, String url) throws IOException{
        conexiones.get(position).writer.writeInt(12);
        conexiones.get(position).writer.writeUTF(url);
    }
    
    public void writeInThreadNAP(int position,String url, int posDueño,boolean isUtility) throws IOException{
        String dueño = conexiones.get(posDueño).nickname;
        conexiones.get(position).writer.writeInt(13);
        conexiones.get(position).writer.writeUTF(url);
        conexiones.get(position).writer.writeUTF(dueño);
        conexiones.get(position).writer.writeBoolean(isUtility);
    }
    public void writeInThreadOwner(int position,String mensaje) throws IOException{
        String name = conexiones.get(position).nickname;
        for (int i = 0; i < conexiones.size(); i++) {
            ThreadServidor current = conexiones.get(i);
            current.writer.writeInt(6);
            current.writer.writeUTF(name);
            current.writer.writeUTF(mensaje);
        }
    }
    public String getTurno(){
        return conexiones.get(turno).nickname;
    }
    public String[] buscarDados(int [] dados){
        String [] arreglo = new String[2];
        String path = "";
        for (int i = 0; i < 2; i++) {
            int dado = dados[i];
            switch(dado){
                case 1:
                    path = "/Resources/Dados/uno.png"; // NOI18N
                    break;
                case 2:
                    path = "/Resources/Dados/dos.png"; 
                    break;
                case 3:
                    path = "/Resources/Dados/tres.png"; 
                    break;
                case 4:
                    path = "/Resources/Dados/cuatro.png"; 
                    break;
                case 5:
                    path = "/Resources/Dados/cinco.png"; 
                    break;
                case 6:
                    path = "/Resources/Dados/seis.png"; 
                    break;
 
            }
            arreglo[i] = path;           
        }
        return arreglo;
    }
    public void cartaChance(int carta,int posicionThread) throws IOException{
        conexiones.get(posicionThread).writer.writeInt(26);
        conexiones.get(posicionThread).writer.writeInt(carta);
    }
    public void arcaComunal(int arca,int posicionThread) throws IOException{
        conexiones.get(posicionThread).writer.writeInt(28);
        conexiones.get(posicionThread).writer.writeInt(arca);   
    }
    public int[] lanzarDados(){
        int [] arreglo = new int[2];
        int dado1 = (new Random()).nextInt(6)+1;
        int dado2 = (new Random()).nextInt(6)+1;
        arreglo[0] = dado1;
        arreglo[1] = dado2;
        return arreglo;
    }
    public void ordenarAL(){
        Collections.sort(conexiones, new Comparator<ThreadServidor>(){
	@Override
	public int compare(ThreadServidor c1, ThreadServidor c2) {
		// Aqui esta el truco, ahora comparamos p2 con p1 y no al reves como antes
		return new Integer(c2.turno).compareTo(new Integer(c1.turno));
	}
        });
        
    }
    public String getNickname(int nombreIndice){
        return conexiones.get(nombreIndice).nickname;
    }
    
    public void runServer(){
        int contadorDeConexiones = 0;
        try{
            srv = new ServerSocket(35577);
            while (running){
               if (!pause){
                if (contadorDeConexiones < 6){
                refPantalla.addMessage("::Esperando conexión ...");
                Socket nuevaConexion = srv.accept();
                if (!partidaIniciada){ 
                    contadorDeConexiones++;
                    if (contadorDeConexiones > 1)
                        refPantalla.btnIniciar.setEnabled(true);
                    refPantalla.addMessage(":Conexión " + contadorDeConexiones + "aceptada");
                    
                    // nuevo thread
                    ThreadServidor newThread = new ThreadServidor(nuevaConexion, this);
                    conexiones.add(newThread);
                    newThread.start();
                }
                }
                else{
                    // OutputStream socket para poder hacer un writer
                    refPantalla.addMessage(":Conexión denegada, ha alcanzado el máximo de jugadores");
                    pause = true;
                   
                }
               }
                
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
  
}
