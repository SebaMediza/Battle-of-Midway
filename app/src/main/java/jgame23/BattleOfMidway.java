package jgame23;

import com.entropyinteractive.*; //jgame
import java.awt.*;
import java.awt.image.*; //imagenes
import javax.imageio.*; //imagenes
import java.util.*;

public class BattleOfMidway extends JGame {
    int patronAvionesEnemigoNuevo, patronAvionesEnemigoViejo = 0;
    public static int puntuacion = 0;
    private static final double VELOCIDAD_IMAGEN = 61, velocidadNuber = 200, velocidadBarco = 59;
    static int index = 1, indexAvionBonus = 1;
    public static ArrayList<Municion> municionAmigaArrayList = new ArrayList<>();
    public static ArrayList<Municion> municionEnemigaArrayList = new ArrayList<>();
    public static Hashtable<Integer, AvionEnemigo> avionEnemigoHashtable = new Hashtable<>();
    public static Hashtable<Integer, AvionEnemigo> avionEnemigoBonusHashtable = new Hashtable<>();
    public static ArrayList<Misil> misilArrayList = new ArrayList<>();
    public static ArrayList<Power_up> powerUpArrayList = new ArrayList<>();
    public static ArrayList<ArmaBonus> armaBonusArrayList = new ArrayList<>();
    public static ArrayList<AvionRefuerzo> refuerzo = new ArrayList<>();

    public static void addRefuerzoArrayList(AvionRefuerzo avionRefuerzo) {
        refuerzo.add(avionRefuerzo);
    }

    public static void addMunicionAmigaArrayList(Municion municion) {
        municionAmigaArrayList.add(municion);
    }

    public static void addMunicionEnemigaArrayList(Municion municion) {
        municionEnemigaArrayList.add(municion);
    }

    public static void addAvionEnemigoHashtable(AvionEnemigo avionEnemigo) {
        avionEnemigoHashtable.put(index, avionEnemigo);
        index++;
    }

    public static void addMisilArrayList(Misil misil) {
        misilArrayList.add(misil);
    }

    public static void addPowerUpArrayList(Power_up powerUp) {
        powerUpArrayList.add(powerUp);
    }

    public static void addAvionEnemigoBonusHashtable(AvionEnemigo avionEnemigo) {
        avionEnemigoBonusHashtable.put(indexAvionBonus, avionEnemigo);
        indexAvionBonus++;
    }

    // Ranking ranking = new Ranking();
    public static int finalScore = 0;
    private int offSetY, posicionNubesY, posicionBarcosY;
    BufferedImage img_fondo, imagenNubes, barquitos;
    BufferedImage kabom = null;
    Avion_p38 avionP38;
    Yamato yamato;
    private long timeForBonus, lastTimeForBonus, tiempoRefuerzo, tiempoRefuerzoTotal;
    int cantAvionBonus, cantEnemigosDerrotados;
    int indexOfRemovalAvionEnemigo, indexOfRemovalAvionEnemigoBonus;
    boolean isBossTime = false;
    int inScreenEnemies = 0, temp = 150;
    Thread hiloAviones;

    public BattleOfMidway() {
        super("Battle Of Midway", 945, Toolkit.getDefaultToolkit().getScreenSize().height - 37);
        // System.out.println(appProperties.stringPropertyNames());
    }

    public void gameStartup() {
        try {
            avionP38 = new Avion_p38("imagenes/avionp38.png");
            avionP38.setPosicion((double) getWidth() / 2, (double) getHeight() / 2);
            img_fondo = ImageIO.read(
                    Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/fondo3.png")));
            imagenNubes = ImageIO.read(
                    Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/nubes2.png")));
            // barquitos =
            // ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/barcos.png")));
            // kabom =
            // ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/explocion.gif")));
            yamato = new Yamato("imagenes/Proyecto nuevo.png");
            yamato.setPosition(0, -1900);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        offSetY = img_fondo.getHeight() - getHeight();
        posicionBarcosY = img_fondo.getHeight() * 2 - getHeight();
        posicionNubesY = img_fondo.getHeight() * 2;
        cantAvionBonus = 0;
        timeForBonus = 0;
        lastTimeForBonus = System.currentTimeMillis();
    }

    public void gameUpdate(double delta) {
        Keyboard keyboard = this.getKeyboard();
        avionP38.mover(delta, keyboard);
        for (AvionRefuerzo avionRefuerzo : refuerzo) {
            avionRefuerzo.mover(delta, keyboard);
        }
        // MOVIMIENTO DEL FONDO
        offSetY -= (int) (VELOCIDAD_IMAGEN * delta);
        if (offSetY < 0) {
            offSetY = img_fondo.getHeight() - getHeight();
        }
        posicionBarcosY -= (int) (velocidadBarco * delta);
        if (posicionBarcosY < 0) {
            posicionBarcosY = img_fondo.getHeight() * 2 - getHeight();
        }
        posicionNubesY -= (int) (velocidadNuber * delta);
        if (posicionNubesY < 0) {
            posicionNubesY = img_fondo.getHeight() * 2;
        }
        // MOVIMIENTO DE LAS BALAS
        for (Municion bala : municionAmigaArrayList) {
            bala.setPosition(bala.getX(), bala.getY() - 25);
        }
        timeForBonus += System.currentTimeMillis() - lastTimeForBonus;
        lastTimeForBonus = System.currentTimeMillis();

        if (!isBossTime && inScreenEnemies == 0
                && !hiloPatron1.isAlive() && !hiloPatron2.isAlive()
                && !hiloPatron3.isAlive() && !hiloPatron4.isAlive() &&
                !hiloPatron5.isAlive()) {
            Random random = new Random();
            patronAvionesEnemigoNuevo = random.nextInt(5) + 1;
            while (patronAvionesEnemigoNuevo == patronAvionesEnemigoViejo) {
                patronAvionesEnemigoNuevo = random.nextInt(5) + 1;
            }
            patronAvionesEnemigoViejo = patronAvionesEnemigoNuevo;
            masAviones(patronAvionesEnemigoNuevo);
        }
        movimiento(patronAvionesEnemigoNuevo);
        /*
         * if (timeForBonus > 1500 && avionEnemigoBonusHashtable.isEmpty() &&
         * powerUpArrayList.isEmpty() && cantAvionBonus == 0) {
         * for (int i = 0; i < 5; i++) {
         * addAvionEnemigoBonusHashtable(new AvionEnemigo("imagenes/avionEnemigo.png",
         * (0), ((i + 10) * 10)));
         * }
         * 
         * // avionesBonus.start();
         * }
         */
        if ((powerUpArrayList.isEmpty() && armaBonusArrayList.isEmpty()) && cantAvionBonus == 0) {
            // powerUp();
            armaUp();
        }
        ArrayList<Municion> toDeleteMunicionAmiga = new ArrayList<>();
        ArrayList<Municion> toDeleteMunicionEnemiga = new ArrayList<>();
        ArrayList<Misil> toDeleteMisilEnemigo = new ArrayList<>();
        ArrayList<Power_up> toDeletePowerUp = new ArrayList<>();
        ArrayList<ArmaBonus> toDeleteArmaBonus = new ArrayList<>();
        ArrayList<AvionRefuerzo> toDeleteRefuerzo = new ArrayList<>();

        // COLICION DE MUNICION AMIGA CON AVION ENEMIGO
        for (Municion municion : municionAmigaArrayList) {
            for (Map.Entry<Integer, AvionEnemigo> avionEnemigoEntry : avionEnemigoHashtable.entrySet()) {
                if (DetectorColiciones.detectarColicionMunicionAmigaAvionEnemigo(municion,
                        avionEnemigoEntry.getValue())) {
                    indexOfRemovalAvionEnemigo = avionEnemigoEntry.getKey();
                    toDeleteMunicionAmiga.add(municion);
                    cantEnemigosDerrotados++;
                    inScreenEnemies--;
                    puntuacion = puntuacion + 500;
                }
            }
            if (DetectorColiciones.detectarColicionMunicionAmigaBordePantalla(municion)) {
                toDeleteMunicionAmiga.add(municion);
            }
            avionEnemigoHashtable.remove(indexOfRemovalAvionEnemigo);
            indexOfRemovalAvionEnemigo = 0;
        }
        for (Map.Entry<Integer, AvionEnemigo> avionEnemigoEntry : avionEnemigoHashtable.entrySet()) {
            avionEnemigoEntry.getValue().disparar();
        }
        for (Municion municion : municionEnemigaArrayList) {
            municion.setPosition(municion.getX(), municion.getY() + 5);
            if (DetectorColiciones.detectarColicionMunicionEnemigaP38(municion, avionP38)) {
                toDeleteMunicionEnemiga.add(municion);
            }
        }
        for (AvionRefuerzo avionRefuerzo : refuerzo) {
            for (Municion municion : municionEnemigaArrayList) {
                if (DetectorColiciones.detectarColicionesRefuerzoBalasEnemigas(municion, avionRefuerzo)) {
                    toDeleteMunicionEnemiga.add(municion);
                    avionRefuerzo.impacto();
                    if (avionRefuerzo.getVida() <= 0) {
                        toDeleteRefuerzo.add(avionRefuerzo);
                    }
                }
            }
        }
        for (Misil misil : misilArrayList) {
            misil.updatePosition(avionP38.getX(), avionP38.getY());
            for (Municion municion : municionAmigaArrayList) {
                if (DetectorColiciones.detectarColicionMunicionAmigaMisilEnemigo(municion, misil)) {
                    toDeleteMunicionEnemiga.add(municion);
                    toDeleteMisilEnemigo.add(misil);
                    puntuacion = puntuacion + 100;
                }
            }
            if (DetectorColiciones.detectarColicionP38MisilEnemigo(avionP38, misil)) {
                toDeleteMisilEnemigo.add(misil);
            }
        }
        if (cantEnemigosDerrotados > 1500 && inScreenEnemies == 0) {
            isBossTime = true;
        }
        // BONUS GENERADOS POR MATAR TODOS LOS AVIONCITOS

        for (Map.Entry<Integer, AvionEnemigo> avionEnemigoEntry : avionEnemigoBonusHashtable.entrySet()) {
            avionEnemigoEntry.getValue().autoMoverAvionBonus();
            if (DetectorColiciones.detectarColicionesP38AvionBonus(avionP38, avionEnemigoEntry.getValue())) {
                indexOfRemovalAvionEnemigo = avionEnemigoEntry.getKey();
            }
        }
        avionEnemigoBonusHashtable.remove(indexOfRemovalAvionEnemigo);
        indexOfRemovalAvionEnemigo = 0;
        for (Municion municionAmiga : municionAmigaArrayList) {
            for (Map.Entry<Integer, AvionEnemigo> avionEnemigoEntry : avionEnemigoBonusHashtable.entrySet()) {
                if (DetectorColiciones.detectarColicionMunicionAmigaAvionEnemigo(municionAmiga,
                        avionEnemigoEntry.getValue())) {
                    cantAvionBonus++;
                    indexOfRemovalAvionEnemigo = avionEnemigoEntry.getKey();
                    toDeleteMunicionAmiga.add(municionAmiga);
                }
            }
            for (Power_up power_up : powerUpArrayList) {
                if (DetectorColiciones.detectarColicionesPowerUpBalas(municionAmiga, power_up)) {
                    toDeleteMunicionAmiga.add(municionAmiga);
                    toDeletePowerUp.add(power_up);
                }
            }
        }
        if (avionEnemigoBonusHashtable.isEmpty() && powerUpArrayList.isEmpty() && cantAvionBonus == 5) {
            powerUp();
            timeForBonus = 0;
        }

        for (Power_up power_up : powerUpArrayList) {
            if (DetectorColiciones.detectarColicionesPowerUp(avionP38, power_up)) {
                power_up.activar(avionP38);
                toDeletePowerUp.add(power_up);
            }
        }
        for (ArmaBonus armaBonus : armaBonusArrayList) {
            if (DetectorColiciones.detectarColicionesArmaBonus(avionP38, armaBonus)) {
                armaBonus.activar(avionP38);
                toDeleteArmaBonus.add(armaBonus);
            }
        }
        avionEnemigoBonusHashtable.remove(indexOfRemovalAvionEnemigo);
        for (Municion municion : toDeleteMunicionEnemiga) {
            municionEnemigaArrayList.remove(municion);
        }
        for (Municion municionInt : toDeleteMunicionAmiga) {
            municionAmigaArrayList.remove(municionInt);
        }
        for (Misil misil : toDeleteMisilEnemigo) {
            misilArrayList.remove(misil);
        }
        for (Power_up power_up : toDeletePowerUp) {
            powerUpArrayList.remove(power_up);
            powerUp();
        }
        for (ArmaBonus armaBonus : toDeleteArmaBonus) {
            armaBonusArrayList.remove(armaBonus);
            armaUp();
        }
        for (AvionRefuerzo avionRefuerzo : toDeleteRefuerzo) {
            refuerzo.remove(avionRefuerzo);
        }
        yamato(isBossTime);
        if (avionP38.getEnegia() <= 0) {
            finalScore = puntuacion;
            this.stop();
            // ranking.setVisible(true);
        }
    }

    public void gameDraw(Graphics2D g) {
        g.drawImage(img_fondo, 0, -offSetY, null);// imagen de fondo
        g.drawImage(imagenNubes, 0, -posicionNubesY, null);// imagen nubes
        if (isBossTime) {
            yamato.draw(g);
        }
        for (Municion bala : municionAmigaArrayList) {
            bala.draw(g);
        }
        for (Municion balaEnemiga : municionEnemigaArrayList) {
            balaEnemiga.draw(g);
        }
        for (Map.Entry<Integer, AvionEnemigo> avionEnemigoEntry : avionEnemigoHashtable.entrySet()) {
            avionEnemigoEntry.getValue().draw(g);
        }
        for (Power_up powerUp : powerUpArrayList) {
            powerUp.setPosition(powerUp.getX(), powerUp.getY() + 0.25);
            powerUp.draw(g);
        }
        for (ArmaBonus armaBonus : armaBonusArrayList) {
            armaBonus.setPosition(armaBonus.getX(), armaBonus.getY() + 0.25);
            armaBonus.draw(g);
        }
        for (Map.Entry<Integer, AvionEnemigo> avionEnemigoEntry : avionEnemigoBonusHashtable.entrySet()) {
            avionEnemigoEntry.getValue().draw(g);
        }
        for (Misil misil : misilArrayList) {
            misil.draw(g);
        }
        for (AvionRefuerzo avionRefuerzo : refuerzo) {
            avionRefuerzo.draw(g);
        }
        g.setColor(Color.black);
        g.drawString(String.valueOf(avionP38.getEnegia()), 480, 50);
        g.drawString(String.valueOf(puntuacion), 5, 50);
        g.setColor(Color.white);
        g.drawString(String.valueOf(avionP38.getEnegia()), 482, 52);
        g.drawString(String.valueOf(puntuacion), 7, 52);
        avionP38.draw(g);
    }

    public void yamato(Boolean isBossTime) {
        if (isBossTime) {
            yamato.updatePosition();
            yamato.dispararMisil(yamato.getX() + 500, yamato.getY() + 900);
        }
    }

    public void gameShutdown() {
        Log.info(getClass().getSimpleName(), "Shutting down game");
    }

    public static void main(String[] args) {
        BattleOfMidway battleOfMidway = new BattleOfMidway();
        battleOfMidway.run(1.0 / 60.0);

    }

    private void masAviones(int patron) {
        switch (patron) {

            case 1:
                hiloPatron1.start();
                break;
            case 2:
                hiloPatron2.start();
                break;
            case 3:
                hiloPatron3.start();
                break;
            case 4:
                hiloPatron4.start();
                break;
            case 5:
                hiloPatron5.start();
                break;

            /*
             * case 1:
             * for (int i = 0; i < 5; i++) {
             * addAvionEnemigoHashtable(
             * new AvionEnemigo("imagenes/Enemigo.png", ((inScreenEnemies + 5) * 60), 15));
             * inScreenEnemies++;
             * }
             * break;
             * case 2:
             * for (int i = 0; i < 10; i++) {
             * addAvionEnemigoHashtable(
             * new AvionEnemigo("imagenes/enemigo_195.png", 0, (inScreenEnemies + 5) * 60));
             * inScreenEnemies++;
             * }
             * break;
             * case 3:
             * for (int i = 0; i < 5; i++) {
             * addAvionEnemigoHashtable(
             * new AvionEnemigo("imagenes/Enemigo.png", (inScreenEnemies + 10) * 40, i *
             * 10));
             * inScreenEnemies++;
             * }
             * break;
             * case 4:
             * for (int i = 0; i < 5; i++) {
             * addAvionEnemigoHashtable(
             * new AvionEnemigo("imagenes/enemigo_315.png", inScreenEnemies * 30,
             * inScreenEnemies * 30));
             * inScreenEnemies++;
             * }
             * break;
             * case 5:
             * for (int i = 0; i < 5; i++) {
             * addAvionEnemigoHashtable(new AvionEnemigo("imagenes/enemigo_195.png",
             * (945 - (inScreenEnemies * 30)), inScreenEnemies * 30));
             * inScreenEnemies++;
             * }
             * break;
             */

        }
    }

    Runnable patron1 = () -> {
        try {
            for (int i = 0; i < 5; i++) {
                // Thread.sleep(500);
                addAvionEnemigoHashtable(
                        new AvionEnemigo("imagenes/Enemigo.png", ((i + 5) * 60), 15));
                inScreenEnemies++;
                System.out.println(inScreenEnemies);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    };

    Runnable patron2 = () -> {
        try {
            for (int i = 0; i < 5; i++) {
                // Thread.sleep(500);
                addAvionEnemigoHashtable(
                        new AvionEnemigo("imagenes/enemigo_195.png", 0, (i + 5) * 60));
                inScreenEnemies++;
            }
        } catch (Exception e) {
        }
    };
    Runnable patron3 = () -> {
        try {
            for (int i = 0; i < 5; i++) {
                // Thread.sleep(500);
                addAvionEnemigoHashtable(
                        new AvionEnemigo("imagenes/Enemigo.png", (i + 10) * 40, i * 10));
                inScreenEnemies++;
            }
        } catch (Exception e) {
        }
    };
    Runnable patron4 = () -> {
        try {
            for (int i = 0; i < 5; i++) {
                // Thread.sleep(500);
                addAvionEnemigoHashtable(
                        new AvionEnemigo("imagenes/enemigo_315.png", i * 30, i * 30));
                inScreenEnemies++;
            }
        } catch (Exception e) {
        }
    };
    Runnable patron5 = () -> {
        try {
            for (int i = 0; i < 5; i++) {
                // Thread.sleep(500);
                addAvionEnemigoHashtable(new AvionEnemigo("imagenes/enemigo_195.png",
                        (945 - (i * 30)), i * 30));
                inScreenEnemies++;
            }
        } catch (Exception e) {
        }
    };
    Thread hiloPatron1 = new Thread(patron1);
    Thread hiloPatron2 = new Thread(patron2);
    Thread hiloPatron3 = new Thread(patron3);
    Thread hiloPatron4 = new Thread(patron4);
    Thread hiloPatron5 = new Thread(patron5);

    private void movimiento(int patron) {
        switch (patron) {
            case 1:
                for (Map.Entry<Integer, AvionEnemigo> avionEnemigoEntry : avionEnemigoHashtable.entrySet()) {
                    avionEnemigoEntry.getValue().autoMover1();
                }
                break;
            case 2:
                for (Map.Entry<Integer, AvionEnemigo> avionEnemigoEntry : avionEnemigoHashtable.entrySet()) {
                    avionEnemigoEntry.getValue().autoMover2();
                }
                break;
            case 3:
                for (Map.Entry<Integer, AvionEnemigo> avionEnemigoEntry : avionEnemigoHashtable.entrySet()) {
                    avionEnemigoEntry.getValue().autoMover3();
                }
                break;
            case 4:
                for (Map.Entry<Integer, AvionEnemigo> avionEnemigoEntry : avionEnemigoHashtable.entrySet()) {
                    avionEnemigoEntry.getValue().autoMover4();
                }
                break;
            case 5:
                for (Map.Entry<Integer, AvionEnemigo> avionEnemigoEntry : avionEnemigoHashtable.entrySet()) {
                    avionEnemigoEntry.getValue().autoMover5();
                }
                break;
        }
    }

    private void powerUp() {
        Random random = new Random();
        int randomPowerUp = random.nextInt(4) + 1;
        int x = random.nextInt((895 - 0) + 1) + 0;
        switch (randomPowerUp) {
            case 1:
                addPowerUpArrayList(new Auto("imagenes/niIdea.png", x, 50));
                break;
            case 2:
                addPowerUpArrayList(new Pow("imagenes/Pow.png", x, 50));
                break;
            case 3:
                addPowerUpArrayList(new Super_shell("imagenes/SuperShell.png", x, 50));
                break;
            case 4:
                addPowerUpArrayList(new EstrellaNinja("imagenes/Estrella.png", x, 50));
        }
    }

    private void armaUp() {
        Random random = new Random();
        int randomPowerUp = 2;// random.nextInt(4) + 1;
        int x = random.nextInt((895 - 0) + 1) + 0;
        switch (randomPowerUp) {
            case 1:
                armaBonusArrayList.add(new Escopeta("imagenes/escopeta.png", x, 50));
                break;
            case 2:
                armaBonusArrayList.add(new Ametralladora("imagenes/ametralladora.png", x, 50));
                break;
            case 3:
                armaBonusArrayList.add(new Laser("imagenes/laser.png", x, 50));
                break;
            case 4:
                armaBonusArrayList.add(new Refuerzo("imagenes/Refuerzo.png", 440, 500));
                break;
        }
    }

    Runnable avionBonus = () -> {
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(500);
                addAvionEnemigoBonusHashtable(new AvionEnemigo("imagenes/avionEnemigo.png", (0), ((i + 10) * 10)));
            }
        } catch (Exception e) {
        }
    };
    Thread avionesBonus = new Thread(avionBonus);
}