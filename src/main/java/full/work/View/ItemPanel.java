package full.work.View;



/*
 * Copyright (c) 2015.  Vladimir Kartyaev
 *
 */

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;


public class ItemPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private BufferedImage image;

    // Create a constructor method
    public ItemPanel() {
        super();
    }


    public boolean matToBufferedImage(Mat matrix) {
        MatOfByte mb = new MatOfByte();
        Highgui.imencode(".jpg", matrix, mb);
        try {
            this.image = ImageIO.read(new ByteArrayInputStream(mb.toArray()));
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Error
        }
        return true; // Successful
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.image == null) return;

        g.drawImage(this.image, 10, 10, this.image.getWidth(), this.image.getHeight(), null);
    }

}
