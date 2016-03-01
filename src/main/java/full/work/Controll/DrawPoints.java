package full.work.Controll;

/*
 * Copyright (c) 2016.  Vladimir Kartyaev
 *
 */

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.awt.TextRenderer;
import full.work.Model.Coordinate;
import full.work.Model.CoordinateList;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class DrawPoints implements GLEventListener {
    public CoordinateList coordinateListforDraw;
    private float x = 5;
    private float y = 5;
    private float z = 5;

    public DrawPoints(CoordinateList coordinateListforDraw) {
        this.coordinateListforDraw = coordinateListforDraw;
    }

    public static void StartDraw(CoordinateList coordinateListforDraw) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Frame frame = new Frame("Traectoriya dvijeniya");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new DrawPoints(coordinateListforDraw));
        frame.add(canvas);
        frame.setSize(640, 480);
        Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(() -> {
                    animator.stop();
                    System.exit(0);
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();
        //System.err.println("INIT GL IS: " + gl.getClass().getName());

        gl.setSwapInterval(1);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        gl.glShadeModel(GL2.GL_SMOOTH);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        if (height <= 0) {
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(-1.5f, -1.8f, -10.0f);
        this.setlines(drawable);
        this.setCoorinatmetka(drawable);
        gl.glFlush();
    }

    public void setlines(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        float zOld = 0;
        float xOld = 0;
        float yOld = 0;

        for (Coordinate coordinate : coordinateListforDraw.coordinateList) {
            x = coordinate.getNewX() / 100;
            y = coordinate.getNewY() / 100;
            z = coordinate.getNewZ() / 100;
            gl.glBegin(GL2.GL_LINES);
            gl.glLineWidth(8);
            gl.glColor3f(1.0f, 1.0f, 1.0f);
            gl.glVertex3f(x, y, z);
            gl.glVertex3f(xOld, yOld, zOld);
            gl.glEnd();
            gl.glFlush();

            gl.glPointSize(5);
            gl.glBegin(GL2.GL_POINTS);
            gl.glColor3f(0.0f, 0.0f, 1.0f);
            gl.glVertex3f(x, y, z);
            gl.glEnd();

            gl.glFlush();
            xOld = x;
            yOld = y;
            zOld = z;
        }

    }//draw axis

    public void setCoorinatmetka(GLAutoDrawable drawable) {
        TextRenderer rendererZ = new TextRenderer(new Font("SansSerif", Font.BOLD, 20));
        GL2 gl = drawable.getGL().getGL2();
        rendererZ.beginRendering(drawable.getSurfaceWidth(), drawable.getSurfaceHeight());
        rendererZ.setColor(1.0f, 0.2f, 0.2f, 0.8f);
        rendererZ.draw("Z", 140, 10);
        rendererZ.endRendering();

        TextRenderer rendererY = new TextRenderer(new Font("SansSerif", Font.BOLD, 20));
        rendererY.beginRendering(drawable.getSurfaceWidth(), drawable.getSurfaceHeight());
        rendererY.setColor(1.0f, 0.2f, 0.2f, 0.8f);
        rendererY.draw("Y", 500, 100);
        rendererY.endRendering();

        TextRenderer rendererYYZ = new TextRenderer(new Font("SansSerif", Font.BOLD, 20));
        rendererYYZ.beginRendering(drawable.getSurfaceWidth(), drawable.getSurfaceHeight());
        rendererYYZ.setColor(1.0f, 0.2f, 0.2f, 0.8f);
        rendererYYZ.draw("(0,0,0)", 170, 140);
        rendererYYZ.endRendering();


        TextRenderer rendererX = new TextRenderer(new Font("SansSerif", Font.BOLD, 20));
        rendererX.beginRendering(drawable.getSurfaceWidth(), drawable.getSurfaceHeight());
        rendererX.setColor(1.0f, 0.2f, 0.2f, 0.8f);
        rendererX.draw("X", 200, 370);
        rendererX.endRendering();


        gl.glBegin(GL2.GL_LINES);
        gl.glColor3f(0.9f, 0.7f, 1.0f);
        gl.glVertex3f(0, 0, 0);
        gl.glVertex3f(5, 0, 0);
        gl.glEnd();
        gl.glFlush();

        gl.glBegin(GL2.GL_LINES);
        gl.glColor3f(0.0f, 0.5f, 1.0f);
        gl.glVertex3f(0, 0, 0);
        gl.glVertex3f(0, 5, 0);
        gl.glEnd();
        gl.glFlush();

        gl.glBegin(GL2.GL_LINES);
        gl.glColor3f(0.4f, 0.0f, 1.0f);
        gl.glVertex3f(0, 0, 0);
        gl.glVertex3f(0, 0, 5);
        gl.glEnd();
        gl.glFlush();
    }//draw X Y Z

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

}