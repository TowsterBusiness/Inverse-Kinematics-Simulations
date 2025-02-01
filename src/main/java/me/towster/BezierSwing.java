package me.towster;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class BezierSwing extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Set anti-aliasing for smooth lines
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Define the four points
        int x1 = 50, y1 = 300; // Start point
        int x2 = 150, y2 = 50;  // Control point 1
        int x3 = 350, y3 = 50;  // Control point 2
        int x4 = 450, y4 = 300; // End point

        // Create a Path2D object
        Path2D path = new Path2D.Double();
        path.moveTo(x1, y1);               // Move to the starting point
        path.curveTo(x2, y2, x3, y3, x4, y4); // Add a cubic Bézier curve

        // Draw the curve
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.BLUE);
        g2d.draw(path);

        // Draw control points and lines for visualization
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(x1, y1, x2, y2); // Line from start point to control point 1
        g2d.drawLine(x3, y3, x4, y4); // Line from control point 2 to end point

        // Draw points
        g2d.fillOval(x1 - 3, y1 - 3, 6, 6); // Start point
        g2d.fillOval(x2 - 3, y2 - 3, 6, 6); // Control point 1
        g2d.fillOval(x3 - 3, y3 - 3, 6, 6); // Control point 2
        g2d.fillOval(x4 - 3, y4 - 3, 6, 6); // End point
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bézier Curve Example");
        BezierSwing panel = new BezierSwing();
        frame.add(panel);
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
