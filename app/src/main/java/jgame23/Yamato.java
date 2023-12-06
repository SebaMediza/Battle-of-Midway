package jgame23;

import com.entropyinteractive.Keyboard;

import java.awt.*;

public class Yamato extends Enemigo{
    private Torreta torreta1, torreta2, torreta3, torreta4, torreta5, torreta6, torreta7;
    private int vida;
    double velY = 0.5, velX = 0.25;
    private MegaTorreta mainTurret;
    private boolean right = true;

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
        vida = 500;
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
        if (right){
            this.position.x -=velX;
            mainTurret.setPosition(mainTurret.position.x -= velX, mainTurret.position.y);
            torreta1.setPosition(torreta1.position.x -= velX, torreta1.position.y);
            torreta2.setPosition(torreta2.position.x -= velX, torreta2.position.y);
            torreta3.setPosition(torreta3.position.x -= velX, torreta3.position.y);
            torreta4.setPosition(torreta4.position.x -= velX, torreta4.position.y);
            torreta5.setPosition(torreta5.position.x -= velX, torreta5.position.y);
            torreta6.setPosition(torreta6.position.x -= velX, torreta6.position.y);
            torreta7.setPosition(torreta7.position.x -= velX, torreta7.position.y);
            if (this.position.x < -430){
                right = false;
            }
        }else{
            this.position.x +=velX;
            mainTurret.setPosition(mainTurret.position.x += velX, mainTurret.position.y);
            torreta1.setPosition(torreta1.position.x += velX, torreta1.position.y);
            torreta2.setPosition(torreta2.position.x += velX, torreta2.position.y);
            torreta3.setPosition(torreta3.position.x += velX, torreta3.position.y);
            torreta4.setPosition(torreta4.position.x += velX, torreta4.position.y);
            torreta5.setPosition(torreta5.position.x += velX, torreta5.position.y);
            torreta6.setPosition(torreta6.position.x += velX, torreta6.position.y);
            torreta7.setPosition(torreta7.position.x += velX, torreta7.position.y);
            if (this.position.x > 215){
                right = true;
            }
        }
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

    public void setVida() {
        if (BattleOfMidway.torretasHashtable.isEmpty()){
            this.vida = 0;
        }
    }
}
