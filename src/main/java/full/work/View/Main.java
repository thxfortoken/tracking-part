package full.work.View;

/*
 * Copyright (c) 2015.  Vladimir Kartyaev
 *
 */

import full.work.Controll.CheckCoordinates;
import full.work.Controll.DrawPoints;
import full.work.Controll.ItemDetector;
import full.work.Controll.Serializator;
import full.work.Model.Coordinate;
import full.work.Model.CoordinateList;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;


public class Main {

    public static void main(String arg[]) throws InterruptedException, CloneNotSupportedException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        ItemDetector itemDetectorFirstCam = new ItemDetector();
        CoordinateList newcoordinatlist = new CoordinateList();
        DrawPoints.StartDraw(newcoordinatlist);

        runAsynchronouslyZCam(itemDetectorFirstCam, newcoordinatlist);
        runAsynchronouslyXYCam(itemDetectorFirstCam);
        runAsynchronouslyCheckMistakes(newcoordinatlist);


        Serializator ser = new Serializator();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Tracking");
            System.out.println("1. Save to file");
            System.out.println("2. Exit");
            System.out.println("Choose ur number:");
            int actionNumber = sc.nextInt();
            switch (actionNumber) {
                case 1: {
                    System.out.println("Enter filename:");
                    String newFilePath = sc.nextLine();//java fail
                    newFilePath = sc.nextLine();
                    ser.serialization(newcoordinatlist, newFilePath);
                    break;
                }
                case 2: {
                    System.exit(0);
                    break;
                }
            }
        }


    } //end main

    public static void runAsynchronouslyZCam(ItemDetector valuefirstcam, CoordinateList newcoordinatlist) {
        new Thread(() -> {

            ///Second frame
            JFrame frame2 = new JFrame("WebCam2 Capture - Item Z detection");
            frame2.setLocation(0, 50);
            frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            ////Second itemdetection
            ItemDetector itemDetectorZCam = new ItemDetector();
            ItemPanel itemPanelZCam = new ItemPanel();
            frame2.setSize(400, 400); //give the frame some arbitrary size
            frame2.setBackground(Color.BLUE);
            frame2.add(itemPanelZCam, BorderLayout.CENTER);
            frame2.setVisible(true);

            //Second WEbcam2
            int frapsCountWebCam = 0;
            Mat webcamZ_image = new Mat();
            VideoCapture webCamZ = new VideoCapture(0);

            if (webCamZ.isOpened()) {
                try {
                    Thread.sleep(500); /// This one-time delay allows the Webcam to initialize itself
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (true) {
                    Coordinate newcoordinate = new Coordinate();
                    webCamZ.read(webcamZ_image);
                    if (!webcamZ_image.empty()) {
                        frame2.setSize(webcamZ_image.width() + 40, webcamZ_image.height() + 60);
                        //Apply the classifier to the captured image and create newcoordinate
                        webcamZ_image = itemDetectorZCam.detectZ(webcamZ_image);
                        //add newcoordinate
                        newcoordinate.setNewX(valuefirstcam.getX());
                        newcoordinate.setNewY(valuefirstcam.getY());
                        newcoordinate.setNewZ(itemDetectorZCam.getZ());
                        newcoordinate.setFrapsCount(frapsCountWebCam);
                        newcoordinatlist.addCoordinate(newcoordinate);

                        //Display the image
                        //Display Scale WebCam Second
                        itemPanelZCam.matToBufferedImage(webcamZ_image);
                        itemPanelZCam.repaint();
                    } else {
                        System.out.println(" --(!) No captured frame from webcam !");
                        break;
                    }
                }
            }

            webCamZ.release();// release the webcam2
        }).start();
    }// start first cam and detect methods

    public static void runAsynchronouslyXYCam(ItemDetector valuefirstcam) {
        new Thread(() -> {

            //make the JFrame
            JFrame frame = new JFrame("WebCam Capture - Items X Y detection");
            frame.setLocation(680, 50);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            ItemPanel itemPanelXYCam = new ItemPanel();
            frame.setSize(400, 400); //give the frame some arbitrary size
            frame.setBackground(Color.BLUE);
            frame.add(itemPanelXYCam, BorderLayout.CENTER);
            frame.setVisible(true);


            //Open and Read from the video stream
            Mat webcamXY_image = new Mat();
            VideoCapture webCam = new VideoCapture(1);


            if (webCam.isOpened()) {
                try {
                    Thread.sleep(500); /// This one-time delay allows the Webcam to initialize itself
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (true) {
                    webCam.read(webcamXY_image);
                    if (!webcamXY_image.empty()) {
                        frame.setSize(webcamXY_image.width() + 40, webcamXY_image.height() + 60);
                        webcamXY_image = valuefirstcam.detectXY(webcamXY_image);
                        itemPanelXYCam.matToBufferedImage(webcamXY_image);
                        itemPanelXYCam.repaint();
                    } else {
                        System.out.println(" --(!) No captured frame from webcam !");
                        break;
                    }
                }
            }
            webCam.release(); //release the webcam
        }).start();
    }//start second cam and detect methods and set dots

    public static void runAsynchronouslyCheckMistakes(final CoordinateList newcoordinatlist) {
        new Thread(() -> {
            try {
                CheckCoordinates.checkCoordinate(newcoordinatlist);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    } // start check mistakes


}