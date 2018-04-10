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
import Logica.Extras.extras;
import Logica.Extras.notify;
import Logica.Administracion.gestionBD;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import Logica.Extras.currencyFormat;

/**
 *
 * @author jhonansmith
 */
public class pruebas {

    public static void main(String[] args) {
        currencyFormat cu = new currencyFormat();
        String idTiquete="1";
        String txtAgricultor ="Jhonan";
        
        System.out.println("INSERT INTO notificaciones (idNotificacion, privilegio, usuario, titulo, texto, tipo, fechaCreacion, fechaVisualizacion, origen) VALUES (0,'basculista',NULL,'Nuevo Tiquete De Entrada De Materia Prima','Tiquete Nº "+idTiquete+" del agricultor "+txtAgricultor+"','tip','"+cu.getDateTimeNow()+"',NULL,'laboratorio')");
        
        notify no =new notify("basculista","b");
        /*currencyFormat cu = new currencyFormat();
        java.util.Date date = new Date();
        System.out.println(date.toString());
        String dates=date.toString();
        cu.DateTime(date);
        System.out.println(cu.DateTime(date));
        //extras ext = new extras();
        // gestionBD ges = new gestionBD();
        // ges.restore();
        /*System.out.println(System.getProperty("user.dir"));
        String sDirectorio = "/Users/jhonansmith/Documents";
        File f = new File(sDirectorio);
        String sqlFiles = "";
        if (f.exists()) {
            System.out.println("Existe");
            File[] ficheros = f.listFiles();
            for (int x = 0; x < ficheros.length; x++) {
                if (ficheros[x].getName().indexOf(".sql") >= 0) {
                    sqlFiles += ficheros[x].getName() + ";";
                }
                //System.out.println(sqlFiles);
            }
            String[] listSql = sqlFiles.split(";");
            for (int i = 0; i < listSql.length; i++) {
                File a = new File(sDirectorio + "/" + listSql[i]);
                Date d = new Date(a.lastModified());
                System.out.print(a.getName() + ", " + a.length() / 1024 + "MB, " + d + ", " + a.getAbsolutePath());
                System.out.println();
            }
        } else { //Directorio no existe 
            System.out.println("No Existe");
        }
        //System.out.println("id= "+ext.getNextIndex("tipodearroz"));
        /* currencyFormat cu = new currencyFormat();
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
