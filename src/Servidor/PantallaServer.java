/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import tecnopoly.Juego;
/**
 *
 * @author Alejandra G
 */
public class PantallaServer extends javax.swing.JFrame{
    Juego prueba = new Juego();
    /**
     * Creates new form PantallaServer
     */
    private Servidor srv;
    public PantallaServer() {
        initComponents();
    }
    
    
    public void setSrv(Servidor srv) {
        this.srv = srv;
    }
    
    
    
    
    public void addMessage(String msj){
        txaMensajes.append(msj + "\n");
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txaMensajes = new javax.swing.JTextArea();
        btnIniciar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txaMensajes.setColumns(20);
        txaMensajes.setRows(5);
        jScrollPane1.setViewportView(txaMensajes);

        btnIniciar.setText("Iniciar");
        btnIniciar.setEnabled(false);
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(163, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnIniciar, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        // TODO add your handling code here:
        srv.juego = new Juego(srv.conexiones.size());
        srv.juego.refServer = srv;
        addMessage("" + srv.conexiones.size());
        srv.ordenarAL();
        //srv.ordenarAL();
        for (int i = 0; i < srv.conexiones.size(); i++) {
        srv.nombres.add(srv.conexiones.get(i).nickname);
        srv.urlBotones.add(srv.conexiones.get(i).pieza);
        addMessage(srv.conexiones.get(i).toString()); 
        addMessage("NOmbre: "+ srv.conexiones.get(i).nickname);
        addMessage("turno: "+srv.conexiones.get(i).turno+"");
        addMessage("pieza: "+srv.conexiones.get(i).pieza+"");}
        try{
            for (int i = 0; i < srv.conexiones.size(); i++) {
            System.out.println("entra"+ i);
            ThreadServidor current = srv.conexiones.get(i);
            current.writer.writeInt(4);
            current.objWriter.writeObject(srv.urlBotones); 
            current.objWriter.writeObject(srv.nombres);
            current.writer.writeInt(i);
            }
            for (int i = 0; i < srv.conexiones.size(); i++) {
            ThreadServidor current = srv.conexiones.get(i);
            current.writer.writeInt(5);
            }
            ThreadServidor current = srv.conexiones.get(0);
            current.writer.writeInt(9);
            
            
        } catch (IOException ex) {
            Logger.getLogger(PantallaServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
      
        
    }//GEN-LAST:event_btnIniciarActionPerformed

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
            java.util.logging.Logger.getLogger(PantallaServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaServer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnIniciar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txaMensajes;
    // End of variables declaration//GEN-END:variables

    
}
