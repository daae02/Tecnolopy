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
public class UtilityProperty extends Property{

    public UtilityProperty(int costo, int index, int hipotecable, String img, String familia, String nombre) {
        super(costo, index, hipotecable, img, familia, nombre);
    }

    @Override
    public int calcularCobro(int dado1, int dado2) {
        int suma = dado1 + dado2;
        if (wholeMonopoly()){
            return suma*10;
        }
        return suma*4;
    }

    @Override
    public int getHouseValue() {
        return 0;
    }
}
