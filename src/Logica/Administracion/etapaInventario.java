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
import Logica.Extras.extras;

/**
 *
 * @author uriel
 */
public class etapaInventario {
    public static extras ext;
    public static EtapaInventario Procedimiento;
    public static Statement st,st2;
     public static ResultSet rs,rs2;
    public static Conexion Con;
    public DefaultTableModel modeloemp,modeloemp2,modeloemp3,modeloemp5;
    public static cargarCombo cargar;
    //public String columnas[] = new String[]{"N° procedimiento", "N° silo", "Fecha", "Hora", "estado"};
    public String columnas2[] = new String[]{"N°", "Etapa", "Fecha", "Hora", "Humedad"};
    public String headerEtapasProedimiento[] = new String[]{"10", "30", "30", "30", "30"};
    public String columnas1[] = new String[]{"N°", "Bateria", "Secadora", "Silo", "Fecha", "Hora"};
    public String headerProcedimiento[] = new String[]{"6", "20", "30", "10", "40", "30"};
    public String columnas3[] = new String[]{"N°", "Bateria", "Secadora", "Silo", "Almacenamiento"};
    public String headerSilo[] = new String[]{"10", "20", "35", "10", "30"};
    public String columnas5[] = new String[]{"N°", "Etapa", "Fecha", "Hora", "H"};
    public String headerEtapaSilo[] = new String[]{"5", "30", "40", "40", "10"};
    public static tablas tbl;
    public static String idSilos,idProcedimiento,idProcedimiento2;

    
    public etapaInventario() {
        crearModeloProcedimiento();
        crearModelo2();
        crearModeloProcedimentosSecamiento();
        alignHeadersTable();
        ext = new extras();
    }
    
    public void alignHeadersTable(){
        tbl.alinearHeaderTable(Procedimiento.jtablecreadas, headerProcedimiento);
        tbl.alinearHeaderTable(Procedimiento.jtablependiente, headerEtapasProedimiento);
        tbl.alinearHeaderTable(Procedimiento.jTable1, headerSilo);
        tbl.alinearHeaderTable(Procedimiento.jTable2, headerEtapaSilo);

    }
            
    public void guardar(){
        SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd");
        Date Fechainicial = Procedimiento.jfecha.getDate();
        String fecha = formato.format(Fechainicial);
        String hora=Procedimiento.jhora.getFormatedTime();
        String humedad = Procedimiento.txtHumedad.getText();
        String estado = Procedimiento.cmbestado.getSelectedItem().toString();
        if(!humedad.equals("")){
             insertar_procedimiento(fecha, hora, humedad, estado);
        crearModeloProcedimentosSecamiento();

       limpiar_campos();
        }else{
            JOptionPane.showMessageDialog(null,"La humedad no puede estar vacia");
        }
       

         //String almacenamiento = Procedimiento.CmbAlmacenamiento.getSelectedItem().toString();
            String observacion= Procedimiento.TxtObs.getText();
        
        
    }
    public void insertar_procedimiento(String fecha,String hora,String humedad,String estado){
        try {
            Con = new Conexion();
            
            st = Con.conexion.createStatement();

            
            st.executeUpdate("Insert Into etapa (idHistorialEtapa,idProcedimiento,etapa,fecha,hora,humedad) values (0,'" + idProcedimiento + "','" + estado + "','" + fecha + "','" + hora + "','" + humedad + "')");
            ext.logs("INSERT","Insert Into etapa (idHistorialEtapa,idProcedimiento,etapa,fecha,hora,humedad) values (0,'" + idProcedimiento + "','" + estado + "','" + fecha + "','" + hora + "','" + humedad + "')");
          JOptionPane.showMessageDialog(null, "El registro ha sido agregado");
            
             st.executeUpdate("UPDATE procedimiento SET tipoAlmacenamiento='trincho' Where procedimiento.idProcedimiento='" + idProcedimiento + "'");
                ext.logs("UPDATE","UPDATE procedimiento SET tipoAlmacenamiento='trincho' Where procedimiento.idProcedimiento='" + idProcedimiento + "'");
        
           
      
            JOptionPane.showMessageDialog(null, "El registro ha sido agregado");

            refrescar();
            crearModeloProcedimiento();
            
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

      //  Procedimiento.txtFecha.setText(Procedimiento.jtablecreadas.getValueAt(rec, 2).toString());
       // Procedimiento.txtHora.setText(Procedimiento.jtablecreadas.getValueAt(rec, 3).toString());

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
            Procedimiento.TxtSilo.setText(idSilos);
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

        Procedimiento.txtHumedad.setText("");
       
        Procedimiento.txtHumedad.setText("");
       
    }
    public void limpiar(){
       
        
    }
    
    public void crearModeloProcedimiento() {
        modeloemp = new DefaultTableModel(null, columnas1) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(Procedimiento.jtablecreadas, modeloemp, columnas1.length, "SELECT procedimiento.idProcedimiento,bateria.nombre,secadora.nombre,silos.numero,procedimiento.fecha,procedimiento.hora from bateria,procedimiento,silos,secadora where procedimiento.idSilos=silos.idSilos and silos.idSecadora=secadora.idSecadora and secadora.idBateria=bateria.idBateria and procedimiento.estado='proceso' and procedimiento.observacion is null");

    }
     public void Datos_Campos_Procedimientos(){
        int rec = Procedimiento.jtablecreadas.getSelectedRow();
        idProcedimiento="";
        idProcedimiento= Procedimiento.jtablecreadas.getValueAt(rec, 0).toString();
         System.out.println("El id del procedimiento  es: "+idProcedimiento);
        Procedimiento.TxtBateria.setText(Procedimiento.jtablecreadas.getValueAt(rec, 1).toString());
        Procedimiento.TxtSecadora.setText(Procedimiento.jtablecreadas.getValueAt(rec, 2).toString());
        Procedimiento.TxtSilo.setText(Procedimiento.jtablecreadas.getValueAt(rec, 3).toString());
        crearModelo2();
       
    }
      public void Datos_Campos_Procedimientos2(){
        int rec = Procedimiento.jTable1.getSelectedRow();
        idProcedimiento2="";
        idProcedimiento2= Procedimiento.jTable1.getValueAt(rec, 0).toString();
        crearModeloEtapasSeco(idProcedimiento2);
        llenarTabla(idProcedimiento2);
        
      }
    public void crearModelo2() {
        modeloemp2 = new DefaultTableModel(null, columnas2) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(Procedimiento.jtablependiente, modeloemp2, columnas2.length, "SELECT idHistorialEtapa,etapa,fecha,hora,humedad FROM etapa WHERE etapa = 'secamiento' and idProcedimiento='"+idProcedimiento+"' ");

    }
    public void crearModeloProcedimentosSecamiento() {
        modeloemp3 = new DefaultTableModel(null, columnas3) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(Procedimiento.jTable1, modeloemp3, columnas3.length, "SELECT procedimiento.idProcedimiento,bateria.nombre,secadora.nombre,silos.numero,procedimiento.tipoAlmacenamiento from bateria,procedimiento,silos,secadora,etapa where procedimiento.idSilos=silos.idSilos and silos.idSecadora=secadora.idSecadora and secadora.idBateria=bateria.idBateria and procedimiento.idProcedimiento=etapa.idProcedimiento and procedimiento.estado='proceso' and etapa.etapa='seco' GROUP BY procedimiento.idProcedimiento ");

    }
    public void crearModeloEtapasSeco(String id) {
        modeloemp5 = new DefaultTableModel(null, columnas5) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(Procedimiento.jTable2, modeloemp5, columnas5.length, "SELECT idHistorialEtapa,etapa,fecha,hora,humedad FROM etapa WHERE etapa = 'secamiento' and etapa.idProcedimiento='" + id + "'");

    }
    public void llenarTabla(String idPro) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("select observacion from procedimiento where idProcedimiento= '" + idPro + "' ");
            //ResultSetMetaData rsc3md = rsc3.getMetaData();
            while (rs.next()) {
                
                Procedimiento.TxtObservacion.setText(rs.getString(1));
            }
           
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void limpiar_campos(){
    // Procedimiento.TxtObs.setText("");
     Procedimiento.TxtObservacion.setText("");
     Procedimiento.TxtSecadora.setText("");
     Procedimiento.TxtSilo.setText("");
     Procedimiento.txtHumedad.setText("");
     Procedimiento.TxtBateria.setText("");
     Procedimiento.TxtObs.setVisible(false);
        Procedimiento.LblObs.setVisible(false);
       
       // crearModeloEtapasSeco("0");
     
    }
    
    
    public void actualizar_procedimiento(){
        String estado= Procedimiento.cmbestado.getSelectedItem().toString();
       
            String observacion= Procedimiento.TxtObs.getText();
            System.out.println("estado"+estado);
           // System.out.println("almacenamiento"+almacenamiento);
            System.out.println("onservacionnn"+observacion);
         if (estado.equals("Seco") && !observacion.equals("")) {
             System.out.println("llegueeeee");
                 try {
            Con = new Conexion();
            
            st = Con.conexion.createStatement();
            
                st.executeUpdate("UPDATE procedimiento SET observacion='"+observacion+"' Where procedimiento.idProcedimiento='" + idProcedimiento + "'");
                ext.logs("UPDATE","UPDATE procedimiento SET observacion='"+observacion+"' Where procedimiento.idProcedimiento='" + idProcedimiento + "'");
        
            JOptionPane.showMessageDialog(null, "El registro ha sido modificado");
         
            crearModeloProcedimiento();
            crearModeloProcedimentosSecamiento();
            idProcedimiento="0";
            crearModelo2();
             Procedimiento.cmbestado.setSelectedIndex(0);
             Procedimiento.TxtObs.setText("");
            limpiar_campos();
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
            }else{
             JOptionPane.showMessageDialog(null, "El campo observacion debe contener información");
                 limpiar_campos();
            }
        
    }
    public void guardar_actualizar(){
        
            String observacion= Procedimiento.TxtObs.getText();
            if(observacion.equals("")){
                guardar();
                idProcedimiento="0";
            crearModelo2();
            }else{
                guardar();
                actualizar_procedimiento();
            }
    }
    public void trillar(){
        
        
        
        if(idProcedimiento2==""){
            JOptionPane.showMessageDialog(null,"Por favor seleccione un procedimiento a trillar");
        }else{
            int aceptar = JOptionPane.showConfirmDialog(null, "Esta seguro que desea enviar a trillar", "Confirmacion", JOptionPane.CANCEL_OPTION);
                if (aceptar == JOptionPane.YES_OPTION) {
           try {
            Con = new Conexion();
            
            st = Con.conexion.createStatement();
            
            st.executeUpdate("UPDATE procedimiento SET estado='finalizado'  Where procedimiento.idProcedimiento='" + idProcedimiento2 + "'");
            ext.logs("UPDATE","UPDATE procedimiento SET estado='finalizado'  Where procedimiento.idProcedimiento='" + idProcedimiento2 + "'");
      
            JOptionPane.showMessageDialog(null, "El registro ha sido Modificado");
         
            crearModeloProcedimiento();
            crearModeloProcedimentosSecamiento();
            
            Con.Desconectar();
            idProcedimiento="0";
               crearModeloEtapasSeco(idProcedimiento);
            crearModelo2();
            limpiar_campos();
        } catch (Exception e) {
            e.printStackTrace();
        } 
        }
                else{
                    JOptionPane.showMessageDialog(null, "Los cambios han sido descartados");
                }
        }
        
    }
    
}
