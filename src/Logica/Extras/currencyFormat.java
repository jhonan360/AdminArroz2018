/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Extras;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 *
 * @author jhonansmith
 */
public class currencyFormat {

    DecimalFormatSymbols simboloDinero = new DecimalFormatSymbols();
    DecimalFormat ftDinero;
    DecimalFormatSymbols simboloMiles = new DecimalFormatSymbols();
    DecimalFormat ftMiles;

    public currencyFormat() {
        simboloDinero.setDecimalSeparator('.');
        simboloDinero.setGroupingSeparator(',');
        ftDinero = new DecimalFormat("$ ###,###.##", simboloDinero);
        simboloMiles.setDecimalSeparator('.');
        simboloMiles.setGroupingSeparator(',');
        ftMiles = new DecimalFormat("###,###.##", simboloMiles);
    }

    public String moneyFormat(double money) {
        return ftDinero.format(money);
    }

    public String notMoneyFormat(String money) {
        return money.replace("$", "").replace(",", "");
    }

    public String thousandsFormat(double number) {
        return ftMiles.format(number);
    }

    public String notThousandsFormat(String money) {
        return money.replace(",", "");
    }

}
