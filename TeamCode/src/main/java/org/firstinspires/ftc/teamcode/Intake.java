package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;

/**
 * Controls the robot intake motor.
 *
 * Configure the motor on the Control Hub as either "Intake" or "IntakeMotor".
 */
public class Intake {
    private final DcMotorEx intakeMotor;

    public Intake(LinearOpMode opMode) {
        // Supports either hardware configuration name used by this project.
        intakeMotor = Robot.getFirst(
                opMode.hardwareMap,
                DcMotorEx.class,
                "Intake",
                "IntakeMotor"
        );
    }

    /** Set intake motor power from -1.0 (reverse) to 1.0 (forward). */
    public void setPower(double power) {
        if (intakeMotor != null) {
            intakeMotor.setPower(Range.clip(power, -1.0, 1.0));
        }
    }

    /** Returns whether the intake motor was found in the hardware configuration. */
    public boolean isAvailable() {
        return intakeMotor != null;
    }

    public String status() {
        return isAvailable() ? "OK" : "MISSING (configure Intake or IntakeMotor)";
    }
}
