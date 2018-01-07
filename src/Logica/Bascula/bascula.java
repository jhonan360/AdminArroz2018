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
import Interfaces.Zona;
import Interfaces.Login;
import Interfaces.Vehiculo;
import Interfaces.BusquedasTiquete;
import Logica.Extras.login;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import javax.swing.JOptionPane;
//import java.util.Date;

/**
 *
 * @author uriel
 */
public class bascula {

    public static Bascula Bas;
    public static Tipo_Arroz Arroz;
    public static Conductor Conductor;
    public static Agricultor Agricultor;
    public static Vehiculo Vehiculo;
    public static Zona Zona;
    public static Login Login;
    public static login login;
    public static BusquedasTiquete BusTiquete;
    public static busquedasTiquete busTiquete;

    public static String fecha, zona, tipoArroz, placa, agricultor, conductor, user, ccAgricultor, ccConductor;
    public static ResultSet rs, rsbus, rsagricultor;
    public static ResultSet rszona, rszona2, rszonas;
    public static ResultSet rstipo, rstipo2, rstipos;
    public static ResultSet rsconductor, rsconductor2, rsconductores;
    public static ResultSet rsplaca, rsplaca2, rsinsertar;
    public static ResultSet rsnum;
    public String idZonas;
    public static Statement st;
    public static Conexion Con;

    public bascula() {
        numeroTiquete();
        fecha();
        cargarZona();
        cargarTipoArroz();
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

    public static void abrirZona() {
        if (!(Zona instanceof Zona)) {
            Zona = new Zona();
            Zona.setVisible(true);
        } else {
            Zona.setVisible(true);
        }
    }

    public static void salir() {
        Login = new Login();
        Login.setVisible(true);

    }

    public static void abrirBusquedasTiquete(int num) {
        BusTiquete = new BusquedasTiquete();
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
                Bas.btnBuscarAgricultor.setEnabled(false);
                Bas.btnBuscarPlaca.setEnabled(false);
                BusTiquete.panel.setSelectedIndex(1);
                break;
        }
    }

    /**
     * Tiquete de entrada
     */
    public static void numeroTiquete() {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            //System.out.println("1");
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
    }

    public void fecha() {
        Calendar c;
        c = Calendar.getInstance();
        int d = c.get(Calendar.DATE), m = 1 + (c.get(Calendar.MONTH)), a = c.get(Calendar.YEAR);
        Bas.txtFecha.setText(a + "/" + m + "/" + d);

    }

    public void cargarZona() {
        try {
            Con.Conectar();
            st = Con.conexion.createStatement();
            Bas.cmbZona.removeAllItems();
            rszona = st.executeQuery("SELECT idZona,nombre FROM zona");
            while (rszona.next()) {
                Bas.cmbZona.addItem(rszona.getString(2));
            }
            String seleccion = (Bas.cmbZona.getSelectedItem().toString());
            rszona2 = st.executeQuery("SELECT idZona FROM zona WHERE nombre='" + seleccion + "' ");
            while (rszona2.next()) {
                String zona = rszona2.getString(1);
                //System.out.println("zona" + zona);
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getIdZona(String nombre) {
        try {
            Con.Conectar();
            st = Con.conexion.createStatement();
            rszonas = st.executeQuery("SELECT idZona FROM zona WHERE nombre='" + nombre + "'");
            while (rszonas.next()) {
                return rszonas.getString(1);
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void cargarTipoArroz() {
        try {
            Con.Conectar();
            st = Con.conexion.createStatement();
            Bas.cmbTipoArroz.removeAllItems();
            rstipo = st.executeQuery("SELECT idTipoDeArroz,nombre FROM tipodearroz");
            while (rstipo.next()) {
                Bas.cmbTipoArroz.addItem(rstipo.getString(2));
            }
            String seleccion = (Bas.cmbTipoArroz.getSelectedItem().toString());
            rstipo2 = st.executeQuery("SELECT idTipoDeArroz FROM tipodearroz WHERE nombre='" + seleccion + "' ");
            while (rstipo2.next()) {
                String tipo = rstipo2.getString(1);
                //System.out.println(tipo);
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getIdTipo(String nombre) {
        try {
            Con.Conectar();
            st = Con.conexion.createStatement();
            rstipos = st.executeQuery("SELECT idTipoDeArroz FROM tipodearroz WHERE nombre='" + nombre + "'");
            while (rstipos.next()) {
                return rstipos.getString(1);
            }

            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void buscarPlaca() {
        String bus = JOptionPane.showInputDialog("Ingrese la placa del vehiculo");
        boolean bandera = false;
        if (bus != null) {
            try {
                Con.Conectar();
                st = Con.conexion.createStatement();
                rsplaca = st.executeQuery("SELECT idplaca FROM vehiculo WHERE idplaca='" + bus + "'");

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
                    } else {

                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * public static String getCedulaAgricultor(String nombre) { try {
     * System.out.println("..." + nombre); Con = new Conexion(); st =
     * Con.conexion.createStatement(); rsagricultor = st.executeQuery("SELECT
     * ccAgricultor FROM agricultor WHERE nombres='" + nombre + "'"); while
     * (rsagricultor.next()) { System.out.println("..."+rsagricultor); return
     * rsagricultor.getString(1); } Con.Desconectar(); } catch (Exception e) {
     * e.printStackTrace(); } return ""; }
     */

    /*public static String getCedulaConductor(String nombre) {
        try {
            //System.out.println("..."+nombre);
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rsconductores = st.executeQuery("SELECT ccConductor FROM conductor WHERE nombres='" + nombre + "'");
            while (rsconductores.next()) {
                return rsconductores.getString(1);
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }*/
    public static void crearTiquete() {

        fecha = Bas.txtFecha.getText();
        agricultor = ccAgricultor;
        zona = Bas.cmbZona.getSelectedItem().toString();
        tipoArroz = Bas.cmbTipoArroz.getSelectedItem().toString();
        user = login.enviarUsuario();
        conductor = ccConductor;
        //conductor = busTiquete.tabla_camposConductor();
        placa = Bas.txtPlaca.getText();

        System.out.println("fecha " + fecha);
        System.out.println("agric " + agricultor);
        System.out.println("zona: " + zona);
        System.out.println("tipo " + tipoArroz);
        System.out.println("user " + user);
        System.out.println("conduc " + conductor);
        System.out.println("placa " + placa);

        if (!fecha.equals("") && !agricultor.equals("") && !zona.equals("") && !tipoArroz.equals("") && !user.equals("") && !conductor.equals("") && !placa.equals("")) {
            zona = getIdZona(zona);
            tipoArroz = getIdTipo(tipoArroz);
            //agricultor = getCedulaAgricultor(agricultor);
            //conductor = getCedulaConductor(conductor);

            insertar(fecha, agricultor, zona, tipoArroz, user, conductor, placa);//Llamado al metodo insertar
            limpiarRegistros();
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        }
    }

    public static void insertar(String fecha, String agricultor, String zona, String tipoArroz, String usuario, String conductor, String placa) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            st.executeUpdate("INSERT INTO tiquete (idTiquete,fecha,ccAgricultor,idZona,idTipoDeArroz,user,ccConductor,idplaca) VALUES (0,'" + fecha + "','" + agricultor + "','" + zona + "','" + tipoArroz + "','" + usuario + "','" + conductor + "','" + placa + "')");
            JOptionPane.showMessageDialog(null, "Tiquete registrado");
            numeroTiquete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void limpiarRegistros() {
        Bas.txtAgricultor.setText("");
        Bas.cmbZona.setSelectedIndex(0);
        Bas.cmbTipoArroz.setSelectedIndex(0);
        Bas.txtConductor.setText("");
        Bas.txtPlaca.setText("");
        Bas.txtPesoInicial.setText("");
        Bas.txtPesoFinal.setText("");
        Bas.txtPesoNeto.setText("");
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

    void cerrarVentana(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
