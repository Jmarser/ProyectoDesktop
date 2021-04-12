/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.internalFrame;

import conexion.ConMySQL;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import models.Login;
import models.Profesor;
import modelsDao.ManagerDaoImpl;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import utils.Constantes;
import utils.Generador;
import utils.Utilidades;

/**
 *
 * @author JMARSER
 */
public class GestionProfesores extends javax.swing.JInternalFrame {

    private static final long serialVersionUID = 1L;
    public static String x;

    private final Connection conn = ConMySQL.getConexion();

    private final ManagerDaoImpl gestor = new ManagerDaoImpl();
    private List<Profesor> listado = new ArrayList<>();

    public GestionProfesores() {
        initComponents();
        this.x = "x";
        initVentana();
        initBotones();
    }

    /*Iniciamos algunas caracteristicas de la ventana y algunos de los componentes*/
    private void initVentana() {
        this.setTitle("GESTIÓN DE PROFESORES");
        this.ocultar_pass.setVisible(false);
        llenarNiveles();
        llenarProfesores();
        AutoCompleteDecorator.decorate(jcb_profesores);//función autocompletar del JComboBox
        soloLetras();
        Utilidades.soloNumeros(this.jtf_longitud);//sólo permitimos números en este campo
    }

    /*Controlamos que en los campos que indiquemos sólo se puedan introducir
    letras*/
    private void soloLetras() {
        Utilidades.soloLetras(this.jtf_nombre);
        Utilidades.soloLetras(this.jtf_primerApellido);
        Utilidades.soloLetras(this.jtf_segundoApellido);
    }

    /*Dependiendo de la acción que queramos hacer, guardar o editar habilitaremos
    unos botones u otros.
    También se deshabilita el campo password para que no pueda ser modificado
    sólo por el usuario desde la app movil*/
    private void initBotones() {
        if (this.jcb_profesores.getSelectedIndex() != 0) {
            this.btn_modificar.setEnabled(true);
            this.btn_guardar.setEnabled(false);
            this.btn_generar.setEnabled(false);
            this.jpf_password.setEditable(false);
        } else {
            limpiarCampos();
            this.btn_modificar.setEnabled(false);
            this.btn_guardar.setEnabled(true);
            this.btn_generar.setEnabled(true);
            this.jpf_password.setEditable(true);
        }
    }

    /*Llenamos los niveles de seguridad para la generación de password aleatorios
    como éstos no van a variar usamos un String[] ya establecido*/
    private void llenarNiveles() {
        for (String nivel : Constantes.NIVELES) {
            this.jcb_nivelSeguridad.addItem(nivel);
        }
    }

    /*Obtenemops los profesores de la base de datos*/
    private void llenarProfesores() {
        /*Para evitar que se repitan los items, borramos los que contiene el JComboBox*/
        this.jcb_profesores.removeAllItems();

        listado = gestor.getProfesorDao().getAll();

        this.jcb_profesores.addItem("Seleccione un profesor para editar.");
        if (listado != null) {
            for (int i = 0; i < listado.size(); i++) {
                this.jcb_profesores.addItem(listado.get(i).toString());
            }
        }

    }

    private void limpiarCampos() {
        this.jcb_profesores.setSelectedIndex(0);
        this.jcb_nivelSeguridad.setSelectedIndex(0);
        this.jtf_nombre.setText("");
        this.jtf_primerApellido.setText("");
        this.jtf_segundoApellido.setText("");
        this.jtf_email.setText("");
        this.jtf_longitud.setText("");
        this.jpf_password.setText("");
        this.jcb_activo.setSelected(false);
    }

    /*Establecemos la longitud mínima dependiendo del nivel de seguridad seleccionado*/
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
                    JOptionPane.showMessageDialog(null, "Indique un nivel de seguridad.");
                    break;
            }
        } else {
            this.jtf_longitud.setText("");
        }
    }

    private void rellenarCampos() {
        int posicion = this.jcb_profesores.getSelectedIndex() - 1;
        this.jtf_nombre.setText(listado.get(posicion).getNombre());
        this.jtf_primerApellido.setText(listado.get(posicion).getPrimerApellido());
        this.jtf_segundoApellido.setText(listado.get(posicion).getSegundoApellido());
        this.jtf_email.setText(listado.get(posicion).getEmail());
        Login login = gestor.getLoginDao().getLoginByEmail(this.jtf_email.getText());
        this.jpf_password.setText(login.getPassword());
        this.jcb_activo.setSelected(login.isActivo());
    }

    private boolean validarCampos() {
        boolean valido = false;

        if (!this.jtf_nombre.getText().isEmpty()) {
            if (!this.jtf_primerApellido.getText().isEmpty()) {
                if (!this.jtf_segundoApellido.getText().isEmpty()) {
                    if (!this.jtf_email.getText().isEmpty()) {
                        if (Utilidades.validarCadena(this.jtf_email.getText().trim(), Constantes.PATTERN_CORREO)) {
                            if (this.jpf_password.getPassword().length < Constantes.LONG_LOW) {
                                valido = true;
                            } else {
                                this.jpf_password.requestFocus();
                            }
                        } else {
                            this.jtf_email.requestFocus();
                        }
                    } else {
                        this.jtf_email.requestFocus();
                    }
                } else {
                    this.jtf_segundoApellido.requestFocus();
                }
            } else {
                this.jtf_primerApellido.requestFocus();
            }
        } else {
            this.jtf_nombre.requestFocus();
        }

        return valido;
    }

    /*Solicitamos guardar los datos del profesor en la tabla profesores y en la 
    tabla login, por lo que vamos a gestionar las transacciones en la BBDD de
    manera manual*/
    private void guardarProfesor() {

        try {

            /*Desactivamos el autocommit para poder gestionar las consultas por
            transacciones*/
            conn.setAutoCommit(false);

            if (gestor.getLoginDao().insert(obtenerLogin())) {
                if (gestor.getProfesorDao().insert(obtenerProfesor())) {
                    JOptionPane.showMessageDialog(null, "Profesor guardado correctamente.");
                } else {
                    /*Como ha acurrido un error al guardar el profesor en la tabla
                    profesores pero no en la tabla login, hacemos un rollback para
                    devolver la base de datos al estado anterior.*/
                    conn.rollback();
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al guardar el profesor");
                }
            } else {
                JOptionPane.showMessageDialog(null, "El profesor ya se encuentra registrado");
            }

            //Los dos insert se han realizado correctamente
            conn.commit();

        } catch (SQLException ex) {
            Logger.getLogger(GestionProfesores.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            /*Nos aseguramos activar el autocommit*/
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(GestionProfesores.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /*Solicitamos modificar los datos del profesor en la BBDD.
    como en el caso de la inserción de datos, aqui también debemos actuar sobre
    dos tablas con lo que vamos a gestionar manualmente las transacciones a la
    BBDD*/
    private void editarProfesor() {
        int indice = this.jcb_profesores.getSelectedIndex() - 1;

        Login login = obtenerLogin();
        login.setId(gestor.getLoginDao().getIdByEmail(listado.get(indice).getEmail()));

        Profesor profesor = obtenerProfesor();
        profesor.setId(listado.get(indice).getId());

        try {

            /*Desactivamos la función autocommit de la base de datos para gestionar
            las transacciones manualmente.*/
            conn.setAutoCommit(false);

            if (gestor.getLoginDao().update(login)) {
                if (gestor.getProfesorDao().update(profesor)) {
                    JOptionPane.showMessageDialog(null, "Profesor modificado correctamente.");
                } else {
                    /*se ha realizado la actualización de la tabla login pero no de
                la tabla de profesores, por lo que hacemos un rollback para 
                devolver la BBDD al estado anterior*/
                    conn.rollback();
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al actualizar el profesor.");
                }

                //Las dos actualizaciones se han realizado correctamente
                conn.commit();
            } else {
                JOptionPane.showMessageDialog(null, "El profesor no ha podido ser modificado");
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionProfesores.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            /*Nos aseguramos de activar el autocommit*/
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(GestionProfesores.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Profesor obtenerProfesor() {
        Profesor profe = new Profesor();

        profe.setNombre(this.jtf_nombre.getText());
        profe.setPrimerApellido(this.jtf_primerApellido.getText());
        profe.setSegundoApellido(this.jtf_segundoApellido.getText());
        profe.setEmail(this.jtf_email.getText().trim());

        return profe;
    }

    private Login obtenerLogin() {
        Login login = new Login();

        login.setEmail(this.jtf_email.getText().trim());
        login.setPassword(String.valueOf(this.jpf_password.getPassword()));
        login.setRol(Constantes.PROFESOR);
        login.setActivo(this.jcb_activo.isSelected());

        return login;
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
        jcb_profesores = new javax.swing.JComboBox<>();
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
        jcb_activo = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jcb_nivelSeguridad = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jtf_longitud = new javax.swing.JTextField();
        btn_generar = new javax.swing.JButton();
        jpf_password = new javax.swing.JPasswordField();
        mostrar_pass = new javax.swing.JLabel();
        ocultar_pass = new javax.swing.JLabel();

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

        jcb_profesores.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcb_profesoresItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addComponent(jcb_profesores, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(272, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jcb_profesores, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        btn_nuevo.setText("NUEVO");
        btn_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevoActionPerformed(evt);
            }
        });

        btn_modificar.setText("MODIFICAR");
        btn_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modificarActionPerformed(evt);
            }
        });

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(btn_cerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.LINE_END);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Datos profesor"));

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

        jcb_activo.setText("Activo");

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
                        .addGap(58, 58, 58))))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mostrar_pass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/pass-visible.png"))); // NOI18N
        mostrar_pass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                mostrar_passMousePressed(evt);
            }
        });

        ocultar_pass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/pass-not-visible.png"))); // NOI18N
        ocultar_pass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ocultar_passMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jcb_activo)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtf_nombre)
                            .addComponent(jtf_primerApellido)
                            .addComponent(jtf_email)
                            .addComponent(jtf_segundoApellido)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jpf_password, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ocultar_pass)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(mostrar_pass)))))
                .addGap(25, 25, 25)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
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
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jpf_password, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(mostrar_pass)))
                            .addComponent(ocultar_pass)))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jcb_activo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(12, Short.MAX_VALUE))
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
                JOptionPane.showMessageDialog(null, "Debe indicar una longitud válida.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe indicar un nivel de seguridad.");
        }
    }//GEN-LAST:event_btn_generarActionPerformed

    private void jcb_profesoresItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcb_profesoresItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            initBotones();
            if (this.jcb_profesores.getSelectedIndex() != 0) {
                rellenarCampos();
            }
        }
    }//GEN-LAST:event_jcb_profesoresItemStateChanged

    private void btn_limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limpiarActionPerformed

        limpiarCampos();
    }//GEN-LAST:event_btn_limpiarActionPerformed

    private void btn_cerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cerrarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btn_cerrarActionPerformed

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        if (validarCampos()) {
            guardarProfesor();
            limpiarCampos();
            llenarProfesores();
        } else {
            JOptionPane.showMessageDialog(null, "Faltan campos por rellenar.");
        }
    }//GEN-LAST:event_btn_guardarActionPerformed

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
        initBotones();
    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void mostrar_passMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mostrar_passMousePressed
        // TODO add your handling code here:
        this.ocultar_pass.setVisible(true);
        this.mostrar_pass.setVisible(false);
        this.jpf_password.setEchoChar((char) 0);
    }//GEN-LAST:event_mostrar_passMousePressed

    private void ocultar_passMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ocultar_passMousePressed
        // TODO add your handling code here:
        this.ocultar_pass.setVisible(false);
        this.mostrar_pass.setVisible(true);
        this.jpf_password.setEchoChar('*');
    }//GEN-LAST:event_ocultar_passMousePressed

    private void btn_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarActionPerformed
        // TODO add your handling code here:
        if (validarCampos()) {
            editarProfesor();
            limpiarCampos();
            llenarProfesores();
        } else {
            JOptionPane.showMessageDialog(null, "Faltan campos por rellenar.");
        }
    }//GEN-LAST:event_btn_modificarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cerrar;
    private javax.swing.JButton btn_generar;
    private javax.swing.JButton btn_guardar;
    private javax.swing.JButton btn_limpiar;
    private javax.swing.JButton btn_modificar;
    private javax.swing.JButton btn_nuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JCheckBox jcb_activo;
    private javax.swing.JComboBox<String> jcb_nivelSeguridad;
    private javax.swing.JComboBox<String> jcb_profesores;
    private javax.swing.JPasswordField jpf_password;
    private javax.swing.JTextField jtf_email;
    private javax.swing.JTextField jtf_longitud;
    private javax.swing.JTextField jtf_nombre;
    private javax.swing.JTextField jtf_primerApellido;
    private javax.swing.JTextField jtf_segundoApellido;
    private javax.swing.JLabel mostrar_pass;
    private javax.swing.JLabel ocultar_pass;
    // End of variables declaration//GEN-END:variables

}
