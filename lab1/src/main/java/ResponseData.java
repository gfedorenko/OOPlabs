import com.sun.javafx.geom.Vec3f;

import java.util.List;

public class ResponseData {

    private List<Vec3f> trajectoryBall1;
    private List<Vec3f> trajectoryBall2;

    ResponseData(List<Vec3f> trajectoryBall1, List<Vec3f> trajectoryBall2) {
        this.trajectoryBall1 = trajectoryBall1;
        this.trajectoryBall2 = trajectoryBall2;
    }

    public List<Vec3f> getTrajectoryBall1() {
        return trajectoryBall1;
    }

    public void setTrajectoryBall1(List<Vec3f> trajectoryBall1) {
        this.trajectoryBall1 = trajectoryBall1;
    }

    public List<Vec3f> getTrajectoryBall2() {
        return trajectoryBall2;
    }

    public void setTrajectoryBall2(List<Vec3f> trajectoryBall2) {
        this.trajectoryBall2 = trajectoryBall2;
    }
}
