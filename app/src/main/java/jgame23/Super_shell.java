package jgame23;

import com.entropyinteractive.Keyboard;

import java.awt.*;

public class Super_shell extends Power_up {
    public Super_shell(String filename, int x, int y) {
        super(filename);
        this.setPosition(x, y);
    }

    @Override
    public void mover(double delta, Keyboard keyboard) {
    }

    @Override
    protected void activar(Avion_p38 avionP38) {
       
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }

    @Override
    public double getCoordenadas() {
        return 0;
    }

}
