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

    public Property(int costo, int index, int hipotecable, String img, String familia, String nombre) {
        this.costo = costo;
        this.index = index;
        this.hipotecable = hipotecable;
        this.img = img;
        this.familia = familia;
        this.nombre = nombre;
    }
    
}