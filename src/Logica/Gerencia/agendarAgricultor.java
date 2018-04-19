/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Gerencia;

import Negocio.Conexion;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Logica.Extras.tablas;
import Interfaces.AgendarAgricultor;
import Logica.Extras.currencyFormat;
import Logica.Extras.log;
import java.util.Date;
import javax.swing.JTable;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;
import Logica.Extras.extras;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 *
 * @author jhonansmith
 */
public class agendarAgricultor {

    public static Conexion Con;
    public static ResultSet rs, rsNotify;
    public static Statement st, stNotify;
    public static AgendarAgricultor AgendarA;

    public DefaultTableModel modeloagr;
    public DefaultTableModel modeloagrAgenda;
    public String columnasAgr[] = new String[]{"N°", "Cedula", "Nombres Apellidos", "Municipio", "Teléfono 1", "Teléfono 2", "Teléfono 3"};
    public String headerColumnas[] = new String[]{"30", "90", "140", "default", "90", "90", "90"};
    public String camposColumnas[] = new String[]{"center", "left", "left", "left", "left", "left", "left"};
    public String columnasAgenda[] = new String[]{"N°", "Observación", "Fecha", "Diás Anticipo"};
    public String headerColumnasAgenda[] = new String[]{"30", "140", "80", "30"};
    public String camposColumnasAgenda[] = new String[]{"center", "center", "center", "center"};
    public tablas tbl;
    public static currencyFormat cu;
    private String ccAgricultor;
    private Date date;
    public static extras ext;

    public agendarAgricultor() {
        ext = new extras();
        cu = new currencyFormat();
        tbl = new tablas();
        date = new Date();
        AgendarA.jfecha.setDate(date);
        crearModelo();
    }

    public void crearModelo() {// crea los modelos de las tablas
        modeloagr = new DefaultTableModel(null, columnasAgr) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl.llenarTabla(AgendarA.tblAgricultor, modeloagr, columnasAgr.length, "SELECT idPersonalExterno,cedula,CONCAT(nombres,' ',apellidos),municipios.nombre,telefono,telefono2,telefono3 FROM personalexterno,municipios WHERE tipo='agricultor' AND personalexterno.idMunicipio=municipios.idMunicipio");
        tbl.alinearHeaderTable(AgendarA.tblAgricultor, headerColumnas);
        tbl.alinearCamposTable(AgendarA.tblAgricultor, camposColumnas);
        tbl.rowNumberTabel(AgendarA.tblAgricultor);
    }

    public void crearModeloAgenda() {
        modeloagrAgenda = new DefaultTableModel(null, columnasAgenda) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl.llenarTabla(AgendarA.tblAgenda, modeloagrAgenda, columnasAgenda.length, "SELECT idRecordatorio,observacion,fecha,diasAntes FROM recordatorio,personalexterno WHERE recordatorio.idPersonalExterno=personalexterno.idPersonalExterno AND personalexterno.cedula='" + ccAgricultor + "'");
        tbl.alinearHeaderTable(AgendarA.tblAgenda, headerColumnasAgenda);
        tbl.alinearCamposTable(AgendarA.tblAgenda, camposColumnasAgenda);
        tbl.rowNumberTabel(AgendarA.tblAgenda);
    }

    public void tabla_campos() { // rellenan campos cuando se selecciona una fila en la tabla 
        int rec = AgendarA.tblAgricultor.getSelectedRow();// devuelve un entero con la posicion de la seleccion en la tabla
        ccAgricultor = AgendarA.tblAgricultor.getValueAt(rec, 1).toString();
        crearModeloAgenda();
    }

    // metodo para busqueda con diferentes criterios se agrupa por ccAgricultor 
    public void buscar() { // ATENCION tengo dudas con % telefono% no sale ningun resultado. si sale con  telefono%
        String nombre = AgendarA.txtBNombre.getText();
        String apellidos = AgendarA.txtBApellidos.getText();
        String ciudad = AgendarA.txtBCiudad.getText();

        modeloagr = new DefaultTableModel(null, columnasAgr) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        if (AgendarA.chNombre.isSelected() == true && AgendarA.chApellidos.isSelected() == true && AgendarA.chCiudad.isSelected() == true) {
            if (!nombre.equals("") && !apellidos.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(AgendarA.tblAgricultor, modeloagr, columnasAgr.length, "SELECT idPersonalExterno,cedula,CONCAT(nombres,' ',apellidos),municipios.Nombre,telefono,telefono2,telefono3 FROM personalexterno,municipios WHERE personalexterno.nombres LIKE '%" + nombre + "%' AND personalexterno.apellidos LIKE '%" + apellidos + "%' AND municipios.Nombre LIKE '%" + ciudad + "%' AND personalexterno.idMunicipio=municipios.idMunicipio AND tipo='agricultor' GROUP BY nombre");
                tbl.alinearHeaderTable(AgendarA.tblAgenda, headerColumnasAgenda);
                tbl.alinearCamposTable(AgendarA.tblAgricultor, camposColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (AgendarA.chNombre.isSelected() == true && AgendarA.chApellidos.isSelected() == true) {
            if (!nombre.equals("") && !apellidos.equals("")) {
                tbl.llenarTabla(AgendarA.tblAgricultor, modeloagr, columnasAgr.length, "SELECT idPersonalExterno,cedula,CONCAT(nombres,' ',apellidos),municipios.Nombre,telefono,telefono2,telefono3 FROM personalexterno,municipios WHERE personalexterno.nombres LIKE '%" + nombre + "%' AND personalexterno.Apellidos LIKE '%" + apellidos + "%' AND personalexterno.idMunicipio=municipios.idMunicipio AND tipo='agricultor' GROUP BY cedula");
                tbl.alinearHeaderTable(AgendarA.tblAgenda, headerColumnasAgenda);
                tbl.alinearCamposTable(AgendarA.tblAgricultor, camposColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (AgendarA.chNombre.isSelected() == true && AgendarA.chCiudad.isSelected() == true) {
            if (!nombre.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(AgendarA.tblAgricultor, modeloagr, columnasAgr.length, "SELECT idPersonalExterno,nombre,CONCAT(nombres,' ',apellidos),municipios.Nombre,telefono,telefono2,telefono3 FROM personalexterno,municipios WHERE municipios.Nombre LIKE '%" + ciudad + "%' AND personalexterno.nombres LIKE '%" + nombre + "%' AND personalexterno.idMunicipio=municipios.idMunicipio AND tipo='agricultor' GROUP BY cedula");
                tbl.alinearHeaderTable(AgendarA.tblAgenda, headerColumnasAgenda);
                tbl.alinearCamposTable(AgendarA.tblAgricultor, camposColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (AgendarA.chApellidos.isSelected() == true && AgendarA.chCiudad.isSelected() == true) {
            if (!apellidos.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(AgendarA.tblAgricultor, modeloagr, columnasAgr.length, "SELECT idPersonalExterno,personalexterno.cedula,nombres,apellidos,municipios.Nombre FROM personalexterno,municipios WHERE municipios.Nombre LIKE '%" + ciudad + "%' AND personalexterno.apellidos LIKE '%" + apellidos + "%' AND personalexterno.idMunicipio=municipios.idMunicipio AND tipo='agricultor' GROUP BY cedula");
                tbl.alinearHeaderTable(AgendarA.tblAgenda, headerColumnasAgenda);
                tbl.alinearCamposTable(AgendarA.tblAgricultor, camposColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (AgendarA.chNombre.isSelected() == true) {
            if (!nombre.equals("")) {
                tbl.llenarTabla(AgendarA.tblAgricultor, modeloagr, columnasAgr.length, "SELECT idPersonalExterno,cedula,CONCAT(nombres,' ',apellidos),municipios.Nombre,telefono,telefono2,telefono3 FROM personalexterno,municipios WHERE personalexterno.nombres LIKE '%" + nombre + "%' AND personalexterno.idMunicipio=municipios.idMunicipio AND tipo='agricultor' GROUP BY cedula");
                tbl.alinearHeaderTable(AgendarA.tblAgenda, headerColumnasAgenda);
                tbl.alinearCamposTable(AgendarA.tblAgricultor, camposColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (AgendarA.chApellidos.isSelected() == true) {
            if (!apellidos.equals("")) {
                tbl.llenarTabla(AgendarA.tblAgricultor, modeloagr, columnasAgr.length, "SELECT idPersonalExterno,cedula,CONCAT(nombres,' ',apellidos),municipios.Nombre,telefono,telefono2,telefono3 FROM personalexterno,municipios WHERE personalexterno.apellidos LIKE '%" + apellidos + "%' AND personalexterno.idMunicipio=municipios.idMunicipio AND tipo='agricultor' GROUP BY cedula");
                tbl.alinearHeaderTable(AgendarA.tblAgenda, headerColumnasAgenda);
                tbl.alinearCamposTable(AgendarA.tblAgricultor, camposColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (AgendarA.chCiudad.isSelected() == true) {
            if (!ciudad.equals("")) {
                tbl.llenarTabla(AgendarA.tblAgricultor, modeloagr, columnasAgr.length, "SELECT idPersonalExterno,cedula,CONCAT(nombres,' ',apellidos),municipios.Nombre,telefono,telefono2,telefono3 FROM personalexterno,municipios WHERE municipios.Nombre LIKE '%" + ciudad + "%' AND  personalexterno.idMunicipio=municipios.idMunicipio AND tipo='agricultor' GROUP BY cedula");
                tbl.alinearHeaderTable(AgendarA.tblAgenda, headerColumnasAgenda);
                tbl.alinearCamposTable(AgendarA.tblAgricultor, camposColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos de busqueda esta seleccionado");
        }
        tbl.rowNumberTabel(AgendarA.tblAgricultor);
        //desactivar_checkbox();
    }

    public void limpiar_registros() {
        AgendarA.txtDiasAntes.setText("");
        AgendarA.txtaObservacion.setText("");
        AgendarA.jfecha.setDate(date);
    }

    public void crearAgenda() {//crear el agricultor se ejecuta cuando se da clic el boton crear
        //Asignación de variables
        int rec = AgendarA.tblAgricultor.getSelectedRow();// devuelve un entero con la posicion de la seleccion en la tabla
        ccAgricultor = AgendarA.tblAgricultor.getValueAt(rec, 1).toString();
        String observacion = AgendarA.txtaObservacion.getText();
        String diasAnticipo = AgendarA.txtDiasAntes.getText();
        Date fecha = AgendarA.jfecha.getDate();
        if (!observacion.equals("") && !diasAnticipo.equals("") && !fecha.equals("")) {
            if (!ccAgricultor.equals("")) {
                insertar(observacion, diasAnticipo, fecha);
                limpiar_registros();
                crearModelo();
                crearModeloAgenda();
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un Agricultor");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        }
    }

    public void insertar(String observacion, String dias, Date fecha) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            st.executeUpdate("INSERT INTO recordatorio(idRecordatorio, idPersonalExterno, observacion, fecha, diasAntes, fechaAntes) VALUES (0,(SELECT idPersonalExterno FROM personalexterno WHERE personalexterno.cedula='"+ccAgricultor+"'),'" + observacion + "','" + cu.dateNotTime(fecha) + "','" + dias + "','" + minusDate(fecha, Integer.parseInt(dias)) + "')");
            //ext.logs("INSERT", "INSERT INTO recordatorio(idRecordatorio, idPersonalExterno, observacion, fecha, diasAntes, fechaAntes) VALUES (0,(SELECT idPersonalExterno FROM personalexterno WHERE personalexterno.cedula='1105689625'),'" + observacion + "','" + fecha.toString() + "','" + dias + "','" + minusDate(fecha, Integer.parseInt(dias)) + "')");
            JOptionPane.showMessageDialog(null, "Recordatorio Agregado");
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String minusDate(Date Date, int minus) {
        LocalDate date = Date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        TemporalUnit unidadTemporal = ChronoUnit.DAYS;
        LocalDate dateResultado = date.minus(minus, unidadTemporal);
        return cu.dateNotTime(Date.from(dateResultado.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    
}
