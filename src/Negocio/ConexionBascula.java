/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Arduino.Arduino;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jhonansmith
 */
public class ConexionBascula {

    Arduino Arduino = new Arduino();
    String peso;
    SerialPortEventListener evento = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {
            if (Arduino.MessageAvailable()) {
                peso = Arduino.PrintMessage();

                //System.out.println(peso);
                // jTextFieldcampo.setText(peso);
            }

        }

    };

    public ConexionBascula() {
        try {
            Arduino.ArduinoRXTX("/dev/cu.usbmodem1411", 2000, 9600, evento);

        } catch (Exception ex) {
            Logger.getLogger(ConexionBascula.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getPeso(String pesoInicial) {
        peso = "0.00";
        try {
            Arduino.SendData("1");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se puede conectar con la bascula");
            Logger.getLogger(ConexionBascula.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true) {
            if (Arduino.MessageAvailable()) {
                peso = Arduino.PrintMessage();
                if (!peso.equals("0.00")) {
                    if (pesoInicial.equals("0")) {
                        return peso;
                    } else if (Double.parseDouble(pesoInicial) <= Double.parseDouble(peso)) {
                        JOptionPane.showMessageDialog(null, "Los kilos brutos deben pesar mas que los kilos de destare.");
                        return "";
                    } else {
                        return peso;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se puede capturar  peso en 0 Kg.");
                    return "";
                }
            }
        }
    }

}
//JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
