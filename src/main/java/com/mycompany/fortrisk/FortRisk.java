package com.mycompany.fortrisk;

import com.mycompany.fortrisk.igu.PantallaPrincipal;
import javax.swing.ImageIcon;



public class FortRisk {

    public static void main(String[] args) {
               
        PantallaPrincipal princ = new PantallaPrincipal();
        princ.setVisible(true);
        princ.setLocationRelativeTo(null);
        
        ImageIcon icono = new ImageIcon("src/main/resources/img/LogoMini.png");
        princ.setIconImage(icono.getImage());
    }
}
