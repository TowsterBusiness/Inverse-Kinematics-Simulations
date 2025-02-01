package me.towster;

import me.towster.utils.MouseListener;
import me.towster.utils.PIDController;
import me.towster.utils.Scene;
import me.towster.utils.Time;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static org.lwjgl.opengl.GL11.*;

public class PIDSimulation extends Scene {
    PIDController pidController;

    Queue<float[]> points;

    public PIDSimulation () {
//        pidController = new PIDController(0.6f, 1.2f, 0.075f);
//        pidController = new PIDController(0.2f, 0.4f, 0.066f);
        pidController = new PIDController(0.001f, 0f, 0f);
        points = new LinkedList<float[]>();
    }

    float finX, finY = 0;
    float trueX, trueY = 0;

    @Override
    public void update(float dt) {
        finY = - (MouseListener.get().getY() / 450 - 1);

        glColor3f(1, 0, 0);
        glLineWidth(5f);
        glBegin(GL_LINES);
        glVertex2f(-1, finY);
        glVertex2f(1, finY);
        glEnd();

        float vel = pidController.update( (finY - trueY), dt);
        trueY += vel * 8f;
        System.out.println("velocity: " + vel);
        System.out.println("position: " + trueY);
        System.out.println("dt: " + dt);

        points.add(new float[] {trueY, Time.getTime()});
        if (points.size() > 300) {
            points.remove();
        }

        glColor3f(0, 0, 0);
        glLineWidth(3f);
        glBegin(GL_LINES);
        glVertex2f(-1, 0);

        for (float[] point : points) {
            glVertex2f(-(Time.getTime() - point[1]) + 0.5f, point[0]);
            glEnd();
            glBegin(GL_LINES);
            glVertex2f(-(Time.getTime() - point[1]) + 0.5f, point[0]);
        }
        glVertex2f(0.5f, trueY);
        glEnd();
    }
}
