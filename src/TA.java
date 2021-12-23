import java.util.LinkedList;
import java.util.List;

public class TA extends Player {
    public static final String IMG_FILE = "files/TA.png";

    public TA(int courtWidth, int courtHeight) {
        super(courtWidth, courtHeight, IMG_FILE);
    }

    @Override
    public List<Bullet> shoot() {
        List<Bullet> returnList = new LinkedList<>();
        returnList.add(
                new Bullet(
                        10, -2, -10,
                        getPx() + SIZE / 2, getPy(), getCourtWidth(), getCourtHeight()
                )
        );
        returnList.add(
                new Bullet(
                        10, 0, -10,
                        getPx() + SIZE / 2, getPy(), getCourtWidth(), getCourtHeight()
                )
        );
        returnList.add(
                new Bullet(
                        10, 2, -10,
                        getPx() + SIZE / 2, getPy(), getCourtWidth(), getCourtHeight()
                )
        );
        return returnList;
    }

    @Override
    public String getType() {
        return "TA";
    }

}
