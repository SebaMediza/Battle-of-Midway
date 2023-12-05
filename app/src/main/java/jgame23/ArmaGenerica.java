package jgame23;

//import java.awt.Graphics2D;

public class ArmaGenerica extends Arma{
    public void disparar(Avion_p38 plane){
        super.disparar(plane);
    }
    public void disparar(Torreta torreta, Avion_p38 plane){
        super.disparar(torreta, plane);
    }
    public void disparar(AvionRefuerzo plane){
        super.disparar(plane);
    }

}
