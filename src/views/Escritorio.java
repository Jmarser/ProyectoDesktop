/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import conexion.ConMySQL;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import utils.Constantes;
import views.dialogos.Acerca;
import views.internalFrame.Ciclos;
import views.internalFrame.Empresas;
import views.internalFrame.GestionAlumnos;
import views.internalFrame.GestionProfesores;
import views.internalFrame.GestionTutores;

/**
 *
 * @author Jmarser
 */
public class Escritorio extends javax.swing.JFrame {

    /*obtenemos la conexión a la base de datos, con lo que nos aseguraremos al 
    cerrar el programa que cerramos la conexión con la base de datos.*/
    private Connection conn = ConMySQL.getConexion();

    //variables para la ayuda
    private HelpSet helpSet;
    private HelpBroker hb;
            
    
    public Escritorio() {
        initComponents();
        initVentana();
    }

    private void initVentana() {
        this.setTitle("Sistema de gestión de FCT");//titulo de la ventana
        this.setExtendedState(MAXIMIZED_BOTH);//a pantalla completa
        this.setLocationRelativeTo(null);//centrado en la pantalla
        initBotones();
        addIcono();
        cerrarVentana();
        cargarAyuda();
    }

    /*Establecemos el icono de la aplicación*/
    private void addIcono() {
        URL url = getClass().getResource(Constantes.RUTA_ICON_DESKTOP);
        ImageIcon icono = new ImageIcon(url);
        this.setIconImage(icono.getImage());
    }

    /*Dependiendo de que tengamos conexión a la base de datos o no al iniciar
    la aplicación lo botones de acceso a la gestión de la base de datos estarán
    inhabilitados o no*/
    private void initBotones() {
        if (conn == null) {
            this.jm_profesores.setEnabled(false);
            this.jm_alumnos.setEnabled(false);
            this.jm_empresas.setEnabled(false);
        } else {
            this.jm_profesores.setEnabled(true);
            this.jm_alumnos.setEnabled(true);
            this.jm_empresas.setEnabled(true);
        }
    }

    /*Modificamos el comportamiento de la acción de cerrar el programa*/
    private void cerrarVentana() {
        try {
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent we) {
                    salir();
                }
            });
            this.setVisible(true);
        } catch (Exception e) {

        }
    }

    /*Hacemos que el programa antes de salir pida confirmación y en el caso de 
    ser afirmativa nos aseguramos de cerrar la conexión con la base de datos*/
    private void salir() {
        int opcion = JOptionPane.showConfirmDialog(this, "¿Seguro que desea salir de la palicación?", "¿Salir?", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            /*antes de salir cerramos la conexión con la BDD, si la conexión esta 
            establecida, en el caso de no estar establecida la conexión, nos 
            saltamos la desconexión para evitar la excepción.*/

            if (conn != null) {
                ConMySQL.desconectar();
                System.out.println("Conexión con la base de datos cerrada.");
            }
            System.exit(0);
        }
    }

    /* Método con el que cargamos la opción ayuda JavaHelp para esta aplicacion.
     éste método no se llama desde un botón u otro método, sino que se carga 
     directamente en el inicio de la ventana*/
    private void cargarAyuda(){
        try{
            //ponemos el foco sobre la ventana
            this.getContentPane().requestFocus();
            //cargamos el fichero help con una ruta relativa
            File fichero = new File("help/help_set.hs");
            URL hsURL = fichero.toURI().toURL();
            //iniciamos el helpset
            helpSet = new HelpSet(getClass().getClassLoader(), hsURL);
            //iniciamos helpBroker
            hb = helpSet.createHelpBroker();
            //establecemos un tamaño para la pantalla de ayuda
            hb.setSize(new Dimension(1000, 900));
            //establecemos que al pulsar la tecla F1 se abra la ventana de ayuda
            hb.enableHelpKey(this.getContentPane(), "escritorio", helpSet);
            //asociamos que aparezca la ayuda al pulsar el boton de la barra de menu
            hb.enableHelpOnButton(this.jmi_ayuda, "escritorio", helpSet);
        } catch (MalformedURLException | HelpSetException ex) {
            Logger.getLogger(Escritorio.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error al cargar la ayuda");
        }
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
        jmi_gestionar_profesor = new javax.swing.JMenuItem();
        jmi_ciclos = new javax.swing.JMenuItem();
        jm_alumnos = new javax.swing.JMenu();
        jmi_gestionar_alumno = new javax.swing.JMenuItem();
        jm_empresas = new javax.swing.JMenu();
        jmi_gestionar_tutor = new javax.swing.JMenuItem();
        jmi_empresas = new javax.swing.JMenuItem();
        jm_herramientas = new javax.swing.JMenu();
        jmi_reconectar = new javax.swing.JMenuItem();
        jm_ayuda = new javax.swing.JMenu();
        jmi_acerca = new javax.swing.JMenuItem();
        jmi_ayuda = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jdp_escritorioLayout = new javax.swing.GroupLayout(jdp_escritorio);
        jdp_escritorio.setLayout(jdp_escritorioLayout);
        jdp_escritorioLayout.setHorizontalGroup(
            jdp_escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 817, Short.MAX_VALUE)
        );
        jdp_escritorioLayout.setVerticalGroup(
            jdp_escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 453, Short.MAX_VALUE)
        );

        jm_profesores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/teacher.png"))); // NOI18N
        jm_profesores.setText("PROFESORES");

        jmi_gestionar_profesor.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jmi_gestionar_profesor.setText("Gestionar Profesor");
        jmi_gestionar_profesor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_gestionar_profesorActionPerformed(evt);
            }
        });
        jm_profesores.add(jmi_gestionar_profesor);

        jmi_ciclos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        jmi_ciclos.setText("Gestionar Ciclos");
        jmi_ciclos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_ciclosActionPerformed(evt);
            }
        });
        jm_profesores.add(jmi_ciclos);

        jMenuBar1.add(jm_profesores);

        jm_alumnos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/student.png"))); // NOI18N
        jm_alumnos.setText("ALUMNOS");

        jmi_gestionar_alumno.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jmi_gestionar_alumno.setText("Gestionar Alumno");
        jmi_gestionar_alumno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_gestionar_alumnoActionPerformed(evt);
            }
        });
        jm_alumnos.add(jmi_gestionar_alumno);

        jMenuBar1.add(jm_alumnos);

        jm_empresas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/empresa.png"))); // NOI18N
        jm_empresas.setText("EMPRESAS");

        jmi_gestionar_tutor.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        jmi_gestionar_tutor.setText("Gestionar Tutor");
        jmi_gestionar_tutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_gestionar_tutorActionPerformed(evt);
            }
        });
        jm_empresas.add(jmi_gestionar_tutor);

        jmi_empresas.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jmi_empresas.setText("Gestionar Empresas");
        jmi_empresas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_empresasActionPerformed(evt);
            }
        });
        jm_empresas.add(jmi_empresas);

        jMenuBar1.add(jm_empresas);

        jm_herramientas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/tools.png"))); // NOI18N
        jm_herramientas.setText("HERRAMIENTAS");

        jmi_reconectar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        jmi_reconectar.setText("Reconectar");
        jmi_reconectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_reconectarActionPerformed(evt);
            }
        });
        jm_herramientas.add(jmi_reconectar);

        jMenuBar1.add(jm_herramientas);

        jm_ayuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/help.png"))); // NOI18N
        jm_ayuda.setText("AYUDA");
        jm_ayuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jm_ayudaActionPerformed(evt);
            }
        });

        jmi_acerca.setText("Acerca de...");
        jmi_acerca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_acercaActionPerformed(evt);
            }
        });
        jm_ayuda.add(jmi_acerca);

        jmi_ayuda.setText("Ayuda");
        jmi_ayuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_ayudaActionPerformed(evt);
            }
        });
        jm_ayuda.add(jmi_ayuda);

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
        // tomamos el valor de la variable estática del jinternal
        String x = GestionProfesores.x;

        /*Comprobamos el valor de la variable de control, si es nula creamos una 
        instancia, de lo contraeio no hacemos nada*/
        if (x == null) {
            GestionProfesores profesor = new GestionProfesores();
            this.jdp_escritorio.add(profesor);
            this.jdp_escritorio.moveToFront(profesor);
            profesor.show();
        }

    }//GEN-LAST:event_jmi_gestionar_profesorActionPerformed

    private void jm_ayudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jm_ayudaActionPerformed

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

    private void jmi_empresasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_empresasActionPerformed
        // Tomamos el valor de la variable estatica del jinternalframe
        String x = Empresas.x;

        /*Comprobamos el valor de la variable, si es nula creamos una instancia
        de lo contrario no hacemos nada*/
        if (x == null) {
            Empresas empresa = new Empresas();
            this.jdp_escritorio.add(empresa);//agregamos al escritorio
            this.jdp_escritorio.moveToFront(empresa);
            empresa.show();//lo mostramos
        }
    }//GEN-LAST:event_jmi_empresasActionPerformed

    private void jmi_gestionar_alumnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_gestionar_alumnoActionPerformed
        // Tomamos el valor de la variable estatica del jinternalframe
        String x = GestionAlumnos.x;

        /*Comprobamos el valor de la variable, si es nula creamos una instancia
        de lo contrario no hacemos nada*/
        if (x == null) {
            GestionAlumnos alumno = new GestionAlumnos();
            this.jdp_escritorio.add(alumno);//agregamos al escritorio
            this.jdp_escritorio.moveToFront(alumno);
            alumno.show();//lo mostramos
        }
    }//GEN-LAST:event_jmi_gestionar_alumnoActionPerformed

    private void jmi_gestionar_tutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_gestionar_tutorActionPerformed
        // Tomamos el valor de la variable estatica del jinternalframe
        String x = GestionTutores.x;

        /*Comprobamos el valor de la variable, si es nula creamos una instancia
        de lo contrario no hacemos nada*/
        if (x == null) {
            GestionTutores tutor = new GestionTutores();
            this.jdp_escritorio.add(tutor);//agregamos al escritorio
            this.jdp_escritorio.moveToFront(tutor);
            tutor.show();//lo mostramos
        }
    }//GEN-LAST:event_jmi_gestionar_tutorActionPerformed

    private void jmi_acercaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_acercaActionPerformed
        // TODO add your handling code here:
        new Acerca(this, false).setVisible(true);
    }//GEN-LAST:event_jmi_acercaActionPerformed

    private void jmi_reconectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_reconectarActionPerformed
        /* en el caso de no tener conexión a la base de datos, podemos volvera 
        intentarlo sin tener que cerrar el programa*/
        if (conn == null) {
            conn = ConMySQL.getConexion();
            initBotones();
        }
    }//GEN-LAST:event_jmi_reconectarActionPerformed

    private void jmi_ayudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_ayudaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jmi_ayudaActionPerformed

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
    private javax.swing.JMenu jm_herramientas;
    private javax.swing.JMenu jm_profesores;
    private javax.swing.JMenuItem jmi_acerca;
    private javax.swing.JMenuItem jmi_ayuda;
    private javax.swing.JMenuItem jmi_ciclos;
    private javax.swing.JMenuItem jmi_empresas;
    private javax.swing.JMenuItem jmi_gestionar_alumno;
    private javax.swing.JMenuItem jmi_gestionar_profesor;
    private javax.swing.JMenuItem jmi_gestionar_tutor;
    private javax.swing.JMenuItem jmi_reconectar;
    // End of variables declaration//GEN-END:variables
}
