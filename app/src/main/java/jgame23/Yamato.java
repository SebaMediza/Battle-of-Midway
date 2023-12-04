package jgame23;

import com.entropyinteractive.Keyboard;

import java.awt.*;

public class Yamato extends Enemigo{
    private Torreta torreta1, torreta2, torreta3, torreta4, mainTurret;
    private int vida;

    public Yamato(String filename, int x, int y) {
        super(filename);
        this.setPosition(x, y);
        mainTurret = new Torreta("imagenes/0.png", (int)this.x + 500, (int)this.y - 1000);
        torreta1 = new Torreta("imagenes/5.png", (int)this.x + 650, (int)this.y - 1000);
        torreta2 = new Torreta("imagenes/5.png", (int)this.x + 650, (int)this.y - 800);
        torreta4 = new Torreta("imagenes/13.png", (int)this.x + 350, (int)this.y - 1000);
        torreta3 = new Torreta("imagenes/13.png", (int)this.x + 350, (int)this.y - 800);
        BattleOfMidway.torretas.add(mainTurret);
        BattleOfMidway.torretas.add(torreta1);
        BattleOfMidway.torretas.add(torreta2);
        BattleOfMidway.torretas.add(torreta3);
        BattleOfMidway.torretas.add(torreta4);
        vida = 1000;
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }

    public void updatePosition() {
        this.setPosition(position.x, position.y + 2/*0.50*/);
        mainTurret.setPosition(mainTurret.position.x, mainTurret.position.y + 2/*0.50*/);
        torreta1.setPosition(torreta1.position.x, torreta1.position.y + 2/*0.50*/);
        torreta2.setPosition(torreta2.position.x, torreta2.position.y + 2/*0.50*/);
        torreta3.setPosition(torreta3.position.x, torreta3.position.y + 2/*0.50*/);
        torreta4.setPosition(torreta4.position.x, torreta4.position.y + 2/*0.50*/);
    }

    @Override
    public void mover(double delta, Keyboard keyboard) {}

    @Override
    public double getCoordenadas() {
        return 0;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
        if (vida <= 0) {
            BattleOfMidway.torretas.clear();
        }
    }
}
