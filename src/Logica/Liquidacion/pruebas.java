/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Liquidacion;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import Negocio.ConexionBascula;
import java.util.Scanner;
import Logica.Extras.currencyFormat;
/**
 *
 * @author jhonansmith
 */
public class pruebas {

    public static void main(String[] args) {
       currencyFormat cu = new currencyFormat();
       String a=cu.moneyFormat(1979);
        System.out.println(a);
        System.out.println(cu.notMoneyFormat(a));
        /* ConexionBascula b = new ConexionBascula();
        
       
        boolean a=true;
        while (a=true) {            
             Scanner sc = new Scanner(System.in);
             int i = sc.nextInt();

             if (i==1) {
               // b.getPeso();
                String g=b.getPeso("0");
                System.out.println(g);
            }else{
                 a=false;
             }
            
        }
     */   
    }
}
