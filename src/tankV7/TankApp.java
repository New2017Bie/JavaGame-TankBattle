/**********************************************
 * Project: tank
 * Author: Bo Yu
 * Date: 2/28/18
 * Time: 3:53 AM
 ***********************************************/

package tankV7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

public class TankApp extends JFrame implements KeyListener{

    private GamePanel mp = null;
    private static int gameWidth = 850;
    private static int gameHeight = 800;
    private static boolean isInGame = false;
    private Thread mt = null;
    private WelcomeScreen ws = null;
    private Audio audio = null;

    public TankApp() {

        // Welcome scene
        GameRecorder.loadRecord();
        ws = new WelcomeScreen();
        this.add(ws);

        audio = new Audio("Sound/welcome.wav");
        audio.play();

        // Menu options
        JMenuBar jmb = new JMenuBar();
        JMenu jmG = new JMenu("Game (G)");
        JMenu jmSL = new JMenu("Save & Load (S)");
        JMenuItem jmiStart = new JMenuItem("Start Game (N)");
        JMenuItem jmiReturn = new JMenuItem("Return (R)");
        JMenuItem jmiExit = new JMenuItem("Exit (E)");
        JMenuItem jmiSave = new JMenuItem("Save & Exit (A)");
        JMenuItem jmiLoad = new JMenuItem("Load Game (L)");

        // Hot keys
        jmSL.setMnemonic('S');
        jmiStart.setMnemonic('N');
        jmiSave.setMnemonic('A');
        jmiLoad.setMnemonic('L');
        jmiExit.setMnemonic('E');
        jmG.setMnemonic('G');
        jmiStart.setMnemonic('S');
        jmiReturn.setMnemonic('R');

        jmb.add(jmG);
        jmG.add(jmiStart);
        jmG.add(jmiExit);
        jmG.add(jmiReturn);

        jmb.add(jmSL);
        jmSL.add(jmiSave);
        jmSL.add(jmiLoad);

        this.addKeyListener(this);

        jmiReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                back();
            }
        });

        // Add actions
        // Exit
        jmiExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameRecorder.saveRecord();
                System.exit(0);
            }
        });

        // Start Game
        jmiStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                gameStart();
            }
        });

        jmiSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameRecorder.setEts(mp.getEts());
                GameRecorder.saveGame();
                GameRecorder.saveRecord();
                System.exit(0);
            }
        });

        jmiLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameRecorder.loadGame();
                gameStart();
            }
        });

        this.setTitle("Battle of Tanks");
        this.setResizable(false);
        this.setJMenuBar(jmb);
        this.setVisible(true);
        this.setBounds(100, 100, gameWidth + 250, gameHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Back to welcome screen
    private void back() {
        setIsInGame(false);
        audio = new Audio("Sound/welcome.wav");
        audio.play();
        ws = new WelcomeScreen();
        this.remove(mp);
        mp = null;
        this.addKeyListener(this);
        this.add(ws);
        this.setVisible(true);
        this.repaint();
        this.revalidate();
    }

    // Start the game
    public void gameStart() {
        setIsInGame(true);

        WelcomeScreen.run = false;

        this.removeKeyListener(this);

        // Stage scene
        ws.stage();
        this.revalidate();
        this.repaint();

        // Game start
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                remove(ws);
                ws = null;

                mp = new GamePanel();
                mt = new Thread(mp);
                mt.start();
                add(mp);
                repaint();
                revalidate();
                addKeyListener(mp);
                mp.setGameOver(false);
            }
        }, 3000);
    }

    public static int getGameWidth() {
        return gameWidth;
    }

    public static int getGameHeight() {
        return gameHeight;
    }

    public static boolean isIsInGame() {
        return isInGame;
    }

    public static void setIsInGame(boolean isInGame) {
        isInGame = isInGame;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (ws != null) {
            if (e != null) {
                gameStart();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static void main(String[] args) {
        TankApp td = new TankApp();
    }
}


