package me.towster.utils;
import org.joml.Vector2d;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

// ANT

public class Bezier {
    private double dt = 0.01;
    private float r = 1, g = 0, b = 1;
    public Bezier() {
    }
    public Bezier(double dt) {
        this.dt = dt;
    }

    public Bezier(double dt, float r, float g, float b) {
        this.dt = dt;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void draw(List<Vector2d> controlPoints) {
        glColor3f(r, g, b);

        glPointSize(10);
        glBegin(GL_POINTS);
        for (float i = 0; i <= 1; i += dt) {
            Vector2d point = getBezierPoint(controlPoints, i);
            glVertex2f((float) point.x, (float) point.y);
        }
        glEnd();

        glColor3f(0.3f, 0.5f, 0.5f);
        glLineWidth(10f);
        glBegin(GL_LINES);
            glVertex2f((float) controlPoints.get(0).x, (float) controlPoints.get(0).y);
            glVertex2f((float) controlPoints.get(1).x, (float) controlPoints.get(1).y);
        glEnd();

        glColor3f(0.5f, 0.5f, 0.3f);
        glLineWidth(10f);
        glBegin(GL_LINES);
        glVertex2f((float) controlPoints.get(2).x, (float) controlPoints.get(2).y);
        glVertex2f((float) controlPoints.get(3).x, (float) controlPoints.get(3).y);
        glEnd();

    }

    public void draw(List<Vector2d> controlPoints, double t) {

    }

    /**
     * Function to get a point on a Bézier curve at time t using De Casteljau's algorithm.
     * @param controlPoints List of control points defining the Bézier curve.
     * @param t Time parameter (0 <= t <= 1).
     * @return The point on the Bézier curve at time t.
     */
    public static Vector2d getBezierPoint(List<Vector2d> controlPoints, double t) {
        if (controlPoints.size() != 4) {
            throw new IllegalArgumentException("Exactly 4 control points are required.");
        }
        if (t < 0 || t > 1) {
            throw new IllegalArgumentException("Parameter t must be in the range [0, 1].");
        }

        // Extract control points
        Vector2d p0 = controlPoints.get(0);
        Vector2d p1 = controlPoints.get(1);
        Vector2d p2 = controlPoints.get(2);
        Vector2d p3 = controlPoints.get(3);

        double oneMinusT = 1 - t;

        // Calculate the blending functions and interpolate
        double x = oneMinusT * oneMinusT * oneMinusT * p0.x
                + 3 * oneMinusT * oneMinusT * t * p1.x
                + 3 * oneMinusT * t * t * p2.x
                + t * t * t * p3.x;

        double y = oneMinusT * oneMinusT * oneMinusT * p0.y
                + 3 * oneMinusT * oneMinusT * t * p1.y
                + 3 * oneMinusT * t * t * p2.y
                + t * t * t * p3.y;

        return new Vector2d(x, y);
    }
}