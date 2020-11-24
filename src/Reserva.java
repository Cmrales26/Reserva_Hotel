/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import clas.Conectar;
import com.toedter.calendar.JDateChooser;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nelson Camilo
 */
public class Reserva extends javax.swing.JFrame {

    Connection con;
    DefaultTableModel dtm = new DefaultTableModel();

    public Reserva() {
        initComponents();
        bloquear();
        cargar("");
        this.setLocationRelativeTo(null);
        setTitle("Reservas");
        this.setLocationRelativeTo(null);
        //TIPO DE DOCUMENTOS
        jComboTipoDoc.removeAllItems();
        jComboTipoDoc.addItem("Selecione su documento de identidad...");
        jComboTipoDoc.addItem("Tarjeta de Identidad");
        jComboTipoDoc.addItem("Cedula de Ciudadania");
        jComboTipoDoc.addItem("Cedula Extranjera ");
        //NUMERO DE PERSONAS
        jCombonumper.addItem("Seleccione el número de personas...");
        jCombonumper.addItem("1");
        jCombonumper.addItem("2");
        jCombonumper.addItem("3");
        jCombonumper.addItem("4");
        //PISO
        jComboPiso.addItem("Seleccione un Piso...");
        jComboPiso.addItem("Piso 1");
        jComboPiso.addItem("Piso 2");
        jComboPiso.addItem("Piso 3");
        jComboPiso.addItem("Piso 4");
    }

    void bloquear() {
        jTextPrecio.setEnabled(false);
        jTextNoches.setEnabled(false);
        jTextPiso.setEditable(false);
        jButtonReservar.setEnabled(false);
    }

    void limpiar() {
        jTextNombre.setText("");
        jTextApellido.setText("");
        jTextDireccion.setText("");
        jTextEmail.setText("");
        jTextNumDoc.setText("");
        jTextPrecio.setText("");
        jTextTelf.setText("");
    }

    public void llenarHabitaciones() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Conectar ct = new Conectar();
        Connection con = ct.conexion();
        String pisaha = jComboPiso.getSelectedItem().toString();
        if (pisaha.equals("Piso 1")) {
            String sql = "SELECT * FROM `habitaciones` WHERE piso = 1 AND estado = \"Desocupado\"";
            try {
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                jComboHabi.removeAllItems();
                while (rs.next()) {

                    jComboHabi.addItem(rs.getString("habitacion"));
                }
                rs.close();
            } catch (Exception e) {
            }
        } else if (pisaha.equals("Piso 2")) {
            String sql = "SELECT * FROM `habitaciones` WHERE piso = 2 AND estado = \"Desocupado\"";
            try {
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                jComboHabi.removeAllItems();
                while (rs.next()) {

                    jComboHabi.addItem(rs.getString("habitacion"));
                }
                rs.close();
            } catch (Exception e) {
            }
        } else if (pisaha.equals("Piso 3")) {
            String sql = "SELECT * FROM `habitaciones` WHERE piso = 3 AND estado = \"Desocupado\"";
            try {
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                jComboHabi.removeAllItems();
                while (rs.next()) {

                    jComboHabi.addItem(rs.getString("habitacion"));
                }
                rs.close();
            } catch (Exception e) {
            }
        } else if (pisaha.equals("Piso 4")) {
            String sql = "SELECT * FROM `habitaciones` WHERE piso = 4 AND estado = \"Desocupado\"";
            try {
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                jComboHabi.removeAllItems();
                while (rs.next()) {
                    jComboHabi.addItem(rs.getString("habitacion"));
                }
                rs.close();
            } catch (Exception e) {
            }
        } else {
            jComboHabi.addItem("Seleccione una habitación...");
        }
    }

    void calcularDias(JDateChooser jDateEntrada, JDateChooser jDateSalida) {
        if (this.jDateEntrada.getDate() != null && this.jDateSalida.getDate() != null) {
            Calendar fecha_Ingreso = this.jDateEntrada.getCalendar();
            Calendar fecha_Salida = this.jDateSalida.getCalendar();
            int dias = -1;
            while (fecha_Ingreso.before(fecha_Salida) || fecha_Ingreso.equals(fecha_Salida)) {
                dias++;
                fecha_Ingreso.add(Calendar.DATE, 1);
            }
            String diass = String.valueOf(dias);
            jTextNoches.setText(diass);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione sus fechas");
        }
    }

    void eliminar() {
        Conectar cc = new Conectar();
        Connection con = cc.conexion();
        String n_doc = jTextNumDoc.getText();
        int seleccion = JOptionPane.showConfirmDialog(null, "SE ELIMINARÁ EL USUARIOS DE LA TABLA Y DE LA BASE DE DATOS", "¿DESEA ELIMINAR AL USUARIO?", JOptionPane.YES_NO_CANCEL_OPTION);
        String sql = "DELETE FROM clientes WHERE no_documento = " + n_doc;
        if (seleccion == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(null, "EL USUARIO NO HA SIDO ELIMINADA");
        } else if (seleccion == JOptionPane.YES_OPTION) {
            try {
                Statement st = con.createStatement();
                st.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "LA PERSONA HA SIDO ELIMINADA");
                limpiar();
                cargar("");
            } catch (SQLException ex) {
                Logger.getLogger(Reserva.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (seleccion == JOptionPane.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(null, "LA PERSONA NO HA SIDO ELIMINADA");
        }
    }

    void Precios() {
        int precioTotal;
        String PrecioTotals;
        String precioFormato;
        int dias = Integer.parseInt(jTextNoches.getText());
        DecimalFormat formatea = new DecimalFormat("###,###.##");
        switch (jComboPiso.getSelectedItem().toString()) {
            case "Piso 1":
                precioTotal = 200000 * dias;
                precioFormato = formatea.format(precioTotal);
                jTextPrecio.setText(precioFormato);
                break;
            case "Piso 2":
                precioTotal = 255000 * dias;
                precioFormato = formatea.format(precioTotal);
                jTextPrecio.setText(precioFormato);
                break;
            case "Piso 3":
                precioTotal = 305000 * dias;
                precioFormato = formatea.format(precioTotal);
                jTextPrecio.setText(precioFormato);
                break;
            case "Piso 4":
                precioTotal = 555000 * dias;
                precioFormato = formatea.format(precioTotal);
                jTextPrecio.setText(precioFormato);
                ;
                break;
            default:
                jTextPrecio.setText("Selecione un piso");
        }
    }

    void ocupado() {
        Conectar ct = new Conectar();
        Connection con = ct.conexion();
        String estado = "Ocupado";
        String sql = "UPDATE habitaciones SET estado = '" + estado + "' WHERE habitacion = '" + jComboHabi.getSelectedItem().toString() + "'";
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.executeUpdate(sql);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar");
        }
    }

    void desocupado() {
        Conectar ct = new Conectar();
        Connection con = ct.conexion();
        String estado = "Desocupado";
        String sql = "UPDATE habitaciones SET estado = '" + estado + "' WHERE habitacion = '" + jTextPiso.getText()+ "'";
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.executeUpdate(sql);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar");
        }
    }

    void cargar(String dato) {
        Conectar ct = new Conectar();
        Connection con = ct.conexion();

        String titulos[] = {"Nombre", "Apellido", "tipo Documento", "no.documento", "Email", "Telefono", "Dirección", "Piso", "Habitacion",
            "Dia De Entrada", "Dia De salida", "numero De Personas", "Numero de noches", "Precio Total"};
        String registros[] = new String[14];

        String sql = "SELECT nombre,apellido,tipo_documento,no_documento,E_mail,telefono,direccion,piso,habitacion,dia_entrada,dia_salida,num_personas,	numero_noches, precio_total FROM `clientes` WHERE no_documento LIKE '%" + dato + "%'";
        dtm = new DefaultTableModel(null, titulos);
        try {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                registros[0] = rs.getString("nombre");
                registros[1] = rs.getString("apellido");
                registros[2] = rs.getString("tipo_documento");
                registros[3] = rs.getString("no_documento");
                registros[4] = rs.getString("E_mail");
                registros[5] = rs.getString("telefono");
                registros[6] = rs.getString("direccion");
                registros[7] = rs.getString("piso");
                registros[8] = rs.getString("habitacion");
                registros[9] = rs.getString("dia_entrada");
                registros[10] = rs.getString("dia_salida");
                registros[11] = rs.getString("num_personas");
                registros[12] = rs.getString("num_personas");
                registros[13] = rs.getString("precio_total");

                dtm.addRow(registros);
            }
            jTablaReserva.setModel(dtm);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    void actualizar() {
        String fts = jTextNumDoc.getText();
        int fti = Integer.parseInt(fts);

        if (fti > 0) {
            Conectar cc = new Conectar();
            Connection con = cc.conexion();
            String nom, apell, t_doc, ndoc, email, tel, direc;
            nom = jTextNombre.getText();
            apell = jTextApellido.getText();
            t_doc = jComboTipoDoc.getSelectedItem().toString();
            ndoc = jTextNumDoc.getText();
            email = jTextEmail.getText();
            tel = jTextTelf.getText();
            direc = jTextDireccion.getText();
            String sql = "UPDATE clientes SET nombre ='" + nom + "',apellido = '" + apell + "',tipo_documento = '" + t_doc + "',no_documento='" + ndoc + "',E_mail = '" + email + "'"
                    + ", telefono = '" + tel + "', direccion = '" + direc + "' WHERE no_documento = " + ndoc;
            Statement st;
            try {
                st = con.prepareStatement(sql);
                st.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "LOS DATOS SE HAN ACTUALIZADO CON EXITO");
                cargar("");
                limpiar();
            } catch (SQLException ex) {
                Logger.getLogger(Reserva.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "NO SE HA SELECIONADO NINGUN USUARIO");
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel8 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        Background = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextNombre = new javax.swing.JTextField();
        jTextApellido = new javax.swing.JTextField();
        jComboTipoDoc = new javax.swing.JComboBox<>();
        jTextNumDoc = new javax.swing.JTextField();
        jTextEmail = new javax.swing.JTextField();
        jTextTelf = new javax.swing.JTextField();
        jTextDireccion = new javax.swing.JTextField();
        jButtonReservar = new javax.swing.JButton();
        jButtonEditar = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaReserva = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jDateEntrada = new com.toedter.calendar.JDateChooser();
        jDateSalida = new com.toedter.calendar.JDateChooser();
        jLabel27 = new javax.swing.JLabel();
        jCombonumper = new javax.swing.JComboBox<>();
        jComboPiso = new javax.swing.JComboBox<>();
        jTextPrecio = new javax.swing.JTextField();
        jComboHabi = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        jTextNoches = new javax.swing.JTextField();
        jButtonCalcular = new javax.swing.JButton();
        jTextBuscar = new javax.swing.JTextField();
        jButtonEliminar = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jTextPiso = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        jLabel8.setText("jLabel8");

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/yes (2).jpg"))); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 2, 12)); // NOI18N
        jLabel1.setText("Nombre");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 2, 12)); // NOI18N
        jLabel2.setText("Apellido");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 2, 12)); // NOI18N
        jLabel3.setText("Tipo De Documento");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 2, 12)); // NOI18N
        jLabel4.setText("Número de documento");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 2, 12)); // NOI18N
        jLabel5.setText("E-Mail");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 2, 12)); // NOI18N
        jLabel6.setText("Telefono");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 2, 12)); // NOI18N
        jLabel7.setText("Dirección");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 3, 12)); // NOI18N
        jLabel9.setText("INFORMACION PERSONAL");

        jTextNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextNombreActionPerformed(evt);
            }
        });

        jTextApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextApellidoActionPerformed(evt);
            }
        });

        jTextDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextDireccionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextTelf))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextDireccion))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextNombre))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextApellido))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jComboTipoDoc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextNumDoc))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel9)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextNumDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextTelf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        jButtonReservar.setFont(new java.awt.Font("Times New Roman", 3, 12)); // NOI18N
        jButtonReservar.setText("RESERVAR");
        jButtonReservar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReservarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonReservar, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 440, -1, 30));

        jButtonEditar.setFont(new java.awt.Font("Times New Roman", 3, 12)); // NOI18N
        jButtonEditar.setText("EDITAR");
        jButtonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 460, -1, 30));

        jButtonCancelar.setFont(new java.awt.Font("Times New Roman", 3, 12)); // NOI18N
        jButtonCancelar.setText("CANCELAR");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 440, -1, 30));

        jLabel18.setFont(new java.awt.Font("Sitka Display", 3, 36)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 0, 0));
        jLabel18.setText("x");
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(1340, 10, 20, 30));

        jTablaReserva.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTablaReserva.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        jTablaReserva.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTablaReserva.setIntercellSpacing(new java.awt.Dimension(1, 2));
        jTablaReserva.setMaximumSize(new java.awt.Dimension(2147483647, 100));
        jTablaReserva.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablaReservaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablaReserva);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 494, 1381, 260));

        jLabel21.setFont(new java.awt.Font("Times New Roman", 3, 12)); // NOI18N
        jLabel21.setText("RESERVA");

        jLabel22.setFont(new java.awt.Font("Times New Roman", 2, 12)); // NOI18N
        jLabel22.setText("Piso");

        jLabel23.setFont(new java.awt.Font("Times New Roman", 2, 12)); // NOI18N
        jLabel23.setText("Habitacion");

        jLabel24.setFont(new java.awt.Font("Times New Roman", 2, 12)); // NOI18N
        jLabel24.setText("Precio TOTAL");

        jLabel25.setFont(new java.awt.Font("Times New Roman", 2, 12)); // NOI18N
        jLabel25.setText("Dia Entrda");

        jLabel26.setFont(new java.awt.Font("Times New Roman", 2, 12)); // NOI18N
        jLabel26.setText("Dia Salida");

        jDateSalida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDateSalidaMouseClicked(evt);
            }
        });
        jDateSalida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jDateSalidaKeyReleased(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Times New Roman", 2, 12)); // NOI18N
        jLabel27.setText("Número de personas");

        jComboPiso.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboPisoItemStateChanged(evt);
            }
        });
        jComboPiso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboPisoActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Times New Roman", 2, 12)); // NOI18N
        jLabel28.setText("Noches");

        jTextNoches.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextNochesActionPerformed(evt);
            }
        });

        jButtonCalcular.setBackground(new java.awt.Color(255, 0, 0));
        jButtonCalcular.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jButtonCalcular.setForeground(new java.awt.Color(255, 255, 255));
        jButtonCalcular.setText("PRESIONE PARA CUALCULAR EL PRECIO TOTAL ");
        jButtonCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCalcularActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonCalcular, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(5, 5, 5)
                            .addComponent(jTextPrecio))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel28))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextNoches)
                                .addComponent(jDateSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboPiso, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addGap(18, 18, 18)
                                .addComponent(jCombonumper, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jDateEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(18, 18, 18)
                                .addComponent(jComboHabi, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(229, 229, 229)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jComboPiso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jComboHabi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCombonumper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextNoches, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButtonCalcular)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 80, 680, -1));

        jTextBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextBuscarKeyReleased(evt);
            }
        });
        getContentPane().add(jTextBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 460, 157, -1));

        jButtonEliminar.setBackground(new java.awt.Color(255, 0, 50));
        jButtonEliminar.setFont(new java.awt.Font("Times New Roman", 3, 12)); // NOI18N
        jButtonEliminar.setForeground(new java.awt.Color(255, 255, 255));
        jButtonEliminar.setText("ELIMINAR");
        jButtonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 460, -1, 30));

        jLabel16.setFont(new java.awt.Font("Times New Roman", 3, 12)); // NOI18N
        jLabel16.setText("Buscar");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, -1, -1));
        getContentPane().add(jTextPiso, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 760, -1, 3));

        jLabel20.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel20.setText("RESERVAS");
        getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 30, 133, -1));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Logo_chikito1.png"))); // NOI18N
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked

        dispose();
    }//GEN-LAST:event_jLabel18MouseClicked

    private void jTextDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextDireccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextDireccionActionPerformed

    private void jTextApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextApellidoActionPerformed

    private void jTextNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextNombreActionPerformed

    private void jButtonCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCalcularActionPerformed
        calcularDias(jDateEntrada, jDateSalida);
        Precios();
        jButtonReservar.setEnabled(true);
    }//GEN-LAST:event_jButtonCalcularActionPerformed

    private void jTextNochesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextNochesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextNochesActionPerformed

    private void jComboPisoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboPisoActionPerformed

    }//GEN-LAST:event_jComboPisoActionPerformed

    private void jComboPisoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboPisoItemStateChanged

        llenarHabitaciones();
    }//GEN-LAST:event_jComboPisoItemStateChanged

    private void jDateSalidaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateSalidaKeyReleased

    }//GEN-LAST:event_jDateSalidaKeyReleased

    private void jDateSalidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDateSalidaMouseClicked

    }//GEN-LAST:event_jDateSalidaMouseClicked

    private void jTablaReservaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablaReservaMouseClicked
        JOptionPane.showMessageDialog(null, "LA ACTUALIZACION DE LOS DATOS SOLO SE HARAN EN LA INFORMACION PERSONAL");
        int fila = jTablaReserva.getSelectedRow();
        jTextNombre.setText(jTablaReserva.getValueAt(fila, 0).toString());
        jTextApellido.setText(jTablaReserva.getValueAt(fila, 1).toString());
        jComboTipoDoc.setSelectedItem(jTablaReserva.getValueAt(fila, 2).toString());
        jTextNumDoc.setText(jTablaReserva.getValueAt(fila, 3).toString());
        jTextEmail.setText(jTablaReserva.getValueAt(fila, 4).toString());
        jTextTelf.setText(jTablaReserva.getValueAt(fila, 5).toString());
        jTextDireccion.setText(jTablaReserva.getValueAt(fila, 6).toString());
        jTextPiso.setText(jTablaReserva.getValueAt(fila, 8).toString());

        jComboPiso.setEnabled(false);
        jComboHabi.setEnabled(false);
        jCombonumper.setEnabled(false);
        jTextPrecio.setEnabled(false);
        jDateEntrada.setEnabled(false);
        jDateSalida.setEnabled(false);
    }//GEN-LAST:event_jTablaReservaMouseClicked

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        eliminar();
        desocupado();
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        limpiar();
        jComboPiso.setEnabled(true);
        jComboHabi.setEnabled(true);
        jCombonumper.setEnabled(true);
        jTextPrecio.setEnabled(true);
        jDateEntrada.setEnabled(true);
        jDateSalida.setEnabled(true);
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jTextBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextBuscarKeyReleased
        cargar(jTextBuscar.getText());
    }//GEN-LAST:event_jTextBuscarKeyReleased

    private void jButtonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarActionPerformed
        actualizar();
        limpiar();
        cargar("");
        jComboPiso.setEnabled(true);
        jComboHabi.setEnabled(true);
        jCombonumper.setEnabled(true);
        jTextPrecio.setEnabled(true);
        jDateEntrada.setEnabled(true);
        jDateSalida.setEnabled(true);
        jComboPiso.setEnabled(true);
        jComboHabi.setEnabled(true);
        jCombonumper.setEnabled(true);
        jTextPrecio.setEnabled(true);
        jDateEntrada.setEnabled(true);
        jDateSalida.setEnabled(true);
    }//GEN-LAST:event_jButtonEditarActionPerformed

    private void jButtonReservarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReservarActionPerformed
        Conectar ct = new Conectar();
        Connection con = ct.conexion();
        String nom, apell, t_doc, ndoc, email, tel, direc, piso, habitacion, n_per, precio, dia_entrada, num_noches, dia_salida, precio_total;
        nom = jTextNombre.getText();
        apell = jTextApellido.getText();
        t_doc = jComboTipoDoc.getSelectedItem().toString();
        ndoc = jTextNumDoc.getText();
        email = jTextEmail.getText();
        tel = jTextTelf.getText();
        direc = jTextDireccion.getText();
        piso = jComboPiso.getSelectedItem().toString();
        habitacion = jComboHabi.getSelectedItem().toString();
        n_per = jCombonumper.getSelectedItem().toString();
        precio = jTextPrecio.getText();
        dia_entrada = jDateEntrada.getDate().toString();
        dia_salida = jDateSalida.getDate().toString();
        num_noches = jTextNoches.getText();
        precio_total = jTextPrecio.getText();
        int n_doc = Integer.parseInt(ndoc);

        String sql = "";

        sql = "INSERT INTO clientes(nombre,apellido,tipo_documento,no_documento,E_mail,telefono,direccion,piso,habitacion,dia_entrada,"
        + "dia_salida,num_personas,numero_noches,precio_total) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, nom);
            pst.setString(2, apell);
            pst.setString(3, t_doc);
            pst.setInt(4, n_doc);
            pst.setString(5, email);
            pst.setString(6, tel);
            pst.setString(7, direc);
            pst.setString(8, piso);
            pst.setString(9, habitacion);
            pst.setString(10, dia_entrada);
            pst.setString(11, dia_salida);
            pst.setString(12, n_per);
            pst.setString(13, num_noches);
            pst.setString(14, precio_total);

            int n = pst.executeUpdate();

            if (n > 0) {
                JOptionPane.showMessageDialog(null, "EL USUARIO HA SIDO REGISTRADO");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Reserva.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "error :c");
        }
        ocupado();
        limpiar();
        cargar("");
        jButtonReservar.setEnabled(false);
    }//GEN-LAST:event_jButtonReservarActionPerformed

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
            java.util.logging.Logger.getLogger(Reserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reserva().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private javax.swing.JButton jButtonCalcular;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonEditar;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonReservar;
    private javax.swing.JComboBox<String> jComboHabi;
    private javax.swing.JComboBox<String> jComboPiso;
    private javax.swing.JComboBox<String> jComboTipoDoc;
    private javax.swing.JComboBox<String> jCombonumper;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateEntrada;
    private com.toedter.calendar.JDateChooser jDateSalida;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablaReserva;
    private javax.swing.JTextField jTextApellido;
    private javax.swing.JTextField jTextBuscar;
    private javax.swing.JTextField jTextDireccion;
    private javax.swing.JTextField jTextEmail;
    private javax.swing.JTextField jTextNoches;
    private javax.swing.JTextField jTextNombre;
    private javax.swing.JTextField jTextNumDoc;
    private javax.swing.JTextField jTextPiso;
    private javax.swing.JTextField jTextPrecio;
    private javax.swing.JTextField jTextTelf;
    // End of variables declaration//GEN-END:variables
}
