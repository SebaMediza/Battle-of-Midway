package jgame23;

import com.entropyinteractive.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class AvionEnemigo extends Enemigo {
    boolean backwards = false, left = false;
    int speedAvionEnemigo = 3, speedAvionBonus = 5;
    float x, y; // Object's position
    float amplitudeX = 4, amplitudeY = 4; // Amplitude of the loop in the x and y directions
    float angle = 90.0f; // Current angle in radians
    float angleBonus = 180.0f;

    BufferedImage enemigo_0, enemigo_45, enemigo_90, enemigo_135, enemigo_180, enemigo_245, enemigo_270;

    public AvionEnemigo(String filename, int x, int y) {
        super(filename);
        this.setPosition(x, y);
        try {
            enemigo_0 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/enemigo_0.png")));
            enemigo_45 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/enemigo_45.png")));
            enemigo_90 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/enemigo_90.png")));
            enemigo_135 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/enemigo_135.png")));
            enemigo_180 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/enemigo_180.png")));
            enemigo_245 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/enemigo_245.png")));
            enemigo_270 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/enemigo_270.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void disparar() {
        super.disparar();
    }

    @Override
    public void update(double delta) {
        super.update(delta);
    }

    public void autoMover1() {
        int xOrigin = (int) this.getX();
        int yOrigin = (int) this.getY();
        double yNow = getY();
        if (yNow < 600 && !backwards) {
            this.setPosition(getX(), getY() + speedAvionEnemigo);
            if (yNow > 490) {
                angle += speedAvionEnemigo;
                x = amplitudeX * (float) Math.cos(angle * Math.PI / 180);
                y = amplitudeY * (float) Math.sin(angle * Math.PI / 180);
                this.setPosition(xOrigin + x, yOrigin + y);
                if (y < -1) {
                    try {
                        this.image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/enemigo_90.png")));
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                backwards = true;
                angle = 90f;
                }
            }
        }
        if (yNow > 10 && backwards) {
            this.setPosition(getX(), getY() - speedAvionEnemigo);
        }
    }
    public void autoMover2(){
        this.setPosition(this.getX() + speedAvionEnemigo, this.getY());
    }
    public void autoMover3() {
        int xOrigin = (int) this.getX();
        int yOrigin = (int) this.getY();
        double yNow = getY();
        if (yNow < 600 && !left) {
            this.setPosition(getX(), getY() + speedAvionEnemigo);
            if (yNow > 490) {
                angle += speedAvionEnemigo;
                x = amplitudeX * (float) Math.cos(angle * Math.PI / 180);
                y = amplitudeY * (float) Math.sin(angle * Math.PI / 180);
                this.setPosition(xOrigin + x, yOrigin + y);
                if (y < 3) {
                    try {
                        this.image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/enemigo_180.png")));
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    left = true;
                }
            }
        }
        if (left){
            this.setPosition(this.getX() - speedAvionEnemigo, this.getY());
        }
    }
    public void autoMover4(){
        this.setPosition(this.getX() + speedAvionEnemigo, this.getY() + speedAvionEnemigo);
    }
    public void temp() {
        if (this.getY() < 500) {
            this.setPosition(this.getX(), this.getY() + 10);
        }
    }

    public void autoMoverAvionBonus(){
        int amplitudeX = 2, amplitudeY = 2;
        int xOrigin = (int) this.getX();
        int yOrigin = (int) this.getY();
        double xNow = this.getX();
        if (xNow > 487) {
            angleBonus += speedAvionEnemigo;
            x = amplitudeX * (float) Math.cos(angleBonus * Math.PI / 180);
            y = amplitudeY * (float) Math.sin(angleBonus * Math.PI / 180);
            this.setPosition(xOrigin + x, yOrigin - y);
        }
        this.setPosition(getX() + speedAvionBonus, getY());
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
