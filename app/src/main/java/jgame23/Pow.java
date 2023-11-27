package jgame23;

import com.entropyinteractive.Keyboard;
/*
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
*/

public class Pow extends Power_up{
    public Pow(String filename, int x, int y) {
        super(filename);
        this.setPosition(x, y);
    }

    protected void activar(Avion_p38 avionP38) {
        avionP38.setEnegia(25);
    }

    @Override
    public void mover(double delta, Keyboard keyboard) {}

    @Override
    public double getCoordenadas() {
        return 0;
    }
}
