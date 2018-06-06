import com.sun.javafx.geom.Vec3f;

import java.util.LinkedList;
import java.util.List;

public class Physics {
    int testCase;
    final static float DIAMETER = 2f;
    float x1;
    float y1;

    float x2;
    float y2;

    float dx1;
    float dy1;

    float dx2;
    float dy2;

    Physics(int testCase) {
        this.testCase = testCase;
    }

    public ResponseData getTrajectory() {
        List<Vec3f> trajectoryBall1 = new LinkedList<>();
        List<Vec3f> trajectoryBall2 = new LinkedList<>();

        switch (testCase) {
            case 0: {
                x1 = -25f;
                y1 = 25f;

                x2 = 25f;
                y2 = -25f;

                dx1 = 0.045f;
                dy1 = -0.045f;

                dx2 = -0.045f;
                dy2 = 0.045f;

                break;
            }
            case 1: {

                x1 = -25f;
                y1 = 20f;

                x2 = -25f;
                y2 = 0.6f;

                dx1 = 0.045f;
                dy1 = -0.045f;

                dx2 = 0.045f;
                dy2 = 0;

                break;
            }
        }

        for (int count = 0; count < 50000; count++, x1 += dx1, y1 += dy1, x2 += dx2, y2 += dy2) {
            trajectoryBall1.add(new Vec3f(x1, y1, 0));
            trajectoryBall2.add(new Vec3f(x2, y2, 0));

            if (areIntersecting()) {
                System.out.println("\n\nIntersect!");
                System.out.println("x1 = " + x1 + "; y1 = " + y1 + "; x2 = " + x2 + "; y2 = " + y2);
//                break;
                computeNewVectors();
            }
        }

        return new ResponseData(trajectoryBall1, trajectoryBall2);
    }

    private boolean areIntersecting() {
        return (Math.sqrt(Math.pow((double) (x2 - x1), 2) + Math.pow((double) (y2 - y1), 2)) <= DIAMETER);
    }

    private void computeNewVectors() {
        float temp = dx1;
        dx1 = dx2;
        dx2 = temp;

        temp = dy1;
        dy1 = dy2;
        dy2 = temp;
    }
}
