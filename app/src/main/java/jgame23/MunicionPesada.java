package jgame23;

import java.awt.Graphics2D;

import com.entropyinteractive.Keyboard;

public class MunicionPesada extends ObjetoGrafico {
    private int posXObjetivo, posYObjetivo;
    private final int speedX = 2, speedY = 2;

    public MunicionPesada(String filename, int x, int y, int posXObjetivo, int posYObjetivo) {
        super(filename);
        this.setPosition(x, y);
        this.posXObjetivo = posXObjetivo;
        this.posYObjetivo = posYObjetivo;
        updatePosition();
    }

    public void draw(Graphics2D g) {
        super.draw(g);
    }

    @Override
    public void mover(double delta, Keyboard keyboard) {
        throw new UnsupportedOperationException("Unimplemented method 'mover'");
    }

    @Override
    public double getCoordenadas() {
        throw new UnsupportedOperationException("Unimplemented method 'getCoordenadas'");
    }

    public void updatePosition() {
        double dx = posXObjetivo - this.getX();
        double dy = posYObjetivo - this.getY();

        double angleInRadians = Math.atan2(dy, dx);
        int angleInDegrees = (int) Math.toDegrees(angleInRadians);

        switch (angleInDegrees) {
            case -180:
                this.setPosition(this.getX() + speedX, this.getY());
                posXObjetivo += speedX;
                break;
            case -135:
                this.setPosition(this.getX() - speedX, this.getY() - speedY);
                posXObjetivo -= speedX;
                posYObjetivo -= speedY;
                break;
            case -90:
                this.setPosition(this.getX(), this.getY() + speedY);
                posYObjetivo += speedY;
                break;
            case -45:
                this.setPosition(this.getX() + speedX, this.getY() - speedY);
                posXObjetivo += speedX;
                posYObjetivo -= speedY;
                break;
            case 0:
                this.setPosition(this.getX() + speedX, this.getY());
                posXObjetivo += speedY;
                break;
            case 45:
                this.setPosition(this.getX() + speedX, this.getY() + speedY);
                posXObjetivo += speedX;
                posYObjetivo += speedY;
                break;
            case 90:
                this.setPosition(this.getX(), this.getY() + speedY);
                posYObjetivo += speedY;
                break;
            case 135:
                this.setPosition(this.getX() - speedX, this.getY() + speedY);
                posXObjetivo -= speedX;
                posYObjetivo += speedY;
                break;
            case 180:
                this.setPosition(this.getX() - speedX, this.getY());
                posXObjetivo += speedX;
                break;
        }
        if (angleInDegrees >= 0 && angleInDegrees <= 90) {
            this.setPosition(this.getX() + speedX, this.getY() + speedY);
            posXObjetivo += speedX;
            posYObjetivo += speedY;
        }
        if (angleInDegrees >= 90 && angleInDegrees <= 180) {
            this.setPosition(this.getX() - speedX, this.getY() + 1);
            posXObjetivo -= 1;
            posYObjetivo += 1;
        }
        if (angleInDegrees >= -180 && angleInDegrees <= -90) {
            this.setPosition(this.getX() - 1, this.getY() - 1);
            posXObjetivo -= 1;
            posYObjetivo -= 1;
        }
        if (angleInDegrees >= -90 && angleInDegrees <= 0) {
            this.setPosition(this.getX() + 1, this.getY() - 1);
            posXObjetivo += 1;
            posYObjetivo -= 1;
        }
    }

}
