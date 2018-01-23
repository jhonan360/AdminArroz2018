/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Laboratorio;


import Interfaces.BusquedasTiqueteInicial;
import Interfaces.Laboratorio_tiquete_inicial;
import Logica.Extras.login;
import Negocio.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;




/**
 *
 * @author uriel
 */
public class laboratorio_tiquete_inicial {
    public static ResultSet rstipo,rstipo2,rsconsecutivo,rstipos;
    public static login login;
    public String user;
    public static Laboratorio_tiquete_inicial labo;
    public static BusquedasTiqueteInicial Bust;
    public static Statement st,st1,st2,st3;
     public static Conexion Con;
     public static String consecutivo,idTiquete,ccAgricultor,idTipoDeArroz,idAgricultor,humedadUno,impurezaUno;
    
    public laboratorio_tiquete_inicial(){
        cargarTipoArroz();
        consecutivo();
        
        System.out.println("usuario es = "+ user);
    }
    
     public void cargarTipoArroz() {
        try {
            Con = new Conexion();
            Con.Conectar();
            st = Con.conexion.createStatement();
            labo.cmbTipoArroz.removeAllItems();
            rstipo = st.executeQuery("SELECT idTipoDeArroz,nombre FROM tipodearroz");
            while (rstipo.next()) {
                labo.cmbTipoArroz.addItem(rstipo.getString(2));
            }
            String seleccion = (labo.cmbTipoArroz.getSelectedItem().toString());
            rstipo2 = st.executeQuery("SELECT idTipoDeArroz FROM tipodearroz WHERE nombre='" + seleccion + "' ");
            while (rstipo2.next()) {
                String tipo = rstipo2.getString(1);
                //System.out.println(tipo);
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     public static void abrirBusquedasTiquete() {
        Bust = new BusquedasTiqueteInicial();
        Bust.setVisible(true);

        
                //panel agricultor
                Bust.panel.setEnabledAt(0, true);
                Bust.panel.setEnabledAt(1, false);
               
                Bust.panel.setSelectedIndex(0);
                
    }
     public static void limpiarCampos(){
         labo.txtAgricultor.setText("");
         labo.TxtHumedadUno.setText("");
         labo.TxtImpurezaUno.setText("");
         labo.cmbTipoArroz.setSelectedIndex(0);
     }
     public void consecutivo() {// Recibe la cedula del empleado y retorna el nombre del departamento de recidencia 
        try {
            Con = new Conexion();

            st1 = Con.conexion.createStatement();
            rsconsecutivo = st1.executeQuery("SELECT MAX(idTiquete) FROM tiquete");
            while (rsconsecutivo.next()) {
                String res = rsconsecutivo.getString(1);
                consecutivo = (Integer.parseInt(res)+1)+"";
            }
            
            labo.Tiquete.setText(consecutivo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
     public void guardar(){
         idTiquete = labo.Tiquete.getText();
         idAgricultor=ccAgricultor;
         idTipoDeArroz=labo.cmbTipoArroz.getSelectedItem().toString();
         user = login.enviarUsuario();
         humedadUno = labo.TxtHumedadUno.getText();
         impurezaUno = labo.TxtImpurezaUno.getText();
         
         System.out.println("idTiquete= "+idTiquete);
          System.out.println("idAgricultor= "+idAgricultor);
           System.out.println("idTipoDeArroz= "+idTipoDeArroz);
            System.out.println("user= "+user);
             System.out.println("humedad= "+humedadUno);
              System.out.println("impureza= "+impurezaUno);
         
         
         
          if (!idTiquete.equals("") && !idAgricultor.equals("") && !idTipoDeArroz.equals("") && !user.equals("") && !humedadUno.equals("") && !impurezaUno.equals("") ) {
            
idTipoDeArroz = getIdTipo(idTipoDeArroz);
            insertar(idTiquete, idAgricultor, idTipoDeArroz, user, humedadUno, impurezaUno);//Llamado al metodo insertar
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        }
         
     }
     public void insertar(String idTiquete,String idAgricultor,String idTipoDeArroz,String user,String humedadUno,String impurezaUno){
          try {
            Con = new Conexion();
            st2 = Con.conexion.createStatement();
            st2.executeUpdate("INSERT INTO tiquete (idTiquete,idAgricultor,idTipoDeArroz,user,humedadUno,impurezaUno) VALUES ('" + idTiquete + "','" + idAgricultor + "','" + idTipoDeArroz + "','" + user + "','" + humedadUno + "','" + impurezaUno + "')");
            JOptionPane.showMessageDialog(null, "Tiquete registrado");
            consecutivo();
        } catch (Exception e) {
            e.printStackTrace();
        }
     }
     public static String getIdTipo(String nombre) {
        try {
            Con.Conectar();
            st3 = Con.conexion.createStatement();
            rstipos = st3.executeQuery("SELECT idTipoDeArroz FROM tipodearroz WHERE nombre='" + nombre + "'");
            while (rstipos.next()) {
                return rstipos.getString(1);
            }

            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}