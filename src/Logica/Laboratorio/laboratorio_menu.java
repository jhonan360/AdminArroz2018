/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Laboratorio;


import Interfaces.BusquedasTiqueteInicial;
import Interfaces.LaboratorioTiquete;
import Interfaces.LaboratorioTiqueteInicial;
import Interfaces.Login;

/**
 *
 * @author uriel
 */
public class laboratorio_menu {
   public static LaboratorioTiquete Labo;
   public static LaboratorioTiqueteInicial LaboTiquete;
   public static BusquedasTiqueteInicial busTiquete;
   public static Login Login;
           
    public static void tiquete2(){
        Labo = new  LaboratorioTiquete();
        Labo.setVisible(true);
        
    }
    public static void tiquete1(){
    LaboTiquete = new LaboratorioTiqueteInicial();
    LaboTiquete.setVisible(true);
    }
    
    public static void busquedaTiq(){
    busTiquete = new BusquedasTiqueteInicial();
    busTiquete.setVisible(true);
    }
    
    public static void salir() {
        Login = new Login();
        Login.setVisible(true);
    }
}
