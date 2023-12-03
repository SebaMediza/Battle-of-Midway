package jgame23;

import com.entropyinteractive.Keyboard;

public class BarcoEnemigo extends Enemigo{
    public BarcoEnemigo(String filename, double x, double y) {
        super(filename);
        this.setPosition(x, y);
    }

    @Override
    public void mover(double delta, Keyboard keyboard) {

    }

    @Override
    public double getCoordenadas() {
        return 0;
    }
}
