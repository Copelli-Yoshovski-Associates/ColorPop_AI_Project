package application.model;


import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("inMove")
public class Move implements java.io.Serializable {

    @Param(0)
    private int x;
    @Param(1)
    private int y;
    @Param(2)
    private int c;

    public Move() {
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public Move(int x, int y, int c) {
        this.x = x;
        this.y = y;
        this.c = c;
    }

    @Override
    public String toString() {
        return "Move{" +
                "x=" + x +
                ", y=" + y +
                ", c=" + c +
                '}';
    }
}
