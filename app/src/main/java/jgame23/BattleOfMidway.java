package jgame23;

import com.entropyinteractive.*;  //jgame
import java.awt.*;
import java.awt.image.*;  //imagenes
import javax.imageio.*; //imagenes
import java.util.*;

public class BattleOfMidway extends JGame {
    private static final double VELOCIDAD_IMAGEN = 61, velocidadNuber = 200, velocidadBarco = 59;
    public static ArrayList<Municion> municionAmigaArrayList = new ArrayList<>();
//    public static Hashtable<Integer,Municion> municionAmigaArrayList = new Hashtable<>();
    public static ArrayList<Municion> municionEnemigaArrayList = new ArrayList<>();
    public static ArrayList<AvionEnemigo> avionEnemigoArrayList = new ArrayList<>();
    public static ArrayList<Misil> misilArrayList = new ArrayList<>();
    public static ArrayList<Power_up> powerUpArrayList = new ArrayList<>();
    public static ArrayList<AvionEnemigo> avionEnemigoBonusArrayList = new ArrayList<>();
    public static void addMunicionAmigaArrayList(Municion municion){
        municionAmigaArrayList.add(municion);
    }
//    public static void addMunicionAmigaArrayList(Municion municion){
//        int lastIndex = municionAmigaArrayList.size() + 1;
//        municionAmigaArrayList.put(lastIndex, municion);
//    }
    public static void addMunicionEnemigaArrayList(Municion municion){
        municionEnemigaArrayList.add(municion);
    }
    public static void addAvionEnemigoArrayList(AvionEnemigo avionEnemigo){
        avionEnemigoArrayList.add(avionEnemigo);
    }
    public static void addMisilArrayList(Misil misil){
        misilArrayList.add(misil);
    }
    public static void addPowerUpArrayList(Power_up powerUp){
        powerUpArrayList.add(powerUp);
    }
    public static void addAvionEnemigoBonusArrayList(AvionEnemigo avionEnemigo){
        avionEnemigoBonusArrayList.add(avionEnemigo);
    }
    //Ranking ranking = new Ranking();
    public static int finalScore = 0;
    BufferedImage img_fondo, imagenNubes, barquitos;
    private int offSetY, posicionNubesY, posicionBarcosY;
    BufferedImage kabom = null;
    Avion_p38 avionP38;
    Akato akato;
    private long time, lastTime, timePlane, lastTimePlane;
    int i, cantEnemigos = 100, temp = 10;

    public BattleOfMidway() {
        super("Battle Of Midway", 945, Toolkit.getDefaultToolkit().getScreenSize().height - 37);
        System.out.println(appProperties.stringPropertyNames());
    }

    public void gameStartup() {
        try {
            avionP38 = new Avion_p38("imagenes/avionp38.png");
            avionP38.setPosicion((double) getWidth() / 2, (double) getHeight() / 2);
            img_fondo = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/fondo3.png")));
            imagenNubes = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/nubes2.png")));
            barquitos = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/barcos.png")));
            kabom = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/explocion.gif")));
            akato = new Akato("imagenes/avionAkato.png");
            akato.setPosition(500, 500);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        offSetY = img_fondo.getHeight() - getHeight();
        posicionBarcosY = img_fondo.getHeight() * 2 - getHeight();
        posicionNubesY = img_fondo.getHeight() * 2;
        time = 0;
        lastTime = System.currentTimeMillis();
        timePlane = 0;
        lastTimePlane = System.currentTimeMillis();
        i=0;
    }

    public void gameUpdate(double delta) {
        Keyboard keyboard = this.getKeyboard();
        avionP38.mover(delta, keyboard);
        //MOVIMIENTO DEL FONDO
        offSetY -= VELOCIDAD_IMAGEN * delta;
        if(offSetY < 0){
            offSetY = img_fondo.getHeight() - getHeight();
        }
        posicionBarcosY -= velocidadBarco * delta;
        if(posicionBarcosY < 0){
            posicionBarcosY = img_fondo.getHeight() * 2 - getHeight();
        }
        posicionNubesY -= velocidadNuber * delta;
        if(posicionNubesY < 0){
            posicionNubesY = img_fondo.getHeight() * 2;
        }
        //MOVIMIENTO DE LAS BALAS
        for (Municion bala : municionAmigaArrayList){
            bala.setPosition(bala.getX(),bala.getY() - 25);
        }
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if (time > 5000 && powerUpArrayList.isEmpty()){
            timePlane += System.currentTimeMillis() - lastTimePlane;
            lastTimePlane = System.currentTimeMillis();
            for (;i<5 && timePlane > 500;i++) {
                addAvionEnemigoBonusArrayList(new AvionEnemigo("imagenes/enemigo_0.png"));
                timePlane = 0;
            }
            if (avionEnemigoBonusArrayList.isEmpty() && time != 0){
                addPowerUpArrayList(new Pow("imagenes/Pow.png"));
                i = 0;
                time = 0;
            }
        }

        if (time > 500 && avionEnemigoArrayList.size() < 9){
            addAvionEnemigoArrayList(new AvionEnemigo("imagenes/Enemigo.png",(10 + (avionEnemigoArrayList.size() * 100)),0));
            time = 0;
            temp--;
        }
        for (AvionEnemigo avionEnemigo : avionEnemigoArrayList){
            avionEnemigo.autoMover1();
        }
        ArrayList<Municion> toDeleteMunicionAmiga= new ArrayList<>();
        ArrayList<AvionEnemigo> toDeleteAvionEnemigo = new ArrayList<>();
        ArrayList<Municion> toDeleteMunicionEnemiga = new ArrayList<>();
//        ArrayList<Power_up> toDeletePowerUp = new ArrayList<>();
        ArrayList<AvionEnemigo> toDeleteBonusPlane = new ArrayList<>();

        int indexOfRemovalMunicionAmiga = -1;
        int indexOfRemovalAvionEnemigo = -1;
        //COLICION DE MUNICION AMIGA CON AVION ENEMIGO
        for (Municion municion : municionAmigaArrayList){
            for (AvionEnemigo avionEnemigo : avionEnemigoArrayList){
                if (DetectorColiciones.detectarColicionMunicionAmigaAvionEnemigo(municion, avionEnemigo)){
//                    toDeleteAvionEnemigo.add(avionEnemigo);
//                    toDeleteMunicionAmiga.add(municion);
                    indexOfRemovalMunicionAmiga = municionAmigaArrayList.indexOf(municion);
                    System.out.println(indexOfRemovalMunicionAmiga);
                    indexOfRemovalAvionEnemigo = avionEnemigoArrayList.indexOf(avionEnemigo);
                    System.out.println(indexOfRemovalAvionEnemigo);
                    cantEnemigos--;
                    System.out.println("ENEMIGOS RESTANTES: " + cantEnemigos);
                }
            }
        }
        for (Municion municion : municionEnemigaArrayList) {
            municion.setPosition(municion.getX(), municion.getY() + 5);
            if (DetectorColiciones.detectarColicionMunicionEnemigaP38(municion, avionP38)){
                toDeleteMunicionEnemiga.add(municion);
            }
        }

        for (AvionEnemigo avionEnemigo : avionEnemigoArrayList){
            if (DetectorColiciones.detectarColicionP38AvionEnemigo(avionP38, avionEnemigo)){
                toDeleteAvionEnemigo.add(avionEnemigo);
                cantEnemigos--;
                System.out.println("ENEMIGOS RESTANTES: " + cantEnemigos);
            }
        }
        for (AvionEnemigo avionEnemigo : avionEnemigoArrayList){
//            avionEnemigo.disparar();
        }
        //BONUS GENERADOS POR MATAR TODOS LOS AVIONCITOS

        for (AvionEnemigo avionEnemigo : avionEnemigoBonusArrayList){
            if(DetectorColiciones.detectarColicionesP38AvionBonus(avionP38, avionEnemigo)){
                toDeleteBonusPlane.add(avionEnemigo);
            }
        }
        for (Municion municionAmiga : municionAmigaArrayList) {
            for (AvionEnemigo avionEnemigo : avionEnemigoBonusArrayList){
                if (DetectorColiciones.detectarColicionMunicionAmigaAvionEnemigo(municionAmiga, avionEnemigo)){
                    toDeleteBonusPlane.add(avionEnemigo);
                    toDeleteMunicionAmiga.add(municionAmiga);
                }
            }
        }
//        akato.disparar();
//        for (Municion municion : toDeleteMunicionEnemiga){
//            municionEnemigaArrayList.remove(municion);
//        }
//        for (AvionEnemigo avionEnemigoInt : toDeleteAvionEnemigo){
//            avionEnemigoArrayList.remove(avionEnemigoInt);
//        }
//        for (Municion municionInt : toDeleteMunicionAmiga){
//            municionAmigaArrayList.remove(municionInt);
//        }
//        for (AvionEnemigo avionEnemigo : toDeleteBonusPlane){
//            avionEnemigoBonusArrayList.remove(avionEnemigo);
//        }
        if (indexOfRemovalMunicionAmiga != -1 && indexOfRemovalAvionEnemigo != -1) {
            avionEnemigoArrayList.remove(indexOfRemovalAvionEnemigo);
            municionAmigaArrayList.remove(indexOfRemovalMunicionAmiga);
        }
    }

    public void gameDraw(Graphics2D g) {
        g.drawImage(img_fondo, 0, -offSetY,null);// imagen de fondo
        //g.drawImage(barquitos, 0, -posicionBarcosY, null);
        g.drawImage(imagenNubes, 0, -posicionNubesY, null);// imagen nubes
        avionP38.draw(g);
        for (Municion bala : municionAmigaArrayList){
            bala.draw(g);
        }
        for (AvionEnemigo plane : avionEnemigoArrayList) {
            plane.draw(g);
        }
        for (Municion balaEnemiga : municionEnemigaArrayList){
            balaEnemiga.draw(g);
        }
        for (Power_up powerUp : powerUpArrayList){
            powerUp.draw(g);
        }
        for (AvionEnemigo avionEnemigo : avionEnemigoBonusArrayList){
            avionEnemigo.draw(g);
        }
        g.setColor(Color.black);
        g.drawString(String.valueOf(avionP38.getEnegia()), 480, 50);
        g.drawString(String.valueOf(finalScore),5,50);
        g.setColor(Color.white);
        g.drawString(String.valueOf(avionP38.getEnegia()),482,52);
        g.drawString(String.valueOf(finalScore),7,52);
        if (cantEnemigos == 0){
            akato.draw(g);
        }
    }
    public void gameShutdown() {
        Log.info(getClass().getSimpleName(), "Shutting down game");
    }

    public static void main(String[] args) {
        BattleOfMidway battleOfMidway = new BattleOfMidway();
        battleOfMidway.run(1.0/60.0);
    }
}