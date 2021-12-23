import java.awt.*;

public class Bullet extends GameObj {
    private int power;

    public static final int BHEIGHT = 5;
    public static final int BWIDTH = 3;

    public Bullet(int power, int vx, int vy, int px, int py, int courtWidth, int courtHeight) {
        super(vx, vy, px, py, BWIDTH, BHEIGHT, courtWidth, courtHeight);
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
    }
}
