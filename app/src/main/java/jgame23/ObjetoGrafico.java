package jgame23;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class ObjetoGrafico extends Rectangle implements Movible{
    protected BufferedImage image;
    protected Point2D.Double position = new Point2D.Double();

    public ObjetoGrafico(String filename){
        try{
            if (this.image == null) {
                this.image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename)));
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public ObjetoGrafico(){}
    public double getWidth(){return this.image.getWidth();}
    public double getHeigth(){return this.image.getHeight();}
    public double getX(){return this.position.getX();}
    public double getY(){return this.position.getY();}
    public void setPosition(double x, double y){this.position.setLocation(x,y);}
    public void draw(Graphics2D g){
        /* g.setColor(java.awt.Color.RED);
        g.drawRect((int)this.position.x, (int)this.position.y, this.image.getWidth(), this.image.getHeight()); */
        g.drawImage(this.image, (int)this.position.getX(), (int)this.position.getY(), null);
    }
}