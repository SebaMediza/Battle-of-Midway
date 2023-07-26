package jgame23;

import com.entropyinteractive.*;
import java.awt.*;


public class AvionEnemigo extends Enemigo{
    private long time, lastTime;

    public AvionEnemigo(String filename){
        super(filename);
        this.setPosition (100,600);
    }

    public AvionEnemigo(String filename, int x, int y) {
        super(filename);
        this.setPosition(x, y);
        time = 0;
        lastTime = System.currentTimeMillis();
    }

    @Override
    public void disparar() {
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if(time > 1000) {
            super.disparar();
            time = 0;
        }
    }
    public void disparaMisil(){
        super.dispararMisil();
    }
    public void autoMoverAvionBonus(){
        this.setPosition(this.getX() + 5, this.getY());
    }
    public void autoMover1(){
        double yNow = getY();
        boolean wasd = true;
        if (yNow < 600) {
            this.setPosition(getX(), getY() + 1);
        }
        if (yNow > 10 && !wasd) {
            this.setPosition(getX(), getY() - 2);
        }
    }
    @Override
    public void mover(double delta, Keyboard keyboard) {}
    @Override
    public double getCoordenadas() {
        return 0;
    }
    public void draw(Graphics2D g) {
        super.draw(g);
    }
}
