/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Extras;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Interfaces.Empleado;
import javax.swing.JTextField;
import Negocio.Conexion;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author uriel
 */
public class validaciones {
    
    public static Empleado emp;
    public static Conexion Con;
     public static Statement st;
      public static ResultSet rs;
    
    
    public static  String ExpresionRegular_Email = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    
    public void IDENTIFICACION(JTextField text) {
        text.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char a =e.getKeyChar();
                if (!((int)a>=48 && (int)a<=57)){
                    e.consume();
                }
            }
        });
    }
    
    public void NOMBRES(JTextField text) {
        text.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char a =e.getKeyChar();
                if (( ((int)a>=33 && (int)a<=64))||((int)a>=91 && (int)a<=96)|| ((int)a>=123 && (int)a<=223)){
                    e.consume();
                }
            }
        });
    }
    
    public void TELEFONOS(JTextField text) {
        text.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char a =e.getKeyChar();
                if (!((int)a>=48 && (int)a<=57)){
                    e.consume();
                }
            }
        });
    }
    
    public void DIRECCION(JTextField text) {
        text.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char a =e.getKeyChar();
                if (( ((int)a >= 33 && (int)a <= 34))||((int)a >= 36 && (int)a <= 39)|| ((int)a >= 59 && (int)a <= 64) || ((int) a >= 91 && (int) a <= 94) || ((int) a == 96) || ((int) a > 122)){
                    e.consume();
                }
            }
        });
    }
    
    public void EMAILS(JTextField text) {
        text.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char a =e.getKeyChar();
                if ((int)a == 32){
                    e.consume();
                }
            }
        });
    }
    
    
    public boolean ValidarEmail(String Email){
        Pattern pattern = Pattern.compile(ExpresionRegular_Email);
        Matcher matcher = pattern.matcher(Email);
        return matcher.matches();
    }
    
    
    
}

