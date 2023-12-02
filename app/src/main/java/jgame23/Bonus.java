package jgame23;

import java.awt.*;

public abstract class Bonus extends ObjetoGrafico {

    public Bonus(String filename) {
        super(filename);
    }

    protected void activar(Avion_p38 avionP38) {}

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }
}
