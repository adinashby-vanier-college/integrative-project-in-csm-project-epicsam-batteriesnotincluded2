import edu.vanier.physics.Kinematics;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KinematicsTest {
    @Test
    void proj_calcTime() {
        Kinematics k = new Kinematics(90, 9.8, 0, 10, 0, 0);
        double expectedTime = 2 * 10 / 9.8;
        assertEquals(expectedTime, k.proj_calcTime());
    }

    @Test
    void proj_calcMaxHeight() {
        Kinematics k = new Kinematics(90, 9.8, 0, 20, 0, 0);
        double expected = (20 * 20) / (2 * 9.8);
        assertEquals(expected, k.proj_calcMaxHeight());
    }

    @Test
    void proj_calcDistance() {
        Kinematics k = new Kinematics(0, 9.8, 0, 10, 0, 0);
        double time = k.proj_calcTime(); // just viY = 0, so time = 0
        double expectedDistance = 10 * time;
        assertEquals(expectedDistance, k.proj_calcDistance());
    }

    @Test
    void kinematic_calcFinalVelocity() {
        Kinematics k = new Kinematics(0, 2, 0, 0, 0, 5);
        assertEquals(10.0, k.kinematic_calcFinalVelocity());
    }

    @Test
    void kinematic_calcFinalPosition() {
        Kinematics k = new Kinematics(0, 2, 0, 0, 0, 5);
        assertEquals(25.0, k.kinematic_calcFinalPosition());
    }

    @Test
    void kinematic_calcTotalDistance() {
        Kinematics k = new Kinematics(0, 2, 0, 0, 0, 5);
        assertEquals(25.0, k.kinematic_calcTotalDistance());
    }

    @Test
    void proj_calcVerticalPosition() {
        Kinematics k = new Kinematics(90, 9.8, 10, 20, 0, 0);
        double time = 1;
        double expected = 10 + 20 * time - 0.5 * 9.8 * time * time;
        assertEquals(expected, k.proj_calcVerticalPosition(time));
    }

    @Test
    void proj_calcHorizontalPosition() {
        Kinematics k = new Kinematics(0, 9.8, 0, 10, 0, 0);
        double time = 2;
        double expected = 10 * time;
        assertEquals(expected, k.proj_calcHorizontalPosition(time));
    }

    @Test
    void proj_calcHorizontalVelocity() {
        Kinematics k = new Kinematics(0, 9.8, 0, 10, 0, 0);
        assertEquals(10.0, k.proj_calcHorizontalVelocity());
    }

    @Test
    void proj_calcVerticalVelocity() {
        Kinematics k = new Kinematics(90, 9.8, 0, 10, 0, 0);
        double time = 2;
        double expected = 10 - 9.8 * time;
        assertEquals(expected, k.proj_calcVerticalVelocity(time));
    }

    @Test
    void kinematic_calcWhatVelocity() {
        Kinematics k = new Kinematics(0, -9.8, 0, 30, 0, 0);
        double time = 2;
        double expected = 30 - 9.8 * time;
        assertEquals(expected, k.kinematic_calcWhatVelocity(time));
    }

    @Test
    void kinematic_calcWhatPosition() {
        Kinematics k = new Kinematics(0, -9.8, 0, 30, 100, 0);
        double time = 2;
        double expected = 100 + 30 * time - 0.5 * 9.8 * time * time;
        assertEquals(expected, k.kinematic_calcWhatPosition(time));
    }
}