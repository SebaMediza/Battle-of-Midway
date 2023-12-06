package jgame23;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import com.entropyinteractive.Keyboard;

public class Torreta extends ObjetoGrafico {

    private long time, lastTime;
    private int vida;
    private ArmaGenerica gun = new ArmaGenerica();
    private BufferedImage img1, img3, img5, img7, img9, img11, img13, img15;
    private BufferedImage img2, img4, img6, img8, img10, img12, img14, img16;
    private BufferedImage kabom;
    String nombre;

    public Torreta(String filename, int x, int y) {
        super(filename);
        this.setPosition(x, y);
        time = 0;
        lastTime = System.currentTimeMillis();
        vida = 125;
        try {
            this.img1 = ImageIO
                    .read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/1.png")));
            this.img3 = ImageIO
                    .read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/3.png")));
            this.img5 = ImageIO
                    .read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/5.png")));
            this.img7 = ImageIO
                    .read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/7.png")));
            this.img9 = ImageIO
                    .read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/9.png")));
            this.img11 = ImageIO
                    .read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/11.png")));
            this.img13 = ImageIO
                    .read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/13.png")));
            this.img15 = ImageIO
                    .read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/15.png")));
            this.img2 = ImageIO
                    .read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/2.png")));
            this.img4 = ImageIO
                    .read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/4.png")));
            this.img6 = ImageIO
                    .read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/6.png")));
            this.img8 = ImageIO
                    .read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/8.png")));
            this.img10 = ImageIO
                    .read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/10.png")));
            this.img12 = ImageIO
                    .read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/12.png")));
            this.img14 = ImageIO
                    .read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/14.png")));
            this.img16 = ImageIO
                    .read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/16.png")));
            this.kabom = ImageIO.read(
                    Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/explocion.gif")));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void draw(Graphics2D g) {
        super.draw(g);
    }

    @Override
    public void mover(double delta, Keyboard keyboard) {
        throw new UnsupportedOperationException("Unimplemented method 'mover'");
    }

    public void disparar(Avion_p38 avion_p38) {
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if (time > 1000) {
            gun.disparar(this, avion_p38);
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
        if (this.vida <= 0) {
            this.image = this.kabom;
        }
    }

    public String toString() {
        return nombre;
    }

    public void rotacion(Avion_p38 avion_p38) {
        double dx = avion_p38.getX() - this.getX();
        double dy = avion_p38.getY() - this.getY();

        double angleInRadians = Math.atan2(dy, dx);
        int angleInDegrees = (int) Math.toDegrees(angleInRadians);

        switch (angleInDegrees) {
            case -135:
                this.image = this.img15;
                break;
            case -90:
                this.image = this.img1;
                break;
            case -45:
                this.image = this.img3;
                break;
            case 0:
                this.image = this.img5;
                break;
            case 45:
                this.image = this.img7;
                break;
            case 90:
                this.image = this.img9;
                break;
            case 135:
                this.image = this.img11;
                break;
            case 180:
                this.image = this.img13;
                break;
        }
        if (angleInDegrees > -90 && angleInDegrees < -45) {
            this.image = this.img2;
        }
        if (angleInDegrees > -45 && angleInDegrees < 0) {
            this.image = this.img4;
        }
        if (angleInDegrees > 0 && angleInDegrees < 45) {
            this.image = this.img6;
        }
        if (angleInDegrees > 45 && angleInDegrees < 90) {
            this.image = this.img8;
        }
        if (angleInDegrees > 90 && angleInDegrees < 135) {
            this.image = this.img10;
        }
        if (angleInDegrees > 135 && angleInDegrees < 180) {
            this.image = this.img12;
        }
        if (angleInDegrees > -180 && angleInDegrees < -135) {
            this.image = this.img14;
        }
        if (angleInDegrees > -135 && angleInDegrees < -90) {
            this.image = this.img16;
        }
    }
}
