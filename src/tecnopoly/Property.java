package tecnopoly;

import java.util.ArrayList;

/**
 *
 * @author Diego Álvarez
 */
abstract class  Property {
    int costo;
    int index;
    int hipotecable;
    boolean hipotecada;
    String img;
    Player dueno;
    ArrayList <Property> hermanas = new ArrayList <Property>();
}