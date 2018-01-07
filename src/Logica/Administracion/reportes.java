/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Administracion;
import Negocio.Conexion;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import Interfaces.Reportes;
import javax.swing.JOptionPane;
/**
 *
 * @author uriel
 */
public class reportes {
   


/**
 *
 * @author uriel
 */

    Conexion Con;
    public static Statement st;
    public static ResultSet rs;
    Reportes Reporte = new Reportes();
    
    public void Empleados(String query){
        try{
            	System.out.println("llegue a reportes");
		Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("sssss.pdf"));
            Font bfBold18 = new Font(FontFamily.TIMES_ROMAN, 18, Font.BOLD,
                    new BaseColor(0, 0, 0));
            Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 12,
                    Font.BOLDITALIC, new BaseColor(0, 0, 0));
            Font bf12 = new Font(FontFamily.TIMES_ROMAN, 12);
            //PdfWriter.getInstance(doc, null);
            
            // document header properties
            doc.addAuthor("betterThanZero");
            doc.addCreationDate();
            doc.addProducer();
            doc.addCreator("MySampleCode.com");
            doc.addTitle("DemoPDF");
            doc.setPageSize(PageSize.LETTER);
            doc.open();
			
         Con = new Conexion();
         
   
        st = Con.conexion.createStatement();
            rs = st.executeQuery(query);
            //ResultSetMetaData rsc3md = rsc3.getMetaData();
            PdfPTable table = new PdfPTable(7);
            table.addCell("Cedula");
            table.addCell("Nombres");
            table.addCell("Apellidos");
            table.addCell("Ciudad");
            table.addCell("Direccion");
            table.addCell("Telefono");
            table.addCell("Cargo");
            while (rs.next()) {
                System.out.println("hay datos");
                for(int aw = 1; aw <= 7; aw++){
                    System.out.println(aw);
                    table.addCell(rs.getString(aw)+"");
        }
                
            }
            doc.add(table);
      
            Con.Desconectar();
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void agricultores(){
        try{
            	System.out.println("llegue a reportes");
		Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("agricultores.pdf"));
            Font bfBold18 = new Font(FontFamily.TIMES_ROMAN, 18, Font.BOLD,
                    new BaseColor(0, 0, 0));
            Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 12,
                    Font.BOLDITALIC, new BaseColor(0, 0, 0));
            Font bf12 = new Font(FontFamily.TIMES_ROMAN, 12);
            //PdfWriter.getInstance(doc, null);
            
            // document header properties
            doc.addAuthor("betterThanZero");
            doc.addCreationDate();
            doc.addProducer();
            doc.addCreator("MySampleCode.com");
            doc.addTitle("DemoPDF");
            doc.setPageSize(PageSize.LETTER);
            doc.open();
			
         Con = new Conexion();
         
   
        st = Con.conexion.createStatement();
            rs = st.executeQuery("SELECT ccAgricultor,nombres,apellidos,municipios.nombre,direccion FROM agricultor,municipios WHERE agricultor.idMunicipio=municipios.idMunicipio");
            //ResultSetMetaData rsc3md = rsc3.getMetaData();
            PdfPTable table = new PdfPTable(5);
            
            table.addCell("Nombres");
            table.addCell("Apellidos");
            table.addCell("Ciudad");
            table.addCell("Direccion");
            table.addCell("Cedula");
            while (rs.next()) {
                for(int aw = 1; aw <= 5; aw++){
                    System.out.println(aw);
            table.addCell(rs.getString(aw)+"");
        }
                
            }
            doc.add(table);
      
            Con.Desconectar();
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void guardar(Reportes X){
       
        System.out.println(X.ChBoxcedula.isSelected());
        System.out.println(X.Chboxciudad.isSelected());
        System.out.println(X.ChBoxCargo.isSelected());
        System.out.println("ddd"+X.TxtCedula.getText());
        
            if (X.ChBoxcedula.isSelected() == true && X.Chboxciudad.isSelected() == true && X.ChBoxCargo.isSelected() == true) {
            if (!X.TxtCedula.getText().equals("") && !X.TxtCiudad.getText().equals("") && !X.TxtCargo.getText().equals("")) {
                String query ="SELECT ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono,cargo.nombre FROM empleado,cargo,municipios WHERE empleado.ccEmpleado like '%" + X.TxtCedula.getText() + "%' and municipios.Nombre like '%" + X.TxtCiudad.getText() + "%' and cargo.nombre like '%"+X.TxtCargo.getText()+"%' and empleado.idCargo = cargo.idCargo and empleado.idMunicipio=municipios.idMunicipio ";
                Empleados(query);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        }
        else if (X.ChBoxcedula.isSelected() == true && X.Chboxciudad.isSelected() == true){
             if (!X.TxtCedula.getText().equals("") && !X.TxtCiudad.getText().equals("")){
                String query ="SELECT ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono,cargo.nombre FROM empleado,cargo,municipios WHERE municipios.Nombre like '%" + X.TxtCiudad.getText() + "%' and empleado.ccEmpleado like '%"+X.TxtCedula.getText()+"%' and empleado.idCargo = cargo.idCargo and empleado.idMunicipio=municipios.idMunicipio ";
                 Empleados(query);
             }else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        }
    else if (X.ChBoxcedula.isSelected() == true && X.ChBoxCargo.isSelected() == true){
             if (!X.TxtCedula.getText().equals("") && !X.ChBoxCargo.getText().equals("")){
                 String query ="SELECT ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono,cargo.nombre FROM empleado,cargo,municipios WHERE empleado.ccEmpleado like '%" + X.TxtCedula.getText() + "%' and cargo.nombre like '%"+X.TxtCargo.getText()+"%' and empleado.idCargo = cargo.idCargo and empleado.idMunicipio=municipios.idMunicipio ";

                 Empleados(query);
             }else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        }
         else if (X.ChBoxCargo.isSelected() == true && X.Chboxciudad.isSelected() == true){
             if (!X.ChBoxCargo.getText().equals("") && !X.TxtCiudad.getText().equals("")){
                 String query =
"SELECT ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono,cargo.nombre FROM empleado,cargo,municipios WHERE municipios.Nombre like '%" + X.TxtCiudad.getText() + "%' and cargo.nombre like '%"+X.TxtCargo.getText()+"%' and empleado.idCargo = cargo.idCargo and empleado.idMunicipio=municipios.idMunicipio ";
                 Empleados(query);
             }else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        }
    }
}

 
