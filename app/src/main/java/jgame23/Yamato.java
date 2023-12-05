package jgame23;

import com.entropyinteractive.Keyboard;

import java.awt.*;

public class Yamato extends Enemigo{
    private Torreta torreta1, torreta2, torreta3, torreta4, torreta5, torreta6, torreta7;
    private int vida, velX = 1;
    double velY = 0.5;
    private MegaTorreta mainTurret;

    public Yamato(String filename, int x, int y) {
        super(filename);
        this.setPosition(x, y);
        mainTurret = new MegaTorreta("imagenes/0.png", (int)this.x + 500, (int)this.y - 1000);
        torreta1 = new Torreta("imagenes/5.png", (int)this.x + 650, (int)this.y - 1000);
        torreta1.nombre = "torreta1";
        torreta2 = new Torreta("imagenes/5.png", (int)this.x + 650, (int)this.y - 800);
        torreta2.nombre = "torreta2";
        torreta3 = new Torreta("imagenes/13.png", (int)this.x + 350, (int)this.y - 800);
        torreta3.nombre = "torreta3";
        torreta4 = new Torreta("imagenes/13.png", (int)this.x + 350, (int)this.y - 1000);
        torreta5 = new Torreta("imagenes/13.png", (int)this.x + 500, (int)this.y - 250);
        torreta6 = new Torreta("imagenes/13.png", (int)this.x + 500, (int)this.y - 400);
        torreta7 = new Torreta("imagenes/13.png", (int)this.x + 500, (int)this.y - 1100);
        torreta4.nombre = "torreta4";
        //BattleOfMidway.torretas.add(mainTurret);
        BattleOfMidway.addTorreta(torreta1);
        BattleOfMidway.addTorreta(torreta2);
        BattleOfMidway.addTorreta(torreta3);
        BattleOfMidway.addTorreta(torreta4);
        BattleOfMidway.addTorreta(torreta5);
        BattleOfMidway.addTorreta(torreta6);
        BattleOfMidway.addTorreta(torreta7);
        vida = 1000;
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        mainTurret.draw(g);
    }

    public void updatePosition() {
        this.setPosition(position.x, position.y + velY/*0.50*/);
        mainTurret.setPosition(mainTurret.position.x, mainTurret.position.y + velY/*0.50*/);
        torreta1.setPosition(torreta1.position.x, torreta1.position.y + velY/*0.50*/);
        torreta2.setPosition(torreta2.position.x, torreta2.position.y + velY/*0.50*/);
        torreta3.setPosition(torreta3.position.x, torreta3.position.y + velY/*0.50*/);
        torreta4.setPosition(torreta4.position.x, torreta4.position.y + velY/*0.50*/);
        torreta5.setPosition(torreta5.position.x, torreta5.position.y + velY/*0.50*/);
        torreta6.setPosition(torreta6.position.x, torreta6.position.y + velY/*0.50*/);
        torreta7.setPosition(torreta7.position.x, torreta7.position.y + velY/*0.50*/);
    }

    @Override
    public void mover(double delta, Keyboard keyboard) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getCoordenadas() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        if (BattleOfMidway.torretasHashtable.isEmpty()){
            this.vida = vida;
            if (vida <= 0) {
                BattleOfMidway.torretasHashtable.clear();
            }
        }else{
            this.vida = 1000;
        }
    }
}
