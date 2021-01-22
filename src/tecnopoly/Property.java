package tecnopoly;

import java.util.ArrayList;

/**
 *
 * @author Diego Álvarez
 */
abstract class  Property {
    String nombre;
    int costo;
    int index;
    int hipotecable;
    boolean hipotecada;
    String img;
    String familia;
    Player dueno;
    ArrayList <Property> hermanas = new ArrayList <Property>();
    int getHouseValue;
    public Property() {
    }
    public Property(int costo, int index, int hipotecable, String img, String familia, String nombre) {
        this.costo = costo;
        this.index = index;
        this.hipotecable = hipotecable;
        this.img = img;
        this.familia = familia;
        this.nombre = nombre;
    }
    public boolean wholeMonopoly(){
        for (int i = 0 ; i<hermanas.size();i++){
            if(this.dueno != hermanas.get(i).dueno){
                return false;
            }
        }
        return true;
    }
    abstract public int calcularCobro(int dado1, int dado2);
    abstract public int getHouseValue();


    
}
