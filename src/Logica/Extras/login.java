/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Extras;

/**
 *
 * @author uriel
 */
import Interfaces.CambioContrasena;
import Interfaces.Bascula;
import Logica.Bascula.bascula;

import Interfaces.Gerente;

import Logica.Bascula.conductor;
import Logica.Gerencia.gerencia;
import Interfaces.Liquidacion;
import Logica.Auditor.auditor;
import Interfaces.Auditor;
import Logica.Extras.StringEncrypt;
import Interfaces.Administracion;
import Interfaces.AlmacenarInventario;
import Interfaces.Gerencia;
import Negocio.Conexion;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import Logica.Extras.log;
import Interfaces.LaboratorioTiqueteInicial;
import Interfaces.Auditoria;
import javax.swing.JFrame;

public class login {

    public static Gerente ger;
    public static Interfaces.Login log;
    public static LaboratorioTiqueteInicial labor;
    public static Conexion Con;
    public static String usu, con, priv, estado, usua;
    public static Statement st,stc;
    public static ResultSet rs, rsusu, rsbas,rsc;
    public static ResultSet rs1;
    public static ResultSet rs2;
    public static Bascula bas;
    public static bascula Bas;
    public static conductor cond;
    public static Administracion admi;
    public static AlmacenarInventario Inventario;
    public static Auditoria Auditoria;
    public static Liquidacion Liqui;
    public static Gerencia Ger;
    public boolean bandera = false;
    public static int contIntentos = 0;
    private static String usuario2 = "",user="";
    public static log logs;
    public static CambioContrasena cambioContrasena;
    public static Auditor audi;
    public static String contraencript = "";
    public static StringEncrypt encrip;
    private static String perfil;

    public login() {
        logs = new log();
        perfil = log.perfil;
        usu = log.txtusuario.getText();
        char[] Pass = log.txtcontra.getPassword();
        con = new String(Pass);
        try {
            contraencript = encrip.encrypt(con);
            //   System.out.println("contra"+hope);
        } catch (Exception e) {
        }
        if (!usu.equals("") && !con.equals("")) {
            Validar(usu, contraencript);
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacío");
        }
    }

    public void limpiar_campos() {
        log.txtcontra.setText("");
        log.txtusuario.setText("");

    }

    boolean Validar(String usuario, String Contraseña) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            String cadenatemporal = "SELECT * FROM usuario WHERE usuario.user = '" + usuario + "' AND usuario.contrasena = '" + Contraseña + "'";
            rs = st.executeQuery("SELECT * FROM usuario WHERE usuario.user = '" + usuario + "' AND usuario.contrasena = '" + Contraseña + "'");
            while (rs.next()) {
                if (rs.getString(1) == null) {
                    return false;
                } else {
                    System.out.println("Usuario encontrado");
                    rs = st.executeQuery("SELECT * FROM usuario,empleado WHERE usuario.user=empleado.user AND usuario.user = '" + usuario + "'");
                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(null, "El usuario no existe o esta inhabilitado");
                        return false;
                    }
                    rs1 = st.executeQuery("SELECT privilegios.nombre,usuario.estado from usuario, privilegios where usuario.user = '" + usu + "' and usuario.idPrivilegios = privilegios.idPrivilegios");
                    if (rs1.next()) {
                        priv = rs1.getObject(1) + "";
                        estado = rs1.getObject(2) + "";
                        System.out.println("Privilegio encontrado " + priv);
                        System.out.print("estado" + estado);
                    }
                  /*  rs2 = st.executeQuery("SELECT usuario.estado FROM usuario WHERE usuario.user='" + usu + "'");
                    if (rs2.next()) {
                        estado = rs2.getObject(1) + "";
                        System.out.print("estado" + estado);
                    } */
                    if (priv.equals("basculista") && estado.equals("activo")) {
                        if (bas != null) {
                            bas.dispose();
                            bas = new Bascula();
                        } else {
                            bas = new Bascula();
                        }
                        bas.setVisible(true);
                        cambiarContrasena(bas);
                            /*
                            Envia al metodo usuario en la clase bascula el nombre del usuario
                            que ingreso con privilegio 2 para realizar un tiquete
                             */
                        enviarUsuario();
                        bandera = true;
                    } else if (priv.equals("administrador") && estado.equals("activo")) {
                        if (admi != null) {
                            admi.dispose();
                            admi = new Administracion();
                        } else {
                            admi = new Administracion();
                        }
                        admi.setVisible(true);
                        cambiarContrasena(admi);
                        enviarUsuario();
                        bandera = true;
                    } else if (priv.equals("laboratorista") && estado.equals("activo")) {
                        if (labor != null) {
                            labor.dispose();
                            labor = new LaboratorioTiqueteInicial();
                        } else {
                            labor = new LaboratorioTiqueteInicial();
                        }
                        labor.setVisible(true);
                        cambiarContrasena(labor);
                        enviarUsuario();
                        bandera = true;
                    } else if (priv.equals("gerente") && estado.equals("activo")) {
                        if (ger != null) {
                            ger.dispose();
                            ger = new Gerente();
                        } else {
                            ger = new Gerente();
                        }
                        ger.setVisible(true);
                        cambiarContrasena(ger);
                        enviarUsuario();
                        bandera = true;
                    } else if (priv.equals("auditor") && estado.equals("activo")) {
                        if (Auditoria != null) {
                            Auditoria.dispose();
                            Auditoria = new Auditoria();
                        } else {
                            Auditoria = new Auditoria();
                        }
                        Auditoria.setVisible(true);
                        cambiarContrasena(Auditoria);
                        enviarUsuario();
                        bandera = true;
                        /*} else if (priv.equals("supervisor") && estado.equals("activo")) {
                        if (supe == null) {
                            supe = new Auditor();
                            supe.setVisible(true);
                        enviarUsuario();
                        } else {
                            audi.setVisible(true);
                        }
                        bandera = true;*/
                    } else if (priv.equals("contador") && estado.equals("activo")) {
                        if (Liqui != null) {
                            Liqui.dispose();
                            Liqui = new Liquidacion();
                        } else {
                            Liqui = new Liquidacion();
                        }
                        Liqui.setVisible(true);
                        cambiarContrasena(Liqui);
                        enviarUsuario();
                        bandera = true;
                    } else if (priv.equals("molinero") && estado.equals("activo")) {
                        if (Inventario != null) {
                            Inventario.dispose();
                            Inventario = new AlmacenarInventario();
                        } else {
                            Inventario = new AlmacenarInventario();
                        }
                        Inventario.setVisible(true);
                        cambiarContrasena(Inventario);
                        enviarUsuario();
                        bandera = true;
                    } else {
                        if (!estado.equals("activo")) {
                            JOptionPane.showMessageDialog(null, "El usuario se encuentra inactivo por favor hable con el administrador del sistema");
                        }
                        limpiar_campos();
                    }
                    usu = "";
                    con = "";
                    Con.Desconectar();
                    return true;
                }
                //Con.Desconectar();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        /* if (usuario2.equals("")) {
            usuario2 = usuario;
        }
        if (contIntentos < 3 && usuario2.equals(usuario)) {
            contIntentos += 1;

        } else {
            usuario2 = usuario;
            contIntentos = 1;
        }
        if (contIntentos == 2) {
            JOptionPane.showMessageDialog(null, "Un intento disponible, al proximo su usuario se bloqueara");
        }
        if (contIntentos == 3) {
            bloquearUsuario(usuario);
            contIntentos = 0;

            log.txtusuario.setText("");
            log.txtcontra.setText("");

            limpiar_campos();

        }
        log.lblIntentos.setText(Integer.toString(contIntentos));
        System.out.println(contIntentos + " " + usuario2 + " " + usuario);*/

        JOptionPane.showMessageDialog(null, "Usuario o contraseña erronea");
        return false;

    }

    public static String enviarUsuario() {
        try {
            String usuario = log.txtusuario.getText();
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rsusu = st.executeQuery("SELECT user FROM usuario WHERE user = '" + usuario + "'");

            while (rsusu.next()) {
                usua = rsusu.getString(1);
                //Bas.usuario(usua);
                //System.out.println("usuuuu "+usua);
                //getPrivilegio(usua);
            }
            Con.Desconectar();
            return usua;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getEmpleado(String usuario) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rsbas = st.executeQuery("SELECT CONCAT(nombres,' ',apellidos) from empleado,usuario where empleado.user = '" + usuario + "' and usuario.user = empleado.user");
            while (rsbas.next()) {
                user = rsbas.getObject(1) + "";
            }
            Con.Desconectar();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getPrivilegio(String usuario) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rsbas = st.executeQuery("SELECT privilegios.nombre from usuario, privilegios where usuario.user = '" + usuario + "' and usuario.idPrivilegios = privilegios.idPrivilegios");
            while (rsbas.next()) {
                priv = rsbas.getObject(1) + "";
            }
            Con.Desconectar();
            return priv;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void bloquearUsuario(String usuario) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rsusu = st.executeQuery("SELECT user FROM usuario WHERE user = '" + usuario + "'");
            while (rsusu.next()) {
                if (rsusu.getString(1) != null) {
                    logs.logBloqueado(usuario);
                    st.executeUpdate("UPDATE usuario SET estado='inactivo'WHERE user='" + usuario + "'");
                    JOptionPane.showMessageDialog(null, "Usuario bloqueado");
                }
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void cambiarContrasena(JFrame jFrame) {
        try {
            Con = new Conexion();
            stc = Con.conexion.createStatement();
            rsc = stc.executeQuery("SELECT cambioContrasena FROM usuario WHERE user = '" + enviarUsuario() + "'");
            while (rsc.next()) {
                if (rsc.getString(1).equals("true")) {
                    Con.Desconectar();
                    if (cambioContrasena != null) {
                        cambioContrasena.dispose();
                        cambioContrasena = new CambioContrasena(jFrame,true);
                    } 
                    else {
                        cambioContrasena = new CambioContrasena(jFrame,true);//PREGUNTAR A LUCHO SI SE SOBRECARGA LA MEMORIA
                    }
                    cambioContrasena.setVisible(true);
                }
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
