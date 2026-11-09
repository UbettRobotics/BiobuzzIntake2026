package org.firstinspires.ftc.teamcode.Autos;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Intake;
import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name = "Auto Left")
public class Auto2 extends LinearOpMode {

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
        Intake intake = new Intake(this);
        intake.setPower(0.0); // Ensure the intake starts stopped.

        telemetry.addLine("Ready");
        telemetry.addData("Intake", intake.status());
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            telemetry.addLine("Going...");
            telemetry.update();

            // Example intake use during autonomous:
            // intake.setPower(1.0);  // Run intake forward to collect
            // intake.setPower(-1.0); // Reverse intake to clear a jam
            // intake.setPower(0.0);  // Stop intake

            moveFor(0, 0, 0.4, 600); // Existing autonomous movement
            intake.setPower(0.0);

            sleep(5000);
        }
    }
}