import java.awt.*;

public class Homework {
    public static final int SIZE = 45;

    private final int fullScore;
    private int score;
    private final String type;
    private int posX;
    private int posY;

    public Homework(int posX, int posY, int currScore, int fullScore, String type) {
        this.fullScore = fullScore;
        this.score = currScore;
        this.type = type;
        this.posX = posX;
        this.posY = posY;
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    public void setX(int posX) {
        this.posX = posX;
    }

    public void setY(int posY) {
        this.posY = posY;
    }

    public void incScore(int inc) {
        score += inc;
    }

    public String getType() {
        return type;
    }

    public int getScore() {
        return score;
    }

    public int getFullScore() {
        return fullScore;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(posX, posY, SIZE, SIZE);
        g.setColor(Color.BLACK);
        Font f = new Font("Comic Sans MS", Font.BOLD, 10);
        g.setFont(f);
        g.drawString(type, posX + 5, posY + 10);
        g.drawString(score + "/" + fullScore, posX + 3, posY + 25);
    }

}
