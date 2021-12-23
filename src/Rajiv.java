import java.util.LinkedList;
import java.util.List;

public class Rajiv extends Player {
    public static final String IMG_FILE = "files/Rajiv.png";

    public Rajiv(int courtWidth, int courtHeight) {
        super(courtWidth, courtHeight, IMG_FILE);
    }

    @Override
    public List<Bullet> shoot() {
        List<Bullet> returnList = new LinkedList<>();
        for (int i = 1; i <= 20; i++) {
            returnList.add(
                    new Bullet(
                            100, 0, -5,
                            getCourtWidth() / 21 * i, getPy(), getCourtWidth(), getCourtHeight()
                    )
            );
        }
        return returnList;
    }

    @Override
    public String getType() {
        return "Rajiv";
    }

}
