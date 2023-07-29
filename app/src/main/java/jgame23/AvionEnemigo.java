package jgame23;

import com.entropyinteractive.*;
import java.awt.*;


public class AvionEnemigo extends Enemigo {
    boolean backwards = true;
    float speed = 100;
    float x, y; // Object's position
    float amplitudeX = 5, amplitudeY = 5; // Amplitude of the loop in the x and y directions
    float frequencyX = 2, frequencyY = 2; // Frequency of the loop in the x and y directions
    float angle; // Current angle in radians

    public AvionEnemigo(String filename) {
        super(filename);
        this.setPosition(100, 600);
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

    public void disparaMisil() {
        super.dispararMisil();
    }

    public void autoMoverAvionBonus() {
        this.setPosition(this.getX() + 5, this.getY());
    }

    public void autoMover1() {
        double yNow = getY();
        if (yNow < 500 && backwards) {
            this.setPosition(getX(), getY() + 5);
            if (yNow > 490) {
                // Object class for the object performing the loop
                x = (float) this.getX();
                y = (float) this.getY();
                amplitudeX = 5;
                amplitudeY = 5;
                frequencyX = 2;
                frequencyY = 2;
                angle = 0.0f;
                speed = 10;
                // Increment the angle based on the speed
                angle += speed;
                // Calculate the object's position in the loop using sine and cosine functions
                x = amplitudeX * (float) Math.cos(angle * frequencyX);
                y = amplitudeY * (float) Math.sin(angle * frequencyY);
                this.setPosition(x, y);
            }
        }
//        backwards = false;
//        if (yNow > 10 && !backwards) {
//            this.setPosition(getX(), getY() - 5);
//            if (yNow < 30){
//                backwards = true;
//            }
//        }
    }
    public void autoMover2(double targetX, double targetY){
        // Calculate movement vector
        float dx = (float) (targetX - this.getX());
        float dy = (float) (targetY - this.getY());

        // Calculate the magnitude of the vector
        float magnitude = (float) Math.sqrt(dx * dx + dy * dy);
        // Normalize the vector
        if (magnitude > 0) {
            dx /= magnitude;
            dy /= magnitude;
        }

        // Set the follower's velocity
        float velocityX = dx * speed;
        float velocityY = dy * speed;
        // Update the follower's position
        x += velocityX;
        y += velocityY;
        this.setPosition(x, y);
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
