package full.work.Controll;

/*
 * Copyright (c) 2015.  Vladimir Kartyaev
 *
 */

import full.work.Model.Coordinate;
import full.work.Model.CoordinateList;


public class CheckCoordinates {

    static Coordinate point = null; // static midl point

    public static void checkCoordinate(CoordinateList listcoordinate) throws InterruptedException {
        Coordinate buff = null;
        while (true) {
            for (Coordinate coordinate : listcoordinate.coordinateList) {
                if (buff != null) {
                    if ((coordinate.getFrapsCount() - buff.getFrapsCount() > createMidlDistance(listcoordinate)) && inSpere(coordinate, listcoordinate)) {
                        listcoordinate.removeCoordinate(coordinate);
                    }
                }
                buff = coordinate;
                removeZeroCoordinate(listcoordinate, coordinate);

            }
        }
    }//method for check all coordinates

    public static void removeZeroCoordinate(CoordinateList coordinateList, Coordinate coordinate) {

        if ((coordinate.getNewX() == 0.0) || coordinate.getNewY() == 0.0 || coordinate.getNewZ() == 0.0) {
            coordinateList.removeCoordinate(coordinate);
        }

    }//remove coordinates with zero points

    public static float createMidlDistance(CoordinateList listcoordinate) {
        float buffMediumCount = 0;
        int countElemnts = 0;
        for (Coordinate coordinate : listcoordinate.coordinateList) {
            buffMediumCount = coordinate.getFrapsCount();
            countElemnts++;
        }
        return buffMediumCount / countElemnts;
    }//  obtaining the average value of dots

    public static float createSpere(CoordinateList listcoordinate) {

        Coordinate temp = null;
        float r;
        for (Coordinate coordinate : listcoordinate.coordinateList) {
            if (point == null) {
                point.setNewX(coordinate.getNewX());
                point.setNewY(coordinate.getNewX());
                point.setNewZ(coordinate.getNewX());
                temp.setNewX(coordinate.getNewX());
                temp.setNewY(coordinate.getNewY());
                temp.setNewZ(coordinate.getNewZ());
            }
            if ((coordinate.getNewX() - point.getNewX() > temp.getNewX()) && (coordinate.getNewY() - point.getNewY() > temp.getNewY()) && (coordinate.getNewZ() - point.getNewZ() > temp.getNewZ())) {
                temp.setNewX(coordinate.getNewX());
                temp.setNewY(coordinate.getNewX());
                temp.setNewZ(coordinate.getNewX());
            }
        }
        r = (temp.getNewX() - point.getNewX()) * (temp.getNewX() - point.getNewX()) + (temp.getNewY() - point.getNewY()) * (temp.getNewY() - point.getNewY()) + (temp.getNewZ() - point.getNewZ()) * (temp.getNewZ() - point.getNewZ());
        return r * r;
    }// creating a sphere centered at the first point

    public static Boolean inSpere(Coordinate coordinate, CoordinateList listcoordinate) {
        if ((coordinate.getNewX() - point.getNewX()) * (coordinate.getNewX() - point.getNewX()) + (coordinate.getNewY() - point.getNewY()) * (coordinate.getNewY() - point.getNewY()) + (coordinate.getNewZ() - point.getNewZ()) * (coordinate.getNewZ() - point.getNewZ()) < createSpere(listcoordinate)) {
            return true;
        } else {
            return false;
        }

    } // check it!  r*r<
}
