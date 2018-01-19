/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Laboratorio;
import Negocio.Conexion;
import Interfaces.Laboratorio;
import static Logica.Administracion.empleado.Con;
import static Logica.Administracion.empleado.Emp;
import Logica.Extras.login;
import Logica.Extras.tablas;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import lu.tudor.santec.jtimechooser.JTimeChooser;

/**
 *
 * @author uriel
 */
public class laboratorio {
    public static Laboratorio Laboratorio;
    public static tablas tbl;
    public DefaultTableModel modeloemp,modeloestufa;
    public login login;
    public String columnas[] = new String[]{"idLaboratorio","idTiquete","fecha","humedad", "impureza","integralRes", "cascarillaRes", "blancoRes","partidoRes","enteroRes","yeso","dañado","ip"};
    public String columnas2[] = new String[]{"idLaboratorio","idTiquete","muestreo","hora","humedad"};
    
    public String user;
     public static Statement st,st4;
     public static ResultSet rs,rsconsecutivo;
    public static Conexion Con;
    public String IDLaboratorio,muestreo,hora,humedadEstufa;
    public String idTiquete,fecha,humedad,impureza,integralRes,cascarillaRes,blancoRes,partidoRes,enteroRes,yeso,danado,ip,consecutivo;
    
    public laboratorio(){
        fecha();
       crearModelo();
        consecutivo();
       // time();
    }
    
    public void crearModelo() {
        modeloemp = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(Laboratorio.jTable1, modeloemp, columnas.length, "SELECT idLaboratorio,idTiquete,fecha,humedad,impureza,integralRes,cascarillaRes,blancoRes,partidoRes,enteroRes,yeso,danado,ip FROM laboratorio");

    }public void crearModelo2(String id) {
        modeloestufa = new DefaultTableModel(null, columnas2) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(Laboratorio.jTable2, modeloestufa, columnas2.length, "SELECT idmuetraestufa,idLaboratorio,muestreo,hora,humedad FROM muetraestufa where idLaboratorio ='" + id + "' ");

    }
    
    
    
     public void fecha() {
        Calendar c;
        c = Calendar.getInstance();
        int d = c.get(Calendar.DATE), m = 1 + (c.get(Calendar.MONTH)), a = c.get(Calendar.YEAR);
        Laboratorio.TxtFecha.setText(a + "/" + m + "/" + d);

    }
     
     public void actualizar(){
          Laboratorio.TxtIDTiqueteEstufa.setText("");
        Laboratorio.TxtHora.setText("");
        Laboratorio.TxtHumedadEstufa.setText("");
        Laboratorio.TxtMuestreo.setText("");
        
        crearModelo();
         crearModelo2(ip);
     }
     /*
      public void tablas_campos() {
        int rec = Laboratorio.jTable1.getSelectedRow();
        Laboratorio.LabelTiquete.setText(Laboratorio.jTable1.getValueAt(rec, 0).toString());
        Laboratorio.TxtFecha.setText(Laboratorio.jTable1.getValueAt(rec, 1).toString());
        //Laboratorio.TxtVariedad.setText(Laboratorio.jTable1.getValueAt(rec, 2).toString());
        Laboratorio.TxtHumedad.setText(Laboratorio.jTable1.getValueAt(rec, 3).toString());
        Laboratorio.TxtImpureza.setText(Laboratorio.jTable1.getValueAt(rec, 4).toString());
       // Laboratorio.TxtHumedadRes.setText(Laboratorio.jTable1.getValueAt(rec, 5).toString());
        Laboratorio.TxtIntegral.setText(Laboratorio.jTable1.getValueAt(rec, 6).toString());
        Laboratorio.TxtCascarilla.setText(Laboratorio.jTable1.getValueAt(rec, 7).toString());
        Laboratorio.TxtBlanco.setText(Laboratorio.jTable1.getValueAt(rec, 8).toString());
        Laboratorio.TxtPartido.setText(Laboratorio.jTable1.getValueAt(rec, 9).toString());
        //Laboratorio.enteroRes.setText(Laboratorio.jTable1.getValueAt(rec, 10).toString());
        Laboratorio.enteroRes.setText(Laboratorio.jTable1.getValueAt(rec, 10).toString());
    }
    */
    
    public void guardar_inicial(){
        idTiquete = Laboratorio.TxtIdTiquete.getText();
        fecha = Laboratorio.TxtFecha.getText();
        //variedad = Laboratorio.TxtVariedad.getText();
        humedad = Laboratorio.TxtHumedad.getText();
        impureza = Laboratorio.TxtImpureza.getText();
        //humedadEst = Laboratorio.TxtHumedadRes.getText();
        user = login.enviarUsuario();
        integralRes = Laboratorio.TxtIntegral.getText();
        cascarillaRes = Laboratorio.TxtCascarilla.getText();
        blancoRes = Laboratorio.TxtBlanco.getText();
        partidoRes = Laboratorio.TxtPartido.getText();
        enteroRes= Laboratorio.enteroRes.getText();
        yeso = Laboratorio.TxtYeso.getText();
        danado = Laboratorio.TxtDanado.getText();
        ip = Laboratorio.TxtIp.getText();
        
        
        if (!fecha.equals("")&&!idTiquete.equals("")&&!humedad.equals("")&&!impureza.equals("")&&!yeso.equals("")&&!integralRes.equals("")&&!cascarillaRes.equals("")&&!blancoRes.equals("")&&!partidoRes.equals("")&&!enteroRes.equals("")&&!danado.equals("")&&!ip.equals("")){
            insertar_inicial(idTiquete,user,fecha,humedad, impureza,integralRes, cascarillaRes, blancoRes, partidoRes,enteroRes,yeso,danado,ip);
            limpiar_campos();
            
        }else{
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        }
    }
    public void insertar_inicial(String idTiquete,String user,String fecha,String humedad,String impureza,String integralRes,String cascarillaRes,String blancoRes,String partidoRes,String enteroRes,String yeso,String danado, String ip){
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            
            st.executeUpdate("Insert Into laboratorio (idLaboratorio,idTiquete,user,fecha,humedad,impureza,integralRes,cascarillaRes,blancoRes,partidoRes,enteroRes,yeso,danado,ip) values (0,'" + idTiquete + "','" + user + "','" + fecha + "','" + humedad + "','" + impureza + "','" + integralRes + "','" + cascarillaRes + "','" + blancoRes + "','" + partidoRes + "','" + enteroRes + "','" + yeso + "','" + danado + "','" + ip + "')");
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
    /*
    public void actualizar(){
        String c=Laboratorio.LabelTiquete.getText();
           fecha = Laboratorio.TxtFecha.getText();
//        variedad = Laboratorio.TxtVariedad.getText();
        humedad = Laboratorio.TxtHumedad.getText();
        impureza = Laboratorio.TxtImpureza.getText();
       // humedadEst = Laboratorio.TxtHumedadRes.getText();
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
*/
    public void limpiar_campos(){
        
        Laboratorio.TxtFecha.setText("");
        Laboratorio.TxtYeso.setText("");
        Laboratorio.TxtDanado.setText("");
        Laboratorio.TxtIp.setText("");
        Laboratorio.TxtIdTiquete.setText("");
       // Laboratorio.TxtVariedad.setText("");
        Laboratorio.TxtHumedad.setText("");
        Laboratorio.TxtImpureza.setText("");
      //  Laboratorio.TxtHumedadRes.setText("");
        Laboratorio.TxtIntegral.setText("");
        Laboratorio.TxtCascarilla.setText("");
        Laboratorio.TxtBlanco.setText("");
        Laboratorio.TxtPartido.setText("");
        Laboratorio.enteroRes.setText("");
        consecutivo();
        fecha();
    }
    public void tabla_consecutivo_campo(){
        int rec = Laboratorio.jTable1.getSelectedRow();
        Laboratorio.TxtIDTiqueteEstufa.setText(Laboratorio.jTable1.getValueAt(rec, 0).toString());
        String id=Laboratorio.jTable1.getValueAt(rec, 0).toString();
        crearModelo2(id);
    }
   
    public void guardar_estufa(){
        IDLaboratorio =Laboratorio.TxtIDTiqueteEstufa.getText();
        muestreo=Laboratorio.TxtMuestreo.getText();
        hora=Laboratorio.jTimeChooser1.getFormatedTime();
        humedadEstufa=Laboratorio.TxtHumedadEstufa.getText();
        
        if (!IDLaboratorio.equals("")&&!muestreo.equals("")&&!hora.equals("")&&!humedadEstufa.equals("")){
            insertar_humedad_estufa(IDLaboratorio, muestreo, hora, humedadEstufa);
            crearModelo2(IDLaboratorio);
            
        }
        
    }
    public void insertar_humedad_estufa(String IDLaboratorio,String muestreo,String hora,String humedadEstufa){
        try {
            Con = new Conexion();
            st4 = Con.conexion.createStatement();
            
            st4.executeUpdate("Insert Into muetraestufa (idmuetraestufa,idLaboratorio,muestreo,hora,humedad) values (0,'" + IDLaboratorio + "','" + muestreo + "','" + hora + "','" + humedadEstufa + "')");
            JOptionPane.showMessageDialog(null, "El registro ha sido agregado");
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
    public void time(){
        JTimeChooser hora;
        hora = new JTimeChooser();
		
		hora.setBounds(100, 100, 1000, 1000);
        hora.setVisible(true);
        hora.setEnabled(true);
        hora.getAutoscrolls();
        //hora.setShowSeconds(false);
        hora.getTimeField();
            Calendar calendar = new GregorianCalendar();
        hora.getCalendarWithTime(calendar);
        
        Laboratorio.jPanel3.add(hora);
    }
*/
}
