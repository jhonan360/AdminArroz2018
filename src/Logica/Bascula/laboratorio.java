/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Bascula;
import Negocio.Conexion;
import Interfaces.Laboratorio;
import static Logica.Administracion.empleado.Con;
import static Logica.Administracion.empleado.Emp;
import Logica.Extras.tablas;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author uriel
 */
public class laboratorio {
    public static Laboratorio Laboratorio;
    public static tablas tbl;
    public DefaultTableModel modeloemp;
    public String columnas[] = new String[]{"numero","fecha", "variedad", "humedad", "impureza","humedadRes", "integralRes", "cascarillaRes", "blancoRes","partidoRes","enteroRes"};
    
     public static Statement st;
     public static ResultSet rs,rsconsecutivo;
    public static Conexion Con;
    public String fecha,variedad,humedad,impureza,humedadEst,integralRes,cascarillaRes,blancoRes,partidoRes,enteroRes,consecutivo;
    
    public laboratorio(){
        fecha();
        crearModelo();
        consecutivo();
    }
    public void crearModelo() {
        modeloemp = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(Laboratorio.jTable1, modeloemp, columnas.length, "SELECT idLaboratorio,fecha,variedad,humedad,impureza,humedadEstufa,integralRes,cascarillaRes,blancoRes,partidoRes,enteroRes FROM laboratorio");

    }
    
    
     public void fecha() {
        Calendar c;
        c = Calendar.getInstance();
        int d = c.get(Calendar.DATE), m = 1 + (c.get(Calendar.MONTH)), a = c.get(Calendar.YEAR);
        Laboratorio.TxtFecha.setText(a + "/" + m + "/" + d);

    }
     
      public void tablas_campos() {
        int rec = Laboratorio.jTable1.getSelectedRow();
        Laboratorio.LabelTiquete.setText(Laboratorio.jTable1.getValueAt(rec, 0).toString());
        Laboratorio.TxtFecha.setText(Laboratorio.jTable1.getValueAt(rec, 1).toString());
        Laboratorio.TxtVariedad.setText(Laboratorio.jTable1.getValueAt(rec, 2).toString());
        Laboratorio.TxtHumedad.setText(Laboratorio.jTable1.getValueAt(rec, 3).toString());
        Laboratorio.TxtImpureza.setText(Laboratorio.jTable1.getValueAt(rec, 4).toString());
        Laboratorio.TxtHumedadRes.setText(Laboratorio.jTable1.getValueAt(rec, 5).toString());
        Laboratorio.TxtIntegral.setText(Laboratorio.jTable1.getValueAt(rec, 6).toString());
        Laboratorio.TxtCascarilla.setText(Laboratorio.jTable1.getValueAt(rec, 7).toString());
        Laboratorio.TxtBlanco.setText(Laboratorio.jTable1.getValueAt(rec, 8).toString());
        Laboratorio.TxtPartido.setText(Laboratorio.jTable1.getValueAt(rec, 9).toString());
        //Laboratorio.enteroRes.setText(Laboratorio.jTable1.getValueAt(rec, 10).toString());
        Laboratorio.enteroRes.setText(Laboratorio.jTable1.getValueAt(rec, 10).toString());
    }
    
    
    public void guardar_inicial(){
        fecha = Laboratorio.TxtFecha.getText();
        variedad = Laboratorio.TxtVariedad.getText();
        humedad = Laboratorio.TxtHumedad.getText();
        impureza = Laboratorio.TxtImpureza.getText();
        humedadEst = Laboratorio.TxtHumedadRes.getText();
        integralRes = Laboratorio.TxtIntegral.getText();
        cascarillaRes = Laboratorio.TxtCascarilla.getText();
        blancoRes = Laboratorio.TxtBlanco.getText();
        partidoRes = Laboratorio.TxtPartido.getText();
        enteroRes= Laboratorio.enteroRes.getText();
        
        
        if (!fecha.equals("")&&!variedad.equals("")&&!humedad.equals("")&&!impureza.equals("")&&!humedadEst.equals("")&&!integralRes.equals("")&&!cascarillaRes.equals("")&&!blancoRes.equals("")&&!partidoRes.equals("")&&!enteroRes.equals("")){
            insertar_inicial(fecha, variedad, humedad, impureza, humedadEst, integralRes, cascarillaRes, blancoRes, partidoRes,enteroRes);
        }else{
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        }
    }
    public void insertar_inicial(String fecha,String variedad,String humedad,String impureza,String humedadEst,String integralRes,String cascarillaRes,String blancoRes,String partidoRes,String enteroRes){
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            
            st.executeUpdate("Insert Into laboratorio (idLaboratorio,fecha,variedad,humedad,impureza,humedadEstufa,integralRes,cascarillaRes,blancoRes,partidoRes,enteroRes) values (0,'" + fecha + "','" + variedad + "','" + humedad + "','" + impureza + "','" + humedadEst + "','" + integralRes + "','" + cascarillaRes + "','" + blancoRes + "','" + partidoRes + "','" + enteroRes + "')");
            JOptionPane.showMessageDialog(null, "El registro ha sido agregado");
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void consecutivo() {// Recibe la cedula del empleado y retorna el nombre del departamento de recidencia 
        try {
            Con = new Conexion();

            st = Con.conexion.createStatement();
            rsconsecutivo = st.executeQuery("SELECT MAX(idLaboratorio) FROM laboratorio");
            while (rsconsecutivo.next()) {
                String res = rsconsecutivo.getString(1);
                consecutivo = (Integer.parseInt(res)+1)+"";
            }
            
            Laboratorio.LabelTiquete.setText(consecutivo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
    public void actualizar(){
        String c=Laboratorio.LabelTiquete.getText();
           fecha = Laboratorio.TxtFecha.getText();
        variedad = Laboratorio.TxtVariedad.getText();
        humedad = Laboratorio.TxtHumedad.getText();
        impureza = Laboratorio.TxtImpureza.getText();
        humedadEst = Laboratorio.TxtHumedadRes.getText();
        integralRes = Laboratorio.TxtIntegral.getText();
        cascarillaRes = Laboratorio.TxtCascarilla.getText();
        blancoRes = Laboratorio.TxtBlanco.getText();
        partidoRes = Laboratorio.TxtPartido.getText();
        enteroRes= Laboratorio.enteroRes.getText();
        
        
        if (!fecha.equals("")&&!variedad.equals("")&&!humedad.equals("")&&!impureza.equals("")&&!humedadEst.equals("")&&!integralRes.equals("")&&!cascarillaRes.equals("")&&!blancoRes.equals("")&&!partidoRes.equals("")&&!enteroRes.equals("")){
     int aceptar = JOptionPane.showConfirmDialog(null, "Esta seguro que quiere modificar la informacion del empleado", "Confirmacion", JOptionPane.CANCEL_OPTION);
                if (aceptar == JOptionPane.YES_OPTION) {
                    try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            
            st.executeUpdate("Update laboratorio set fecha='" + fecha + "', variedad= '" + variedad + "',humedad='" + humedad + "',impureza='" + impureza + "',humedadEstufa='" + humedadEst + "',integralRes='" + integralRes + "',cascarillaRes='" + cascarillaRes + "',blancoRes='" + blancoRes + "',partidoRes='" + partidoRes + "',enteroRes='" + enteroRes + "'where idLaboratorio='" + c + "'");
            JOptionPane.showMessageDialog(null, "El registro ha sido modificado");
            fecha();
            limpiar_campos();
            crearModelo();
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
                }else{
                    JOptionPane.showMessageDialog(null, "ha canselado");
                    fecha();
            limpiar_campos();
            crearModelo();
                }
            
            
        
        }else{
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        } 
        }
    public void limpiar_campos(){
        consecutivo();
        fecha();
        Laboratorio.TxtFecha.setText("");
        Laboratorio.TxtVariedad.setText("");
        Laboratorio.TxtHumedad.setText("");
        Laboratorio.TxtImpureza.setText("");
        Laboratorio.TxtHumedadRes.setText("");
        Laboratorio.TxtIntegral.setText("");
        Laboratorio.TxtCascarilla.setText("");
        Laboratorio.TxtBlanco.setText("");
        Laboratorio.TxtPartido.setText("");
        Laboratorio.enteroRes.setText("");
    }
}
