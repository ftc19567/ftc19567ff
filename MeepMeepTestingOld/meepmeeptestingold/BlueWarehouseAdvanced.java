package com.example.meepmeeptestingold;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;

public class BlueWarehouseAdvanced {
    public static void main(String args[]) {
        MeepMeep mm = new MeepMeep(800)
                // Set field image.
                .setBackground(MeepMeep.Background.FIELD_FREIGHT_FRENZY)
                // Set theme
                .setTheme(new ColorSchemeRedDark())
                // Background opacity from 0-1
                .setBackgroundAlpha(1f)
                // Set constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(57.635630404559784, 38.7, Math.toRadians(180), Math.toRadians(180), 15).setBotDimensions(13.2,16.603)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(10, 63, Math.toRadians(90)))
                                .addSpatialMarker(new Vector2d(40,64),() -> { /* mechanisms.moveIntake(0.7); */ })
                                .addSpatialMarker(new Vector2d(5,50),() -> { /* mechanisms.rotateArm(650,0.25); */ })
                                .lineToSplineHeading(new Pose2d(-2,40, Math.toRadians(240)))
                                .addDisplacementMarker(() -> {
                                    /*
                                    mechanisms.releaseServoMove(0.3);
                                     */
                                })
                                .addDisplacementMarker(() -> { /*mechanisms.reset(); */ })
                                .setReversed(true).splineTo(new Vector2d(10, 60),Math.toRadians(20))
                                .splineTo(new Vector2d(50,64),Math.toRadians(0)).setReversed(true)
                                //.lineToLinearHeading(new Pose2d(12,-64,Math.toRadians(180))).strafeTo(new Vector2d(50,-64))//.splineToSplineHeading(new Pose2d(4,-62,Math.toRadians(180)),Math.toRadians(90)).splineToConstantHeading(new Vector2d(50, -64), Math.toRadians(0))
                                .back(5).forward(5)
                                /* .splineToConstantHeading(new Vector2d(36, -64),0) */
                                .addDisplacementMarker(() -> { /*
                                    mechanisms.reset();
                                */ })
                                .addSpatialMarker(new Vector2d(0,50),() -> {
                                    /* mechanisms.rotateArm(650,0.25); */
                                }).splineTo(new Vector2d(-11.5,42),Math.toRadians(-90))
                                .addDisplacementMarker(() -> {
                                    /*
                                    mechanisms.releaseServoMove(0.3);
                                    */
                                })
                                .setReversed(true).addDisplacementMarker(() -> {
                                    /*
                                    mechanisms.reset();
                                    */
                        })
                                .addSpatialMarker(new Vector2d(0,50),() -> {
                                    /* mechanisms.rotateArm(650,0.25); */ })
                                .splineTo(new Vector2d(50,64),Math.toRadians(0))
                                .forward(5).back(5)
                                .setReversed(false)
                                .splineTo(new Vector2d(-11.5,42),Math.toRadians(-90))
                                .addDisplacementMarker(() -> {
                                    /*
                                    mechanisms.releaseServoMove(0.3);
                                    */
                                }).addSpatialMarker(new Vector2d(40,64),() -> { /* mechanisms.moveIntake(0.7); */ })
                                .setReversed(true).addDisplacementMarker(() -> {
                                    /*
                                    mechanisms.reset();
                                    */
                        })
                                .splineTo(new Vector2d(50,64),Math.toRadians(0)).forward(5).back(5)
                                .setReversed(false)
                                .splineTo(new Vector2d(-11.5,42),Math.toRadians(-90))
                                .addDisplacementMarker(() -> {
                                    /*
                                    mechanisms.releaseServoMove(0.3);
                                    */
                                }).addSpatialMarker(new Vector2d(40,64),() -> { /* mechanisms.moveIntake(0.7); */ })
                                .setReversed(true).addDisplacementMarker(() -> {
                                    /*
                                    mechanisms.reset();
                                    */
                        })
                                .splineTo(new Vector2d(50,64),Math.toRadians(0))
                                .build()).start();
    }
}