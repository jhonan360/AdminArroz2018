/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Extras;
import com.mysql.jdbc.Connection;
import java.sql.CallableStatement;
import Negocio.Conexion;
/**
 *
 * @author jhonansmith
 */
public class log {
    public static  CallableStatement call;
    public static Conexion Con;
    
    public void logAgricultor(String tipo, String usuario, String ccAgricultor, String nombres, String apellidos, String direccion, int idMunicipio){
        try {
           Con = new Conexion();
           call=Con.conexion.prepareCall("{call logAgricultor(?,?,?,?,?,?,?)}");
           //CALL logAgricultor("i","yo","222","222","222","222","2222")
           //INSERT INTO agricultorlog VALUES (0,"i",now(),"yo","222","222","222","222","2222")
           call.setString(1, tipo);
           call.setString(2, usuario);
           call.setString(3, ccAgricultor);
           call.setString(4, nombres);
           call.setString(5, apellidos);
           call.setString(6, direccion);
           call.setInt(7, idMunicipio);
           call.execute();
           Con.Desconectar();
            System.err.println("log agricultor");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void logConductor(String tipo, String usuario, String ccConductor, String nombres, String apellidos, String telefono ,String direccion, int idMunicipio){
        try {
           Con = new Conexion();
           call=Con.conexion.prepareCall("{call logConductor(?,?,?,?,?,?,?,?)}");
           call.setString(1, tipo);
           call.setString(2, usuario);
           call.setString(3, ccConductor);
           call.setString(4, nombres);
           call.setString(5, apellidos);
           call.setString(6, telefono);
           call.setString(7, direccion);
           call.setInt(8, idMunicipio);
           call.execute();
           Con.Desconectar();
           System.err.println("log Conductor");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void logTelAgri(String tipo, String usuario, int idTelAgri, String telefono, String ccAgricultor){
        try {
           Con = new Conexion();
           call=Con.conexion.prepareCall("{call logTelAgri(?,?,?,?,?)}");
           call.setString(1, tipo);
           call.setString(2, usuario);
           call.setInt(3, idTelAgri);
           call.setString(4, telefono);
           call.setString(5, ccAgricultor);
           call.execute();
           Con.Desconectar();
           System.err.println("log TelAgri");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void logVehiculo(String tipo, String usuario, String idplaca, String color, String modelo, String marca){
        try {
           Con = new Conexion();
           call=Con.conexion.prepareCall("{call logVehiculo(?,?,?,?,?,?)}");
           call.setString(1, tipo);
           call.setString(2, usuario);
           call.setString(3, idplaca);
           call.setString(4, color);
           call.setString(5, modelo);
           call.setString(6, marca);
           call.execute();
           Con.Desconectar();
           System.err.println("log Vehiculo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void logZona(String tipo, String usuario, int idZona, String nombre, String descripcion, int idMunicipio){
        try {
           Con = new Conexion();
           call=Con.conexion.prepareCall("{call logZona(?,?,?,?,?,?)}");
           call.setString(1, tipo);
           call.setString(2, usuario);
           call.setInt(3, idZona);
           call.setString(4, nombre);
           call.setString(5, descripcion);
           call.setInt(6, idMunicipio);
           call.execute();
           Con.Desconectar();
           System.err.println("log zona");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void logBloqueado(String usuario){
        try {
           Con = new Conexion();
           call=Con.conexion.prepareCall("{call logBloqueado(?)}");
           call.setString(1, usuario);
              call.execute();
           Con.Desconectar();
           System.err.println("log Bloqueado");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
