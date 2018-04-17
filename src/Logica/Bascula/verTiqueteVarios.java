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
import Logica.Extras.currencyFormat;
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
    public static String columnas[] = new String[]{"N°", "Fecha", "Conductor", "Vehículo", "Destino", "Observación", "Kl Brutos", "Destare", "Kl Netos"};
    public static String headerColumnas[] = new String[]{"20", "45", "180", "55", "default", "default", "55", "55", "55"};
    public static String camposColumnas[] = new String[]{"center", "center", "left", "left", "left", "left", "right", "right", "right"};
    public static String columnasEntradas[] = new String[]{"Cantidad", "Descripción"};
    public static String headerColumnasEntradas[] = new String[]{"20", "160"};
    public static String camposColumnasEntradas[] = new String[]{"right", "left"};
    //public static log logs;
    public static login login;
    public static currencyFormat cu;

    public verTiqueteVarios() {
        ext = new extras();
        tbl = new tablas();
        cu = new currencyFormat();
        //logs = new log();
        crearModelo();
    }

    public void crearModelo() {
        modelVerTiqVarios = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl.llenarTabla(VerTiqVarios.tblVerTiqVarios, modelVerTiqVarios, columnas.length, "SELECT idTiqueteVarios,fecha,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),vehiculo.placa,destino,observacion,kilosBrutos,destare,kilosNetos FROM tiquetevarios,personalexterno,vehiculo WHERE tiquetevarios.idConductor=personalexterno.idPersonalExterno AND tiquetevarios.idVehiculo=vehiculo.idVehiculo AND tiquetevarios.destare<>0.00 AND tiquetevarios.kilosNetos<>0.00 GROUP BY fecha DESC");
        tbl.alinearHeaderTable(VerTiqVarios.tblVerTiqVarios, headerColumnas);
        tbl.alinearCamposTable(VerTiqVarios.tblVerTiqVarios, camposColumnas);
        tbl.alinearHeaderTable(VerTiqVarios.tblEntradas, headerColumnasEntradas);
        formatoTabla();
    }

    public void formatoTabla() {
        int row = VerTiqVarios.tblVerTiqVarios.getRowCount();
        for (int i = 0; i < row; i++) {
            VerTiqVarios.tblVerTiqVarios.setValueAt((cu.dateNotTime(VerTiqVarios.tblVerTiqVarios.getValueAt(i, 1).toString())), i, 1);
            VerTiqVarios.tblVerTiqVarios.setValueAt(cu.thousandsFormat(Double.parseDouble(VerTiqVarios.tblVerTiqVarios.getValueAt(i, 6).toString())), i, 6);
            VerTiqVarios.tblVerTiqVarios.setValueAt(cu.thousandsFormat(Double.parseDouble(VerTiqVarios.tblVerTiqVarios.getValueAt(i, 7).toString())), i, 7);
            VerTiqVarios.tblVerTiqVarios.setValueAt(cu.thousandsFormat(Double.parseDouble(VerTiqVarios.tblVerTiqVarios.getValueAt(i, 8).toString())), i, 8);
        }
    }

    public void tablaCamposEntrada() {

        int rec = VerTiqVarios.tblVerTiqVarios.getSelectedRow();
        idTiqueteVarios = VerTiqVarios.tblVerTiqVarios.getValueAt(rec, 0).toString();

        try {
            Con = new Conexion();
            //st = Con.conexion.createStatement();
            // rs = st.executeQuery("SELECT cantidad,descripcion FROM entradas WHERE entradas.idTiqueteVarios='" + idTiqueteVarios + "'");
            tbl.llenarTabla(VerTiqVarios.tblEntradas, modelEntradas, columnasEntradas.length, "SELECT cantidad,descripcion FROM entradas WHERE entradas.idTiqueteVarios='" + idTiqueteVarios + "'");
            tbl.alinearCamposTable(VerTiqVarios.tblEntradas, camposColumnasEntradas);
            formatoTablaEntradas();
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
        tbl.alinearHeaderTable(VerTiqVarios.tblEntradas, headerColumnasEntradas);
    }

    public void formatoTablaEntradas() {
        int row = VerTiqVarios.tblEntradas.getRowCount();
        for (int i = 0; i < row; i++) {
            VerTiqVarios.tblEntradas.setValueAt(cu.thousandsFormat(Double.parseDouble(VerTiqVarios.tblEntradas.getValueAt(i, 0).toString())), i, 0);
        }
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
        String descripcion = VerTiqVarios.txtDescripcion.getText();

        modelVerTiqVarios = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        if (VerTiqVarios.chTiquete.isSelected() == true && VerTiqVarios.chDescripcion.isSelected() == true && VerTiqVarios.chFecha.isSelected()) {
            if (!tiquete.equals("") && !descripcion.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(VerTiqVarios.tblVerTiqVarios, modelVerTiqVarios, columnas.length, "SELECT tiqueteVarios.idTiqueteVarios,fecha,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),vehiculo.placa,destino,observacion,kilosBrutos,destare,kilosNetos FROM tiquetevarios,personalexterno,vehiculo,entradas WHERE tiqueteVarios.idTiqueteVarios='" + tiquete + "' AND entradas.descripcion LIKE '%" + descripcion + "%' AND fecha >= '" + fechaI + "' AND fecha<='" + fechaF + "' AND tiquetevarios.idConductor=personalexterno.idPersonalExterno AND tiquetevarios.idVehiculo=vehiculo.idVehiculo AND tiquetevarios.destare<>0.00 AND tiquetevarios.kilosNetos<>0.00 AND entradas.idTiqueteVarios=tiquetevarios.idTiqueteVarios GROUP BY fecha DESC ");
                tbl.alinearHeaderTable(VerTiqVarios.tblVerTiqVarios, headerColumnas);
                tbl.alinearCamposTable(VerTiqVarios.tblVerTiqVarios, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqVarios.chTiquete.isSelected() == true && VerTiqVarios.chDescripcion.isSelected() == true) {
            if (!tiquete.equals("") && !descripcion.equals("")) {
                tbl.llenarTabla(VerTiqVarios.tblVerTiqVarios, modelVerTiqVarios, columnas.length, "SELECT tiqueteVarios.idTiqueteVarios,fecha,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),vehiculo.placa,destino,observacion,kilosBrutos,destare,kilosNetos FROM tiquetevarios,personalexterno,vehiculo,entradas WHERE tiqueteVarios.idTiqueteVarios='" + tiquete + "' AND entradas.descripcion LIKE '%" + descripcion + "%' AND tiquetevarios.idConductor=personalexterno.idPersonalExterno AND tiquetevarios.idVehiculo=vehiculo.idVehiculo AND tiquetevarios.destare<>0.00 AND tiquetevarios.kilosNetos<>0.00 AND entradas.idTiqueteVarios=tiquetevarios.idTiqueteVarios GROUP BY fecha DESC ");
                tbl.alinearHeaderTable(VerTiqVarios.tblVerTiqVarios, headerColumnas);
                tbl.alinearCamposTable(VerTiqVarios.tblVerTiqVarios, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqVarios.chTiquete.isSelected() == true && VerTiqVarios.chFecha.isSelected()) {
            if (!tiquete.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(VerTiqVarios.tblVerTiqVarios, modelVerTiqVarios, columnas.length, "SELECT tiqueteVarios.idTiqueteVarios,fecha,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),vehiculo.placa,destino,observacion,kilosBrutos,destare,kilosNetos FROM tiquetevarios,personalexterno,vehiculo,entradas WHERE tiqueteVarios.idTiqueteVarios='" + tiquete + "' AND fecha >= '" + fechaI + "' AND fecha<='" + fechaF + "' AND tiquetevarios.idConductor=personalexterno.idPersonalExterno AND tiquetevarios.idVehiculo=vehiculo.idVehiculo AND tiquetevarios.destare<>0.00 AND tiquetevarios.kilosNetos<>0.00 AND entradas.idTiqueteVarios=tiquetevarios.idTiqueteVarios GROUP BY fecha DESC ");
                tbl.alinearHeaderTable(VerTiqVarios.tblVerTiqVarios, headerColumnas);
                tbl.alinearCamposTable(VerTiqVarios.tblVerTiqVarios, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqVarios.chDescripcion.isSelected() == true && VerTiqVarios.chFecha.isSelected() == true) {
            if (!descripcion.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(VerTiqVarios.tblVerTiqVarios, modelVerTiqVarios, columnas.length, "SELECT tiqueteVarios.idTiqueteVarios,fecha,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),vehiculo.placa,destino,observacion,kilosBrutos,destare,kilosNetos FROM tiquetevarios,personalexterno,vehiculo,entradas WHERE entradas.descripcion LIKE '%" + descripcion + "%' AND fecha >= '" + fechaI + "' AND fecha<='" + fechaF + "' AND tiquetevarios.idConductor=personalexterno.idPersonalExterno AND tiquetevarios.idVehiculo=vehiculo.idVehiculo AND tiquetevarios.destare<>0.00 AND tiquetevarios.kilosNetos<>0.00 AND entradas.idTiqueteVarios=tiquetevarios.idTiqueteVarios GROUP BY fecha DESC ");
                tbl.alinearHeaderTable(VerTiqVarios.tblVerTiqVarios, headerColumnas);
                tbl.alinearCamposTable(VerTiqVarios.tblVerTiqVarios, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqVarios.chTiquete.isSelected() == true) {
            if (!tiquete.equals("")) {
                tbl.llenarTabla(VerTiqVarios.tblVerTiqVarios, modelVerTiqVarios, columnas.length, "SELECT tiqueteVarios.idTiqueteVarios,fecha,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),vehiculo.placa,destino,observacion,kilosBrutos,destare,kilosNetos FROM tiquetevarios,personalexterno,vehiculo,entradas WHERE tiqueteVarios.idTiqueteVarios='" + tiquete + "' AND tiquetevarios.idConductor=personalexterno.idPersonalExterno AND tiquetevarios.idVehiculo=vehiculo.idVehiculo AND tiquetevarios.destare<>0.00 AND tiquetevarios.kilosNetos<>0.00 AND entradas.idTiqueteVarios=tiquetevarios.idTiqueteVarios GROUP BY fecha DESC ");
                tbl.alinearHeaderTable(VerTiqVarios.tblVerTiqVarios, headerColumnas);
                tbl.alinearCamposTable(VerTiqVarios.tblVerTiqVarios, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqVarios.chDescripcion.isSelected() == true) {
            if (!descripcion.equals("")) {
                tbl.llenarTabla(VerTiqVarios.tblVerTiqVarios, modelVerTiqVarios, columnas.length, "SELECT tiqueteVarios.idTiqueteVarios,fecha,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),vehiculo.placa,destino,observacion,kilosBrutos,destare,kilosNetos FROM tiquetevarios,personalexterno,vehiculo,entradas WHERE entradas.descripcion LIKE '%" + descripcion + "%' AND tiquetevarios.idConductor=personalexterno.idPersonalExterno AND tiquetevarios.idVehiculo=vehiculo.idVehiculo AND tiquetevarios.destare<>0.00 AND tiquetevarios.kilosNetos<>0.00 AND entradas.idTiqueteVarios=tiquetevarios.idTiqueteVarios GROUP BY fecha DESC ");
                tbl.alinearHeaderTable(VerTiqVarios.tblVerTiqVarios, headerColumnas);
                tbl.alinearCamposTable(VerTiqVarios.tblVerTiqVarios, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqVarios.chFecha.isSelected() == true) {
            if (!fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(VerTiqVarios.tblVerTiqVarios, modelVerTiqVarios, columnas.length, "SELECT tiqueteVarios.idTiqueteVarios,fecha,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),vehiculo.placa,destino,observacion,kilosBrutos,destare,kilosNetos FROM tiquetevarios,personalexterno,vehiculo,entradas WHERE fecha >= '" + fechaI + "' AND fecha<='" + fechaF + "' AND tiquetevarios.idConductor=personalexterno.idPersonalExterno AND tiquetevarios.idVehiculo=vehiculo.idVehiculo AND tiquetevarios.destare<>0.00 AND tiquetevarios.kilosNetos<>0.00 AND entradas.idTiqueteVarios=tiquetevarios.idTiqueteVarios GROUP BY fecha DESC ");
                tbl.alinearHeaderTable(VerTiqVarios.tblVerTiqVarios, headerColumnas);
                tbl.alinearCamposTable(VerTiqVarios.tblVerTiqVarios, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos de busqueda esta seleccionado");
        }
        //desactivar_checkbox();*/
    }

    
}
