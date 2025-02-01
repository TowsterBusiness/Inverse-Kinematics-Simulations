package me.towster;

import me.towster.utils.MouseListener;
import me.towster.utils.Rectangle;
import me.towster.utils.Scene;
import org.lwjgl.opengl.GL11;

import java.util.LinkedList;
import java.util.Queue;

import static org.lwjgl.opengl.GL11.*;

public class SimulationScene extends Scene {

    Rectangle arm1;
    Rectangle arm2;
    float armBuffer = 0.05f;

    float lastArm1Angle = 90;
    public SimulationScene() {

    }

    @Override
    public void update(float dt) {
        float max = -1;
        float[] answers = {};

        float finX =  (MouseListener.get().getX() / 500 - 1);
        float finY = -(MouseListener.get().getY() / 450 - 1);

        float armLength1 = 0.3f;
        float armLength2 = 0.1f;
        float y1, y2, difference;

        // Checks a lot of the angles from -3.14 to 3.14
        for (float x = -3.14f; x < 3.14f; x += 0.001f) {
            y1 = (float) Math.acos((finX - armLength1 * Math.cos(x)) / armLength2);
            y2 = (float) Math.asin((finY - armLength1 * Math.sin(x)) / armLength2);

            difference = Math.abs(y1 - y2);
            if (!Float.isNaN(difference) && (difference < max || max == -1)) {
                max = difference;
                answers = new float[] {y1,y2,x};
            }

            y1 = -y1;

            difference = Math.abs(y1 - y2);
            if (!Float.isNaN(difference) && (difference < max || max == -1)) {
                max = difference;
                answers = new float[] {y1,y2,x};
            }
            y1 = -y1;
            y2 = 3.14f - y2;

            difference = Math.abs(y1 - y2);
            if (!Float.isNaN(difference) && (difference < max || max == -1)) {
                max = difference;
                answers = new float[] {y1,y2,x};
            }
            y1 = -y1;

            difference = Math.abs(y1 - y2);
            if (!Float.isNaN(difference) && (difference < max || max == -1)) {
                max = difference;
                answers = new float[] {y1,y2,x};
            }
        }

        float arm1Angle = (float) Math.toDegrees(answers[0]) % 360;
        float arm2Angle = (float) Math.toDegrees(answers[2]) % 360;

        float finDegrees = (float) Math.toDegrees(Math.atan2((double) finY, (double) finX));
        float wiggleRoom = 4;
        System.out.println(((2 * finDegrees - arm1Angle) % 360) + " " + (lastArm1Angle + wiggleRoom));
        if (2 * finDegrees - arm1Angle < lastArm1Angle + wiggleRoom && 2 * finDegrees - arm1Angle > lastArm1Angle - wiggleRoom) {
            arm1Angle = 2 * finDegrees - arm1Angle;
            arm2Angle = 2 * finDegrees - arm2Angle;
        }


        arm1 = new Rectangle(0, 0, armBuffer, armBuffer, armBuffer, armLength1 + armBuffer, 1, 0, 0, arm1Angle);
        arm2 = new Rectangle(
                (float) Math.cos(Math.toRadians(arm1Angle)) * armLength2,
                (float) Math.sin(Math.toRadians(arm1Angle)) * armLength2,
                armBuffer, armBuffer, armBuffer, armLength2 + armBuffer, 0, 0, 1, arm2Angle
        );

        lastArm1Angle = arm1Angle;

        arm1.draw();
        arm2.draw();
    }

    private boolean isNearAngle(float input, float closeTo, float wiggleRoom) {
        return true;
    }

}
