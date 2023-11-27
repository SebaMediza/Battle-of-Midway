package jgame23;

import com.entropyinteractive.Keyboard;

public class Auto extends Power_up{

    public Auto(String filename , int x, int y) {
        super(filename);
        this.setPosition(x, y);
    }

    @Override
    public void mover(double delta, Keyboard keyboard) {}

    @Override
    public double getCoordenadas() {
        return 0;
    }

    @Override
    protected void activar(Avion_p38 avionP38) {
        avionP38.setAuto(true);
    }
}
