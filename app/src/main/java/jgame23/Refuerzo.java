package jgame23;

import com.entropyinteractive.Keyboard;

public class Refuerzo extends Power_up {
    public Refuerzo(String filename, int x, int y) {
        super(filename);
        this.setPosition(x, y);
    }

    @Override
    public double getCoordenadas() {
        return 0;
    }

    @Override
    protected void activar(Avion_p38 avionP38) {
        AvionRefuerzo avionRefuerzo1 = new AvionRefuerzo("imagenes/enemigo_90.png", avionP38.getX() + 75, avionP38.getY() - 50);
        AvionRefuerzo avionRefuerzo2 = new AvionRefuerzo("imagenes/enemigo_90.png", avionP38.getX() - 45, avionP38.getY() - 50);
        BattleOfMidway.addRefuerzoArrayList(avionRefuerzo1);
        BattleOfMidway.addRefuerzoArrayList(avionRefuerzo2);
    }

    @Override
    public void mover(double delta, Keyboard keyboard) { // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mover'");
    }
}
