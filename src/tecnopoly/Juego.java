
package tecnopoly;

import Servidor.Servidor;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Diego Álvarez
 */
public class Juego {
    public int chanceActual = 0;
    public int arcaActual = 0;
    public ArrayList<Player> currentPlayers = new ArrayList<Player>();
    public ArrayList<Property> propiedades = new ArrayList<Property>();
    //public ArrayList<Chance> cartasChance = new ArrayList<Property>(); 
    public int cantConexiones;
    int houses;
    int hotel;
    public Servidor refServer;
    public Juego(){}
    public Juego(int cantConexiones){
        this.cantConexiones = cantConexiones;
        startGame();
    }
    void propietyLinker(){
        for (int i = 0; i < propiedades.size(); i++){
            for (int j = 0; j < propiedades.size(); j++){
                if(!(propiedades.get(i).nombre.equals(propiedades.get(j).nombre)) && 
                  (propiedades.get(i).familia.equals(propiedades.get(j).familia))){
                    propiedades.get(i).hermanas.add(propiedades.get(j));
                }    
            }   
        }   
    }
    void propietyFactory(int index,int tipo, String nombre,String URL, int valor, int cobro, int c1, int c2,int c3, int c4,int hotel, int hipoteca, String familia){
        switch (tipo){
            case 0:
                propiedades.add(new CommonProperty(c1,c2,c3,c4,hotel,valor,cobro,index,hipoteca,URL,familia,nombre));
                break;
            case 1:
                propiedades.add(new TransportProperty(valor, index, hipoteca, URL, familia,nombre));
                break;
            case 2:
                propiedades.add(new UtilityProperty(valor, index, hipoteca, URL, familia,nombre));
                break;                
        }
    }
    void propietyCreator(){
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
        String[] URL = {"/Resources/Propiedades/Comunes/Moradas/CulturaDeporte.png","/Resources/Propiedades/Comunes/Moradas/CienciasSociales.png",
                        "/Resources/Propiedades/Transporte/BiciPubli.png","/Resources/Propiedades/Comunes/Celestes/CienciasLenguaje.png",
                        "/Resources/Propiedades/Comunes/Celestes/Quimica.png","/Resources/Propiedades/Comunes/Celestes/Matematica.png",
                        "/Resources/Propiedades/Comunes/Rosas/Biologia.png","/Resources/Propiedades/Comunes/Rosas/Agricola.png",
                        "/Resources/Propiedades/Comunes/Rosas/Forestal.png","/Resources/Propiedades/Transporte/Lumaca.png",
                        "/Resources/Propiedades/Comunes/Naranjas/Administracion.png","/Resources/Propiedades/Comunes/Naranjas/SeguridadHigiene.png",
                        "/Resources/Propiedades/Comunes/Naranjas/Producción.png","/Resources/Propiedades/Comunes/Rojas/Fisica.png",
                        "/Resources/Propiedades/Comunes/Rojas/EducacionTecnica.png","/Resources/Propiedades/Comunes/Rojas/Construccion.png",
                        "/Resources/Propiedades/Transporte/BiciTEC.png","/Resources/Propiedades/Comunes/Amarillas/Electromecanica.png",
                        "/Resources/Propiedades/Comunes/Amarillas/Electronica.png","/Resources/Propiedades/Comunes/Amarillas/Materiales.png",
                        "/Resources/Propiedades/Comunes/Verdes/DiseñoIndustrial.png","/Resources/Propiedades/Comunes/Verdes/Mecatronica.png",
                        "/Resources/Propiedades/Comunes/Verdes/Computadores.png","/Resources/Propiedades/Transporte/Incofer.png",
                        "/Resources/Propiedades/Comunes/Azules/Computación.png","/Resources/Propiedades/Comunes/Azules/ArquitecturaUrbanismo.png",
                        "/Resources/Propiedades/Servicios/Fotocopiadora.png","/Resources/Propiedades/Servicios/LAIMI.png"};
        String[] familia = {"mor","mor","trans","cel","cel","cel","ros","ros","ros","trans","nar","nar","nar","roj","roj","roj",
                            "trans","amar","amar","amar","ver","ver","ver","trans","azu","azu","util","util"};
        int[] valores = {60,60,200,100,100,120,140,140,160,200,180,180,200,220,220,240,200,260,260,280,300,300,320,200,350,400,150,150};
        int[] cobro = {2,4,50,6,6,8,10,10,12,50,14,14,16,18,18,20,50,22,22,24,26,26,28,50,35,50,0,0};
        int[] casa1 = {10,20,0,30,30,40,50,50,60,0,70,70,80,90,90,100,0,110,110,120,130,130,150,0,175,175,0,0};
        int[] casa2 = {30,60,0,90,90,100,150,150,180,0,200,200,220,250,250,300,0,330,330,360,390,390,390,0,500,500,0,0};
        int[] casa3 = {90,180,0,270,270,300,450,450,500,0,550,550,600,700,700,750,0,800,800,850,900,900,900,0,1100,1100,0,0};
        int[] casa4 = {160,320,0,400,400,450,625,625,700,0,750,750,800,875,875,925,0,975,975,1025,1100,1100,1200,0,1300,1700,0,0};
        int[] hotel = {250,450,0,550,550,600,750,750,900,0,950,950,1000,1050,1050,1100,0,1150,1150,1200,1275,1275,1400,0,1500,2000,0,0};
        int[] hipoteca = {30,30,100,50,50,60,70,70,80,100,90,90,100,110,110,120,100,130,130,140,150,150,160,100,175,200,75,75};
        int[] tipos = {0,0 ,1,0,0,0 ,0,0,0 ,1,0,0,0 , 0,0,0, 1,0,0,0 , 0,0,0 ,1,0,0, 2,2};
        int[] index = {1,3,5,6,8,9,11,13,14,15,16,18,19,21,23,24,25,26,27,29,31,32,34,35,37,39,12,28};
        for(int i = 0; i < nombres.length;i++){
            
            propietyFactory(index[i],tipos[i],nombres[i],URL[i],valores[i],cobro[i],casa1[i],casa2[i],casa3[i],casa4[i],hotel[i],hipoteca[i],familia[i]);
        }
    }
    public void crearPlayers(){
        for (int i = 0; i < cantConexiones; i++) {
            currentPlayers.add(new Player(i));           
        }
    }
    public void startGame(){
        crearPlayers();
        propietyCreator();
        propietyLinker();
        /* for (int i = 0; i < propiedades.size(); i++){
             System.out.println("Nombre: "+propiedades.get(i).nombre);
             System.out.println("Costo: "+propiedades.get(i).costo);
             System.out.println("Posición: "+propiedades.get(i).index);
             for (int j = 0; j < propiedades.get(i).hermanas.size(); j++){
                 System.out.println("Nombre hermana #"+j+" :"+propiedades.get(i).hermanas.get(j).nombre);
             }
         }*/
    }
    public void comprarPorSubasta(int indice, int indicePropiedad,int pago) throws IOException{
         for (int i = 0; i<propiedades.size(); i++){
             System.out.println("Sí entra");
            if (propiedades.get(i).index == indicePropiedad){
                currentPlayers.get(indice).properties.add(propiedades.get(i));
                propiedades.get(i).dueno =  currentPlayers.get(indice);
                currentPlayers.get(indice).money -= pago;
                System.out.println("Compro la propiedad "+propiedades.get(i).nombre);
                refServer.writeInThreadOwner(indice,"Compro la propiedad "+propiedades.get(i).nombre);
                break;
            }
        }
    }
    public void comprarPropiedad(int indice) throws IOException{
        for (int i = 0; i<propiedades.size(); i++){
            
            if (propiedades.get(i).index == currentPlayers.get(indice).currentIndex){
                currentPlayers.get(indice).properties.add(propiedades.get(i));
                propiedades.get(i).dueno =  currentPlayers.get(indice);
                currentPlayers.get(indice).money -= propiedades.get(i).costo;
                System.out.println("Compro la propiedad "+propiedades.get(i).nombre);
                refServer.writeInThreadOwner(indice,"Compro la propiedad "+propiedades.get(i).nombre);
                break;
            }
        }
    }
    private ArrayList<Property> findProperties(ArrayList <String> nombres){
        ArrayList<Property> resultado = new ArrayList<Property>();
        for (int i=0; i<nombres.size();i++){
            for (int j=0;j<propiedades.size();j++){
                if (propiedades.get(j).nombre.equals(nombres.get(i))){
                    System.out.println("\t"+propiedades.get(j).nombre+"\n");
                    resultado.add(propiedades.get(j));
                }
            }
        }
        return resultado;
    }
    private void changeOwner(ArrayList<Property> propiedades, int antiguo, int nuevo){
        for(int i=0; i<propiedades.size();i++){
            currentPlayers.get(antiguo).properties.remove(propiedades.get(i));
            propiedades.get(i).dueno = currentPlayers.get(nuevo);
            currentPlayers.get(nuevo).properties.add(propiedades.get(i));
        }
    }
    public boolean estaHipotecada(int propiedad, int indice){
        System.out.println("Indice: "+indice+" propiedad: "+propiedad);
        return currentPlayers.get(indice).properties.get(propiedad).hipotecada;
    }
    public boolean puedeDeshipotecar(int propiedad, int indice){
        Player current = currentPlayers.get(indice);
        return current.money >= current.properties.get(propiedad).hipotecable;
    }
    public void intercambio (ArrayList <String> nombres1, int dinero1, int indice1,ArrayList <String> nombres2, int dinero2, int indice2){
        System.out.println("Propiedades de: "+indice1+"\n");
       ArrayList<Property> propiedades1 = findProperties(nombres1);
       System.out.println("Propiedades de: "+indice2+"\n");
       ArrayList<Property> propiedades2 = findProperties(nombres2);
       changeOwner(propiedades1,indice1,indice2);
       changeOwner(propiedades2,indice2,indice1);
        currentPlayers.get(indice1).money += dinero2;
        currentPlayers.get(indice1).money -= dinero1;
        currentPlayers.get(indice2).money += dinero1;
        currentPlayers.get(indice2).money -= dinero2;
    }
    public void hipotecar (int indice,int indexCliente){
        Player current = currentPlayers.get(indexCliente);
        Property propiedad = current.properties.get(indice);
        if (propiedad.hipotecada){
            System.out.println("La deshipoteco");
            currentPlayers.get(indice).money -= propiedad.hipotecable;
        }
        else{
            System.out.println("La hipoteco");
            currentPlayers.get(indice).money += propiedad.hipotecable;
        }
        propiedad.hipotecada = !propiedad.hipotecada;
      /*      for (int i=0;i<propiedades.size();i++){
                if (propiedades.get(i).nombre.equals(propiedad)){
                    if (propiedades.get(i).hipotecada == true){
                        
                        currentPlayers.get(indice).money -= propiedades.get(i).hipotecable;
                    }
                    else{
                        currentPlayers.get(indice).money += propiedades.get(i).hipotecable;
                    }
                    propiedades.get(i).hipotecada = !propiedades.get(i).hipotecada;
                }
            }*/
    }
    public int getPropertyValue(int indice){
        System.out.println("Posicion del jugador: "+ currentPlayers.get(indice).currentIndex);
        for (int i = 0; i<propiedades.size();i++){
            System.out.println("Indice de la propiedad: "+propiedades.get(i).index);
            
            if (currentPlayers.get(indice).currentIndex == propiedades.get(i).index){
                System.out.println("Propiedad: "+propiedades.get(i).nombre);
                return propiedades.get(i).costo;
            }
        }
        return 0;
    }
    public String getProperties(int indice){
        String res = "";
        for (int i = 0; i<currentPlayers.get(indice).properties.size();i++){
            res += currentPlayers.get(indice).properties.get(i).nombre + "\n";
        }
        return res;
    }
    private Property getProperty(int posicionJugador){
        for (int i = 0; i < propiedades.size(); i++) {
            if(propiedades.get(i).index == posicionJugador)
                return propiedades.get(i);   
        }
        return null;
    }
    public void salirCarcelBtn(int posicionThread,int gratis) throws IOException{
        if (gratis == 1){
            currentPlayers.get(posicionThread).enCarcel = false;
            currentPlayers.get(posicionThread).turnoCarcel = 0;
            refServer.funcionesCarcel(posicionThread,2);
        }
        else{
        if (currentPlayers.get(posicionThread).money >= 50){
            currentPlayers.get(posicionThread).money -= 50;
            currentPlayers.get(posicionThread).enCarcel = false;
            currentPlayers.get(posicionThread).turnoCarcel = 0;
            refServer.funcionesCarcel(posicionThread,2);
        }
        else refServer.funcionesCarcel(posicionThread,3);
        }
        
    }
    public int [] getDatosDinero(int posicionThread,int dado1,int dado2){
        int [] datos = new int[2];
        Property propiedadTmp = getProperty(currentPlayers.get(posicionThread).currentIndex);
        datos [0] = currentPlayers.get(posicionThread).money;
        datos[1] = propiedadTmp.calcularCobro(dado1, dado2);
        return datos;
    }
    public void pagarPorPropiedad(int posicionThread,int plataPagar) throws IOException{
        currentPlayers.get(posicionThread).money -= plataPagar;
    }
    public void turnoJugadorAux(int posicionThread,int adonde) throws IOException{
        Player current = currentPlayers.get(posicionThread);
        current.currentIndex = adonde;
        if (current.currentIndex == 30){
            current.enCarcel = true;
            current.currentIndex = 10;
            refServer.funcionesCarcel(posicionThread,1);
            
        }
        else {
            Property propiedadTmp = getProperty(current.currentIndex);
            if (propiedadTmp != null){
            if(propiedadTmp.dueno == null)
                refServer.writeInThreadAP(posicionThread,propiedadTmp.img);
           else {
                if (propiedadTmp.dueno.indiceArreglo == posicionThread)
                    refServer.writeInThreadOwner(posicionThread,"El jugador ya es dueño de esta propiedad");
                else refServer.writeInThreadNAP(posicionThread,propiedadTmp.img,propiedadTmp.dueno.indiceArreglo);
            }
        }
        }

    }
    public void turnoJugador(int posicionThread,int casillasMover, boolean iguales) throws IOException{
        Player current = currentPlayers.get(posicionThread);
        current.currentIndex += casillasMover;
        if (current.currentIndex > 39){
            current.currentIndex -= 40;
            current.money += 200;
        }
        if (current.currentIndex == 30){
            current.enCarcel = true;
            current.currentIndex = 10;
            refServer.funcionesCarcel(posicionThread,1);
            
        }
        else if (current.currentIndex == 7 || current.currentIndex == 22 || current.currentIndex == 36){
            refServer.cartaChance(chanceActual,posicionThread);
            chanceActual++;
            if (chanceActual>10)
                chanceActual = 0;
        }
        else if (current.currentIndex == 2 || current.currentIndex == 17 || current.currentIndex == 33){
            refServer.arcaComunal(arcaActual,posicionThread);
            arcaActual++;
            if (arcaActual>7)
                arcaActual = 0;
        }
        else{
            if (!current.enCarcel){
                Property propiedadTmp = getProperty(current.currentIndex);
                if (propiedadTmp != null)
                    if(propiedadTmp.dueno == null)
                        refServer.writeInThreadAP(posicionThread,propiedadTmp.img);
                   else {
                        if (propiedadTmp.dueno.indiceArreglo == posicionThread)
                            refServer.writeInThreadOwner(posicionThread,"El jugador ya es dueño de esta propiedad");
                        else refServer.writeInThreadNAP(posicionThread,propiedadTmp.img,propiedadTmp.dueno.indiceArreglo);
                    }
                }
            else {
                if (current.turnoCarcel < 3 && !iguales){
                    current.turnoCarcel++;
                    
                }
                else {
                    current.enCarcel = false;
                    current.turnoCarcel = 0;
                    refServer.funcionesCarcel(posicionThread,2);
                }
            }
        }
            
    }
}



