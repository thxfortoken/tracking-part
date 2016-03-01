package full.work.Controll;

/*
 * Copyright (c) 2015.  Vladimir Kartyaev
 *
 */

import full.work.Model.Coordinate;
import full.work.Model.CoordinateList;

import java.io.*;


public class Serializator {
    public void serialization(CoordinateList serCoordinateList, String filePath) {
        try {
            this.deletePoints(serCoordinateList);
            FileOutputStream fos = new FileOutputStream(filePath + "items.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(serCoordinateList);
            oos.close();
            fos.close();
            System.out.printf("Serialized data is saved in" + filePath + "items.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public CoordinateList deSerialization(String filePath) throws IOException, ClassNotFoundException {
        if (new File(filePath).exists()) {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            CoordinateList deSerCoordinateList = (CoordinateList) ois.readObject();
            ois.close();
            fis.close();
            return deSerCoordinateList;
        } else {
            System.out.println("File not found, please, try again.");
            return null;
        }
    }

    public void deletePoints(CoordinateList list) {
        int i = 0;
        Coordinate max = null;
        for (Coordinate coordinate : list.coordinateList) {
            i++;
            max = coordinate;
        }
        //Coordinate temp;


        int probablyfalse;
        probablyfalse = (int) (i * 0.10);
        while (probablyfalse != 0) {
            for (Coordinate coordinate : list.coordinateList) {
                if (coordinate.getNewX() > max.getNewX() && coordinate.getNewY() > max.getNewY() && coordinate.getNewZ() > max.getNewZ()) {
                    max = coordinate;
                }
            }
            list.removeCoordinate(max);
            probablyfalse--;
            max.setNewY(-1);
            max.setNewZ(-1);
            max.setNewX(-1);
        }
    }
}

