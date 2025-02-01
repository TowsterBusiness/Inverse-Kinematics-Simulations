package me.towster.utils;

import static org.lwjgl.opengl.GL11.*;

public class RobotVisualizer {
    private float x, y, width, height, angle, r, g, b;

    public RobotVisualizer(float x, float y, float width, float height, float r, float g, float b, float angle) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void draw() {
        glColor4f(r, g, b, 0.5f);
        glBegin(GL_QUADS);
        float[] temp = rotate(-width, height, angle);
        glVertex2f(temp[0] + x, temp[1] + y);
        temp = rotate(width, height, angle);
        glVertex2f(temp[0] + x, temp[1] + y);
        temp = rotate(width, -height, angle);
        glVertex2f(temp[0] + x, temp[1] + y);
        temp = rotate(-width, -height, angle);
        glVertex2f(temp[0] + x, temp[1] + y);
        glEnd();

        glColor3f(0, 1.0f, 0);
        glPointSize(10f);
        glBegin(GL_POINTS);
        glVertex2f(x, y);
        glEnd();
    }

    public float[] rotate(float x, float y, float angle) {
        double radian = Math.atan2(y, x) + Math.toRadians(angle);
        double m = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

        float[] answer = new float[] { (float) (Math.cos(radian) * m), (float) (Math.sin(radian) * m) };

        return answer;

    }
}
