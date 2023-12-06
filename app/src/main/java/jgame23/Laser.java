package jgame23;

public class Laser extends ArmaBonus{

    public Laser(String filename, int x, int y) {
        super(filename);
        this.setPosition(x, y);
    }

    @Override
    protected void activar(Avion_p38 avionP38) {
        System.out.println("Ametralladora activada");
        avionP38.setAmetralladora(true);
    }
}
