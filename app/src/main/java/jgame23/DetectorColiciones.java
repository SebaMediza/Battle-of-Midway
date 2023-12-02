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
            BattleOfMidway.finalScore += 500;
        }
        return colicion;
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
    public static boolean detectarColicionesRefuerzoBalasEnemigas(Municion municion, AvionRefuerzo avionRefuerzo){
        Rectangle objeto1 = new Rectangle((int) municion.getX(), (int) municion.getY(), (int) municion.getHeigth(), (int) municion.getWidth());
        Rectangle objeto2 = new Rectangle((int) avionRefuerzo.getX(), (int) avionRefuerzo.getY(), (int) avionRefuerzo.getHeigth(), (int) avionRefuerzo.getWidth());
        return objeto1.intersects(objeto2);
    }

}
