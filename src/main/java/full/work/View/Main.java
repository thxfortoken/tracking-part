package full.work.View;

/*
 * Copyright (c) 2015.  Vladimir Kartyaev
 *
 */

import full.work.Controll.*;
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

        CamStream.runAsynchronouslyZCam(itemDetectorFirstCam, newcoordinatlist);
        CamStream.runAsynchronouslyXYCam(itemDetectorFirstCam);
        CamStream.runAsynchronouslyCheckMistakes(newcoordinatlist);


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


}