/**********************************************
 * Project: tank
 * Author: Bo Yu
 * Date: 2/28/18
 * Time: 3:53 AM
 ***********************************************/

package tankv4_stages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TankApp extends JFrame{

    private StagePanel sp = null;
    private MyPanel mp = null;
    private static int gameWidth = 850;
    private static int gameHeight = 800;
    private boolean isInGame = false;


    public TankApp() {

        // Stage scene
        GameRecorder.loadRecord();
        sp = new StagePanel();
        Thread stageT = new Thread(sp);
        stageT.start();
        this.add(sp);

        // Menu options
        JMenuBar jmb = new JMenuBar();
        JMenu jmG = new JMenu("Game (G)");
        JMenu jmSL = new JMenu("S/L (S)");
        JMenuItem jmiStart = new JMenuItem("Start Game (N)");
        JMenuItem jmiExit = new JMenuItem("Exit (E)");
        JMenuItem jmiSave = new JMenuItem("Save & Exit (S)");
        JMenuItem jmiLoad = new JMenuItem("Load Game (L)");
        // Hot keys
        jmSL.setMnemonic('S');
        jmiStart.setMnemonic('N');
        jmiSave.setMnemonic('S');
        jmiLoad.setMnemonic('L');
        jmiExit.setMnemonic('E');
        jmG.setMnemonic('G');
        jmiStart.setMnemonic('S');

        jmb.add(jmG);
        jmG.add(jmiStart);
        jmG.add(jmiExit);

        jmb.add(jmSL);
        jmSL.add(jmiSave);
        jmSL.add(jmiLoad);

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
                if (!isInGame) {
                    gameStart();
                }
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
            }
        });

        this.setTitle("Battle of Tanks");
        this.setResizable(false);
        this.setJMenuBar(jmb);
        this.setVisible(true);
        this.setBounds(100, 100, gameWidth + 250, gameHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void gameStart() {
        mp = new MyPanel();

        Thread mt = new Thread(mp);

        mt.start();
        mp.setBackground(Color.WHITE);
        this.remove(sp);
        this.add(mp);
        this.addKeyListener(mp);
        this.setVisible(true);
        isInGame = true;
    }

    public static int getGameWidth() {
        return gameWidth;
    }

    public static int getGameHeight() {
        return gameHeight;
    }

    public static void main(String[] args) {
        TankApp td = new TankApp();
    }
}


