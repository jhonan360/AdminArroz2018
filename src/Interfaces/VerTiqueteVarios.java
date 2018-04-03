/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Logica.Bascula.bascula;
import Logica.Bascula.verTiqueteVarios;
import java.util.Date;

/**
 *
 * @author Lizeth
 */
public class VerTiqueteVarios extends javax.swing.JFrame {

    public static verTiqueteVarios verTiqVarios;
    public static bascula bascula;

    /**
     * Creates new form VisualizarTiquetePrincipal
     */
    public VerTiqueteVarios() {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        verTiqVarios = new verTiqueteVarios();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtTiquete = new javax.swing.JTextField();
        chTiquete = new javax.swing.JCheckBox();
        chFecha = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        dcFechaInicial = new com.toedter.calendar.JDateChooser();
        dcFechaFinal = new com.toedter.calendar.JDateChooser();
        btnbuscar = new javax.swing.JButton();
        btnRefrescar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVerTiqVarios = new javax.swing.JTable();
        btnCrear = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblEntradas = new javax.swing.JTable();
        jLabel29 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        mnConductor = new javax.swing.JMenuItem();
        mnTipo_Arroz = new javax.swing.JMenuItem();
        mnVehiculo = new javax.swing.JMenuItem();
        mnLote = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        mnTiqueteMateriaPrima = new javax.swing.JMenuItem();
        mnTiqueteVarios = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        mnVerTiqueteMateriaPrima = new javax.swing.JMenuItem();
        mnVerTiqueteVarios = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setPreferredSize(new java.awt.Dimension(1116, 114));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("BUSQUEDA TIQUETES DE MOVIMIENTOS VARIOS");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/verTiqVarios.png"))); // NOI18N
        jLabel2.setPreferredSize(new java.awt.Dimension(80, 90));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(230, 230, 230)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setPreferredSize(new java.awt.Dimension(710, 123));

        txtTiquete.setEditable(false);

        chTiquete.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chTiquete.setText("N° Tiquete");
        chTiquete.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chTiqueteItemStateChanged(evt);
            }
        });

        chFecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chFecha.setText("Fecha");
        chFecha.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chFechaItemStateChanged(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel10.setText("Busquedas:");

        dcFechaInicial.setEnabled(false);
        dcFechaInicial.setOpaque(false);

        dcFechaFinal.setEnabled(false);
        dcFechaFinal.setOpaque(false);

        btnbuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/search.png"))); // NOI18N
        btnbuscar.setText("Buscar");
        btnbuscar.setMinimumSize(new java.awt.Dimension(101, 4));
        btnbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscarActionPerformed(evt);
            }
        });

        btnRefrescar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/actualizar.png"))); // NOI18N
        btnRefrescar.setText("Refrescar");
        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("-");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/search2.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chTiquete)
                            .addComponent(txtTiquete, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(55, 55, 55)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chFecha)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(dcFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)
                                .addGap(4, 4, 4)
                                .addComponent(dcFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefrescar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel10))
                .addGap(4, 4, 4)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(chTiquete)
                                .addGap(0, 0, 0)
                                .addComponent(txtTiquete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(chFecha)
                                .addGap(0, 0, 0)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dcFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dcFechaFinal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRefrescar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        tblVerTiqVarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblVerTiqVarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVerTiqVariosMouseClicked(evt);
            }
        });
        tblVerTiqVarios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblVerTiqVariosKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblVerTiqVarios);

        btnCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/download.png"))); // NOI18N
        btnCrear.setText("Descargar");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

        tblEntradas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Cantidad", "Descripcion"
            }
        ));
        tblEntradas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEntradasMouseClicked(evt);
            }
        });
        tblEntradas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblEntradasKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tblEntradas);

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 153));
        jLabel29.setText("*Seleccione el tiquete que desea guardar o descargar.");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1329, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1048, Short.MAX_VALUE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(8, 8, 8)
                .addComponent(btnCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );

        jMenuBar1.setBackground(new java.awt.Color(153, 153, 255));
        jMenuBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jMenuBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jMenuBar1.setPreferredSize(new java.awt.Dimension(139, 34));

        jMenu1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/plus.png"))); // NOI18N
        jMenu1.setText("Opciones");
        jMenu1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jMenu1.setPreferredSize(new java.awt.Dimension(175, 32));

        jMenuItem1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/derechaN.png"))); // NOI18N
        jMenuItem1.setText("Crear Agricultor");
        jMenuItem1.setPreferredSize(new java.awt.Dimension(175, 22));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        mnConductor.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        mnConductor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/derechaN.png"))); // NOI18N
        mnConductor.setText("Crear Conductor");
        mnConductor.setPreferredSize(new java.awt.Dimension(175, 22));
        mnConductor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnConductorActionPerformed(evt);
            }
        });
        jMenu1.add(mnConductor);

        mnTipo_Arroz.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        mnTipo_Arroz.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/derechaN.png"))); // NOI18N
        mnTipo_Arroz.setText("Crear Tipo de arroz");
        mnTipo_Arroz.setPreferredSize(new java.awt.Dimension(175, 22));
        mnTipo_Arroz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnTipo_ArrozActionPerformed(evt);
            }
        });
        jMenu1.add(mnTipo_Arroz);

        mnVehiculo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        mnVehiculo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/derechaN.png"))); // NOI18N
        mnVehiculo.setText("Crear Vehiculo");
        mnVehiculo.setPreferredSize(new java.awt.Dimension(175, 22));
        mnVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnVehiculoActionPerformed(evt);
            }
        });
        jMenu1.add(mnVehiculo);

        mnLote.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        mnLote.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/derechaN.png"))); // NOI18N
        mnLote.setText("Crear Lote");
        mnLote.setPreferredSize(new java.awt.Dimension(175, 22));
        mnLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnLoteActionPerformed(evt);
            }
        });
        jMenu1.add(mnLote);

        jMenuBar1.add(jMenu1);

        jMenu3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/file.png"))); // NOI18N
        jMenu3.setText("Crear Tiquete");
        jMenu3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jMenu3.setPreferredSize(new java.awt.Dimension(195, 32));

        mnTiqueteMateriaPrima.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        mnTiqueteMateriaPrima.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/derechaN.png"))); // NOI18N
        mnTiqueteMateriaPrima.setText("Tiquete Materia Prima");
        mnTiqueteMateriaPrima.setPreferredSize(new java.awt.Dimension(195, 22));
        mnTiqueteMateriaPrima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnTiqueteMateriaPrimaActionPerformed(evt);
            }
        });
        jMenu3.add(mnTiqueteMateriaPrima);

        mnTiqueteVarios.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        mnTiqueteVarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/derechaN.png"))); // NOI18N
        mnTiqueteVarios.setText("Tiquete Entrada Varios");
        mnTiqueteVarios.setToolTipText("");
        mnTiqueteVarios.setPreferredSize(new java.awt.Dimension(195, 22));
        mnTiqueteVarios.setRequestFocusEnabled(false);
        mnTiqueteVarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnTiqueteVariosActionPerformed(evt);
            }
        });
        jMenu3.add(mnTiqueteVarios);

        jMenuBar1.add(jMenu3);

        jMenu4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/search2.png"))); // NOI18N
        jMenu4.setText("Ver Tiquete");
        jMenu4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jMenu4.setPreferredSize(new java.awt.Dimension(195, 32));

        mnVerTiqueteMateriaPrima.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        mnVerTiqueteMateriaPrima.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/derechaN.png"))); // NOI18N
        mnVerTiqueteMateriaPrima.setText("Tiquete Materia Prima");
        mnVerTiqueteMateriaPrima.setPreferredSize(new java.awt.Dimension(195, 22));
        mnVerTiqueteMateriaPrima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnVerTiqueteMateriaPrimaActionPerformed(evt);
            }
        });
        jMenu4.add(mnVerTiqueteMateriaPrima);

        mnVerTiqueteVarios.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        mnVerTiqueteVarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/derechaN.png"))); // NOI18N
        mnVerTiqueteVarios.setText("Tiquete Entrada Varios");
        mnVerTiqueteVarios.setToolTipText("");
        mnVerTiqueteVarios.setPreferredSize(new java.awt.Dimension(195, 22));
        mnVerTiqueteVarios.setRequestFocusEnabled(false);
        mnVerTiqueteVarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnVerTiqueteVariosActionPerformed(evt);
            }
        });
        jMenu4.add(mnVerTiqueteVarios);

        jMenuBar1.add(jMenu4);

        jMenu2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/user.png"))); // NOI18N
        jMenu2.setText("Cuenta");
        jMenu2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jMenu2.setPreferredSize(new java.awt.Dimension(195, 32));

        menuSalir.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        menuSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/derechaN.png"))); // NOI18N
        menuSalir.setText("Cerrar Sesión");
        menuSalir.setPreferredSize(new java.awt.Dimension(195, 22));
        menuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSalirActionPerformed(evt);
            }
        });
        jMenu2.add(menuSalir);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1353, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chTiqueteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chTiqueteItemStateChanged
        if (chTiquete.isSelected() == true) {
            txtTiquete.setEditable(true);

        } else {
            txtTiquete.setEditable(false);
            txtTiquete.setText("");
        }
    }//GEN-LAST:event_chTiqueteItemStateChanged

    private void chFechaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chFechaItemStateChanged
        // TODO add your handling code here:
        if (chFecha.isSelected() == true) {
            dcFechaInicial.setEnabled(true);
            dcFechaFinal.setEnabled(true);
        } else {
            dcFechaInicial.setEnabled(false);
            dcFechaFinal.setEnabled(false);
            dcFechaInicial.setDate(null);
            dcFechaFinal.setDate(null);
        }
    }//GEN-LAST:event_chFechaItemStateChanged

    private void btnbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscarActionPerformed
        if (dcFechaFinal.getDate() == null && dcFechaInicial.getDate() == null) {
            verTiqVarios.buscar();
        } else {
            if (dcFechaFinal.getDate() == null) {
                Date FechaInicial = dcFechaInicial.getDate();
                dcFechaFinal.setDate(FechaInicial);
            } else {
                if (dcFechaInicial.getDate() == null) {
                    Date FechaFinal = dcFechaFinal.getDate();
                    dcFechaInicial.setDate(FechaFinal);
               }
            }
        }
        verTiqVarios.crearModeloEntradas();
        verTiqVarios.buscar();
    }//GEN-LAST:event_btnbuscarActionPerformed

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed
        verTiqVarios.crearModeloEntradas();
        verTiqVarios.crearModelo();
    }//GEN-LAST:event_btnRefrescarActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        verTiqVarios.reporteBasculaTiqVarios();
    }//GEN-LAST:event_btnCrearActionPerformed

    private void tblEntradasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEntradasMouseClicked
        //tiqVarios.tabla_campos();
        //tblEntradas.setRowSelectionAllowed(true);
        //tblEntradas.setCellSelectionEnabled(true);
    }//GEN-LAST:event_tblEntradasMouseClicked

    private void tblEntradasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblEntradasKeyReleased
        //tiqVarios.tabla_campos();
    }//GEN-LAST:event_tblEntradasKeyReleased

    private void tblVerTiqVariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVerTiqVariosMouseClicked
        verTiqVarios.crearModeloEntradas();
        verTiqVarios.tablaCamposEntrada();
        
    }//GEN-LAST:event_tblVerTiqVariosMouseClicked

    private void tblVerTiqVariosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblVerTiqVariosKeyReleased
        verTiqVarios.crearModeloEntradas();
        verTiqVarios.tablaCamposEntrada();
    }//GEN-LAST:event_tblVerTiqVariosKeyReleased

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        bascula.abrirAgricultor();
        //setVisible(false);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void mnConductorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnConductorActionPerformed
        bascula.abrirConductor();
    }//GEN-LAST:event_mnConductorActionPerformed

    private void mnTipo_ArrozActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnTipo_ArrozActionPerformed
        bascula.tipo_de_arroz();
    }//GEN-LAST:event_mnTipo_ArrozActionPerformed

    private void mnVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnVehiculoActionPerformed
        bascula.abrirVehiculo();
    }//GEN-LAST:event_mnVehiculoActionPerformed

    private void mnLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnLoteActionPerformed
        bascula.abrirLote();
    }//GEN-LAST:event_mnLoteActionPerformed

    private void mnTiqueteMateriaPrimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnTiqueteMateriaPrimaActionPerformed
        bascula.abrirTiqueteMateriaPrima();
    }//GEN-LAST:event_mnTiqueteMateriaPrimaActionPerformed

    private void mnTiqueteVariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnTiqueteVariosActionPerformed
        bascula.abrirTiqueteVarios();
    }//GEN-LAST:event_mnTiqueteVariosActionPerformed

    private void mnVerTiqueteMateriaPrimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnVerTiqueteMateriaPrimaActionPerformed
        bascula.abrirVerTiqueteMateriaPrima();
    }//GEN-LAST:event_mnVerTiqueteMateriaPrimaActionPerformed

    private void mnVerTiqueteVariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnVerTiqueteVariosActionPerformed
        bascula.abrirVerTiqueteVarios();
    }//GEN-LAST:event_mnVerTiqueteVariosActionPerformed

    private void menuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSalirActionPerformed
        dispose();
        bascula.salir();
    }//GEN-LAST:event_menuSalirActionPerformed

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
            java.util.logging.Logger.getLogger(VerTiqueteVarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VerTiqueteVarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VerTiqueteVarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VerTiqueteVarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VerTiqueteVarios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnCrear;
    public static javax.swing.JButton btnRefrescar;
    public static javax.swing.JButton btnbuscar;
    public static javax.swing.JCheckBox chFecha;
    public static javax.swing.JCheckBox chTiquete;
    public static com.toedter.calendar.JDateChooser dcFechaFinal;
    public static com.toedter.calendar.JDateChooser dcFechaInicial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    public static javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JMenuItem menuSalir;
    public static javax.swing.JMenuItem mnConductor;
    public static javax.swing.JMenuItem mnLote;
    public static javax.swing.JMenuItem mnTipo_Arroz;
    public static javax.swing.JMenuItem mnTiqueteMateriaPrima;
    public static javax.swing.JMenuItem mnTiqueteVarios;
    public static javax.swing.JMenuItem mnVehiculo;
    public static javax.swing.JMenuItem mnVerTiqueteMateriaPrima;
    public static javax.swing.JMenuItem mnVerTiqueteVarios;
    public static javax.swing.JTable tblEntradas;
    public static javax.swing.JTable tblVerTiqVarios;
    public static javax.swing.JTextField txtTiquete;
    // End of variables declaration//GEN-END:variables
}
