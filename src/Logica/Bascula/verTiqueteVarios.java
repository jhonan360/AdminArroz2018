/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Bascula;

import Interfaces.BusquedasTiquete;
import Negocio.Conexion;
import Interfaces.TiqueteVarios;
import Interfaces.VerTiqueteVarios;
import static Logica.Bascula.bascula.Vehiculo;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import Logica.Extras.tablas;
import Logica.Extras.extras;
import Logica.Extras.log;
import Logica.Extras.login;
import com.toedter.calendar.JDateChooser;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Lizeth
 */
public class verTiqueteVarios {

    public static Conexion Con;
    public static ResultSet rs;
    public static Statement st;
    public static VerTiqueteVarios VerTiqVarios;
    public static extras ext;
    public tablas tbl;
    public static String placa, color, modelo, marca, idVehiculo, idTiqueteVarios;
    public static DefaultTableModel modelVerTiqVarios, modelEntradas;
    public static String columnas[] = new String[]{"N°", "Fecha", "Conductor", "Vehículo", "Destino", "Observación", "KL Brutos", "Destare", "KilosNetos"};
    public static String columnasEntradas[] = new String[]{"Cantidad", "Descripción"};
    //public static log logs;
    public static login login;

    public verTiqueteVarios() {
        ext = new extras();
        //logs = new log();
        crearModelo();
    }

    public void crearModelo() {
        modelVerTiqVarios = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(VerTiqVarios.tblVerTiqVarios, modelVerTiqVarios, columnas.length, "SELECT idTiqueteVarios,fecha,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),vehiculo.placa,destino,observacion,kilosBrutos,destare,kilosNetos FROM tiquetevarios,personalexterno,vehiculo WHERE tiquetevarios.idConductor=personalexterno.idPersonalExterno AND tiquetevarios.idVehiculo=vehiculo.idVehiculo AND tiquetevarios.destare<>0.00 AND tiquetevarios.kilosNetos<>0.00 ");
    }

    public void tablaCamposEntrada() {

        int rec = VerTiqVarios.tblVerTiqVarios.getSelectedRow();
        idTiqueteVarios = VerTiqVarios.tblVerTiqVarios.getValueAt(rec, 0).toString();

        try {
            Con = new Conexion();
            //st = Con.conexion.createStatement();
           // rs = st.executeQuery("SELECT cantidad,descripcion FROM entradas WHERE entradas.idTiqueteVarios='" + idTiqueteVarios + "'");
                tbl = new tablas();
                tbl.llenarTabla(VerTiqVarios.tblEntradas, modelEntradas, columnasEntradas.length, "SELECT cantidad,descripcion FROM entradas WHERE entradas.idTiqueteVarios='" + idTiqueteVarios + "'");
                Con.Desconectar();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearModeloEntradas() {
        modelEntradas = new DefaultTableModel(null, columnasEntradas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        VerTiqVarios.tblEntradas.setModel(modelEntradas);
        //tbl = new tablas();
        //tbl.llenarTabla(TiqVarios.tblEntradas, modelTiqVarios, columnas.length, "SELECT idEntradas,cantidad,descripcion FROM entradas");
    }

    public void buscar() {
        //String select = "SELECT idTiquete,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo";
        String fechaI, fechaF;
        Date FechaInicial, FechaFinal;

        SimpleDateFormat formatoI = new SimpleDateFormat("yyy-MM-dd 00:00:00");
        SimpleDateFormat formatoF = new SimpleDateFormat("yyy-MM-dd 23:59:59");
        FechaInicial = VerTiqVarios.dcFechaInicial.getDate();
        FechaFinal = VerTiqVarios.dcFechaFinal.getDate();

        if (FechaInicial == null && FechaFinal == null) {
            fechaI = "";
            fechaF = "";
        } else {
            fechaI = formatoI.format(FechaInicial);
            fechaF = formatoF.format(FechaFinal);
        }

        String tiquete = VerTiqVarios.txtTiquete.getText();

        modelVerTiqVarios = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();

        if (VerTiqVarios.chTiquete.isSelected() == true && VerTiqVarios.chFecha.isSelected()) {
            if (!tiquete.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                //tbl.llenarTabla(VerTiqVarios.tblVerTiqVarios, modelVerTiqVarios, columnas.length, "SELECT idPersonalExterno,cedula,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM personalexterno,municipios WHERE personalexterno.cedula like '%" + cedula + "%' and personalexterno.apellidos like '%" + apellidos + "%' and municipios.Nombre like '%" + ciudad + "%' and personalexterno.idMunicipio=municipios.idMunicipio and personalexterno.tipo='conductor' ");
                tbl.llenarTabla(VerTiqVarios.tblVerTiqVarios, modelVerTiqVarios, columnas.length, "SELECT idTiqueteVarios,fecha,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),vehiculo.placa,destino,observacion,kilosBrutos,destare,kilosNetos FROM tiquetevarios,personalexterno,vehiculo WHERE idTiqueteVarios='" + tiquete + "' AND fecha >= '" + fechaI + "' AND fecha<='" + fechaF + "' AND tiquetevarios.idConductor=personalexterno.idPersonalExterno AND tiquetevarios.idVehiculo=vehiculo.idVehiculo AND tiquetevarios.destare<>0.00 AND tiquetevarios.kilosNetos<>0.00 ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqVarios.chTiquete.isSelected() == true) {
            if (!tiquete.equals("")) {
                tbl.llenarTabla(VerTiqVarios.tblVerTiqVarios, modelVerTiqVarios, columnas.length, "SELECT idTiqueteVarios,fecha,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),vehiculo.placa,destino,observacion,kilosBrutos,destare,kilosNetos FROM tiquetevarios,personalexterno,vehiculo WHERE idTiqueteVarios='" + tiquete + "' AND tiquetevarios.idConductor=personalexterno.idPersonalExterno AND tiquetevarios.idVehiculo=vehiculo.idVehiculo AND tiquetevarios.destare<>0.00 AND tiquetevarios.kilosNetos<>0.00 ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqVarios.chFecha.isSelected() == true) {
            if (!fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(VerTiqVarios.tblVerTiqVarios, modelVerTiqVarios, columnas.length, "SELECT idTiqueteVarios,fecha,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),vehiculo.placa,destino,observacion,kilosBrutos,destare,kilosNetos FROM tiquetevarios,personalexterno,vehiculo WHERE fecha >= '" + fechaI + "' AND fecha<='" + fechaF + "' AND tiquetevarios.idConductor=personalexterno.idPersonalExterno AND tiquetevarios.idVehiculo=vehiculo.idVehiculo AND tiquetevarios.destare<>0.00 AND tiquetevarios.kilosNetos<>0.00 ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos de busqueda esta seleccionado");
        }
        //desactivar_checkbox();*/
    }
}
