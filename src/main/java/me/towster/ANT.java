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

import me.towster.utils.Bezier;
import me.towster.utils.MouseListener;
import me.towster.utils.Rectangle;
import me.towster.utils.Scene;
import org.joml.Vector2d;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class ANT extends Scene {
    Bezier bezier = new Bezier();
    List<Vector2d> controlPoints = new ArrayList<>();
    public ANT() {

        controlPoints.add(new Vector2d(-0.3, 0.1));
        controlPoints.add(new Vector2d(-0.3, 0.4));
        controlPoints.add(new Vector2d(0.2, 0.5));
        controlPoints.add(new Vector2d(0.2, -0.1));
    }

    @Override
    public void update(float dt) {
        bezier.draw(controlPoints);

    }
}
