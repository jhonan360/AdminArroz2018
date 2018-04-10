/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Administracion;

import Interfaces.GestionBD;
import Logica.Extras.currencyFormat;
import Logica.Extras.tablas;
import Negocio.Conexion;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jhonansmith
 */
public class gestionBD {

    public static tablas tbl;
    public static GestionBD GestionBD;
    private static Conexion Con;
    private static Statement st;
    currencyFormat cu;
    String columnasTabla[] = new String[]{"N", "Nombre", "Tamaño", "Fecha de Modificación", "Ruta"};
    String alineaHeader[] = new String[]{"1", "default", "default", "default", "default"};
    String alineaCampo[] = new String[]{"center", "center", "center", "center", "center"};
    String path = System.getProperty("user.dir");
    DefaultTableModel modeloTabla;
    File Dir = null;
    String sqlFiles = "";

    public gestionBD() {
        tbl = new tablas();
        cu = new currencyFormat();
        checkDirectory();
        crearModelo();
    }

    public void crearModelo() {
        modeloTabla = new DefaultTableModel(null, columnasTabla) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        sqlFiles = "";
        File[] listaFicheros = Dir.listFiles();
        for (int x = 0; x < listaFicheros.length; x++) {
            if (listaFicheros[x].getName().indexOf(".sql") >= 0) {
                sqlFiles += listaFicheros[x].getName() + ";";
            }
        }
        if (!sqlFiles.equals("")) {
            String[] listSql = sqlFiles.split(";");
            for (int i = 0; i < listSql.length; i++) {
                String datos[] = new String[columnasTabla.length];
                File a = new File(Dir + "/" + listSql[i]);
                datos[0] = String.valueOf(i + 1);
                datos[1] = a.getName();
                datos[2] = setFotmatWeight(a.length());
                Date d = new Date(a.lastModified());
                datos[3] = cu.DateTime(d);
                datos[4] = a.getAbsolutePath();
                modeloTabla.addRow(datos);
            }
        }
        GestionBD.tblDB.setModel(modeloTabla);
        tbl.alinearHeaderTable(GestionBD.tblDB, alineaHeader);
        tbl.alinearCamposTable(GestionBD.tblDB, alineaCampo);
    }

    public boolean checkDirectory() {
        Dir = new File(path + "/backup");
        if (!Dir.exists()) {
            Dir.mkdirs();
        }
        return true;
    }

    public void backup() {
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de realizar un respaldo de la base de datos?", "Confirmación", JOptionPane.CANCEL_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {

            try {
                Process p = null;
                String os = getOS();
                switch (os) {
                    case "win":
                        if (Con.pass.equals("")) {
                            p = Runtime.getRuntime().exec("mysqldump -u root " + Con.BD + "");
                        } else {
                            p = Runtime.getRuntime().exec("mysqldump -u root -p" + Con.pass + "  " + Con.BD + "");
                        }
                        break;
                    case "mac":
                        if (Con.pass.equals("")) {
                           p = Runtime.getRuntime().exec("/usr/local/mysql/bin/mysqldump -u root " + Con.BD + "");
                        } else {
                           p = Runtime.getRuntime().exec("/usr/local/mysql/bin/mysqldump -u root -p" + Con.pass + "  " + Con.BD + "");
                        }
                        break;
                }
                InputStream is = p.getInputStream();
                String nameFile = "admiArroz_" + getDate();
                FileOutputStream fos = new FileOutputStream(Dir + "/" + nameFile + ".sql");
                byte[] buffer = new byte[1000];
                int leido = is.read(buffer);
                while (leido > 0) {
                    fos.write(buffer, 0, leido);
                    leido = is.read(buffer);
                }
                fos.close();
                JOptionPane.showMessageDialog(null, "Respaldo realizado con exito.");
                crearModelo();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(gestionBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void restore() {
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de restaurar la base de datos?\nRecurde que los datos actuales se perderan", "Confirmación", JOptionPane.CANCEL_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            int rec = GestionBD.tblDB.getSelectedRow();
            if (rec >= 0) {
                String pathFile = GestionBD.tblDB.getValueAt(rec, 4).toString();
                try {
                    try {
                        Con = new Conexion();
                        st = Con.conexion.createStatement();
                        st.executeUpdate("DROP DATABASE " + Con.BD + ";");
                        st.executeUpdate("CREATE DATABASE " + Con.BD + ";");
                        Con.Desconectar();
                    } catch (SQLException ex) {
                        Logger.getLogger(gestionBD.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Process p = null;
                    String os = getOS();
                    switch (os) {
                        case "win":
                            if (Con.pass.equals("")) {
                                p = Runtime.getRuntime().exec("mysql -u root " + Con.BD + "");
                            } else {
                                p = Runtime.getRuntime().exec("mysql -u root -p" + Con.pass + "  " + Con.BD + "");
                            }
                            break;
                        case "mac":
                            if (Con.pass.equals("")) {
                                p = Runtime.getRuntime().exec("/usr/local/mysql/bin/mysql -u root " + Con.BD + "");
                            } else {
                                p = Runtime.getRuntime().exec("/usr/local/mysql/bin/mysql -u root -p" + Con.pass + "  " + Con.BD + "");
                            }
                            break;
                    }
                    OutputStream gos = p.getOutputStream();
                    FileInputStream fis = new FileInputStream(pathFile);
                    byte[] buffer = new byte[1000];
                    int leido = fis.read(buffer);
                    while (leido > 0) {
                        gos.write(buffer, 0, leido);
                        leido = fis.read(buffer);
                    }
                    gos.flush();
                    gos.close();
                    fis.close();
                    JOptionPane.showMessageDialog(null, "Restauración realizada con exito.");
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(gestionBD.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecciones un respaldo en la tabla.");
            }
        }
    }

    public String getOS() {
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.indexOf("win") >= 0) {
            return "win";
        } else if (OS.indexOf("mac") >= 0) {
            return "mac";
        } else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0) {
            return "unix";
        } else if (OS.indexOf("sunos") >= 0) {
            return "solaris";
        }
        return "";
    }

    public String getDate() {
        java.util.Date date = new Date();
        String dates = cu.DateTime(date);
        dates = dates.replace(" ", "_");
        dates = dates.replace(":", "-");
        return dates;
    }

    public String setFotmatWeight(long file) {
        if (file > Math.pow(1024,3)) {
            return file / (Math.pow(1024,3)) + " GB";
        }else 
        if (file > Math.pow(1024,2)) {
            return file / (Math.pow(1024,2)) + " MB";
        } else if (file > 1024) {
            return file / (1024) + " KB";
        } else {
            return file + " B";
        }
    }
}
