package me.towster;

import org.joml.Vector2d;

import java.util.ArrayList;
import java.util.Vector;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;

public class EncoderlessPath {
    public boolean loop;
    private double loopExtraTime;
    private ArrayList<Vector2d> paths;
    private double[] times;
    private double maxTime;

    public double motorSpeed = 3;

    public EncoderlessPath() {
        paths = new ArrayList<Vector2d>();
    }

    public EncoderlessPath addPoint(Vector2d point) {
        paths.add(point);
        return this;
    }

    public EncoderlessPath build() {
        Vector2d lastVector = null;
        double timeAdder = 0;
        times = new double[paths.size()];
        for (int i = 0; i < paths.size(); i++) {
            Vector2d point = paths.get(i);

            if (lastVector == null) {
                times[0] = 0;
                lastVector = point;
            } else {
                timeAdder += lastVector.distance(point) * motorSpeed;
                times[i] = timeAdder;
                lastVector = point;
            }
        }
        loopExtraTime = paths.get(paths.size() - 1).distance(paths.get(0)) * motorSpeed;
        maxTime = times[times.length - 1];

        return this;
    }

    public Vector2d posOnTime(float t) {
        if (loop) {
            t %= (float) (maxTime + loopExtraTime);
        }
        if (t > maxTime) {
            if (loop) {
                double p = (t - times[times.length - 1]) / loopExtraTime;
                return lerp(paths.get(paths.size() - 1), paths.get(0), p);
            } else {
                return paths.get(paths.size() - 1);
            }
        }
        int betweenIndex = times.length - 1;
        for (int i = 1; i < times.length; i++ ) {
            if (t < times[i]) {
                betweenIndex = i;
                break;
            }
        }

        double p = (t - times[betweenIndex - 1]) / (times[betweenIndex] - times[betweenIndex - 1]);
        return lerp(paths.get(betweenIndex - 1), paths.get(betweenIndex), p);
    }

    public void drawAnchorPoints() {
        glColor3f(0, 0, 1);
        glLineWidth(3f);
        glBegin(GL_POINTS);
        for (Vector2d point : paths) {
            glVertex2f((float) point.x, (float) point.y);
        }
        glEnd();
    }

    public void drawPosOnTime(float t) {
        glBegin(GL_POINTS);
        Vector2d a = posOnTime(t);
        glVertex2f((float) a.x, (float) a.y);
        glEnd();
    }

    Vector2d lerp(Vector2d a, Vector2d b, double t) {
        return new Vector2d(a.x + (b.x - a.x) * t, a.y + (b.y - a.y) * t);
    }
}
