package org.firstinspires.ftc.Team19567.pipeline;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

/**
 * An attempt at making an "all-in-one" pipeline that could detect whatever based on the information passed in the constructor <br>
 * However, it never proved to be useful and was just too complicated; <br>
 * A better use would have been creating a base thresholding pipeline class instead. <br> <br>
 * This pipeline is a combination of {@link org.firstinspires.ftc.Team19567.pipeline.greenPipeline greenPipeline}, {@link org.firstinspires.ftc.Team19567.pipeline.redPipeline redPipeline}, and {@link org.firstinspires.ftc.Team19567.pipeline.bluePipeline bluePipeline}. <br>
 * The logic and code behind this pipeline is scuffed and should have been executed differently.
 * If you want to study a proper pipeline, see {@link org.firstinspires.ftc.Team19567.pipeline.greenPipeline greenPipeline.java} instead.
 */
@Deprecated
public class tsePipeline extends OpenCvPipeline {
    private Mat output = new Mat();
    private static final double width = 544;
    private static final double height = 288;
    private static final double margin = 10;
    private static final double one_square = (width-4*margin)/3;
    private static final Rect LEFT_SQUARE = new Rect(
            new Point(margin,margin), new Point(margin+one_square,height-margin)
    );

    private static final Rect RIGHT_SQUARE = new Rect(
            new Point(2*margin+one_square,margin), new Point(2*(margin+one_square),height-margin)
    );

    private static final Rect RIGHTEST_SQUARE = new Rect(
            new Point(3*margin+2*one_square,margin), new Point( 3*(margin+one_square),height-margin)
    );

    private Scalar lowHSV = new Scalar(36, 50, 70);
    private Scalar highHSV = new Scalar(89, 255, 255);
    private double firstConf = 0.0;
    private double secondConf = 0.0;
    private double thirdConf = 0.0;
    private boolean tseFirst = false;
    private boolean tseSecond = false;
    private boolean tseThird = false;
    Scalar detectedColor = new Scalar(0,255,0);
    Scalar none = new Scalar(255,0,0);

    private LOCATION location = LOCATION.ALLIANCE_THIRD;

    private static double THRESHOLD = 0.025;

    Telemetry telemetry;
    COLOR color = COLOR.TSE;

    public tsePipeline(COLOR color, Telemetry t) {
        telemetry = t;
        this.color = color;
        switch(color) {
            case RED: {
                this.lowHSV = new Scalar(100, 150, 0);
                this.highHSV = new Scalar(140, 255, 255);
            }
            case BLUE: {
                this.lowHSV = new Scalar(100, 150, 0);
                this.highHSV = new Scalar(140, 255, 255);
            }
            case TSE: {
                this.lowHSV = new Scalar(36, 50, 70);
                this.highHSV = new Scalar(89, 255, 255);
            }
        }
    }


    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input,output,Imgproc.COLOR_RGB2HSV);
        telemetry.addData("Pipeline Status","Setup Complete");

        Core.inRange(output,lowHSV,highHSV,output);
        telemetry.addData("Pipeline Status","InRange Conv. Completed");

        Mat first = output.submat(LEFT_SQUARE);
        Mat second = output.submat(RIGHT_SQUARE);
        Mat third = output.submat(RIGHTEST_SQUARE);
        telemetry.addData("Pipeline Status","Submats Computed");

        firstConf = Core.sumElems(first).val[0] / LEFT_SQUARE.area()/255;
        secondConf = Core.sumElems(second).val[0] / RIGHT_SQUARE.area()/255;
        thirdConf = Core.sumElems(third).val[0] / RIGHTEST_SQUARE.area()/255;

        telemetry.addData("Pipeline Status","Confidences Ascertained");

        first.release();
        second.release();

        telemetry.addData("Left",(int) Core.sumElems(first).val[0]);
        telemetry.addData("Right",(int) Core.sumElems(second).val[0]);
        telemetry.addData("LeftP(%.2f)",firstConf*100);
        telemetry.addData("RightP(%.2f)",secondConf*100);
        telemetry.addData("Telemetry Status","Values Broadcasted via Telemetry");

        tseFirst = firstConf > THRESHOLD;
        tseSecond = secondConf > THRESHOLD;
        tseThird = thirdConf > THRESHOLD;

        telemetry.addData("Yop?",tseFirst);
        telemetry.addData("Yop2?",tseSecond);
        telemetry.addData("Yop3?",tseThird);

        if(color == COLOR.TSE) {
            if (tseFirst) {
                location = LOCATION.ALLIANCE_FIRST;
                telemetry.addData("Level", "First");
            } else if (tseSecond) {
                location = LOCATION.ALLIANCE_SECOND;
                telemetry.addData("Level", "Second");
            } else if (tseThird) {
                location = LOCATION.ALLIANCE_THIRD;
            } else {
                location = LOCATION.NO_ALLIANCE;
                telemetry.addData("Level", "Third");
            }
        }
        else {
            if(!tseFirst) {
                location = LOCATION.ALLIANCE_FIRST;
                telemetry.addData("Level","First");
            }
            else if(!tseSecond) {
                location = LOCATION.ALLIANCE_SECOND;
                telemetry.addData("Level","Second");
            }
            else if(!tseThird) {
                location = LOCATION.ALLIANCE_THIRD;
            }
            else {
                location = LOCATION.NO_ALLIANCE;
                telemetry.addData("Level","Third");
            }
        }
        telemetry.addData("OpenCV Status","Location Decided");

        Imgproc.cvtColor(output, output, Imgproc.COLOR_GRAY2RGB);


        telemetry.addData("OpenCV Status","Final Conversion + Color Complete");
        telemetry.update();

        Imgproc.rectangle(output,LEFT_SQUARE,location==LOCATION.ALLIANCE_FIRST? detectedColor:none);
        Imgproc.rectangle(output,RIGHT_SQUARE,location==LOCATION.ALLIANCE_SECOND? detectedColor:none);
        Imgproc.rectangle(output,RIGHTEST_SQUARE,(location==LOCATION.ALLIANCE_THIRD || location==LOCATION.NO_ALLIANCE)? detectedColor:none);
        telemetry.addData("OpenCV Status","Rectangles Drawn");
        System.gc();
        telemetry.addData("Input Frame Size",input.rows()+" x "+input.cols());
        telemetry.addData("Output Frame Size", output.rows()+" x "+output.cols());
        telemetry.update();

        return output;
    }

    public LOCATION getLocation() {
        return location;
    }
}