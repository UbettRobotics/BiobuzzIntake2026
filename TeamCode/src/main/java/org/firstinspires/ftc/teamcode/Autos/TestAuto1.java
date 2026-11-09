package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name = "Auto Right")
public class TestAuto1 extends LinearOpMode {

    // Same mixing as Robot.driveRC()
    private void move(double forward, double strafe, double turn) {
        double denom = Math.max(Math.abs(forward) + Math.abs(strafe) + Math.abs(turn), 1);

        double lf = (forward + strafe + turn) / denom;
        double lb = (forward - strafe + turn) / denom;
        double rf = (forward - strafe - turn) / denom;
        double rb = (forward + strafe - turn) / denom;

        Robot.drive(rf, rb, lb, lf);
    }

    private void moveFor(double forward, double strafe, double turn, long ms) {
        move(forward, strafe, turn);
        sleep(ms);
        move(0, 0, 0);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(this);
        robot.initDrive();

        waitForStart();

        if (opModeIsActive()) {
            moveFor(0, 0, -0.4, 600); // strafe right, 1 second at 40%

            sleep(5000);
        }
    }
}