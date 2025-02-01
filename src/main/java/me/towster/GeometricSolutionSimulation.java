/*
* All Calculations are derived from https://www.desmos.com/calculator/6ocjywflgo
* I am not smart enough to figure this out.
*
*
*
*
*
*
* */

package me.towster;

import me.towster.utils.MouseListener;
import me.towster.utils.Rectangle;
import me.towster.utils.Scene;
import org.lwjgl.opengl.GL11;

import java.util.LinkedList;
import java.util.Queue;

import static org.lwjgl.opengl.GL11.*;

public class GeometricSolutionSimulation extends Scene {

    Rectangle arm1;
    Rectangle arm2;
    Rectangle arm3;
    Rectangle arm4;
    float armBuffer = 0.05f;
    float armLength1 = 0.4f;
    float armLength2 = 0.4f;

    float lastArmAngle1;
    float lastArmAngle2;

    @Override
    public void update(float dt) {
        // TODO: Add code to make sure that the pointer is not too close to the orgin or too far from the outside

        float finX = (MouseListener.get().getX() / 400 - 1);
        float finY = - (MouseListener.get().getY() / 300 - 1);

        double c = Math.sqrt(Math.pow(finX, 2) + Math.pow(finY, 2));

        double a1 = Math.PI - Math.acos((Math.pow(armLength1, 2) + Math.pow(armLength2, 2) - Math.pow(c, 2)) / (2 * armLength1 * armLength2));
        double a2 = Math.PI + Math.acos((Math.pow(armLength1, 2) + Math.pow(armLength2, 2) - Math.pow(c, 2)) / (2 * armLength1 * armLength2));

        double ad1 = Math.atan2(finY, finX) - Math.atan2(Math.sin(a1) * armLength2, armLength1 + Math.cos(a1) * armLength2);
        double ad2 = Math.atan2(finY, finX) - Math.atan2(Math.sin(a2) * armLength2, armLength1 + Math.cos(a2) * armLength2);

        double ax1 = Math.cos(ad1) * armLength1;
        double ay1 = Math.sin(ad1) * armLength1;
        double ax2 = Math.cos(ad2) * armLength1;
        double ay2 = Math.sin(ad2) * armLength1;

        double bx1 = ax1 + Math.cos(a1 + ad1) * armLength2;
        double by1 = ay1 + Math.sin(a1 + ad1) * armLength2;
        double bx2 = ax2 + Math.cos(a2 + ad2) * armLength2;
        double by2 = ay2 + Math.sin(a2 + ad2) * armLength2;

        float arm1Angle = (float) Math.toDegrees(Math.atan2(ay1, ax1));
        float arm2Angle = (float) Math.toDegrees(Math.atan2(by1 - ay1, bx1 - ax1));
        float arm3Angle = (float) Math.toDegrees(Math.atan2(ay2, ax2));
        float arm4Angle = (float) Math.toDegrees(Math.atan2(by2 - ay2, bx2 - ax2));

        arm1 = new Rectangle(0, 0, armBuffer, armBuffer, armBuffer, armLength1 + armBuffer, 1, 0, 0, arm1Angle);
        arm2 = new Rectangle(
                (float) Math.cos(Math.toRadians(arm1Angle)) * armLength2,
                (float) Math.sin(Math.toRadians(arm1Angle)) * armLength2,
                armBuffer, armBuffer, armBuffer, armLength2 + armBuffer, 0, 0, 1, arm2Angle
        );
        arm3 = new Rectangle(0, 0, armBuffer, armBuffer, armBuffer, armLength1 + armBuffer, 1, 1, 0, arm3Angle);
        arm4 = new Rectangle(
                (float) Math.cos(Math.toRadians(arm3Angle)) * armLength2,
                (float) Math.sin(Math.toRadians(arm3Angle)) * armLength2,
                armBuffer, armBuffer, armBuffer, armLength2 + armBuffer, 0, 1, 1, arm4Angle
        );


        System.out.println("1: " + arm3Angle);
        System.out.println("2: " + Math.toDegrees(Math.atan2(by2, bx2)));

        arm1.draw();
        arm2.draw();
        arm3.draw();
        arm4.draw();


    }
}
