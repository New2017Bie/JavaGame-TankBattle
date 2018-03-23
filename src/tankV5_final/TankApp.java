/**********************************************
 * Project: tank
 * Author: Bo Yu
 * Date: 2/28/18
 * Time: 3:53 AM
 ***********************************************/

package tankV5_final;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TankApp extends JFrame implements KeyListener{

    private StagePanel sp = null;
    private GamePanel mp = null;
    private static int gameWidth = 850;
    private static int gameHeight = 800;
    private static boolean isInGame = false;
    private Thread mt = null;
    private WelcomeScreen ws = null;
    private Audio audio = null;

    public TankApp() {

        // Stage scene
        GameRecorder.loadRecord();
        ws = new WelcomeScreen();
        Thread stageT = new Thread(ws);
        stageT.start();
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

    private void back() {
        setIsInGame(false);
        audio = new Audio("Sound/welcome.wav");
        audio.play();
        ws = new WelcomeScreen();
        mt = new Thread(ws);
        mt.start();
        this.remove(mp);
        mp = null;
        this.addKeyListener(this);
        this.add(ws);
        this.setVisible(true);
        this.repaint();
        this.revalidate();
    }


    public void gameStart() {
        GameRecorder.reset();

        this.removeKeyListener(this);


        sp = new StagePanel();
        mt = new Thread(sp);
        mt.start();
        ws.setVisible(false);
        ws.repaint();
        ws.revalidate();
        this.add(sp);
        sp.setVisible(true);
        sp.repaint();
        sp.revalidate();
        this.revalidate();
        this.repaint();
        ws = null;

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        this.remove(ws);
        this.remove(sp);
        mp = new GamePanel();
        mt = new Thread(mp);
        mt.start();
        this.add(mp);
        this.repaint();
        this.revalidate();
        this.addKeyListener(mp);
        mp.setGameOver(false);
        sp = null;
        setIsInGame(true);
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
        TankApp.isInGame = isInGame;
    }

    public static void main(String[] args) {
        TankApp td = new TankApp();
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
}


