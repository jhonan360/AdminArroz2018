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
import Interfaces.VerTiquetePrincipal;
import Interfaces.VerTiqueteVarios;
import Logica.Extras.extras;
import Logica.Extras.login;
import Logica.Extras.tablas;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Negocio.ConexionBascula;

/**
 *
 * @author uriel
 */
public class bascula {

    public static Bascula Bas;
    public static TiqueteVarios TiqVarios;
    public static VerTiquetePrincipal VerTiqPrincipal;
    public static VerTiqueteVarios VerTiqVarios;
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
    public static String columSegundoPesaje[] = new String[]{"N", "Agricultor", "KL Brutos"};
    public static DefaultTableModel modeloentrada, modeloSegundoPesaje;
    public static String idTiquete, fecha, lote, tipoArroz, placa, idAgricultor, agricultor, idConductor, conductor, user, ccAgricultor, ccConductor, idVehiculo, observacion, kilosBrutos, destare, kilosNetos;
    public static ResultSet rs, rsbus, rsagricultor;
    public static ResultSet rslote, rslote2, rslotes;
    public static ResultSet rstipo, rstipo2, rstipos;
    public static ResultSet rsconductor, rsconductor2, rsconductores;
    public static ResultSet rsplaca, rsplaca2, rsinsertar;
    public static ResultSet rsnum, rsTiquete;
    public String idLotes;
    public static extras ext;
    public static Statement st;
    public static Conexion Con;
    public static tablas tbl;
    public static int row1 = 0, row2 = 0; //variables para notificaciones de tiquetes en espera
    public static String idTiqueteEspera, estadoTiquete;
    public static boolean estado;
    public static ConexionBascula ConBascula;
    //public static Double kilosBrutoss,destare,kilosNetos;

    public bascula() {
        ConBascula = new ConexionBascula();
        //numeroTiquete();
        fecha();
        ext = new extras();
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

    public static void abrirVerTiqueteMateriaPrima() {
        if (!(VerTiqPrincipal instanceof VerTiquetePrincipal)) {
            VerTiqPrincipal = new VerTiquetePrincipal();
            VerTiqPrincipal.setVisible(true);
        } else {
            VerTiqPrincipal.setVisible(true);
        }
    }

    public static void abrirVerTiqueteVarios() {
        if (!(VerTiqVarios instanceof VerTiqueteVarios)) {
            VerTiqVarios = new VerTiqueteVarios();
            VerTiqVarios.setVisible(true);
        } else {
            VerTiqVarios.setVisible(true);
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

        estado = true;
        int rec = Bas.tblEspera.getSelectedRow();
        idTiqueteEspera = Bas.tblEspera.getValueAt(rec, 0).toString();
        Bas.lblNumeroTiquete.setText(idTiqueteEspera);
        Bas.txtAgricultor.setText(Bas.tblEspera.getValueAt(rec, 1).toString());
        String tipoArroz = Bas.tblEspera.getValueAt(rec, 2).toString();
        Bas.cmbTipoArroz.setSelectedItem(tipoArroz);

        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();

            rsTiquete = st.executeQuery("SELECT humedadUno,impurezaUno,idAgricultor FROM tiquete WHERE idTiquete='" + idTiqueteEspera + "'");
            while (rsTiquete.next()) {

                Bas.lblHumedad.setText(rsTiquete.getString(1));
                Bas.lblImpureza.setText(rsTiquete.getString(2));
                idAgricultor = rsTiquete.getString(3);
                estadoTiquete = "enEspera";
            }

            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * TIQUETES PENDIENTES POR REGISTRAR SEGUNDO PESAJE
     */
    public static void tiquetesEsperandoSegundoPesaje(boolean visible) {

        modeloSegundoPesaje = new DefaultTableModel(null, columSegundoPesaje) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tbl.llenarTabla(Bas.tblSegundoPesaje, modeloSegundoPesaje, columSegundoPesaje.length, "SELECT tiquete.idTiquete, CONCAT(personalexterno.nombres,' ',personalexterno.apellidos), tiquete.kilosBrutos FROM tiquete,personalexterno,tipodearroz,variedad,lote WHERE tiquete.destare=0.00 AND tiquete.kilosNetos=0.00 AND tiquete.idAgricultor=personalexterno.idPersonalExterno AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad and tiquete.idLote=lote.idLote ORDER BY tiquete.idTiquete DESC;");
    }

    public void tablaCampos_SegundoPesaje(String SegundoPeso) {
        estado = false;
        int rec = Bas.tblSegundoPesaje.getSelectedRow();
        idTiqueteEspera = Bas.tblSegundoPesaje.getValueAt(rec, 0).toString();
        Bas.lblNumeroTiquete.setText(idTiqueteEspera);
        Bas.txtAgricultor.setText(Bas.tblSegundoPesaje.getValueAt(rec, 1).toString());
        Bas.txtPesoInicial.setText(Bas.tblSegundoPesaje.getValueAt(rec, 2).toString());

        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();

            rsTiquete = st.executeQuery("SELECT CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,vehiculo.placa,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),tiquete.observacion,humedadUno,impurezaUno,idAgricultor,idConductor FROM tiquete,personalexterno,tipodearroz,variedad,lote,vehiculo WHERE idTiquete='" + idTiqueteEspera + "' AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo AND tiquete.idConductor=personalexterno.idPersonalExterno");

            while (rsTiquete.next()) {
                Bas.cmbTipoArroz.setSelectedItem(rsTiquete.getString(1));
                Bas.cmbLote.setSelectedItem(rsTiquete.getString(2));
                Bas.txtPlaca.setText(rsTiquete.getString(3));
                Bas.txtConductor.setText(rsTiquete.getString(4));
                Bas.txtObservacion.setText(rsTiquete.getString(5));
                Bas.lblHumedad.setText(rsTiquete.getString(6));
                Bas.lblImpureza.setText(rsTiquete.getString(7));

                idAgricultor = rsTiquete.getString(8);
                idConductor = rsTiquete.getString(9);
                estadoTiquete = "segundoPesaje";
            }

            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String fecha() {
        SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd");
        SimpleDateFormat formato2 = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
        java.util.Date fecha = new Date();
        String fec = formato.format(fecha);
        String fec2 = formato2.format(fecha);
        Bas.txtFecha.setText(fec);
        return fec2;
    }

    public static void abrirBusquedasTiquete(int num, String TiqPrincipal) {
        BusTiquete = new BusquedasTiquete(TiqPrincipal);
        BusTiquete.setVisible(true);

        switch (num) {
            case 1:
                //panel agricultor
                BusTiquete.panel.setEnabledAt(0, true);
                BusTiquete.panel.setEnabledAt(1, false);
                BusTiquete.panel.setEnabledAt(2, false);
                Bas.btnBuscarConductor.setEnabled(false);
                Bas.btnBuscarPlaca.setEnabled(false);
                BusTiquete.panel.setSelectedIndex(0);
                break;
            case 2:
                //panel vehiculo
                BusTiquete.panel.setEnabledAt(0, false);
                BusTiquete.panel.setEnabledAt(1, true);
                BusTiquete.panel.setEnabledAt(2, false);
                Bas.btnEditarAgricultor.setEnabled(false);
                Bas.btnBuscarPlaca.setEnabled(false);
                BusTiquete.panel.setSelectedIndex(1);
                break;

            case 3:
                //panel vehiculo
                BusTiquete.panel.setEnabledAt(0, false);
                BusTiquete.panel.setEnabledAt(1, false);
                BusTiquete.panel.setEnabledAt(2, true);
                Bas.btnEditarAgricultor.setEnabled(false);
                Bas.btnBuscarConductor.setEnabled(false);
                BusTiquete.panel.setSelectedIndex(2);
                break;
        }
    }

    public static void capturarPeso(int opc) {
        double inicial = (double) Math.floor(Math.random() * (5000 - 1500 + 1) + 1500);
        double fina = (double) Math.floor(Math.random() * (1400 - 800) + 800);
        switch (opc) {
            case 1:
                Bas.txtPesoInicial.setText("");
                //Bas.txtPesoInicial.setText(ConBascula.getPeso("0"));
                Bas.txtPesoInicial.setText(String.valueOf(inicial));
                if (!Bas.txtPesoInicial.getText().equals("")) {
                    Bas.btnCapturarInicial.setEnabled(false);
                }
                break;
            case 2:
                if (!Bas.txtPesoInicial.getText().equals("")) {
                    Bas.txtPesoFinal.setText("");
                    Bas.txtPesoFinal.setText(String.valueOf(fina));
                    //Bas.txtPesoFinal.setText(ConBascula.getPeso(Bas.txtPesoInicial.getText()));
                    double ini = Double.parseDouble(Bas.txtPesoInicial.getText());
                    if (!Bas.txtPesoFinal.getText().equals("")) {
                        fina = Double.parseDouble(Bas.txtPesoFinal.getText());
                        Bas.txtPesoNeto.setText(String.valueOf(ini - fina));
                        Bas.btnCapturarFinal.setEnabled(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Sin peso bruto");
                }
                break;
        }
    }

    public static void crearTiquete() {
        idTiquete = Bas.lblNumeroTiquete.getText();
        agricultor = idAgricultor;
        fecha = fecha();
        lote = String.valueOf(Bascula.cmbLote.getSelectedIndex() + 1);
        tipoArroz = String.valueOf(Bascula.cmbTipoArroz.getSelectedIndex() + 1);
        user = login.enviarUsuario();
        conductor = idConductor;
        /**
         * if (conductor==null){ conductor=""; }*
         */
        placa = Bas.txtPlaca.getText();
        observacion = Bas.txtObservacion.getText();
        kilosBrutos = Bas.txtPesoInicial.getText();
        destare = Bas.txtPesoFinal.getText();
        kilosNetos = Bas.txtPesoNeto.getText();

        if (destare.equals("")) {
            destare = "0.00";
        }
        if (kilosNetos.equals("")) {
            kilosNetos = "0.00";
        }

        System.out.println("idTiq " + idTiquete);
        System.out.println("fecha " + fecha);
        System.out.println("agric " + agricultor);
        System.out.println("lote: " + lote);
        System.out.println("tipo " + tipoArroz);
        System.out.println("user " + user);
        System.out.println("conduc " + conductor);
        System.out.println("observacion " + observacion);
        System.out.println("kilosBrutoss " + kilosBrutos);
        System.out.println("destare " + destare);
        System.out.println("kilsNetos " + kilosNetos);

        if (idTiquete.equals("")) {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        } else {
            switch (estadoTiquete) {
                case "enEspera":
                    if (!fecha.equals("") && agricultor != null && !lote.equals("") && !tipoArroz.equals("") && !user.equals("") && conductor != null && !placa.equals("") && !kilosBrutos.equals("")) {
                        placa = ext.getIdPlaca(placa);
                        System.out.println("placa " + placa);
                        insertar(idTiquete, fecha, agricultor, lote, tipoArroz, user, conductor, placa, observacion, kilosBrutos, destare, kilosNetos);//Llamado al metodo insertar
                        limpiarRegistros();
                    } else {
                        JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
                    }
                    break;

                case "segundoPesaje":
                    if (!fecha.equals("") && !agricultor.equals("") && !lote.equals("") && !tipoArroz.equals("") && !user.equals("") && !conductor.equals("") && !placa.equals("") && !kilosBrutos.equals("0.00") && !kilosNetos.equals("0.00") && !destare.equals("0.00")) {
                        placa = ext.getIdPlaca(placa);
                        System.out.println("placa " + placa);
                        insertar(idTiquete, fecha, agricultor, lote, tipoArroz, user, conductor, placa, observacion, kilosBrutos, destare, kilosNetos);//Llamado al metodo insertar
                        limpiarRegistros();
                    } else {
                        JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
                    }
                    break;
            }
        }
    }

    public static void insertar(String idTiquete, String fecha, String agricultor, String lote, String tipoArroz, String usuario, String conductor, String placa, String observacion, String kilosBrutos, String destare, String kilosNetos) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            st.executeUpdate("UPDATE tiquete SET idAgricultor='" + agricultor + "',idLote='" + lote + "',idVehiculo='" + placa + "',idConductor='" + conductor + "',user='" + user + "',fecha='" + fecha + "',kilosBrutos='" + kilosBrutos + "',destare='" + destare + "',kilosNetos='" + kilosNetos + "',observacion='" + observacion + "' WHERE idTiquete='" + idTiquete + "'");
            JOptionPane.showMessageDialog(null, "Tiquete registrado");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void limpiarRegistros() {
        Bas.lblNumeroTiquete.setText("");
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
