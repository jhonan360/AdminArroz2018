/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Bascula;

import Interfaces.BusquedasTiquete;
import Negocio.Conexion;
import Interfaces.TiqueteVarios;
import Interfaces.VerTiquetePrincipal;
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
public class verTiquetePrincipal {

    public static Conexion Con;
    public static ResultSet rs;
    public static Statement st;
    public static VerTiquetePrincipal VerTiqPrincipal;
    public static extras ext;
    public tablas tbl;
    public static String placa, color, modelo, marca, idVehiculo;
    public static DefaultTableModel modelVerTiqPrincipal;
    public static String columnas[] = new String[]{"N", "Fecha", "Agricultor", "Tipo Arroz", "Lote", "Conductor", "Vehiculo", "KL Brutos", "Destare", "KilosNetos", "Humedad", "Impureza", "Observación"};
    //public static log logs;
    public static login login;

    public verTiquetePrincipal() {
        ext = new extras();
        //logs = new log();
        crearModelo();
    }

    public void crearModelo() {
        modelVerTiqPrincipal = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo WHERE tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo");
    }

    public void buscar() {
        String select = "SELECT idTiquete,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo";
        String tiquete = VerTiqPrincipal.txtTiquete.getText();
        String agricultor = VerTiqPrincipal.txtAgricultor.getText();
        
        SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd");
        Date FechaInicial = VerTiqPrincipal.dcFechaInicial.getDate();
        String fechaI=formato.format(FechaInicial);
        
        Date FechaFinal = VerTiqPrincipal.dcFechaFinal.getDate();
        String fechaF=formato.format(FechaFinal);
        String idAgricultor = ext.getIdPersonalExterno(agricultor,"agricultor");

         System.out.println(fechaI);
         System.out.println(fechaF);
        
        modelVerTiqPrincipal = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();

       if (VerTiqPrincipal.chTiquete.isSelected() == true && VerTiqPrincipal.chAgricultor.isSelected() == true && VerTiqPrincipal.chFechaInicial.isSelected() == true && VerTiqPrincipal.chFechaFinal.isSelected() == true) {
            if (!tiquete.equals("") && !agricultor.equals("") && !fechaI.equals("")&& !fechaF.equals("")) {
                //tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idPersonalExterno,cedula,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM personalexterno,municipios WHERE personalexterno.cedula like '%" + cedula + "%' and personalexterno.apellidos like '%" + apellidos + "%' and municipios.Nombre like '%" + ciudad + "%' and personalexterno.idMunicipio=municipios.idMunicipio and personalexterno.tipo='conductor' ");
                tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo WHERE idtiquete='"+tiquete+"' AND tiquete.idAgricultor='"+idAgricultor+"' AND fecha >= '"+fechaI+"' AND fecha<='"+fechaF+"' AND tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
       /** } else if (VerTiqPrincipal.chCedula.isSelected() == true && VerTiqPrincipal.chApellidos.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.jTable1, modelVerTiqPrincipal, columnas.length, "SELECT idPersonalExterno,cedula,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM personalexterno,municipios WHERE personalexterno.apellidos like '%" + apellidos + "%' and personalexterno.cedula like '%" + cedula + "%' and personalexterno.idMunicipio=municipios.idMunicipio and personalexterno.tipo='conductor' ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chCedula.isSelected() == true && VerTiqPrincipal.chCiudad.isSelected() == true) {
            if (!cedula.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.jTable1, modelVerTiqPrincipal, columnas.length, "SELECT idPersonalExterno,cedula,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM personalexterno,municipios WHERE personalexterno.cedula like '%" + cedula + "%' and municipios.Nombre like '%" + ciudad + "%' and  and personalexterno.idMunicipio=municipios.idMunicipio and personalexterno.tipo='conductor' ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chApellidos.isSelected() == true && VerTiqPrincipal.chCiudad.isSelected() == true) {
            if (!apellidos.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.jTable1, modelVerTiqPrincipal, columnas.length, "SELECT idPersonalExterno,cedula,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM personalexterno,municipios WHERE personalexterno.apellidos like '%" + apellidos + "%' and municipios.Nombre like '%" + ciudad + "%' and personalexterno.idMunicipio=municipios.idMunicipio and personalexterno.tipo='conductor' ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chCedula.isSelected() == true) {
            if (!cedula.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.jTable1, modelVerTiqPrincipal, columnas.length, "SELECT idPersonalExterno,cedula,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM personalexterno,municipios WHERE personalexterno.cedula like '%" + cedula + "%' and personalexterno.idMunicipio=municipios.idMunicipio and personalexterno.tipo='conductor' ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chApellidos.isSelected() == true) {
            if (!apellidos.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.jTable1, modelVerTiqPrincipal, columnas.length, "SELECT idPersonalExterno,cedula,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM personalexterno,municipios WHERE personalexterno.apellidos like '%" + apellidos + "%' and personalexterno.idMunicipio=municipios.idMunicipio and personalexterno.tipo='conductor' ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chCiudad.isSelected() == true) {
            if (!ciudad.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.jTable1, modelVerTiqPrincipal, columnas.length, "SELECT idPersonalExterno,cedula,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM personalexterno,municipios WHERE municipios.Nombre like '%" + ciudad + "%' and personalexterno.idMunicipio=municipios.idMunicipio and personalexterno.tipo='conductor' ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos de busqueda esta seleccionado");
        }
        //desactivar_checkbox();*/
       }
        
    }
    

}
