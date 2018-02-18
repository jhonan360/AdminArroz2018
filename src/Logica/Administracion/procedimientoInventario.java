/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Administracion;

import java.text.SimpleDateFormat;
import Interfaces.ProcedimientoInventario;
import static Interfaces.ProcedimientoInventario.jfecha;
import java.sql.ResultSet;
import java.sql.Statement;
import Negocio.Conexion;
import java.util.Date;
import javax.swing.JOptionPane;
import Logica.Extras.cargarCombo;
import Logica.Extras.tablas;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author uriel
 */
public class procedimientoInventario {
    
    public static ProcedimientoInventario Procedimiento;
    public static Statement st,st2;
     public static ResultSet rs,rs2;
    public static Conexion Con;
    public DefaultTableModel modeloemp,modeloemp2;
    public static cargarCombo cargar;
    public String columnas[] = new String[]{"N° procedimiento","N° silo","Fecha,hora,estado"};
    public String columnas2[] = new String[]{"N°","Seccion","Bateria,hora,Kilos"};
    public static tablas tbl;
    public static String idSilos;
    public procedimientoInventario(){
               crearModelo();
        
    }
            
    public void guardar(){
        SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd");
        Date Fechainicial = Procedimiento.jfecha.getDate();
        String fecha = formato.format(Fechainicial);
        String hora=Procedimiento.jhora.getFormatedTime();
        String silo = Procedimiento.cmbsilos.getSelectedItem().toString();
        String estado = Procedimiento.cmbestado.getSelectedItem().toString();
        insertar_procedimiento(fecha, hora, silo, estado);
        
        
    }
    public void insertar_procedimiento(String fecha,String hora,String silo,String estado){
        try {
            Con = new Conexion();
            st2 = Con.conexion.createStatement();
            rs2 = st2.executeQuery("SELECT silos.idSilos FROM silos WHERE silos.numero = '" + silo + "'");
            while (rs2.next()) {
            idSilos = rs2.getObject(1) + "";
            }
            st = Con.conexion.createStatement();
            
            st.executeUpdate("Insert Into procedimiento (idProcedimiento,idSilos,fecha,hora,estado) values (0,'" + idSilos + "','" + fecha + "','" + hora + "','" + estado + "')");
            JOptionPane.showMessageDialog(null, "El registro ha sido agregado");
            refrescar();
            crearModelo();
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void refrescar(){
        Date datep = new   Date();
        Procedimiento.jfecha.setDate(datep);
        Procedimiento.jhora.setTime(datep);
        cargar.silos(Procedimiento.cmbsilos);
    }
    
    public void crearModelo() {
        modeloemp = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(Procedimiento.jtablecreadas, modeloemp, columnas.length, "SELECT  procedimiento.idProcedimiento,silos.numero,procedimiento.fecha,procedimiento.hora,procedimiento.estado FROM procedimiento, silos WHERE procedimiento.idSilos=silos.IdSilos");

    }
    public void crearModelo2() {
        modeloemp2 = new DefaultTableModel(null, columnas2) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(Procedimiento.jtablependiente, modeloemp2, columnas2.length, "select numero from silos as si,seccion as se,bateria as ba where si.idSeccion = se.idSeccion and se.idBateria = ba.idBateria  and si.idSilos not in (select idSilos from procedimiento group by idSilos )");

    }
    
}
