package jgame23;

public class Ametralladora extends ArmaBonus{
    
    public Ametralladora(String filename, int x, int y) {
        super(filename);
        this.setPosition(x, y);
    }

    @Override
    protected void activar(Avion_p38 avionP38) {
        System.out.println("Ametralladora activada");
        avionP38.setAmetralladora(true);
    }
}
