package jgame23;

import com.entropyinteractive.Keyboard;

import java.awt.*;


public class Misil extends ObjetoGrafico{
    private float speed = 5;
    public Misil(String filename, double x, double y) {
        super(filename);
        this.setPosition(x, y);
    }

    public void updatePosition(double targetX, double targetY){
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
        position.x += (int) velocityX;
        position.y += (int) velocityY;
        //this.setPosition(x, y);
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }

    @Override
    public void mover(double delta, Keyboard keyboard) {}

    @Override
    public double getCoordenadas() {
        return 0;
    }
}
