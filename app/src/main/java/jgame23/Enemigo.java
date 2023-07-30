package jgame23;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Enemigo extends ObjetoGrafico{

    private long time, lastTime,timeMisil,lastTimeMisil;

    public Enemigo(String filename) {
        super(filename);
        time = timeMisil = 0;
        lastTime = lastTimeMisil = System.currentTimeMillis();
    }

    private ArmaGenerica gun = new ArmaGenerica();
    protected int cantEnergia;
    protected Color colorEnemigo;
    protected double x;
    protected double y;
    protected static double speed;
    protected boolean movingDown;
    protected int maxY;
    protected int minY;
    private BufferedImage imagen;

    public void update(double delta){
        if(movingDown){
            y += speed * delta;
            if(y >= (getHeight() - imagen.getHeight())){
                y = getHeight() - imagen.getHeight();
                movingDown = false;
            }
        } else {
            y -= speed * delta;
            if(y <= 0){
                y = 0;
                movingDown = true;
            }
        }
    }


    protected void disparar(){
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if(time > 1000) {
            gun.disparar(this);
            time = 0;
        }
    }

    protected void dispararMisil(double x, double y){
        timeMisil += System.currentTimeMillis() - lastTimeMisil;
        lastTimeMisil = System.currentTimeMillis();
        if(timeMisil > 2000) {
            gun.dispararMisil(x, y);
            timeMisil = 0;
        }
    }
}
