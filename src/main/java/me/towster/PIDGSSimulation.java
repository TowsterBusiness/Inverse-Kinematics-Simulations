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

import me.towster.utils.*;

import java.util.LinkedList;
import java.util.Queue;

import static me.towster.utils.InverseKinematics.inverseKinematics;
import static org.lwjgl.opengl.GL11.*;

public class PIDGSSimulation extends Scene {
    Rectangle arm1;
    Rectangle arm2;
    float armBuffer = 0.05f;
    float armLength1 = 0.5f;
    float armLength2 = 0.8f;
    float lastArmAngle1 = 0.0f;
    float lastArmAngle2 = 0.0f;
    float spinCount1 = 0;
    float spinCount2 = 0;

    PIDController pidController1 = new PIDController(1f, 0.1f, 0.005f);
    PIDController pidController2 = new PIDController(1f, 0.1f, 0.005f);

    float finAngle1, finAngle2 = 0;

    @Override
    public void update(float dt) {
        // TODO: Add code to make sure that the pointer is not too close to the orgin or too far from the outside

        float finX = (MouseListener.get().getX() / 400 - 1);
        float finY = -(MouseListener.get().getY() / 300 - 1);

        boolean isTriggered = KeyListener.isKeyPressed(32);
        double[] angles = inverseKinematics(armLength1, armLength2, finX, finY, isTriggered);

        float arm1Angle = (float) angles[0];
        float arm2Angle = (float) angles[1];

        if (lastArmAngle1 - arm1Angle > 300) {
            spinCount1++;
        } else if (lastArmAngle1 - arm1Angle < -300) {
            spinCount1--;
        }
        if (lastArmAngle2 - arm2Angle > 300) {
            spinCount2++;
        } else if (lastArmAngle2 - arm2Angle < -300) {
            spinCount2--;
        }

        float trueAngle1 = arm1Angle + spinCount1 * 360;
        float trueAngle2 = arm2Angle + spinCount2 * 360;

        lastArmAngle1 = arm1Angle;
        lastArmAngle2 = arm2Angle;

        float vel1 = pidController1.update(trueAngle1 - finAngle1, dt);
        float vel2 = pidController2.update(trueAngle2 - finAngle2, dt);

        finAngle1 += vel1 * 0.1;
        finAngle2 += vel2 * 0.1;

        arm1 = new Rectangle(0, 0, armBuffer, armBuffer, armBuffer, armLength1 + armBuffer, 1, 1, 0, finAngle1);
        arm2 = new Rectangle(
                (float) Math.cos(Math.toRadians(finAngle1)) * armLength1,
                (float) Math.sin(Math.toRadians(finAngle1)) * armLength1,
                armBuffer, armBuffer, armBuffer, armLength2 + armBuffer, 0, 1, 1, finAngle2
        );

        arm1.draw();
        arm2.draw();
    }
}