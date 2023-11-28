package jgame23;

public abstract class Arma {
    protected final int danio = 10;

    public void disparar(Avion_p38 plane){
        Municion bala = new Municion("imagenes/municion4.png");
        BattleOfMidway.addMunicionAmigaArrayList(bala);
        bala.setPosition(plane.getX() + 18, plane.getY());
    }

    public void disparar(Enemigo plane){
        Municion bala = new Municion("imagenes/tiroEnemigo.png");
        BattleOfMidway.addMunicionEnemigaArrayList(bala);
        bala.setPosition(plane.getX(), plane.getY());
    }

    public void disparar(AvionRefuerzo plane){
        Municion bala = new Municion("imagenes/tiroEnemigo.png");
        BattleOfMidway.addMunicionAmigaArrayList(bala);
        bala.setPosition(plane.getX(), plane.getY());
    }

    public void dispararMisil(double x, double y){
        Misil misil = new Misil("imagenes/misil.png");
        BattleOfMidway.addMisilArrayList(misil);
        misil.setPosition(x, y);
    }
}
