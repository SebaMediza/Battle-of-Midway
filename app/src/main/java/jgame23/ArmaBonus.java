package jgame23;

import com.entropyinteractive.Keyboard;

public abstract class ArmaBonus extends Bonus {

    public ArmaBonus(String filename) {
        super(filename);
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