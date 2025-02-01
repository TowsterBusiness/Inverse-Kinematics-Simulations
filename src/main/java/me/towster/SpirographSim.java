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
import org.joml.Vector2d;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import static me.towster.utils.InverseKinematics.inverseKinematics;
import static org.lwjgl.opengl.GL11.*;

public class SpirographSim extends Scene {
    Rectangle arm1;
    Rectangle arm2;
    Rectangle armE1;
    Rectangle armE2;
    float armBuffer = 0.05f;
    float armLength1 = .5f;
    float armLength2 = .75f;
    Vector2d armPos1 = new Vector2d(0.25f, -1f);
    Vector2d armPos2 = new Vector2d(-0.25f, -1f);
    PIDController pid1 = new PIDController(0.3f, 0f, 0);
    PIDController pid2 = new PIDController(0.3f, 0f, 0);

    float finX = 0;
    float finY = 0;

    float goalX = 0;
    float goalY = 0;

    float currentArmAngle1 = 0;
    float currentArmAngle2 = 0;

    long timeThreshold = 2;
    int counter = 0;

    Queue<Vector2d> points = new LinkedList<Vector2d>();

    EncoderlessPath path;

    public SpirographSim() {
            path = new EncoderlessPath()
                    .addPoint(new Vector2d(-0.25, -0.25))
                    .addPoint(new Vector2d(0.25, -0.25))
                    .addPoint(new Vector2d(0.25, 0))
                    .addPoint(new Vector2d(-0.25, 0))
                    .build();
            path.loop = true;
    }

    @Override
    public void update(float dt) {

        // TODO: Add code to make sure that the pointer is not too close to the orgin or too far from the outside

        if (Time.getTime() > timeThreshold) {
            if (counter == 0) {
                goalX = -0.25f;
                goalY = -0.25f;
            } else if (counter == 1) {
                goalX = 0.25f;
                goalY = -0.25f;
            } else if (counter == 2) {
                goalX = 0.25f;
                goalY = 0;
            } else if (counter == 3) {
                goalX = -0.25f;
                goalY = 0;
            }
            counter ++;
            counter %= 4;

            timeThreshold += 2;
        }
        finX = (float) path.posOnTime(Time.getTime()).x;
        finY = (float) path.posOnTime(Time.getTime()).y;

        double[] arm1Angle = inverseKinematics(armLength1, armLength2, finX - armPos1.x, finY - armPos1.y, true);
        double[] arm2Angle = inverseKinematics(armLength1, armLength2, finX - armPos2.x, finY - armPos2.y, false);

        float vel1 = pid1.update( (float) arm1Angle[0] - currentArmAngle1, dt);
        float vel2 = pid2.update( (float) arm2Angle[0] - currentArmAngle2, dt);

        currentArmAngle1 += vel1;
        currentArmAngle2 += vel2;

        Vector2d a2sP1 = new Vector2d(Math.cos(Math.toRadians(currentArmAngle1)), Math.sin(Math.toRadians(currentArmAngle1))).mul(armLength1).add(armPos1);
        Vector2d a2sP2 = new Vector2d(Math.cos(Math.toRadians(currentArmAngle2)), Math.sin(Math.toRadians(currentArmAngle2))).mul(armLength1).add(armPos2);

        float thetaA = (float) Math.acos(a2sP1.distance(a2sP2)/(2*armLength2));
        float thetaB1 = -thetaA + (float) Math.atan2(a2sP2.y - a2sP1.y, a2sP2.x - a2sP1.x);
        float thetaB2 = thetaA + (float) Math.atan2(a2sP1.y - a2sP2.y, a2sP1.x - a2sP2.x);

//        System.out.println(Math.toDegrees(thetaB1) + " " + Math.toDegrees(thetaB2));

        arm1 = new Rectangle((float) armPos1.x, (float) armPos1.y, armBuffer, armBuffer, armBuffer, armLength1 + armBuffer, 1, 0, 0, (float) currentArmAngle1);
        armE1 = new Rectangle(
                (float) a2sP1.x,
                (float) a2sP1.y,
                armBuffer, armBuffer, armBuffer, armLength2 + armBuffer, 0, 1, 0, (float) Math.toDegrees(thetaB1));
        arm2 = new Rectangle((float) armPos2.x, (float) armPos2.y, armBuffer, armBuffer, armBuffer, armLength1 + armBuffer, 0, 1, 1, (float) currentArmAngle2);
        armE2 = new Rectangle(
                (float) a2sP2.x,
                (float) a2sP2.y,
                armBuffer, armBuffer, armBuffer, armLength2 + armBuffer, 1, 1, 0, (float) Math.toDegrees(thetaB2));

        arm1.draw();
        arm2.draw();
        armE1.draw();
        armE2.draw();

        Vector2d estimatedPoint = new Vector2d(Math.cos(thetaB1), Math.sin(thetaB1)).mul(armLength2).add(a2sP1);

        points.add(estimatedPoint);
        if (points.size() > 300) {
            points.remove();
        }

        glColor3f(0, 0, 0);
        glLineWidth(3f);
        glBegin(GL_POINTS);
        for (Vector2d point : points) {
            glVertex2f((float) point.x, (float) point.y);
        }
        glEnd();

        path.drawPosOnTime(Time.getTime());
        path.drawAnchorPoints();
    }
}



