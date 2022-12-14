package org.firstinspires.ftc.Team19567.util.testing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Opmode to test the carousel motor; used prior to the first and second qualifiers
 */
@TeleOp(name="Carousel Test", group="Testing Programs")
@Disabled
@Deprecated
public class CarouselTest extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor carouselDC = null;

    public void init() {
        telemetry.addData("Status", "Initialized");

        carouselDC = hardwareMap.get(DcMotor.class, "rightFront");

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void init_loop() {
        telemetry.addData("Status", "Waiting for start");
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        double power = gamepad1.left_stick_y;

        carouselDC.setPower(power);

        telemetry.addData("Status", "Run Time: " + runtime.toString());
    }

    @Override
    public void stop() {
        telemetry.addData("Status", "Stopped");
    }

}
