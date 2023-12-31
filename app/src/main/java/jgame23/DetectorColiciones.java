package jgame23;

import java.awt.*;

public abstract class DetectorColiciones extends Rectangle {
    public static boolean detectarColicionMunicionAmigaAvionEnemigo(Municion municion, AvionEnemigo avionEnemigo) {
        Rectangle objeto1 = new Rectangle((int) municion.getX(), (int) municion.getY(), (int) municion.getWidth(), (int) municion.getHeigth());
        Rectangle objeto2 = new Rectangle((int) avionEnemigo.getX(), (int) avionEnemigo.getY(), (int) avionEnemigo.getHeigth(), (int) avionEnemigo.getWidth());
    return objeto1.intersects(objeto2);
    }
    public static boolean detectarColicionP38AvionEnemigo(Avion_p38 avionP38, AvionEnemigo avionEnemigo) {
        Rectangle objeto1 = new Rectangle((int) avionP38.getX(), (int) avionP38.getY(), (int) avionP38.getWidth(), (int) avionP38.getHeigth());
        Rectangle objeto2 = new Rectangle((int) avionEnemigo.getX(), (int) avionEnemigo.getY(), (int) avionEnemigo.getHeigth(), (int) avionEnemigo.getWidth());
        return objeto1.intersects(objeto2);
    }
    public static boolean detectarColicionMunicionEnemigaP38(Municion municion, Avion_p38 avionP38){
        boolean colicion = false;
        Rectangle objeto1 = new Rectangle((int) avionP38.getX(), (int) avionP38.getY(), (int) avionP38.getHeigth(), (int) avionP38.getWidth());
        Rectangle objeto2 = new Rectangle((int) municion.getX(), (int) municion.getY(), (int) municion.getHeigth(), (int) municion.getWidth());
        if (objeto2.intersects(objeto1)){
            avionP38.hit();
            colicion = true;
        }
        return colicion;
    }
    public static boolean detectarColicionMunicionEnemigaP38(MunicionPesada municion, Avion_p38 avionP38){
        boolean colicion = false;
        Rectangle objeto1 = new Rectangle((int) avionP38.getX(), (int) avionP38.getY(), (int) avionP38.getHeigth(), (int) avionP38.getWidth());
        Rectangle objeto2 = new Rectangle((int) municion.getX(), (int) municion.getY(), (int) municion.getHeigth(), (int) municion.getWidth());
        if (objeto2.intersects(objeto1)){
            avionP38.hit();
            colicion = true;
        }
        return colicion;
    }
    public static boolean detectarColicionesP38AvionBonus(Avion_p38 avionP38, AvionEnemigo avionEnemigo){
        Rectangle objeto1 = new Rectangle((int) avionP38.getX(), (int) avionP38.getY(), (int) avionP38.getHeigth(), (int) avionP38.getWidth());
        Rectangle objeto2 = new Rectangle((int) avionEnemigo.getX(), (int) avionEnemigo.getY(), (int) avionEnemigo.getHeigth(), (int) avionEnemigo.getWidth());
        return objeto1.intersects(objeto2);
    }
    public static boolean detectarColicionMunicionAmigaBordePantalla(Municion municion){
        Rectangle objeto1 = new Rectangle((int) municion.getX(), (int) municion.getY(), (int) municion.getHeigth(), (int) municion.getWidth());
        Rectangle objeto2 = new Rectangle(0,  5, Toolkit.getDefaultToolkit().getScreenSize().width, 25);
        return objeto1.intersects(objeto2);
    }
    public static boolean detectarColicionAvionEnemigoBordePantallaSup(AvionEnemigo avionEnemigo){
        Rectangle objeto1 = new Rectangle((int) avionEnemigo.getX(), (int) avionEnemigo.getY(), (int) avionEnemigo.getHeigth(), (int) avionEnemigo.getWidth());
        Rectangle objeto2 = new Rectangle(0,  -5, Toolkit.getDefaultToolkit().getScreenSize().width, 25);
        return objeto1.intersects(objeto2);
    }
    public static boolean detectarColicionBArcoEnemigoBordePantallaInf(BarcoEnemigo barcoEnemigo){
        Rectangle objeto1 = new Rectangle((int) barcoEnemigo.getX(), (int) barcoEnemigo.getY(), (int) barcoEnemigo.getHeigth(), (int) barcoEnemigo.getWidth());
        Rectangle objeto2 = new Rectangle(0, (Toolkit.getDefaultToolkit().getScreenSize().height - 50), Toolkit.getDefaultToolkit().getScreenSize().width, 25);
        return objeto1.intersects(objeto2);
    }

    public static boolean detectarColicionAvionEnemigoBordePantallaDerecho(AvionEnemigo avionEnemigo){
        Rectangle objeto1 = new Rectangle((int) avionEnemigo.getX(), (int) avionEnemigo.getY(), (int) avionEnemigo.getHeigth(), (int) avionEnemigo.getWidth());
        Rectangle objeto2 = new Rectangle(950, 0, 20, Toolkit.getDefaultToolkit().getScreenSize().height - 37);
        return objeto1.intersects(objeto2);
    }
    public static boolean detectarColicionAvionEnemigoBordePantallaIzquierdo(AvionEnemigo avionEnemigo){
        Rectangle objeto1 = new Rectangle((int) avionEnemigo.getX(), (int) avionEnemigo.getY(), (int) avionEnemigo.getHeigth(), (int) avionEnemigo.getWidth());
        Rectangle objeto2 = new Rectangle(0, 0, 10, Toolkit.getDefaultToolkit().getScreenSize().height - 37);
        return objeto1.intersects(objeto2);
    }
    
    public static boolean detectarColicionMunicionAmigaMisilEnemigo(Municion municion, Misil misil){
        Rectangle objeto1 = new Rectangle((int) misil.getX(), (int) misil.getY(), (int) misil.getHeigth(), (int) misil.getWidth());
        Rectangle objeto2 = new Rectangle((int) municion.getX(), (int) municion.getY(), (int) municion.getHeigth(), (int) municion.getWidth());
        return objeto2.intersects(objeto1);
    }
    public static boolean detectarColicionP38MisilEnemigo(Avion_p38 avionP38, Misil misil){
        Rectangle objeto1 = new Rectangle((int) misil.getX(), (int) misil.getY(), (int) misil.getHeigth(), (int) misil.getWidth());
        Rectangle objeto2 = new Rectangle((int) avionP38.getX(), (int) avionP38.getY(), (int) avionP38.getHeigth(), (int) avionP38.getWidth());
        return objeto2.intersects(objeto1);
    }
    public static boolean detectarColicion(Municion municionAmiga, Municion municionEnemiga){
        boolean colicion = false;
        Rectangle objeto1 = new Rectangle((int) municionAmiga.getX(), (int) municionAmiga.getY(), (int) municionAmiga.getHeigth(), (int) municionAmiga.getWidth());
        Rectangle objeto2 = new Rectangle((int) municionEnemiga.getX(), (int) municionEnemiga.getY(), (int) municionEnemiga.getHeigth(), (int) municionEnemiga.getWidth());
        if (objeto1.intersects(objeto2)){
            colicion = true;
            BattleOfMidway.puntuacion += 500;
        }
        return colicion;
    }
    public static boolean detectarColicionMuniAmiTorreta(Municion municionAmiga, Torreta torreta){
        Rectangle objeto1 = new Rectangle((int) municionAmiga.getX(), (int) municionAmiga.getY(), (int) municionAmiga.getHeigth(), (int) municionAmiga.getWidth());
        Rectangle objeto2 = new Rectangle((int) torreta.getX(), (int) torreta.getY(), (int) torreta.getHeigth(), (int) torreta.getWidth());
        return objeto1.intersects(objeto2);
    }

    public static boolean detectarColicionesPowerUp(Avion_p38 avionP38, Power_up powerUp){
        Rectangle objeto1 = new Rectangle((int) avionP38.getX(), (int) avionP38.getY(), (int) avionP38.getHeigth(), (int) avionP38.getWidth());
        Rectangle objeto2 = new Rectangle((int) powerUp.getX(), (int) powerUp.getY(), (int) powerUp.getHeigth(), (int) powerUp.getWidth());
        return objeto1.intersects(objeto2);
    }
    public static boolean detectarColicionesArmaBonus(Avion_p38 avionP38, ArmaBonus armaBonus){
        Rectangle objeto1 = new Rectangle((int) avionP38.getX(), (int) avionP38.getY(), (int) avionP38.getHeigth(), (int) avionP38.getWidth());
        Rectangle objeto2 = new Rectangle((int) armaBonus.getX(), (int) armaBonus.getY(), (int) armaBonus.getHeigth(), (int) armaBonus.getWidth());
        return objeto1.intersects(objeto2);
    }
    public static boolean detectarColicionesPowerUpBalas(Municion municion, Power_up powerUp){
        Rectangle objeto1 = new Rectangle((int) municion.getX(), (int) municion.getY(), (int) municion.getHeigth(), (int) municion.getWidth());
        Rectangle objeto2 = new Rectangle((int) powerUp.getX(), (int) powerUp.getY(), (int) powerUp.getHeigth(), (int) powerUp.getWidth());
        return objeto1.intersects(objeto2);
    }
    public static boolean detectarColicionesArmaUpBalas(Municion municion, ArmaBonus armaBonus){
        Rectangle objeto1 = new Rectangle((int) municion.getX(), (int) municion.getY(), (int) municion.getHeigth(), (int) municion.getWidth());
        Rectangle objeto2 = new Rectangle((int) armaBonus.getX(), (int) armaBonus.getY(), (int) armaBonus.getHeigth(), (int) armaBonus.getWidth());
        return objeto1.intersects(objeto2);
    }
    public static boolean detectarColicionesRefuerzoBalasEnemigas(Municion municion, AvionRefuerzo avionRefuerzo){
        Rectangle objeto1 = new Rectangle((int) municion.getX(), (int) municion.getY(), (int) municion.getHeigth(), (int) municion.getWidth());
        Rectangle objeto2 = new Rectangle((int) avionRefuerzo.getX(), (int) avionRefuerzo.getY(), (int) avionRefuerzo.getHeigth(), (int) avionRefuerzo.getWidth());
        return objeto1.intersects(objeto2);
    }
    public static boolean detectarColicionesBarcosBalas(Municion municion, BarcoEnemigo barcoEnemigo){
        Rectangle objeto1 = new Rectangle((int) municion.getX(), (int) municion.getY(), (int) municion.getHeigth(), (int) municion.getWidth());
        Rectangle objeto2 = new Rectangle((int) barcoEnemigo.getX(), (int) barcoEnemigo.getY(), (int) barcoEnemigo.getHeigth(), (int) barcoEnemigo.getWidth());
        return objeto1.intersects(objeto2);
    }
    public static boolean detectarColicionesMegaTorreta(Municion municion, MegaTorreta megaTorreta){
        Rectangle objeto1 = new Rectangle((int) municion.getX(), (int) municion.getY(), (int) municion.getHeigth(), (int) municion.getWidth());
        Rectangle objeto2 = new Rectangle((int) megaTorreta.getX(), (int) megaTorreta.getY(), (int) megaTorreta.getHeigth(), (int) megaTorreta.getWidth());
        return objeto1.intersects(objeto2);
    }

}
