package me.towster.utils;

import static org.lwjgl.opengl.GL11.*;

public class Rectangle {
    private float x, y, up, down, left, right, angle, r, g, b;

    public Rectangle(float x, float y, float up, float down, float left, float right, float r, float g, float b, float angle) {
        this.x = x;
        this.y = y;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        this.angle = angle;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void draw() {
        glColor4f(r, g, b, 0.5f);
        glBegin(GL_QUADS);
        float[] temp = rotate(-left, up, angle);
        glVertex2f(temp[0] + x, temp[1] + y);
        temp = rotate(right, up, angle);
        glVertex2f(temp[0] + x, temp[1] + y);
        temp = rotate(right, -down, angle);
        glVertex2f(temp[0] + x, temp[1] + y);
        temp = rotate(-left, -down, angle);
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
