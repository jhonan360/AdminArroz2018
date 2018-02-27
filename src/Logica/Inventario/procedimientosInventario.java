/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Inventario;

import Interfaces.ProcedimientosInventario;
import Logica.Extras.currencyFormat;
import Negocio.Conexion;
import Logica.Extras.tablas;
import Logica.Extras.extras;
import Logica.Extras.login;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author Lizeth
 */
public class procedimientosInventario {

    public static ProcedimientosInventario ProcedI;
    public static extras ext;
    public static login login;
    public static ResultSet rs;
    public static Statement st;
    public static Conexion Con;
    public static tablas tbl;
    public static DefaultTableModel modelSilos, modelProcedimientos;
    public static String columSilos[] = new String[]{"Bateria", "Sección", "Silo", "Kg Actuales"};
    public static String alinearHeaderSilos[] = new String[]{"default", "default", "default", "default"};
    public static String alinearCampoSilos[] = new String[]{"center", "center", "center", "right"};
    public static String columProcedimientos[] = new String[]{"N°", "Bateria", "Sección", "Silo", "Kg Actuales", "Fecha", "Hora"};
    public static String alinearHeaderProced[] = new String[]{"30", "50", "50", "40", "80", "default", "default"};
    public static String alinearCampoPoced[] = new String[]{"center", "center", "center", "center", "right", "center", "center"};
    public static String idProcedimiento, idSilo, nSilo, kilos, fecha, hora, nomSeccion;
    public static currencyFormat cu;

    public procedimientosInventario() {
        ext = new extras();
        tbl = new tablas();
        cu = new currencyFormat();
        crearModeloSilos();
        crearModeloProcedimientos();
    }

    public void numeroProcedimiento() {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("SELECT count(*),idProcedimiento FROM procedimiento ORDER BY idProcedimiento ASC");

            while (rs.next()) {
                if (rs.getString(1).equals(0)) {
                } else {
                    String resultado = rs.getString(1);
                    int numero = Integer.parseInt(resultado);
                    int num2 = numero + 1;
                    String resul = Integer.toString(num2);
                    ProcedI.txtProcedimiento.setText(resul);
                }
            }
            Con.Desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * Silos sin procedimientos creados
     */
    public static void crearModeloSilos() {
        modelSilos = new DefaultTableModel(null, columSilos) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl.llenarTabla(ProcedI.tblSilos, modelSilos, columSilos.length, "SELECT bateria.nombre,seccion.nombre,silos.numero,silos.kilos FROM bateria,seccion,silos where silos.idSilos NOT IN(SELECT procedimiento.idSilos from silos,procedimiento WHERE procedimiento.idSilos=silos.idSilos) AND silos.kilos<>0.00 AND silos.idSeccion=seccion.idSeccion AND seccion.idBateria=bateria.idBateria");
        tbl.alinearHeaderTable(ProcedI.tblSilos, alinearHeaderSilos);
        tbl.alinearCamposTable(ProcedI.tblSilos, alinearCampoSilos);
        formatoTablaSilos();
    }
    
    public static void formatoTablaSilos() {
        int row = ProcedI.tblSilos.getRowCount();
        for (int i = 0; i < row; i++) {
            kilos = ProcedI.tblSilos.getValueAt(i, 3).toString();
            kilos = cu.thousandsFormat(Double.parseDouble(kilos));
            ProcedI.tblSilos.setValueAt(kilos, i, 3);
        }
    }

    public void completarCamposConTblSilos() {
        int rec = ProcedI.tblSilos.getSelectedRow();
        ProcedI.tblSilos.getValueAt(rec, 0).toString();
        nomSeccion = ProcedI.tblSilos.getValueAt(rec, 1).toString();
        ProcedI.txtSilo.setText(ProcedI.tblSilos.getValueAt(rec, 2).toString());
        ProcedI.txtKilos.setText(ProcedI.tblSilos.getValueAt(rec, 3).toString());
        ProcedI.txtFecha.setText(fecha());
    }

    /*
        procedimientos creados
     */
    public void crearModeloProcedimientos() {
        modelProcedimientos = new DefaultTableModel(null, columProcedimientos) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl.llenarTabla(ProcedI.tblProcedimiento, modelProcedimientos, columProcedimientos.length, "SELECT idProcedimiento,bateria.nombre,seccion.nombre,silos.numero,silos.kilos,fecha,hora FROM procedimiento,silos,bateria,seccion WHERE procedimiento.estado='proceso' AND procedimiento.idSilos=silos.idSilos AND silos.idSeccion=seccion.idSeccion AND seccion.idBateria=bateria.idBateria ORDER BY procedimiento.idProcedimiento ASC");
        tbl.alinearHeaderTable(ProcedI.tblProcedimiento, alinearHeaderProced);
        tbl.alinearCamposTable(ProcedI.tblProcedimiento, alinearCampoPoced);
        formatoTablaProced();
    }
    
    public static void formatoTablaProced() {
        int row = ProcedI.tblProcedimiento.getRowCount();
        for (int i = 0; i < row; i++) {
            kilos = ProcedI.tblProcedimiento.getValueAt(i, 4).toString();
            kilos = cu.thousandsFormat(Double.parseDouble(kilos));
            ProcedI.tblProcedimiento.setValueAt(kilos, i, 4);
        }
    }

    public void completarCamposConTblProcedimientos() {
        int rec = ProcedI.tblProcedimiento.getSelectedRow();
        ProcedI.txtProcedimiento.setText(ProcedI.tblProcedimiento.getValueAt(rec, 0).toString());
        ProcedI.tblProcedimiento.getValueAt(rec, 1).toString();
        nomSeccion = ProcedI.tblProcedimiento.getValueAt(rec, 2).toString();
        ProcedI.txtSilo.setText(ProcedI.tblProcedimiento.getValueAt(rec, 3).toString());
        ProcedI.txtKilos.setText(ProcedI.tblProcedimiento.getValueAt(rec, 4).toString());
        ProcedI.txtFecha.setText(ProcedI.tblProcedimiento.getValueAt(rec, 5).toString());
        hora = ProcedI.tblProcedimiento.getValueAt(rec, 6).toString();
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
        Date fechaDate;
        try {
            fechaDate = formato.parse(hora);
            ProcedI.jTimeHora.setTime(fechaDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void limpiarCampos() {
        ProcedI.txtSilo.setText("");
        ProcedI.txtFecha.setText("");
        ProcedI.txtKilos.setText("");
    }

    public static String fecha() {
        SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd");
        java.util.Date fecha = new Date();
        String fec = formato.format(fecha);
        return fec;
    }

    public void accionProcedimiento(String accion) {
        idProcedimiento = ProcedI.txtProcedimiento.getText();
        nSilo = ProcedI.txtSilo.getText();
        idSilo = ext.getIdSilo(nomSeccion, nSilo);
        kilos = ProcedI.txtKilos.getText();
        fecha = ProcedI.txtFecha.getText();
        hora = ProcedI.jTimeHora.getFormatedTime();

        //Valida que ningun campo este vacio
        System.out.println("proced " + idProcedimiento);
        System.out.println("nsilo " + nSilo);
        System.out.println("idSilo " + idSilo);
        System.out.println("kilos " + kilos);
        System.out.println("fecha " + fecha);
        System.out.println("hora " + hora);
        if (!idProcedimiento.equals("") && !idSilo.equals("") && !fecha.equals("") && !kilos.equals("")) {
            if (!hora.equals("00:00")) {
                switch (accion) {
                    case "crear":
                        insertarProcedimiento(idProcedimiento, idSilo, fecha, hora);//Llamado al metodo insertar
                        break;

                    case "modificar":
                        modificarProcedimiento(idProcedimiento, fecha, hora);//Llamado al metodo modificar
                        break;

                }
                crearModeloSilos();
                crearModeloProcedimientos();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(null, "Establezca una hora diferente a 00:00");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un registro para " + accion);
        }

    }

    public void insertarProcedimiento(String idProcedimiento, String idSilo, String fecha, String hora) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            st.executeUpdate("INSERT INTO procedimiento (idProcedimiento,idSilos,fecha,hora,estado) VALUES ('" + idProcedimiento + "','" + idSilo + "','" + fecha + "','" + hora + "','proceso')");
            JOptionPane.showMessageDialog(null, "Procedimiento creado");
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificarProcedimiento(String idProcedimiento, String fecha, String hora) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            st.executeUpdate("UPDATE procedimiento SET fecha='" + fecha + "',hora='" + hora + "' WHERE idProcedimiento='" + idProcedimiento + "'");
            JOptionPane.showMessageDialog(null, "Procedimiento modificado");
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
