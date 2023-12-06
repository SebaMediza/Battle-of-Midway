package jgame23;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.entropyinteractive.Keyboard;

public class AvionRefuerzo extends ObjetoGrafico {
    private long time, lastTime;
    private ArmaGenerica gun = new ArmaGenerica();
    private int vida;

    public AvionRefuerzo(String filename, double x, double y) {
        super(filename);
        this.setPosition(x, y);
        time = 0;
        lastTime = System.currentTimeMillis();
        vida = 1;
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
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if (keyboard.isKeyPressed(KeyEvent.VK_X) && time > 200) {
            gun.disparar(this);
            time = 0;
        }
    }

    public void impacto() {
        this.vida--;
    }

    public int getVida() {
        return vida;
    }

    @Override
    public double getCoordenadas() {
        throw new UnsupportedOperationException("Unimplemented method 'getCoordenadas'");
    }

}
