/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Auditor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;
import Interfaces.Auditoria;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import Logica.Extras.currencyFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author uriel
 */
public class auditoria {

    public static Auditoria Auditoria;
    public static currencyFormat cu;
    static final Logger LOGGER = Logger.getAnonymousLogger();
    String path = System.getProperty("user.dir");
    JFileChooser seleccionado1 = new JFileChooser();//Declaracion del objeto filechooser el cual abre la ventana encargada de recivir el archivo
    File Archivo1;//elemento tipo file almacena el arcivo
    File fichero = new File(path + "/logs/logs.log");
    File fichero2 = new File(path + "/descarga_logs/logs.log");
    String ficheroini = path + "/logs/logs.log";
    String ficherofin = path + "/descarga_logs/logs.log";
    File Dir = null;
    long Tamaño_Archivo1 = 0;//almacena el tamaño del archivo el kilobytes
    String NombreArchivo1 = "";//Almacena el nombre del archivo 1
    String Extension_Archivo1 = "";//almacena la extencion del archivo 1
    String evento = "";
    String usuario = "";
    FileReader filereader1 = null;//se encarga de leer el archivo 1
    BufferedReader bufferedreader1 = null;//se encarga de procesar el archivo 1
    String Lineas_Archivo1 = "";//lee cada una de las lineas del archivo 1 al momento de ser procesado
    int Cantidad_Lineas1 = 0;//almacena la cantidad del registros del archivo 1 
    String[][] matriz1 = new String[Cantidad_Lineas1][5];//matriz encargada de qalmacenar cada uno de los campos existentes en el archivo 1     
    public DefaultTableModel Modelo1;//declara el midelo de la tabla 1 para el archivo 1
    String[][] matriz2 = new String[2][5];
    String[][] matriz3 = new String[2][5];
    String[][] matriz4 = new String[2][5];
    public String columnas[] = new String[]{"Tipo", "Evento", "Cambios", "Usuario", "Fecha"};//declara el nombre de cada una de las columnas para las dos tablas

    public auditoria() {
        cu = new currencyFormat();
        LeerArchivos();

    }

    public void DatosArchivoNumero1() {//metodo encargado de obtener los datos del archivo 1
        if (seleccionado1.showDialog(null, "Abrir archivo") == JFileChooser.APPROVE_OPTION) {//si se selecciona un archivo valido obtiene sus datos

            Archivo1 = seleccionado1.getSelectedFile();//archivo toma el path completo de donde se encuentra ubicado el archivo 1
            if (Archivo1.canRead()) {//si el archivo es de un tipo que se pueda leer lo reviza
                ObtenerExtensionArchivo1();//llamado del metodo encargado de entregar la extencion del archivo escojido
            } else {
                JOptionPane.showMessageDialog(null, "El archivo no puede ser leido");//si no puede ser leido este mensaje se mmuestra en una nueva ventana
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se encontro ningun archivo");//si se da cancelar en el filechooser sale este mensaje en una ventana de alerta
        }
    }

    public String ObtenerExtensionArchivo1() {//metodo encargado de obtener la extencion del archivo almacenado
        int index = NombreArchivo1.lastIndexOf('.');//busca el ultimo punto que se encuentra dentro del nombre del archivo
        if (index == -1) {//si no hay ningun punto significa que no se escogio un archivo
            Extension_Archivo1 = null;//devuelve null
        } else {//si encuentra un punto en el nombre del archivo
            Extension_Archivo1 = NombreArchivo1.substring(index + 1);//devuelve todo lo que se encuentre despues del punto
        }
        return Extension_Archivo1;//devuelve el nombre de la extencion
    }

    public void LeerArchivos() {//metodo encargado de leer los dos archivos seleccionados

        try {
            filereader1 = new FileReader(fichero);
            bufferedreader1 = new BufferedReader(filereader1);
            while (Lineas_Archivo1 != null) {
                Lineas_Archivo1 = bufferedreader1.readLine();
                if (Lineas_Archivo1 != null) {

                    Cantidad_Lineas1++;
                }
            }
            bufferedreader1.close();
            Lineas_Archivo1 = "";
            SepararEnMatrices();
            CargarTablas();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void SepararEnMatrices() {
        try {
            filereader1 = new FileReader(fichero);
            bufferedreader1 = new BufferedReader(filereader1);
            matriz1 = new String[Cantidad_Lineas1][5];
            int cont = 0;
            while (Lineas_Archivo1 != null) {
                Lineas_Archivo1 = bufferedreader1.readLine();
                if (Lineas_Archivo1 != null) {
                    String[] linea_archivo_1 = Lineas_Archivo1.split(";");
                    for (int i = 0; i < 5; i++) {
                        matriz1[cont][i] = linea_archivo_1[i];
                    }
                    cont++;
                }

            }
            bufferedreader1.close();
            for (int x = 0; x <= (matriz1.length - 1); x++) {
                for (int y = 0; y < matriz1[x].length; y++) {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CargarTablas() {
        Modelo1 = new DefaultTableModel(null, columnas);
        Auditoria.TablaArchivo1.setModel(Modelo1);
        Modelo1.setRowCount(Cantidad_Lineas1);
        Modelo1.setColumnCount(5);
        for (int i = 0; i < Cantidad_Lineas1; i++) {
            for (int j = 0; j < 5; j++) {
                Auditoria.TablaArchivo1.setValueAt(matriz1[i][j], i, j);
            }
        }
        //buscar();
    }

    public void CargarTablas2(String[][] matriz, int lineas) {
        Modelo1 = new DefaultTableModel(null, columnas);
        Auditoria.TablaArchivo1.setModel(Modelo1);
        Modelo1.setRowCount(lineas);
        Modelo1.setColumnCount(5);
        for (int i = 0; i < lineas; i++) {
            for (int j = 0; j < 5; j++) {
                Auditoria.TablaArchivo1.setValueAt(matriz[i][j], i, j);
            }
        }
    }

    public void BuscarRegistros() throws ParseException {
        evento = Auditoria.CmbEvento.getSelectedItem().toString();
        usuario = Auditoria.TxtUsuario.getText();

       
        if (Auditoria.ChEvento.isSelected() == true && Auditoria.ChUsuario.isSelected() == true && Auditoria.chFecha.isSelected() == true ) {
            if (!evento.equals("") && !usuario.equals("")) {
                evento = Auditoria.CmbEvento.getSelectedItem().toString();
                usuario = Auditoria.TxtUsuario.getText();
                buscarTodo(evento, usuario);
            } else {
                JOptionPane.showMessageDialog(null, "El campo evento debe estar seleccionado");
            }

        } else if (Auditoria.ChEvento.isSelected() == true && Auditoria.ChUsuario.isSelected() == true) {
            if (!evento.equals("") && !usuario.equals("")) {
                buscarUsuarioEvento(evento, usuario);
                System.out.println("hola que haceis");
            } else {
                JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
            }

        }else if (Auditoria.ChEvento.isSelected() == true && Auditoria.chFecha.isSelected() == true) {
            if (!evento.equals("")) {
                evento = Auditoria.CmbEvento.getSelectedItem().toString();
                buscarEventoFecha(evento);
            } else {
                JOptionPane.showMessageDialog(null, "El campo evento debe estar seleccionado");
            }

        } else if (Auditoria.ChUsuario.isSelected() == true && Auditoria.chFecha.isSelected() == true) {
            if (!usuario.equals("")) {
               usuario = Auditoria.TxtUsuario.getText();
                buscarEventoFecha(usuario);
            } else {
                JOptionPane.showMessageDialog(null, "El campo evento debe estar seleccionado");
            }

        }else if (Auditoria.ChEvento.isSelected() == true) {
            if (!evento.equals("")) {
                evento = Auditoria.CmbEvento.getSelectedItem().toString();
                buscarRegistrosUsuarioEvento(evento);
            } else {
                JOptionPane.showMessageDialog(null, "El campo evento deb estar seleccionado");
            }

        } else if (Auditoria.ChUsuario.isSelected() == true) {
            if (!usuario.equals("")) {
                usuario = Auditoria.TxtUsuario.getText();
                buscarRegistrosUsuarioEvento(usuario);
            } else {
                JOptionPane.showMessageDialog(null, "El campo usuario debe contener información");
            }

        } else if (Auditoria.chFecha.isSelected() == true) {

            buscarFecha();

        }
    }

    public void buscarUsuarioEvento(String evento, String usuario) {
        int cont = 0;
        int cont2 = 0;
        int contcantidad = 0;
        int contDiferencias = 0;
        int posDiferencias = 0;
        for (int f = 0; f < matriz1.length; f++) {
            for (int g = 0; g < matriz1[f].length; g++) {
                if (matriz1[f][g].equals(evento)) {

                    contcantidad = contcantidad + 1;
                }
            }

        }
        matriz2 = new String[contcantidad][5];
        for (int i = 0; i < matriz1.length; i++) {
            for (int j = 0; j < matriz1[i].length; j++) {
                if (matriz1[i][j].equals(evento)) {

                    matriz2[cont][0] = matriz1[i][0];
                    matriz2[cont][1] = matriz1[i][1];
                    matriz2[cont][2] = matriz1[i][2];
                    matriz2[cont][3] = matriz1[i][3];
                    matriz2[cont][4] = matriz1[i][4];

                    cont = cont + 1;

                } else {

                    System.out.println("Los archivos son diferentes en la columna " + i + " y en la columna " + j);
                }
            }

        }

        buscarEventoUsuario(usuario);
    }
     public void buscarEventoFecha(String evento) throws ParseException {
        int cont = 0;
        int cont2 = 0;
        int contcantidad = 0;
        int contDiferencias = 0;
        int posDiferencias = 0;
        for (int f = 0; f < matriz1.length; f++) {
            for (int g = 0; g < matriz1[f].length; g++) {
                if (matriz1[f][g].equals(evento)) {

                    contcantidad = contcantidad + 1;
                }
            }

        }
        matriz2 = new String[contcantidad][5];
        for (int i = 0; i < matriz1.length; i++) {
            for (int j = 0; j < matriz1[i].length; j++) {
                if (matriz1[i][j].equals(evento)) {

                    matriz2[cont][0] = matriz1[i][0];
                    matriz2[cont][1] = matriz1[i][1];
                    matriz2[cont][2] = matriz1[i][2];
                    matriz2[cont][3] = matriz1[i][3];
                    matriz2[cont][4] = matriz1[i][4];

                    cont = cont + 1;

                } else {

                    System.out.println("Los archivos son diferentes en la columna " + i + " y en la columna " + j);
                }
            }

        }

        buscarFecha2();
    }

    public void buscarEventoUsuario(String usuario) {
        int cont2 = 0;
        int contadorcantidad = 0;
        for (int a = 0; a < matriz2.length; a++) {
            for (int b = 0; b < matriz2[a].length; b++) {

                if (matriz2[a][b].equals(usuario)) {

                    contadorcantidad = contadorcantidad + 1;

                }
            }
        }
        matriz3 = new String[contadorcantidad][5];
        for (int c = 0; c < matriz2.length; c++) {
            for (int d = 0; d < matriz2[c].length; d++) {

                if (matriz2[c][d].equals(usuario)) {

                    matriz3[cont2][0] = matriz2[c][0];
                    matriz3[cont2][1] = matriz2[c][1];
                    matriz3[cont2][2] = matriz2[c][2];
                    matriz3[cont2][3] = matriz2[c][3];
                    matriz3[cont2][4] = matriz2[c][4];

                    cont2 = cont2 + 1;

                } else {

                    System.out.println("Los archivos son diferentes en la columna " + c + " y en la columna " + d);
                }
            }

        }
        CargarTablas2(matriz3, contadorcantidad);
        matriz3 = new String[0][5];
        limpiar();

    }

    public void buscarRegistrosUsuarioEvento(String busqueda) {
        int cont = 0;
        int cont2 = 0;
        int contcantidad = 0;
        int contDiferencias = 0;
        int posDiferencias = 0;
        for (int f = 0; f < matriz1.length; f++) {
            for (int g = 0; g < matriz1[f].length; g++) {
                if (matriz1[f][g].equals(busqueda)) {

                    contcantidad = contcantidad + 1;
                }
            }

        }

        matriz2 = new String[contcantidad][5];
        for (int i = 0; i < matriz1.length; i++) {
            for (int j = 0; j < matriz1[i].length; j++) {
                if (matriz1[i][j].equals(busqueda)) {

                    matriz2[cont][0] = matriz1[i][0];
                    matriz2[cont][1] = matriz1[i][1];
                    matriz2[cont][2] = matriz1[i][2];
                    matriz2[cont][3] = matriz1[i][3];
                    matriz2[cont][4] = matriz1[i][4];

                    cont = cont + 1;

                } else {

                    System.out.println("Los archivos son diferentes en la columna " + i + " y en la columna " + j);
                }
            }

        }

        CargarTablas2(matriz2, contcantidad);
        matriz2 = new String[0][5];
        limpiar();

    }

    public void buscarFecha() throws ParseException {
        int cont = 0;
        int cont2 = 0;

        Date fecha, fecha2;
        fecha = Auditoria.dcFechaInicial.getDate();
        fecha2 = Auditoria.dcFechaFinal.getDate();
        String fechaInicial = cu.dateNotTime(fecha);
        String fechaFinal = cu.dateNotTime(fecha2);
        int contcantidad1 = 0;
        int contDiferencias = 0;
        int posDiferencias = 0;
        for (int f = 0; f < matriz1.length; f++) {
            for (int g = 0; g < matriz1[f].length; g++) {
                System.out.println("tamaño matriz:" + matriz1[f].length);
                if (g == 4) {
                    System.out.println("hola que hace");
                    SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd");
                    System.out.println("matriz2[f][g]" + matriz1[f][g]);
                    Date date1 = formato.parse(matriz1[f][g]);
                    Date date2 = formato.parse(fechaInicial);
                    Date date3 = formato.parse(fechaFinal);

                    System.out.println("Date1" + date1);
                    System.out.println("resultado1"+date1.after(date2));
                     System.out.println("resultado2"+date1.before(date3));
                    System.out.println("Dateini" + date2);

                    System.out.println("Datefin" + date3);
                    if (date1.after(date2) && date1.before(date3)) {
                        contcantidad1 = contcantidad1 + 1;
                    }
                }
                // SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd");
                // Date date1=formato.parse(fecha1);
                //Date date2=formato.parse(fecha2);
                //System.out.println("resultado="+date1.before(date2));

            }

        }
        System.out.println("contador"+contcantidad1);
        matriz3 = new String[contcantidad1][5];
        for (int i = 0; i < matriz1.length; i++) {
            for (int j = 0; j < matriz1[i].length; j++) {
               if (j == 4) {
                    System.out.println("hola que hace2");
                    SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd");
                    Date date1 = formato.parse(matriz1[i][j]);
                    Date date2 = formato.parse(fechaInicial);
                    Date date3 = formato.parse(fechaFinal);

                    System.out.println("Date1" + date1);
                    System.out.println("resultado1"+date1.after(date2));
                     System.out.println("resultado2"+date1.before(date3));
                    System.out.println("Dateini" + date2);

                    System.out.println("Datefin" + date3);
                    if (date1.after(date2) && date1.before(date3)) {
                        
                         matriz3[cont][0] = matriz1[i][0];
                    matriz3[cont][1] = matriz1[i][1];
                    matriz3[cont][2] = matriz1[i][2];
                    matriz3[cont][3] = matriz1[i][3];
                    matriz3[cont][4] = matriz1[i][4];
                    cont = cont + 1;
                    }
                } else {

                    System.out.println("Los archivos son diferentes en la columna " + i + " y en la columna " + j);
                }
            }

        }

        CargarTablas2(matriz3, contcantidad1);
        matriz3 = new String[0][5];
        limpiar();

    }
public void buscarFecha2() throws ParseException {
        int cont = 0;
        int cont2 = 0;

        Date fecha, fecha2;
        fecha = Auditoria.dcFechaInicial.getDate();
        fecha2 = Auditoria.dcFechaFinal.getDate();
        String fechaInicial = cu.dateNotTime(fecha);
        String fechaFinal = cu.dateNotTime(fecha2);
        int contcantidad1 = 0;
        int contDiferencias = 0;
        int posDiferencias = 0;
        for (int f = 0; f < matriz2.length; f++) {
            for (int g = 0; g < matriz2[f].length; g++) {
                System.out.println("tamaño matriz:" + matriz2[f].length);
                if (g == 4) {
                    System.out.println("hola que hace");
                    SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd");
                    System.out.println("matriz2[f][g]" + matriz2[f][g]);
                    Date date1 = formato.parse(matriz2[f][g]);
                    Date date2 = formato.parse(fechaInicial);
                    Date date3 = formato.parse(fechaFinal);

                    System.out.println("Date1" + date1);
                    System.out.println("resultado1"+date1.after(date2));
                     System.out.println("resultado2"+date1.before(date3));
                    System.out.println("Dateini" + date2);

                    System.out.println("Datefin" + date3);
                    if (date1.after(date2) && date1.before(date3)) {
                        contcantidad1 = contcantidad1 + 1;
                    }
                }
                // SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd");
                // Date date1=formato.parse(fecha1);
                //Date date2=formato.parse(fecha2);
                //System.out.println("resultado="+date1.before(date2));

            }

        }
        System.out.println("contador"+contcantidad1);
        matriz3 = new String[contcantidad1][5];
        for (int i = 0; i < matriz2.length; i++) {
            for (int j = 0; j < matriz2[i].length; j++) {
               if (j == 4) {
                    System.out.println("hola que hace2");
                    SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd");
                    Date date1 = formato.parse(matriz2[i][j]);
                    Date date2 = formato.parse(fechaInicial);
                    Date date3 = formato.parse(fechaFinal);

                    System.out.println("Date1" + date1);
                    System.out.println("resultado1"+date1.after(date2));
                     System.out.println("resultado2"+date1.before(date3));
                    System.out.println("Dateini" + date2);

                    System.out.println("Datefin" + date3);
                    if (date1.after(date2) && date1.before(date3)) {
                        
                         matriz3[cont][0] = matriz2[i][0];
                    matriz3[cont][1] = matriz2[i][1];
                    matriz3[cont][2] = matriz2[i][2];
                    matriz3[cont][3] = matriz2[i][3];
                    matriz3[cont][4] = matriz2[i][4];
                    cont = cont + 1;
                    }
                } else {

                    System.out.println("Los archivos son diferentes en la columna " + i + " y en la columna " + j);
                }
            }

        }

        CargarTablas2(matriz3, contcantidad1);
        matriz3 = new String[0][5];
        limpiar();

    }
    public void limpiar() {
        Auditoria.TxtUsuario.setText("");
        Auditoria.CmbEvento.setSelectedIndex(0);

    }

    public void CopiarArchivo() {
        try {
            Dir = new File(path + "/descarga_logs");
            if (!Dir.exists()) {
                Dir.mkdirs();
            }
            Path origenPath = Paths.get(ficheroini);
            Path destinoPath = Paths.get(ficherofin);
            //sobreescribir el fichero de destino si existe y lo copia
            Files.copy(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
            JOptionPane.showMessageDialog(null, "El archivo fue descargado en la ruta= " + ficherofin);
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }
     public void buscarTodo(String evento, String usuario) throws ParseException {
        int cont = 0;
        int cont2 = 0;
        int contcantidad = 0;
        int contDiferencias = 0;
        int posDiferencias = 0;
        for (int f = 0; f < matriz1.length; f++) {
            for (int g = 0; g < matriz1[f].length; g++) {
                if (matriz1[f][g].equals(evento)) {

                    contcantidad = contcantidad + 1;
                }
            }

        }
        matriz2 = new String[contcantidad][5];
        for (int i = 0; i < matriz1.length; i++) {
            for (int j = 0; j < matriz1[i].length; j++) {
                if (matriz1[i][j].equals(evento)) {

                    matriz2[cont][0] = matriz1[i][0];
                    matriz2[cont][1] = matriz1[i][1];
                    matriz2[cont][2] = matriz1[i][2];
                    matriz2[cont][3] = matriz1[i][3];
                    matriz2[cont][4] = matriz1[i][4];

                    cont = cont + 1;

                } else {

                    System.out.println("Los archivos son diferentes en la columna " + i + " y en la columna " + j);
                }
            }

        }

        buscarTodo2(usuario);
    }
     public void buscarTodo2(String usuario) throws ParseException {
        int cont2 = 0;
        int contadorcantidad = 0;
        for (int a = 0; a < matriz2.length; a++) {
            for (int b = 0; b < matriz2[a].length; b++) {

                if (matriz2[a][b].equals(usuario)) {

                    contadorcantidad = contadorcantidad + 1;

                }
            }
        }
        matriz3 = new String[contadorcantidad][5];
        for (int c = 0; c < matriz2.length; c++) {
            for (int d = 0; d < matriz2[c].length; d++) {

                if (matriz2[c][d].equals(usuario)) {

                    matriz3[cont2][0] = matriz2[c][0];
                    matriz3[cont2][1] = matriz2[c][1];
                    matriz3[cont2][2] = matriz2[c][2];
                    matriz3[cont2][3] = matriz2[c][3];
                    matriz3[cont2][4] = matriz2[c][4];

                    cont2 = cont2 + 1;

                } else {

                    System.out.println("Los archivos son diferentes en la columna " + c + " y en la columna " + d);
                }
            }

        }
        buscarTodo3();

    }
     public void buscarTodo3() throws ParseException {
        int cont = 0;
        int cont2 = 0;

        Date fecha, fecha2;
        fecha = Auditoria.dcFechaInicial.getDate();
        fecha2 = Auditoria.dcFechaFinal.getDate();
        String fechaInicial = cu.dateNotTime(fecha);
        String fechaFinal = cu.dateNotTime(fecha2);
        int contcantidad1 = 0;
        int contDiferencias = 0;
        int posDiferencias = 0;
        for (int f = 0; f < matriz3.length; f++) {
            for (int g = 0; g < matriz3[f].length; g++) {
                System.out.println("tamaño matriz:" + matriz3[f].length);
                if (g == 4) {
                    System.out.println("hola que hace");
                    SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd");
                    Date date1 = formato.parse(matriz3[f][g]);
                    Date date2 = formato.parse(fechaInicial);
                    Date date3 = formato.parse(fechaFinal);

                    System.out.println("Date1" + date1);
                    System.out.println("resultado1"+date1.after(date2));
                     System.out.println("resultado2"+date1.before(date3));
                    System.out.println("Dateini" + date2);

                    System.out.println("Datefin" + date3);
                    if (date1.after(date2) && date1.before(date3)) {
                        contcantidad1 = contcantidad1 + 1;
                    }
                }
                // SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd");
                // Date date1=formato.parse(fecha1);
                //Date date2=formato.parse(fecha2);
                //System.out.println("resultado="+date1.before(date2));

            }

        }
        System.out.println("contador"+contcantidad1);
        matriz4 = new String[contcantidad1][5];
        for (int i = 0; i < matriz3.length; i++) {
            for (int j = 0; j < matriz3[i].length; j++) {
               if (j == 4) {
                    System.out.println("hola que hace2");
                    SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd");
                    Date date1 = formato.parse(matriz3[i][j]);
                    Date date2 = formato.parse(fechaInicial);
                    Date date3 = formato.parse(fechaFinal);

                    System.out.println("Date1" + date1);
                    System.out.println("resultado1"+date1.after(date2));
                     System.out.println("resultado2"+date1.before(date3));
                    System.out.println("Dateini" + date2);

                    System.out.println("Datefin" + date3);
                    if (date1.after(date2) && date1.before(date3)) {
                        
                         matriz4[cont][0] = matriz3[i][0];
                    matriz4[cont][1] = matriz3[i][1];
                    matriz4[cont][2] = matriz3[i][2];
                    matriz4[cont][3] = matriz3[i][3];
                    matriz4[cont][4] = matriz3[i][4];
                    cont = cont + 1;
                    }
                } else {

                    System.out.println("Los archivos son diferentes en la columna " + i + " y en la columna " + j);
                }
            }

        }

        CargarTablas2(matriz4, contcantidad1);
        matriz3 = new String[0][5];
        matriz2 = new String[0][5];
        matriz4 = new String[0][5];
        limpiar();

    }


}
