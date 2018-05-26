/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Gerencia;

import Interfaces.Parametros;
import Negocio.Conexion;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Lizeth
 */
public class parametros {

    public static Parametros Parametro;
    public static Conexion Con;
    public static ResultSet rs;
    public static Statement st;

    public parametros() {
        obtenerParametros();
    }

    public void obtenerParametros() {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("SELECT idParametros,valor FROM parametros");

            while (rs.next()) {
                String idParametros = rs.getString(1);
                switch (idParametros) {
                    case "1":
                        Parametro.txtReteFuente.setText(rs.getString(2));
                        break;
                    case "2":
                        Parametro.txtComisionBolsa.setText(rs.getString(2));
                        break;
                    case "3":
                        Parametro.txtFomentoArrocero.setText(rs.getString(2));
                        break;
                    case "4":
                        Parametro.txtHumedad.setText(rs.getString(2));
                        break;
                    case "5":
                        Parametro.txtImpureza.setText(rs.getString(2));
                        break;
                }
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizarParametros() {
        String reteFuente, comisionBolsa, fomentoArrocero, humedad, impureza;

        reteFuente = Parametro.txtReteFuente.getText();
        comisionBolsa = Parametro.txtComisionBolsa.getText();
        fomentoArrocero = Parametro.txtFomentoArrocero.getText();
        humedad = Parametro.txtHumedad.getText();
        impureza = Parametro.txtImpureza.getText();

        if (!reteFuente.equals("") && !comisionBolsa.equals("") && !fomentoArrocero.equals("") && !humedad.equals("") && !impureza.equals("")) {
            try {
                Con = new Conexion();
                st = Con.conexion.createStatement();
                st.executeUpdate("UPDATE parametros SET valor='" + reteFuente + "' WHERE idParametros='1'");
                st.executeUpdate("UPDATE parametros SET valor='" + comisionBolsa + "' WHERE idParametros='2'");
                st.executeUpdate("UPDATE parametros SET valor='" + fomentoArrocero + "' WHERE idParametros='3'");
                st.executeUpdate("UPDATE parametros SET valor='" + humedad + "' WHERE idParametros='4'");
                st.executeUpdate("UPDATE parametros SET valor='" + impureza + "' WHERE idParametros='5'");
                
                JOptionPane.showMessageDialog(null, "Estandares modificado");
                Con.Desconectar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ningun campo debe estar vacio");
        }
    }

    public static void limpiarCampos() {
        Parametro.txtReteFuente.setText("");
        Parametro.txtImpureza.setText("");
        Parametro.txtHumedad.setText("");
        Parametro.txtFomentoArrocero.setText("");
        Parametro.txtComisionBolsa.setText("");
    }
}
