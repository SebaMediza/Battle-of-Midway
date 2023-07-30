package jgame23;

import com.entropyinteractive.*;  //jgame
import java.awt.*;
import java.awt.image.*;  //imagenes
import javax.imageio.*; //imagenes
import java.util.*;

public class BattleOfMidway extends JGame{
    private static final double VELOCIDAD_IMAGEN = 61, velocidadNuber = 200, velocidadBarco = 59;
    static int index = 1;
    public static ArrayList<Municion> municionAmigaArrayList = new ArrayList<>();
    public static ArrayList<Municion> municionEnemigaArrayList = new ArrayList<>();
    public static Hashtable<Integer, AvionEnemigo> avionEnemigoHashtable = new Hashtable<>();
    public static ArrayList<Misil> misilArrayList = new ArrayList<>();
    public static ArrayList<Power_up> powerUpArrayList = new ArrayList<>();
    public static ArrayList<AvionEnemigo> avionEnemigoBonusArrayList = new ArrayList<>();
    public static void addMunicionAmigaArrayList(Municion municion){
        municionAmigaArrayList.add(municion);
    }
    public static void addMunicionEnemigaArrayList(Municion municion){
        municionEnemigaArrayList.add(municion);
    }
    public static void addAvionEnemigoHashtable(AvionEnemigo avionEnemigo){
        avionEnemigoHashtable.put(index, avionEnemigo);
        index++;
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
    Yamato yamato;
    private long time, lastTime, timePlane, lastTimePlane, timeForBonus, lastTimeForBonus;
    int cantAvionBonus, cantEnemigos = 16;
    int indexOfRemovalAvionEnemigo;
    boolean isBossTime = false;
    Random random = new Random();
    int patten = 1, inScreenEnemies = 0, temp = 100;
    Thread thread;

    public BattleOfMidway() {
        super("Battle Of Midway", 945, Toolkit.getDefaultToolkit().getScreenSize().height - 37);
//        System.out.println(appProperties.stringPropertyNames());
    }

    public void gameStartup() {
        try {
            avionP38 = new Avion_p38("imagenes/avionp38.png");
            avionP38.setPosicion((double) getWidth() / 2, (double) getHeight() / 2);
            img_fondo = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/fondo3.png")));
            imagenNubes = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/nubes2.png")));
            barquitos = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/barcos.png")));
            kabom = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/explocion.gif")));
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
        cantAvionBonus = 0;
        timeForBonus = 0;
        lastTimeForBonus = System.currentTimeMillis();

    }

    public void gameUpdate(double delta) {
        Keyboard keyboard = this.getKeyboard();
        avionP38.mover(delta, keyboard);
        //MOVIMIENTO DEL FONDO
        offSetY -= (int) (VELOCIDAD_IMAGEN * delta);
        if(offSetY < 0){
            offSetY = img_fondo.getHeight() - getHeight();
        }
        posicionBarcosY -= (int) (velocidadBarco * delta);
        if(posicionBarcosY < 0){
            posicionBarcosY = img_fondo.getHeight() * 2 - getHeight();
        }
        posicionNubesY -= (int) (velocidadNuber * delta);
        if(posicionNubesY < 0){
            posicionNubesY = img_fondo.getHeight() * 2;
        }
        //MOVIMIENTO DE LAS BALAS
        for (Municion bala : municionAmigaArrayList){
            bala.setPosition(bala.getX(),bala.getY() - 25);
        }
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        timeForBonus += System.currentTimeMillis() - lastTimeForBonus;
        lastTimeForBonus = System.currentTimeMillis();

        if (timeForBonus > 5000 && powerUpArrayList.isEmpty()){
            timePlane += System.currentTimeMillis() - lastTimePlane;
            lastTimePlane = System.currentTimeMillis();
            for (; cantAvionBonus <5 && timePlane > 500; cantAvionBonus++) {

                timePlane = 0;
            }
            if (avionEnemigoBonusArrayList.isEmpty() && time != 0){
                addPowerUpArrayList(new Pow("imagenes/Pow.png"));
                cantAvionBonus = 0;
                time = 0;
            }
        }
            if (!isBossTime && inScreenEnemies != 10 && time > 1000){
                addAvionEnemigoHashtable(new AvionEnemigo("imagenes/enemigo_270.png", temp, 0));
                temp = temp + 50;
                time = 0;
                inScreenEnemies++;
            }
            patten = random.nextInt(4) + 1;

        for (Map.Entry<Integer, AvionEnemigo> avionEnemigoEntry : avionEnemigoHashtable.entrySet()){
           avionEnemigoEntry.getValue().temp();
        }
        ArrayList<Municion> toDeleteMunicionAmiga= new ArrayList<>();
        ArrayList<Municion> toDeleteMunicionEnemiga = new ArrayList<>();
//        ArrayList<Power_up> toDeletePowerUp = new ArrayList<>();
        ArrayList<AvionEnemigo> toDeleteBonusPlane = new ArrayList<>();
        ArrayList<Misil> toDeleteMisilEnemigo = new ArrayList<>();


        //COLICION DE MUNICION AMIGA CON AVION ENEMIGO
        for (Municion municion : municionAmigaArrayList){
            for (Map.Entry<Integer, AvionEnemigo> avionEnemigoEntry : avionEnemigoHashtable.entrySet()){
                if (DetectorColiciones.detectarColicionMunicionAmigaAvionEnemigo(municion,avionEnemigoEntry.getValue())){
                    indexOfRemovalAvionEnemigo = avionEnemigoEntry.getKey();
                    toDeleteMunicionAmiga.add(municion);
                    cantEnemigos--;
                    inScreenEnemies--;
                    System.out.println(cantEnemigos);
                }
            }
            if (DetectorColiciones.detectarColicionMunicionAmigaBordePantalla(municion)){
                toDeleteMunicionAmiga.add(municion);
//                System.out.println("yes");
            }
            avionEnemigoHashtable.remove(indexOfRemovalAvionEnemigo);
            indexOfRemovalAvionEnemigo = 0;
        }
        for (Map.Entry<Integer, AvionEnemigo> avionEnemigoEntry : avionEnemigoHashtable.entrySet()){
//            avionEnemigoEntry.getValue().disparar();
//            avionEnemigoEntry.getValue().disparaMisil();
        }
        for (Municion municion : municionEnemigaArrayList) {
            municion.setPosition(municion.getX(), municion.getY() + 5);
            if (DetectorColiciones.detectarColicionMunicionEnemigaP38(municion, avionP38)){
                toDeleteMunicionEnemiga.add(municion);
            }
        }
        for (Misil misil : misilArrayList){
            misil.updatePosition(avionP38.getX(), avionP38.getY());
            for (Municion municion : municionAmigaArrayList){
                if (DetectorColiciones.detectarColicionMunicionAmigaMisilEnemigo(municion, misil)){
                    toDeleteMunicionEnemiga.add(municion);
                    toDeleteMisilEnemigo.add(misil);
                }
            }
            if (DetectorColiciones.detectarColicionP38MisilEnemigo(avionP38,misil)){
                toDeleteMisilEnemigo.add(misil);
            }
        }
        if(cantEnemigos == 15){
//            avionEnemigoHashtable.clear();
            yamato = new Yamato("imagenes/buqueYamato.png");
            yamato.setPosition(470, ((double) Toolkit.getDefaultToolkit().getScreenSize().height /2));
            isBossTime = true;
        }
        //BONUS GENERADOS POR MATAR TODOS LOS AVIONCITOS

        for (AvionEnemigo avionEnemigo : avionEnemigoBonusArrayList){
            avionEnemigo.autoMoverAvionBonus();
            if(DetectorColiciones.detectarColicionesP38AvionBonus(avionP38, avionEnemigo)){
                toDeleteBonusPlane.add(avionEnemigo);
            }
        }
//        for (Municion municionAmiga : municionAmigaArrayList) {
//            for (AvionEnemigo avionEnemigo : avionEnemigoBonusArrayList){
//                if (DetectorColiciones.detectarColicionMunicionAmigaAvionEnemigo(municionAmiga, avionEnemigo)){
//                    toDeleteBonusPlane.add(avionEnemigo);
//                    toDeleteMunicionAmiga.add(municionAmiga);
//                }
//            }
//        }
//        akato.disparar();
        for (Municion municion : toDeleteMunicionEnemiga){
            municionEnemigaArrayList.remove(municion);
        }
//        for (AvionEnemigo avionEnemigoInt : toDeleteAvionEnemigo){
//            avionEnemigoArrayList.remove(avionEnemigoInt);
//        }
        for (Municion municionInt : toDeleteMunicionAmiga){
            municionAmigaArrayList.remove(municionInt);
        }
//        for (AvionEnemigo avionEnemigo : toDeleteBonusPlane){
//            avionEnemigoBonusArrayList.remove(avionEnemigo);
//        }
        for (Misil misil : toDeleteMisilEnemigo){
            misilArrayList.remove(misil);
        }
    }

    public void gameDraw(Graphics2D g) {
        g.drawImage(img_fondo, 0, -offSetY,null);// imagen de fondo
        g.drawImage(barquitos, 0, -posicionBarcosY, null);
        g.drawImage(imagenNubes, 0, -posicionNubesY, null);// imagen nubes
        avionP38.draw(g);
        for (Municion bala : municionAmigaArrayList){
            bala.draw(g);
        }
        for (Municion balaEnemiga : municionEnemigaArrayList){
            balaEnemiga.draw(g);
        }
        for (Map.Entry<Integer, AvionEnemigo> avionEnemigoEntry : avionEnemigoHashtable.entrySet()){
            avionEnemigoEntry.getValue().draw(g);
        }
        for (Power_up powerUp : powerUpArrayList){
            powerUp.draw(g);
        }
        for (AvionEnemigo avionEnemigo : avionEnemigoBonusArrayList){
            avionEnemigo.draw(g);
        }
        for (Misil misil : misilArrayList){
            misil.draw(g);
        }
        g.setColor(Color.black);
        g.drawString(String.valueOf(avionP38.getEnegia()), 480, 50);
        g.drawString(String.valueOf(finalScore),5,50);
        g.setColor(Color.white);
        g.drawString(String.valueOf(avionP38.getEnegia()),482,52);
        g.drawString(String.valueOf(finalScore),7,52);
        if (isBossTime){
            yamato.draw(g);
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