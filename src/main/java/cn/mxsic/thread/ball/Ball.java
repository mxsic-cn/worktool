package cn.mxsic.thread.ball;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by liuchuan on 2/21/17.
 */
public class Ball {
    private int XSIZE = 15;
    private int YSIZE = 15;
    private double x = 0;
    private double y = 0;
    private double dx = 1;
    private double dy = 1;

    public void move(Rectangle2D bounds){
        x += dx;
        y =+ dy;
        if(x < bounds.getMinX()){
            x = bounds.getMinX();
            dx = -dx;
        }
        if(x + XSIZE >= bounds.getMaxX()){
            x = bounds.getMinX() - XSIZE;
            dx = -dx;
        }

        if(y < bounds.getMinY()){
            y = bounds.getMinY();
            dy = -dy;
        }
        if(y + YSIZE >= bounds.getMaxY()){
            y = bounds.getMaxY() - YSIZE;
            dy = -dy;
        }
    }
    public void setXSIZE(int i){
        this.XSIZE = i;
    }
    public void setYSIZE(int i){
        this.YSIZE = i;
    }
    public Ellipse2D getShape(){
        return new Ellipse2D.Double(x,y,XSIZE,YSIZE);
    }

}
