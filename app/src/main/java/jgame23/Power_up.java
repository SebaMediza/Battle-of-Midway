package jgame23;

import java.awt.*;

public abstract class Power_up extends Bonus {
    protected int duracion = 20;

    public Power_up(String filename){
        super(filename);
    }

    
    

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }

}
