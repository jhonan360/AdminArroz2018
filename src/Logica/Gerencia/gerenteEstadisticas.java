/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Gerencia;

import Interfaces.GerenteEstadisticas;
import Logica.Liquidacion.*;
import Reportes.logicaReportes;

/**
 *
 * @author Lizeth
 */
public class gerenteEstadisticas {

    public static GerenteEstadisticas Estadisticas;
    public static logicaReportes reportes;

    public gerenteEstadisticas() {
        reportes = new logicaReportes();
    }

    public void capturar() {
        int mesIni = Estadisticas.jMonthChooserIni.getMonth() + 1;
        int mesFin = Estadisticas.jMonthChooserFin.getMonth() + 1;
        int anio = Estadisticas.jYearChooserIni.getYear();
        reportes.reporteGerenteEstadisticas(mesIni, mesFin, anio);
        System.out.println(mesIni);
        System.out.println(mesFin);
        System.out.println(anio);
    }
}
