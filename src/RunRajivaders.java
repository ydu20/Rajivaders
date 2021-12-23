
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class RunRajivaders implements Runnable {

    private JLabel status;
    private JButton reset;
    private JButton pause;
    private JButton save;
    private JButton ta;
    private JButton rajiv;
    private JButton student;
    private JPanel controlPanel;
    private String saved;

    public void run() {
        final JFrame frame = new JFrame("Rajivaders");
        frame.setLocation(300, 300);

        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        status = new JLabel("Running...");
        status_panel.add(status);

        final Space space = new Space(this);
        frame.add(space, BorderLayout.CENTER);
        space.setBackground(new Color(2, 9, 41));

        controlPanel = new JPanel();
        frame.add(controlPanel, BorderLayout.NORTH);

        reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                space.reset();
            }
        });
        controlPanel.add(reset);

        pause = new JButton("Pause");
        pause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                space.togglePause();
            }
        });
        controlPanel.add(pause);

        save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                space.save();
            }
        });
        controlPanel.add(save);

        student = new JButton("Student");
        student.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                space.startGame(0);
            }
        });
        controlPanel.add(student);

        ta = new JButton("TA");
        ta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                space.startGame(1);
            }
        });
        controlPanel.add(ta);

        rajiv = new JButton("Rajiv");
        rajiv.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                space.startGame(2);
            }
        });
        controlPanel.add(rajiv);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        final JFrame instructions = new JFrame("Game Instructions");
        instructions.setLocation(425, 350);

        String inst = "<html><body width='400'><h2>Game Instructions:</h2>" +
                "<p> - Space Invaders, but instead of aliens, there are 160 PSets and Exams!" +
                "<P> - Destroy the fleet of homework before it gets to you! It picks up " +
                "speed as it gets closer!";
        inst = inst + "<p> - Control your player with left/right arrow keys, and press " +
                "space to shoot." +
                "<p> - Different types of players have different powers and shoot differently: " +
                "<p> ---- Student: 15 power and shoots a single bullet" +
                "<p> ---- TA: 10 power but shoots 3 bullets at a time" +
                "<p> ---- Rajiv: We'll let you figure this one out ;)" +
                "<p> - When a homework gets hit, it goes up in score (what?). It disappears " +
                "once it reaches ";
        inst = inst + "full score." +
                "<p> - You can save an unfinished game! The game file will be stored in the " +
                "files/saved folder. ";
        inst = inst +
                "When you want to load a game file in that folder, put its name (along " +
                "with the extension, " +
                "but not the file path) in the argument when starting this program." +
                "<p>";

        if (saved == null) {
            inst = inst + "<h3> Now you're ready! Choose your player to start the game!</h3><p>";
        } else {
            inst = inst + "<h3> Your game has been loaded! Click Resume to keep winning!</h3><p>";
        }

        JLabel instLabel = new JLabel(inst);

        instructions.add(instLabel, BorderLayout.CENTER);
        instructions.add(new JLabel("     "), BorderLayout.WEST);
        instructions.add(new JLabel("     "), BorderLayout.EAST);
        instructions.pack();
        instructions.setVisible(true);

        // Start game
        if (saved == null) {
            space.reset();
        } else {
            space.reset(saved);
        }
    }

    public void setSavedGame(String s) {
        saved = s;
    }

    public JLabel getStatus() {
        return status;
    }

    public JButton getReset() {
        return reset;
    }

    public JButton getPause() {
        return pause;
    }

    public JButton getSave() {
        return save;
    }

    public JButton getTa() {
        return ta;
    }

    public JButton getRajiv() {
        return rajiv;
    }

    public JButton getStudent() {
        return student;
    }

    public JPanel getControlPanel() {
        return controlPanel;
    }

    public static void main(String[] args) {

        RunRajivaders game = new RunRajivaders();
        if (args.length == 1) {
            game.setSavedGame("files/saved/" + args[0]);
        }
        SwingUtilities.invokeLater(game);
    }

}
