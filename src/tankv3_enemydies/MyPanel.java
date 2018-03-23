/**********************************************
 * Project: tankv3
 * Author: Bo Yu
 * Date: 3/3/18
 * Time: 12:44 AM
 ***********************************************/

package tankv3_enemydies;

import tankv4_stages.GameRecorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener, Runnable {

    Hero hero = null;
    int enSize = 10;
    Vector<EnemyTank> ets = new Vector<>();
    Vector<Explosion> explosions = new Vector<>();

    public MyPanel () {

        // new hero
        hero = new Hero(700, 200, 0, 5);

        // new enemies and their threads
        for (int i = 0; i < enSize; i++) {
            EnemyTank et = new EnemyTank((i + 1) * 70, 70, 1, 1);
            ets.add(et);

            et.setEts(ets);

            et.fire();

            Thread thread = new Thread(et);
            thread.start();
        }
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

    private void tankDied(Tank t) {
        t.setAlive(false);

        Explosion exp = new Explosion(t.getX() - 26, t.getY() - 28);
        explosions.add(exp);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // draw hero

        if (hero != null) {
            if (hero.isAlive()) drawTank(hero.getX(), hero.getY(), g, hero.getDirection(), hero.getColor());
            else hero = null;
        } else if (GameRecorder.getMyLife() > 0) {
            hero = new Hero(700, 200, 0, 5);
            GameRecorder.setMyLife(GameRecorder.getMyLife() - 1);
        } else {
            JOptionPane.showMessageDialog(this, "Sorry, game over! Would you want another game?", "Game Over", JOptionPane.PLAIN_MESSAGE);
        }

        explode(g);

        // draw enemies
        for (EnemyTank el: ets) {
            this.drawTank(el.getX(), el.getY(), g, el.getDirection(), el.getColor());
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // hero control
    @Override
    public void keyPressed(KeyEvent e) {
        int pressed = e.getKeyCode();

        if (hero != null) {
            // control directions
            switch (pressed) {
                case KeyEvent.VK_DOWN:
                    hero.setDirection(1);
                    hero.moveDown();
                    break;
                case KeyEvent.VK_LEFT:
                    hero.setDirection(2);
                    hero.moveLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    hero.setDirection(3);
                    hero.moveRight();
                    break;
                case KeyEvent.VK_UP:
                    hero.setDirection(0);
                    hero.moveUp();
                    break;
            }

            // control shot
            if (e.getKeyCode() == KeyEvent.VK_SPACE) hero.fire();
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

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
                if (!ets.get(i).isAlive()) ets.remove(i);
            }

            // remove hero dead bullets
            if (hero != null) {
                if (hero.isTouched()) {
                    tankDied(hero);
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
