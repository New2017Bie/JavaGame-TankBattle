/**********************************************
 * Project: tankv3
 * Author: Bo Yu
 * Date: 3/3/18
 * Time: 12:44 AM
 ***********************************************/

package tankv4_stages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener, Runnable {

    private Hero hero = null;
    private int enSize = GameRecorder.getNumNPC();
    private Vector<EnemyTank> ets = new Vector<>();
    private Vector<Explosion> explosions = new Vector<>();
    private Set<Integer> pressed = new HashSet<>();
    private Vector<Node> nodes = new Vector<>();
    private static boolean isNewGame = true;
    private String bgm = "Sound/BGM.wav";
    private String fireSound = "Sound/fire.wav";
    private String explosionSound = "Sound/explosion.wav";
    private Audio audio;

    public MyPanel () {

        // Play BGM
        audio = new Audio(bgm);
        audio.play();

        if (isNewGame()) {
            // new hero
            hero = new Hero(400, 500, 0, 5);
            initializeNPC();
        } else {
            setNodes(GameRecorder.getNodes());
            hero = new Hero(400, 500, 0, 5);
            for (Node el: getNodes()) {
                EnemyTank et = new EnemyTank(el.getX(), el.getY(), 1, el.getSpeed());
                et.setDirection(el.getDirection());
                ets.add(et);
                et.fire();

                Thread thread = new Thread(et);
                thread.start();
            }
        }

        this.setBounds(0, 0, TankApp.getGameWidth(), TankApp.getGameHeight());
    }

    private void tankDied(Tank t) {
        t.setAlive(false);

        Explosion exp = new Explosion(t.getX() - 26, t.getY() - 28);
        explosions.add(exp);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // game canvas
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, TankApp.getGameWidth(), TankApp.getGameHeight());

        // show game status
        showInfo(g);


        // draw hero

        if (hero != null) {
            if (hero.isAlive()) drawTank(hero.getX(), hero.getY(), g, hero.getDirection(), hero.getColor());
            else hero = null;
        } else if (GameRecorder.getMyLife() > 0) {
            hero = new Hero(700, 200, 0, 5);
            GameRecorder.setMyLife(GameRecorder.getMyLife() - 1);
        } else {
            for (EnemyTank el: ets) {
                el = null;
            }
        }

        // explosion
        explode(g);

        // draw enemies
        for (EnemyTank el: ets) {
            this.drawTank(el.getX(), el.getY(), g, el.getDirection(), el.getColor());
        }
    }

    private void initializeNPC() {
        EnemyTank et = null;
        // new enemies and their threads
        for (int i = 0; i < enSize; i++) {
            if (i < 12) {
                et = new EnemyTank((i + 1) * 70, 70, 1, 1);
            } else {
                et = new EnemyTank(((i - 12) + 1) * 70, 180, 1, 1);
            }
            ets.add(et);
            et.setEts(ets);
            et.fire();

            Thread thread = new Thread(et);
            thread.start();
        }
    }

    private void showInfo(Graphics g) {
        // status console
        this.drawTank(900, 120, g, 0, 1);
        this.drawTank(900, 190, g, 0, 0);
        this.drawTank(900, 350, g, 0, 1);
        this.drawTank(900, 540, g, 0, 1);

        g.setColor(Color.BLACK);
        g.setFont(new Font("BLACK", Font.PLAIN, 30));
        g.drawString(" X " + GameRecorder.getTotalNPC() + "", 940, 130);
        g.drawString(" X " + GameRecorder.getMyLife() + "", 940, 200);
        g.drawString(" X " + GameRecorder.getNumDeadNPC() + "", 940, 360);
        g.drawString(" X " + GameRecorder.getRecord() + "", 940, 550);


        g.setColor(new Color(170, 0,0));
        g.setFont(new Font("BLACK", Font.BOLD, 40));
        g.drawString("Score:", 860, 290);
        g.drawString("Status:", 860, 60);
        g.drawString("Highest", 860, 450);
        g.drawString("Score:", 860, 490);

    }

    public void hitTank(Bullet b, Tank t) {
        switch(t.getDirection()) {
            case 0:
            case 1:
                if (b.getX() > t.getX() -23 && b.getX() < t.getX() + 23
                        && b.getY() > t.getY() - 25 && b.getY() < t.getY() + 25) {

                    b.setAlive(false);
                    tankDied(t);
                }
                break;
            case 2:
            case 3:
                if (b.getX() > t.getX() - 25 && b.getX() < t.getX() + 25
                        && b.getY() > t.getY() - 23 && b.getY() < t.getY() + 23) {

                    b.setAlive(false);
                    tankDied(t);
                }
                break;
        }
    }

    // explosion effect
    private void explode(Graphics g) {
        for (int i = explosions.size() - 1; i >= 0; i--) {
            Explosion el = explosions.get(i);
            if (el.getTimer() > 6) {
                g.setColor(new Color(255, 128, 0));
                g.fillOval(el.getX(), el.getY(), 55, 55);
                try {
                    Thread.sleep(90);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (el.getTimer() > 3) {
                g.setColor(new Color(255, 218, 28));
                g.fillOval(el.getX() + 8, el.getY() + 8, 40, 40);
                try {
                    Thread.sleep(90);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                g.setColor(new Color(255, 254, 208));
                g.fillOval(el.getX() + 15, el.getY() + 13, 20, 20);
                try {
                    Thread.sleep(90);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            audio = new Audio(explosionSound);
            audio.play();
            el.countDown();
            if (el.getTimer() == 0) {
                explosions.remove(el);
            }
        }
    }

    // generic way to draw tanks
    private void drawTank(int x, int y, Graphics g, int direction, int type) {
        // 0 as hero and 1 as enemy
        switch (type) {
            case 0:
                g.setColor(Color.ORANGE);
                break;
            case 1:
                g.setColor(Color.CYAN);
                break;
        }

        // draw tanks in 4 directions
        switch (direction) {
            case 0:
                g.fill3DRect(x - 23, y - 25, 10, 50, false);
                g.fill3DRect(x + 14, y - 25, 10, 50,false);
                g.fill3DRect(x - 13, y - 15, 27, 30, false);
                g.fillOval(x - 9, y - 10, 18, 18);
                g.fillRect(x -1, y - 25, 3, 15 );
                for (int i = 1; i < 5; i ++) g.drawLine(x - 23, y -25 + 10 * i, x - 14, y -25 + 10 * i);
                for (int i = 1; i < 5; i ++) g.drawLine(x + 14, y -25 + 10 * i, x + 23, y -25 + 10 * i);

                break;
            case 1:
                g.fill3DRect(x - 23, y - 25, 10, 50, false);
                g.fill3DRect(x + 14, y - 25, 10, 50,false);
                g.fill3DRect(x - 13, y - 15, 27, 30, false);
                g.fillOval(x - 9, y - 9, 18, 18);
                g.fillRect(x -1, y + 10, 3, 15 );
                for (int i = 1; i < 5; i ++) g.drawLine(x - 23, y -25 + 10 * i, x - 14, y -25 + 10 * i);
                for (int i = 1; i < 5; i ++) g.drawLine(x + 14, y -25 + 10 * i, x + 23, y -25 + 10 * i);

                break;
            case 2:
                g.fill3DRect(x - 25, y - 23, 50, 10, false);
                g.fill3DRect(x -25 , y + 14, 50, 10,false);
                g.fill3DRect(x - 15, y - 13, 30, 27, false);
                g.fillOval(x - 10, y - 9, 18, 18);
                g.fillRect(x - 25, y - 1, 15, 3 );
                for (int i = 1; i < 5; i ++) g.drawLine(x -25 + 10 * i, y -23, x -25 + 10 * i, y - 14);
                for (int i = 1; i < 5; i ++) g.drawLine(x -25 + 10 * i, y + 14, x -25 + 10 * i, y + 23);

                break;
            case 3:
                g.fill3DRect(x - 25, y - 23, 50, 10, false);
                g.fill3DRect(x - 25, y + 14, 50, 10,false);
                g.fill3DRect(x - 15, y - 13, 30, 27, false);
                g.fillOval(x - 9, y - 9, 18, 18);
                g.fillRect(x + 10, y - 1, 15, 3 );
                for (int i = 1; i < 5; i ++) g.drawLine(x -25 + 10 * i, y -23, x -25 + 10 * i, y - 14);
                for (int i = 1; i < 5; i ++) g.drawLine(x -25 + 10 * i, y + 14, x -25 + 10 * i, y + 23);

                break;
        }

        // hero fires
        if (hero != null && hero.getBullet() != null && hero.getBullet().isAlive()) {
            g.setColor(Color.WHITE);

            for (Bullet el: hero.getBv()) {
                g.fill3DRect(el.getX(), el.getY(), 3, 3, true);
            }
        }

        // enemy fires
        for (EnemyTank el: ets) {
            if (el.getBullet() != null && el.getBullet().isAlive()) {
                g.setColor(Color.WHITE);

                for (EnemyTank el1: ets) {
                    for (Bullet el2: el.getBv()) {
                        g.fill3DRect(el2.getX(), el2.getY(), 3, 3, true);
                    }
                }
            }
        }
    }

    // to be continued
    public void pauseResume() {

    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Vector<EnemyTank> getEts() {
        return ets;
    }

    public void setEts(Vector<EnemyTank> ets) {
        this.ets = ets;
    }

    public Vector<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Vector<Node> nodes) {
        this.nodes = nodes;
    }

    public boolean isNewGame() {
        return isNewGame;
    }

    public static void setNewGame(boolean newGame) {
        isNewGame = newGame;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // hero control
    @Override
    public void keyPressed(KeyEvent e) {
        pressed.add(e.getKeyCode());

        if (hero != null) {
            // control directions
            if (pressed.contains(KeyEvent.VK_DOWN)) {
                hero.setDirection(1);
                hero.moveDown();
            }
            if (pressed.contains(KeyEvent.VK_LEFT)) {
                    hero.setDirection(2);
                    hero.moveLeft();
            }
            if (pressed.contains(KeyEvent.VK_RIGHT)) {
                    hero.setDirection(3);
                    hero.moveRight();
            }
            if (pressed.contains(KeyEvent.VK_UP)) {
                    hero.setDirection(0);
                    hero.moveUp();
            }
//            if (pressed.contains(KeyEvent.VK_ESCAPE)) {
//                    if (!pause) {
//                        pause = true;
//                    } else pause = false;
//                    pauseResume();
//            }
            if (pressed.contains(KeyEvent.VK_SPACE)) {
                hero.fire();
                audio = new Audio(fireSound);
                audio.play();
            }

            // control shot
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        if (e.getKeyCode() == KeyEvent.VK_SPACE) hero.fire();
        pressed.remove(e.getKeyCode());
    }

    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            // judge if there's any tanks hit
            for (EnemyTank el: ets) {
                if (hero != null) {
                    for (Bullet b : hero.getBv()) hitTank(b, el);
                    for (Bullet el1 : el.getBv()) hitTank(el1, hero);
                }
            }

            // remove dead enemy tanks
            for (int i = ets.size() - 1; i >= 0; i--) {
                if (!ets.get(i).isAlive()) {
                    ets.remove(i);
                    // Total NPC number decreases as one NPC dies
                    GameRecorder.setTotalNPC(GameRecorder.getTotalNPC() - 1);
                    GameRecorder.setNumDeadNPC(GameRecorder.getNumDeadNPC() + 1);
                }
            }

            // remove hero dead bullets
            if (hero != null) {
                if (hero.isTouched()) {
                    tankDied(hero
                    );
                }
                for (int i = hero.getBv().size() - 1; i >= 0; i--) {
                    if (!hero.getBv().get(i).isAlive()) hero.removeBullet(i);
                }
            }

            // remove enemy dead bullets and fire
            for (int j = ets.size() - 1; j >= 0; j--) {
                for (int i = ets.get(j).getBv().size() - 1; i >= 0; i--) {
                    if (!ets.get(j).getBv().get(i).isAlive()) {
                        ets.get(j).removeBullet(i);
                        ets.get(j).fire();
                    }
                }
            }

            repaint();
        }
    }
}
