/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Laboratorio;


import Interfaces.Laboratorio;
import Interfaces.Laboratorio_tiquete_inicial;

/**
 *
 * @author uriel
 */
public class laboratorio_menu {
   public static Laboratorio Labo;
   public static Laboratorio_tiquete_inicial LaboTiquete;
    public static void tiquete2(){
        Labo = new  Laboratorio();
        Labo.setVisible(true);
        
    }
    public static void tiquete1(){
    LaboTiquete = new Laboratorio_tiquete_inicial();
    LaboTiquete.setVisible(true);
    }
}
