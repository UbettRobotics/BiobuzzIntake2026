package org.firstinspires.ftc.teamcode.telops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.Intake;
import org.firstinspires.ftc.teamcode.Outtake;
import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name="TeleOp_Integrated", group="TeleOp")
public class Teleop extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private Robot robot;
    private Outtake outtake;
    private Intake intake;

    @Override
    public void runOpMode() {
        try {
            runRobot();
        } catch (Exception e) {
            showCrash(e);
        }
    }

    private void runRobot() {

        robot = new Robot(this);

        robot.initAll(0, true);

        outtake = new Outtake(this);
        intake = new Intake(this);

        telemetry.addData("Status", "Initialized");
        telemetry.addData("Intake", intake.status());
        telemetry.addData("Outtake", outtake.status());
        telemetry.addData("Config motors", hardwareMap.getAllNames(DcMotor.class));
        telemetry.addData("Config CR servos", hardwareMap.getAllNames(CRServo.class));
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            if (!robot.testWheels()) {
                robot.driveRC();
            }


            if (gamepad1.right_bumper) {
                intake.setPower(1.0);
            } else if (gamepad1.left_bumper) {
                intake.setPower(-1.0);
            } else {
                intake.setPower(0.0);
            }

            if (gamepad2.dpad_up) {
                outtake.setPower(1.0);
            } else if (gamepad2.dpad_down) {
                outtake.setPower(-1.0);
            } else {
                outtake.setPower(0.0);
            }

            if (gamepad2.right_bumper) {
                outtake.setTransmissionPower(1.0);
            } else if (gamepad2.left_bumper) {
                outtake.setTransmissionPower(-1.0);
            } else {
                outtake.setTransmissionPower(0.0);
            }

            telemetry.addData("Run Time", runtime.toString());
            telemetry.addData("Intake", intake.status());
            telemetry.addData("Outtake", outtake.status());
            telemetry.addData("Gamepad2 connected?", gamepad2.getGamepadId() != -1 ? "yes" : "NO");
            telemetry.update();
        }
    }












    // show crash
    private void showCrash(Exception e) {
        RobotLog.ee("TELEOP_CRASH", e, "Crash in Teleop");

        telemetry.clearAll();
        telemetry.addData("CRASH", e.getClass().getName());
        telemetry.addData("Message", String.valueOf(e.getMessage()));

        StackTraceElement[] st = e.getStackTrace();
        int shown = 0;
        for (StackTraceElement el : st) {
            if (el.getClassName().startsWith("org.firstinspires.ftc.teamcode")) {
                telemetry.addLine(el.toString());
                if (++shown >= 6) break;
            }
        }
        for (int i = 0; i < Math.min(4, st.length); i++) {
            telemetry.addLine(st[i].toString());
        }
        telemetry.update();

        while (!isStopRequested()) {
            sleep(100);
        }
    }
}