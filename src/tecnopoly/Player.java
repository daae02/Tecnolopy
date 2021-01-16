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
    ArrayList<Property> properties = new ArrayList<Property>();
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
    

}
