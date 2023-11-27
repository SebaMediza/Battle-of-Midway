package jgame23;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.entropyinteractive.Keyboard;

public class AvionRefuerzo extends ObjetoGrafico {

    public AvionRefuerzo(String filename, double x, double y) {
        super(filename);
        this.setPosition(x, y);
    }

    public void draw(Graphics2D g) {
        super.draw(g);
    }

    @Override
    public void mover(double delta, Keyboard keyboard) {
        if (keyboard.isKeyPressed(KeyEvent.VK_UP)) {
            this.setPosition(this.getX(), this.getY() - 450 * delta);
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
            this.setPosition(this.getX(), this.getY() + 450 * delta);
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
            this.setPosition(this.getX() + 450 * delta, this.getY());
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
            this.setPosition(this.getX() - 450 * delta, this.getY());
        }
    }

    @Override
    public double getCoordenadas() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCoordenadas'");
    }
    
}
