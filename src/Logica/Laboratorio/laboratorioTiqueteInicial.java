/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Laboratorio;
import Interfaces.BusquedasTiquete;
import Interfaces.BusquedasTiqueteInicial;
import Interfaces.LaboratorioTiqueteInicial;
import Logica.Extras.currencyFormat;
import Logica.Extras.extras;
import Logica.Extras.login;
import Negocio.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author uriel
 */
public class laboratorioTiqueteInicial {

    public static login login;
    public String user;
    public static extras ext;
    public static currencyFormat cu;
    public static LaboratorioTiqueteInicial labo;
    public static BusquedasTiquete BusTiquete;
    public static BusquedasTiqueteInicial BusInicial;
    public static Statement st, st1, st2, st3;
    public static Conexion Con;
    public static ResultSet rstipo, rstipo2, rsconsecutivo, rstipos;
    public static String consecutivo, idTiquete, ccAgricultor, idTipoDeArroz, idAgricultor, humedadUno, impurezaUno, fecha;

    public laboratorioTiqueteInicial() {
        ext = new extras();
        cu = new currencyFormat();
        labo.LBFECHA.setText(cu.dateNotTime(ext.fecha()));
        labo.Tiquete.setText(String.valueOf(ext.getNextIndex("tiquete")));
    }

    public static void abrirBusquedasTiquete(String TiqLab) {
        BusTiquete = new BusquedasTiquete(TiqLab);
        BusTiquete.setVisible(true);
        BusTiquete.panel.setEnabledAt(0, true);
        BusTiquete.panel.setEnabledAt(1, false);
        BusTiquete.panel.setEnabledAt(2, false);
        BusTiquete.panel.setSelectedIndex(0);
    }

    public static void limpiarCampos() {
        labo.txtAgricultor.setText("");
        labo.TxtHumedadUno.setText("");
        labo.TxtImpurezaUno.setText("");
        labo.cmbTipoArroz.setSelectedIndex(0);
    }

    public void guardar() {
        idTiquete = labo.Tiquete.getText();
        idAgricultor = ccAgricultor;
        idTipoDeArroz = String.valueOf(labo.cmbTipoArroz.getSelectedIndex() + 1);
        //idTipoDeArroz = labo.cmbTipoArroz.getSelectedItem().toString();
        user = login.enviarUsuario();
        humedadUno = labo.TxtHumedadUno.getText();
        impurezaUno = labo.TxtImpurezaUno.getText();
        fecha = ext.fecha();
        System.out.println("idTiquete= " + idTiquete);
        System.out.println("idAgricultor= " + idAgricultor);
        System.out.println("idTipoDeArroz= " + idTipoDeArroz);
        System.out.println("user= " + user);
        System.out.println("humedad= " + humedadUno);
        System.out.println("impureza= " + impurezaUno);

        if (!idTiquete.equals("") && idAgricultor != null && !idTipoDeArroz.equals("") && !user.equals("") && !humedadUno.equals("") && !impurezaUno.equals("")) {
            //idTipoDeArroz = getIdTipo(idTipoDeArroz);
            insertar(idTiquete, idAgricultor, idTipoDeArroz, user, fecha, humedadUno, impurezaUno);//Llamado al metodo insertar
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        }

    }

    public void insertar(String idTiquete, String idAgricultor, String idTipoDeArroz, String user, String fecha, String humedadUno, String impurezaUno) {
        try {
            Con = new Conexion();
            st2 = Con.conexion.createStatement();
            st2.executeUpdate("INSERT INTO tiquete (idTiquete,idAgricultor,idTipoDeArroz,user,fecha,humedadUno,impurezaUno) VALUES ('" + idTiquete + "','" + idAgricultor + "','" + idTipoDeArroz + "','" + user + "','" + fecha + "','" + humedadUno + "','" + impurezaUno + "')");
            JOptionPane.showMessageDialog(null, "Tiquete registrado");
            labo.Tiquete.setText(String.valueOf(ext.getNextIndex("tiquete")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}