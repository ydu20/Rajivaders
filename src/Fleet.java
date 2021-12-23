import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Fleet {
    private Homework[][] fArray;
    private final int spaceH;
    private final int spaceW;
    private int timeFrame;
    private boolean movingRight;
    private int fleetY = 50;
    private int fleetX = 20;

    public static final int SP = 60;
    public static final int DY = 30;

    public Fleet(int w, int h, int frame) {
        spaceH = h;
        spaceW = w;
        timeFrame = frame;
        movingRight = true;
        fArray = new Homework[5][6];
        boolean tuesday = true;
        boolean mid1 = true;
        boolean mid2 = true;
        boolean fin = true;
        int week = 1;
        for (int i = 4; i > -1; i--) {
            for (int j = 5; j > -1; j--) {
                Homework hw;
                if (week == 6 && tuesday && mid1) {
                    hw = new Homework(20 + j * SP, 50 + i * SP, 0, 100, "Mid 1");
                    mid1 = false;
                } else if (week == 10 && tuesday && mid2) {
                    hw = new Homework(20 + j * SP, 50 + i * SP, 0, 100, "Mid 2");
                    mid2 = false;
                } else if (week == 14 && !tuesday && fin) {
                    hw = new Homework(20 + j * SP, 50 + i * SP, 0, 100, "Final!");
                    fin = false;
                } else if (tuesday) {
                    hw = new Homework(20 + j * SP, 50 + i * SP, 0, 30, "HW" + week + "t");
                    tuesday = false;
                } else {
                    hw = new Homework(20 + j * SP, 50 + i * SP, 0, 70, "HW" + week + "h");
                    tuesday = true;
                    week += 1;
                }
                fArray[i][j] = hw;
            }
        }

    }

    public Fleet(int w, int h, BufferedReader br) {
        spaceH = h;
        spaceW = w;
        fArray = new Homework[5][6];
        try {
            String[] params = br.readLine().split(",");
            timeFrame = Integer.parseInt(params[0]);
            movingRight = Boolean.parseBoolean(params[1]);
            fleetX = Integer.parseInt(params[2]);

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 6; j++) {
                    String line = br.readLine();
                    if (!line.equals("null")) {
                        params = line.split(",");
                        fArray[i][j] = new Homework(
                                Integer.parseInt(params[0]), Integer.parseInt(params[1]),
                                Integer.parseInt(params[2]), Integer.parseInt(params[3]), params[4]
                        );
                    } else {
                        fArray[i][j] = null;
                    }

                }
            }

        } catch (IOException e) {
            System.out.println("Loading Failed!");
        }

    }

    public void move() {
        if (movingRight) {
            int fleetW = 6 * SP;
            if (fleetX + fleetW + SP > spaceW) {
                movingRight = false;
                moveDown();
            } else {
                moveRight();
            }
        } else {
            if (fleetX - SP < 0) {
                movingRight = true;
                moveDown();
            } else {
                moveLeft();
            }
        }
    }

    private void moveRight() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                if (fArray[i][j] != null) {
                    fArray[i][j].setX(fArray[i][j].getX() + SP);
                }
            }
        }
        fleetX += SP;
    }

    private void moveLeft() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                if (fArray[i][j] != null) {
                    fArray[i][j].setX(fArray[i][j].getX() - SP);
                }
            }
        }
        fleetX -= SP;
    }

    private void moveDown() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                if (fArray[i][j] != null) {
                    fArray[i][j].setY(fArray[i][j].getY() + DY);
                }
            }
        }
        fleetY += DY;
        timeFrame *= 0.8;
    }

    public int gameOver() {
        for (int i = 4; i > -1; i--) {
            for (int j = 5; j > -1; j--) {
                if (fArray[i][j] != null) {
                    if (fArray[i][j].getY() >= spaceH - SP) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            }
        }
        return 1;
    }

    public boolean hit(Bullet blt) {
        for (int i = 4; i > -1; i--) {
            for (int j = 5; j > -1; j--) {
                if (fArray[i][j] != null) {
                    Homework curr = fArray[i][j];
                    if (curr.getX() + Homework.SIZE >= blt.getPx()
                            && curr.getY() + Homework.SIZE >= blt.getPy()
                            && blt.getPx() + blt.getWidth() >= curr.getX()
                            && blt.getPy() + blt.getHeight() >= curr.getY()) {
                        curr.incScore(blt.getPower());
                        if (curr.getScore() >= curr.getFullScore()) {
                            fArray[i][j] = null;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int getTimeFrame() {
        return timeFrame;
    }

    public boolean getMovingRight() {
        return movingRight;
    }

    public int getPosX() {
        return fleetX;
    }

    public void draw(Graphics g) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                if (fArray[i][j] != null) {
                    fArray[i][j].draw(g);
                }
            }
        }
    }

    public void recordState(BufferedWriter bw) throws IOException {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                if (fArray[i][j] != null) {
                    Homework h = fArray[i][j];
                    String line = h.getX() + "," + h.getY() + "," + h.getScore() + ","
                            + h.getFullScore() + "," + h.getType() + "\n";
                    bw.write(line);
                } else {
                    bw.write("null\n");
                }
            }
        }
    }

}
