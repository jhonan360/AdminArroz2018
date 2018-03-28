/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Laboratorio;
import Logica.Extras.tablas;
import Interfaces.BusquedasTiquete;
import Interfaces.BusquedasTiqueteInicial;
import Interfaces.Bascula;
import Interfaces.LaboratorioTiqueteInicial;
import Logica.Laboratorio.laboratorioTiqueteInicial;
import Interfaces.Vehiculo;
import Negocio.Conexion;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author uriel
 */
public class busquedaLaboratorio {
    public static Statement st4;
    
    public static Conexion Con;
    public static BusquedasTiquete BusTiquete;
    public static BusquedasTiqueteInicial BusT;
    public static ResultSet rs;
     public String columnas2[] = new String[]{"N° Laboratorio","N° Tiquete","Muestreo","Hora","Humedad"};
    public static LaboratorioTiqueteInicial LabT;
   // public static bascula bas;
    public static laboratorioTiqueteInicial labt;
    
  
    public static String cedula,id;
    public static tablas tbl;
    public static DefaultTableModel mdelolab,modeloestufa;
    public static String columnasAgr[] = new String[]{"id","Cedula", "Nombres", "Apellidos", "Direccion", "Municipio"};
    public static String columnasAgri[] = new String[]{"N° Tiq Lab","N° Tiq Bas","Fecha","Humedad", "Impureza","Integral", "Cascarilla", "Blanco","Partido","Entero","Yeso","Dañado","IP"};
    
    

    public busquedaLaboratorio() {
        tbl = new tablas();
       crearModeloAgricultor();
        
        
        //tabla_camposAgricultor();
    }

    /**
     * Busqueda Agricultor
     */
    public void cerrar(){
        
        //LabT.btnBuscarAgricultor.setEnabled(true);
      
    }
    public void cedula(){
        try {
            cedula=BusT.txtcedula.getText();
            Con = new Conexion();

            st4 = Con.conexion.createStatement();
            rs = st4.executeQuery("SELECT idPersonalExterno FROM personalexterno WHERE cedula = '" + cedula + "'");
            while (rs.next()) {
                id = rs.getString(1);
                
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
   
    public void crearModeloAgricultor() {
        //System.out.println("2");
        mdelolab = new DefaultTableModel(null, columnasAgri) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas(); 
        tbl.llenarTabla(BusT.jTable2, mdelolab, columnasAgri.length, "SELECT idLaboratorio,idTiquete,fecha,humedad,impureza,integralRes,cascarillaRes,blancoRes,partidoRes,enteroRes,yeso,danado,ip FROM laboratorio WHERE laboratorio.estado='cerrado'");
}
    public void crearModelo2(String id) {
        modeloestufa = new DefaultTableModel(null, columnas2) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(BusT.jTable3, modeloestufa, columnas2.length, "SELECT idmuetraestufa,idLaboratorio,muestreo,hora,humedad FROM muetraestufa where idLaboratorio ='" + id + "' ");

    }
     public void tabla_consecutivo_campo(){
        int rec = BusT.jTable2.getSelectedRow();
        
        String id=BusT.jTable2.getValueAt(rec, 0).toString();
        crearModelo2(id);
        
    }
       
    public static void desactivar_checkboxAgricultor() { // desactiva las checkbox
        BusT.chfecha.setSelected(false);
        BusT.chtiquete.setSelected(false);
        BusT.chcedula.setSelected(false);
        
    }
    

    public void buscarAgricultor() {
        SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
        Date Fechainicial = BusT.jDateinicial.getDate();
        String FechaIni = formato.format(Fechainicial);
        
        Date FechaFinal = BusT.jDatefinal.getDate();
        String FechaFin = formato.format(FechaFinal);
        //Date date = BusT.jDatefinal.getDate();
       // String fecha_final = "2018/01/02";
       // String fecha_final = BusT.jDatefinal.toString();
        //String fecha_inicial = BusT.jDateinicial.getDateFormatString();
        //String fecha_inicial = "2018/01/02";
        String cedula = BusT.txtcedula.getText();
        String numtiquete = BusT.txttiquete.getText();
        System.out.println("num"+numtiquete);
        System.out.println("id"+id);
        System.out.print("FechaIni "+FechaIni);

        mdelolab = new DefaultTableModel(null, columnasAgri) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        if (BusT.chfecha.isSelected() == true && BusT.chtiquete.isSelected() == true && BusT.chcedula.isSelected() == true) {
            if (!FechaIni.equals("") &&!FechaFin.equals("") && !cedula.equals("") && !numtiquete.equals("")) {
                tbl.llenarTabla(BusT.jTable2, mdelolab, columnasAgri.length, "SELECT laboratorio.idLaboratorio,laboratorio.idTiquete,laboratorio.fecha,laboratorio.humedad,laboratorio.impureza,laboratorio.integralRes,laboratorio.cascarillaRes,laboratorio.blancoRes,laboratorio.partidoRes,laboratorio.enteroRes,laboratorio.yeso,laboratorio.danado,laboratorio.ip FROM laboratorio,tiquete WHERE laboratorio.idTiquete= (SELECT idTiquete FROM tiquete WHERE idAgricultor='"+id+"')AND laboratorio.idTiquete='"+numtiquete+"' AND laboratorio.fecha > '"+FechaIni+"' AND laboratorio.fecha <'"+FechaFin+"' and tiquete.idTiquete=laboratorio.idTiquete");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusT.chfecha.isSelected() == true && BusT.chtiquete.isSelected() == true) {
            if (!FechaIni.equals("") &&!FechaFin.equals("") && !numtiquete.equals("")) {
                tbl.llenarTabla(BusT.jTable2, mdelolab, columnasAgri.length, "SELECT laboratorio.idLaboratorio,laboratorio.idTiquete,laboratorio.fecha,laboratorio.humedad,laboratorio.impureza,laboratorio.integralRes,laboratorio.cascarillaRes,laboratorio.blancoRes,laboratorio.partidoRes,laboratorio.enteroRes,laboratorio.yeso,laboratorio.danado,laboratorio.ip FROM laboratorio,tiquete WHERE laboratorio.idTiquete='"+numtiquete+"' AND laboratorio.fecha > '"+FechaIni+"' AND laboratorio.fecha <'"+FechaFin+"' and tiquete.idTiquete=laboratorio.idTiquete");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusT.chfecha.isSelected() == true && BusT.chcedula.isSelected() == true) {
            if (!FechaIni.equals("") &&!FechaFin.equals("") && !cedula.equals("")) {
                tbl.llenarTabla(BusT.jTable2, mdelolab, columnasAgri.length, "SELECT laboratorio.idLaboratorio,laboratorio.idTiquete,laboratorio.fecha,laboratorio.humedad,laboratorio.impureza,laboratorio.integralRes,laboratorio.cascarillaRes,laboratorio.blancoRes,laboratorio.partidoRes,laboratorio.enteroRes,laboratorio.yeso,laboratorio.danado,laboratorio.ip FROM laboratorio,tiquete WHERE tiquete.idAgricultor='"+id+"'AND  laboratorio.fecha > '"+FechaIni+"' AND laboratorio.fecha <'"+FechaFin+"'and tiquete.idTiquete=laboratorio.idTiquete");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusT.chtiquete.isSelected() == true && BusT.chcedula.isSelected() == true) {
            if (!numtiquete.equals("") && !cedula.equals("")) {
                tbl.llenarTabla(BusT.jTable2, mdelolab, columnasAgri.length, "SELECT laboratorio.idLaboratorio,laboratorio.idTiquete,laboratorio.fecha,laboratorio.humedad,laboratorio.impureza,laboratorio.integralRes,laboratorio.cascarillaRes,laboratorio.blancoRes,laboratorio.partidoRes,laboratorio.enteroRes,laboratorio.yeso,laboratorio.danado,laboratorio.ip FROM laboratorio,tiquete WHERE laboratorio.idTiquete= (SELECT idTiquete FROM tiquete WHERE tiquete.idAgricultor='"+id+"' AND laboratorio.idTiquete='"+numtiquete+"'and tiquete.idTiquete=laboratorio.idTiquete");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusT.chfecha.isSelected() == true) {
            if (!FechaIni.equals("") &&!FechaFin.equals("")) {
                tbl.llenarTabla(BusT.jTable2, mdelolab, columnasAgri.length, "SELECT laboratorio.idLaboratorio,laboratorio.idTiquete,laboratorio.fecha,laboratorio.humedad,laboratorio.impureza,laboratorio.integralRes,laboratorio.cascarillaRes,laboratorio.blancoRes,laboratorio.partidoRes,laboratorio.enteroRes,laboratorio.yeso,laboratorio.danado,laboratorio.ip FROM laboratorio,tiquete WHERE laboratorio.fecha > '"+FechaIni+"' AND laboratorio.fecha <'"+FechaFin+"'and tiquete.idTiquete=laboratorio.idTiquete");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusT.chtiquete.isSelected() == true) {
            if (!numtiquete.equals("")) {
                tbl.llenarTabla(BusT.jTable2, mdelolab, columnasAgri.length, "SELECT laboratorio.idLaboratorio,laboratorio.idTiquete,laboratorio.fecha,laboratorio.humedad,laboratorio.impureza,laboratorio.integralRes,laboratorio.cascarillaRes,laboratorio.blancoRes,laboratorio.partidoRes,laboratorio.enteroRes,laboratorio.yeso,laboratorio.danado,laboratorio.ip FROM laboratorio,tiquete WHERE laboratorio.idTiquete='"+numtiquete+"' and tiquete.idTiquete=laboratorio.idTiquete");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusT.chcedula.isSelected() == true) {
            if (!cedula.equals("")) {
                tbl.llenarTabla(BusT.jTable2, mdelolab, columnasAgri.length, "SELECT laboratorio.idLaboratorio,laboratorio.idTiquete,laboratorio.fecha,laboratorio.humedad,laboratorio.impureza,laboratorio.integralRes,laboratorio.cascarillaRes,laboratorio.blancoRes,laboratorio.partidoRes,laboratorio.enteroRes,laboratorio.yeso,laboratorio.danado,laboratorio.ip FROM laboratorio,tiquete WHERE tiquete.idTiquete=laboratorio.idTiquete AND tiquete.idAgricultor='"+id+"'");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos de busqueda esta seleccionado");
        }
        desactivar_checkboxAgricultor();
    }

    /**
     * Busqueda Conductor
     */
    
}
