/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Logica.Bascula.bascula;
import Logica.Bascula.busquedasTiquete;
import Logica.Laboratorio.busquedaLaboratorio;
import Logica.Laboratorio.laboratorioTiqueteInicial;
import Reportes.logicaReportes;
import java.util.Date;
import Logica.Extras.validaciones;

/**
 *
 * @author Andre
 */
public class BusquedasTiqueteInicial extends javax.swing.JFrame {

    public static busquedasTiquete busTiquete;
    static busquedaLaboratorio busT;
    public static bascula bascula;
    static laboratorioTiqueteInicial labo;
    public static logicaReportes reportes;
    public static validaciones vali;

    /**
     * Creates new form BusquedasTiquete
     */
    public BusquedasTiqueteInicial() {
        initComponents();
        setLocationRelativeTo(null);
        this.setResizable(false);
        //setLocationRelativeTo(null);
        busT = new busquedaLaboratorio();
        reportes = new logicaReportes();
        vali = new validaciones();
        vali.IDENTIFICACION(txtcedula);
        vali.IDENTIFICACION(txttiquete);
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
        jTable1 = new javax.swing.JTable();
        panelAgricultor = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        chcedula = new javax.swing.JCheckBox();
        txtcedula = new javax.swing.JTextField();
        chtiquete = new javax.swing.JCheckBox();
        txttiquete = new javax.swing.JTextField();
        chfecha = new javax.swing.JCheckBox();
        jDateinicial = new com.toedter.calendar.JDateChooser();
        jDatefinal = new com.toedter.calendar.JDateChooser();
        btnBuscarAgricultor = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblLaboratorio = new javax.swing.JTable();
        jLabel29 = new javax.swing.JLabel();
        btnCrear = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnPorAprobar = new javax.swing.JMenuItem();
        mnTiqueteLaboratorio = new javax.swing.JMenuItem();
        mnAgricultor = new javax.swing.JMenuItem();
        mnTipoArroz = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        mnVerTiqueteMateriaPrima = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        menuSalir = new javax.swing.JMenuItem();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1280, 725));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panelAgricultor.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setPreferredSize(new java.awt.Dimension(710, 123));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel10.setText("Busquedas:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("-");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/search2.png"))); // NOI18N

        chcedula.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chcedula.setText("CC Agricultor");
        chcedula.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chcedulaItemStateChanged(evt);
            }
        });
        chcedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chcedulaActionPerformed(evt);
            }
        });

        txtcedula.setEditable(false);

        chtiquete.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chtiquete.setText("N° Tiquete bascula");
        chtiquete.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chtiqueteItemStateChanged(evt);
            }
        });

        txttiquete.setEditable(false);

        chfecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chfecha.setText("Fecha");
        chfecha.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chfechaItemStateChanged(evt);
            }
        });
        chfecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chfechaActionPerformed(evt);
            }
        });

        jDateinicial.setEnabled(false);

        jDatefinal.setEnabled(false);

        btnBuscarAgricultor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/search.png"))); // NOI18N
        btnBuscarAgricultor.setText("Buscar");
        btnBuscarAgricultor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarAgricultorActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/actualizar.png"))); // NOI18N
        jButton1.setText("Refrescar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chtiquete)
                            .addComponent(txttiquete, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chcedula)
                            .addComponent(txtcedula, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chfecha)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jDateinicial, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jLabel1)
                                .addGap(4, 4, 4)
                                .addComponent(jDatefinal, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBuscarAgricultor, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jSeparator1)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel10))
                .addGap(4, 4, 4)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chcedula)
                            .addComponent(chfecha))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateinicial, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtcedula, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDatefinal, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(chtiquete)
                        .addGap(0, 0, 0)
                        .addComponent(txttiquete, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBuscarAgricultor, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6))
        );

        chtiquete.getAccessibleContext().setAccessibleName("chApellidosAgricultor");
        txttiquete.getAccessibleContext().setAccessibleName("txtBApellidosAgricultor");
        chfecha.getAccessibleContext().setAccessibleName("chCedulaAgricultor");
        btnBuscarAgricultor.getAccessibleContext().setAccessibleName("btnBuscarAgricultor");

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Muestreo", "Hora", "Humedad"
            }
        ));
        jScrollPane2.setViewportView(jTable3);

        tblLaboratorio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        tblLaboratorio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLaboratorioMouseClicked(evt);
            }
        });
        tblLaboratorio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblLaboratorioKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tblLaboratorio);

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 153));
        jLabel29.setText("*Seleccione el tiquete que desea guardar o imprimir.");

        btnCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/download.png"))); // NOI18N
        btnCrear.setText("Descargar");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelAgricultorLayout = new javax.swing.GroupLayout(panelAgricultor);
        panelAgricultor.setLayout(panelAgricultorLayout);
        panelAgricultorLayout.setHorizontalGroup(
            panelAgricultorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAgricultorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 1222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
            .addGroup(panelAgricultorLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(panelAgricultorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1247, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAgricultorLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelAgricultorLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 915, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(6, 6, 6))
        );
        panelAgricultorLayout.setVerticalGroup(
            panelAgricultorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAgricultorLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAgricultorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addComponent(btnCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/tiqlab.png"))); // NOI18N
        jLabel3.setText("BUSQUEDA TIQUETES DE LABORATORIO");
        jLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel3.setPreferredSize(new java.awt.Dimension(593, 132));

        jMenuBar1.setBackground(new java.awt.Color(153, 153, 255));
        jMenuBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jMenuBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jMenuBar1.setPreferredSize(new java.awt.Dimension(139, 34));

        jMenu1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/laboratorista3.png"))); // NOI18N
        jMenu1.setText("Laboratorio");
        jMenu1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jMenu1.setPreferredSize(new java.awt.Dimension(195, 32));

        mnPorAprobar.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        mnPorAprobar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/derechaN.png"))); // NOI18N
        mnPorAprobar.setText("Tiquete Entrada");
        mnPorAprobar.setPreferredSize(new java.awt.Dimension(195, 22));
        mnPorAprobar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnPorAprobarActionPerformed(evt);
            }
        });
        jMenu1.add(mnPorAprobar);

        mnTiqueteLaboratorio.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        mnTiqueteLaboratorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/derechaN.png"))); // NOI18N
        mnTiqueteLaboratorio.setText("Tiquete Laboratorio");
        mnTiqueteLaboratorio.setPreferredSize(new java.awt.Dimension(195, 22));
        mnTiqueteLaboratorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnTiqueteLaboratorioActionPerformed(evt);
            }
        });
        jMenu1.add(mnTiqueteLaboratorio);

        mnAgricultor.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        mnAgricultor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/derechaN.png"))); // NOI18N
        mnAgricultor.setText("Crear Agricultor");
        mnAgricultor.setPreferredSize(new java.awt.Dimension(195, 22));
        mnAgricultor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnAgricultorActionPerformed(evt);
            }
        });
        jMenu1.add(mnAgricultor);

        mnTipoArroz.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        mnTipoArroz.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/derechaN.png"))); // NOI18N
        mnTipoArroz.setText("Crear Tipo de arroz");
        mnTipoArroz.setPreferredSize(new java.awt.Dimension(195, 22));
        mnTipoArroz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnTipoArrozActionPerformed(evt);
            }
        });
        jMenu1.add(mnTipoArroz);

        jMenuBar1.add(jMenu1);

        jMenu5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/search2.png"))); // NOI18N
        jMenu5.setText("Ver Tiquete");
        jMenu5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jMenu5.setPreferredSize(new java.awt.Dimension(195, 32));

        mnVerTiqueteMateriaPrima.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        mnVerTiqueteMateriaPrima.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/derechaN.png"))); // NOI18N
        mnVerTiqueteMateriaPrima.setText("Tiquete Laboratorio");
        mnVerTiqueteMateriaPrima.setPreferredSize(new java.awt.Dimension(195, 22));
        mnVerTiqueteMateriaPrima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnVerTiqueteMateriaPrimaActionPerformed(evt);
            }
        });
        jMenu5.add(mnVerTiqueteMateriaPrima);

        jMenuBar1.add(jMenu5);

        jMenu6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/user.png"))); // NOI18N
        jMenu6.setText("Cuenta");
        jMenu6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jMenu6.setPreferredSize(new java.awt.Dimension(195, 32));

        menuSalir.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        menuSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/derechaN.png"))); // NOI18N
        menuSalir.setText("Cerrar Sesión");
        menuSalir.setPreferredSize(new java.awt.Dimension(195, 22));
        menuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSalirActionPerformed(evt);
            }
        });
        jMenu6.add(menuSalir);

        jMenuBar1.add(jMenu6);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelAgricultor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelAgricultor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        panelAgricultor.getAccessibleContext().setAccessibleName("panelAgricultor");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     *
     * AGRICULTOR
     */
    /**
     *
     * CONDUCTOR
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        //busT.cerrar();
    }//GEN-LAST:event_formWindowClosing

    private void btnBuscarAgricultorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarAgricultorActionPerformed
        //busT.cedula();
        if (jDatefinal.getDate() == null && jDateinicial.getDate() == null) {
            busT.buscarAgricultor();
        } else {
            if (jDatefinal.getDate() == null) {
                Date FechaInicial = jDateinicial.getDate();
                jDatefinal.setDate(FechaInicial);
            } else {
                if (jDateinicial.getDate() == null) {
                    Date FechaFinal = jDatefinal.getDate();
                    jDateinicial.setDate(FechaFinal);
                }
            }
        }
        busT.buscarAgricultor();
    }//GEN-LAST:event_btnBuscarAgricultorActionPerformed

    private void chtiqueteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chtiqueteItemStateChanged
        if (chtiquete.isSelected() == true) {
            txttiquete.setEditable(true);
        } else {
            txttiquete.setEditable(false);
            txttiquete.setText("");
        }
    }//GEN-LAST:event_chtiqueteItemStateChanged

    private void chfechaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chfechaItemStateChanged
        if (chfecha.isSelected() == true) {
            jDateinicial.setEnabled(true);
            jDatefinal.setEnabled(true);
        } else {
            jDateinicial.setEnabled(false);
            jDatefinal.setEnabled(false);
            jDateinicial.setDate(null);
            jDatefinal.setDate(null);
        }
    }//GEN-LAST:event_chfechaItemStateChanged

    private void chfechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chfechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chfechaActionPerformed

    private void chcedulaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chcedulaItemStateChanged
        // TODO add your handling code here:
        if (chcedula.isSelected() == true) {
            txtcedula.setEditable(true);
        } else {
            txtcedula.setEditable(false);
            txtcedula.setText("");
        }

    }//GEN-LAST:event_chcedulaItemStateChanged

    private void chcedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chcedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chcedulaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        busT.crearModelo2();
        busT.crearModeloAgricultor();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblLaboratorioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLaboratorioMouseClicked
        busT.crearModelo2();
        busT.tabla_consecutivo_campo();
    }//GEN-LAST:event_tblLaboratorioMouseClicked

    private void tblLaboratorioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblLaboratorioKeyReleased
        busT.crearModelo2();
        busT.tabla_consecutivo_campo();
    }//GEN-LAST:event_tblLaboratorioKeyReleased

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        reportes.reportLaboratorioTiquete();
    }//GEN-LAST:event_btnCrearActionPerformed

    private void mnPorAprobarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnPorAprobarActionPerformed
        labo.tiquete1();
        hide();
    }//GEN-LAST:event_mnPorAprobarActionPerformed

    private void mnVerTiqueteMateriaPrimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnVerTiqueteMateriaPrimaActionPerformed
        //labo.busquedaTiq();
        //hide();
    }//GEN-LAST:event_mnVerTiqueteMateriaPrimaActionPerformed

    private void menuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSalirActionPerformed
        labo.salir();
        dispose();
    }//GEN-LAST:event_menuSalirActionPerformed

    private void mnTiqueteLaboratorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnTiqueteLaboratorioActionPerformed
        labo.tiquete2();
        hide();
    }//GEN-LAST:event_mnTiqueteLaboratorioActionPerformed

    private void mnAgricultorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnAgricultorActionPerformed
        bascula.abrirAgricultor();
        hide();
    }//GEN-LAST:event_mnAgricultorActionPerformed

    private void mnTipoArrozActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnTipoArrozActionPerformed
        bascula.tipo_de_arroz();
        hide();
    }//GEN-LAST:event_mnTipoArrozActionPerformed

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
            java.util.logging.Logger.getLogger(BusquedasTiquete.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BusquedasTiquete.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BusquedasTiquete.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BusquedasTiquete.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BusquedasTiqueteInicial().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnBuscarAgricultor;
    public static javax.swing.JButton btnCrear;
    public static javax.swing.JCheckBox chcedula;
    public static javax.swing.JCheckBox chfecha;
    public static javax.swing.JCheckBox chtiquete;
    private javax.swing.JButton jButton1;
    public static com.toedter.calendar.JDateChooser jDatefinal;
    public static com.toedter.calendar.JDateChooser jDateinicial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    public static javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    public static javax.swing.JTable jTable3;
    private javax.swing.JMenuItem menuSalir;
    public static javax.swing.JMenuItem mnAgricultor;
    public static javax.swing.JMenuItem mnPorAprobar;
    public static javax.swing.JMenuItem mnTipoArroz;
    public static javax.swing.JMenuItem mnTiqueteLaboratorio;
    public static javax.swing.JMenuItem mnVerTiqueteMateriaPrima;
    public static javax.swing.JPanel panelAgricultor;
    public static javax.swing.JTable tblLaboratorio;
    public static javax.swing.JTextField txtcedula;
    public static javax.swing.JTextField txttiquete;
    // End of variables declaration//GEN-END:variables
}
