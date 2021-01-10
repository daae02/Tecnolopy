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
public class TransportProperty extends Property{

    public TransportProperty(int costo, int index, int hipotecable, String img, String familia, String nombre) {
        super(costo, index, hipotecable, img, familia, nombre);
    }

    @Override
    public int calcularCobro(int dado1, int dado2) {
        int res = 50;
        for (int i = 0 ; i<hermanas.size();i++){
            if (dueno == hermanas.get(i).dueno){
                res +=50;
            }
        }
        return res;
    }
}
