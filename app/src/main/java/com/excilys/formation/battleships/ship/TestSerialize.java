package com.excilys.formation.battleships.ship;

import com.excilys.formation.battleships.BattleShipsAI;
import com.excilys.formation.battleships.Board;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.excilys.formation.battleships.Game.SAVE_FILE;


/**
 * Created by nicolas on 22/09/16.
 */
public class TestSerialize {
    static class Computer implements Serializable {
        private List<Device> devices = new LinkedList<>();

        public Computer(Device... device) {
            this.devices.addAll(Arrays.asList(device));
        }
    }

    static abstract class Device implements Serializable {
        private String name;

        public Device(String name) {
            this.name = name;
        }
    }

    static class Screen extends Device implements Serializable {
        public Screen() {
            super("screen");
        }
    }

    static class Keyboard extends Device implements Serializable {
        public Keyboard() {
            super("keyboard");
        }
    }

    public static void main(String[] args) {
        Computer c = new Computer(new Keyboard(), new Screen());
        File saveFile = new File("saveFile");
        saveComputer(c, saveFile);
        c = loadComputer(saveFile);
    }

    private static Computer loadComputer(File saveFile) {
        Computer computer = null;
        if (saveFile.exists()) {
            try {
                ObjectInputStream ois =
                        new ObjectInputStream(new FileInputStream(saveFile));

                computer = (Computer) ois.readObject();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return computer;
    }

    public static void saveComputer(Computer c, File saveFile) {
        try {
            if (!saveFile.exists()) {
                saveFile.getAbsoluteFile().getParentFile().mkdirs();
            }
            FileOutputStream out = new FileOutputStream(saveFile);
            ObjectOutputStream oout = new ObjectOutputStream(out);
            oout.writeObject(c);
            oout.flush();
            oout.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
