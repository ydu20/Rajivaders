import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Space extends JPanel {
    private Player player;

    private boolean playing = false; // whether the game is running
    private boolean pregame = true;
    private RunRajivaders engine;
    private Fleet fleet;
    private Timer fleetTimer;
    private List<Bullet> bullets;
    private boolean shot = false;
    private boolean paused = true;
    private BufferedImage background;

    public static final int COURT_WIDTH = 700;
    public static final int COURT_HEIGHT = 620;
    public static final int SQUARE_VELOCITY = 10;
    public static final int INTERVAL = 35;
    public static final int INITIALSP = 1000;

    public Space(RunRajivaders run) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        engine = run;
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start();

        fleetTimer = new Timer(INITIALSP, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                moveFleet();
            }
        });
        fleetTimer.start();

        try {
            if (background == null) {
                background = ImageIO.read(new File("files/Background.png"));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }

        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    player.setVx(-SQUARE_VELOCITY);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player.setVx(SQUARE_VELOCITY);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (!shot) {
                        bullets.addAll(player.shoot());
                        shot = true;
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
                player.setVx(0);
                shot = false;
            }

        });

    }

    public void startGame(int type) {
        if (type == 0) {
            player = new Student(COURT_WIDTH, COURT_HEIGHT);
        } else if (type == 1) {
            player = new TA(COURT_WIDTH, COURT_HEIGHT);
        } else {
            player = new Rajiv(COURT_WIDTH, COURT_HEIGHT);
        }
        fleet = new Fleet(COURT_WIDTH, COURT_HEIGHT, INITIALSP);
        bullets = new LinkedList<>();

        pregame = false;
        playing = true;

        engine.getPause().setVisible(true);
        engine.getRajiv().setVisible(false);
        engine.getTa().setVisible(false);
        engine.getStudent().setVisible(false);
        engine.getStatus().setText("Running...");
        requestFocusInWindow();

    }

    public void reset() {
        pregame = true;
        paused = false;
        engine.getStatus().setText("Choose your player");
        engine.getPause().setText("Pause");
        engine.getPause().setVisible(false);
        engine.getReset().setVisible(false);
        engine.getSave().setVisible(false);
        engine.getRajiv().setVisible(true);
        engine.getTa().setVisible(true);
        engine.getStudent().setVisible(true);
        repaint();
    }

    public void reset(String saved) {
        pregame = false;
        paused = true;

        engine.getPause().setVisible(true);
        engine.getPause().setText("Resume");
        engine.getReset().setVisible(true);
        engine.getSave().setVisible(true);
        engine.getRajiv().setVisible(false);
        engine.getTa().setVisible(false);
        engine.getStudent().setVisible(false);

        try {
            BufferedReader br = new BufferedReader(new FileReader(saved));
            String[] params = br.readLine().split(",");
            if (params[0].equals("Student")) {
                player = new Student(COURT_WIDTH, COURT_HEIGHT);
                player.setPx(Integer.parseInt(params[1]));
            } else if (params[0].equals("TA")) {
                player = new TA(COURT_WIDTH, COURT_HEIGHT);
                player.setPx(Integer.parseInt(params[1]));
            } else {
                player = new Rajiv(COURT_WIDTH, COURT_HEIGHT);
                player.setPx(Integer.parseInt(params[1]));
            }
            fleet = new Fleet(COURT_WIDTH, COURT_HEIGHT, br);

            bullets = new LinkedList<>();

            String line;
            while ((line = br.readLine()) != null) {
                params = line.split(",");
                bullets.add(
                        new Bullet(
                                Integer.parseInt(params[0]), Integer.parseInt(params[1]),
                                Integer.parseInt(params[2]), Integer.parseInt(params[3]),
                                Integer.parseInt(params[4]), COURT_WIDTH, COURT_HEIGHT
                        )
                );
            }
            engine.getStatus().setText("Game Loaded: " + saved);

        } catch (FileNotFoundException e) {
            reset();
            engine.getStatus().setText("File Not Found! Choose your player");
        } catch (IOException e) {
            reset();
            engine.getStatus().setText("Reading error! Choose your player");
        }
        repaint();
    }

    private void checkGameOver() {
        if (fleet.gameOver() != 0) {
            playing = false;
            if (fleet.gameOver() == 1) {
                engine.getStatus().setText("Game over, you won!");
            } else {
                engine.getStatus().setText("Game over, you lost!");
            }
            engine.getPause().setVisible(false);
            engine.getSave().setVisible(false);
            engine.getReset().setVisible(true);

        }
    }

    void tick() {
        if (playing) {
            player.move();
            Iterator<Bullet> iter = bullets.iterator();
            while (iter.hasNext()) {
                Bullet curr = iter.next();
                curr.move();
                if (curr.hitWall() != null) {
                    iter.remove();
                } else if (fleet.hit(curr)) {
                    iter.remove();
                }
            }
            repaint();
        }
    }

    public void moveFleet() {
        if (playing) {
            checkGameOver();
            fleet.move();
            fleetTimer.setDelay(fleet.getTimeFrame());

        }
    }

    public void togglePause() {
        if (!paused) {
            paused = true;
            playing = false;
            engine.getPause().setText("Resume");
            engine.getReset().setVisible(true);
            engine.getSave().setVisible(true);
            engine.getStatus().setText(("Paused!"));
        } else {
            paused = false;
            playing = true;
            engine.getPause().setText("Pause");
            engine.getReset().setVisible(false);
            engine.getSave().setVisible(false);
            engine.getStatus().setText(("Running..."));
            this.requestFocus();
        }
    }

    public void save() {
        String time = java.time.LocalDateTime.now().toString().split("\\.")[0];
        time = time.replace(":", ".");
        String name = time + ".raj";
        File file = Paths.get("files/saved/" + name).toFile();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
            String line = player.getType() + "," + player.getPx() + "," + player.getPy() + "\n";
            bw.write(line);
            bw.flush();

            bw.write(
                    fleet.getTimeFrame() + "," + fleet.getMovingRight() + "," + fleet.getPosX()
                            + "\n"
            );
            bw.flush();
            fleet.recordState(bw);

            for (Bullet curr : bullets) {
                line = curr.getPower() + "," + curr.getVx() + "," + curr.getVy() + "," +
                        curr.getPx() + "," + curr.getPy() + "\n";
                bw.write(line);
                bw.flush();
            }
            bw.close();
            engine.getStatus().setText(("Game Saved at " + time.split("T")[1] + "!"));
        } catch (IOException e) {
            System.out.println("Writing Error");
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (pregame) {
            setBackground(new Color(2, 9, 41));
        } else {
            g.drawImage(background, 0, 0, this);
            player.draw(g);
            fleet.draw(g);
            for (Bullet curr : bullets) {
                curr.draw(g);
            }
        }

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }

}
