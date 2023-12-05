package jgame23;

public abstract class Arma {
    protected final int danio = 10;

    public void disparar(Avion_p38 plane) {
        Municion bala = new Municion("imagenes/municion4.png", (int) plane.getX() + 18, (int) plane.getY());
        BattleOfMidway.addMunicionAmigaArrayList(bala);
    }

    public void disparar(Torreta torreta, Avion_p38 plane) {
        MunicionPesada bala = new MunicionPesada("imagenes/tiroEnemigo.png", (int) torreta.getX(), (int) torreta.getY(),
                (int) plane.getX(), (int) plane.getY());
        BattleOfMidway.addBalaYamato(bala);
    }

    public void disparar(Enemigo plane) {
        Municion bala = new Municion("imagenes/tiroEnemigo.png", (int) plane.getX(), (int) plane.getY());
        BattleOfMidway.addMunicionEnemigaArrayList(bala);
    }

    public void disparar(AvionRefuerzo plane) {
        Municion bala = new Municion("imagenes/tiroEnemigo.png", (int) plane.getX(), (int) plane.getY());
        BattleOfMidway.addMunicionAmigaArrayList(bala);
    }

    public void dispararMisil(double x, double y) {
        Misil misil = new Misil("imagenes/misil.png", x, y);
        BattleOfMidway.addMisilArrayList(misil);
        misil.setPosition(x, y);
    }
}
