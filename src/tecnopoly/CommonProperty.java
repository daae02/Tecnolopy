/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnopoly;

/**
 *
 * @author Alejandra G
 */
public class CommonProperty extends Property{
    int casas;
    int casa1;
    int casa2;
    int casa3;
    int casa4;
    int hotel;
    public CommonProperty(int casa1, int casa2, int casa3, int casa4, int hotel, int costo, int index, int hipotecable, String img, String familia,String nombre) {
        super(costo, index, hipotecable, img, familia,nombre);
        this.casa1 = casa1;
        this.casa2 = casa2;
        this.casa3 = casa3;
        this.casa4 = casa4;
        this.hotel = hotel;
    }
}
