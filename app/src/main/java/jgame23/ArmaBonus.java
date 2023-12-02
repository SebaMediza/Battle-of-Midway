package jgame23;

import com.entropyinteractive.Keyboard;

public abstract class ArmaBonus extends Bonus {

    public ArmaBonus(String filename) {
        super(filename);
    }

    public void disparar(Avion_p38 plane){
        Municion bala = new Municion("imagenes/municion4.png");
        BattleOfMidway.addMunicionAmigaArrayList(bala);
        bala.setPosition(plane.getX() + 18, plane.getY());
        bala.setPosition(plane.getX() + 18, plane.getY() - 25);
        bala.setPosition(plane.getX() + 18, plane.getY() + 25);
    }




    @Override
    public void mover(double delta, Keyboard keyboard) {
        throw new UnsupportedOperationException("Unimplemented method 'mover'");
    }

    @Override
    public double getCoordenadas() {
        throw new UnsupportedOperationException("Unimplemented method 'getCoordenadas'");
    }

    
}