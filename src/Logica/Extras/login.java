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
import Interfaces.Bascula;
import Logica.Bascula.bascula;

import Logica.Bascula.conductor;
import Logica.Gerencia.gerencia;

import Logica.Auditor.auditor;
import Interfaces.Auditor;

import Interfaces.Administracion;
import Interfaces.Gerencia;
import Negocio.Conexion;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import Logica.Extras.log;

public class login {

    public static Interfaces.Login log;
    public static Conexion Con;
    public static String usu, con, priv, estado, usua;
    public static Statement st;
    public static ResultSet rs, rsusu, rsbas;
    public static ResultSet rs1;
    public static ResultSet rs2;
    public static Bascula bas;
    public static bascula Bas;
    public static conductor cond;
    public static Administracion admi;

    public static Gerencia Ger;
    public boolean bandera = false;
    public static int contIntentos = 0;
    private static String usuario2 = "";
    public static log logs;

    public static Auditor audi;
    public static String contraencript = "";
    public static encriptar encrip;
    private static String perfil;

    public login() {
        logs = new log();
        perfil=log.perfil;
        usu = log.txtusuario.getText();
        char[] Pass = log.txtcontra.getPassword();
        con = new String(Pass);
        try {
            contraencript = encrip.main(con);
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
            rs = st.executeQuery("SELECT * FROM usuario WHERE user = '" + usuario + "' AND contrasena = '" + Contraseña + "'");
            while (rs.next()) {
                if (rs.getString(1) == null) {
                    return false;
                } else {
                    System.out.println("Usuario encontrado");
                    rs1 = st.executeQuery("SELECT privilegios.nombre from usuario, privilegios where usuario.user = '" + usu + "' and usuario.idPrivilegios = privilegios.idPrivilegios");
                    while (rs1.next()) {
                        priv = rs1.getObject(1) + "";
                        System.out.println("Privilegio encontrado " + priv);

                    }
                    rs2 = st.executeQuery("SELECT usuario.estado FROM usuario WHERE usuario.user='" + usu + "'");
                    while (rs2.next()) {
                        estado = rs2.getObject(1) + "";
                        System.out.print("estado" + estado);
                    }
                    if (priv.equals("bascula") && estado.equals("activo") && priv.equals(perfil)) {
                        if (bas == null) {
                            bas = new Bascula();
                            bas.setVisible(true);
                            /*
                            Envia al metodo usuario en la clase bascula el nombre del usuario
                            que ingreso con privilegio 2 para realizar un tiquete
                             */
                            enviarUsuario();
                        } else {
                            bas.setVisible(true);
                        }
                        bandera = true;
                    } else if (priv.equals("administracion") && estado.equals("activo") && priv.equals(perfil)) {
                        if (admi == null) {
                            admi = new Administracion();
                            admi.setVisible(true);
                        } else {
                            admi.setVisible(true);
                        }
                        bandera = true;
                    } else if (priv.equals("gerencia") && estado.equals("activo") && priv.equals(perfil)) {
                        if (Ger == null) {
                            Ger = new Gerencia();
                            Ger.setVisible(true);
                        } else {
                            Ger.setVisible(true);
                        }
                        bandera = true; 
                    }else if (priv.equals("auditor") && estado.equals("activo") && priv.equals(perfil)) {

                        if (audi == null) {
                            audi = new Auditor();
                            audi.setVisible(true);
                        } else {
                            audi.setVisible(true);
                        }
                        bandera = true;
                    }else {
                       if (!priv.equals(perfil)) {
                            JOptionPane.showMessageDialog(null, "El usuario no corresponde a este perfil");
                        }else if (!estado.equals("activo")) {
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
        if (usuario2.equals("")) {
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
        System.out.println(contIntentos + " " + usuario2 + " " + usuario);

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
}
