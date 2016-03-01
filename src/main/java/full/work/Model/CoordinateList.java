package full.work.Model;

/*
 * Copyright (c) 2015.  Vladimir Kartyaev
 *
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArraySet;


public class CoordinateList implements Serializable {
    public CopyOnWriteArraySet<Coordinate> coordinateList;

    public CoordinateList() {
        this.coordinateList = new CopyOnWriteArraySet<Coordinate>(new ArrayList<Coordinate>());
    }

    public void addCoordinate(Coordinate newCoordinate) {
        coordinateList.add(newCoordinate);
    }

    public void showAll() {
        int i = 0;
        System.out.println(i + "Again");
        for (Coordinate coordinate : coordinateList) {
            System.out.println(coordinate.getNewX() + " " + "X");
            System.out.println(coordinate.getNewY() + " " + "Y");
            System.out.println(coordinate.getNewZ() + " " + "Z");
            System.out.println("________________________");
            System.out.println(i);
            i++;
        }

    }

    public void removeCoordinate(Coordinate coordinate) {
        coordinateList.remove(coordinate);
    }


}
