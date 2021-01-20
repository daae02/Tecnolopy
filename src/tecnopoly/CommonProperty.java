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
    int cobro;
    int casas;
    int casa1;
    int casa2;
    int casa3;
    int casa4;
    int hotel;
    public CommonProperty(int casa1, int casa2, int casa3, int casa4, int hotel, int costo,int cobro, int index, int hipotecable, String img, String familia,String nombre) {
        super(costo, index, hipotecable, img, familia,nombre);
        this.cobro = cobro;
        this.casa1 = casa1;
        this.casa2 = casa2;
        this.casa3 = casa3;
        this.casa4 = casa4;
        this.hotel = hotel;
    }
    public int obtenerValor(){
       switch (casas){
           case 1:
                return casa1;
           case 2:
                return casa2;
           case 3:
                return casa3;
           case 4:
                return casa4;
           case 5:
               return hotel;
           default:
               return 0;
       } 
    }
    @Override
    public int calcularCobro(int dado1, int dado2) {
        if (hipotecada){
            return 0;
        }
        else if (casas != 0){
                return obtenerValor();
            }
        else if (wholeMonopoly()){
                return cobro*2;
            }        
        else{
            return cobro;
        }
    }
}
