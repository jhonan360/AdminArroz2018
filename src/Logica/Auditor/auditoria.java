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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author uriel
 */
public class auditoria {
    public static Auditoria Auditoria;
    JFileChooser seleccionado1 = new JFileChooser();//Declaracion del objeto filechooser el cual abre la ventana encargada de recivir el archivo
    File Archivo1;//elemento tipo file almacena el arcivo
    long Tamaño_Archivo1 = 0;//almacena el tamaño del archivo el kilobytes
    String NombreArchivo1 = "";//Almacena el nombre del archivo 1
    String Extension_Archivo1 = "";//almacena la extencion del archivo 1
    FileReader filereader1 = null;//se encarga de leer el archivo 1
    BufferedReader bufferedreader1 = null;//se encarga de procesar el archivo 1
    String Lineas_Archivo1 = "";//lee cada una de las lineas del archivo 1 al momento de ser procesado
    int Cantidad_Lineas1 = 0;//almacena la cantidad del registros del archivo 1 
    String[][] matriz1 = new String[Cantidad_Lineas1][5];//matriz encargada de qalmacenar cada uno de los campos existentes en el archivo 1     
    public DefaultTableModel Modelo1;//declara el midelo de la tabla 1 para el archivo 1
    public String columnas[] = new String[]{"Tipo", "Evento", "Cambios", "Usuario", "Fecha"};//declara el nombre de cada una de las columnas para las dos tablas

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
                    filereader1 = new FileReader(Archivo1);
                    bufferedreader1 = new BufferedReader(filereader1);
                    while (Lineas_Archivo1 != null ) {
                        Lineas_Archivo1 = bufferedreader1.readLine();
                        if (Lineas_Archivo1 != null) {
                            System.out.println(Lineas_Archivo1);
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
            filereader1 = new FileReader(Archivo1);
            bufferedreader1 = new BufferedReader(filereader1);
            matriz1 = new String[Cantidad_Lineas1][5];
            int cont = 0;
            while (Lineas_Archivo1 != null) {
                Lineas_Archivo1 = bufferedreader1.readLine();
                if (Lineas_Archivo1 != null ) {
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
                    System.out.println(matriz1[x][y]);
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
        
    }
}
