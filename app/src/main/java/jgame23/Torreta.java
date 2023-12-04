package jgame23;

import java.awt.Graphics2D;
import com.entropyinteractive.Keyboard;

public class Torreta extends ObjetoGrafico {

    private long time, lastTime;
    private int vida;

    private ArmaGenerica gun = new ArmaGenerica();
    public Torreta(String filename, int x, int y) {
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

    public void disparar() {
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if(time > 1000) {
            gun.disparar(this);
            time = 0;
        }
    }

    @Override
    public double getCoordenadas() {
        throw new UnsupportedOperationException("Unimplemented method 'getCoordenadas'");
    }

    public int getVida() {
        return vida;
    }
    
    public void setVida(int vida) {
        this.vida = vida;
    }
    
}
