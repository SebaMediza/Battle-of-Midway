package jgame23;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.entropyinteractive.Keyboard;

public class AvionRefuerzo extends ObjetoGrafico {

    private ArmaGenerica gun = new ArmaGenerica();

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
            int newY = (int) (this.getY() - 450 * delta);
            position.y = newY;
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
            int newY = (int) (this.getY() + 450 * delta);
            position.y = newY;
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
            int newX = (int) (this.getX() + 450 * delta);
            position.x = newX;
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
            int newX = (int) (this.getX() - 450 * delta);
            position.x = newX;
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_X)) {
            gun.disparar(this);
        }
    }

    public void mover(double delta, Keyboard keyboard, double x, double y) {
        if (keyboard.isKeyPressed(KeyEvent.VK_UP)) {
            this.setPosition(x, y - 450 * delta);
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
            this.setPosition(x, y + 450 * delta);
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
            this.setPosition(x + 450 * delta, y);
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
            this.setPosition(x - 450 * delta, y);
        }
    }

    @Override
    public double getCoordenadas() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCoordenadas'");
    }

}
