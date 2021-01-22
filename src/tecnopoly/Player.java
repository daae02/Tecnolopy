/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnopoly;

import java.util.ArrayList;
import javax.swing.JLabel;
/**
 *
 * @author Alejandra G
 */
public class Player {
    public int money;
    public ArrayList<Property> properties = new ArrayList<Property>();
    public int currentIndex;
    boolean enCarcel;
    int turnosRestantes;
    public int indiceArreglo;
    public int turnoCarcel = 0;
    public Player(int indiceArreglo){
        money = 2000;
        currentIndex = 0;
        this.indiceArreglo = indiceArreglo;
    }
    public ArrayList<String> getPropertiesNames(){
        ArrayList<String> resultado = new ArrayList<String>();
        System.out.println("Entra ciclo");
        for(int i = 0;i<properties.size();i++){
            System.out.println(properties.get(i).nombre);
            resultado.add(properties.get(i).nombre);
        }
        System.out.println("Sale ciclo");
        return resultado;
    } 
    public int getValorTotal(){
        int valor = 0; 
        for (int i = 0; i < properties.size(); i++) {
            Property tmp = properties.get(i);
            valor += tmp.costo;
        }
        valor+= money;
        return valor;
    }

}
