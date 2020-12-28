
package tecnopoly;

import java.util.ArrayList;

/**
 *
 * @author Diego Álvarez
 */
public class Juego {
    public ArrayList<Player> currentPlayers = new ArrayList<Player>();
    public ArrayList<Property> propiedades = new ArrayList<Property>();
    int houses;
    int hotel;
    
    void startGame(){
    String[] nombres = {"Cultura y Deporta","Esc. Ciencias Sociales","BiciPubliCartago",
                        "Ciencias del Lenguaje", "Escuela de Quimica", "Escuela de Matematicas",
                        "Escuela de Biologia", "Ingenieria Agricola", "Ingenieria Forestal",
                        "Autobuses Lumaca","Administración de Empresas","Ing en Seg. Laboral E Higiene Amb.",
                        "Ingenieria en producción Industrial","Escuela de Fisica","Escuela de Educación Técnica",
                        "Ingenieria en Construcción","BICITEC","Ingenieria Electromecanica",
                        "Ingenieria Electrica", "Ingenieria en Materiales","Escuela de Diseño Industrial",
                        "Ingenieria Mecatronica", "Ingenieria en Computadores", "Trenes Incofer",
                        "Ingenieria en Computación","Escuela de Arquitectura y Urbanismo", "Fotocopiadora",
                        "LAIMI"};
    String[] URL = {"Resources\\Propiedades\\Comunes\\Moradas\\CulturaDeporte.png","Resources\\Propiedades\\Comunes\\Moradas\\CienciasSociales.png",
                    "Resources\\Propiedades\\Transporte\\BiciPubli.png","Resources\\Propiedades\\Comunes\\Celestes\\CienciasLenguaje.png",
                    "Resources\\Propiedades\\Comunes\\Celestes\\Quimica.png","Resources\\Propiedades\\Comunes\\Celestes\\Matematica.png",
                    "Resources\\Propiedades\\Comunes\\Rosas\\Biologia.png","Resources\\Propiedades\\Comunes\\Rosas\\Agricola.png",
                    "Resources\\Propiedades\\Comunes\\Rosas\\Forestal.png","Resources\\Propiedades\\Transporte\\Lumaca.png",
                    "Resources\\Propiedades\\Comunes\\Naranjas\\Administracion.png","Resources\\Propiedades\\Comunes\\Naranjas\\SeguridadHigiene.png",
                    "Resources\\Propiedades\\Comunes\\Naranjas\\Producción.png","Resources\\Propiedades\\Comunes\\Rojas\\Fisica.png",
                    "Resources\\Propiedades\\Comunes\\Rojas\\EducacionTecnica.png","Resources\\Propiedades\\Comunes\\Rojas\\Construccion.png",
                    "Resources\\Propiedades\\Transporte\\BiciTEC.png","Resources\\Propiedades\\Comunes\\Amarillas\\Electromecanica.png",
                    "Resources\\Propiedades\\Comunes\\Amarillas\\Electronica.png","Resources\\Propiedades\\Comunes\\Amarillas\\Materiales.png",
                    "Resources\\Propiedades\\Comunes\\Verdes\\DiseñoIndustrial.png","Resources\\Propiedades\\Comunes\\Verdes\\Mecatronica.png",
                    "Resources\\Propiedades\\Comunes\\Verdes\\Computadores.png","Resources\\Propiedades\\Transporte\\Incofer.png",
                    "Resources\\Propiedades\\Comunes\\Azules\\Computación.png","Resources\\Propiedades\\Comunes\\Azules\\ArquitecturaUrbanismo.png"};
    int[] valores = {60,60,200,100,100,120,140,140,160,200,180,180,200,220,220,240,200,260,260,280,300,300,320,200,350,400,150,150};
    }
}

