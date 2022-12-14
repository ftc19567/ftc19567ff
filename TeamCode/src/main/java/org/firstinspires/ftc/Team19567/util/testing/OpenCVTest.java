package org.firstinspires.ftc.Team19567.util.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.Team19567.pipeline.greenPipeline;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.firstinspires.ftc.Team19567.pipeline.LOCATION;

/**
 * Opmode to test OpenCV without running the robot in any other way. <br>
 * Genuinely useful!
 */
@Autonomous(name="OpenCV Test",group="Testing")
public class OpenCVTest extends LinearOpMode {
    private LOCATION location = LOCATION.ALLIANCE_THIRD;
    @Override
    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap
                        .appContext.getPackageName());
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        OpenCvCamera camera = OpenCvCameraFactory.getInstance()
                .createWebcam(webcamName,cameraMonitorViewId);

        greenPipeline pipeline = new greenPipeline(telemetry);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(544,288, OpenCvCameraRotation.UPRIGHT);
                camera.setPipeline(pipeline);
                telemetry.addData("OpenCV","OpenCV actually connected wow");
                telemetry.update();
            }
            @Override
            public void onError(int errorCode) {
                telemetry.addData("OpenCV","OpenCV failed to load :( Error Code: " + errorCode);
                telemetry.update();
            }
        });
        waitForStart();
        if(!opModeIsActive()) return;
    }
}
