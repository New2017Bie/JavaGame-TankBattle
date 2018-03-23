/**********************************************
 * Project: tank
 * Author: Bo Yu
 * Date: 2/28/18
 * Time: 3:53 AM
 ***********************************************/

package tankv2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class TankDemo1 extends JFrame{

    MyPanel mp = null;

    public TankDemo1 () {
        mp = new MyPanel();

        Thread mt = new Thread(mp);

        mt.start();
        mp.setBackground(Color.BLACK);
        this.add(mp);
        this.setVisible(true);
        this.setBounds(100, 100, 600, 600);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        TankDemo1 td = new TankDemo1();
    }
}

class MyPanel extends JPanel implements KeyListener, Runnable {

    Hero hero = null;
    int enSize = 3;
    Vector<EnemyTank> ets = new Vector<>();

    public MyPanel () {
        hero = new Hero(200, 200, 0, 5);

        for (int i = 0; i < 3; i++) {
            EnemyTank et = new EnemyTank((i + 1) * 70, 70, 1, 5);
            ets.add(et);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        drawTank(hero.getX(), hero.getY(), g, hero.getDirection(), hero.getColor());

        for (EnemyTank el: ets) this.drawTank(el.getX(), el.getY(), g, el.getDirection(), el.getColor());
    }

    private void drawTank(int x, int y, Graphics g, int direction, int type) {

        switch (type) {
            case 0:
                g.setColor(Color.ORANGE);
                break;
            case 1:
                g.setColor(Color.CYAN);
                break;
        }

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

        if (hero.getBullet() != null && hero.getBullet().isAlive()) {
            g.setColor(Color.WHITE);

            for (Bullet el: hero.getBv()) {
                g.fill3DRect(el.getX(), el.getY(), 3, 3, true);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int pressed = e.getKeyCode();

        switch(pressed) {
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

        if(e.getKeyCode() == KeyEvent.VK_SPACE) hero.shot();

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            repaint();
        }
    }
}

