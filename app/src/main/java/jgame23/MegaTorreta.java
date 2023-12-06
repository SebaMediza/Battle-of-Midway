package jgame23;

import com.entropyinteractive.Keyboard;
import java.awt.*;

public class MegaTorreta extends ObjetoGrafico {

    private long time, lastTime;
    private int vida;
    private ArmaGenerica gun = new ArmaGenerica();

    public MegaTorreta(String filename, int x, int y) {
        super(filename);
        this.setPosition(x, y);
        time = 0;
        lastTime = System.currentTimeMillis();
        this.vida = 125;
    }
    public void draw(Graphics2D g) {
        super.draw(g);
    }

    public void disparar() {
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if (time > 500) {
            gun.dispararMisil(this);
            time = 0;
        }
    }

    public void hit(){
        vida -= 5;
    }

    public int getVida(){
        return vida;
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
