import java.util.LinkedList;
import java.util.List;

public class Student extends Player {
    public static final String IMG_FILE = "files/Student.png";

    public Student(int courtWidth, int courtHeight) {
        super(courtWidth, courtHeight, IMG_FILE);
    }

    @Override
    public List<Bullet> shoot() {
        List<Bullet> returnList = new LinkedList<>();
        returnList.add(
                new Bullet(
                        15, 0, -10,
                        getPx() + SIZE / 2, getPy(), getCourtWidth(), getCourtHeight()
                )
        );
        return returnList;
    }

    @Override
    public String getType() {
        return "Student";
    }
}
