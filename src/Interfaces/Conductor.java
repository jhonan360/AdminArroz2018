//Interfaz grafica del conductor
package Interfaces;
import static Logica.Administracion.usuarios.usuario;
import static Logica.Bascula.agricultor.ext;
import Logica.Bascula.bascula;
import Logica.Bascula.conductor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Logica.Extras.cargarCombo;
import Logica.Extras.tablas;
import Logica.Extras.validaciones;
import Logica.Extras.login;
import javax.swing.JOptionPane;
/**
 *
 * @author Lizeth
 */
public class Conductor extends javax.swing.JFrame {
    public static conductor cdt;
    //Extras
    public static tablas tbl;
    public static validaciones val,vali;
    public static cargarCombo cargar;
    public static login log;
    public static bascula bascula;

    public Conductor() {//Constructor de la clase
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        cdt = new conductor();
        cargar=new cargarCombo();
        cargar.CargarDepa(cmbDepartamento);
        cargar.cargarMunicipio(String.valueOf(cmbDepartamento.getSelectedIndex()+1), cmbCiudad );

        //Validaciones de las cajas de texto
        vali=new validaciones();
        vali.IDENTIFICACION(txtCedula);
        vali.NOMBRES(txtNombres);
        vali.NOMBRES(txtApellidos);
        vali.DIRECCION(txtDireccion);
        vali.TELEFONOS(txtTelefono);

        vali.IDENTIFICACION(txtBCedula);
        vali.NOMBRES(txtBCiudad);
        vali.NOMBRES(txtBApellido);

        municipio();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNombres = new javax.swing.JTextField();
        txtApellidos = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cmbDepartamento = new javax.swing.JComboBox<>();
        cmbCiudad = new javax.swing.JComboBox<>();
        txtDireccion = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnCrear = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        txtBCedula = new javax.swing.JTextField();
        txtBApellido = new javax.swing.JTextField();
        txtBCiudad = new javax.swing.JTextField();
        chCedula = new javax.swing.JCheckBox();
        chApellidos = new javax.swing.JCheckBox();
        chCiudad = new javax.swing.JCheckBox();
        btnbuscar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        btnRefrescar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
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
        setPreferredSize(new java.awt.Dimension(1270, 708));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setPreferredSize(new java.awt.Dimension(1116, 114));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("REGISTRO DE CONDUCTORES");

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/conductor.png"))); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(80, 90));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(382, 382, 382)
                .addComponent(jLabel13)
                .addGap(6, 6, 6)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(381, 381, 381))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 114, Short.MAX_VALUE)
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setPreferredSize(new java.awt.Dimension(1272, 555));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setPreferredSize(new java.awt.Dimension(470, 450));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel1.setText("Cedula*:");

        txtCedula.setPreferredSize(new java.awt.Dimension(253, 37));
        txtCedula.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCedulaFocusLost(evt);
            }
        });
        txtCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCedulaActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel2.setText("Nombres*:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel3.setText("Apellidos*:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel4.setText("Telefono*:");

        txtNombres.setPreferredSize(new java.awt.Dimension(253, 37));

        txtApellidos.setPreferredSize(new java.awt.Dimension(253, 37));

        txtTelefono.setPreferredSize(new java.awt.Dimension(253, 37));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel5.setText("Departamento*:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel6.setText("Ciudad*:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel7.setText("Direccion*:");

        cmbDepartamento.setPreferredSize(new java.awt.Dimension(253, 37));
        cmbDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDepartamentoActionPerformed(evt);
            }
        });

        cmbCiudad.setPreferredSize(new java.awt.Dimension(253, 37));

        txtDireccion.setPreferredSize(new java.awt.Dimension(253, 37));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbCiudad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbDepartamento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(91, Short.MAX_VALUE))
        );

        txtCedula.getAccessibleContext().setAccessibleName("txtCedula");
        txtNombres.getAccessibleContext().setAccessibleName("txtNombres");
        txtApellidos.getAccessibleContext().setAccessibleName("txtApellidos");
        txtTelefono.getAccessibleContext().setAccessibleName("txtTelefono");
        txtDireccion.getAccessibleContext().setAccessibleName("txtDireccion");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setPreferredSize(new java.awt.Dimension(523, 161));

        btnCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/save.png"))); // NOI18N
        btnCrear.setText("Guardar");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/modificar.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/limpiar.png"))); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.setPreferredSize(new java.awt.Dimension(102, 35));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnCrear.getAccessibleContext().setAccessibleName("btnCrear");
        btnCrear.getAccessibleContext().setAccessibleDescription("");
        btnCrear.getAccessibleContext().setAccessibleParent(this);
        btnModificar.getAccessibleContext().setAccessibleName("btnModificar");
        btnLimpiar.getAccessibleContext().setAccessibleName("btnLimpiar");
        btnEliminar.getAccessibleContext().setAccessibleName("btnEliminar");
        btnEliminar.getAccessibleContext().setAccessibleDescription("");

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setPreferredSize(new java.awt.Dimension(710, 123));

        txtBCedula.setEditable(false);
        txtBCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBCedulaActionPerformed(evt);
            }
        });

        txtBApellido.setEditable(false);
        txtBApellido.setSelectedTextColor(new java.awt.Color(240, 240, 240));

        txtBCiudad.setEditable(false);
        txtBCiudad.setDisabledTextColor(new java.awt.Color(240, 240, 240));

        chCedula.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chCedula.setText("Cedula");
        chCedula.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chCedulaItemStateChanged(evt);
            }
        });

        chApellidos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chApellidos.setText("Apellidos");
        chApellidos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chApellidosItemStateChanged(evt);
            }
        });

        chCiudad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chCiudad.setText("Ciudad");
        chCiudad.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chCiudadItemStateChanged(evt);
            }
        });

        btnbuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/search.png"))); // NOI18N
        btnbuscar.setText("Buscar");
        btnbuscar.setMinimumSize(new java.awt.Dimension(101, 4));
        btnbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscarActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel10.setText("Busquedas:");

        btnRefrescar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/actualizar.png"))); // NOI18N
        btnRefrescar.setText("Refrescar");
        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
            }
        });

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/search2.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtBCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chCedula))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chApellidos)
                            .addComponent(txtBApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtBCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chCiudad))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefrescar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRefrescar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(4, 4, 4)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chApellidos, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(chCedula)
                                .addComponent(chCiudad)))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtBCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtBApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtBCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(6, 6, 6))
        );

        txtBCedula.getAccessibleContext().setAccessibleName("txtBCedula");
        txtBApellido.getAccessibleContext().setAccessibleName("txtBApellido");
        txtBCiudad.getAccessibleContext().setAccessibleName("txtBCiudad");
        chCedula.getAccessibleContext().setAccessibleName("chCedula");
        chApellidos.getAccessibleContext().setAccessibleName("chApellidos");
        chCiudad.getAccessibleContext().setAccessibleName("chCiudad");
        btnbuscar.getAccessibleContext().setAccessibleName("btnbuscar");
        btnRefrescar.getAccessibleContext().setAccessibleName("btbRefrescar");

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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 153));
        jLabel29.setText("*Seleccione un registro para editar su información.");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 817, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );

        jPanel3.getAccessibleContext().setAccessibleName("txtBDepartamento");

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
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1340, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1340, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedulaActionPerformed
    }//GEN-LAST:event_txtCedulaActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        cdt.crearConductor();
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        cdt.limpiarRegistros();
         cdt.estado ="crear";
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        cdt.modificarConductor("modificar");
    }//GEN-LAST:event_btnModificarActionPerformed

    private void cmbDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDepartamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbDepartamentoActionPerformed

    private void btnbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscarActionPerformed
       cdt.buscar();
    }//GEN-LAST:event_btnbuscarActionPerformed

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed
        cdt.crearModelo();
    }//GEN-LAST:event_btnRefrescarActionPerformed

    private void chCedulaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chCedulaItemStateChanged
        if (chCedula.isSelected()==true){
            txtBCedula.setEditable(true);

        }else{
            txtBCedula.setEditable(false);
            txtBCedula.setText("");
        }
    }//GEN-LAST:event_chCedulaItemStateChanged

    private void chApellidosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chApellidosItemStateChanged
        if (chApellidos.isSelected()==true){
            txtBApellido.setEditable(true);
        }else{
            txtBApellido.setEditable(false);
            txtBApellido.setText("");
        }
    }//GEN-LAST:event_chApellidosItemStateChanged

    private void chCiudadItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chCiudadItemStateChanged
        // TODO add your handling code here:
        if (chCiudad.isSelected()==true){
            txtBCiudad.setEditable(true);

        }else{
            txtBCiudad.setEditable(false);
            txtBCiudad.setText("");
        }
    }//GEN-LAST:event_chCiudadItemStateChanged

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        cdt.modificarConductor("eliminar");
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtCedulaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCedulaFocusLost
        // TODO add your handling code here:
         if (ext.validar("c", this.txtCedula.getText()) && cdt.estado=="crear") {
        JOptionPane.showMessageDialog(null, "La cedula ya se encuentra registrada en el sistema");
        }
    }//GEN-LAST:event_txtCedulaFocusLost

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        cdt.limpiarRegistros();
        cdt.tabla_campos();
    }//GEN-LAST:event_jTable1KeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
         cdt.limpiarRegistros();
         cdt.tabla_campos();
    }//GEN-LAST:event_jTable1MouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        cdt.desactivar_checkbox();
        cdt.crearModelo();
    }//GEN-LAST:event_formWindowClosed

    private void txtBCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBCedulaActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        bascula.abrirAgricultor();
        hide();
        //setVisible(false);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void mnConductorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnConductorActionPerformed
        //bascula.abrirConductor();
    }//GEN-LAST:event_mnConductorActionPerformed

    private void mnTipo_ArrozActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnTipo_ArrozActionPerformed
        bascula.tipo_de_arroz();
        hide();
    }//GEN-LAST:event_mnTipo_ArrozActionPerformed

    private void mnVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnVehiculoActionPerformed
        bascula.abrirVehiculo();
        hide();
    }//GEN-LAST:event_mnVehiculoActionPerformed

    private void mnLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnLoteActionPerformed
        bascula.abrirLote();
        hide();
    }//GEN-LAST:event_mnLoteActionPerformed

    private void mnTiqueteMateriaPrimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnTiqueteMateriaPrimaActionPerformed
        bascula.abrirTiqueteMateriaPrima();
        hide();
    }//GEN-LAST:event_mnTiqueteMateriaPrimaActionPerformed

    private void mnTiqueteVariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnTiqueteVariosActionPerformed
        bascula.abrirTiqueteVarios();
        hide();
    }//GEN-LAST:event_mnTiqueteVariosActionPerformed

    private void mnVerTiqueteMateriaPrimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnVerTiqueteMateriaPrimaActionPerformed
        bascula.abrirVerTiqueteMateriaPrima();
        hide();
    }//GEN-LAST:event_mnVerTiqueteMateriaPrimaActionPerformed

    private void mnVerTiqueteVariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnVerTiqueteVariosActionPerformed
        bascula.abrirVerTiqueteVarios();
        hide();
    }//GEN-LAST:event_mnVerTiqueteVariosActionPerformed

    private void menuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSalirActionPerformed
        bascula.salir();
        dispose();
    }//GEN-LAST:event_menuSalirActionPerformed

    public void municipio() {
        cmbDepartamento.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cargar.cargarMunicipio(String.valueOf(cmbDepartamento.getSelectedIndex()+1),cmbCiudad);
            }
        });
    }

    public static void main(String args[]) {//Main de la clase
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Conductor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnCrear;
    public static javax.swing.JButton btnEliminar;
    public static javax.swing.JButton btnLimpiar;
    public static javax.swing.JButton btnModificar;
    public static javax.swing.JButton btnRefrescar;
    public static javax.swing.JButton btnbuscar;
    public static javax.swing.JCheckBox chApellidos;
    public static javax.swing.JCheckBox chCedula;
    public static javax.swing.JCheckBox chCiudad;
    public static javax.swing.JComboBox<String> cmbCiudad;
    public static javax.swing.JComboBox<String> cmbDepartamento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    public static javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    public static javax.swing.JTable jTable1;
    private javax.swing.JMenuItem menuSalir;
    public static javax.swing.JMenuItem mnConductor;
    public static javax.swing.JMenuItem mnLote;
    public static javax.swing.JMenuItem mnTipo_Arroz;
    public static javax.swing.JMenuItem mnTiqueteMateriaPrima;
    public static javax.swing.JMenuItem mnTiqueteVarios;
    public static javax.swing.JMenuItem mnVehiculo;
    public static javax.swing.JMenuItem mnVerTiqueteMateriaPrima;
    public static javax.swing.JMenuItem mnVerTiqueteVarios;
    public static javax.swing.JTextField txtApellidos;
    public static javax.swing.JTextField txtBApellido;
    public static javax.swing.JTextField txtBCedula;
    public static javax.swing.JTextField txtBCiudad;
    public static javax.swing.JTextField txtCedula;
    public static javax.swing.JTextField txtDireccion;
    public static javax.swing.JTextField txtNombres;
    public static javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
