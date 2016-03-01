package full.work.Model;

/*
 * Copyright (c) 2015.  Vladimir Kartyaev
 *
 */

import java.io.Serializable;

public class Coordinate implements Serializable {


    private float newX;
    private float newY;
    private float newZ;
    private int frapsCount;

    public int getFrapsCount() {
        return frapsCount;
    }

    public void setFrapsCount(int frapsCount) {
        this.frapsCount = frapsCount;
    }

    public float getNewX() {
        return newX;
    }

    public void setNewX(float newX) {
        this.newX = newX;
    }

    public float getNewY() {
        return newY;
    }

    public void setNewY(float newY) {
        this.newY = newY;
    }

    public float getNewZ() {
        return newZ;
    }

    public void setNewZ(float newZ) {
        this.newZ = newZ;
    }


}
