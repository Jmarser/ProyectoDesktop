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
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import models.Alumno;
import models.Ciclo;
import models.Login;
import models.Profesor;
import models.Tutor;
import modelsDao.ManagerDaoImpl;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import utils.Constantes;
import utils.Generador;
import utils.Utilidades;

/**
 *
 * @author JMARSER
 */
public class GestionAlumnos extends javax.swing.JInternalFrame {

    private static final long serialVersionUID = 1L;
    public static String x;
    private final Connection conn = ConMySQL.getConexion();

    /*atributo que gestiona la conexión de la base de datos y los métodos de 
    consulta de los diferentes modelos*/
    private final ManagerDaoImpl gestor = new ManagerDaoImpl();
    private List<Alumno> alumnos = new ArrayList<>();
    private List<Profesor> profesores = new ArrayList<>();
    private List<Tutor> tutores = new ArrayList<>();
    private List<Ciclo> ciclos = new ArrayList<>();

    public GestionAlumnos() {
        initComponents();
        this.x = "x";
        initVentana();
        initBotones();
    }

    /*Iniciamos algunas características de la ventana y algunos de los componentes*/
    private void initVentana() {
        this.setTitle("GESTIÓN DE ALUMNOS");
        this.ocultar_pass.setVisible(false);
        llenarNiveles();
        llenarAlumnos();
        llenarCiclos();
        llenarProfesores();
        llenarTutores();
        autoCompletarJCB();
        soloLetras();
        Utilidades.soloNumeros(jtf_longitud);//sólo permitimos números en este campo
    }
    
    /*Controlamos que en los campos que indiquemos sólo se puedan introducir
    letras*/
    private void soloLetras(){
        Utilidades.soloLetras(this.jtf_nombre);
        Utilidades.soloLetras(this.jtf_primerApellido);
        Utilidades.soloLetras(this.jtf_segundoApellido);
    }

    /*Incluimos la función de autocompletar el texto en los JComboBox para el caso
    de que tengamos muchos registros*/
    private void autoCompletarJCB() {
        AutoCompleteDecorator.decorate(jcb_alumnos);
        AutoCompleteDecorator.decorate(jcb_ciclos);
        AutoCompleteDecorator.decorate(jcb_profesores);
        AutoCompleteDecorator.decorate(jcb_tutores);
    }

    /*Método con el que habilitamos o deshabilitamos botones dependiendo de la 
    acción que queramos hacer, guardar o editar.
    deshabilitamos el cuadro de password para que sólo el usuario pueda cambiar
    el password.*/
    private void initBotones() {
        if (this.jcb_alumnos.getSelectedIndex() != 0) {
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

    /*Llenamos los niveles de seguridad para la generación de password aleatorios
    como éstos no van a variar usamos un String[] ya establecido*/
    private void llenarNiveles() {
        for (String nivel : Constantes.NIVELES) {
            this.jcb_nivelSeguridad.addItem(nivel);
        }
    }

    /*Obtenemos los alumnos que hay en la base de datos*/
    private void llenarAlumnos() {
        /*Para evitar que se agregen repetidos, borramos los que ya haya en el 
        JComboBox*/
        this.jcb_alumnos.removeAllItems();

        alumnos = gestor.getAlumnoDao().getAll();

        this.jcb_alumnos.addItem("Seleccione un alumno para editar");
        if (alumnos != null) {
            for (int i = 0; i < alumnos.size(); i++) {
                this.jcb_alumnos.addItem(alumnos.get(i).toString());
            }
        }
    }

    /*Obtenemos los diferentes ciclos que hay guardados en la base de datos*/
    private void llenarCiclos() {
        //Eliminamos los Items que haya en el JComboBox
        this.jcb_ciclos.removeAllItems();

        ciclos = gestor.getCicloDao().getAll();

        this.jcb_ciclos.addItem("Seleccione el ciclo del alumno...");

        if (ciclos != null) {
            for (int i = 0; i < ciclos.size(); i++) {
                this.jcb_ciclos.addItem(ciclos.get(i).getNombre());
            }
        }

    }

    /*Obtenemos los diferentes tutores que haya en la base de datos*/
    private void llenarTutores() {
        //Eliminamos los Items que haya en el JComboBox
        this.jcb_tutores.removeAllItems();

        tutores = gestor.getTutorDao().getAll();

        this.jcb_tutores.addItem("Seleccione un tutor para editar");
        if (tutores != null) {
            for (int i = 0; i < tutores.size(); i++) {
                this.jcb_tutores.addItem(tutores.get(i).toString());
            }
        }
    }

    /*Obtenemos los diferentes profesores que haya en la base de datos*/
    private void llenarProfesores() {
        //Eliminamos los Items que haya en el JComboBox
        this.jcb_profesores.removeAllItems();

        profesores = gestor.getProfesorDao().getAll();

        this.jcb_profesores.addItem("Seleccione un profesor para editar.");
        if (profesores != null) {
            for (int i = 0; i < profesores.size(); i++) {
                this.jcb_profesores.addItem(profesores.get(i).toString());
            }
        }
    }

    /*Establecemos la longitud mínima del password dependiendo del nivel de seguridad*/
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
                    JOptionPane.showMessageDialog(null, "Indique un nivel válido");
                    break;
            }
        } else {
            this.jtf_longitud.setText("");
        }
    }

    private void limpiarCampos() {
        this.jcb_alumnos.setSelectedIndex(0);
        this.jcb_nivelSeguridad.setSelectedIndex(0);
        this.jcb_ciclos.setSelectedIndex(0);
        this.jcb_profesores.setSelectedIndex(0);
        this.jcb_tutores.setSelectedIndex(0);
        this.jtf_nombre.setText("");
        this.jtf_primerApellido.setText("");
        this.jtf_segundoApellido.setText("");
        this.jtf_email.setText("");
        this.jpf_password.setText("");
        this.checkBox_activo.setSelected(false);
    }

    /*Al seleccionar un nombre de alumno del JComboBox, mostramos sus datos en 
    los campos correspondientes.*/
    private void rellenarCampos() {
        int posicion = this.jcb_alumnos.getSelectedIndex() - 1;
        this.jtf_nombre.setText(alumnos.get(posicion).getNombre());
        this.jtf_primerApellido.setText(alumnos.get(posicion).getPrimerApellido());
        this.jtf_segundoApellido.setText(alumnos.get(posicion).getSegundoApellido());
        this.jtf_email.setText(alumnos.get(posicion).getEmail());

        Login login = gestor.getLoginDao().getLoginByEmail(alumnos.get(posicion).getEmail());
        this.jpf_password.setText(login.getPassword());
        this.checkBox_activo.setSelected(login.isActivo());

        for (int i = 0; i < profesores.size(); i++) {
            //La comprobacion de dos Long se hace con el siguiente método
            if (Objects.equals(profesores.get(i).getId(), alumnos.get(posicion).getProfesorID())) {
                this.jcb_profesores.setSelectedIndex(i + 1);
            }
        }

        for (int i = 0; i < tutores.size(); i++) {
            if (Objects.equals(tutores.get(i).getId(), alumnos.get(posicion).getTutorID())) {
                this.jcb_tutores.setSelectedIndex(i + 1);
            }
        }

        for (int i = 0; i < ciclos.size(); i++) {
            if (ciclos.get(i).getNombre().equals(alumnos.get(posicion).getCiclo())) {
                this.jcb_ciclos.setSelectedIndex(i + 1);
            }
        }
    }

    private boolean validarCampos() {
        boolean valido = false;

        if (!this.jtf_nombre.getText().isEmpty()) {
            if (!this.jtf_primerApellido.getText().isEmpty()) {
                if (!this.jtf_segundoApellido.getText().isEmpty()) {
                    if (!this.jtf_email.getText().isEmpty()) {
                        if (Utilidades.validarCorreo(this.jtf_email.getText().trim())) {
                            if (this.jpf_password.getPassword().length != 0) {
                                if (this.jcb_ciclos.getSelectedIndex() != 0) {
                                    if (this.jcb_profesores.getSelectedIndex() != 0) {
                                        if (this.jcb_tutores.getSelectedIndex() != 0) {
                                            valido = true;

                                        } else {//establecemos el foco en el campo con el error
                                            this.jcb_tutores.requestFocus();
                                        }
                                    } else {
                                        this.jcb_profesores.requestFocus();
                                    }
                                } else {
                                    this.jcb_ciclos.requestFocus();
                                }
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

    /*solicitamos que se guarde el alumno en la base de datos, tanto en la tabla
    de login como en la tabla de alumnos. Al ser un guardado en varias tablas
    que debe efectuarse si o si, vamos a controlarlo con gestión de transacciones 
    de la base de datos*/
    private void guardarAlumno() {

        try {
            conn.setAutoCommit(false);//desactivamos el autocommit de la BBDD

            if (gestor.getLoginDao().insert(obtenerLogin())) {
                if (gestor.getAlumnoDao().insert(obtenerAlumno())) {
                    JOptionPane.showMessageDialog(null, "Alumno guardado correctamente.");
                } else {
                    /*En el caso de que se guarde el login, pero no el alumno, borramos
                    el registro de la tabla login para poder guardarlo más adelante*/
                    //gestor.getLoginDao().delete(obtenerLogin());

                    JOptionPane.showMessageDialog(null, "Ha ocurrido un fallo al guardar el alumno");

                    /*estamos en el caso de que se ha guardado en la tabla login
                    pero no en la tabla alumno, por lo que efectuamos un rolback
                    y devolvemos la BBDD al estado que tenia antes de hacer el 
                    primer insert*/
                    conn.rollback();
                }
            } else {
                JOptionPane.showMessageDialog(null, "El Alumno ya se encuentra registrado");
            }
            //se han realizado los dos insert
            conn.commit();

        } catch (SQLException ex) {
            Logger.getLogger(GestionAlumnos.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                /*Nos aseguramos de activar el autocommit*/
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(GestionAlumnos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /*Solicitamos la modificación de los datos del alumno, como la modificación 
    debe realizarse en dos tabla vamos a controlar que se realiza correctamente 
    gestionando las transacciones */
    private void editarAlumno() {

        int indice = this.jcb_alumnos.getSelectedIndex() - 1;

        Login login = obtenerLogin();
        login.setId(gestor.getLoginDao().getIdByEmail(alumnos.get(indice).getEmail()));

        Alumno alumno = obtenerAlumno();
        alumno.setId(alumnos.get(indice).getId());

        try {

            conn.setAutoCommit(false);//desactivamos el autocommit de la BBDD

            if (gestor.getLoginDao().update(login)) {
                if (gestor.getAlumnoDao().update(alumno)) {
                    JOptionPane.showMessageDialog(null, "Alumno modificado correctamente.");
                } else {
                    //codigo
                    /*Estamos en el caso de que se ha actualizado el registro en
                    la tabla login pero ha ocurrido un error al actualizar en la 
                    tabla de alumnos, por lo que vamos ha hacer un rollback para
                    devolver la BBDD a su estado anterior.*/
                    conn.rollback();
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al actualizar el alumno.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "El Alumno no ha podido ser registrado");
            }
            
            //se han realizado correctamente los dos update
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(GestionAlumnos.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                /*Nos aseguramos de activar el autocommit*/
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(GestionAlumnos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Alumno obtenerAlumno() {
        Alumno alumno = new Alumno();

        alumno.setNombre(this.jtf_nombre.getText());
        alumno.setPrimerApellido(this.jtf_primerApellido.getText());
        alumno.setSegundoApellido(this.jtf_segundoApellido.getText());
        alumno.setEmail(this.jtf_email.getText().trim());
        //alumno.setCiclo(this.jcb_ciclos.getSelectedItem().toString());
        alumno.setProfesorID(profesores.get(this.jcb_profesores.getSelectedIndex() - 1).getId());
        alumno.setTutorID(tutores.get(this.jcb_tutores.getSelectedIndex() - 1).getId());

        return alumno;
    }

    private Login obtenerLogin() {
        Login login = new Login();

        login.setEmail(this.jtf_email.getText().trim());
        login.setPassword(String.valueOf(this.jpf_password.getPassword()));
        login.setActivo(this.checkBox_activo.isSelected());
        login.setRol(Constantes.ALUMNO);

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
        jcb_alumnos = new javax.swing.JComboBox<>();
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
        jLabel9 = new javax.swing.JLabel();
        jcb_ciclos = new javax.swing.JComboBox<>();
        mostrar_pass = new javax.swing.JLabel();
        ocultar_pass = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jcb_profesores = new javax.swing.JComboBox<>();
        jcb_tutores = new javax.swing.JComboBox<>();

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

        jcb_alumnos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcb_alumnosItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addComponent(jcb_alumnos, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(294, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jcb_alumnos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_modificar)
                    .addComponent(btn_nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(btn_nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 145, Short.MAX_VALUE)
                .addComponent(btn_cerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.LINE_END);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Datos alumno"));

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
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Ciclo:");

        jcb_ciclos.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jcb_ciclosPopupMenuWillBecomeVisible(evt);
            }
        });

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
                    .addComponent(checkBox_activo)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtf_nombre)
                            .addComponent(jtf_primerApellido)
                            .addComponent(jtf_email)
                            .addComponent(jtf_segundoApellido)
                            .addComponent(jcb_ciclos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jpf_password, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(mostrar_pass)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ocultar_pass)))))
                .addGap(26, 26, 26)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(mostrar_pass, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jpf_password, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(ocultar_pass))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcb_ciclos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(checkBox_activo))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Tutores asignados"));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Profesor:");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Tutor:");

        jcb_profesores.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jcb_profesoresPopupMenuWillBecomeVisible(evt);
            }
        });

        jcb_tutores.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jcb_tutoresPopupMenuWillBecomeVisible(evt);
            }
        });
        jcb_tutores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcb_tutoresActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jcb_profesores, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_tutores, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_profesores, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_tutores, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        this.x = null;
    }//GEN-LAST:event_formInternalFrameClosing

    private void jcb_tutoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcb_tutoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcb_tutoresActionPerformed

    private void jcb_nivelSeguridadItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcb_nivelSeguridadItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            longitudMinima();
        }
    }//GEN-LAST:event_jcb_nivelSeguridadItemStateChanged

    private void jcb_alumnosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcb_alumnosItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            initBotones();
            if (this.jcb_alumnos.getSelectedIndex() != 0) {
                rellenarCampos();
            }
        }
    }//GEN-LAST:event_jcb_alumnosItemStateChanged

    private void btn_limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btn_limpiarActionPerformed

    private void btn_generarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_generarActionPerformed
        if (this.jcb_nivelSeguridad.getSelectedIndex() != 0) {
            if (!this.jtf_longitud.getText().equals("")) {
                this.jpf_password.setText(new Generador().getClave(
                        this.jcb_nivelSeguridad.getSelectedItem().toString(),
                        Integer.parseInt(this.jtf_longitud.getText())));
            } else {
                JOptionPane.showMessageDialog(null, "Indique una longitud válida");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Indique un nivel de seguridad válido");
        }
    }//GEN-LAST:event_btn_generarActionPerformed

    private void btn_cerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cerrarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btn_cerrarActionPerformed

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        // TODO add your handling code here:
        if (validarCampos()) {
            guardarAlumno();
            limpiarCampos();
            llenarAlumnos();
        } else {
            JOptionPane.showMessageDialog(null, "Faltan campos por rellenar.");
        }
    }//GEN-LAST:event_btn_guardarActionPerformed

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
            editarAlumno();
            limpiarCampos();
            llenarAlumnos();
        } else {
            JOptionPane.showMessageDialog(null, "Faltan campos por rellenar.");
        }
    }//GEN-LAST:event_btn_modificarActionPerformed

    /**
     * Con los siguientes métodos de respuesta a eventos nos aseguramos que al
     * desplegar el JComboBox este este actualizado
     */
    private void jcb_ciclosPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jcb_ciclosPopupMenuWillBecomeVisible
        // TODO add your handling code here:
        llenarCiclos();
    }//GEN-LAST:event_jcb_ciclosPopupMenuWillBecomeVisible

    private void jcb_profesoresPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jcb_profesoresPopupMenuWillBecomeVisible
        // TODO add your handling code here:
        llenarProfesores();
    }//GEN-LAST:event_jcb_profesoresPopupMenuWillBecomeVisible

    private void jcb_tutoresPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jcb_tutoresPopupMenuWillBecomeVisible
        // TODO add your handling code here:
        llenarTutores();
    }//GEN-LAST:event_jcb_tutoresPopupMenuWillBecomeVisible


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
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JComboBox<String> jcb_alumnos;
    private javax.swing.JComboBox<String> jcb_ciclos;
    private javax.swing.JComboBox<String> jcb_nivelSeguridad;
    private javax.swing.JComboBox<String> jcb_profesores;
    private javax.swing.JComboBox<String> jcb_tutores;
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
