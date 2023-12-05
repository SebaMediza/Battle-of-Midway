package jgame23;

import com.entropyinteractive.*; //jgame
import java.awt.*;
import java.awt.image.*; //imagenes
import javax.imageio.*; //imagenes
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
    public static ArrayList<BarcoEnemigo> barcoEnemigos = new ArrayList<>();
    public static ArrayList<AvionRefuerzo> refuerzo = new ArrayList<>();
    public static ArrayList<Torreta> torretas = new ArrayList<>();

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

    Ranking ranking = new Ranking();
    public static int finalScore = 0;
    private int offSetY, posicionNubesY, posicionBarcosY;
    private BufferedImage img_fondo, imagenNubes;
    private Avion_p38 avionP38;
    private Yamato yamato;
    private long timeForBonus, lastTimeForBonus;
    private int cantAvionBonus, cantEnemigosDerrotados = 0;
    private int indexOfRemovalAvionEnemigo;
    private boolean isBossTime = false, secondFase = false;
    private int inScreenEnemies = 0;

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
            yamato = new Yamato("imagenes/Proyecto nuevo.png", 0, -1900);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        FXPlayer.init();
        FXPlayer.volume = FXPlayer.Volume.LOW;
        offSetY = img_fondo.getHeight() - getHeight();
        posicionBarcosY = img_fondo.getHeight() * 2 - getHeight();
        posicionNubesY = img_fondo.getHeight() * 2;
        cantAvionBonus = 0;
        timeForBonus = 0;
        lastTimeForBonus = System.currentTimeMillis();
        FXPlayer.INTRO.play();
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

        if (!isBossTime && inScreenEnemies == 0) {
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
                addAvionEnemigoBonusHashtable(new AvionEnemigo("imagenes/avionEnemigo.png", 0, ((i + 5) * 75)));
            }
            timeForBonus = 0;
        }
        if (!isBossTime && secondFase && barcoEnemigos.isEmpty()) {
            Random random = new Random();
            for (int i = 0; i < 5; i++) {
                int x = random.nextInt((795 - 100) + 1) + 100;
                int y = random.nextInt((200 - 0) + 1) + 0;
                barcoEnemigos.add(new BarcoEnemigo("imagenes/barquito2.png", x, y));
            }
        }
        for (BarcoEnemigo barcoEnemigo : barcoEnemigos) {
            barcoEnemigo.updatePosition();
            barcoEnemigo.dispararMisil();
        }
        if ((powerUpArrayList.isEmpty() && armaBonusArrayList.isEmpty()) && cantAvionBonus == 5) {
            System.out.println("condiciones para bonus cumplidas");
            Random random = new Random();
            int x = random.nextInt(100) + 1;
            if (x <= 50) {
                powerUp();
            } else {
                armaUp();
            }
            timeForBonus = 0;
            cantAvionBonus = 0;
        }

        ArrayList<Municion> toDeleteMunicionAmiga = new ArrayList<>();
        ArrayList<Municion> toDeleteMunicionEnemiga = new ArrayList<>();
        ArrayList<Misil> toDeleteMisilEnemigo = new ArrayList<>();
        ArrayList<Power_up> toDeletePowerUp = new ArrayList<>();
        ArrayList<Power_up> toDeleteActivatedPowerUp = new ArrayList<>();
        ArrayList<ArmaBonus> toDeleteArmaBonus = new ArrayList<>();
        ArrayList<ArmaBonus> toDeleteActivatedArmaBonus = new ArrayList<>();
        ArrayList<AvionRefuerzo> toDeleteRefuerzo = new ArrayList<>();
        ArrayList<Torreta> toDeleteTorreta = new ArrayList<>();

        // COLICION DE MUNICION AMIGA CON AVION ENEMIGO
        if (!secondFase){
            for (Municion municion : municionAmigaArrayList) {
            for (Map.Entry<Integer, AvionEnemigo> avionEnemigoEntry : avionEnemigoHashtable.entrySet()) {
                avionEnemigoEntry.getValue().disparar();
                if (DetectorColiciones.detectarColicionMunicionAmigaAvionEnemigo(municion,
                        avionEnemigoEntry.getValue())) {
                    indexOfRemovalAvionEnemigo = avionEnemigoEntry.getKey();
                    toDeleteMunicionAmiga.add(municion);
                    cantEnemigosDerrotados++;
                    inScreenEnemies--;
                    puntuacion = puntuacion + 500;
                    if (cantEnemigosDerrotados > 10){
                        FXPlayer.SECOND_FASE.play();
                        secondFase = true;
                    }
                }
            }
            if (DetectorColiciones.detectarColicionMunicionAmigaBordePantalla(municion)) {
                toDeleteMunicionAmiga.add(municion);
            }
            avionEnemigoHashtable.remove(indexOfRemovalAvionEnemigo);
            indexOfRemovalAvionEnemigo = 0;
        }
        }
        if (isBossTime) {
            for (Torreta torreta : torretas) {
                torreta.disparar();
                torreta.rotacion(avionP38);
                for (Municion municion : municionAmigaArrayList) {
                    if (DetectorColiciones.detectarColicionMuniAmiTorreta(municion, torreta)) {
                        toDeleteMunicionAmiga.add(municion);
                        torreta.setVida(torreta.getVida() - 5);
                        if (torreta.getVida() <= 0) {
                            toDeleteTorreta.add(torreta);
                            System.out.println("Torreta destruida + " + torreta);
                        }
                    }
                }
            }
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
        if (cantEnemigosDerrotados > 20) {
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
        for (ArmaBonus armaBonus : toDeleteActivatedArmaBonus) {
            armaBonusArrayList.remove(armaBonus);
        }
        for (Power_up power_up : toDeleteActivatedPowerUp) {
            powerUpArrayList.remove(power_up);
        }
        for (AvionRefuerzo avionRefuerzo : toDeleteRefuerzo) {
            refuerzo.remove(avionRefuerzo);
        }
        for (Torreta torreta : toDeleteTorreta) {
            System.out.println(torreta + "afuera");
            torretas.remove(torreta);
        }
        toDeleteTorreta.clear();
        yamato(isBossTime, keyboard, delta);
        if (avionP38.getEnegia() <= 0) {
            finalScore = puntuacion;
            // this.stop();
            // ranking.setVisible(true);
        }
    }

    public void gameDraw(Graphics2D g) {
        g.drawImage(img_fondo, 0, -offSetY, null);// imagen de fondo
        if (!secondFase) {
            g.drawImage(imagenNubes, 0, -posicionNubesY, null);// imagen nubes
        }
        if(secondFase){
            for (BarcoEnemigo barcoEnemigo : barcoEnemigos) {
                barcoEnemigo.draw(g);
            }
        }
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
        for (Torreta torreta : torretas) {
            torreta.draw(g);
        }
        g.setColor(Color.black);
        g.drawString(String.valueOf(avionP38.getEnegia()), 480, 50);
        g.drawString(String.valueOf(puntuacion), 5, 50);
        g.setColor(Color.white);
        g.drawString(String.valueOf(avionP38.getEnegia()), 482, 52);
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
        Random r = new Random();
        int cant = 5;//(r.nextInt(15) + 1);
        switch (patron) {
            case 1:
                for (int i = 0; i < cant; i++) {
                    addAvionEnemigoHashtable(
                            new AvionEnemigo("imagenes/Enemigo.png", ((inScreenEnemies + 5) * 60), 15));
                    inScreenEnemies++;
                }
                break;
            case 2:
                for (int i = 0; i < cant; i++) {
                    addAvionEnemigoHashtable(
                            new AvionEnemigo("imagenes/enemigo_0.png", 0, (inScreenEnemies + 5) * 60));
                    inScreenEnemies++;
                }
                break;
            case 3:
                for (int i = 0; i < cant; i++) {
                    addAvionEnemigoHashtable(
                            new AvionEnemigo("imagenes/enemigo_270.png", (inScreenEnemies + 10) * 40, i *
                                    10));
                    inScreenEnemies++;
                }
                break;
            case 4:
                for (int i = 0; i < cant; i++) {
                    addAvionEnemigoHashtable(
                            new AvionEnemigo("imagenes/enemigo_315.png", inScreenEnemies * 30,
                                    inScreenEnemies * 30));
                    inScreenEnemies++;
                }
                break;
            case 5:
                for (int i = 0; i < cant; i++) {
                    addAvionEnemigoHashtable(new AvionEnemigo("imagenes/enemigo_195.png",
                            (945 - (inScreenEnemies * 30)), inScreenEnemies * 30));
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
}