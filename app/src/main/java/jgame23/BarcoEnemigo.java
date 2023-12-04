package jgame23;

import java.awt.Graphics2D;

import com.entropyinteractive.Keyboard;

public class BarcoEnemigo extends Enemigo {
    public BarcoEnemigo(String filename, double x, double y) {
        super(filename);
        this.setPosition(x, y);
    }

    public void updatePosition() {
        this.setPosition(position.x, position.y + 1/* 0.50 */);
    }

    public void draw(Graphics2D g) {
        super.draw(g);
    }

    public void dispararMisil() {
       super.dispararMisil(position.x, position.y);
    }

    @Override
    public void mover(double delta, Keyboard keyboard) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getCoordenadas() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
