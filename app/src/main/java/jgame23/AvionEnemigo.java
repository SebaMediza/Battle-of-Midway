package jgame23;

import com.entropyinteractive.*;
import java.awt.*;


public class AvionEnemigo extends Enemigo{
    boolean backwards = true;

    public AvionEnemigo(String filename){
        super(filename);
        this.setPosition (100,600);
    }

    public AvionEnemigo(String filename, int x, int y) {
        super(filename);
        this.setPosition(x, y);
    }

    @Override
    public void disparar() {
        super.disparar();
    }

    @Override
    public void update(double delta) {
        super.update(delta);
    }

    public void disparaMisil(){
        super.dispararMisil();
    }
    public void autoMoverAvionBonus(){
        this.setPosition(this.getX() + 5, this.getY());
    }
    public void autoMover1(){
        double yNow = getY();
        if (yNow < 600 && backwards) {
            this.setPosition(getX(), getY() + 5);
            if (yNow > 590) {
                backwards = false;
            }
        }
//        if (yNow > 10 && !backwards) {
//            this.setPosition(getX(), getY() - 10);
//            if (yNow < 20){
//                backwards = true;
//            }
//        }
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
