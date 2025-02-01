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

import static me.towster.utils.InverseKinematics.inverseKinematics;
import static org.lwjgl.opengl.GL11.*;


public class RoundedGSS extends Scene {
    Rectangle arm1;
    Rectangle arm2;
    Rectangle arm3;
    Rectangle arm4;
    float armBuffer = 0.05f;
    float armLength1 = 0.216f;
    float armLength2 = 0.312f;

    float lastArmAngle1 = 0.0f;
    float lastArmAngle2 = 0.0f;
    float lastArmAngle3 = 0.0f;
    float lastArmAngle4 = 0.0f;
    float spinCount1 = 0;
    float spinCount2 = 0;
    float spinCount3 = 0;
    float spinCount4 = 0;

    @Override
    public void update(float dt) {
        // TODO: Add code to make sure that the pointer is not too close to the orgin or too far from the outside

        float finX = (MouseListener.get().getX() / 400 - 1);
        float finY = -(MouseListener.get().getY() / 300 - 1);

        double[] angles2 = inverseKinematics(armLength1, armLength2, finX, finY, false);
        double[] angles1 = inverseKinematics(armLength1, armLength2, finX, finY, true);

        float arm1Angle = (float) angles1[0];
        float arm2Angle = (float) angles1[1];
        float arm3Angle = (float) angles2[0];
        float arm4Angle = (float) angles2[1];

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
        if (lastArmAngle3 - arm3Angle > 300) {
            spinCount3++;
        } else if (lastArmAngle3 - arm3Angle < -300) {
            spinCount3--;
        }
        if (lastArmAngle4 - arm4Angle > 300) {
            spinCount4++;
        } else if (lastArmAngle4 - arm4Angle < -300) {
            spinCount4--;
        }

        float finAngle1 = arm1Angle + spinCount1 * 360;
        float finAngle2 = arm2Angle + spinCount2 * 360;
        float finAngle3 = arm3Angle + spinCount3 * 360;
        float finAngle4 = arm4Angle + spinCount4 * 360;

        lastArmAngle1 = arm1Angle;
        lastArmAngle2 = arm2Angle;
        lastArmAngle3 = arm3Angle;
        lastArmAngle4 = arm4Angle;

        arm1 = new Rectangle(0, 0, armBuffer, armBuffer, armBuffer, armLength1 + armBuffer, 1, 0, 0, finAngle1);
        arm2 = new Rectangle(
                (float) Math.cos(Math.toRadians(finAngle1)) * armLength1,
                (float) Math.sin(Math.toRadians(finAngle1)) * armLength1,
                armBuffer, armBuffer, armBuffer, armLength2 + armBuffer, 0, 0, 1, finAngle2
        );
        arm3 = new Rectangle(0, 0, armBuffer, armBuffer, armBuffer, armLength1 + armBuffer, 1, 1, 0, finAngle3);
        arm4 = new Rectangle(
                (float) Math.cos(Math.toRadians(finAngle3)) * armLength1,
                (float) Math.sin(Math.toRadians(finAngle3)) * armLength1,
                armBuffer, armBuffer, armBuffer, armLength2 + armBuffer, 0, 1, 1, finAngle4
        );

        arm1.draw();
        arm2.draw();
        arm3.draw();
        arm4.draw();
    }
}

