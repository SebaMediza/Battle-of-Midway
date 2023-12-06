package jgame23;

import com.entropyinteractive.*; //jgame
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.*; //imagenes
import java.io.IOException;
import java.time.LocalDate;

import javax.swing.*;
import javax.imageio.*; //imagenes
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.util.*;

/* 
 * DUDAS PARA LA CLASE
 * - ¿Como hacer la configuración? SQLite
 * - Orientación para encarar los ataque especiales
 * - Corregir el disparo del P38 - flag
 * - Corregir el disparo y la colición de las torretas del Yamato - vector
 * - Barra de vida - imagenes + numero de vida
 * - Misiles - arregaldo
 * - hashtable para las torreras
 * - arreglar UML
 */

public class BattleOfMidway extends JGame implements ActionListener {
    private Ranking ranking;
    int patronAvionesEnemigoNuevo, patronAvionesEnemigoViejo = 0;
    public static int puntuacion = 0;
    private static final double VELOCIDAD_IMAGEN = 61, velocidadNuber = 200, velocidadBarco = 59;
    public static int finalScore = 0;
    private int offSetY, posicionNubesY, posicionBarcosY;
    private BufferedImage img_fondo, imagenNubes, vida;
    private Avion_p38 avionP38;
    private Yamato yamato;
    private long timeForBonus, lastTimeForBonus;
    private int cantAvionBonus, cantEnemigosDerrotados = 0;
    private int indexOfRemovalAvionEnemigo;
    private boolean isDone = false, isBossTime = false, secondFase = false, rayo = false, tsumani = false;
    private int inScreenEnemies = 0;
    static int index = 1, indexAvionBonus = 1, indexMisil = 1, indexBarco = 1, indexTorreta = 1, balaYamato = 1,
            indexBalaEmemiga = 1;
    private Clip MAIN_THEME;

    public static ArrayList<Municion> municionAmigaArrayList = new ArrayList<>();
    public static Hashtable<Integer, Municion> municionEnemigaArrayList = new Hashtable<>();
    public static Hashtable<Integer, AvionEnemigo> avionEnemigoHashtable = new Hashtable<>();
    public static Hashtable<Integer, AvionEnemigo> avionEnemigoBonusHashtable = new Hashtable<>();
    public static Hashtable<Integer, BarcoEnemigo> barcoEnemigo = new Hashtable<>();
    public static Hashtable<Integer, Misil> misilArrayList = new Hashtable<>();
    public static ArrayList<Power_up> powerUpArrayList = new ArrayList<>();
    public static ArrayList<ArmaBonus> armaBonusArrayList = new ArrayList<>();
    public static ArrayList<AvionRefuerzo> refuerzo = new ArrayList<>();
    public static Hashtable<Integer, Torreta> torretasHashtable = new Hashtable<>();
    public static Hashtable<Integer, MunicionPesada> balasYamato = new Hashtable<>();

    private ArrayList<Municion> toDeleteMunicionAmiga = new ArrayList<>();
    private ArrayList<Municion> toDeleteMunicionEnemiga = new ArrayList<>();
    private ArrayList<Misil> toDeleteMisilEnemigo = new ArrayList<>();
    private ArrayList<Power_up> toDeletePowerUp = new ArrayList<>();
    private ArrayList<Power_up> toDeleteActivatedPowerUp = new ArrayList<>();
    private ArrayList<ArmaBonus> toDeleteArmaBonus = new ArrayList<>();
    private ArrayList<ArmaBonus> toDeleteActivatedArmaBonus = new ArrayList<>();
    private ArrayList<AvionRefuerzo> toDeleteRefuerzo = new ArrayList<>();
    private ArrayList<Torreta> toDeleteTorreta = new ArrayList<>();

    public static void addAvionEnemigoHashtable(AvionEnemigo avionEnemigo) {
        avionEnemigoHashtable.put(index, avionEnemigo);
        index++;
    }

    public static void addMisisl(Misil misil) {
        misilArrayList.put(indexMisil, misil);
        indexMisil++;
    }

    public static void addTorreta(Torreta torreta) {
        torretasHashtable.put(indexTorreta, torreta);
        indexTorreta++;
    }

    public static void addBalaYamato(MunicionPesada municion) {
        balasYamato.put(balaYamato, municion);
        balaYamato++;
    }

    public static void addRefuerzoArrayList(AvionRefuerzo avionRefuerzo) {
        refuerzo.add(avionRefuerzo);
    }

    public static void addMunicionAmigaArrayList(Municion municion) {
        municionAmigaArrayList.add(municion);
    }

    public static void addMunicionEnemigaArrayList(Municion municion) {
        municionEnemigaArrayList.put(indexBalaEmemiga, municion);
        indexBalaEmemiga++;
    }

    public static void addBarco(BarcoEnemigo barcoEnemigos) {
        barcoEnemigo.put(indexBarco, barcoEnemigos);
        indexBarco++;
    }

    public static void addPowerUpArrayList(Power_up powerUp) {
        powerUpArrayList.add(powerUp);
    }

    public static void addAvionEnemigoBonusHashtable(AvionEnemigo avionEnemigo) {
        avionEnemigoBonusHashtable.put(indexAvionBonus, avionEnemigo);
        indexAvionBonus++;
    }

    public BattleOfMidway() {
        super("Battle Of Midway", 945, Toolkit.getDefaultToolkit().getScreenSize().height - 37);
        // System.out.println(appProperties.stringPropertyNames());
    }

    public void gameStartup() {
        try {
            Clip intro = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem
                    .getAudioInputStream(getClass().getClassLoader().getResourceAsStream("SFX/INTRO.wav"));
            intro.open(audioInputStream);
            MAIN_THEME = AudioSystem.getClip();
            AudioInputStream audioInputStream2 = AudioSystem
                    .getAudioInputStream(getClass().getClassLoader().getResourceAsStream("SFX/MAIN_THEME.wav"));
            MAIN_THEME.open(audioInputStream2);
            intro.addLineListener(e -> {
                if (e.getType() == LineEvent.Type.STOP) {
                    try {
                        MAIN_THEME.loop(Clip.LOOP_CONTINUOUSLY);
                    } catch (Exception es) {
                        System.out.println(es);
                    }
                }
            });
            intro.start();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            avionP38 = new Avion_p38("imagenes/avionp38.png");
            avionP38.setPosicion((double) getWidth() / 2, (double) getHeight() / 2);
            img_fondo = ImageIO.read(
                    Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/fondo3.png")));
            imagenNubes = ImageIO.read(
                    Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/nubes2.png")));
            yamato = new Yamato("imagenes/Proyecto nuevo.png", 0, -1600);
            vida = ImageIO.read(
                    Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/barra100.png")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ranking = new Ranking();
        offSetY = img_fondo.getHeight() - getHeight();
        posicionBarcosY = img_fondo.getHeight() * 2 - getHeight();
        posicionNubesY = img_fondo.getHeight() * 2;
        cantAvionBonus = 0;
        timeForBonus = 0;
        lastTimeForBonus = System.currentTimeMillis();
    }

    public void gameUpdate(double delta) {
        Keyboard keyboard = this.getKeyboard();
        if (keyboard.isKeyPressed(KeyEvent.VK_Z) && avionP38.getEnegia() > 25 && !secondFase) {
            rayo = true;
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_Z) && avionP38.getEnegia() > 25 && secondFase) {
            System.out.println("Tsunami");
            tsumani = true;
        }
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

        if (!isBossTime && inScreenEnemies == 0 && !secondFase) {
            Random random = new Random();
            patronAvionesEnemigoNuevo = random.nextInt(5) + 1;
            while (patronAvionesEnemigoNuevo == patronAvionesEnemigoViejo) {
                patronAvionesEnemigoNuevo = random.nextInt(5) + 1;
            }
            patronAvionesEnemigoViejo = patronAvionesEnemigoNuevo;
            masAviones(patronAvionesEnemigoNuevo);
        }
        movimiento(patronAvionesEnemigoNuevo);

        if (timeForBonus > 15000) {
            for (int i = 0; i < 5; i++) {
                addAvionEnemigoBonusHashtable(new AvionEnemigo("imagenes/avionEnemigo.png",
                        0, ((i + 5) * 75)));
            }
            timeForBonus = 0;
        }
        if (!isBossTime && secondFase) {
            Random random = new Random();
            if (barcoEnemigo.size() < 3) {
                for (int index = 0; index < 5; index++) {
                    int x = random.nextInt((795 - 100) + 1) + 100;
                    int y = random.nextInt((100 - 0) + 1) + 0;
                    addBarco(new BarcoEnemigo("imagenes/barquito2.png", x, y));
                }
            }

        }
        int afueraBarco = 0;
        for (Map.Entry<Integer, BarcoEnemigo> barcoEnemigo : barcoEnemigo.entrySet()) {
            barcoEnemigo.getValue().updatePosition();
            barcoEnemigo.getValue().dispararMisil();
            for (Municion municion : municionAmigaArrayList) {
                if (DetectorColiciones.detectarColicionesBarcosBalas(municion, barcoEnemigo.getValue())) {
                    toDeleteMunicionAmiga.add(municion);
                    barcoEnemigo.getValue().setVida(barcoEnemigo.getValue().getVida() - 2);
                    if (barcoEnemigo.getValue().getVida() <= 0) {
                        System.out.println("Barco destruido");
                        afueraBarco = barcoEnemigo.getKey();
                        puntuacion += 500;
                        cantEnemigosDerrotados++;
                    }
                }
            }
            if (DetectorColiciones.detectarColicionBArcoEnemigoBordePantallaInf(barcoEnemigo.getValue())) {
                afueraBarco = barcoEnemigo.getKey();
            }
        }
        barcoEnemigo.remove(afueraBarco);
        afueraBarco = 0;

        if ((powerUpArrayList.isEmpty() && armaBonusArrayList.isEmpty()) && cantAvionBonus == 5) {
            System.out.println("condiciones para bonus cumplidas");
            Random random = new Random();
            int x = random.nextInt(100) + 1;
            double posX = random.nextDouble((795 - 100) + 1) + 100;
            double posY = random.nextDouble((100 - 0) + 1) + 0;
            if (x <= 50) {
                powerUp(posX, posY);
            } else {
                armaUp(posX, posY);
            }
            timeForBonus = 0;
            cantAvionBonus = 0;
        }

        // COLICION DE MUNICION AMIGA CON AVION ENEMIGO
        if (!secondFase) {
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
            }
            if (cantEnemigosDerrotados > 10 && !secondFase) {
                MAIN_THEME.stop();
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                            getClass().getClassLoader().getResourceAsStream("SFX/SECOND_FASE.wav"));
                    clip.open(audioInputStream);
                    clip.addLineListener(e -> {
                        if (e.getType() == LineEvent.Type.STOP) {
                            try {
                                MAIN_THEME.loop(Clip.LOOP_CONTINUOUSLY);
                            } catch (Exception es) {
                                System.out.println(es);
                            }
                        }
                    });
                    clip.start();
                } catch (Exception e) {
                    System.out.println(e);
                }
                secondFase = true;
            }
            avionEnemigoHashtable.remove(indexOfRemovalAvionEnemigo);
            indexOfRemovalAvionEnemigo = 0;
            for (Map.Entry<Integer, AvionEnemigo> avionEnemigoEntry : avionEnemigoHashtable.entrySet()) {
                avionEnemigoEntry.getValue().disparar();
            }
            for (Map.Entry<Integer, AvionEnemigo> avionEnemigoEntry : avionEnemigoHashtable.entrySet()) {
                if (DetectorColiciones.detectarColicionAvionEnemigoBordePantallaDerecho(avionEnemigoEntry.getValue()) ||
                        DetectorColiciones
                                .detectarColicionAvionEnemigoBordePantallaIzquierdo(avionEnemigoEntry.getValue())
                        ||
                        DetectorColiciones.detectarColicionAvionEnemigoBordePantallaSup(avionEnemigoEntry.getValue())) {
                    indexOfRemovalAvionEnemigo = avionEnemigoEntry.getKey();
                }
            }
            if (indexOfRemovalAvionEnemigo != 0) {
                avionEnemigoHashtable.remove(indexOfRemovalAvionEnemigo);
                inScreenEnemies--;
                indexOfRemovalAvionEnemigo = 0;
            }
        }
        if (isBossTime) {
            int afueraTorreta = 0;
            for (Map.Entry<Integer, Torreta> torreta : torretasHashtable.entrySet()) {
                torreta.getValue().disparar(avionP38);
                torreta.getValue().rotacion(avionP38);
                for (Municion municion : municionAmigaArrayList) {
                    if (DetectorColiciones.detectarColicionMuniAmiTorreta(municion, torreta.getValue())) {
                        toDeleteMunicionAmiga.add(municion);
                        torreta.getValue().setVida(torreta.getValue().getVida() - 5);
                        if (torreta.getValue().getVida() <= 0) {
                            afueraTorreta = torreta.getKey();
                        }
                    }
                }
            }
            torretasHashtable.remove(afueraTorreta);
            afueraTorreta = 0;
        }
        int afueraBalaEnemiga = 0;
        for (Map.Entry<Integer, Municion> municion : municionEnemigaArrayList.entrySet()) {
            municion.getValue().setPosition(municion.getValue().getX(), municion.getValue().getY() + 5);
            if (DetectorColiciones.detectarColicionMunicionEnemigaP38(municion.getValue(), avionP38)) {
                afueraBalaEnemiga = municion.getKey();
            }
        }
        municionEnemigaArrayList.remove(afueraBalaEnemiga);
        afueraBalaEnemiga = 0;
        int afueraBala = 0;
        for (Map.Entry<Integer, MunicionPesada> municion : balasYamato.entrySet()) {
            municion.getValue().updatePosition();
            if (DetectorColiciones.detectarColicionMunicionEnemigaP38(municion.getValue(), avionP38)) {
                afueraBala = municion.getKey();
            }
        }
        balasYamato.remove(afueraBala);
        afueraBala = 0;
        for (AvionRefuerzo avionRefuerzo : refuerzo) {
            for (Map.Entry<Integer, Municion> municion : municionEnemigaArrayList.entrySet()) {
                if (DetectorColiciones.detectarColicionesRefuerzoBalasEnemigas(municion.getValue(), avionRefuerzo)) {
                    afueraBalaEnemiga = municion.getKey();
                    avionRefuerzo.impacto();
                    if (avionRefuerzo.getVida() <= 0) {
                        toDeleteRefuerzo.add(avionRefuerzo);
                    }
                }
            }
            municionEnemigaArrayList.remove(afueraBalaEnemiga);
            afueraBalaEnemiga = 0;
        }
        int afueraMisil = 0;
        for (Map.Entry<Integer, Misil> misil : misilArrayList.entrySet()) {
            misil.getValue().updatePosition(avionP38.getX(), avionP38.getY());
            for (Municion municion : municionAmigaArrayList) {
                if (DetectorColiciones.detectarColicionMunicionAmigaMisilEnemigo(municion, misil.getValue())) {
                    toDeleteMunicionEnemiga.add(municion);
                    afueraMisil = misil.getKey();
                    puntuacion = puntuacion + 100;
                }
            }
            if (DetectorColiciones.detectarColicionP38MisilEnemigo(avionP38, misil.getValue())) {
                afueraMisil = misil.getKey();
                System.out.println("P38 golpeado");
                System.out.println(avionP38.getEnegia());
                avionP38.superHit();
                System.out.println(avionP38.getEnegia());
            }
        }
        if (afueraMisil != 0) {
            misilArrayList.remove(afueraMisil);
            afueraMisil = 0;
        }
        if (cantEnemigosDerrotados > 20 && !isBossTime) {
            MAIN_THEME.stop();
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                        getClass().getClassLoader().getResourceAsStream("SFX/YAMATO.wav"));
                clip.open(audioInputStream);
                clip.addLineListener(e -> {
                    if (e.getType() == LineEvent.Type.STOP) {
                        try {
                            MAIN_THEME.loop(Clip.LOOP_CONTINUOUSLY);
                            isBossTime = true;
                        } catch (Exception es) {
                            System.out.println(es);
                        }
                    }
                });
                clip.start();
            } catch (Exception e) {
                System.out.println(e);
            }
            isBossTime = true;
        }

        // BONUS GENERADOS POR MATAR TODOS LOS AVIONCITOS

        for (Map.Entry<Integer, AvionEnemigo> avionEnemigoEntry : avionEnemigoBonusHashtable.entrySet()) {
            avionEnemigoEntry.getValue().autoMoverAvionBonus();
            if (DetectorColiciones.detectarColicionesP38AvionBonus(avionP38, avionEnemigoEntry.getValue())) {
                indexOfRemovalAvionEnemigo = avionEnemigoEntry.getKey();
                cantAvionBonus++;
            }
        }
        avionEnemigoBonusHashtable.remove(indexOfRemovalAvionEnemigo);
        indexOfRemovalAvionEnemigo = 0;
        for (Municion municionAmiga : municionAmigaArrayList) {
            for (Map.Entry<Integer, AvionEnemigo> avionEnemigoEntry : avionEnemigoBonusHashtable.entrySet()) {
                if (DetectorColiciones.detectarColicionMunicionAmigaAvionEnemigo(municionAmiga,
                        avionEnemigoEntry.getValue())) {
                    cantAvionBonus++;
                    System.out.println(cantAvionBonus);
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
            for (ArmaBonus armaBonus : armaBonusArrayList) {
                if (DetectorColiciones.detectarColicionesArmaUpBalas(municionAmiga, armaBonus)) {
                    toDeleteMunicionAmiga.add(municionAmiga);
                    toDeleteArmaBonus.add(armaBonus);
                }
            }
        }
        for (Power_up power_up : powerUpArrayList) {
            if (DetectorColiciones.detectarColicionesPowerUp(avionP38, power_up)) {
                power_up.activar(avionP38);
                System.out.println("Power up activado");
                toDeleteActivatedPowerUp.add(power_up);
            }
        }
        for (ArmaBonus armaBonus : armaBonusArrayList) {
            if (DetectorColiciones.detectarColicionesArmaBonus(avionP38, armaBonus)) {
                armaBonus.activar(avionP38);
                toDeleteActivatedArmaBonus.add(armaBonus);
            }
        }
        avionEnemigoBonusHashtable.remove(indexOfRemovalAvionEnemigo);
        for (Municion municionInt : toDeleteMunicionAmiga) {
            municionAmigaArrayList.remove(municionInt);
        }
        for (Power_up power_up : toDeletePowerUp) {
            powerUp(power_up.position.x, power_up.position.y);
        }
        for (ArmaBonus armaBonus : toDeleteArmaBonus) {
            armaUp(armaBonus.position.x, armaBonus.position.y);
        }
        for (ArmaBonus armaBonus : toDeleteActivatedArmaBonus) {
            armaBonusArrayList.remove(armaBonus);
        }
        for (Power_up power_up : toDeleteActivatedPowerUp) {
            powerUpArrayList.remove(power_up);
        }
        for (AvionRefuerzo avionRefuerzo : toDeleteRefuerzo) {
            refuerzo.remove(avionRefuerzo);
        }
        toDeleteTorreta.clear();
        yamato(isBossTime, keyboard, delta);
        if ((avionP38.getEnegia() <= 0 || yamato.getVida() <= 0) && !isDone) {
            isDone = true;
            finalScore = puntuacion;
            MAIN_THEME.stop();
            endGame();
        }
        toDeleteActivatedArmaBonus.clear();
        toDeleteActivatedPowerUp.clear();
        toDeleteArmaBonus.clear();
        toDeletePowerUp.clear();
        toDeleteMisilEnemigo.clear();
        toDeleteMunicionAmiga.clear();
        toDeleteMunicionEnemiga.clear();
        toDeleteRefuerzo.clear();
    }

    public void gameDraw(Graphics2D g) {
        g.drawImage(img_fondo, 0, -offSetY, null);// imagen de fondo
        if (!secondFase) {
            g.drawImage(imagenNubes, 0, -posicionNubesY, null);// imagen nubes
        }
        if (rayo) {
            try {
                System.out.println(cantEnemigosDerrotados);
                BufferedImage rayito = ImageIO.read(Objects
                        .requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/repalago-2.gif")));
                BufferedImage fondito = ImageIO.read(Objects
                        .requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/fondo3_menos.png")));
                BufferedImage nubecitas = ImageIO.read(Objects
                        .requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/nubes2-menos.png")));
                g.drawImage(fondito, 0, -offSetY, null);
                g.drawImage(nubecitas, 0, -posicionNubesY, null);// imagen nubes
                g.drawImage(rayito, 0, 0, null);
                cantEnemigosDerrotados += avionEnemigoHashtable.size();
                System.out.println(cantEnemigosDerrotados);
                avionEnemigoHashtable.clear();
                inScreenEnemies = 0;
                rayo = false;
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        if (tsumani) {
            try {
                BufferedImage olita = ImageIO.read(Objects
                        .requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/big-ola.gif")));
                BufferedImage fondito = ImageIO.read(Objects
                        .requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/fondo3_menos.png")));
                BufferedImage nubecitas = ImageIO.read(Objects
                        .requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/nubes2-menos.png")));
                g.drawImage(fondito, 0, -offSetY, null);
                g.drawImage(nubecitas, 0, -posicionNubesY, null);// imagen nubes
                g.drawImage(olita, 0, 0, null);
                cantEnemigosDerrotados += barcoEnemigo.size();
                barcoEnemigo.clear();
                inScreenEnemies = 0;
                tsumani = false;
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        if (secondFase) {
            /* for (BarcoEnemigo barcoEnemigo : barcoEnemigos) { */
            for (Map.Entry<Integer, BarcoEnemigo> barcoEnemigo : barcoEnemigo.entrySet()) {
                barcoEnemigo.getValue().draw(g);
            }
        }
        if (isBossTime) {
            yamato.draw(g);
        }
        for (Municion bala : municionAmigaArrayList) {
            bala.draw(g);
        }
        for (Map.Entry<Integer, Municion> municion : municionEnemigaArrayList.entrySet()) {
            municion.getValue().draw(g);
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
        for (Map.Entry<Integer, Misil> misil : misilArrayList.entrySet()) {
            misil.getValue().draw(g);
        }
        for (AvionRefuerzo avionRefuerzo : refuerzo) {
            avionRefuerzo.draw(g);
        }
        for (Map.Entry<Integer, Torreta> torreta : torretasHashtable.entrySet()) {
            torreta.getValue().draw(g);
        }
        for (Map.Entry<Integer, MunicionPesada> municion : balasYamato.entrySet()) {
            municion.getValue().draw(g);
        }
        try {
            BufferedImage file = ImageIO.read(Objects.requireNonNull(
                    getClass().getClassLoader().getResourceAsStream("imagenes/barra" + avionP38.getEnegia() + ".png")));
            if (file != null) {
                vida = file;
            }
        } catch (Exception e) {
            // System.out.println(e);
        }
        g.drawImage(vida, 780, 50, null);
        g.setColor(Color.black);
        g.drawString(String.valueOf(puntuacion), 5, 50);

        g.setColor(Color.white);
        g.drawString(String.valueOf(puntuacion), 7, 52);
        avionP38.draw(g);
    }

    public void yamato(Boolean isBossTime, Keyboard keyboard, Double delta) {
        if (isBossTime) {
            yamato.updatePosition();
            // yamato.disparar();
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
        int cant = 5;
        switch (patron) {
            case 1:
                for (int i = 0; i < cant; i++) {
                    addAvionEnemigoHashtable(
                            new AvionEnemigo("imagenes/Enemigo.png", (10 + (inScreenEnemies + 5) * 60), 25));
                    inScreenEnemies++;
                }
                break;
            case 2:
                for (int i = 0; i < cant; i++) {
                    addAvionEnemigoHashtable(
                            new AvionEnemigo("imagenes/enemigo_0.png", 10, (inScreenEnemies + 5) * 60));
                    inScreenEnemies++;
                }
                break;
            case 3:
                for (int i = 0; i < cant; i++) {
                    addAvionEnemigoHashtable(
                            new AvionEnemigo("imagenes/enemigo_270.png", (10 + (inScreenEnemies + 10) * 40), (10 + (i *
                                    10))));
                    inScreenEnemies++;
                }
                break;
            case 4:
                for (int i = 0; i < cant; i++) {
                    addAvionEnemigoHashtable(
                            new AvionEnemigo("imagenes/enemigo_315.png", (10 + (inScreenEnemies * 30)),
                                    (10 + (inScreenEnemies * 30))));
                    inScreenEnemies++;
                }
                break;
            case 5:
                for (int i = 0; i < cant; i++) {
                    addAvionEnemigoHashtable(new AvionEnemigo("imagenes/enemigo_195.png",
                            (945 - (inScreenEnemies * 30)), (10 + (inScreenEnemies * 30))));
                    inScreenEnemies++;
                }
                break;
        }
    }

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

    private void powerUp(double x, double y) {
        powerUpArrayList.clear();
        Random random = new Random();
        int randomPowerUp = random.nextInt(4) + 1;
        switch (randomPowerUp) {
            case 1:
                addPowerUpArrayList(new Auto("imagenes/auto.png", (int) x, (int) y));
                break;
            case 2:
                addPowerUpArrayList(new Pow("imagenes/Pow.png", (int) x, (int) y));
                break;
            case 3:
                addPowerUpArrayList(new Super_shell("imagenes/SuperShell.png", (int) x, (int) y));
                break;
            case 4:
                addPowerUpArrayList(new EstrellaNinja("imagenes/Estrella.png", (int) x, (int) y));
        }
    }

    private void armaUp(double x, double y) {
        armaBonusArrayList.clear();
        Random random = new Random();
        int randomPowerUp = random.nextInt(4) + 1;
        switch (randomPowerUp) {
            case 1:
                armaBonusArrayList.add(new Escopeta("imagenes/escopeta.png", (int) x, (int) y));
                break;
            case 2:
                armaBonusArrayList.add(new Ametralladora("imagenes/ametralladora.png", (int) x, (int) y));
                break;
            case 3:
                armaBonusArrayList.add(new Laser("imagenes/laser.png", (int) x, (int) y));
                break;
            case 4:
                armaBonusArrayList.add(new Refuerzo("imagenes/Refuerzo.png", (int) x, (int) y));
                break;
        }
    }
    private JButton boton;
    private String date = LocalDate.now().toString();
    JTextArea nombre;
    JFrame morido;
    private void endGame() {
        morido = new JFrame();
        boton = new JButton("Guardar");
        boton.addActionListener(this);
        JLabel fechaObtencion = new JLabel("Fecha de Obtencion: " + LocalDate.now().toString());
        JLabel label = new JLabel("Puntaje Obtenido: " + String.valueOf(finalScore));
        JLabel label2 = new JLabel("Ingrese su nombre");
        nombre = new JTextArea();
        LayoutManager layout = new GridLayout(5, 1);
        morido.setLayout(layout);
        morido.setSize(500, 500);
        morido.setLocationRelativeTo(null);
        morido.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        morido.add(label2);
        morido.add(nombre);
        morido.add(label);
        morido.add(fechaObtencion);
        morido.add(boton);
        morido.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals(boton.getActionCommand())) {
            morido.setVisible(false);
            ranking.insert(nombre.getText(), finalScore, date);
            this.stop();
            ranking.getData();
        }

    }
}