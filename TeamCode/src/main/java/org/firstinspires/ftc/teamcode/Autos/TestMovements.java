package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Robot; // Import the instance-based Robot class

@Autonomous(name = "Test Movements")
public class TestMovements extends LinearOpMode {

        @Override
        public void runOpMode() throws InterruptedException {
                // 1. Create a clean local instance of the robot object for this OpMode run
                Robot robot = new Robot(this);

                // 2. Initialize the hardware safely through the robot instance
                robot.initAll(0);

                // 3. Update ad2 configuration flags through the robot instance wrapper
                // Note: Replaced .outputInfo() with .setOutputInfo(true) if your method signatures align
                robot.ad2.setOutputInfo(true);
                robot.ad2.setTimeLimit(2);

                // 4. Build your spline path networks using the clean object scope
                double t = 0;
                robot.ad2.createPath(new double[]{0,0, 180}, 0);
                robot.ad2.getPath(0).addSpline(24, -24, 180, 90, 20);
                robot.ad2.getPath(0).addSpline(48, 0, 180, 270, 20);
                robot.ad2.getPath(0).addSpline(0, 0, 180, 0, 10);
                robot.ad2.getPath(0).addSpline(24, -24, 0, 90, 20);
                robot.ad2.getPath(0).addSpline(48, 0, 270, 270, 20);
                robot.ad2.getPath(0).addSpline(0, 0, 180, 0);

                robot.ad2.createPath(new double[]{0,0, 180}, 90);
                robot.ad2.getPath(1).addSpline(12, -18, 270, 0, 15);
                robot.ad2.getPath(1).addSpline(12, -38, 90, 90, 20);
                robot.ad2.getPath(1).addSpline(36, -36, 310, 315, 10);

                // Wait for the driver to press PLAY on the Driver Station
                waitForStart();

                if (opModeIsActive()) {
                        telemetry.addLine("Try 1 Done:");
                        telemetry.update();

                        // 5. Run active trajectory movements sequentially
                        for (int i = 1; i <= 4; i++) {
                                robot.ad2.goToHeading(180 + 90 * i);
                        }

                        robot.ad2.runPathConstantHeading(0, 0.025);

                        for (int i = 1; i <= 4; i++) {
                                robot.ad2.goToHeading(180 + 90 * i);
                        }

                        robot.ad2.runPathConstantHeading(1, 0.025);

                        sleep(2600);

                        robot.ad2.goToPointLinear(0, 0, 180);

                        telemetry.addLine("All Done!!");
                        telemetry.update();
                }
        }
}
