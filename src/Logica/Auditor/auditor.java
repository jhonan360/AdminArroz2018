/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Auditor;

import Interfaces.Auditor;
import Logica.Extras.tablas;
import Negocio.Conexion;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import java.util.Date;

import javax.swing.table.DefaultTableModel;
/**
 *
 * @author uriel
 */
public class auditor {

    public Auditor Audi;
    public static Conexion Con;
    public static Statement st, st1;
    public static ResultSet rs, rs1;
    public tablas tbl;
    public DefaultTableModel modelo;
    public String columnas[] = new String[]{"ID", "Acción", "Fecha Hora", "Usuario", "Cedula", "Nombres", "Apellidos", "Dirección", "Ciudad"};

    //SELECT * FROM agricultorlog WHERE usuario='b' AND tipo='a' AND tipo='i' AND tipo='e' AND tipo='ie' AND fechahora LIKE '2017-05-16%'
    public auditor() {
        Con = new Conexion();
        tbl = new tablas();
        crearModelo();
    }

    public String calendario() {
        String dia = Integer.toString(Audi.calendario.getCalendar().get(Calendar.DAY_OF_MONTH));
        switch (dia) {
            case "1":
                dia = "01";
                break;
            case "2":
                dia = "02";
                break;
            case "3":
                dia = "03";
                break;
            case "4":
                dia = "04";
                break;
            case "5":
                dia = "05";
                break;
            case "6":
                dia = "06";
                break;
            case "7":
                dia = "07";
                break;
            case "8":
                dia = "08";
                break;
            case "9":
                dia = "09";
                break;
        }
        String mes = Integer.toString(Audi.calendario.getCalendar().get(Calendar.MONTH) + 1);
        switch (mes) {
            case "1":
                mes = "01";
                break;
            case "2":
                mes = "02";
                break;
            case "3":
                mes = "03";
                break;
            case "4":
                mes = "04";
                break;
            case "5":
                mes = "05";
                break;
            case "6":
                mes = "06";
                break;
            case "7":
                mes = "07";
                break;
            case "8":
                mes = "08";
                break;
            case "9":
                mes = "09";
                break;
        }
        String año = Integer.toString(Audi.calendario.getCalendar().get(Calendar.YEAR));
        String fecha = año + "-" + mes + "-" + dia;
        System.out.println(fecha);
        return fecha;
    }

    public void crearModelo() {// crea los modelos de las tablas
        modelo = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

    }

    public void buscar() {
        crearModelo();
        String fecha = calendario();
        String usuario = Audi.cmbUsuarios.getSelectedItem().toString();

        if (Audi.chUsuario.isSelected() != true) {
            System.out.println("Con usuario");
            if (Audi.chDelete.isSelected() != true && Audi.chUpdate.isSelected() != true && Audi.chInsert.isSelected() != true) {
                tbl.llenarTabla(Audi.tblAuditor, modelo, columnas.length, "SELECT * FROM agricultorlog WHERE usuario='" + usuario + "' AND CAST(fechahora AS DATE) ='" + fecha + "'");
            }
            if (Audi.chUpdate.isSelected() == true) {
                tbl.llenarTabla(Audi.tblAuditor, modelo, columnas.length, "SELECT * FROM agricultorlog WHERE usuario='" + usuario + "' AND tipo='a' AND CAST(fechahora AS DATE) ='" + fecha + "'");
            }
            if (Audi.chInsert.isSelected() == true) {
                tbl.llenarTabla(Audi.tblAuditor, modelo, columnas.length, "SELECT * FROM agricultorlog WHERE usuario='" + usuario + "' AND tipo='i' AND CAST(fechahora AS DATE) ='" + fecha + "'");
            }
            if (Audi.chDelete.isSelected() == true) {
                tbl.llenarTabla(Audi.tblAuditor, modelo, columnas.length, "SELECT * FROM agricultorlog WHERE usuario='" + usuario + "' AND tipo='e' AND tipo='ie' AND CAST(fechahora AS DATE) ='" + fecha + "'");
            }
            if (Audi.chDelete.isSelected() == true && Audi.chUpdate.isSelected() == true && Audi.chInsert.isSelected() == true) {
                tbl.llenarTabla(Audi.tblAuditor, modelo, columnas.length, "SELECT * FROM agricultorlog WHERE usuario='" + usuario + "' AND tipo='a' AND tipo='i' AND tipo='e' AND tipo='ie' AND CAST(fechahora AS DATE) ='" + fecha + "'");
            }
            if (Audi.chDelete.isSelected() == true && Audi.chUpdate.isSelected() == true) {
                tbl.llenarTabla(Audi.tblAuditor, modelo, columnas.length, "SELECT * FROM agricultorlog WHERE usuario='" + usuario + "' AND tipo='e' AND tipo='ie' AND tipo='a' AND CAST(fechahora AS DATE) ='" + fecha + "'");
            }
            if (Audi.chDelete.isSelected() == true && Audi.chInsert.isSelected() == true) {
                tbl.llenarTabla(Audi.tblAuditor, modelo, columnas.length, "SELECT * FROM agricultorlog WHERE usuario='" + usuario + "' AND tipo='e' AND tipo='ie' AND tipo='i' AND CAST(fechahora AS DATE) ='" + fecha + "'");
            }
            if (Audi.chUpdate.isSelected() == true && Audi.chInsert.isSelected() == true) {
                tbl.llenarTabla(Audi.tblAuditor, modelo, columnas.length, "SELECT * FROM agricultorlog WHERE usuario='" + usuario + "'  AND tipo='i' AND tipo='a' AND CAST(fechahora AS DATE) ='" + fecha + "'");
            }
        } else {
            System.out.println("SIN usuario");
            if (Audi.chDelete.isSelected() != true && Audi.chUpdate.isSelected() != true && Audi.chInsert.isSelected() != true) {
                tbl.llenarTabla(Audi.tblAuditor, modelo, columnas.length, "SELECT * FROM agricultorlog WHERE CAST(fechahora AS DATE) ='" + fecha + "'");
            }
            if (Audi.chUpdate.isSelected() == true) {
                tbl.llenarTabla(Audi.tblAuditor, modelo, columnas.length, "SELECT * FROM agricultorlog WHERE tipo='a' AND CAST(fechahora AS DATE) ='" + fecha + "'");
            }
            if (Audi.chInsert.isSelected() == true) {
                tbl.llenarTabla(Audi.tblAuditor, modelo, columnas.length, "SELECT * FROM agricultorlog WHERE tipo='i' AND CAST(fechahora AS DATE) ='" + fecha + "'");
            }
            if (Audi.chDelete.isSelected() == true) {
                tbl.llenarTabla(Audi.tblAuditor, modelo, columnas.length, "SELECT * FROM agricultorlog WHERE tipo='e' AND tipo='ie' AND CAST(fechahora AS DATE) ='" + fecha + "'");
            }
            if (Audi.chDelete.isSelected() == true && Audi.chUpdate.isSelected() == true && Audi.chInsert.isSelected() == true) {
                tbl.llenarTabla(Audi.tblAuditor, modelo, columnas.length, "SELECT * FROM agricultorlog WHERE  tipo='a' AND tipo='i' AND tipo='e' AND tipo='ie' AND CAST(fechahora AS DATE) ='" + fecha + "'");
            }
            if (Audi.chDelete.isSelected() == true && Audi.chUpdate.isSelected() == true) {
                tbl.llenarTabla(Audi.tblAuditor, modelo, columnas.length, "SELECT * FROM agricultorlog WHERE tipo='e' AND tipo='ie' AND tipo='a' AND CAST(fechahora AS DATE) ='" + fecha + "'");
            }
            if (Audi.chDelete.isSelected() == true && Audi.chInsert.isSelected() == true) {
                tbl.llenarTabla(Audi.tblAuditor, modelo, columnas.length, "SELECT * FROM agricultorlog WHERE tipo='e' AND tipo='ie' AND tipo='i' AND CAST(fechahora AS DATE) ='" + fecha + "'");
            }
            if (Audi.chUpdate.isSelected() == true && Audi.chInsert.isSelected() == true) {
                tbl.llenarTabla(Audi.tblAuditor, modelo, columnas.length, "SELECT * FROM agricultorlog WHERE tipo='i' AND tipo='a' AND CAST(fechahora AS DATE) ='" + fecha + "'");
            }
        }
    }

    /*public void tablaUsuario(String usuario){
        //SELECT usuario FROM agricultorlog WHERE usuario='b' GROUP BY usuario
         try {
            Con.Conectar();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("SELECT idMunicipio FROM municipios WHERE nombre='" + nombre + "'and idDepartamento='" + idDeparta + "'");
            while (rs.next()) {
                System.out.println("d");
                return rs.getString(1);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }*/
}
