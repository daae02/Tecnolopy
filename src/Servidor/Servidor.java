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
    private int turno = 0;
    private boolean partidaIniciada = false;
    private boolean pause = false;

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
    
    public void stopserver(){
        running = false;
    }
    
    public String getNextTurno(){
        if ( ++turno >= conexiones.size())
            turno = 0;
        
        return conexiones.get(turno).nickname;
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
    
    public void runServer(){
        int contadorDeConexiones = 0;
        try{
            srv = new ServerSocket(35577);
            while (running){
               if (!pause){
                   refPantalla.addMessage("pasa 1");
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
