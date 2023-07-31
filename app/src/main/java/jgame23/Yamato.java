package jgame23;

import com.entropyinteractive.Keyboard;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Yamato extends Enemigo{
    BufferedImage mainTurret;

    public Yamato(String filename) {
        super(filename);
        try{
            mainTurret = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/0.png")));
        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        g.drawImage(mainTurret,(int) this.getX() + 500, (int) this.getY() + 900,null);
    }

    public void disparar(){
        super.disparar();
    }

//    @Override
    protected void dispararMisil(double x, double y) {
        super.dispararMisil(x, y);
    }

    public void updatePosition() {
        this.setPosition(position.x, position.y + 2/*0.50*/);
    }

    @Override
    public void mover(double delta, Keyboard keyboard) {}

    @Override
    public double getCoordenadas() {
        return 0;
    }
}
