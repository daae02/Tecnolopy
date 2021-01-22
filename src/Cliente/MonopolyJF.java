/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;


import java.awt.Component;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;
import static java.lang.Boolean.parseBoolean;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Alejandra G
 */
public class MonopolyJF extends javax.swing.JFrame {

    /**
     * Creates new form MonopolyJF
     */
    public boolean actualizado = false;
    public CartaChance cartaJF;
    public int myIndex = 0;
    public int labelActual = 0;
    public ArrayList<String> botones;
    public ArrayList<String> nombres;
    public ArrayList<javax.swing.JPanel> casillas;
    public ArrayList<javax.swing.JPanel> casillasCasas;
    public ArrayList<ImageIcon> iconos;
    public ImageIcon myIcon; 
    ArrayList<JLabel> lblAL;
    Cliente refCliente;
    private String nombreTurno = "";
    public String nickname = "";
    public String pieza = "hello";
    public int turnoActual = 0; 
    PropiedadesJF pantalla;
    elegirIntercambio elegirPantalla;
    ventanaIntercambio intercambios;
    comprarCasas compraVendeCasas;
    Hipotecas refHipotecas;
    public Subasta pantallaSubastas;
    public boolean enCarcel = false;
    public boolean dadosIguales = false;
    public boolean rendido = false;
    public BrokeForm deudaCarta;
    public boolean tieneSalirGratis = false;
    
    public MonopolyJF() {
        initComponents();
        cartaJF = new CartaChance();
        cartaJF.refPantalla = this;
        btnSalirCarcel.setVisible(false);
        btnCarcelGratis.setVisible(false);
        pantalla = new PropiedadesJF();
        pantalla.pantallaPrincipal = this;
        pantallaSubastas = new Subasta(this);
        System.out.println("Prueba");
        refHipotecas = new Hipotecas();
        refHipotecas.refPantalla = this;
        elegirPantalla = new elegirIntercambio();
        elegirPantalla.refPantalla = this;
        intercambios = new ventanaIntercambio();
        intercambios.refPantalla = this;
        compraVendeCasas = new comprarCasas();
        compraVendeCasas.refPantalla = this;
        lblAL = new ArrayList();
        iconos = new ArrayList();
        deudaCarta = new BrokeForm();
        deudaCarta.afuera = true;
        deudaCarta.refPantalla = pantalla;
        JLabel lblToken1 = new javax.swing.JLabel();
        JLabel lblToken2 = new javax.swing.JLabel();
        JLabel lblToken3 = new javax.swing.JLabel();
        JLabel lblToken4 = new javax.swing.JLabel();
        JLabel lblToken5 = new javax.swing.JLabel();
        JLabel lblToken6 = new javax.swing.JLabel();
        lblAL.add(lblToken1);
        lblAL.add(lblToken2);
        lblAL.add(lblToken3);
        lblAL.add(lblToken4);
        lblAL.add(lblToken5);
        lblAL.add(lblToken6);
        agregarCasillas();
        agregarCasillasCasas();
        

    }
    public void showDialog(int anfitrion) throws IOException{
        String mensaje = nombres.get(anfitrion)+" le ha invitado aun intercambio";
        String[] options = new String[2];
        options[0] = "Aceptar";
        options[1] = "Rechazar";
        int resultado= JOptionPane.showOptionDialog(this.getContentPane(),mensaje,"Invitación intercambio", 0,JOptionPane.INFORMATION_MESSAGE,null,options,null);
        refCliente.hiloCliente.writer.writeInt(18);
        refCliente.hiloCliente.writer.writeInt(anfitrion);
        refCliente.hiloCliente.writer.writeInt(resultado);
        
    }
    public void showDialogRejected(int rechazador) throws IOException{
        String mensaje = nombres.get(rechazador)+" ha rechazado la ofera de intercambio";
        JOptionPane.showMessageDialog(this.getContentPane(), mensaje, "Intercambio Rechazado",JOptionPane.ERROR_MESSAGE);  
    }
    public void findTokenAux(int dondeEstoy,ImageIcon micono,int dondeVoy){
        JPanel casilla = casillas.get(dondeEstoy);
        Component [] componentes = casilla.getComponents();
        //System.out.println("myicnonito"+iconi);
        for (int i = 0; i < componentes.length ; i++) {
            JLabel iconito = (JLabel)componentes[i];
            if (iconito.getIcon().toString().equals(micono.toString())) {
                casilla.remove(componentes[i]);  
                //System.out.println("Sí lo encontró");
                casilla.revalidate();
                casilla.repaint();
                repaintToken(iconito,dondeVoy);
            }
            //System.out.println(iconito.getIcon());
        }
    }
    public void felicitarGanador(){
        JOptionPane.showMessageDialog(this, "Felicidades"+nickname+ "usted ha sido el ganador :)");
    }
    public void comproCasa(int buscar){
        JPanel panelTmp = casillasCasas.get(buscar);
        Component [] tmpComponentes = panelTmp.getComponents();
        if (tmpComponentes.length == 4){
            panelTmp.removeAll();
            JLabel lblHotel = new javax.swing.JLabel();
            lblHotel.setSize(15,15);
            lblHotel.setOpaque(false);
            ImageIcon icon = new ImageIcon(getClass().getResource("/Resources/Casas/hotel.png"));
            int width =15;
            int height = 15;
            icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
            lblHotel.setIcon(icon);
            panelTmp.add(lblHotel);
            panelTmp.revalidate();
            panelTmp.repaint();     
        }
        else{
            JLabel lblHotel = new javax.swing.JLabel();
            lblHotel.setSize(20,20);
            lblHotel.setOpaque(false);
            ImageIcon icon = new ImageIcon(getClass().getResource("/Resources/Casas/casa.png"));
            int width =15;
            int height = 15;
            icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
            lblHotel.setIcon(icon);
            panelTmp.add(lblHotel);
            panelTmp.revalidate();
            panelTmp.repaint(); 
        }
    }
    public void vendioCasa(int buscar,int eraHotel){
        JPanel panelTmp = casillasCasas.get(buscar);
        Component [] tmpComponentes = panelTmp.getComponents();
        if (eraHotel == 0){
            panelTmp.removeAll();
            for (int i = 0; i < 4; i++) {
                JLabel lblHotel = new javax.swing.JLabel();
                lblHotel.setSize(15,15);
                lblHotel.setOpaque(false);
                ImageIcon icon = new ImageIcon(getClass().getResource("/Resources/Casas/casa.png"));
                int width =15;
                int height = 15;
                icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
                lblHotel.setIcon(icon);
                panelTmp.add(lblHotel);
                panelTmp.revalidate();
                panelTmp.repaint();  
            }
        }
        else{
            panelTmp.remove(panelTmp.getComponentCount()-1);
            panelTmp.revalidate();
            panelTmp.repaint(); 
        }
    }
    public void hesBroke(int plata,int deuda){
        deudaCarta.deudaCarta(deuda, plata);
        deudaCarta.btnPagarCarta.setVisible(true);
        deudaCarta.setVisible(true);
    }
    public void moverCartaX(int adonde) throws IOException{
              refCliente.hiloCliente.writer.writeInt(25);
              refCliente.hiloCliente.writer.writeInt(labelActual);
              refCliente.hiloCliente.writer.writeInt(adonde);
              refCliente.hiloCliente.objWriter.writeObject(myIcon);
              labelActual = adonde;   
              if (labelActual == 4 ){
                  cambiarDinero(200,1);
                    }
              else if (labelActual == 38 ){
                  cambiarDinero(75,1);
              } 
              refCliente.hiloCliente.writer.writeInt(26);
              refCliente.hiloCliente.writer.writeInt(adonde);
    }
    public void cambiarDinero(int suma,int sumar) throws IOException{
              refCliente.hiloCliente.writer.writeInt(27);
              refCliente.hiloCliente.writer.writeInt(suma);
              refCliente.hiloCliente.writer.writeInt(sumar);
    }
    public void cartaChanceAux(int carta) throws IOException{
        cartaJF.cartaB = false;
        cartaJF.setVisible(false);
        switch(carta){
            case 0:
                moverCartaX(39);
                break;
            case 1:
                moverCartaX(11);                
                break;
            case 2:
                moverCartaX(24);
                break;                
            case 3:
                moverCartaX(0);
                cambiarDinero(200,0);
                break;        
            case 4:
                moverCartaX(5);
                break;      
            case 5:
                JOptionPane.showMessageDialog(this, "Ha caido en la carcel");
                moverCartaX(30);
                break;                
            case 6:
                cambiarDinero(150,0);
                break; 
            case 7:
                cambiarDinero(15,1);
                break;
            case 8:
                cambiarDinero(50,0);
                break;             
            case 9:
                moverCartaX(labelActual-3);
                break; 
            case 10:
                btnCarcelGratis.setVisible(true);
                break;
            default:
                moverCartaX(labelActual-3);
                break;

        }
        
    }
    public void cartaArcaAux(int carta) throws IOException{
        System.out.println("Cayo el switch con el numero "+carta);
        cartaJF.setVisible(false);
        switch(carta){
            case 0:
                JOptionPane.showMessageDialog(this, "Ha caido en la carcel");
                moverCartaX(30);
                break;
            case 1:
               cambiarDinero(50,1);
               break;
            case 2:
                cambiarDinero(20,0);
                break;                
            case 3:
                cambiarDinero(100,0);
                break;        
            case 4:
                cambiarDinero(100,1);
                break;      
            case 5:
                cambiarDinero(100,0);
                break;                
            case 6:
                cambiarDinero(100,1);
                break; 
            case 7:
                btnCarcelGratis.setVisible(true);
                tieneSalirGratis = true;
                break;
            default:
                cambiarDinero(100,0);
                break;

        }
        
    }
    public void cartaChance(int carta){
        System.out.println("cual llegoooo carta "+carta);
        String url = "";
        switch(carta){
            case 0:
                url = "/Resources/Chance/AvanceArquitectura.png";
                break;
            case 1:
                url = "/Resources/Chance/AvanceBiologia.png";
                break;
            case 2:
                url = "/Resources/Chance/AvanceConstruccion.png";
                break;                
            case 3:
                url = "/Resources/Chance/AvanceGo.png";
                break;        
            case 4:
                url = "/Resources/Chance/Avancebicipublica.png";
                break;      
            case 5:
                url = "/Resources/Chance/ClasesVirtuales.png";
                break;                
            case 6:
                url = "/Resources/Chance/DepositoPrestamo.png";
                break; 
            case 7:
                url = "/Resources/Chance/MultaBiciTEC.png";
                break;
            case 8:
                url = "/Resources/Chance/PagoAsistencia.png";
                break;             
            case 9:
                url = "/Resources/Chance/RetrocedaTresCasillas.png";
                break;
            case 10:
                url = "/Resources/Chance/SalirClasesVirtuales.png";
                break;
            default:
                url = "/Resources/Chance/RetrocedaTresCasillas.png";
                break; 
        }
        cartaJF.cartaB = true;
        cartaJF.setChance(carta,url);
        cartaJF.setVisible(true);
        
    }
    public void cartaArca(int arca){
        System.out.println("cual llegoooo arca "+arca);
        String url = "";
        switch(arca){
            case 0:
                url = "/Resources/ArcaComunal/ClasesVirtuales.png";
                break;
            case 1:
                url = "/Resources/ArcaComunal/CobroClinica.png";
                break;
            case 2:
                url = "/Resources/ArcaComunal/CobroImpuestos.png";
                break;                
            case 3:
                url = "/Resources/ArcaComunal/PagoSeguro.png";
                break;        
            case 4:
                url = "/Resources/ArcaComunal/PagoVacaciones.png";
                break;      
            case 5:
                url = "/Resources/Chance/PagosHospital.png";
                break;                
            case 6:
                url = "/Resources/Chance/PagueMateriales.png";
                break; 
            case 7:
                url = "/Resources/Chance/SaleClasesVirtuales.png";
                break;
            default:
                url = "/Resources/Chance/PagosHospital.png";
                break; 
        }
        cartaJF.cartaB = false;
        cartaJF.setChance(arca,url);    
        cartaJF.setVisible(true);
        
    }
    public void mostrarSubasta(String subastada){
        System.out.println("En el metodo: "+subastada);
        ImageIcon icon = new ImageIcon(getClass().getResource(subastada));
        int width =275;
        int height = 410;
        icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        pantallaSubastas.lblPropiedad.setIcon(icon);
        pantalla.panelDisponible.setVisible(true);
        pantallaSubastas.setVisible(true);
    }
    void cargarVentanaIntercambio(ArrayList<String> misProps, ArrayList<String> susProps, int miDinero, int suDinero, String nombre2, int otraPersona) {
        intercambios.setVisible(true);    
        intercambios.updateData(misProps, susProps, miDinero,suDinero,nombre2,otraPersona);
        System.out.println("Updated");
              
    }
    public void setRefCliente(Cliente refCliente) {
        this.refCliente = refCliente;
    }
    public void agregarCasillas(){
        casillas = new ArrayList();
        casillas.add(inicio);
        casillas.add(culturaYdeporte);
        casillas.add(communityChest0);
        casillas.add(escCienciasSociales);
        casillas.add(pagoCreditos);
        casillas.add(biciTec);
        casillas.add(escCienciasLenguaje);
        casillas.add(chance0);
        casillas.add(escQuimica);
        casillas.add(escMatematicas);
        casillas.add(carcel);
        casillas.add(escBiologia);
        casillas.add(fotocopiadora);
        casillas.add(ingAgricola);
        casillas.add(ingForestal);
        casillas.add(lumaca);
        casillas.add(administracion);
        casillas.add(communityChest1);
        casillas.add(ingSegLaboral);
        casillas.add(produccionInd);
        casillas.add(parqueo);
        casillas.add(escFisica);
        casillas.add(chance1);
        casillas.add(escEducTecnica);
        casillas.add(ingConstruccion);
        casillas.add(bicitec2);
        casillas.add(ingElectromecanica);
        casillas.add(ingElectrica);
        casillas.add(laimi);
        casillas.add(ingMateriales);
        casillas.add(vayaCarcel);
        casillas.add(diseñoInd);
        casillas.add(ingMecatronica);
        casillas.add(communityChest2);
        casillas.add(ingComputadores);
        casillas.add(trenes);
        casillas.add(chance2);
        casillas.add(ingComputacion);
        casillas.add(pagoPoliza);
        casillas.add(escArqui);
        
        
    }
    public void agregarCasillasCasas(){
        casillasCasas = new ArrayList();
        casillasCasas.add(inicio);
        casillasCasas.add(casas1);
        casillasCasas.add(communityChest0);
        casillasCasas.add(casas2);
        casillasCasas.add(pagoCreditos);
        casillasCasas.add(biciTec);
        casillasCasas.add(casas3);
        casillasCasas.add(chance0);
        casillasCasas.add(casas4);
        casillasCasas.add(casas5);
        casillasCasas.add(carcel);
        casillasCasas.add(casas6);
        casillasCasas.add(fotocopiadora);
        casillasCasas.add(casas7);
        casillasCasas.add(casas8);
        casillasCasas.add(lumaca);
        casillasCasas.add(casas9);
        casillasCasas.add(communityChest1);
        casillasCasas.add(casas10);
        casillasCasas.add(casas11);
        casillasCasas.add(parqueo);
        casillasCasas.add(casas12);
        casillasCasas.add(chance1);
        casillasCasas.add(casas13);
        casillasCasas.add(casas14);
        casillasCasas.add(bicitec2);
        casillasCasas.add(casas15);
        casillasCasas.add(casas16);
        casillasCasas.add(laimi);
        casillasCasas.add(casas17);
        casillasCasas.add(vayaCarcel);
        casillasCasas.add(casas18);
        casillasCasas.add(casas19);
        casillasCasas.add(communityChest2);
        casillasCasas.add(casas20);
        casillasCasas.add(trenes);
        casillasCasas.add(chance2);
        casillasCasas.add(casas21);
        casillasCasas.add(pagoPoliza);
        casillasCasas.add(casas22);
        
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnSalirCarcel = new javax.swing.JButton();
        btnHipotecar = new javax.swing.JButton();
        btnConstruir = new javax.swing.JButton();
        btnTerminarTurno = new javax.swing.JButton();
        btnIntercambiar = new javax.swing.JButton();
        btnCarcelGratis = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        cbbNombres = new javax.swing.JComboBox<>();
        lblMoney = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lblProperties = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane19 = new javax.swing.JScrollPane();
        jTextArea19 = new javax.swing.JTextArea();
        jLabel34 = new javax.swing.JLabel();
        chatSms = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        casas22 = new javax.swing.JPanel();
        casas21 = new javax.swing.JPanel();
        casas20 = new javax.swing.JPanel();
        casas19 = new javax.swing.JPanel();
        casas18 = new javax.swing.JPanel();
        casas17 = new javax.swing.JPanel();
        casas16 = new javax.swing.JPanel();
        casas15 = new javax.swing.JPanel();
        casas14 = new javax.swing.JPanel();
        casas13 = new javax.swing.JPanel();
        casas12 = new javax.swing.JPanel();
        casas11 = new javax.swing.JPanel();
        casas10 = new javax.swing.JPanel();
        casas9 = new javax.swing.JPanel();
        casas8 = new javax.swing.JPanel();
        casas7 = new javax.swing.JPanel();
        casas6 = new javax.swing.JPanel();
        casas5 = new javax.swing.JPanel();
        casas4 = new javax.swing.JPanel();
        casas3 = new javax.swing.JPanel();
        casas2 = new javax.swing.JPanel();
        casas1 = new javax.swing.JPanel();
        inicio = new javax.swing.JPanel();
        culturaYdeporte = new javax.swing.JPanel();
        communityChest0 = new javax.swing.JPanel();
        escCienciasSociales = new javax.swing.JPanel();
        pagoCreditos = new javax.swing.JPanel();
        biciTec = new javax.swing.JPanel();
        escCienciasLenguaje = new javax.swing.JPanel();
        chance0 = new javax.swing.JPanel();
        escQuimica = new javax.swing.JPanel();
        escMatematicas = new javax.swing.JPanel();
        carcel = new javax.swing.JPanel();
        escBiologia = new javax.swing.JPanel();
        fotocopiadora = new javax.swing.JPanel();
        ingAgricola = new javax.swing.JPanel();
        ingForestal = new javax.swing.JPanel();
        lumaca = new javax.swing.JPanel();
        administracion = new javax.swing.JPanel();
        communityChest1 = new javax.swing.JPanel();
        ingSegLaboral = new javax.swing.JPanel();
        produccionInd = new javax.swing.JPanel();
        parqueo = new javax.swing.JPanel();
        escFisica = new javax.swing.JPanel();
        chance1 = new javax.swing.JPanel();
        escEducTecnica = new javax.swing.JPanel();
        ingConstruccion = new javax.swing.JPanel();
        bicitec2 = new javax.swing.JPanel();
        ingElectromecanica = new javax.swing.JPanel();
        ingElectrica = new javax.swing.JPanel();
        laimi = new javax.swing.JPanel();
        ingMateriales = new javax.swing.JPanel();
        vayaCarcel = new javax.swing.JPanel();
        diseñoInd = new javax.swing.JPanel();
        ingMecatronica = new javax.swing.JPanel();
        communityChest2 = new javax.swing.JPanel();
        ingComputadores = new javax.swing.JPanel();
        trenes = new javax.swing.JPanel();
        chance2 = new javax.swing.JPanel();
        ingComputacion = new javax.swing.JPanel();
        pagoPoliza = new javax.swing.JPanel();
        escArqui = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblDado2 = new javax.swing.JLabel();
        lblDado1 = new javax.swing.JLabel();
        btnLanzarDados = new javax.swing.JButton();
        btnMoverToken = new javax.swing.JButton();
        jSpinner1 = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(206, 255, 221));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSalirCarcel.setBackground(new java.awt.Color(121, 150, 130));
        btnSalirCarcel.setForeground(new java.awt.Color(255, 255, 255));
        btnSalirCarcel.setText("SALIR CARCEL");
        btnSalirCarcel.setEnabled(false);
        btnSalirCarcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirCarcelActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalirCarcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 110, 50));

        btnHipotecar.setBackground(new java.awt.Color(121, 150, 130));
        btnHipotecar.setForeground(new java.awt.Color(255, 255, 255));
        btnHipotecar.setText("HIPOTECAR");
        btnHipotecar.setEnabled(false);
        btnHipotecar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHipotecarActionPerformed(evt);
            }
        });
        jPanel1.add(btnHipotecar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, 110, 50));

        btnConstruir.setBackground(new java.awt.Color(121, 150, 130));
        btnConstruir.setForeground(new java.awt.Color(255, 255, 255));
        btnConstruir.setText("CONSTRUIR");
        btnConstruir.setEnabled(false);
        btnConstruir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConstruirActionPerformed(evt);
            }
        });
        jPanel1.add(btnConstruir, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, 110, 50));

        btnTerminarTurno.setBackground(new java.awt.Color(121, 150, 130));
        btnTerminarTurno.setForeground(new java.awt.Color(255, 255, 255));
        btnTerminarTurno.setText("TERMINAR TURNO");
        btnTerminarTurno.setEnabled(false);
        btnTerminarTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTerminarTurnoActionPerformed(evt);
            }
        });
        jPanel1.add(btnTerminarTurno, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 130, 50));

        btnIntercambiar.setBackground(new java.awt.Color(121, 150, 130));
        btnIntercambiar.setForeground(new java.awt.Color(255, 255, 255));
        btnIntercambiar.setText("INTERCAMBIAR");
        btnIntercambiar.setEnabled(false);
        btnIntercambiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIntercambiarActionPerformed(evt);
            }
        });
        jPanel1.add(btnIntercambiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 110, 50));

        btnCarcelGratis.setBackground(new java.awt.Color(121, 150, 130));
        btnCarcelGratis.setForeground(new java.awt.Color(255, 255, 255));
        btnCarcelGratis.setText("SALIR CARCEL GRATIS");
        btnCarcelGratis.setEnabled(false);
        btnCarcelGratis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarcelGratisActionPerformed(evt);
            }
        });
        jPanel1.add(btnCarcelGratis, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 190, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 540, 550, 150));

        jPanel3.setBackground(new java.awt.Color(206, 255, 221));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cbbNombres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbNombresActionPerformed(evt);
            }
        });
        jPanel3.add(cbbNombres, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 140, 20));

        lblMoney.setBackground(new java.awt.Color(255, 255, 255));
        lblMoney.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMoney.setOpaque(true);
        jPanel3.add(lblMoney, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 170, 20));

        lblProperties.setColumns(20);
        lblProperties.setRows(5);
        jScrollPane3.setViewportView(lblProperties);

        jPanel3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 300, 100));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Jugador:");
        jLabel10.setOpaque(true);
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 80, 20));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Propiedades:");
        jLabel8.setOpaque(true);
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 80, 20));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Dinero:");
        jLabel11.setOpaque(true);
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 80, 20));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 0, 330, 250));

        jPanel19.setBackground(new java.awt.Color(206, 255, 221));
        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextArea19.setColumns(20);
        jTextArea19.setRows(5);
        jScrollPane19.setViewportView(jTextArea19);

        jPanel19.add(jScrollPane19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 300, 180));

        jLabel34.setBackground(new java.awt.Color(255, 255, 255));
        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel34.setText("CHAT");
        jLabel34.setOpaque(true);
        jPanel19.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 40, 20));

        chatSms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatSmsActionPerformed(evt);
            }
        });
        chatSms.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                chatSmsKeyPressed(evt);
            }
        });
        jPanel19.add(chatSms, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 300, -1));

        getContentPane().add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 260, 330, 270));

        jPanel2.setBackground(new java.awt.Color(206, 255, 221));
        jPanel2.setToolTipText("");
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        casas22.setOpaque(false);
        casas22.setLayout(new java.awt.GridLayout(1, 0));
        java.awt.GridLayout gridLayout10 = new java.awt.GridLayout();
        gridLayout10.setColumns(1);
        gridLayout10.setRows(4);
        casas22.setLayout(gridLayout10);
        jPanel2.add(casas22, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 540, 20, 60));

        casas21.setOpaque(false);
        casas21.setLayout(new java.awt.GridLayout(1, 0));
        java.awt.GridLayout gridLayout9 = new java.awt.GridLayout();
        gridLayout9.setColumns(1);
        gridLayout9.setRows(4);
        casas21.setLayout(gridLayout9);
        jPanel2.add(casas21, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 430, 20, 60));

        casas20.setOpaque(false);
        casas20.setLayout(new java.awt.GridLayout(1, 0));
        java.awt.GridLayout gridLayout8 = new java.awt.GridLayout();
        gridLayout8.setColumns(1);
        gridLayout8.setRows(4);
        casas20.setLayout(gridLayout8);
        jPanel2.add(casas20, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 260, 20, 60));

        casas19.setOpaque(false);
        casas19.setLayout(new java.awt.GridLayout(1, 0));
        java.awt.GridLayout gridLayout7 = new java.awt.GridLayout();
        gridLayout7.setColumns(1);
        gridLayout7.setRows(4);
        casas19.setLayout(gridLayout7);
        jPanel2.add(casas19, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 150, 20, 60));

        casas18.setOpaque(false);
        casas18.setLayout(new java.awt.GridLayout(1, 0));
        java.awt.GridLayout gridLayout6 = new java.awt.GridLayout();
        gridLayout6.setColumns(1);
        gridLayout6.setRows(4);
        casas18.setLayout(gridLayout6);
        jPanel2.add(casas18, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 90, 20, 60));

        casas17.setOpaque(false);
        casas17.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(casas17, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 70, 60, 20));

        casas16.setOpaque(false);
        casas16.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(casas16, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 70, 60, 20));

        casas15.setOpaque(false);
        casas15.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(casas15, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 70, 60, 20));

        casas14.setOpaque(false);
        casas14.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(casas14, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 60, 20));

        casas13.setOpaque(false);
        casas13.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(casas13, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 60, 20));

        casas12.setOpaque(false);
        casas12.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(casas12, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 60, 20));

        casas11.setOpaque(false);
        casas11.setLayout(new java.awt.GridLayout(1, 0));
        java.awt.GridLayout gridLayout5 = new java.awt.GridLayout();
        gridLayout5.setColumns(1);
        gridLayout5.setRows(4);
        casas11.setLayout(gridLayout5);
        jPanel2.add(casas11, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 90, 20, 60));

        casas10.setOpaque(false);
        casas10.setLayout(new java.awt.GridLayout(1, 0));
        java.awt.GridLayout gridLayout4 = new java.awt.GridLayout();
        gridLayout4.setColumns(1);
        gridLayout4.setRows(4);
        casas10.setLayout(gridLayout4);
        jPanel2.add(casas10, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 150, 20, 60));

        casas9.setOpaque(false);
        casas9.setLayout(new java.awt.GridLayout(1, 0));
        java.awt.GridLayout gridLayout3 = new java.awt.GridLayout();
        gridLayout3.setColumns(1);
        gridLayout3.setRows(4);
        casas9.setLayout(gridLayout3);
        jPanel2.add(casas9, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 260, 20, 60));

        casas8.setOpaque(false);
        casas8.setLayout(new java.awt.GridLayout(1, 0));
        java.awt.GridLayout gridLayout2 = new java.awt.GridLayout();
        gridLayout2.setColumns(1);
        gridLayout2.setRows(4);
        casas8.setLayout(gridLayout2);
        jPanel2.add(casas8, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 370, 20, 60));

        casas7.setOpaque(false);
        casas7.setLayout(new java.awt.GridLayout(1, 0));
        java.awt.GridLayout gridLayout1 = new java.awt.GridLayout();
        gridLayout1.setColumns(1);
        gridLayout1.setRows(4);
        casas7.setLayout(gridLayout1);
        jPanel2.add(casas7, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 430, 20, 60));

        casas6.setOpaque(false);
        java.awt.GridLayout gridLayout = new java.awt.GridLayout();
        gridLayout.setColumns(1);
        gridLayout.setRows(4);
        casas6.setLayout(new java.awt.GridLayout(1, 0));
        casas6.setLayout(gridLayout);
        jPanel2.add(casas6, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 543, 20, 60));

        casas5.setOpaque(false);
        casas5.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(casas5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 600, 60, 20));

        casas4.setOpaque(false);
        casas4.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(casas4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 600, 60, 20));

        casas3.setOpaque(false);
        casas3.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(casas3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 600, 60, 20));

        casas2.setOpaque(false);
        casas2.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(casas2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 600, 60, 20));

        casas1.setOpaque(false);
        casas1.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(casas1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 600, 60, 20));

        inicio.setOpaque(false);
        inicio.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(inicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 600, 90, 90));

        culturaYdeporte.setOpaque(false);
        culturaYdeporte.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(culturaYdeporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 600, 60, 90));

        communityChest0.setOpaque(false);
        communityChest0.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(communityChest0, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 600, 50, 90));

        escCienciasSociales.setOpaque(false);
        escCienciasSociales.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(escCienciasSociales, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 600, 60, 90));

        pagoCreditos.setOpaque(false);
        pagoCreditos.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(pagoCreditos, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 600, 50, 90));

        biciTec.setOpaque(false);
        biciTec.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(biciTec, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 600, 60, 90));

        escCienciasLenguaje.setOpaque(false);
        escCienciasLenguaje.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(escCienciasLenguaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 600, 60, 90));

        chance0.setOpaque(false);
        chance0.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(chance0, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 600, 50, 90));

        escQuimica.setOpaque(false);
        escQuimica.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(escQuimica, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 600, 60, 90));

        escMatematicas.setOpaque(false);
        escMatematicas.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(escMatematicas, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 600, 60, 90));

        carcel.setOpaque(false);
        carcel.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(carcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 90, 90));

        escBiologia.setOpaque(false);
        escBiologia.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(escBiologia, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, 90, 60));

        fotocopiadora.setOpaque(false);
        fotocopiadora.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(fotocopiadora, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 90, 50));

        ingAgricola.setOpaque(false);
        ingAgricola.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(ingAgricola, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 90, 60));

        ingForestal.setOpaque(false);
        ingForestal.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(ingForestal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 90, 60));

        lumaca.setOpaque(false);
        lumaca.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(lumaca, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 90, 50));

        administracion.setOpaque(false);
        administracion.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(administracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 90, 60));

        communityChest1.setOpaque(false);
        communityChest1.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(communityChest1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 90, 50));

        ingSegLaboral.setOpaque(false);
        ingSegLaboral.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(ingSegLaboral, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 90, 60));

        produccionInd.setOpaque(false);
        produccionInd.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(produccionInd, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 90, 60));

        parqueo.setOpaque(false);
        parqueo.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(parqueo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 90));

        escFisica.setOpaque(false);
        escFisica.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(escFisica, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, 60, 90));

        chance1.setOpaque(false);
        chance1.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(chance1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 50, 90));

        escEducTecnica.setOpaque(false);
        escEducTecnica.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(escEducTecnica, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 60, 90));

        ingConstruccion.setOpaque(false);
        ingConstruccion.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(ingConstruccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 60, 90));

        bicitec2.setOpaque(false);
        bicitec2.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(bicitec2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 50, 90));

        ingElectromecanica.setOpaque(false);
        ingElectromecanica.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(ingElectromecanica, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 0, 60, 90));

        ingElectrica.setOpaque(false);
        ingElectrica.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(ingElectrica, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 0, 60, 90));

        laimi.setOpaque(false);
        laimi.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(laimi, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 0, 50, 90));

        ingMateriales.setOpaque(false);
        ingMateriales.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(ingMateriales, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 0, 60, 90));

        vayaCarcel.setOpaque(false);
        vayaCarcel.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(vayaCarcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 0, 90, 90));

        diseñoInd.setOpaque(false);
        diseñoInd.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(diseñoInd, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 90, 90, 60));

        ingMecatronica.setOpaque(false);
        ingMecatronica.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(ingMecatronica, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 150, 90, 60));

        communityChest2.setOpaque(false);
        communityChest2.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(communityChest2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 210, 90, 50));

        ingComputadores.setOpaque(false);
        ingComputadores.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(ingComputadores, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 260, 90, 60));

        trenes.setOpaque(false);
        trenes.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(trenes, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 320, 90, 50));

        chance2.setOpaque(false);
        chance2.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(chance2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 380, 90, 50));

        ingComputacion.setOpaque(false);
        ingComputacion.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(ingComputacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 430, 90, 60));

        pagoPoliza.setOpaque(false);
        pagoPoliza.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(pagoPoliza, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 490, 90, 50));

        escArqui.setOpaque(false);
        escArqui.setLayout(new java.awt.GridLayout(1, 0));
        jPanel2.add(escArqui, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 540, 90, 60));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Tablero.png"))); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 690));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 690, 690));

        jPanel4.setBackground(new java.awt.Color(206, 255, 221));
        jPanel4.setToolTipText("");
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDado2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.add(lblDado2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 84, 84));

        lblDado1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.add(lblDado1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 84, 84));

        btnLanzarDados.setBackground(new java.awt.Color(121, 150, 130));
        btnLanzarDados.setForeground(new java.awt.Color(255, 255, 255));
        btnLanzarDados.setText("LANZAR DADOS");
        btnLanzarDados.setEnabled(false);
        btnLanzarDados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLanzarDadosActionPerformed(evt);
            }
        });
        jPanel4.add(btnLanzarDados, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 110, 40));

        btnMoverToken.setText("OK");
        btnMoverToken.setEnabled(false);
        btnMoverToken.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoverTokenActionPerformed(evt);
            }
        });
        jPanel4.add(btnMoverToken, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 60, 20));
        jPanel4.add(jSpinner1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, -1, -1));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 0, 220, 530));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void setTitulo(){
        this.setTitle(refCliente.nickname);
    }
    public void cayoCarcel() throws IOException{
        enCarcel = true;
        deshabilitarBtns();
        btnSalirCarcel.setVisible(true);
        btnSalirCarcel.setEnabled(false);
        btnTerminarTurno.setEnabled(true);
        if (tieneSalirGratis){
            btnCarcelGratis.setVisible(true);
            btnCarcelGratis.setEnabled(true);
        }
        refCliente.hiloCliente.writer.writeInt(8);
        refCliente.hiloCliente.writer.writeInt(labelActual);
        refCliente.hiloCliente.writer.writeInt(20);
        refCliente.hiloCliente.objWriter.writeObject(myIcon);
        labelActual = 10;
        refCliente.hiloCliente.writer.writeInt(5);
        refCliente.hiloCliente.writer.writeUTF("El jugador cayó en la carcel");
        
    }
    public void salioCarcel() throws IOException{
        enCarcel = false;
        btnCarcelGratis.setEnabled(false);
        btnCarcelGratis.setVisible(false);
        refCliente.hiloCliente.writer.writeInt(5);
        refCliente.hiloCliente.writer.writeUTF("El jugador salio de la carcel");
        btnSalirCarcel.setVisible(false);
    }
    public void noDinero(){
        JOptionPane.showMessageDialog(this, "No tiene sufienciente dinero");
    }
    public void actualizarDinero(){
       try {
            // TODO add your handling code here:
            int index = cbbNombres.getSelectedIndex();
            if(index>-1 && actualizado){
            refCliente.hiloCliente.writer.writeInt(6);
            refCliente.hiloCliente.writer.writeInt(index);
            }
        } catch (IOException ex) {
            Logger.getLogger(MonopolyJF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void cbbNombresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbNombresActionPerformed
        actualizarDinero();
    }//GEN-LAST:event_cbbNombresActionPerformed

    private void chatSmsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatSmsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chatSmsActionPerformed

    private void btnSalirCarcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirCarcelActionPerformed
        try {
            System.out.println("BOTON CARCEL");
            btnLanzarDados.setEnabled(false);
            refCliente.hiloCliente.writer.writeInt(16);
            refCliente.hiloCliente.writer.writeInt(0);
        } catch (IOException ex) {
            Logger.getLogger(MonopolyJF.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnSalirCarcelActionPerformed
    public void addMessage(String msj){
        jTextArea19.append(msj + "\n");
    }
    public void pintarLanzamientoDados(String icon1,String icon2,int turnoLegal,String iguales) throws IOException{
        lblDado1.setIcon(new javax.swing.ImageIcon(getClass().getResource(icon1))); // NOI18
        lblDado2.setIcon(new javax.swing.ImageIcon(getClass().getResource(icon2)));
        //turnoActual = turnoLegal;
        turnoActual = (int) jSpinner1.getValue();
        boolean igual = parseBoolean(iguales);
        dadosIguales = igual;
        if (!igual){
            btnLanzarDados.setEnabled(false);
        }
        if (enCarcel){
            btnLanzarDados.setEnabled(false);
            btnSalirCarcel.setEnabled(false);
        }
       
    }
    public void habilitarBtns() throws IOException{
        if (!rendido){
            btnLanzarDados.setEnabled(true);
            btnMoverToken.setEnabled(true);
            btnTerminarTurno.setEnabled(true);
        if (!enCarcel){
        btnIntercambiar.setEnabled(true);
        btnSalirCarcel.setEnabled(true);
        btnHipotecar.setEnabled(true);
        btnConstruir.setEnabled(true);
        }
        else{
        btnIntercambiar.setEnabled(false);
        btnSalirCarcel.setEnabled(true);
        btnHipotecar.setEnabled(false);
        btnConstruir.setEnabled(false); 
        }
        }
        else {
              refCliente.hiloCliente.writer.writeInt(5);
              refCliente.hiloCliente.writer.writeUTF("Era mi turno pero me he rendido, paso al siguiente");
              btnTerminarTurno.doClick();
        }
    
    }
    public void deshabilitarBtns(){
        btnIntercambiar.setEnabled(false);
        btnLanzarDados.setEnabled(false);
        btnSalirCarcel.setEnabled(false);
        btnHipotecar.setEnabled(false);
        btnConstruir.setEnabled(false);
        btnTerminarTurno.setEnabled(false);
        btnMoverToken.setEnabled(false);
    }
    private void chatSmsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_chatSmsKeyPressed
        // TODO add your handling code here:
      if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
          try {
              String str = chatSms.getText();
              refCliente.hiloCliente.writer.writeInt(5);
              refCliente.hiloCliente.writer.writeUTF(str);
              chatSms.setText("");
          } catch (IOException ex) {
              Logger.getLogger(MonopolyJF.class.getName()).log(Level.SEVERE, null, ex);
          }
   }
    }//GEN-LAST:event_chatSmsKeyPressed
    public void updateHipoteca(ArrayList <String> elementos){
        refHipotecas.updateComboBox(elementos);
        refHipotecas.setVisible(true);
    }
    private void btnHipotecarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHipotecarActionPerformed

        try {
            refCliente.hiloCliente.writer.writeInt(29);
        } catch (IOException ex) {
            Logger.getLogger(MonopolyJF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnHipotecarActionPerformed

    private void btnConstruirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConstruirActionPerformed

        try {
            System.out.println("Do");
            refCliente.hiloCliente.writer.writeInt(32);
            System.out.println("Do do");
        } catch (IOException ex) {
            Logger.getLogger(MonopolyJF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnConstruirActionPerformed

    private void btnTerminarTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTerminarTurnoActionPerformed
        // TODO add your handling code here:
        try {
              refCliente.hiloCliente.writer.writeInt(7);
              
          } catch (IOException ex) {
              Logger.getLogger(MonopolyJF.class.getName()).log(Level.SEVERE, null, ex);
          }
    }//GEN-LAST:event_btnTerminarTurnoActionPerformed

    private void btnLanzarDadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLanzarDadosActionPerformed
        // TODO add your handling code here:
        try {
              refCliente.hiloCliente.writer.writeInt(3);
          } catch (IOException ex) {
              Logger.getLogger(MonopolyJF.class.getName()).log(Level.SEVERE, null, ex);
          }
    }//GEN-LAST:event_btnLanzarDadosActionPerformed
    public void findToken(int lblAbuscar,ImageIcon iconi,int casillaMover){
        JPanel casilla = casillas.get(lblAbuscar);
        Component [] componentes = casilla.getComponents();
        //System.out.println("myicnonito"+iconi);
        for (int i = 0; i < componentes.length ; i++) {
            JLabel iconito = (JLabel)componentes[i];
            if (iconito.getIcon().toString().equals(iconi.toString())) {
                casilla.remove(componentes[i]);  
                //System.out.println("Sí lo encontró");
                casilla.revalidate();
                casilla.repaint();
                repaintToken(iconito,lblAbuscar+casillaMover);
            }
        }
    }
    public void repaintToken(JLabel lbl,int casillaActual){
        if (casillaActual > 39){
            casillaActual = casillaActual-40;
        }
        JPanel casilla = casillas.get(casillaActual);
        casilla.add(lbl);
        casilla.revalidate();
        casilla.repaint();
    }
    public void mostrarPropDisponible(String url){
        pantalla.setVisible(true);
        pantalla.imagen = url;
        ImageIcon icon = new ImageIcon(getClass().getResource(url));
        int width =233;
        int height = 343;
        icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        pantalla.lblProperty.setIcon(icon);
        pantalla.panelNoDisponible.setVisible(false);
        pantalla.panelDisponible.setVisible(true);
                
    }
    public void mostrarPropNoDisponible(String url,String dueño ,boolean esUtility){
        ImageIcon icon = new ImageIcon(getClass().getResource(url));
        int width =233;
        int height = 343;
        icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        pantalla.lblProperty.setIcon(icon);
        pantalla.lblDueño.setText(dueño);
        pantalla.panelDisponible.setVisible(false);
        pantalla.panelNoDisponible.setVisible(true);
        System.out.println("Es utility  ?"+esUtility);
        if (esUtility){
            pantalla.setVisible(true);
            pantalla.pnlUtility.setVisible(true);
        }
        else{
            try {
                pantalla.setVisible(true);
                pantalla.pnlUtility.setVisible(false);
                refCliente.hiloCliente.writer.writeInt(23);
                refCliente.hiloCliente.writer.writeInt(1);
            } catch (IOException ex) {
                Logger.getLogger(MonopolyJF.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    private void btnMoverTokenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoverTokenActionPerformed
        // TODO add your handling code here:
        //findToken(labelActual,myIcon);
        //findToken(labelActual,myIcon,turnoActual);
        try {
            if (labelActual + turnoActual == 30)
                JOptionPane.showMessageDialog(this, "Ha caido en la carcel");
              refCliente.hiloCliente.writer.writeInt(5);
              refCliente.hiloCliente.writer.writeUTF("Tiro los dados y sacó: "+turnoActual);
              refCliente.hiloCliente.writer.writeInt(8);
              refCliente.hiloCliente.writer.writeInt(labelActual);
              refCliente.hiloCliente.writer.writeInt(turnoActual);
              refCliente.hiloCliente.objWriter.writeObject(myIcon);
              labelActual += turnoActual;
              if (labelActual > 39)
                labelActual -= 40;
              if (labelActual == 4 ){
                  cambiarDinero(200,1);
                    }
              else if (labelActual == 38 ){
                  cambiarDinero(75,1);
              } 
              refCliente.hiloCliente.writer.writeInt(9);
              refCliente.hiloCliente.writer.writeInt(turnoActual);
              //refCliente.hiloCliente.writer.writeInt(myIndex);
              //refCliente.hiloCliente.writer.writeUTF(myIcon.toString());
              
              
          } catch (IOException ex) {
              Logger.getLogger(MonopolyJF.class.getName()).log(Level.SEVERE, null, ex);
          }
          btnMoverToken.setEnabled(false);
    }//GEN-LAST:event_btnMoverTokenActionPerformed

    private void btnIntercambiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIntercambiarActionPerformed
        elegirPantalla.actualizarCBB();
        intercambios.aceptar.setEnabled(false);
        elegirPantalla.setVisible(true);
    }//GEN-LAST:event_btnIntercambiarActionPerformed

    private void btnCarcelGratisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarcelGratisActionPerformed
        // TODO add your handling code here:
        try {
            System.out.println("BOTON CARCEL");
            tieneSalirGratis = false;
            btnLanzarDados.setEnabled(false);
            refCliente.hiloCliente.writer.writeInt(16);
            refCliente.hiloCliente.writer.writeInt(1);
        } catch (IOException ex) {
            Logger.getLogger(MonopolyJF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCarcelGratisActionPerformed
    public void actualizarCBBplayer(String propiedades,String dinero){
        lblMoney.setText("");
        lblProperties.setText("");
        lblMoney.setText(dinero);
        lblProperties.setText(propiedades);
    }
    public void actualizarCBB(){
        for (int i = 0; i < nombres.size(); i++){
            cbbNombres.addItem(nombres.get(i));
        } 
        cbbNombres.setSelectedIndex(-1);
        actualizado = true;
    }
    public void colocarBtnsInicio(){
        for (int i = 0; i < botones.size(); i++) {
            lblAL.get(i).setSize(32, 32);
            ImageIcon iconi = new ImageIcon(getClass().getResource(botones.get(i)));
            iconos.add(iconi);
            if (i == myIndex) myIcon = iconi;
            lblAL.get(i).setIcon(iconi); // NOI18N
            lblAL.get(i).setOpaque(false);
            inicio.add(lblAL.get(i));   
            //inicio.get
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MonopolyJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MonopolyJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MonopolyJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MonopolyJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MonopolyJF().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel administracion;
    private javax.swing.JPanel biciTec;
    private javax.swing.JPanel bicitec2;
    private javax.swing.JButton btnCarcelGratis;
    public javax.swing.JButton btnConstruir;
    public javax.swing.JButton btnHipotecar;
    private javax.swing.JButton btnIntercambiar;
    private javax.swing.JButton btnLanzarDados;
    private javax.swing.JButton btnMoverToken;
    private javax.swing.JButton btnSalirCarcel;
    public javax.swing.JButton btnTerminarTurno;
    private javax.swing.JPanel carcel;
    private javax.swing.JPanel casas1;
    private javax.swing.JPanel casas10;
    private javax.swing.JPanel casas11;
    private javax.swing.JPanel casas12;
    private javax.swing.JPanel casas13;
    private javax.swing.JPanel casas14;
    private javax.swing.JPanel casas15;
    private javax.swing.JPanel casas16;
    private javax.swing.JPanel casas17;
    private javax.swing.JPanel casas18;
    private javax.swing.JPanel casas19;
    private javax.swing.JPanel casas2;
    private javax.swing.JPanel casas20;
    private javax.swing.JPanel casas21;
    private javax.swing.JPanel casas22;
    private javax.swing.JPanel casas3;
    private javax.swing.JPanel casas4;
    private javax.swing.JPanel casas5;
    private javax.swing.JPanel casas6;
    private javax.swing.JPanel casas7;
    private javax.swing.JPanel casas8;
    private javax.swing.JPanel casas9;
    private javax.swing.JComboBox<String> cbbNombres;
    private javax.swing.JPanel chance0;
    private javax.swing.JPanel chance1;
    private javax.swing.JPanel chance2;
    private javax.swing.JTextField chatSms;
    private javax.swing.JPanel communityChest0;
    private javax.swing.JPanel communityChest1;
    private javax.swing.JPanel communityChest2;
    private javax.swing.JPanel culturaYdeporte;
    private javax.swing.JPanel diseñoInd;
    private javax.swing.JPanel escArqui;
    private javax.swing.JPanel escBiologia;
    private javax.swing.JPanel escCienciasLenguaje;
    private javax.swing.JPanel escCienciasSociales;
    private javax.swing.JPanel escEducTecnica;
    private javax.swing.JPanel escFisica;
    private javax.swing.JPanel escMatematicas;
    private javax.swing.JPanel escQuimica;
    private javax.swing.JPanel fotocopiadora;
    private javax.swing.JPanel ingAgricola;
    private javax.swing.JPanel ingComputacion;
    private javax.swing.JPanel ingComputadores;
    private javax.swing.JPanel ingConstruccion;
    private javax.swing.JPanel ingElectrica;
    private javax.swing.JPanel ingElectromecanica;
    private javax.swing.JPanel ingForestal;
    private javax.swing.JPanel ingMateriales;
    private javax.swing.JPanel ingMecatronica;
    private javax.swing.JPanel ingSegLaboral;
    private javax.swing.JPanel inicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTextArea jTextArea19;
    private javax.swing.JPanel laimi;
    private javax.swing.JLabel lblDado1;
    private javax.swing.JLabel lblDado2;
    private javax.swing.JLabel lblMoney;
    private javax.swing.JTextArea lblProperties;
    private javax.swing.JPanel lumaca;
    private javax.swing.JPanel pagoCreditos;
    private javax.swing.JPanel pagoPoliza;
    private javax.swing.JPanel parqueo;
    private javax.swing.JPanel produccionInd;
    private javax.swing.JPanel trenes;
    private javax.swing.JPanel vayaCarcel;
    // End of variables declaration//GEN-END:variables



}
