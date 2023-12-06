package jgame23;

import com.entropyinteractive.Keyboard;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Objects;

import static java.lang.System.*;

class Avion_p38 extends ObjetoGrafico {
    final double NAVE_DESPLAZAMIENTO = 450.0;
    private BufferedImage imagen = null;
    private final Point2D.Double posicion = new Point2D.Double();
    private final ArmaGenerica gun = new ArmaGenerica();
    public static int enegia = 100;
    private long time, lastTime;
    private int xMin = 0;
    private int yMin = 27;
    private int xMax = 895;
    private int yMax = Toolkit.getDefaultToolkit().getScreenSize().height - 76;
    private boolean inclinadoIzquierda = false;
    private boolean inclinadoDerecha = false;
    private boolean auto = false;
    private boolean ametralladoraActivada = false;
    private boolean laser = false;
    private BufferedImage imagenincliizqui;
    private BufferedImage imageninclidere;
    private long startAuto, currentTimeAuto, lastTimeEnergia, nowEnergia;

    public void setAuto(boolean auto) {
        this.auto = auto;
        startAuto = System.currentTimeMillis();
    }
    public void setLaser(boolean laser) {
        this.laser = laser;
        startAuto = System.currentTimeMillis();
    }
    public void setAmetralladora(boolean ametralladora) {
        this.ametralladoraActivada = ametralladora;
        startAuto = System.currentTimeMillis();
    }

    public Avion_p38(String file) {
        super(file);
        try {
            this.setImagen(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(file))));
            imagenincliizqui = ImageIO.read(Objects.requireNonNull(
                    getClass().getClassLoader().getResourceAsStream("imagenes/inclinacionIzquierda.png")));
            imageninclidere = ImageIO.read(Objects.requireNonNull(
                    getClass().getClassLoader().getResourceAsStream("imagenes/inclinacionDerecha.png")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        time = 0;
        lastTime = System.currentTimeMillis();
        nowEnergia = 0;
        lastTimeEnergia = System.currentTimeMillis();
    }

    public void setImagen(BufferedImage img) {
        this.imagen = img;
    }

    public void setPosicion(double x, double y) {
        posicion.setLocation(x, y);
    }

    public void setX(double x) {
        posicion.x = x;
    }

    public void setY(double y) {
        posicion.y = y;
    }

    public double getX() {
        return posicion.getX();
    }

    public double getY() {
        return posicion.getY();
    }

    public void draw(Graphics2D g) {
        if (inclinadoIzquierda) {
            g.drawImage(imagenincliizqui, (int) posicion.getX(), (int) posicion.getY(), null);
        } else if (inclinadoDerecha) {
            g.drawImage(imageninclidere, (int) posicion.getX(), (int) posicion.getY(), null);
        } else {
            g.drawImage(imagen, (int) posicion.getX(), (int) posicion.getY(), null);
        }
    }

    @Override
    public void mover(double delta, Keyboard keyboard) {
        // Procesar teclas de direccion
        if (keyboard.isKeyPressed(KeyEvent.VK_UP)) {
            // shipY -= NAVE_DESPLAZAMIENTO * delta;
            int nuevaY = (int) (this.getY() - NAVE_DESPLAZAMIENTO * delta);
            if (nuevaY >= yMin) {
                this.setY(nuevaY);
            }
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
            // shipY += NAVE_DESPLAZAMIENTO * delta;
            int nuevaY = (int) (this.getY() + NAVE_DESPLAZAMIENTO * delta);
            if (nuevaY <= yMax) {
                this.setY(nuevaY);
            }
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
            /// shipX -= NAVE_DESPLAZAMIENTO * delta;
            int nuevaX = (int) (this.getX() - NAVE_DESPLAZAMIENTO * delta);
            if (nuevaX >= xMin) {
                this.setX(nuevaX);
                inclinadoIzquierda = true;
                inclinadoDerecha = false;
            }
        } else if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
            // shipX += NAVE_DESPLAZAMIENTO * delta;
            int nuevaX = (int) (this.getX() + NAVE_DESPLAZAMIENTO * delta);
            if (nuevaX <= xMax) {
                this.setX(nuevaX);
                inclinadoIzquierda = false;
                inclinadoDerecha = true;
            }
        } else {
            inclinadoIzquierda = false;
            inclinadoDerecha = false;
        }
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if (keyboard.isKeyPressed(88) && time > 200) {
            if (auto) {
                currentTimeAuto = System.currentTimeMillis() - startAuto;
                if (currentTimeAuto > 5000) {
                    setAuto(false);
                    System.out.println("Shell desactivado");
                }
                autoDisparar();
                time = 0;
            } else {
                if (ametralladoraActivada) {
                    System.out.println("Disparo ametralladora");
                    currentTimeAuto = System.currentTimeMillis() - startAuto;
                    if (currentTimeAuto > 5000) {
                        setAmetralladora(false);
                        System.out.println("Amertralladora desactivado");
                    }
                    dispararAmetralladora();
                    time = 0;
                } else {
                    if (laser){
                        System.out.println("Disparo laser");
                        currentTimeAuto = System.currentTimeMillis() - startAuto;
                        if (currentTimeAuto > 5000) {
                            setLaser(false);
                            System.out.println("Laser desactivado");
                            time = 0;
                        }
                        disparar();
                    } else{
                        disparar();
                        time = 0;
                    }
                }
            }
        }
        // Esc fin del juego
        LinkedList<KeyEvent> keyEvents = keyboard.getEvents();
        for (KeyEvent event : keyEvents) {
            if ((event.getID() == KeyEvent.KEY_PRESSED) &&
                    (event.getKeyCode() == KeyEvent.VK_ESCAPE)) {
                exit(0);
            }
        }
    }

    public void drainEnergia(){
        nowEnergia += System.currentTimeMillis() - lastTimeEnergia;
        lastTimeEnergia = System.currentTimeMillis();
        if (nowEnergia > 30000){
            enegia -= 1;
            nowEnergia = 0;
        }
    }

    public void disparar() {
        gun.disparar(this);
    }

    public void dispararLaser() {
        gun.disparar(this);
    }

    public void autoDisparar() {
        Thread t = new Thread(r);
        t.start();
    }

    public void dispararAmetralladora() {
        Thread t = new Thread(ametralladora);
        t.start();
    }

    public void hit() {
        enegia = enegia - 1;
    }

    public void superHit() {
        enegia = enegia - 5;
    }

    public void crash() {
        enegia = enegia - 10;
    }

    public int getEnegia() {
        return Math.max(enegia, 0);
    }

    public void setEnegia(int enegia) {
        if (Avion_p38.enegia + enegia > 100) {
            Avion_p38.enegia = 100;
        } else {
            Avion_p38.enegia += enegia;
        }
    }

    public boolean sufEnergia() {
        if (Avion_p38.enegia > 25) {
            enegia -= 25;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public double getCoordenadas() {
        return 0;
    }

    Runnable r = () -> {
        try {
            for (int i = 0; i < 3; i++) {
                gun.disparar(this);
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };
    Runnable ametralladora = () -> {
        Municion bala = new Municion("imagenes/municion4.png", (int) this.getX() + 18, (int) this.getY());
        Municion balaDere = new Municion("imagenes/municion4.png", (int) this.getX() + 55, (int) this.getY());
        Municion balaIzqi = new Municion("imagenes/municion4.png", (int) this.getX() - 35, (int) this.getY());
        BattleOfMidway.addMunicionAmigaArrayList(bala);
        BattleOfMidway.addMunicionAmigaArrayList(balaDere);
        BattleOfMidway.addMunicionAmigaArrayList(balaIzqi);
    };
}
