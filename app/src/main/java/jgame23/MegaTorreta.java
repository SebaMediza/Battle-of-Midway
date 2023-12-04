package jgame23;

import com.entropyinteractive.Keyboard;
import java.awt.*;

public class MegaTorreta extends ObjetoGrafico {

    private long time, lastTime;
    private int vida;

    public MegaTorreta(String filename, int x, int y) {
        super(filename);
        this.setPosition(x, y);
        time = 0;
        lastTime = System.currentTimeMillis();
        vida = 5;
    }
    public void draw(Graphics2D g) {
        super.draw(g);
    }

    @Override
    public void mover(double delta, Keyboard keyboard) {
        throw new UnsupportedOperationException("Unimplemented method 'mover'");
    }

    @Override
    public double getCoordenadas() {
        throw new UnsupportedOperationException("Unimplemented method 'getCoordenadas'");
    }
}
