/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Extras;

import Negocio.Conexion;
import java.sql.ResultSet;
import java.sql.Statement;

public class extras {

    public static Conexion Con;
    public static Statement st, st1;
    public static ResultSet rs, rs1;

    public String getIdMunicipio(String nombre, String idDeparta) { // Metodo que retorna el id del municipio recibe el nombre del municipio y el ID del depatamento
        try {

            Con = new Conexion();
            // Con.Conectar();
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
    }
    // ATENCION no estoy seguro de este 
    public String getIdDepartamento(String nombre) {// metodo que retorna el id del departamento recibe el nombre del departamento
        try {
            Con = new Conexion();
            Con.Conectar();
            st1 = Con.conexion.createStatement();
            rs1 = st1.executeQuery("SELECT nombre FROM departamentos WHERE idDepartamento=(SELECT idDepartamento FROM municipios WHERE nombre='" + nombre + "')");
            while (rs1.next()) {
                return rs1.getString(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    // metodo que retorna el nombre del departamento donde reside el usuario se utiliza switch case para determinar la sentencia
    //ATENCION no se si colocar numeros o seguir trabajando con letras
    public String getDepartamento(String opc, String cedula) {
        String res, sentencia = null;
        switch (opc) {
            case "a":
                sentencia = "SELECT departamentos.nombre FROM departamentos WHERE idDepartamento=(SELECT municipios.idDepartamento FROM municipios,agricultor WHERE agricultor.ccAgricultor='" + cedula + "' AND agricultor.idMunicipio=municipios.idMunicipio)";
                break;
            case "e":
                sentencia = "SELECT departamentos.nombre FROM departamentos WHERE idDepartamento=(SELECT municipios.idDepartamento FROM municipios,empleado WHERE empleado.ccEmpleado='" + cedula + "' and empleado.idMunicipio=municipios.idMunicipio)";
                break;
            case "c":
                sentencia = "SELECT departamentos.nombre FROM departamentos WHERE idDepartamento=(SELECT municipios.idDepartamento FROM municipios,conductor WHERE conductor.ccConductor='" + cedula + "' and conductor.idMunicipio=municipios.idMunicipio)";
                break;
            case "z":
                sentencia = "SELECT departamentos.nombre FROM departamentos WHERE idDepartamento=(SELECT municipios.idDepartamento FROM municipios,lote WHERE lote.idLote='"+cedula+"' and lote.idMunicipio=municipios.idMunicipio)";
                break;
        }
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery(sentencia);
            while (rs.next()) {
                res=rs.getString(1);
                Con.Desconectar();
                return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String[] getIdTelefonos(String cedula, int fil) {
        String[] idsTel = new String[fil];
        int i = 0;
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("SELECT idTelAgri FROM `telagri` WHERE ccAgricultor='" + cedula + "'");

            while (rs.next()) {

                idsTel[i] = rs.getString(1);
                i++;
            }
            return idsTel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean validarTelefonos(String telefono1, String telefono2, String telefono3) {
        if (!telefono1.equals(telefono2) && !telefono1.equals(telefono3)) {
            if (!(telefono2.equals("") || !telefono3.equals(""))) {
                if (!telefono2.equals(telefono3)) {
                    return true;
                }else{
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
    // metodo para validar la exitencia del usuario en la BD se utiliza switch case para determinar la sentencia
    //ATENCION no se si colocar numeros o seguir trabajando con letras
    public static boolean validar(String opc, String cedula) {
        String res, sentencia;
        switch (opc) {
            case "a":
                sentencia = "SELECT ccAgricultor FROM agricultor WHERE ccAgricultor = '" + cedula + "'";
                break;
            default:
            case "e":
                sentencia = "SELECT ccEmpleado FROM empleado WHERE ccEmpleado='" + cedula + "'";
                break;

            case "c":
                sentencia = "SELECT ccConductor FROM conductor WHERE ccConductor = '" + cedula + "'";
                break;
            case "z":
                sentencia = "SELECT nombre FROM lote WHERE nombre='" + cedula + "'";
                break;
                
            case "v":
                sentencia = "SELECT placa FROM vehiculo WHERE placa='" + cedula + "'";
                break;
        }
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery(sentencia);
            while (rs.next()) {
                res = rs.getObject(1) + "";
                if (rs.getString(1) == null) {
                    Con.Desconectar();
                    return false;
                }
                if (cedula.equals(res)) {
                    Con.Desconectar();
                    return true;
                } else {
                    Con.Desconectar();
                    return false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public int getNextIndex(String tabla){
        String id="";
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("select AUTO_INCREMENT from information_schema.TABLES where TABLE_SCHEMA='molino' and TABLE_NAME='"+tabla+"'");
            while (rs.next()) {
               id= rs.getObject(1) + "";
         }
            Con.Desconectar();
            return Integer.parseInt(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
   
}
