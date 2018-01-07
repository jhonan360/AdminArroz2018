/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Administracion;
import Interfaces.Usuarios;
import Logica.Extras.cargarCombo;
import Logica.Extras.tablas;
import Logica.Extras.encriptar;
import javax.swing.table.DefaultTableModel;
import Negocio.Conexion;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.plaf.OptionPaneUI;

        

/**
 *
 * @author uriel
 */
public class usuarios {
    public static DefaultTableModel modelemp,modelusu;
    public static tablas tbl;
    public static Conexion Con;
    public static encriptar encrip;
    public static String columnas[]= new String[]{"Cedula","Nombres","Apellidos"};
    public static String columnasUsu[]= new String[]{"Usuario","Contrasena","Privilegio","Estado"};
    public static Usuarios Usu;
    public static Statement st;
    public static ResultSet rs,rs1,rs2;
    public static String cedula,usuario,contrasena,contraencript,privilegio,estado;
    
    
    public usuarios(){
        crearModelo();
        tbl = new tablas();
    }
    
     public void crearModelo(){
        modelemp = new DefaultTableModel(null, columnas) {
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
    };
        tbl = new tablas();
        tbl.llenarTabla(Usu.jTable1,modelemp,columnas.length,"SELECT ccEmpleado,nombres,apellidos FROM empleado ");
        
        modelusu = new DefaultTableModel(null,columnasUsu){
            public boolean isCellEditable(int filas,int columnas){
                return false;
            }
        };
                
}
     
     
     public void crearModeloUsu(String cedula){
        
        modelusu = new DefaultTableModel(null,columnasUsu){
            public boolean isCellEditable(int filas,int columnas){
                return false;
            }
        };
        tbl.llenarTabla(Usu.jTable2, modelusu, columnasUsu.length, "SELECT usuario.user,usuario.contrasena,privilegios.nombre,usuario.estado FROM usuario,privilegios WHERE idEmpleado=(SELECT empleado.idEmpleado FROM empleado WHERE ccEmpleado='"+cedula+"') AND usuario.idPrivilegios=privilegios.idPrivilegios");
        
        }
     public void tablas_campos(){
         int rec = Usu.jTable1.getSelectedRow();
        Usu.txtCedula.setText(Usu.jTable1.getValueAt(rec, 0).toString());
        Usu.txtNombre.setText(Usu.jTable1.getValueAt(rec, 1).toString());
        Usu.txtApellidos.setText(Usu.jTable1.getValueAt(rec, 2).toString());
        crearModeloUsu(Usu.jTable1.getValueAt(rec, 0).toString());
        
        
     }
     public void tabla_usuario_campos(){
         int rec = Usu.jTable2.getSelectedRow();
         Usu.txtUsuario.setText(Usu.jTable2.getValueAt(rec,0).toString());
         Usu.txtContrasena.setText(Usu.jTable2.getValueAt(rec,1).toString());
         Usu.cmbPrivilegio.setSelectedItem(Usu.jTable2.getValueAt(rec,2).toString());
         Usu.cmbEstado.setSelectedItem(Usu.jTable2.getValueAt(rec,3).toString());
         tablas_campos();
     }
     public void limpiar_campos(){
        Usu.txtCedula.setText("");
        Usu.txtNombre.setText("");
        Usu.txtApellidos.setText("");
        Usu.txtUsuario.setText("");
        Usu.txtContrasena.setText("");
        Usu.cmbPrivilegio.setSelectedIndex(0);
        Usu.cmbEstado.setSelectedIndex(0);
     }
     public void crear_usuario(){
         cedula = Usu.txtCedula.getText();
         usuario = Usu.txtUsuario.getText();
         char[] Pass = Usu.txtContrasena.getPassword();
         contrasena = new String(Pass);
         privilegio =String.valueOf(Usu.cmbPrivilegio.getSelectedIndex()+1);
         estado = Usu.cmbEstado.getSelectedItem().toString();
         if(!cedula.equals("") && !usuario.equals("")&& ! contrasena.equals("")){
            // System.out.println("contraseña = "+contrasena);
             try {
                 contraencript=encrip.main(contrasena);
              //   System.out.println("contra"+hope);
             } catch (Exception e) {
                 e.printStackTrace();
             } 
                 //(System.out.println("sss"+hope );
             
             insertar_usuario(cedula, usuario, contraencript, privilegio,estado);
             crearModeloUsu(cedula);
            
         }else{
             JOptionPane.showMessageDialog(null, "alguno de los campos se encuentra vacio");
         }
     }
    public void insertar_usuario(String cedula,String usuario,String contrasena,String privilegio,String estado){
         try {
             
             Con = new Conexion();
             st = Con.conexion.createStatement();
             int conusu=0;
             rs2 = st.executeQuery("SELECT COUNT(usuario.user) FROM usuario WHERE usuario.user='"+usuario+"'");
             while(rs2.next()){
                 conusu= ((Number) rs2.getObject(1)).intValue();
             }
             String idUsuario="";
             rs = st.executeQuery("SELECT idEmpleado FROM empleado WHERE ccEmpleado='"+cedula+"'");
             while(rs.next()){
              idUsuario =rs.getObject(1) + "";   
             }
             int contador=0;
             rs1= st.executeQuery("SELECT count(privilegios.nombre) from usuario,privilegios WHERE idEmpleado ='"+idUsuario+"' AND usuario.idPrivilegios='"+privilegio+"'  AND usuario.idPrivilegios=privilegios.idPrivilegios");
             while(rs1.next()){
                 
                 contador =  ((Number) rs1.getObject(1)).intValue();
                 System.out.println("contador= "+contador);
                 contraencript="";
                     
             }
             if(conusu<1){
                 if (contador==0){
                      st.executeUpdate("INSERT INTO usuario (user,contrasena,idEmpleado,idPrivilegios,estado) VALUES('"+usuario+"','"+contrasena+"','"+idUsuario+"','"+privilegio+"','"+estado+"')");
                      JOptionPane.showMessageDialog(null,"El usuario a sido ingresado");
                       limpiar_campos();
                 }else{
                    JOptionPane.showMessageDialog(null,"Usted ya cuenta con un usuario de este tipo"); 
                 }
             }else{
                 JOptionPane.showMessageDialog(null,"Ye existe un usuario con este nombre"); 
             }
            
             Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
     }
    public void modificar_usuario(){
         cedula = Usu.txtCedula.getText();
         usuario = Usu.txtUsuario.getText();
         char[] Pass = Usu.txtContrasena.getPassword();
         contrasena = new String(Pass);
         privilegio =String.valueOf(Usu.cmbPrivilegio.getSelectedIndex()+1);
         estado = Usu.cmbEstado.getSelectedItem().toString();
        if(!cedula.equals("") && !usuario.equals("")&& ! contrasena.equals("")){
            int aceptar = JOptionPane.showConfirmDialog(null, "Esta seguro que quiere modificar la informacion del empleado", "Confirmacion", JOptionPane.CANCEL_OPTION);
                if (aceptar == JOptionPane.YES_OPTION) {
                     try {
                 contraencript=encrip.main(contrasena);
              //   System.out.println("contra"+hope);
             } catch (Exception e) {
             }
                    actualizar_usuario(cedula, usuario, contraencript, privilegio, estado);
                    contraencript="";
            crearModeloUsu(cedula);
             limpiar_campos();
                }else{
                    JOptionPane.showMessageDialog(null,"los cambios seran descartados");
                }
            
        }else{
           JOptionPane.showMessageDialog(null, "alguno de los campos se encuentra vacio");
        }
}
    public void actualizar_usuario(String cedula,String usuario,String contrasena,String privilegio,String estado){
        try{
             Con = new Conexion();
             st = Con.conexion.createStatement();
             st.executeUpdate("UPDATE usuario SET contrasena='"+contrasena+"',idPrivilegios='"+privilegio+"',estado='"+estado+"' WHERE usuario.idEmpleado=(SELECT empleado.idEmpleado FROM empleado WHERE ccEmpleado='"+cedula+"') AND usuario.user='"+usuario+"'");
        }catch(Exception e){
             e.printStackTrace();
        }
    }
}