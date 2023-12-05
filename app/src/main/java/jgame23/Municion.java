package jgame23;

import com.entropyinteractive.Keyboard;
import java.awt.*;

public class Municion extends ObjetoGrafico {
    private int posXObjetivo, posYObjetivo;

    public Municion(String filename, int x, int y) {
        super(filename);
        this.position.x = x;
        this.position.y = y;
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }

    @Override
    public void mover(double delta, Keyboard keyboard) {
    }

    @Override
    public double getCoordenadas() {
        return 0;
    }

    void setPosXObjetivo(int x) {
        this.posXObjetivo = x;
    }

    void setPosYObjetivo(int y) {
        this.posYObjetivo = y;
    }

    void recorerVector() {
        

    }
}
