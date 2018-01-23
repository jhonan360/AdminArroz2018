
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Bascula;

import javax.swing.JOptionPane;
import Negocio.Conexion;
import Interfaces.Bascula;
import Interfaces.Tipo_Arroz;
import Interfaces.Conductor;
import Interfaces.Agricultor;
import Interfaces.Lote;
import Interfaces.Login;
import Interfaces.Vehiculo;
import Interfaces.BusquedasTiquete;
import Interfaces.TiqueteVarios;
import Logica.Extras.login;
import Logica.Extras.tablas;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
//import java.util.Date;

/**
 *
 * @author uriel
 */
public class bascula {

    public static Bascula Bas;
    public static TiqueteVarios TiqVarios;
    public static Tipo_Arroz Arroz;
    public static Conductor Conductor;
    public static Agricultor Agricultor;
    public static Vehiculo Vehiculo;
    public static Lote Lote;
    public static Lote Zona;
    public static Login Login;
    public static login login;
    public static BusquedasTiquete BusTiquete;
    public static busquedasTiquete busTiquete;
    public static String columEspera[] = new String[]{"N", "Agricultor", "Tipo Arroz"};
    public static DefaultTableModel modeloentrada;
    public static String fecha, lote, tipoArroz, placa, agricultor, conductor, user, ccAgricultor, ccConductor, observacion;
    public static ResultSet rs, rsbus, rsagricultor;
    public static ResultSet rslote, rslote2, rslotes;
    public static ResultSet rstipo, rstipo2, rstipos;
    public static ResultSet rsconductor, rsconductor2, rsconductores;
    public static ResultSet rsplaca, rsplaca2, rsinsertar;
    public static ResultSet rsnum, rsTiquete;
    public String idLotes;
    public static Statement st;
    public static Conexion Con;
    public static tablas tbl;
    public static int row1 = 0, row2 = 0; //variables para notificaciones de tiquetes en espera

    public bascula() {
        //numeroTiquete();
        fecha();
        //cargarLote();
        //cargarTipoArroz();
        tbl = new tablas();
    }

    public static void tipo_de_arroz() {
        if (!(Arroz instanceof Tipo_Arroz)) {
            Arroz = new Tipo_Arroz();
            Arroz.setVisible(true);
        } else {
            Arroz.setVisible(true);
        }
    }

    public static void abrirConductor() {
        if (!(Conductor instanceof Conductor)) {
            Conductor = new Conductor();
            Conductor.setVisible(true);
        } else {
            Conductor.setVisible(true);
        }
    }

    public static void abrirAgricultor() {
        if (!(Agricultor instanceof Agricultor)) {
            Agricultor = new Agricultor();
            Agricultor.setVisible(true);
        } else {
            Agricultor.setVisible(true);
        }
    }

    public static void abrirVehiculo() {
        if (!(Vehiculo instanceof Vehiculo)) {
            Vehiculo = new Vehiculo();
            Vehiculo.setVisible(true);
        } else {
            Vehiculo.setVisible(true);
        }
    }

    public static void abrirLote() {
        if (!(Lote instanceof Lote)) {
            Lote = new Lote();
            Lote.setVisible(true);
        } else {
            Lote.setVisible(true);
        }
    }

    public static void abrirTiqueteMateriaPrima() {
        if (!(Bas instanceof Bascula)) {
            Bas = new Bascula();
            Bas.setVisible(true);
        } else {
            Bas.setVisible(true);
        }
    }

    public static void abrirTiqueteVarios() {
        if (!(TiqVarios instanceof TiqueteVarios)) {
            TiqVarios = new TiqueteVarios();
            TiqVarios.setVisible(true);
        } else {
            TiqVarios.setVisible(true);
        }
    }

    public static void salir() {
        Login = new Login();
        Login.setVisible(true);

    }

    /*
    *----------------------------------------TIQUETES EN ESPERA------------------------------------------------------------
     */
    public static void tiquetes_en_espera(boolean visible) {

        modeloentrada = new DefaultTableModel(null, columEspera) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tbl.llenarTabla(Bas.tblEspera, modeloentrada, columEspera.length, "SELECT tiquete.idTiquete, CONCAT(personalexterno.nombres,' ',personalexterno.apellidos), CONCAT(tipodearroz.nombre,' - ',variedad.nombre) FROM tiquete,personalexterno,tipodearroz,variedad WHERE tiquete.idConductor IS NULL AND tiquete.kilosBrutos IS NULL AND tiquete.idAgricultor=personalexterno.idPersonalExterno AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad ORDER BY tiquete.idTiquete DESC;");

        if (row1 == 0) {
            row1 = modeloentrada.getRowCount();
        } else {
            row2 = modeloentrada.getRowCount();
        }

        if (row1 < row2) {
            String resta = String.valueOf(row2 - row1);
            if (!visible) {
                JOptionPane.showMessageDialog(null, "tiene " + row2 + " notificaciones pendientes");
            } else {
                JOptionPane.showMessageDialog(null, "tiene " + resta + " notificaciones");
            }
            row1 = 0;
            row2 = 0;
        }
    }

    public void tablaCampos_TiquetesEspera() {
        int rec = Bas.tblEspera.getSelectedRow();
        String idTiqueteEspera = Bas.tblEspera.getValueAt(rec, 0).toString();
        Bas.lblNumeroTiquete.setText(Bas.tblEspera.getValueAt(rec, 0).toString());
        Bas.txtAgricultor.setText(Bas.tblEspera.getValueAt(rec, 1).toString());
        String tipoArroz = Bas.tblEspera.getValueAt(rec, 2).toString();
        Bas.cmbTipoArroz.setSelectedItem(tipoArroz);

        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();

            rsTiquete = st.executeQuery("SELECT humedadUno,impurezaUno FROM tiquete WHERE idTiquete='" + idTiqueteEspera + "'");
            while (rsTiquete.next()) {
                Bas.lblHumedad.setText(rsTiquete.getString(1));
                Bas.lblImpureza.setText(rsTiquete.getString(2));
            }

            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ------------------------------------TIQUETE DE ENTRADA DE MATERIA
     * PRIMA----------------------------------------------
     */
    /*public static void numeroTiquete() {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement(); //System.out.println("1"); 
            rsnum = st.executeQuery("SELECT idTiquete FROM tiquete ORDER BY idTiquete ASC");
            System.out.println(rsnum);

            while (rsnum.next()) {
                if (rsnum.getString(1) == null) {
                } else {
                    String resultado = rsnum.getString(1);
                    int numero = Integer.parseInt(resultado);
                    int num2 = numero + 1;
                    String resul = Integer.toString(num2);
                    Bas.lblNumeroTiquete.setText(resul);
                }
            }
            Con.Desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
    public void fecha() {
        Calendar c;
        c = Calendar.getInstance();
        int d = c.get(Calendar.DATE), m = 1 + (c.get(Calendar.MONTH)), a = c.get(Calendar.YEAR);
        Bas.txtFecha.setText(a + "/" + m + "/" + d);
    }

    /*public void cargarLote() {
        try {
            Con.Conectar();
            st = Con.conexion.createStatement();
<<<<<<< HEAD
            Bas.cmbLote.removeAllItems();
            rslote = st.executeQuery("SELECT idLote,nombre FROM lote");
            while (rslote.next()) {
                Bas.cmbLote.addItem(rslote.getString(2));
            }
            String seleccion = (Bas.cmbLote.getSelectedItem().toString());
            rslote2 = st.executeQuery("SELECT idLote FROM lote WHERE nombre='" + seleccion + "' ");
            while (rslote2.next()) {
                String lote = rslote2.getString(1);
                //System.out.println("lote" + lote);
=======
            Bas.cmbZona.removeAllItems();
            rszona = st.executeQuery("SELECT idLote,nombre FROM lote");
            while (rszona.next()) {
                Bas.cmbZona.addItem(rszona.getString(2));
            }
            String seleccion = (Bas.cmbZona.getSelectedItem().toString());
            rszona2 = st.executeQuery("SELECT idLote FROM lote WHERE nombre='" + seleccion + "' ");
            while (rszona2.next()) {
                String zona = rszona2.getString(1);
                //System.out.println("zona" + zona);
>>>>>>> c73b50a1cb0570d9eea4dce553e463bd662310a2
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getIdLote(String nombre) {
        try {
            Con.Conectar();
            st = Con.conexion.createStatement();
<<<<<<< HEAD
<<<<<<< HEAD
            rslotes = st.executeQuery("SELECT idLote FROM lote WHERE nombre='" + nombre + "'");
            while (rslotes.next()) {
                return rslotes.getString(1);
=======
=======
>>>>>>> c73b50a1cb0570d9eea4dce553e463bd662310a2
            rszonas = st.executeQuery("SELECT idLote FROM lote WHERE nombre='" + nombre + "'");
            while (rszonas.next()) {
                return rszonas.getString(1);
>>>>>>> c73b50a1cb0570d9eea4dce553e463bd662310a2
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }*/

 /*
    public void cargarTipoArroz() {
        try {
            Con.Conectar();
            st= Con.conexion.createStatement();
            Bas.cmbTipoArroz.removeAllItems();
            rstipo= st.executeQuery("SELECT idTipoDeArroz,nombre FROM tipodearroz");
            while (rstipo.next()) {
                Bas.cmbTipoArroz.addItem(rstipo.getString(2));
            }
            String seleccion = (Bas.cmbTipoArroz.getSelectedItem().toString());
            rstipo2
                    = st.executeQuery("SELECT idTipoDeArroz FROM tipodearroz WHERE nombre='"
                            + seleccion + "' ");
            while (rstipo2.next()) {
                String tipo= rstipo2.getString(1); //System.out.println(tipo); 
            } 
            Con.Desconectar(); 
            }
                catch (Exception e) { e.printStackTrace(); }
            }
        }
    }
      

    public static String getIdTipo(String nombre) {
        try {
            Con.Conectar();
            st= Con.conexion.createStatement();
            rstipos = st.executeQuery("SELECT idTipoDeArroz FROM tipodearroz WHERE nombre ='" + nombre + "'  "); 
            while (rstipos.next()) {
                return rstipos.getString(1);
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }*/
    public void buscarPlaca() {
        String bus = JOptionPane.showInputDialog("Ingrese la placa del vehiculo");
        boolean bandera = false;
        if (bus != null) {
            try {
                Con.Conectar();
                st = Con.conexion.createStatement();
                rsplaca = st.executeQuery("SELECT placa FROM vehiculo WHERE placa='" + bus + "'");

                while (rsplaca.next()) {
                    if (rsplaca.getString(1) == null) {
                    } else {
                        String resultado = rsplaca.getString(1);
                        // System.out.println(resultado);
                        bandera = true;
                        Bas.txtPlaca.setText(resultado);

                    }
                }

                if (bandera == false) {
                    int valor = JOptionPane.showConfirmDialog(null, "La placa no pertenece a un vehiculo registrado" + "\n               Desea registrarlo ahora?", "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (valor == JOptionPane.YES_OPTION) {
                        abrirVehiculo();
                        Vehiculo.txtPlaca.setText(bus);
                    } else {
                        JOptionPane.showMessageDialog(null, "Se ha cancelado la operación.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * public static String getCedulaAgricultor(String nombre) { try {
     * System.out.println("..." + nombre); Con = new Conexion(); st=
     * Con.conexion.createStatement(); rsagricultor = st.executeQuery("SELECT
     * ccAgricultor FROM agricultor WHERE nombres ='" + nombre + "' "); *
     * while(rsagricultor.next()) { System.out.println("..." + rsagricultor);
     * return rsagricultor.getString(1); } Con.Desconectar(); } catch (Exception
     * e) { e.printStackTrace(); } return ""; }
     *
     * public static String getCedulaConductor(String nombre) { try {
     * //System.out.println("..."+nombre); Con = new Conexion(); st =
     * Con.conexion.createStatement(); rsconductores = st.executeQuery("SELECT
     * ccConductor FROM conductor WHERE nombres='" + nombre + "'"); while
     * (rsconductores.next()) { return rsconductores.getString(1); }
     * Con.Desconectar(); } catch (Exception e) { e.printStackTrace(); } return
     * ""; }
     */
    public static void abrirBusquedasTiquete(int num,String TiqPrincipal) {
        BusTiquete = new BusquedasTiquete(TiqPrincipal);
        BusTiquete.setVisible(true);

        switch (num) {
            case 1:
                //panel agricultor
                BusTiquete.panel.setEnabledAt(0, true);
                BusTiquete.panel.setEnabledAt(1, false);
                Bas.btnBuscarConductor.setEnabled(false);
                Bas.btnBuscarPlaca.setEnabled(false);
                BusTiquete.panel.setSelectedIndex(0);
                break;
            case 2:
                //panel vehiculo
                BusTiquete.panel.setEnabledAt(0, false);
                BusTiquete.panel.setEnabledAt(1, true);
                Bas.btnEditarAgricultor.setEnabled(false);
                Bas.btnBuscarPlaca.setEnabled(false);
                BusTiquete.panel.setSelectedIndex(1);
                break;
        }
    }

    public static void capturarPeso(int opc) {
        int inicial = (int) Math.floor(Math.random() * (5000 - 1500 + 1) + 1500);
        int fina = (int) Math.floor(Math.random() * (1400 - 800) + 800);
        switch (opc) {
            case 1:
                Bas.txtPesoInicial.setText("");
                Bas.txtPesoInicial.setText(String.valueOf(inicial));
                break;
            case 2:
                if (!Bas.txtPesoInicial.getText().equals("")) {
                    Bas.txtPesoFinal.setText("");
                    Bas.txtPesoFinal.setText(String.valueOf(fina));
                    int ini = Integer.parseInt(Bas.txtPesoInicial.getText());
                    Bas.txtPesoNeto.setText(String.valueOf(ini - fina));
                } else {
                    JOptionPane.showMessageDialog(null, "Sin peso bruto");
                }
                break;
        }
    }

    public static void crearTiquete() {

        fecha = Bas.txtFecha.getText();
        agricultor = ccAgricultor;
        lote = Bas.cmbLote.getSelectedItem().toString();
        tipoArroz = Bas.cmbTipoArroz.getSelectedItem().toString();
        user = login.enviarUsuario();
        conductor = ccConductor;
        //conductor = busTiquete.tabla_camposConductor();
        placa = Bas.txtPlaca.getText();
        observacion = Bas.txtObservacion.getText();

        System.out.println("fecha " + fecha);
        System.out.println("agric " + agricultor);
        System.out.println("lote: " + lote);
        System.out.println("tipo " + tipoArroz);
        System.out.println("user " + user);
        System.out.println("conduc " + conductor);
        System.out.println("placa " + placa);

        if (!fecha.equals("") && !agricultor.equals("") && !lote.equals("") && !tipoArroz.equals("") && !user.equals("") && !conductor.equals("") && !placa.equals("") && !observacion.equals("")) {
            //lote = getIdLote(lote);

            lote = String.valueOf(Bascula.cmbLote.getSelectedIndex() + 1);
            //tipoArroz = getIdTipo(tipoArroz);
            //agricultor = getCedulaAgricultor(agricultor);
            //conductor = getCedulaConductor(conductor);

            insertar(fecha, agricultor, lote, tipoArroz, user, conductor, placa, observacion);//Llamado al metodo insertar
            limpiarRegistros();
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        }
    }

    public static void insertar(String fecha, String agricultor, String lote, String tipoArroz, String usuario, String conductor, String placa, String observacion) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            st.executeUpdate("INSERT INTO tiquete (idTiquete,fecha,idAgricultor,idLote,idTipoDeArroz,user,idConductor,idVehiculo,observacion) VALUES (0,'" + fecha + "','" + agricultor + "','" + lote + "','" + tipoArroz + "','" + usuario + "','" + conductor + "','" + placa + "','" + observacion + "')");
            JOptionPane.showMessageDialog(null, "Tiquete registrado");
            //numeroTiquete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void limpiarRegistros() {
        Bas.txtAgricultor.setText("");
        Bas.cmbLote.setSelectedIndex(0);
        Bas.cmbTipoArroz.setSelectedIndex(0);
        Bas.txtConductor.setText("");
        Bas.txtPlaca.setText("");
        Bas.txtObservacion.setText("");
        Bas.txtPesoInicial.setText("");
        Bas.txtPesoFinal.setText("");
        Bas.txtPesoNeto.setText("");
    }

    void cerrarVentana(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
