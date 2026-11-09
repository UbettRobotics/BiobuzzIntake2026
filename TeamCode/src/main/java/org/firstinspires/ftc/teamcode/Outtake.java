package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Outtake {
    DcMotorEx OuttakeMotor;
//    DcMotorEx OuttakeMotor2;
    LinearOpMode opMode;
    CRServo Transmission;

    public Outtake(LinearOpMode opMode){
        this.opMode = opMode;
        // Each device is optional: tries the names used across the project, null if missing
        OuttakeMotor = Robot.getFirst(opMode.hardwareMap, DcMotorEx.class, "OuttakeMotor", "Outtake");
//        OuttakeMotor2 = Robot.getFirst(opMode.hardwareMap, DcMotorEx.class, "OuttakeMotor 2", "OuttakeMotor2");
        Transmission = Robot.getFirst(opMode.hardwareMap, CRServo.class, "Transmission", "transmission");

        if (OuttakeMotor != null) OuttakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        if (Transmission != null) Transmission.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setPower(double power) {
        if (OuttakeMotor != null) OuttakeMotor.setPower(power);
     //   if (OuttakeMotor2 != null) OuttakeMotor2.setPower(power);
    }

    public void setTransmissionPower(double power) {
        if (Transmission != null) Transmission.setPower(power);
    }

    public String status() {
        return "motor1=" + (OuttakeMotor != null ? "ok" : "MISSING")
  //              + " motor2=" + (OuttakeMotor2 != null ? "ok" : "MISSING")
                + " transmission=" + (Transmission != null ? "ok" : "MISSING");
    }
}