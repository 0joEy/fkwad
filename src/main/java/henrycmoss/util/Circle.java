package henrycmoss.util;

import org.joml.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Circle {

    private int centerX;
    private int centerY;
    private int radius;

    public Circle(int radius, int x, int y) {
        centerX = x;
        centerY = y;
        this.radius = radius;
    }

    public List<Vector2d> getPoints(int iterations) {
        double increment = 2 * Math.PI / iterations;

        int[] xPoints = new int[iterations];
        int[] yPoints = new int[iterations];

        List<Vector2d> points = new ArrayList<>();

        for(int i = 0; i < iterations; i++) {
            double angle = i * increment;
            xPoints[i] =  (int) (centerX + radius * Math.cos(angle));
            xPoints[i] =  (int) (centerY + radius * Math.sin(angle));
            points.add(new Vector2d(xPoints[i], yPoints[i]));
        }

        return points;
    }
}
