/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import utils.Constantes;
import views.dialogos.Acerca;
import views.internalFrame.Ciclos;
import views.internalFrame.Empresas;
import views.internalFrame.NuevoAlumno;
import views.internalFrame.NuevoProfesor;
import views.internalFrame.NuevoTutor;

/**
 *
 * @author Jmarser
 */
public class Escritorio extends javax.swing.JFrame {

    /**
     * Creates new form Escritorio
     */
    public Escritorio() {
        initComponents();
        initVentana();
    }

    private void initVentana() {
        this.setTitle("Sistema de gestión de FCT");//titulo de la ventana
        this.setExtendedState(MAXIMIZED_BOTH);//a pantalla completa
        this.setLocationRelativeTo(null);//centrado en la pantalla

        /*Vamos a tomar la decoración del sistema donde se instale la aplicación*/
        /*
        this.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Escritorio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Escritorio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Escritorio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Escritorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        addIcono();

    }

    /*Establecemos el icono de la aplicación*/
    private void addIcono() {
        URL url = getClass().getResource(Constantes.RUTA_ICON_DESKTOP);
        ImageIcon icono = new ImageIcon(url);
        this.setIconImage(icono.getImage());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jdp_escritorio = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jm_profesores = new javax.swing.JMenu();
        jmi_nuevo_profesor = new javax.swing.JMenuItem();
        jmi_gestionar_profesor = new javax.swing.JMenuItem();
        jmi_ciclos = new javax.swing.JMenuItem();
        jm_alumnos = new javax.swing.JMenu();
        jmi_nuevo_alumno = new javax.swing.JMenuItem();
        jmi_gestionar_alumno = new javax.swing.JMenuItem();
        jm_empresas = new javax.swing.JMenu();
        jmi_empresas = new javax.swing.JMenuItem();
        jmi_nuevo_tutor = new javax.swing.JMenuItem();
        jmi_gestionar_tutor = new javax.swing.JMenuItem();
        jm_ayuda = new javax.swing.JMenu();
        jmi_acerca = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jdp_escritorioLayout = new javax.swing.GroupLayout(jdp_escritorio);
        jdp_escritorio.setLayout(jdp_escritorioLayout);
        jdp_escritorioLayout.setHorizontalGroup(
            jdp_escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 817, Short.MAX_VALUE)
        );
        jdp_escritorioLayout.setVerticalGroup(
            jdp_escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 451, Short.MAX_VALUE)
        );

        jm_profesores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/teacher.png"))); // NOI18N
        jm_profesores.setText("PROFESORES");

        jmi_nuevo_profesor.setText("Nuevo profesor");
        jmi_nuevo_profesor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_nuevo_profesorActionPerformed(evt);
            }
        });
        jm_profesores.add(jmi_nuevo_profesor);

        jmi_gestionar_profesor.setText("Gestionar Profesor");
        jmi_gestionar_profesor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_gestionar_profesorActionPerformed(evt);
            }
        });
        jm_profesores.add(jmi_gestionar_profesor);

        jmi_ciclos.setText("Ciclos");
        jmi_ciclos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_ciclosActionPerformed(evt);
            }
        });
        jm_profesores.add(jmi_ciclos);

        jMenuBar1.add(jm_profesores);

        jm_alumnos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/student.png"))); // NOI18N
        jm_alumnos.setText("ALUMNOS");

        jmi_nuevo_alumno.setText("Nuevo Alumno");
        jmi_nuevo_alumno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_nuevo_alumnoActionPerformed(evt);
            }
        });
        jm_alumnos.add(jmi_nuevo_alumno);

        jmi_gestionar_alumno.setText("Gestionar Alumno");
        jm_alumnos.add(jmi_gestionar_alumno);

        jMenuBar1.add(jm_alumnos);

        jm_empresas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/empresa.png"))); // NOI18N
        jm_empresas.setText("EMPRESAS");

        jmi_empresas.setText("Empresas");
        jmi_empresas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_empresasActionPerformed(evt);
            }
        });
        jm_empresas.add(jmi_empresas);

        jmi_nuevo_tutor.setText("Nuevo Tutor");
        jmi_nuevo_tutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_nuevo_tutorActionPerformed(evt);
            }
        });
        jm_empresas.add(jmi_nuevo_tutor);

        jmi_gestionar_tutor.setText("Gestionar Tutor");
        jm_empresas.add(jmi_gestionar_tutor);

        jMenuBar1.add(jm_empresas);

        jm_ayuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/help.png"))); // NOI18N
        jm_ayuda.setText("AYUDA");
        jm_ayuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jm_ayudaActionPerformed(evt);
            }
        });

        jmi_acerca.setText("Acerca de...");
        jm_ayuda.add(jmi_acerca);

        jMenuBar1.add(jm_ayuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jdp_escritorio)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jdp_escritorio)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmi_gestionar_profesorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_gestionar_profesorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jmi_gestionar_profesorActionPerformed

    private void jm_ayudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jm_ayudaActionPerformed
        // TODO add your handling code here:
        Acerca acerca = new Acerca(this, true);
        this.jdp_escritorio.add(acerca);
        this.jdp_escritorio.moveToFront(acerca);
        acerca.setVisible(true);
    }//GEN-LAST:event_jm_ayudaActionPerformed

    private void jmi_ciclosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_ciclosActionPerformed
        // TODO add your handling code here:
        //tomamos el valor de la variable estática del JInternal
        String x = Ciclos.x;

        /*Comprobamos el valor de la variable de control, si es nula creamos
        una instancia, de lo contrario no hacemos nada*/
        if (x == null) {
            Ciclos ciclos = new Ciclos();
            this.jdp_escritorio.add(ciclos);
            this.jdp_escritorio.moveToFront(ciclos);//ponemos al frente de las demás ventanas.
            ciclos.show();
        }
    }//GEN-LAST:event_jmi_ciclosActionPerformed

    private void jmi_nuevo_profesorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_nuevo_profesorActionPerformed
        // Tomamos el valor de la variable estatica del jinternalframe
        String x = NuevoProfesor.x;
        
        /*Comprobamos el valor de la variable, si es nula creamos una instancia
        de lo contrario no hacemos nada*/
        if (x == null){
            NuevoProfesor profesor = new NuevoProfesor();
            this.jdp_escritorio.add(profesor);//agregamos al escritorio
            this.jdp_escritorio.moveToFront(profesor);
            profesor.show();//lo mostramos
        }
        
    }//GEN-LAST:event_jmi_nuevo_profesorActionPerformed

    private void jmi_nuevo_alumnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_nuevo_alumnoActionPerformed
        // Tomamos el valor de la variable estatica del jinternalframe
        String x = NuevoAlumno.x;
        
        /*Comprobamos el valor de la variable, si es nula creamos una instancia
        de lo contrario no hacemos nada*/
        if (x == null){
            NuevoAlumno alumno = new NuevoAlumno();
            this.jdp_escritorio.add(alumno);//agregamos al escritorio
            this.jdp_escritorio.moveToFront(alumno);
            alumno.show();//lo mostramos
        }
        
    }//GEN-LAST:event_jmi_nuevo_alumnoActionPerformed

    private void jmi_nuevo_tutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_nuevo_tutorActionPerformed
        // Tomamos el valor de la variable estatica del jinternalframe
        String x = NuevoTutor.x;
        
        /*Comprobamos el valor de la variable, si es nula creamos una instancia
        de lo contrario no hacemos nada*/
        if (x == null){
            NuevoTutor tutor = new NuevoTutor();
            this.jdp_escritorio.add(tutor);//agregamos al escritorio
            this.jdp_escritorio.moveToFront(tutor);
            tutor.show();//lo mostramos
        }
    }//GEN-LAST:event_jmi_nuevo_tutorActionPerformed

    private void jmi_empresasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_empresasActionPerformed
        // Tomamos el valor de la variable estatica del jinternalframe
        String x = Empresas.x;
        
        /*Comprobamos el valor de la variable, si es nula creamos una instancia
        de lo contrario no hacemos nada*/
        if (x == null){
            Empresas empresa = new Empresas();
            this.jdp_escritorio.add(empresa);//agregamos al escritorio
            this.jdp_escritorio.moveToFront(empresa);
            empresa.show();//lo mostramos
        }
    }//GEN-LAST:event_jmi_empresasActionPerformed

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
            java.util.logging.Logger.getLogger(Escritorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Escritorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Escritorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Escritorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Escritorio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JDesktopPane jdp_escritorio;
    private javax.swing.JMenu jm_alumnos;
    private javax.swing.JMenu jm_ayuda;
    private javax.swing.JMenu jm_empresas;
    private javax.swing.JMenu jm_profesores;
    private javax.swing.JMenuItem jmi_acerca;
    private javax.swing.JMenuItem jmi_ciclos;
    private javax.swing.JMenuItem jmi_empresas;
    private javax.swing.JMenuItem jmi_gestionar_alumno;
    private javax.swing.JMenuItem jmi_gestionar_profesor;
    private javax.swing.JMenuItem jmi_gestionar_tutor;
    private javax.swing.JMenuItem jmi_nuevo_alumno;
    private javax.swing.JMenuItem jmi_nuevo_profesor;
    private javax.swing.JMenuItem jmi_nuevo_tutor;
    // End of variables declaration//GEN-END:variables
}
