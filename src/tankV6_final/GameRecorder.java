/**********************************************
 * Project: tankv4_stages
 * Author: Bo Yu
 * Date: 3/4/18
 * Time: 9:37 PM
 ***********************************************/

package tankV6_final;

import java.io.*;
import java.util.Arrays;
import java.util.Vector;

public class GameRecorder {

    private static File recorder = new File("Saves/record.sav");
    private static File game = new File("Saves/game.sav");
//    private static int numNPC = 6;
    private static int numDeadNPC = 0;
    private static int totalNPC = 6;
    private static int myLife = 1;
    private static int record = 0;
    private static Vector<EnemyTank> ets = new Vector<>();
    private static Vector<Node> nodes = new Vector<>();

    public static void reset() {
//        setNumNPC(5);
        setNumDeadNPC(0);
        setTotalNPC(6);
        setMyLife(1);
    }

    // save a game
    public static void saveGame() {
        FileWriter fw = null;
        BufferedWriter bw = null;
        String xyds = null;

        try {
            fw = new FileWriter(game);
            bw = new BufferedWriter(fw);

//            bw.write("NPC:\r\n");

            // Record NPC info
            for (int i = 0; i < ets.size(); i++) {
                EnemyTank et = ets.get(i);

                // Save NPC
                if (et.isAlive()) {
                    xyds = "0 " + et.getX() + " " + et.getY() + " " + et.getDirection() + " " + et.getSpeed();
                    bw.write(xyds + "\r\n");
                }
            }

            // Save hero
//            bw.write("Hero:\r\n");
             xyds = "1 " + GamePanel.getHero().getX() + " " + GamePanel.getHero().getY() + " " +
                    GamePanel.getHero().getDirection() + " " + GamePanel.getHero().getSpeed();
            bw.write(xyds + "\r\n");

            // Save Bullets
//            bw.write("Bullet1: \r\n");
//            for (EnemyTank et: ets) {
//                if (et.isAlive()) {
//                    for (Bullet b: et.getBv()) {
//                        xyds = "2 " + b.getX() + " " + b.getY() + " " + b.getDirection() + " " + b.getSpeed();
//                        bw.write(xyds + "\r\n");
//                    }
//                }
//            }

//            bw.write("Buttlet2:\r\n");
//            for (Bullet b: GamePanel.getHero().getBv()) {
//                xyds = "3 " + b.getX() + " " + b.getY() + " " + b.getDirection() + " " + b.getSpeed();
//                bw.write(xyds + "\r\n");
//            }

//            bw.write("Bricks:\r\n");
            for (Material m: GamePanel.getMat()) {
                if (m.getClass().getSimpleName().equals("Brick")) {
                    xyds = "4 " + m.getX() + " " + m.getY() + " 0" + " 0";
                    bw.write(xyds + "\r\n");
                }
            }

            xyds = "5 " + GameRecorder.getTotalNPC() + " " + GameRecorder.getMyLife() + " " +
                    GameRecorder.getNumDeadNPC() + " 0";
            bw.write(xyds + "\r\n");


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // Load a saved game
    public static void loadGame() {
        FileReader fr = null;
        BufferedReader br = null;

        GamePanel.setLoadGame(true);

        try {
            fr = new FileReader(game);
            br = new BufferedReader(fr);
            String temp = "";

            while ((temp = br.readLine()) != null) {
                String[] xyds = temp.split(" ");
                System.out.println(Arrays.toString(xyds));
                nodes.add(new Node(Integer.valueOf(xyds[0]), Integer.valueOf(xyds[1]),
                        Integer.valueOf(xyds[2]), Integer.valueOf(xyds[3]), Integer.valueOf(xyds[4])));
            }

            GamePanel.setGameOver(false);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fr.close();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Save the highest record to file
    public static void saveRecord() {
        FileWriter fw = null;
        BufferedWriter bw = null;

        try {
            fw = new FileWriter(recorder);
            bw = new BufferedWriter(fw);

            bw.write(getRecord() + "");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Load the record from file
    public static void loadRecord() {
        FileReader fr = null;
        BufferedReader br = null;

        try {
            fr = new FileReader(recorder);
            br = new BufferedReader(fr);
            String temp = "";

            if ((temp = br.readLine()) != null) {
                setRecord(Integer.valueOf(temp));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fr.close();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getRecord() {
        if (getNumDeadNPC() > record) {
            record = getNumDeadNPC();
            setRecord(record);
        }

        return record;
    }

    public static void setRecord(int record) {
        GameRecorder.record = record;
    }

//    public static int getNumNPC() {
//        return numNPC;
//    }
//
//    public static void setNumNPC(int numNPC) {
//        GameRecorder.numNPC = numNPC;
//    }

    public static int getNumDeadNPC() {
        return numDeadNPC;
    }

    public static void setNumDeadNPC(int numDeadNPC) {
        GameRecorder.numDeadNPC = numDeadNPC;
    }

    public static int getTotalNPC() {
        return totalNPC;
    }

    public static void setTotalNPC(int totalNPC) {
        GameRecorder.totalNPC = totalNPC;
    }

    public static int getMyLife() {
        return myLife;
    }

    public static void setMyLife(int myLife) {
        GameRecorder.myLife = myLife;
    }

    public static Vector<EnemyTank> getEts() {
        return ets;
    }

    public static void setEts(Vector<EnemyTank> e) {
        ets = e;
    }

    public static Vector<Node> getNodes() {
        return nodes;
    }
}

class Node {
    int x;
    int y;
    int direction;
    int speed;
    int type;

    public Node(int type, int x, int y, int direction, int speed) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}