package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.*;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {

    public static DcMotorEx fr;
    public static DcMotorEx br;
    public static DcMotorEx bl;
    public static DcMotorEx fl;

    public double slow = 0.67;
    public double fast = 1.0;

    public GoBildaPinpointDriver odo;
    public AutonomousDrive2 ad2;

    private LinearOpMode opMode;

    public Robot(LinearOpMode opmode) {
        this.opMode = opmode;
    }

    public static <T> T getFirst(HardwareMap map, Class<T> type, String... names) {
        for (String name : names) {
            if (name == null) continue;
            try {
                return map.get(type, name);
            } catch (IllegalArgumentException ignored) {
                // not in config under this name, try the next one
            }
        }
        return null;
    }

    public void initAll(int sideNum) {
        initAll(sideNum, false);
    }

    public void initAll(int sideNum, boolean b) {
        odo = opMode.hardwareMap.get(GoBildaPinpointDriver.class, "odo");

        initDrive();

        if (ad2 == null) {
            ad2 = new AutonomousDrive2(opMode, this, b);
        }
    }

    public void initDrive() {
        fr = opMode.hardwareMap.get(DcMotorEx.class, "fr");
        br = opMode.hardwareMap.get(DcMotorEx.class, "br");
        bl = opMode.hardwareMap.get(DcMotorEx.class, "bl");
        fl = opMode.hardwareMap.get(DcMotorEx.class, "fl");


        DcMotorEx[] driveMotors = new DcMotorEx[]{fr, fl, br, bl};

        for (DcMotorEx motor : driveMotors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        fl.setDirection(REVERSE);
        bl.setDirection(REVERSE);
        fr.setDirection(REVERSE);
        br.setDirection(FORWARD);
    }


    public static void drive(double rfPower, double rbPower, double lbPower, double lfPower) {
        fr.setPower(rfPower);
        br.setPower(rbPower);
        bl.setPower(lbPower);
        fl.setPower(lfPower);
    }

    public boolean testWheels() {
        Gamepad g = opMode.gamepad1;
        double t = 0.4;
        double lf = 0, rf = 0, lb = 0, rb = 0;
        boolean any = false;
        if (g.dpad_up)    { lf = t; any = true; }
        if (g.dpad_right) { rf = t; any = true; }
        if (g.dpad_down)  { rb = t; any = true; }
        if (g.dpad_left)  { lb = t; any = true; }
        if (any) drive(rf, rb, lb, lf);
        return any;
    }

    public void driveRC() {
        if (fr == null) return;

//left stick x = rx
// y = x
// right stick x = y

        double rx = -opMode.gamepad1.left_stick_x; // controls  x
        double y = opMode.gamepad1.right_stick_x; // controls rx
        double x = opMode.gamepad1.left_stick_y; //controls y


        x = x * 1.1; // counteract imperfect strafing

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double speed = (opMode.gamepad1.right_trigger > 0.5) ? slow : fast;

        double lf = (y + x + rx) / denominator * speed;
        double lb = (y - x + rx) / denominator * speed;
        double rf = (y - x - rx) / denominator * speed;
        double rb = (y + x - rx) / denominator * speed;

        drive(rf, rb, lb, lf);
    }
}