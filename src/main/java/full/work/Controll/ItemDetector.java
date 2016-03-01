package full.work.Controll;

/*
 * Copyright (c) 2015.  Vladimir Kartyaev
 *
 */

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class ItemDetector {
    private CascadeClassifier item_cascade;

    public void setX(int x) {
        X = x;
    }

    public void setY(int y) {
        Y = y;
    }

    public void setZ(int z) {
        Z = z;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public int getZ() {
        return Z;
    }

    public int X;
    public int Y;
    public int Z;

    int midX;
    int midY;
    int midZ;

    // Create a constructor method
    public ItemDetector() {
        item_cascade = new CascadeClassifier("src\\main\\resources\\cascade.xml");
        if (item_cascade.empty()) {
            System.out.println("--(!)Error loading A\n");
        } else {
            System.out.println("Item classifier loaded up");
        }
    }//load classf

    public Mat detectXY(Mat inputframe) {
        Mat mRgba = new Mat();
        Mat mGrey = new Mat();
        MatOfRect items = new MatOfRect();
        inputframe.copyTo(mRgba);
        inputframe.copyTo(mGrey);
        Imgproc.cvtColor(mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(mGrey, mGrey);
        item_cascade.detectMultiScale(mGrey, items);
        for (Rect rect : items.toArray()) {

            Core.rectangle(mRgba,  //where to draw the box
                    new Point(rect.x, rect.y),   //bottom left
                    new Point(rect.x + rect.width, rect.y + rect.height), //top right
                    new Scalar(255, 255, 0)); //RGB colour litte green
            midX = rect.x + rect.width;
            midY = rect.y + rect.height;
            setX(midX);
            setY(midY);
        }
        return mRgba;
    }//get frame, recognize item and draw rectangle

    public Mat detectZ(Mat inputframe) {
        Mat mRgba = new Mat();
        Mat mGrey = new Mat();
        MatOfRect items = new MatOfRect();
        inputframe.copyTo(mRgba);
        inputframe.copyTo(mGrey);
        Imgproc.cvtColor(mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(mGrey, mGrey);
        item_cascade.detectMultiScale(mGrey, items);

        for (Rect rect : items.toArray()) {

            Core.rectangle(mRgba,  //where to draw the box
                    new Point(rect.x, rect.y),   //bottom left
                    new Point(rect.x + rect.width, rect.y + rect.height), //top right
                    new Scalar(0, 255, 255)); //RGB colour lite green
            midZ = rect.y + rect.height;
            setZ(midZ);
        }
        return mRgba;
    }//same for axis Z
}
