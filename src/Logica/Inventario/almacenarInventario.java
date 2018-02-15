/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Inventario;

import Interfaces.AlmacenarInventario;
import Interfaces.EtapaInventario;
import Interfaces.Login;
import Interfaces.ProcedimientoInventario;
import Interfaces.VerProcedimientoInventario;

/**
 *
 * @author Lizeth
 */
public class almacenarInventario {

    public static Login Login;
    public static AlmacenarInventario AlmacenarI;
    public static ProcedimientoInventario ProcedI;
    public static EtapaInventario EtapaI;
    public static VerProcedimientoInventario VerProcedI;

    public void almacenarInventario() {

    }

    public static void abrirProcedimiento() {
        if (!(ProcedI instanceof ProcedimientoInventario)) {
            ProcedI = new ProcedimientoInventario();
            ProcedI.setVisible(true);
        } else {
            ProcedI.setVisible(true);
        }
    }

    public static void abrirEtapa() {
        if (!(EtapaI instanceof EtapaInventario)) {
            EtapaI = new EtapaInventario();
            EtapaI.setVisible(true);
        } else {
            EtapaI.setVisible(true);
        }
    }

    public static void abrirVerProcedimiento() {
        if (!(VerProcedI instanceof VerProcedimientoInventario)) {
            VerProcedI = new VerProcedimientoInventario();
            VerProcedI.setVisible(true);
        } else {
            VerProcedI.setVisible(true);
        }
    }

    public static void salir() {
        Login = new Login();
        Login.setVisible(true);
    }
}
