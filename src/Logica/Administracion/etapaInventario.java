/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Administracion;

import java.text.SimpleDateFormat;
import Interfaces.EtapaInventario;
import static Interfaces.EtapaInventario.jfecha;
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
public class etapaInventario {
    
    public static EtapaInventario Procedimiento;
    public static Statement st,st2;
     public static ResultSet rs,rs2;
    public static Conexion Con;
    public DefaultTableModel modeloemp,modeloemp2;
    public static cargarCombo cargar;
    public String columnas[] = new String[]{"N° procedimiento","N° silo","Fecha","hora","estado"};
    public String columnas2[] = new String[]{"N°","Etapa","Fecha","hora","Humedad"};
    public static tablas tbl;
    public static String idSilos,idProcedimiento;
    public etapaInventario(){
               crearModelo();
               crearModelo2();
        
    }
            
    public void guardar(){
        SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd");
        Date Fechainicial = Procedimiento.jfecha.getDate();
        String fecha = formato.format(Fechainicial);
        String hora=Procedimiento.jhora.getFormatedTime();
        String humedad = Procedimiento.txtHumedad.getText();
                String estado = Procedimiento.cmbestado.getSelectedItem().toString();
        insertar_procedimiento(fecha, hora, humedad, estado);
        
        
    }
    public void insertar_procedimiento(String fecha,String hora,String humedad,String estado){
        try {
            Con = new Conexion();
            
            st = Con.conexion.createStatement();
            
            st.executeUpdate("Insert Into etapa (idHistorialEtapa,idProcedimiento,etapa,fecha,hora,humedad) values (0,'" + idProcedimiento + "','" + estado + "','" + fecha + "','" + hora + "','" + humedad + "')");
            JOptionPane.showMessageDialog(null, "El registro ha sido agregado");
            refrescar();
            crearModelo();
            crearModelo2();
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    public void tabla_campos(){
        int rec = Procedimiento.jtablecreadas.getSelectedRow();
             idProcedimiento="";
        idProcedimiento= Procedimiento.jtablecreadas.getValueAt(rec, 0).toString();
//        Procedimiento.txtFecha.setText(Procedimiento.jtablecreadas.getValueAt(rec, 2).toString());
//        Procedimiento.txtHora.setText(Procedimiento.jtablecreadas.getValueAt(rec, 3).toString());
        String silo=Procedimiento.jtablecreadas.getValueAt(rec, 1).toString();
        numsilo(silo);
    }
    public void numsilo(String idsilo){
            try {
                
            Con = new Conexion();
            st2 = Con.conexion.createStatement();
            rs2 = st2.executeQuery("SELECT silos.numero FROM silos WHERE silos.idSilos = '" + idsilo + "'");
            while (rs2.next()) {
            idSilos = rs2.getObject(1) + "";
        //    Procedimiento.txtsilo.setText(idSilos);
            }
           
           
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        } 
        }
    
    public void refrescar(){
        Date datep = new   Date();
        Procedimiento.jfecha.setDate(datep);
        Procedimiento.jhora.setTime(datep);
//         Procedimiento.txtFecha.setText("");
//        Procedimiento.txtHora.setText("");
        Procedimiento.txtHumedad.setText("");
//        Procedimiento.txtsilo.setText("");
       
    }
    public void limpiar(){
       
        
    }
    
    public void crearModelo() {
        modeloemp = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(Procedimiento.jtablecreadas, modeloemp, columnas.length, "SELECT idProcedimiento, idSilos, fecha,hora,estado FROM procedimiento WHERE estado = 'proceso'");

    }
    public void crearModelo2() {
        modeloemp2 = new DefaultTableModel(null, columnas2) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(Procedimiento.jtablependiente, modeloemp2, columnas2.length, "SELECT idHistorialEtapa,etapa,fecha,hora,humedad FROM etapa WHERE etapa = 'secamiento' ");

    }
    
}
