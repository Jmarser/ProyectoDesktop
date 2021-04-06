/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.internalFrame;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import models.Empresa;
import models.Login;
import models.Tutor;
import modelsDao.ManagerDaoImpl;
import utils.Constantes;
import utils.Generador;
import utils.Utilidades;

/**
 *
 * @author JMARSER
 */
public class GestionTutores extends javax.swing.JInternalFrame {

    private static final long serialVersionUID = 1L;
    public static String x;

    private ManagerDaoImpl gestor = new ManagerDaoImpl();
    private List<Tutor> tutores = new ArrayList<>();
    private List<Empresa> empresas = new ArrayList<>();

    public GestionTutores() {
        initComponents();
        this.x = "x";
        initVentana();
        initBotones();
    }

    private void initVentana() {
        this.setTitle("GESTIÓN DE TUTORES");

        llenarNiveles();
        llenarTutores();
        llenarEmpresas();
    }

    private void initBotones() {
        if (this.jcb_tutores.getSelectedIndex() != 0) {
            this.btn_modificar.setEnabled(true);
            this.btn_guardar.setEnabled(false);
            this.btn_generar.setEnabled(false);
            this.jpf_password.setEditable(false);
        } else {
            //limpiarCampos();
            this.btn_modificar.setEnabled(false);
            this.btn_guardar.setEnabled(true);
            this.btn_generar.setEnabled(true);
            this.jpf_password.setEditable(true);
        }
    }

    private void llenarNiveles() {
        for (String nivel : Constantes.NIVELES) {
            this.jcb_nivelSeguridad.addItem(nivel);
        }
    }

    private void llenarTutores() {
        this.jcb_tutores.removeAllItems();

        tutores = gestor.getTutorDao().getAll();

        this.jcb_tutores.addItem("Seleccione un tutor para editar");
        if (tutores != null) {
            for (int i = 0; i < tutores.size(); i++) {
                this.jcb_tutores.addItem(tutores.get(i).toString());
            }
        }
    }

    private void llenarEmpresas() {
        this.jcb_empresas.removeAllItems();

        this.jcb_empresas.addItem("Seleccione una empresa");
        empresas = gestor.getEmpresaDao().getAll();

        if (empresas != null) {
            for (int i = 0; i < empresas.size(); i++) {
                this.jcb_empresas.addItem(empresas.get(i).getNombre());
            }
        }
    }

    private void limpiarCampos() {
        this.jcb_tutores.setSelectedIndex(0);
        this.jcb_nivelSeguridad.setSelectedIndex(0);
        this.jcb_empresas.setSelectedIndex(-1);
        this.jtf_nombre.setText("");
        this.jtf_primerApellido.setText("");
        this.jtf_segundoApellido.setText("");
        this.jtf_email.setText("");
        this.jtf_longitud.setText("");
        this.jpf_password.setText("");
        this.checkBox_activo.setSelected(false);
    }

    private void longitudMinima() {
        if (this.jcb_nivelSeguridad.getSelectedIndex() != 0) {
            String nivel = this.jcb_nivelSeguridad.getSelectedItem().toString();
            switch (nivel) {
                case Constantes.BAJO:
                    this.jtf_longitud.setText(String.valueOf(Constantes.LONG_LOW));
                    break;
                case Constantes.MEDIO:
                    this.jtf_longitud.setText(String.valueOf(Constantes.LONG_MEDIUM));
                    break;
                case Constantes.ALTO:
                    this.jtf_longitud.setText(String.valueOf(Constantes.LONG_HIGH));
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Indique un nivel de seguridad válido.");
                    break;
            }
        } else {
            this.jtf_longitud.setText("");
        }
    }

    private void rellenarCampos() {
        int posicion = this.jcb_tutores.getSelectedIndex() - 1;
        this.jtf_nombre.setText(tutores.get(posicion).getNombre());
        this.jtf_primerApellido.setText(tutores.get(posicion).getPrimerApellido());
        this.jtf_segundoApellido.setText(tutores.get(posicion).getSegundoApellido());
        this.jtf_email.setText(tutores.get(posicion).getEmail());

        Login login = gestor.getLoginDao().getLoginByEmail(this.jtf_email.getText().trim());
        this.jpf_password.setText(login.getPassword());
        this.checkBox_activo.setSelected(login.isActivo());

        for (int i = 0; i < empresas.size(); i++) {
            if (empresas.get(i).getNombre().equalsIgnoreCase(tutores.get(posicion).getEmpresa())) {
                this.jcb_empresas.setSelectedIndex(i + 1);
            }
        }
    }

    private boolean validarCampos() {
        boolean valido = false;

        if (!this.jtf_nombre.getText().isEmpty()) {
            if (!this.jtf_primerApellido.getText().isEmpty()) {
                if (!this.jtf_segundoApellido.getText().isEmpty()) {
                    if (!this.jtf_email.getText().isEmpty()) {
                        if (this.jcb_empresas.getSelectedIndex() != 0) {
                            if (Utilidades.validarCorreo(this.jtf_email.getText())) {
                                if (this.checkBox_activo.isSelected()) {
                                    valido = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return valido;
    }
    
    private void guardarTutor(){
        Tutor tutor = new Tutor();
        Login login = new Login();
        
        String email = this.jtf_email.getText().trim();
        
        tutor.setNombre(this.jtf_nombre.getText());
        tutor.setPrimerApellido(this.jtf_primerApellido.getText());
        tutor.setSegundoApellido(this.jtf_segundoApellido.getText());
        tutor.setEmail(email);
        tutor.setEmpresa(this.jcb_empresas.getSelectedItem().toString());
        
        login.setEmail(email);
        login.setPassword(String.valueOf(this.jpf_password.getPassword()));
        login.setActivo(this.checkBox_activo.isSelected());
        login.setRol(Constantes.TUTOR);
        
        gestor.getLoginDao().insert(login);
        gestor.getTutorDao().insert(tutor);
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
        jcb_tutores = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        btn_nuevo = new javax.swing.JButton();
        btn_modificar = new javax.swing.JButton();
        btn_guardar = new javax.swing.JButton();
        btn_limpiar = new javax.swing.JButton();
        btn_cerrar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jtf_nombre = new javax.swing.JTextField();
        jtf_primerApellido = new javax.swing.JTextField();
        jtf_segundoApellido = new javax.swing.JTextField();
        jtf_email = new javax.swing.JTextField();
        checkBox_activo = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jcb_nivelSeguridad = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jtf_longitud = new javax.swing.JTextField();
        btn_generar = new javax.swing.JButton();
        jpf_password = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        jcb_empresas = new javax.swing.JComboBox<>();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jcb_tutores.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcb_tutoresItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addComponent(jcb_tutores, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(272, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jcb_tutores, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        btn_nuevo.setText("NUEVO");

        btn_modificar.setText("MODIFICAR");

        btn_guardar.setText("GUARDAR");
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });

        btn_limpiar.setText("LIMPIAR");
        btn_limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limpiarActionPerformed(evt);
            }
        });

        btn_cerrar.setText("CERRAR");
        btn_cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_modificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_cerrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_limpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_guardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_nuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btn_nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                .addComponent(btn_cerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.LINE_END);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Datos tutor"));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Nombre:");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("1º Apellido:");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Email:");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("2º Apellido:");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Password:");

        checkBox_activo.setText("Activo");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Generar Password", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jcb_nivelSeguridad.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcb_nivelSeguridadItemStateChanged(evt);
            }
        });

        jLabel6.setText("Longitud password:");

        jtf_longitud.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btn_generar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/random.png"))); // NOI18N
        btn_generar.setText("GENERAR");
        btn_generar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_generar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btn_generar.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        btn_generar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_generarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtf_longitud, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jcb_nivelSeguridad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(btn_generar)
                        .addGap(60, 60, 60))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcb_nivelSeguridad, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtf_longitud, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btn_generar)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Empresa:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(checkBox_activo)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtf_nombre)
                            .addComponent(jtf_primerApellido)
                            .addComponent(jtf_email)
                            .addComponent(jtf_segundoApellido)
                            .addComponent(jpf_password, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(jcb_empresas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtf_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtf_primerApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtf_segundoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtf_email, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jpf_password, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcb_empresas, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(checkBox_activo)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        this.x = null;
    }//GEN-LAST:event_formInternalFrameClosing

    private void jcb_nivelSeguridadItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcb_nivelSeguridadItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            longitudMinima();
        }
    }//GEN-LAST:event_jcb_nivelSeguridadItemStateChanged

    private void btn_generarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_generarActionPerformed
        if (this.jcb_nivelSeguridad.getSelectedIndex() != 0) {
            if (!this.jtf_longitud.getText().equals("")) {
                this.jpf_password.setText(new Generador().getClave(
                        this.jcb_nivelSeguridad.getSelectedItem().toString(),
                        Integer.parseInt(this.jtf_longitud.getText())));
            } else {
                JOptionPane.showMessageDialog(null, "Indique una longitud válida.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Indique un nivel de seguridad válido.");
        }
    }//GEN-LAST:event_btn_generarActionPerformed

    private void jcb_tutoresItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcb_tutoresItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            initBotones();
            if (this.jcb_tutores.getSelectedIndex() != 0) {
                rellenarCampos();
            } else {
                limpiarCampos();
            }
        }
    }//GEN-LAST:event_jcb_tutoresItemStateChanged

    private void btn_limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btn_limpiarActionPerformed

    private void btn_cerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cerrarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btn_cerrarActionPerformed

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        // TODO add your handling code here:
        if(validarCampos()){
            guardarTutor();
            limpiarCampos();
            llenarTutores();
        }else{
            JOptionPane.showMessageDialog(null, "Error al guardar el tutor");
        }
    }//GEN-LAST:event_btn_guardarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cerrar;
    private javax.swing.JButton btn_generar;
    private javax.swing.JButton btn_guardar;
    private javax.swing.JButton btn_limpiar;
    private javax.swing.JButton btn_modificar;
    private javax.swing.JButton btn_nuevo;
    private javax.swing.JCheckBox checkBox_activo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JComboBox<String> jcb_empresas;
    private javax.swing.JComboBox<String> jcb_nivelSeguridad;
    private javax.swing.JComboBox<String> jcb_tutores;
    private javax.swing.JPasswordField jpf_password;
    private javax.swing.JTextField jtf_email;
    private javax.swing.JTextField jtf_longitud;
    private javax.swing.JTextField jtf_nombre;
    private javax.swing.JTextField jtf_primerApellido;
    private javax.swing.JTextField jtf_segundoApellido;
    // End of variables declaration//GEN-END:variables
}
