import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class Player extends GameObj {

    private final int courtWidth;
    private final int courtHeight;
    private BufferedImage img;

    public static final int SIZE = 40;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;

    public Player(int courtWidth, int courtHeight, String file) {
        super(
                INIT_VEL_X, INIT_VEL_Y, courtWidth / 2 - SIZE / 2,
                courtHeight - SIZE, SIZE, SIZE, courtWidth, courtHeight
        );
        this.courtWidth = courtWidth;
        this.courtHeight = courtHeight;
        try {
            img = ImageIO.read(new File(file));
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    public void draw(Graphics g) {
        g.drawImage(
                getImage(), this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null
        );
    }

    public BufferedImage getImage() {
        return img;
    }

    public int getCourtWidth() {
        return courtWidth;
    }

    public int getCourtHeight() {
        return courtHeight;
    }

    public abstract List<Bullet> shoot();

    public abstract String getType();
}
